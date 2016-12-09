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

import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This is a default implementation to {@link IStatefulObject}.
 * This class can be subclassed or used as it.
 * The state type is parametrized (typically an enumeration).
 *
 * TODO Remove locks - use spin set and test
 *
 * @see IStatefulObject
 * @see StateListener Listener for state modifications
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public abstract class AbstractState<StateType, ErrorType extends Throwable> implements IStatefulObject<StateType, ErrorType> {

	/** Current state */
	private StateType state = null;
	/** Optional error state */
	private StateType errorState = null;
	/** Error cause */
	private ErrorType errorCause;

	// Optimization for 1 listener, ListenerList is heavy //
	// TODO Replace with array that provides snapshots with-out instantiating new object
	private SnapshotArray<StateListener<StateType>> listenerList = null;
	private SnapshotArray<StateListener<StateType>> notifiableList = null;
	private Object lock = new Object();

	/**
	 * <p>Constructor for AbstractState.</p>
	 *
	 * @param initialState a StateType object.
	 */
	public AbstractState(StateType initialState)
	{
		state = initialState;
	}

	/**
	 * Creates a state with a error state. The state object goes to errorState on setError().
	 *
	 * @param initialState a StateType object.
	 * @param errorState a StateType object.
	 */
	public AbstractState(StateType initialState, StateType errorState)
	{
		state = initialState;
		this.errorState = errorState;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Add on-event listener.
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public synchronized void addStateListener(StateListener<StateType> listener) {
		if (listener==null)  throw new IllegalArgumentException("null arg");
		if (listenerList==null) listenerList = new SnapshotArray(StateListener.class);
		listenerList.add(listener);
		return;
	}

	/**
	 * {@inheritDoc}
	 *
	 * Add post-event notification listener. The prosessing thread is random.
	 * The prosessing order is not guaranteed if the handling is not synchronized.
	 */
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public synchronized void addStateNotifiable(StateListener<StateType> listener)
	{
		if (listener==null)  throw new IllegalArgumentException("null arg");
		if (notifiableList==null) notifiableList = new SnapshotArray(StateListener.class);
		notifiableList.add(listener);
	}

	/** {@inheritDoc} */
	@Override
	public ErrorType getError()
	{
		return errorCause;
	}

	/** {@inheritDoc} */
	@Override
	public synchronized StateType getState() {
		return state;
	}

	/**
	 * <p>hasError.</p>
	 *
	 * @return a boolean.
	 */
	public boolean hasError()
	{
		return errorCause!=null;
	}

	/** {@inheritDoc} */
	@Override
	public synchronized void removeStateListener(StateListener<StateType> listener) {
		if (listener==null) throw new IllegalArgumentException("null arg");
		if (listenerList==null) return;
		listenerList.remove(listener);
		if (listenerList.isEmpty()) listenerList = null;
		return;
	}

	/** {@inheritDoc} */
	@Override
	public synchronized void removeStateNotifiable(StateListener<StateType> listener)
	{
		if (notifiableList!=null) notifiableList.remove(listener);
		if (notifiableList.isEmpty()) notifiableList = null;
	}

	/** {@inheritDoc} */
	@Override
	public StateType waitForState(Set<StateType> set)
			throws InterruptedException, ErrorType
	{
		// This impl makes unnecessary wakeups but is memory conservative
		synchronized(lock) {
			while (!set.contains(state))
				lock.wait();
			ErrorType e = getError();
			if (e!=null)
				throw e;
			return state;
		}
	}

	/** {@inheritDoc} */
	@Override
	public StateType waitForState(
			Set<StateType> set,
			long timeout,
			TimeUnit unit)
					throws InterruptedException, TimeoutException, ErrorType {
		long abortTime = System.currentTimeMillis() + unit.toMillis(timeout);
		synchronized(lock) {
			while (!set.contains(state)) {
				long waitTime = System.currentTimeMillis() - abortTime;
				if (waitTime<0)
					throw new TimeoutException("timeout");
				lock.wait(waitTime);
				ErrorType e = getError();
				if (e!=null)
					throw e;
			}
			return state;
		}
	}

	/** {@inheritDoc} */
	@Override
	public StateType waitForStateUninterruptibly(Set<StateType> set)
			throws ErrorType
	{
		// This impl makes unnecessary wakeups but is memory conservative
		synchronized(lock) {
			while (!set.contains(state))
				try {
					lock.wait();
				} catch (InterruptedException qwer) {}
			ErrorType e = getError();
			if (e!=null)
				throw e;
			return state;
		}
	}

	/**
	 * <p>assertNoError.</p>
	 *
	 * @throws ErrorType if any.
	 */
	protected void assertNoError()
			throws ErrorType
	{
		ErrorType e = errorCause;
		if (e!=null)
			throw e;
	}

	/**
	 * Attempts to change the state. The state will be changed only if current
	 * state is one of the expected states.
	 *
	 * @param prerequisiteState expected current state
	 * @param newState a StateType object.
	 * @return state after attempt
	 */
	protected StateType attemptSetState(Set<StateType> prerequisiteState, StateType newState)
	{
		if (prerequisiteState==null || newState==null)
			throw new IllegalArgumentException("null arg");
		return setState(newState, null, prerequisiteState);
	}

	/**
	 * <p>clearError.</p>
	 */
	protected void clearError()
	{
		errorCause = null;
	}

	/**
	 * Checks whether state transition is allowed.
	 * Override this
	 *
	 * @param oldState a StateType object.
	 * @param newState a StateType object.
	 * @return true if state transition is allowed
	 */
	protected boolean isStateTransitionAllowed(StateType oldState, StateType newState)
	{
		return true;
	}

	/**
	 * Override this.
	 *
	 * @param rte a {@link java.lang.RuntimeException} object.
	 */
	protected void onListenerException(RuntimeException rte)
	{
		rte.printStackTrace();
	}

	/**
	 * Override this.
	 *
	 * @param oldState a StateType object.
	 * @param newState a StateType object.
	 */
	protected void onStateTransition(StateType oldState, StateType newState)
	{
	}

	/**
	 * <p>setError.</p>
	 *
	 * @param error a ErrorType object.
	 */
	protected void setError(ErrorType error)
	{
		this.errorCause = error;
		if (errorState==null || !setState(errorState))
		{
			// wake up sleepers
			synchronized(lock)
			{
				lock.notifyAll();
			}
		}
	}

	/**
	 * <p>Setter for the field <code>state</code>.</p>
	 *
	 * @param state a StateType object.
	 * @return a boolean.
	 */
	protected boolean setState(StateType state)
	{
		return setState(state, null, null) == state;
	}

	/**
	 * Set state
	 *
	 * @param state a StateType object.
	 * @param listenerExecutor executor for post listener handling or null for immediate
	 * @param prerequisiteStates old state prerequisite or null
	 * @return state after attempt
	 */
	protected StateType setState(StateType state, Executor listenerExecutor, Set<StateType> prerequisiteStates)
	{
		if (listenerExecutor!=null && listenerExecutor instanceof CurrentThreadExecutor) listenerExecutor = null;
		StateListener<StateType>[] listeners = null;
		StateListener<StateType>[] notifiables = null;
		StateType oldState = null;
		StateType newState = null;
		synchronized (this) {
			oldState = this.state;
			newState = state;
			if (oldState==newState) return state;
			if (prerequisiteStates!=null && !prerequisiteStates.contains(this.state))
				return state;
			if (!isStateTransitionAllowed(oldState, newState))
				return state;

			this.state = newState;
			if (listenerList!=null) listeners = listenerList.getArray();
			if (notifiableList!=null) notifiables = notifiableList.getArray();
		}
		synchronized(lock)
		{
			lock.notifyAll();
		}
		// Threads wake up here...

		// Handle listeners
		onStateTransition(oldState, newState);

		final StateType os = oldState;
		final StateType ns = newState;
		if (listeners!=null && listenerExecutor==null) {
			for (final StateListener<StateType> sl : listeners) {
				try {
					sl.onStateTransition(AbstractState.this, os, ns);
				} catch (RuntimeException e) {
					onListenerException(e);
				}
			}
		}

		if ( (listeners!=null && listenerExecutor!=null) ) {
			for (final StateListener<StateType> sl : listeners) {
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						try {
							sl.onStateTransition(AbstractState.this, os, ns);
						} catch (RuntimeException e) {
							onListenerException(e);
						}
					}};
					listenerExecutor.execute(runnable);
			}
		}

		if ( notifiables!=null ) {
			for (final StateListener<StateType> sl : notifiables) {
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						try {
							sl.onStateTransition(AbstractState.this, os, ns);
						} catch (RuntimeException e) {
							onListenerException(e);
						}
					}};
					StackUtils.getBlockingWorkExecutor().execute(runnable);
			}
		}

		return state;
	}

}
