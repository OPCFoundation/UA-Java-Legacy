/* ========================================================================
 * Copyright (c) 2005-2015 The OPC Foundation, Inc. All rights reserved.
 *
 * OPC Foundation MIT License 1.00
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * The complete license agreement can be found here:
 * http://opcfoundation.org/License/MIT/1.00/
 * ======================================================================*/

package _PackageName_;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.opcfoundation.ua.builtintypes.DateTime;
import org.opcfoundation.ua.builtintypes.DiagnosticInfo;
import org.opcfoundation.ua.builtintypes.StatusCode;
import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.builtintypes.UnsignedInteger;
import org.opcfoundation.ua.common.ServiceResultException;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.core.ServiceFault;
import org.opcfoundation.ua.core.StatusCodes;
_imports_

@Description("_description_")
public class _ClassName_ extends _SuperType_ implements Structure, Cloneable {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers._ClassName_);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers._ClassName__Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers._ClassName__Encoding_DefaultXml);
	
	public static ServiceFault createServiceFault(UnsignedInteger statusCode)
	{
		ResponseHeader rh = new ResponseHeader();
		ServiceFault result = new ServiceFault(rh);		
		rh.setServiceResult( new StatusCode( statusCode ) );
		rh.setTimestamp(new DateTime());
		return result;
	}

	/**
	 * Gathers info from an exception and puts the description into a new ServiceFault.
	 * Stack Trace is converted into a DiagnosticInfo. 
	 * 
	 * @param t a ServiceResultException or Throwable (Bad_InternalError)
	 * @return new service fault
	 */
	public static ServiceFault toServiceFault(Throwable t) {
		ResponseHeader rh = new ResponseHeader();
		ServiceFault result = new ServiceFault(rh);
		
		rh.setServiceResult(t instanceof ServiceResultException ? ((ServiceResultException)t).getStatusCode() : new StatusCode(StatusCodes.Bad_InternalError));
		rh.setTimestamp(new DateTime());

		// Stack Trace
		List<String> stringTable = new ArrayList<String>();

		DiagnosticInfo di = null;		
		while (t!=null) {
			if (di==null) {
				rh.setServiceDiagnostics( di = new DiagnosticInfo() );
			} else {
				di.setInnerDiagnosticInfo( di = new DiagnosticInfo() );
			}
			di.setStringTable(stringTable);
			di.setLocalizedTextStr( t instanceof ServiceResultException ? t.getMessage() : t.toString() );
			StringWriter sw = new StringWriter(100);
			PrintWriter pw = new PrintWriter(sw);		
            for (StackTraceElement e : t.getStackTrace())
                pw.println("\tat " + e);
			di.setAdditionalInfo(sw.toString());
			di.setInnerStatusCode(t instanceof ServiceResultException ? ((ServiceResultException)t).getStatusCode() : new StatusCode(StatusCodes.Bad_InternalError));
			t = t.getCause();
		}
		
		rh.setStringTable(stringTable.toArray(new String[stringTable.size()]));
		
		return result;
	}
	
_Content_

	public ExpandedNodeId getTypeId() {
		return ID;
	}

	public ExpandedNodeId getXmlEncodeId() {
		return XML;
	}

	public ExpandedNodeId getBinaryEncodeId() {
		return BINARY;
	}
	
	public String toString() {
    	ResponseHeader rh = getResponseHeader();
    	if (rh==null) return "ServiceFault";
		StringBuilder sb = new StringBuilder();		
		sb.append("ServiceFault: ");		
    	StatusCode code = rh.getServiceResult();
    	if (code != null) {
    		sb.append(code.toString());
    	}
    	sb.append('\n');    	

		DiagnosticInfo di = rh.getServiceDiagnostics();
    	if (di!=null)
    		DiagnosticInfo.toString(di, sb, false, true, false);    	
    	
    	if (sb.length()==0) return "ServiceFault";
    	if (sb.charAt(sb.length()-1)=='\n')
    		sb.setLength(sb.length()-1);
    	
		return sb.toString();    	
	}

}
