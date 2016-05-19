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

import org.opcfoundation.ua.builtintypes.ServiceResult;
import org.opcfoundation.ua.builtintypes.Structure;
import org.opcfoundation.ua.builtintypes.ExpandedNodeId;
import org.opcfoundation.ua.builtintypes.NodeId;
import org.opcfoundation.ua.core.HistoryReadValueId;
import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.core.StatusCodes;
import org.opcfoundation.ua.utils.NumericRange;
import org.opcfoundation.ua.utils.ObjectUtils;
import org.opcfoundation.ua.builtintypes.QualifiedName;
_imports_

@Description("_description_")
public class _ClassName_ extends _SuperType_ implements Structure, Cloneable {

	public static final ExpandedNodeId ID = new ExpandedNodeId(Identifiers._ClassName_);
	public static final ExpandedNodeId BINARY = new ExpandedNodeId(Identifiers._ClassName__Encoding_DefaultBinary);
	public static final ExpandedNodeId XML = new ExpandedNodeId(Identifiers._ClassName__Encoding_DefaultXml);
	NumericRange ParsedIndexRange;

_Content_ 

	public static ServiceResult validate(HistoryReadValueId valueId){
		// Check for null structure.
		if (valueId == null) {
			return new ServiceResult(StatusCodes.Bad_StructureMissing);
		}

		// Null node ids are always invalid.
		if (valueId.getNodeId() == null) {
			return new ServiceResult(StatusCodes.Bad_NodeIdInvalid);
		}
	
		//init as empty
		NumericRange range = NumericRange.getEmpty();
		if(!(valueId.getIndexRange() == null || valueId.getIndexRange().isEmpty())){
			try{
				range = NumericRange.parse(valueId.getIndexRange());
				valueId.setParsedIndexRange(range);
				
			}
			catch (Exception e) {
				return new ServiceResult(StatusCodes.Bad_IndexRangeInvalid, e);
			}
		} else {
			valueId.setParsedIndexRange(NumericRange.getEmpty());
		}

		// passed basic validation
		return null;
	}


	public NumericRange getParsedIndexRange() {
		return ParsedIndexRange;
	}

	public void setParsedIndexRange(NumericRange parsedIndexRange) {
		ParsedIndexRange = parsedIndexRange;
	}


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
		return "_ClassName_: "+ObjectUtils.printFieldsDeep(this);
	}

}
