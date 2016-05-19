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



public class ServiceResult {

	/**
	 * Create service result with stack trace from an exception.
	 * If exception is ServiceResultException, use its reported status code,    
	 * if not the error code will be set to Bad_UnexpectedError. 
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

	public ServiceResult() {
		initialize();
	}

	//TODO added by Mikko
	public ServiceResult(StatusCode code) {
		initialize(code);
	}
	//TODO ADDED BY MIKKO
	public ServiceResult(StatusCode code, Throwable e) {
		initialize(code, e);
	}

	//TODO ADDED BY MIKKO
	public ServiceResult(UnsignedInteger code, Throwable e) {
		initialize(new StatusCode(code), e);
	}
	public ServiceResult(UnsignedInteger code) {
		initialize(new StatusCode(code));
	}
	public boolean isBad() {
		if (code == null) return false;
		return code.isBad();
	}
	
	
	//TODO ADDED BY MIKKO
	private String lookUpSymbolicId(StatusCode code) {
		return code.getName(); 
	}
	
	//TODO ADDED BY MIKKO
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

	public boolean isGood() {
		if (code == null) return false;
		return code.isGood();
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public StatusCode getCode() {
		return code;
	}

	public void setCode(StatusCode code) {
		this.code = code;
	}

	public ServiceResult getInnerResult() {
		return innerResult;
	}

	public void setInnerResult(ServiceResult innerResult) {
		this.innerResult = innerResult;
	}

	public LocalizedText getLocalizedText() {
		return localizedText;
	}

	public void setLocalizedText(LocalizedText localizedText) {
		this.localizedText = localizedText;
	}

	public String getNamespaceUri() {
		return namespaceUri;
	}

	public void setNamespaceUri(String namespaceUri) {
		this.namespaceUri = namespaceUri;
	}

	public String getSymbolicId() {
		return symbolicId;
	}

	public void setSymbolicId(String symbolicId) {
		this.symbolicId = symbolicId;
	}


}
