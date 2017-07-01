package me.jbreaux.register;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.util.regex.Pattern;

@Component
public class Validation {
	@Value("${validation.zip}")
	private String zipRegex;

	@Value("${validation.name}")
	private String nameRegex;

	@Value("${validation.country}")
	private String countryList[];

	@Value("${validation.address.minlength}")
	private int minAddrLength;
	@Value("${validation.address.maxlength}")
	private int maxAddrLength;

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
	 * Sets the regex for valid names or namelike fields
	 */
	public void setNameRegex(String val) {
		nameRegex = val;
	}

	/**
	 * Set the minimum address length
	 */
	public void setAddressMin(int val) {
		minAddrLength = val;
	}

	/**
	 * Set the maximum address length
	 */
	public void setAddressMax(int val) {
		maxAddrLength = val;
	}

	/**
	 *  Validates a ZIP Code using a regex match
	 *  @return Whether the zip is valid
	 */
	public boolean isValidZip(String zip) {
		return zip != null && Pattern.matches(zipRegex, zip);
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

	/**
	 * Validates the address length, we only check bounds on min and maximum lengths
	 */
	public boolean isValidAddress(String address) {
		return address != null && address.length() >= minAddrLength && address.length() <= maxAddrLength;
	}

	/**
 	* Validate the name using the supplied regex.
 	*/
	public boolean isValidName(String name) {
		return name != null && Pattern.matches(nameRegex, name);
	} 	

}
