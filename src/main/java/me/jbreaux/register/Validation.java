package me.jbreaux.register;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import java.util.regex.Pattern;

@Component
public class Validation {
	@Value("${validation.zip}")
	private String zipRegex;

	public void setZipRegex(String val) {
		zipRegex = val;
	}

	/**
	 *  Validates a ZIP Code using a regex match
	 *  @return Whether the zip is valid
	 */
	public boolean isValidZip(String zip) {
		return Pattern.matches(zipRegex, zip);
	}

}
