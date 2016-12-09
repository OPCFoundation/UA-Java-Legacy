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

package org.opcfoundation.ua.transport.impl;

import java.util.concurrent.Executor;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import org.opcfoundation.ua.common.ServiceFaultException;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.transport.AsyncResult;
import org.opcfoundation.ua.transport.ResultListener;
import org.opcfoundation.ua.utils.StackUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>AsyncResultImpl class.</p>
 *
 */
public class AsyncResultImpl<T> implements AsyncResult<T> {

	/** Logger */
	static Logger logger = LoggerFactory.getLogger(AsyncResultImpl.class);
	static Logger listenerLogger = LoggerFactory.getLogger(ResultListener.class);

	static Executor executor = StackUtils.getBlockingWorkExecutor();

	/** Container for an error */
	ServiceResultException error;
	/** Container for a result */
	T result;
	/** Listener */
	ResultListener<T> listener;
	/** Semaphore that is released once there is a result or error */
	Semaphore s = new Semaphore(0);

	/**
	 * <p>Constructor for AsyncResultImpl.</p>
	 */
	public AsyncResultImpl() {
	}

	/** {@inheritDoc} */
	@Override
	public ServiceResultException getError() {
		return error;
	}

	/** {@inheritDoc} */
	@Override
	public T getResult() {
		return result;
	}

	/** {@inheritDoc} */
	@Override
	public AsyncResultStatus getStatus() {
		if (error!=null)
			return AsyncResultStatus.Failed;
		if (result!=null)
			return AsyncResultStatus.Succeed;
		return AsyncResultStatus.Waiting;
	}

	/**
	 * Set error, invokes any listener in executor thread.
	 *
	 * If result or error has already been set, this method does nothing.
	 *
	 * @param error a {@link org.opcfoundation.ua.common.ServiceResultException} object.
	 */
	public void setError(final ServiceResultException error)
	{
		synchronized(this) {
			if (isFinished()) return;
			this.error = error;
			final ResultListener<T> l = listener;
			if (l != null) {
				executor.execute(new Runnable() {
					@Override
					public void run() {
						try {
							l.onError(error);
						} catch (RuntimeException rte) {
							listenerLogger.error("Unexpected RuntimeException in ResultListener#onError(", rte);
						} finally {
							finish();
						}
					}});
			}
		}
		s.release(Integer.MAX_VALUE);
	}

	/**
	 * Set error, invokes any listener here and now.
	 * Note the listener may throw unexpected {@link RuntimeException}
	 *
	 * @param error a {@link org.opcfoundation.ua.common.ServiceResultException} object.
	 * @throws java.lang.RuntimeException if any.
	 */
	public void setErrorSync(final ServiceResultException error)
			throws RuntimeException
	{
		synchronized(this) {
			if (isFinished()) return;
			this.error = error;
			final ResultListener<T> l = listener;
			if (l != null) {
				try {
					l.onError(error);
				} catch (RuntimeException rte) {
					listenerLogger.error("Unexpected RuntimeException in ResultListener#onError(", rte);
				} finally {
					finish();
				}

			}
		}
		s.release(Integer.MAX_VALUE);
	}

	/** {@inheritDoc} */
	@Override
	public void setListener(ResultListener<T> listener) {
		ServiceResultException _error;
		T _result;
		synchronized(this) {
			this.listener = listener;
			_error = error;
			_result = result;
		}
		if (listener!=null) {
			if (_result!=null) listener.onCompleted(_result);
			if (_error!=null) listener.onError(_error);
		}
	}

	/**
	 * Set error, invokes any listener in excutor thread
	 *
	 * If result or error has already been set, this method does nothing.
	 *
	 * @param result a T object.
	 */
	public void setResult(T result) {
		synchronized(this) {
			if (isFinished()) return;
			this.result = result;
			final ResultListener<T> l = listener;
			if (l != null) {
				executor.execute(new Runnable() {
					@Override
					public void run() {
						try {
							l.onCompleted(AsyncResultImpl.this.result);
						} catch (RuntimeException rte) {
							listenerLogger.error("Unexpected RuntimeException in ResultListener#onCompleted", rte);
						} finally {
							finish();
						}
					}});
			}
		}
		s.release(Integer.MAX_VALUE);
	}

	/**
	 * <p>setResultSync.</p>
	 *
	 * @param result a T object.
	 * @throws java.lang.RuntimeException if any.
	 */
	public void setResultSync(T result)
			throws RuntimeException
	{
		synchronized(this) {
			if (isFinished()) return;
			this.result = result;
			final ResultListener<T> l = listener;
			if (l != null) {
				try {
					l.onCompleted(result);
				} catch (RuntimeException rte) {
					listenerLogger.error("Unexpected RuntimeException in ResultListener#onCompleted(", rte);
				} finally {
					finish();
				}
			}
		}
		s.release(Integer.MAX_VALUE);
	}

	/**
	 * Links another result as a source of this result object
	 *
	 * @param source a {@link org.opcfoundation.ua.transport.AsyncResult} object.
	 */
	public void setSource(AsyncResult<T> source)
	{
		source.setListener(new ResultListener<T>() {
			@Override
			public void onCompleted(T result) {
				setResult(result);
			}
			@Override
			public void onError(ServiceResultException error) {
				setError(error);
			}
		});
	}

	/** {@inheritDoc} */
	@Override
	public T waitForResult()
			throws ServiceResultException
	{
		try {
			s.acquire();
		} catch (InterruptedException e) {
			logger.debug("timeout: ", error);
			throw new ServiceResultException(StatusCodes.Bad_Timeout, e);
		}
		if (result!=null) return result;
		if (error!=null) {
			logger.debug("error: ", error);
			// The error thread has originated from TcpConnection / Read - Switch the thread in the stack trace to the current thread
			if (error instanceof ServiceFaultException)
				throw new ServiceFaultException(((ServiceFaultException) error).getServiceFault());
			else
				throw error;
		}
		throw new ServiceResultException(StatusCodes.Bad_UnexpectedError);
	}

	/** {@inheritDoc} */
	@Override
	public T waitForResult(long timeout, TimeUnit unit)
			throws ServiceResultException {
		try {
			s.tryAcquire(timeout, unit);
		} catch (InterruptedException e) {
			throw new ServiceResultException(StatusCodes.Bad_Timeout, e);
		}
		//		logger.debug("result:"+ result);
		if (result!=null) return result;
		logger.debug("error:", error);
		if (error!=null) {
			// The error thread has originated from TcpConnection / Read - Switch the thread in the stack trace to the current thread
			if (error instanceof ServiceFaultException)
				throw new ServiceFaultException(((ServiceFaultException) error).getServiceFault());
			else
				throw error;
		}
		throw new ServiceResultException(StatusCodes.Bad_Timeout);
	}

	private void finish() {
		//		logger.debug("finish");
		setListener(null);
	}

	/**
	 * @return
	 */
	private boolean isFinished() {
		return this.result!=null || this.error!=null;
	}

}
