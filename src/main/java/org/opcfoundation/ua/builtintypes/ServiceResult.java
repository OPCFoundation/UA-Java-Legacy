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

package org.opcfoundation.ua.builtintypes;

import java.util.Arrays;
import java.util.Locale;

import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.StatusCodes;



/**
 * <p>ServiceResult class.</p>
 *
 */
public class ServiceResult {

	/**
	 * Create service result with stack trace from an exception.
	 * If exception is ServiceResultException, use its reported status code,
	 * if not the error code will be set to Bad_UnexpectedError.
	 *
	 * @param t throwable
	 * @return service result
	 */
	public static ServiceResult toServiceResult(Throwable t)
	{
		ServiceResult res = new ServiceResult();
    	res.setCode(t instanceof ServiceResultException ? ((ServiceResultException)t).getStatusCode() : new StatusCode(StatusCodes.Bad_UnexpectedError));
		res.setSymbolicId( res.toString() );
    	res.setLocalizedText(new LocalizedText(t.getMessage(), ""));
    	res.setAdditionalInfo(Arrays.toString(t.getStackTrace()));		
    	return res;
	}
	
	private StatusCode code;
	private String symbolicId;
	private String namespaceUri;
	private LocalizedText localizedText;
	private String additionalInfo;
	private ServiceResult innerResult;

	/**
	 * <p>Constructor for ServiceResult.</p>
	 */
	public ServiceResult() {
		initialize();
	}

	//TODO added by Mikko
	/**
	 * <p>Constructor for ServiceResult.</p>
	 *
	 * @param code a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
	 */
	public ServiceResult(StatusCode code) {
		initialize(code);
	}
	//TODO ADDED BY MIKKO
	/**
	 * <p>Constructor for ServiceResult.</p>
	 *
	 * @param code a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
	 * @param e a {@link java.lang.Throwable} object.
	 */
	public ServiceResult(StatusCode code, Throwable e) {
		initialize(code, e);
	}

	//TODO ADDED BY MIKKO
	/**
	 * <p>Constructor for ServiceResult.</p>
	 *
	 * @param code a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 * @param e a {@link java.lang.Throwable} object.
	 */
	public ServiceResult(UnsignedInteger code, Throwable e) {
		initialize(new StatusCode(code), e);
	}
	/**
	 * <p>Constructor for ServiceResult.</p>
	 *
	 * @param code a {@link org.opcfoundation.ua.builtintypes.UnsignedInteger} object.
	 */
	public ServiceResult(UnsignedInteger code) {
		initialize(new StatusCode(code));
	}
	/**
	 * <p>isBad.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isBad() {
		if (code == null) return false;
		return code.isBad();
	}
	
	
	//TODO ADDED BY MIKKO
	private String lookUpSymbolicId(StatusCode code) {
		return code.getName(); 
	}
	
	//TODO ADDED BY MIKKO
	/**
	 * <p>buildExceptionTrace.</p>
	 *
	 * @param e a {@link java.lang.Throwable} object.
	 * @return a {@link java.lang.String} object.
	 */
	public static String buildExceptionTrace(Throwable e) {
		if (e == null)
			return null;
		else
			return e.getStackTrace().toString();
	}
	
	
	private void initialize() {
		//TODO SCHSANGE this to StatusCode.Good
		initialize(StatusCode.GOOD, null);
	}

	private void initialize(StatusCode code) {
		this.code = code;
		symbolicId = lookUpSymbolicId(code);
		localizedText = null;
		additionalInfo = null;
	}

	private void initialize(StatusCode code, Throwable e) {
		assert(e != null);
		this.code = code;
		symbolicId = lookUpSymbolicId(this.code);
		localizedText = new LocalizedText(e.getMessage(), Locale.ENGLISH);
		additionalInfo = buildExceptionTrace(e);
	}

	/**
	 * <p>isGood.</p>
	 *
	 * @return a boolean.
	 */
	public boolean isGood() {
		if (code == null) return false;
		return code.isGood();
	}

	/**
	 * <p>Getter for the field <code>additionalInfo</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getAdditionalInfo() {
		return additionalInfo;
	}

	/**
	 * <p>Setter for the field <code>additionalInfo</code>.</p>
	 *
	 * @param additionalInfo a {@link java.lang.String} object.
	 */
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	/**
	 * <p>Getter for the field <code>code</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
	 */
	public StatusCode getCode() {
		return code;
	}

	/**
	 * <p>Setter for the field <code>code</code>.</p>
	 *
	 * @param code a {@link org.opcfoundation.ua.builtintypes.StatusCode} object.
	 */
	public void setCode(StatusCode code) {
		this.code = code;
	}

	/**
	 * <p>Getter for the field <code>innerResult</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.ServiceResult} object.
	 */
	public ServiceResult getInnerResult() {
		return innerResult;
	}

	/**
	 * <p>Setter for the field <code>innerResult</code>.</p>
	 *
	 * @param innerResult a {@link org.opcfoundation.ua.builtintypes.ServiceResult} object.
	 */
	public void setInnerResult(ServiceResult innerResult) {
		this.innerResult = innerResult;
	}

	/**
	 * <p>Getter for the field <code>localizedText</code>.</p>
	 *
	 * @return a {@link org.opcfoundation.ua.builtintypes.LocalizedText} object.
	 */
	public LocalizedText getLocalizedText() {
		return localizedText;
	}

	/**
	 * <p>Setter for the field <code>localizedText</code>.</p>
	 *
	 * @param localizedText a {@link org.opcfoundation.ua.builtintypes.LocalizedText} object.
	 */
	public void setLocalizedText(LocalizedText localizedText) {
		this.localizedText = localizedText;
	}

	/**
	 * <p>Getter for the field <code>namespaceUri</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getNamespaceUri() {
		return namespaceUri;
	}

	/**
	 * <p>Setter for the field <code>namespaceUri</code>.</p>
	 *
	 * @param namespaceUri a {@link java.lang.String} object.
	 */
	public void setNamespaceUri(String namespaceUri) {
		this.namespaceUri = namespaceUri;
	}

	/**
	 * <p>Getter for the field <code>symbolicId</code>.</p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	public String getSymbolicId() {
		return symbolicId;
	}

	/**
	 * <p>Setter for the field <code>symbolicId</code>.</p>
	 *
	 * @param symbolicId a {@link java.lang.String} object.
	 */
	public void setSymbolicId(String symbolicId) {
		this.symbolicId = symbolicId;
	}


}
