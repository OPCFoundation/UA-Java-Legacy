/* Copyright (c) 1996-2015, OPC Foundation. All rights reserved.
   The source code in this file is covered under a dual-license scenario:
     - RCL: for OPC Foundation members in good-standing
     - GPL V2: everybody else
   RCL license terms accompanied with this source code. See http://opcfoundation.org/License/RCL/1.00/
   GNU General Public License as published by the Free Software Foundation;
   version 2 of the License are accompanied with this source code. See http://opcfoundation.org/License/GPLv2
   This source code is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
*/

package org.opcfoundation.ua.application;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opcfoundation.ua.builtintypes.ServiceRequest;
import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.ServiceFault;
import org.opcfoundation.ua.core.SessionServiceSetHandler;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.encoding.IEncodeable;
import org.opcfoundation.ua.transport.endpoint.EndpointServiceRequest;

/**
 * Composition of ServiceHandlers.
 */
public class ServiceHandlerComposition implements ServiceHandler {

	/** Logger */
	static Logger log = LoggerFactory.getLogger(ServiceHandlerComposition.class);
	
	/**
	 * Create service handler composed of a set of service handlers.
	 *
	 * Service handlers either (a) implement {@link ServiceHandler} or
	 * implement service request methods, e.g. methods in {@link SessionServiceSetHandler}.
	 *
	 * @param handlers service handlers.
	 * @return a new service handler
	 */
	public static final ServiceHandler of(Object...handlers)
	{
		if (handlers.length==1 && handlers[0] instanceof ServiceHandler) return (ServiceHandler) handlers[0];
		ServiceHandlerComposition result = new ServiceHandlerComposition();
		for (Object h : handlers)
			result.add(h);
		return result;
	}
	
	Map<Class<? extends IEncodeable>, ServiceHandler> handlerMap = 
		new HashMap<Class<? extends IEncodeable>, ServiceHandler>();
	
	Map<Class<? extends IEncodeable>, Object> handlerMap2 = 
		new HashMap<Class<? extends IEncodeable>, Object>();
	ServiceHandler[] handlers;
	
	/**
	 * <p>Constructor for ServiceHandlerComposition.</p>
	 */
	public ServiceHandlerComposition() 
	{		
	}
	
	/**
	 * Add <tt>ServiceHandler</tt> or Service handling object.
	 *
	 * <tt>ServiceHandler</tt> is added as is, other objects are
	 * inspectew with reflected and suitable service handling
	 * methods are added.
	 *
	 * A method is suitable for service handing if it has no return arguments
	 * and one parametrized argument of EndpointServiceReqest.
	 *
	 * For example:
	 *
	 * new Object() {
	 * 	public void onTestStack(
	 *		EndpointServiceRequest&lt;TestStackRequest, TestStackResponse&gt; req) {}
	 * }
	 *
	 * @param o <tt>ServiceHandler</tt> or Service handling object.
	 */
	public void add(Object o)
	{
		if (o==null) throw new IllegalArgumentException("null");
		if (o instanceof ServiceHandler)
		{
			ServiceHandler h = (ServiceHandler) o;
			ArrayList<Class<? extends IEncodeable>> list = new ArrayList<Class<? extends IEncodeable>>();
			h.getSupportedServices(list);
			for (Class<? extends IEncodeable> clazz : list)
			{
				ServiceHandler oldHandler = handlerMap.get(clazz);
				if (oldHandler!=null && h!=oldHandler)
					throw new RuntimeException("ServiceHandlerComposition already handles "+clazz);
				handlerMap.put(clazz, h);
				handlerMap2.put(clazz, o);
			}
		} else {
			readWithReflection(o, this);
		}
		handlers = handlerMap.values().toArray(new ServiceHandler[0]);
	}
	
	/**
	 * <p>getServiceHandlers.</p>
	 *
	 * @return an array of {@link org.opcfoundation.ua.application.ServiceHandler} objects.
	 */
	public ServiceHandler[] getServiceHandlers()
	{
		return handlers;
	}	
	
	/** {@inheritDoc} */
	@Override
	public void serve(EndpointServiceRequest<?, ?> request) throws ServiceResultException 
	{
		//Check isDebugEnabled() left here for possible performance reasons.
		if (log.isDebugEnabled()) {
			log.debug("serve: {}", request.getRequest().getClass().getSimpleName());
			log.debug("serve: handlerMap={}", Arrays.toString(handlerMap.keySet().toArray(new Class[0])));
		}
		ServiceHandler handler = handlerMap.get(request.getRequest().getClass());
		log.debug("serve: handler={}", handler);
		if (handler==null) {						
			log.info("Service {} is not supported", request.getRequest().getClass().getSimpleName());
			sendErrorResponse(request, new ServiceResultException(StatusCodes.Bad_ServiceUnsupported,
					request.getRequest().getClass().getSimpleName()));
			return;
		}
		try {
			handler.serve(request);
		} catch (ServiceResultException e) {
			log.error("While handling " + request.getRequest(), e);
			sendErrorResponse(request, e);
		}
		
	}


	/**
	 * @param request
	 * @param serviceResult 
	 * @param additionalInfo 
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	private void sendErrorResponse(EndpointServiceRequest<?, ?> request, ServiceResultException e)
	{
		// TODO: Use DiagnosticInfoMask from request
		ServiceFault serviceFault = ServiceFault.toServiceFault(e);
		request.sendFault(serviceFault);
		return;
	}

	/** {@inheritDoc} */
	@Override
	public boolean supportsService(Class<? extends IEncodeable> clazz) {
		return handlerMap.containsKey(clazz);
	}

	/** {@inheritDoc} */
	@Override
	public void getSupportedServices(Collection<Class<? extends IEncodeable>> result) {
		result.addAll(handlerMap.keySet());
	}

	/**
	 * Returns the service handler that handles given request.
	 *
	 * @param <T> service handler class
	 * @param requestClass request class
	 * @return the service handler or null
	 */
	@SuppressWarnings("unchecked")
	public <T> T getServiceHandlerByService(Class<? extends ServiceRequest> requestClass)
	{
		return (T) handlerMap2.get(requestClass);
	}	
	
	/**
	 * Reads supported service handler methods with reflection.
	 *
	 * @param serviceHandler service handler
	 * @param result composition where reflection based handlers are added
	 */
	@SuppressWarnings("unchecked")
	public static void readWithReflection(final Object serviceHandler, ServiceHandlerComposition result)
	{
		Class<?> clazz = serviceHandler.getClass();
		
		for (final Method m : clazz.getMethods())
		{					
			// Is method handling method
			if (m.getReturnType()!=void.class)
				continue;
			
			Class<?>[] params = m.getParameterTypes();
			if (params==null || params.length!=1) continue;
			if (!EndpointServiceRequest.class.isAssignableFrom(params[0])) continue;
			Type[] types = m.getGenericParameterTypes();
			if (types==null || types.length!=1 || !(types[0] instanceof ParameterizedType)) continue;
			ParameterizedType pt = (ParameterizedType) types[0];
			types = pt.getActualTypeArguments();
			if (types==null || types.length!=2 || types[0]==null || types[1]==null) continue;			
			
			if (!(types[0] instanceof Class) || !IEncodeable.class.isAssignableFrom((Class<?>)types[0])) continue;
			if (!(types[1] instanceof Class) || !IEncodeable.class.isAssignableFrom((Class<?>)types[1])) continue;
			
			Class<? extends IEncodeable> req = (Class<? extends IEncodeable>) types[0]; 
			// not used: Class<? extends IEncodeable> res = (Class<? extends IEncodeable>) types[1];
			
			m.setAccessible(true);
			if (!m.isAccessible())
				throw new Error(clazz.getName() +"."+ m.getName()+" is not accessible to be used as a service handler");
			
			ServiceHandler h = new AbstractServiceHandler(req) {
				@Override
				public void serve(EndpointServiceRequest<?, ?> request) throws ServiceResultException {
					try {
						m.invoke(serviceHandler, request);
					} catch (IllegalArgumentException e) {
						throw new Error(e);
					} catch (IllegalAccessException e) {
						throw new Error(e);
					} catch (InvocationTargetException e) {
						Throwable e2 = e.getCause() != null ? e.getCause() : e;

						// Throw error in service handling as a service result exception which will 
						// be adapted to a service response object.
						if (e2 instanceof ServiceResultException) throw (ServiceResultException) e2;
						else throw new ServiceFaultException(e2);						
					}
				}};
			result.add( h );
			result.handlerMap2.put(req, serviceHandler);
		}		
	}
	
	
}
