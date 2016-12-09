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

/**
 * <p>State class.</p>
 *
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public class State<StateType> extends AbstractState<StateType, RuntimeException> {

	/**
	 * <p>Constructor for State.</p>
	 *
	 * @param initialState a StateType object.
	 */
	public State(StateType initialState) {
		super(initialState);
	}
	
	/**
	 * <p>setState.</p>
	 *
	 * @param state a StateType object.
	 * @return a boolean.
	 */
	public boolean setState(StateType state) {
		return super.setState(state);
	}
	
	/** {@inheritDoc} */
	public StateType setState(StateType state, java.util.concurrent.Executor listenerExecutor, java.util.Set<StateType> prerequisiteStates) {
		return super.setState(state, listenerExecutor, prerequisiteStates);
	};
	
	/**
	 * <p>setError.</p>
	 *
	 * @param error a {@link java.lang.RuntimeException} object.
	 */
	public void setError(RuntimeException error) {
		super.setError(error);
	}
	
	/**
	 * <p>attemptSetState.</p>
	 *
	 * @param prerequisiteState a {@link java.util.Set} object.
	 * @param newState a StateType object.
	 * @return a StateType object.
	 */
	public StateType attemptSetState(java.util.Set<StateType> prerequisiteState, StateType newState) {
		return super.attemptSetState(prerequisiteState, newState);
	}
	
	/** {@inheritDoc} */
	@Override
	public void assertNoError() throws RuntimeException {
		super.assertNoError();
	}
	
	/** {@inheritDoc} */
	@Override
	protected void clearError() {
		super.clearError();
	}

}
