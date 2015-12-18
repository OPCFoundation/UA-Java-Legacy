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

import java.util.List;

import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;


public class DiagnosticInfo {

	public static final NodeId ID = Identifiers.DiagnosticInfo;
	
	Integer symbolicId;	
	Integer namespaceUri;
	Integer localizedText;
	Integer locale;
	String additionalInfo;
	StatusCode innerStatusCode;
	DiagnosticInfo innerDiagnosticInfo;
	List<String> stringTable;
	String[] stringArray;
	
	public DiagnosticInfo() 
	{	
	}

	public DiagnosticInfo(
			String additionalInfo,
			DiagnosticInfo innerDiagnosticInfo, 
			StatusCode innerStatusCode,
			Integer locale, 
			Integer localizedText, 
			Integer namespaceUri,
			Integer symbolicId) {
		this.additionalInfo = additionalInfo;
		this.innerDiagnosticInfo = innerDiagnosticInfo;
		this.innerStatusCode = innerStatusCode;
		this.locale = locale;
		this.localizedText = localizedText;
		this.namespaceUri = namespaceUri;
		this.symbolicId = symbolicId;
	}
	
	public DiagnosticInfo(
			String additionalInfo,
			DiagnosticInfo innerDiagnosticInfo, 
			StatusCode innerStatusCode,
			String locale, 
			String localizedText, 
			String namespaceUri,
			String symbolicId,
			List<String> stringTable) {
		this.additionalInfo = additionalInfo;
		this.innerDiagnosticInfo = innerDiagnosticInfo;
		this.innerStatusCode = innerStatusCode;
		this.stringTable = stringTable;
		this.locale = addOrGetIndex(locale);
		this.localizedText = addOrGetIndex(localizedText);
		this.namespaceUri = addOrGetIndex(namespaceUri);
		this.symbolicId = addOrGetIndex(symbolicId);
	}	
	
	public void setStringArray(String[] array)
	{
		this.stringTable = null;
		this.stringArray = array;
	}
	
	public void setStringTable(List<String> stringTable)
	{
		this.stringTable = stringTable;
		this.stringArray = null;
	}
	
	public List<String> getStringTable()
	{
		return stringTable;
	}
	
	private int addOrGetIndex(String str)
	{
		int index = stringTable.indexOf(str);
		if (index>=0) return index;
		stringTable.add(str);
		return stringTable.size()-1;		
	}
	
	public Integer getSymbolicId() {
		return symbolicId;
	}

	public void setSymbolicId(Integer symbolicId) {
		this.symbolicId = symbolicId;
	}

	public String getSymbolicIdStr() {
		if (symbolicId==null) return null;
		if (stringArray!=null)
			return stringArray[symbolicId];
		if (stringTable!=null)
			return stringTable.get(symbolicId);
		return symbolicId.toString();
	}

	public void setSymbolicIdStr(String symbolicId) {
		this.symbolicId = addOrGetIndex(symbolicId);
	}

	public String getNamespaceUriStr() {
		if (namespaceUri==null) return null;
		if (stringArray!=null)
			return stringArray[namespaceUri];
		if (stringTable!=null)
			return stringTable.get(namespaceUri);
		return namespaceUri.toString();
	}

	public void setNamespaceUriStr(String namespaceUri) {
		this.namespaceUri = addOrGetIndex(namespaceUri);
	}

	public Integer getNamespaceUri() {
		return namespaceUri;
	}

	public void setNamespaceUri(Integer namespaceUri) {
		this.namespaceUri = namespaceUri;
	}

	public String getLocalizedTextStr() {
		if (localizedText==null) return null;
		if (stringArray!=null)
			return stringArray[localizedText];
		if (stringTable!=null)
			return stringTable.get(localizedText);
		return localizedText.toString();
	}

	public void setLocalizedTextStr(String localizedText) {
		this.localizedText = addOrGetIndex(localizedText);
	}	
	
	public Integer getLocalizedText() {
		return localizedText;
	}

	public void setLocalizedText(Integer localizedText) {
		this.localizedText = localizedText;
	}

	public Integer getLocale()
	{
		return locale;
	}
	
	public void setLocale(Integer locale)
	{
		this.locale = locale;
	}

	public String getLocaleStr() {
		if (locale==null) return null;
		if (stringArray!=null)
			return stringArray[locale];
		if (stringTable!=null)
			return stringTable.get(locale);
		return locale.toString();
	}

	public void setLocaleStr(String locale) {
		this.locale = addOrGetIndex( locale );
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public StatusCode getInnerStatusCode() {
		return innerStatusCode;
	}

	public void setInnerStatusCode(StatusCode innerStatusCode) {
		this.innerStatusCode = innerStatusCode;
	}

	public DiagnosticInfo getInnerDiagnosticInfo() {
		return innerDiagnosticInfo;
	}

	public void setInnerDiagnosticInfo(DiagnosticInfo innerDiagnosticInfo) {
		this.innerDiagnosticInfo = innerDiagnosticInfo;
	}
	
	@Override
	public int hashCode() {
		return 
		ObjectUtils.hashCode(symbolicId) +  
		ObjectUtils.hashCode(namespaceUri)*3 +
		ObjectUtils.hashCode(localizedText)*5 +
		ObjectUtils.hashCode(locale)*13 + 
		ObjectUtils.hashCode(additionalInfo)*7 +
		ObjectUtils.hashCode(innerDiagnosticInfo)*17 +
		ObjectUtils.hashCode(innerStatusCode)*19;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DiagnosticInfo)) return false;
		DiagnosticInfo di = (DiagnosticInfo) obj;
		
		return 
			ObjectUtils.objectEquals(di.symbolicId, symbolicId) &&
			ObjectUtils.objectEquals(di.namespaceUri, namespaceUri) &&
			ObjectUtils.objectEquals(di.localizedText, localizedText) &&
			ObjectUtils.objectEquals(di.locale, locale) &&
			ObjectUtils.objectEquals(di.additionalInfo, additionalInfo) &&
			ObjectUtils.objectEquals(di.innerStatusCode, innerStatusCode) &&
			ObjectUtils.objectEquals(di.innerDiagnosticInfo, innerDiagnosticInfo);		
	}
	
	public static void toString(DiagnosticInfo di, StringBuilder sb, boolean omitLocalizedTextField, boolean omitStatusCode, boolean innerInfo) {
        sb.append(innerInfo ? "Inner Info: " : "Diagnostic Info: ");

        if (!omitLocalizedTextField && di.getLocalizedTextStr()!=null) 
        {
        	sb.append(di.getLocalizedTextStr());
        	sb.append(' ');
        }        	

        if (!omitStatusCode && di.getInnerStatusCode()!=null) {
			sb.append("(");
			sb.append(di.getInnerStatusCode().toString());
			sb.append(")");
		}
		sb.append('\n');
		
		if (di.getAdditionalInfo()!=null) {
			sb.append('\t');
			sb.append(di.getAdditionalInfo());
			sb.append('\n');
		}		
		
        if (di.getSymbolicIdStr()!=null)
            sb.append("\tSymbolicId: "+di.getSymbolicIdStr()+"\n");        

        if (di.getNamespaceUriStr()!=null) {
        	sb.append("\tNamespaceUri: "+di.getNamespaceUriStr()+"\n");
        }
		
		DiagnosticInfo inner = di.getInnerDiagnosticInfo();
		if (inner!=null) {
			toString(inner, sb, false, false, true);
		}		
	}	
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		toString(this, sb, false, false, false);
		return sb.toString();
	}
	
}
