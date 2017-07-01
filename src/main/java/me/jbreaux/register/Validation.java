package me.jbreaux.register;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.util.regex.Pattern;

@Component
public class Validation {
	@Value("${validation.zip}")
	private String zipRegex;

	@Value("${validation.country}")
	private String countryList[];

	/**
	 * Sets the Valid Regex for Zip Codes
	 */
	public void setZipRegex(String val) {
		zipRegex = val;
	}

	/**
	 * Sets the list of valid country values
	 */
	public void setCountryCodes(String vals[]) {
		countryList = vals;
	}

	/**
	 *  Validates a ZIP Code using a regex match
	 *  @return Whether the zip is valid
	 */
	public boolean isValidZip(String zip) {
		return Pattern.matches(zipRegex, zip);
	}

	/**
	 * Validates that a Country is in the country list
	 */
	public boolean isValidCountry(String country) {
		for (int i = 0; i < countryList.length; i++) {
			if(countryList[i].equals(country)) {
				return true; //short circuit true if it matches a country code
			}
		}
		return countryList.length == 0; //return true for an empty list of countries
	}

}
