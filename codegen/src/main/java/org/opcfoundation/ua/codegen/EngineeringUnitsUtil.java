package org.opcfoundation.ua.codegen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class EngineeringUnitsUtil {

	public static class EUnit implements Comparable<EUnit>{

		final String javaName;
		final String commonCode;
		final int id;
		final String description;
		final String displayName;

		public EUnit(String javaName, String commonCode, int id, String description, String displayName) {
			this.javaName = javaName;
			this.commonCode = commonCode;
			this.id = id;
			this.description = description;
			this.displayName = displayName;
		}

		@Override
		public int compareTo(EUnit o) {
			return javaName.compareTo(o.javaName);
		}

	}

	public static List<EUnit> readUnits(File file) throws Exception{
		List<EUnit> r = new ArrayList<EngineeringUnitsUtil.EUnit>();
		CSVFormat format = CSVFormat.newFormat('\t').withFirstRecordAsHeader().withTrim();
		
		Reader in = new InputStreamReader(new FileInputStream(file), "UTF-16LE");
		try{
			CSVParser data = format.parse(in);
			
			/*
			 * The data contains at least one "Denier" unit that contains the same name.
			 * Therefore the generated code gives warnings. Skipping dublicates.
			 */
			Set<String> usedNames = new HashSet<String>();
			for(CSVRecord record : data.getRecords()){
				//System.out.println(record.getRecordNumber());
				String status = record.get(0);
				if(status != null){
					//deleted
					if("X".equals(status)){
						continue;
					}
					//depricated
					if("D".equals(status)){
						continue;
					}
				}
				String code = record.get("CommonCode");
				
				int id = unitIdForCommonCode(code);
				String description = record.get("Name");
				String displayName = record.get("Symbol");

				//must escape description and displayname since they might contain e.g. "-character
				//seems this must be done 2 times to survive output to file
				description = StringEscapeUtils.escapeJava(description);
				description = StringEscapeUtils.escapeJava(description);
				displayName = StringEscapeUtils.escapeJava(displayName);
				displayName = StringEscapeUtils.escapeJava(displayName);

				//attempting to make java constant from description
				String javaName = StringUtils.normalizeSpace(description);
				javaName = StringUtils.replace(javaName, "/", " per ");
				javaName = StringUtils.replace(javaName, "%", " percent ");
				javaName = StringUtils.replace(javaName, "\\u00BAC", " CELSIUS ");
				javaName = StringUtils.replace(javaName, "\\u00BAF", " FAHRENHEIT ");
				javaName = StringUtils.replace(javaName, "\\u00A0", " ");
				
				javaName = StringUtils.replace(javaName, "-", "_");
				javaName = StringUtils.upperCase(javaName);
				javaName = StringUtils.replace(javaName, " ", "_");
				//',.()'' cannot be used, removing				
				javaName = StringUtils.replaceChars(javaName, ",.()'[]", "");
				
				if(NumberUtils.isDigits(javaName.charAt(0)+"")){
					javaName = "_"+javaName;
				}
				
				if(usedNames.contains(javaName)){
					continue;
				}else{
					usedNames.add(javaName);
				}
				
				if(code != null && !code.isEmpty()){ //skips rows of just ;;; at end
					r.add(new EUnit(javaName, code, id, description, displayName));
				}
			}
		}catch(Exception e){
			System.out.println("Error while parsing file:"+file);
			e.printStackTrace();
		}finally{
			in.close();
		}
		Collections.sort(r);
		return r;
	}

	private static int unitIdForCommonCode(String commoncode){
		//based on specification Part 8 5.6.3 EUInformation
		int unitId = 0;
		for(int i=0;i<commoncode.length(); i++){
			unitId = unitId << 8;
			unitId = unitId | commoncode.charAt(i);
		}
		return unitId;		
	}
	
}
