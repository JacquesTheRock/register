package me.jbreaux.register;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataAccessException;

//Logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping(value = "/api/register")
public class RegisterController {

	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);

	private Validation validator;
	@Autowired
	public void setValidator(Validation v) {
		this.validator = v;
	}

	@Autowired
	JdbcTemplate jdbcTemplate;
	//DataSource ds;

	private String afterRegister = "/confirm.html";
	@RequestMapping(method = RequestMethod.POST)
	public RedirectView RegisterInfo(
			HttpServletRequest request,
			@RequestParam(value="fname") String fname,
			@RequestParam(value="lname") String lname,
			@RequestParam(value="addr1") String addr1,
			@RequestParam(value="addr2", required=false) String addr2,
			@RequestParam(value="city") String city,
			@RequestParam(value="state") String state,
			@RequestParam(value="zip") String zip,
			@RequestParam(value="country") String country) {
		boolean validFName = validator.isValidName(fname);
		boolean validLName = validator.isValidName(lname);
		boolean validAddr1 = validator.isValidAddress(addr1);
		boolean validState = validator.isValidState(state);
		boolean validZip = validator.isValidZip(zip);
		boolean validCountry = validator.isValidCountry(country);
		boolean valid = validFName && 
				validLName &&
				validAddr1 &&
				validState &&
				validZip && 
				validCountry;
		if(!validFName) {
			logger.debug("Failure to insert registree due to invalid First Name: ({})", fname);
		} 
		if(!validLName) {
			logger.debug("Failure to insert registree due to invalid Last Name: ({})", lname);
		}
		if(!validAddr1) {
			logger.debug("Failure to insert registree due to invalid Address 1: ({})", addr1);
		}
		if(!validState) {
			logger.debug("Failure to insert registree due to invalid State: ({})", state);
		}
		if(!validZip) {
			logger.debug("Failure to insert registree due to invalid Zip Code: ({})", zip);
		}
		if(!validCountry) {
			logger.debug("Failure to insert registree due to invalid Country: ({})", country);
		}
		if (!valid) {
			logger.debug("Validation Used: {}", validator);
			return new RedirectView(request.getHeader("referer"), false);
		} else {
			try {
				jdbcTemplate.update(
					"INSERT INTO registration(givenName,familyName,address1,address2,city,state,zip,country) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", 
					fname,lname,addr1,addr2,city,state,zip,country
					);
			} catch(DataAccessException ex) {
				//TODO: Log the error and redirect to an Error page
				logger.error("Failure to insert record due to: {}", ex.getMessage());
				return new RedirectView(request.getHeader("referer"), false);
			}
			return new RedirectView(afterRegister, true);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody Registree[] RegistreeInfo(HttpServletRequest request) {
		ArrayList<Registree> registeredUsers = new ArrayList<Registree>();
		try {
			jdbcTemplate.query(
				"SELECT givenName,familyName,address1,address2,city,state,zip,country,date FROM registration ORDER BY date DESC",
				(res, rowNum) -> new Registree(
						res.getString("givenName"),
						res.getString("familyName"),
						res.getString("address1"),
						res.getString("address2"),
						res.getString("city"),
						res.getString("state"),
						res.getString("zip"),
						res.getString("country"),
						res.getString("date")
					)).forEach(registree -> registeredUsers.add(registree));
		} catch (DataAccessException ex) {
			//TODO: Log the error
		}
		Registree out[] = new Registree[registeredUsers.size()];
		for(int i = 0; i < registeredUsers.size(); i++) {
			out[i] = registeredUsers.get(i);
		}
		return out;
	}
}
