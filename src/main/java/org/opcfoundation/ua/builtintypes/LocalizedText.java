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

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.opcfoundation.ua.core.Identifiers;
import org.opcfoundation.ua.utils.ObjectUtils;


/**
 * This primitive DataType is specified as a string that is composed of a 
 * language component and a country/region component as specified by RFC 3066. 
 * The &lt;country/region&gt; component is always preceded by a hyphen. The format of 
 * the LocaleId string is shown below:
 * 
 *    &lt;language&gt;[-&lt;country/region&gt;], where &lt;language&gt; is the two letter ISO 639 
 *    code for a language, &lt;country/region&gt; is the two letter ISO 3166 code for 
 *    the country/region.
 * 
 * The rules for constructing LocaleIds defined by RFC 3066 are restricted for 
 * OPC UA as follows:
 *  d) OPC UA permits only zero or one &lt;country/region&gt; component to follow the 
 *    &lt;language&gt; component,
 *  e) OPC UA also permits the "-CHS" and "-CHT" three-letter &lt;country/region&gt; 
 *    codes for "Simplified" and "Traditional" Chinese locales.
 *  f) OPC UA also allows the use of other &lt;country/region&gt; codes as deemed 
 *    necessary by the client or the server.
 *    
 * Example:
 *  English				en
 *  English (US)		en-US
 *  German				de
 *  German (Germany)	de-DE
 *  German (Austrian)	de-AT
 * 
 * See Country Codes <code>http://www.iso.org/iso/english_country_names_and_code_elements</code>
 * See Language Codes <code>http://www.loc.gov/standards/iso639-2/php/English_list.php</code> 
 * @see <code>http://www.ietf.org/rfc/rfc3066.txt</code> 
 * @see Locale
 * @author Toni Kalajainen (toni.kalajainen@vtt.fi)
 */
public final class LocalizedText {
	
	/** The pattern of the locale part */
	public static final Pattern LOCALE_PATTERN = Pattern.compile("^(([a-z]{2})(-([A-Z]{2,3}){1})?)?$");
	
	public static final Locale NO_LOCALE = new Locale("", "");
	
	public static final Locale NULL_LOCALE = null;
	
	public static final NodeId ID = Identifiers.LocalizedText;

	public static final LocalizedText NULL = new LocalizedText(null, NULL_LOCALE);
	
	public static final LocalizedText EMPTY = new LocalizedText("", NULL_LOCALE);
	// Empty string in English locale
	public static final LocalizedText EMPTY_EN = english("");
	
	/** Localized text */
	private String text;
	
	/** Optional locale */
	private String locale;

	/**
	 * Convert UA LocateId to {@link Locale} 
	 * @param localeId or null
	 * @return locale or null
	 */
	public static Locale toLocale(String localeId) {
		if (localeId==null) return null;
		Matcher m = LOCALE_PATTERN.matcher(localeId);
		if (!m.matches()) 
			return NO_LOCALE;
			//throw new IllegalArgumentException("Invalid locale \""+localeId+"\""); // changes made 3.6. TODO

		String language = m.group(2);
		String country = m.group(4);
		if (language == null) language = "";
		if (country == null) country = "";
		
		return new Locale(language, country);
	}

	/**
	 * Convert {@link Locale} to UA LocaleId String
	 * @param locale locale or null
	 * @return LocaleId or null
	 */
	public static String toLocaleId(Locale locale) {
		if (locale==null) return null;
		return locale.getLanguage() + (!locale.getCountry().equals("")?"-"+locale.getCountry():"") ;		
	}

	/**
	 * Create new Localized Text
	 * 
	 * @param text Localized text or null
	 * @param localeId &lt;language&gt;[-&lt;country/region&gt;] or null
	 */
	public LocalizedText(String text, String localeId) {
		this.text = text;
		this.locale = localeId;
	}
	
	/**
	 * Create new Localized Text with locale NO_LOCALE
	 * @param text the text
	 */
	public LocalizedText(String text){
		this(text, NO_LOCALE);
	}
	
	/**
	 * Create a english text
	 * 
	 * @param text string
	 * @return english text
	 */
	public static LocalizedText english(String text) {
		return new LocalizedText(text, "en");
	}
	
	/**
	 * Create new localized text
	 * 
	 * @param text or null
	 * @param locale locale or null 
	 */
	public LocalizedText(String text, Locale locale) {
		this.text = text;
		this.locale = (locale == null ? null : locale.toString());
	}
	
	@Override
	public int hashCode() {
		return ObjectUtils.hashCode(text) + 
				3*ObjectUtils.hashCode(locale);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) return this.locale == null && this.text == null;
		if (!(obj instanceof LocalizedText)) return false;
		LocalizedText other = (LocalizedText) obj;
		return 
			ObjectUtils.objectEquals(text, other.text) &&
			ObjectUtils.objectEquals(locale, other.locale);
	}

	/**
	 * Get the whole locale string
	 * @return LocaleId
	 */
	public String getLocaleId() {
		return locale;
	}
	
	/**
	 * Get locale object
	 * @return locale or null
	 */
	public Locale getLocale() {
		return toLocale(locale);
	}

	public String getText() {
		return text;
	}
	
	@Override
	public String toString() {
		if (getLocaleId() == null)
			  return getText();
		return "("+getLocaleId()+") "+getText();
	}
	
}
