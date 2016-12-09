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

package org.opcfoundation.ua.utils;

import java.io.EOFException;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.net.ConnectException;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.Selector;
import java.util.Random;
import java.util.Timer;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.EncodeableSerializer;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.encoding.binary.EncodeableReflectionSerializer;
import org.opcfoundation.ua.encoding.binary.IEncodeableSerializer;
import org.opcfoundation.ua.encoding.utils.EncodeableDesc;
import org.opcfoundation.ua.encoding.utils.EncodeableDescTable;
import org.opcfoundation.ua.encoding.utils.SerializerComposition;
import org.opcfoundation.ua.transport.AsyncResult;
import org.opcfoundation.ua.transport.AsyncResult.AsyncResultStatus;
import org.opcfoundation.ua.transport.ResultListener;
import org.opcfoundation.ua.transport.tcp.impl.Acknowledge;
import org.opcfoundation.ua.transport.tcp.impl.ErrorMessage;
import org.opcfoundation.ua.transport.tcp.impl.Hello;
import org.opcfoundation.ua.utils.asyncsocket.AsyncSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility methods for the OPC UA Java Stack.
 */
public class StackUtils {
	/**
	 * The default thread factory
	 */
	static class NamedThreadFactory implements ThreadFactory {
		static final AtomicInteger poolNumber = new AtomicInteger(1);
		final ThreadGroup group;
		final AtomicInteger threadNumber = new AtomicInteger(1);
		final String namePrefix;

		NamedThreadFactory(String name) {
			SecurityManager s = System.getSecurityManager();
			group = (s != null)? s.getThreadGroup() :
				Thread.currentThread().getThreadGroup();
			namePrefix = name+"-pool-" +
					poolNumber.getAndIncrement() +
					"-thread-";
		}

		@Override
		public Thread newThread(Runnable r) {
			Thread t = new Thread(group, r,
					namePrefix + threadNumber.getAndIncrement(),
					0);
			if (t.isDaemon())
				t.setDaemon(false);
			if (t.getPriority() != Thread.NORM_PRIORITY)
				t.setPriority(Thread.NORM_PRIORITY);
			t.setUncaughtExceptionHandler(uncaughtExceptionHandler);
			return t;
		}
	}

	private static Logger logger = LoggerFactory.getLogger(StackUtils.class);

	private static int blockingWorkerThreadPoolCoreSize = 64;
	private static long blockingWorkerThreadPoolTimeout = 3L;

	private static final int CORES = Runtime.getRuntime().availableProcessors();
	/**
	 * Use {@link #getNonBlockingWorkExecutor()} instead
	 */
	private static Executor NON_BLOCKING_EXECUTOR;

	/**
	 * Use {@link #getBlockingWorkExecutor()} instead
	 */
	private static Executor BLOCKING_EXECUTOR;

	private static ExecutorService rejectionExecutor;

	/**
	 * Use #getSelector() instead.
	 */
	private static AsyncSelector SELECTOR;

	/** Constant <code>RANDOM</code> */
	public static Random RANDOM = new Random();

	/** Requested token lifetime */
	public static final UnsignedInteger CLIENT_TOKEN_LIFETIME_REQUEST = new UnsignedInteger(600000);
	/** Maximum lifetime server is willing to offer */
	public static final UnsignedInteger SERVER_GIVEN_TOKEN_LIFETIME = UnsignedInteger.getFromBits(600*1000);
	/** Constant <code>TCP_PROTOCOL_VERSION=0</code> */
	public static final int TCP_PROTOCOL_VERSION = 0;

	private static IEncodeableSerializer DEFAULT_SERIALIZER;

	private static volatile UncaughtExceptionHandler uncaughtExceptionHandler = new UncaughtExceptionHandler() {


		@Override
		public void uncaughtException(Thread t, Throwable e) {
			logger.error("Uncaught Exception in Thread " + t, e);
			e.printStackTrace(); // In case the logger is not enabled
		}
	};
	/**
	 * Wait for a group to requests to go into final state
	 *
	 * @param requests a group of requests
	 * @param timeout timeout in seconds
	 * @return true if completed ok
	 * @throws java.lang.InterruptedException if any.
	 */
	public static boolean barrierWait(AsyncResult<?>[] requests, long timeout)
			throws InterruptedException
	{
		final Semaphore sem = new Semaphore(0);

		ResultListener l = new ResultListener() {
			@Override
			public void onCompleted(Object result) {
				sem.release();
			}

			@Override
			public void onError(ServiceResultException error) {
				sem.release();
			}};

			for (AsyncResult<?> r : requests) {
				synchronized(r) {
					if (r.getStatus() != AsyncResultStatus.Waiting)
						sem.release();
					else
						r.setListener(l);
				}
			}

			return sem.tryAcquire(requests.length, timeout, TimeUnit.SECONDS);
	}

	/**
	 * <p>cores.</p>
	 *
	 * @return a int.
	 */
	public static int cores() {
		return CORES;
	}
	/**
	 * Get Executor for long term and potentially blocking operations.
	 *
	 * @param maxThreadPoolSize max number of threads to create in the thread pool
	 * @return executor for blocking operations
	 * @param name a {@link java.lang.String} object.
	 */
	public static synchronized Executor createBlockingWorkExecutor(String name, int maxThreadPoolSize) {
		if (BLOCKING_EXECUTOR == null) {
			final ThreadGroup tg = new ThreadGroup(name);
			final AtomicInteger counter = new AtomicInteger(0);
			ThreadFactory tf = new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(tg, r, "Blocking-Work-Executor-"+(counter.incrementAndGet()));
					t.setDaemon(true);
					t.setUncaughtExceptionHandler(uncaughtExceptionHandler);
					return t;
				}};
				//SynchronousQueue queue = new SynchronousQueue<Runnable>();
				BlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>( Integer.MAX_VALUE );
				BLOCKING_EXECUTOR =
						new ThreadPoolExecutor(
								blockingWorkerThreadPoolCoreSize,
								maxThreadPoolSize,
								blockingWorkerThreadPoolTimeout,
								TimeUnit.SECONDS,
								queue,
								tf);

				((ThreadPoolExecutor) BLOCKING_EXECUTOR).setRejectedExecutionHandler( new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						/*
						 *  Technically this should happen only in shutdown because we are using Integer.MAX_VALUE bound queue. It should be enough, so this is more of a fail-safe.
						 */
						getRejectionExecutor().execute(r);
					}});
		}
		return BLOCKING_EXECUTOR;
	}

	/**
	 * <p>Getter for the field <code>blockingWorkerThreadPoolCoreSize</code>.</p>
	 *
	 * @return the core size of the thread pool for BlockingWorkerExecutor.
	 */
	public static int getBlockingWorkerThreadPoolCoreSize() {
		return blockingWorkerThreadPoolCoreSize;
	}

	/**
	 * <p>Getter for the field <code>blockingWorkerThreadPoolTimeout</code>.</p>
	 *
	 * @return the timeout in seconds of the thread pool for BlockingWorkerExecutor.
	 */
	public static long getBlockingWorkerThreadPoolTimeout() {
		return blockingWorkerThreadPoolTimeout;
	}

	/**
	 * Get Executor for long term and potentially blocking operations.
	 * <p>
	 * Calls getBlockingWorkExecutor(getDefaultMaxBlockingWorkerThreadPoolSize())
	 *
	 * @return executor for blocking operations
	 */
	public static Executor getBlockingWorkExecutor() {
		return createBlockingWorkExecutor( "Blocking-Work-Executor", 256 );
	}
	/**
	 * Get default encodeable serializer
	 *
	 * @return encodeable serialier
	 */
	public synchronized static IEncodeableSerializer getDefaultSerializer()
	{
		if (DEFAULT_SERIALIZER==null) {
			SerializerComposition serializer = EncodeableSerializer.getInstance();

			// Add acknowledge/hello/errormessage to the selected serializer
			EncodeableDescTable reflectionTable = new EncodeableDescTable();
			reflectionTable.addStructureInfo( EncodeableDesc.readFromClass(Acknowledge.class, Acknowledge.getFields()) );
			reflectionTable.addStructureInfo( EncodeableDesc.readFromClass(Hello.class, Hello.getFields()) );
			reflectionTable.addStructureInfo( EncodeableDesc.readFromClass(ErrorMessage.class, ErrorMessage.getFields()) );
			EncodeableReflectionSerializer e = new EncodeableReflectionSerializer(reflectionTable);

			serializer.addSerializer(e);

			DEFAULT_SERIALIZER = serializer;
		}
		return DEFAULT_SERIALIZER;
	}
	/**
	 * Get Executor for non-blocking operations.
	 * This executor has one thread for one core in the system.
	 *
	 * @return Executor that executes non-blocking short term operations.
	 */
	public static synchronized Executor getNonBlockingWorkExecutor() {
		if (NON_BLOCKING_EXECUTOR == null) {
			final ThreadGroup tg = new ThreadGroup("Non-Blocking-Work-Executor-Group");
			final AtomicInteger counter = new AtomicInteger(0);
			ThreadFactory tf = new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(tg, r, "Non-Blocking-Work-Executor-"+(counter.incrementAndGet()));
					t.setDaemon(true);
					t.setUncaughtExceptionHandler(uncaughtExceptionHandler);
					return t;
				}};
				BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>( Integer.MAX_VALUE );

				RejectedExecutionHandler rejectHandler = new RejectedExecutionHandler() {
					@Override
					public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
						getBlockingWorkExecutor().execute(r);
					}
				};

				NON_BLOCKING_EXECUTOR = //new ScheduledThreadPoolExecutor( CORES );
						new ThreadPoolExecutor(
								CORES,
								CORES,
								3L, TimeUnit.SECONDS,
								workQueue,
								tf,
								rejectHandler);
		}
		return NON_BLOCKING_EXECUTOR;
	}

	/**
	 * Get Executor that handles tasks that are rejected by blocking work executor
	 *
	 * @return the executor
	 */
	public static synchronized Executor getRejectionExecutor(){
		if(rejectionExecutor == null){
			ThreadFactory tf = new ThreadFactory() {
				@Override
				public Thread newThread(Runnable r) {
					Thread t = new Thread(r, "RejectedRunnableHanlder");
					t.setDaemon(true);
					t.setUncaughtExceptionHandler(uncaughtExceptionHandler);
					return t;
				}};

				rejectionExecutor = Executors.newSingleThreadExecutor(tf);
		}
		return rejectionExecutor;
	}

	/**
	 * <p>getSelector.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.utils.asyncsocket.AsyncSelector} object.
	 */
	public static AsyncSelector getSelector() {
		if (SELECTOR == null)
			try {
				SELECTOR = new AsyncSelector(Selector.open());
			} catch (IOException e) {
				throw new Error(e);
			}
		return SELECTOR;
	}

	/**
	 * The handler that is called, if any of the worker threads encounter an exception that is not handled.
	 *
	 * @return the uncaughtexceptionhandler
	 */
	public static UncaughtExceptionHandler getUncaughtExceptionHandler() {
		return uncaughtExceptionHandler;
	}

	/**
	 * <p>logStatus.</p>
	 */
	public static void logStatus() {
		logExecutor("BLOCKING_EXECUTOR", (ThreadPoolExecutor)BLOCKING_EXECUTOR);
		logExecutor("NON_BLOCKING_EXECUTOR", (ThreadPoolExecutor)NON_BLOCKING_EXECUTOR);
	}
	/**
	 * <p>newNamedThreadFactory.</p>
	 *
	 * @param name a {@link java.lang.String} object.
	 * @return a {@link java.util.concurrent.ThreadFactory} object.
	 */
	public static ThreadFactory newNamedThreadFactory(String name) {
		return new NamedThreadFactory(name);
	}


	/**
	 * <p>Setter for the field <code>blockingWorkerThreadPoolCoreSize</code>.</p>
	 *
	 * @param blockingWorkerThreadPoolCoreSize a int.
	 */
	public static void setBlockingWorkerThreadPoolCoreSize(int blockingWorkerThreadPoolCoreSize) {
		StackUtils.blockingWorkerThreadPoolCoreSize = blockingWorkerThreadPoolCoreSize;
	}

	/**
	 * Define the timeout (in seconds) of the thread pool for BlockingWorkerExecutor.
	 * <p>
	 * Default: 3
	 *
	 * @param blockingWorkerThreadPoolTimeout a long.
	 */
	public static void setBlockingWorkerThreadPoolTimeout(
			long blockingWorkerThreadPoolTimeout) {
		StackUtils.blockingWorkerThreadPoolTimeout = blockingWorkerThreadPoolTimeout;
	}

	/**
	 * Define the handler that is called, if any of the worker threads encounter an exception that is not handled.
	 * <p>
	 * The default handler will just log the exception as an Error to the log4j log.
	 * <p>
	 * Set the handler to provide custom behavior in your application.
	 * <p>
	 * The handler is set when new worker threads are started and setting the value will not affect already running threads.
	 *
	 * @param uncaughtexceptionhandler the uncaughtexceptionhandler to set
	 */
	public static void setUncaughtExceptionHandler(
			UncaughtExceptionHandler uncaughtexceptionhandler) {
		uncaughtExceptionHandler = uncaughtexceptionhandler;
	}

	/**
	 * Perform a "context shutdown" to clean up the Stack resources. Necessary for web service modules, etc.
	 * <p>
	 * Shuts down {@link #BLOCKING_EXECUTOR} and {@link #NON_BLOCKING_EXECUTOR}, closes {@link #SELECTOR}
	 * and cancels {@link TimerUtil#getTimer()}.
	 */
	public static void shutdown() {
		if (BLOCKING_EXECUTOR != null) {
			((ThreadPoolExecutor)BLOCKING_EXECUTOR).shutdown();
			BLOCKING_EXECUTOR = null;
		}
		if (NON_BLOCKING_EXECUTOR != null) {
			((ThreadPoolExecutor)NON_BLOCKING_EXECUTOR).shutdown();
			NON_BLOCKING_EXECUTOR = null;
		}

		if(rejectionExecutor != null){
			rejectionExecutor.shutdown();
			rejectionExecutor = null;
		}

		if (SELECTOR != null)
			try {
				SELECTOR.close();
				SELECTOR = null;
			} catch (IOException e) {
				logger.debug("SELECTOR.close failed", e);
			}
		Timer timer = TimerUtil.timer.get();
		if (timer != null) {
			timer.cancel();
			TimerUtil.timer = null;
		}
	}


	/**
	 * <p>toServiceResultException.</p>
	 *
	 * @param e a {@link java.lang.Exception} object.
	 * @return a {@link org.opcfoundation.ua.common.ServiceResultException} object.
	 */
	public static ServiceResultException toServiceResultException(Exception e)
	{
		if (e instanceof ServiceResultException)
			return (ServiceResultException) e;
		if (e instanceof ClosedChannelException)
			return new ServiceResultException(StatusCodes.Bad_ConnectionClosed, e);
		if (e instanceof EOFException)
			return new ServiceResultException(StatusCodes.Bad_ConnectionClosed, e, "Connection closed (graceful)");
		if (e instanceof ConnectException)
			return new ServiceResultException(StatusCodes.Bad_ConnectionRejected, e);
		if (e instanceof SocketException)
			return new ServiceResultException(StatusCodes.Bad_ConnectionClosed, e, "Connection closed (unexpected)");
		if (e instanceof IOException)
			return new ServiceResultException(StatusCodes.Bad_ConnectionClosed, e);
		return new ServiceResultException(e);
	}

	private static void logExecutor(String name, final ThreadPoolExecutor e) {
		logger.debug("{}: ActiveCount={} CompletedTaskCount={} PoolSize={} LargestPoolSize={} TaskCount={}", name, e.getActiveCount(), e.getCompletedTaskCount(), e.getPoolSize(), e.getLargestPoolSize(), e.getTaskCount());
	}

}
