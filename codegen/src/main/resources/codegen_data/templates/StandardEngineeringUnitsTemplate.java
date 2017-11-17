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

import org.opcfoundation.ua.core.EUInformation;
import org.opcfoundation.ua.builtintypes.LocalizedText;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

_imports_

@Description("_description_")
public class _ClassName_ {
	
	public static final String URI = "https://opcfoundation.org/UA/units/un/cefact";
	
	private static Map<Integer, EUInformation> unitMap = new HashMap<Integer, EUInformation>();
	private static Map<String, EUInformation> ccUnitMap = new HashMap<String, EUInformation>();
	private static List<EUInformation> all = new ArrayList<EUInformation>();
	
_Content_ 

	public static EUInformation getByUnitId(int unitId){
		return unitMap.get(unitId);
	}

	public static EUInformation getByCommonCode(String commonCode){
		return ccUnitMap.get(commonCode);
	}
	
	public static List<EUInformation> getAll(){
	  List<EUInformation> r = new ArrayList<EUInformation>();
	  for(EUInformation eui : all){
	    r.add(eui.clone());
	  }
	  return r;
	}

	private static EUInformation init(String commonCode, int unitId, String displayName, String description){
		 EUInformation unit = new EUInformation(URI, unitId, new LocalizedText(displayName), new LocalizedText(description));
		 unitMap.put(unitId, unit);
		 ccUnitMap.put(commonCode, unit);
		 all.add(unit);
		 return unit;
	}

}
