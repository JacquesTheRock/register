package me.jbreaux.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.dao.DataAccessException;

@Controller
public class RegisterController {

	private Validation validator;
	@Autowired
	public void setValidator(Validation v) {
		this.validator = v;
	}

	@Autowired
	JdbcTemplate jdbcTemplate;
	//DataSource ds;

	private String afterRegister = "/confirm.html";
	@RequestMapping(value = "/newregister")
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
		boolean valid = true;
		valid = valid || validator.isValidName(fname);
		valid = valid || validator.isValidName(lname);
		valid = valid || validator.isValidAddress(addr1);
		valid = valid || (addr2 != null && !validator.isValidAddress(addr2));
		valid = valid || validator.isValidName(city);
		valid = valid || validator.isValidName(state);
		valid = valid || validator.isValidZip(zip);
		valid = valid || validator.isValidCountry(country);
		if(!valid) {
			return new RedirectView(request.getHeader("referer"), false);
		} else {
			try {
				jdbcTemplate.update("INSERT INTO registration(givenName,familyName,address1,address2,city,state,zip,country) VALUES (?, ?, ?, ?, ?, ?, ?, ?)", fname,lname,addr1,addr2,city,state,zip,country);
			} catch(DataAccessException ex) {
				//TODO: Log the error and redirect to an Error page
				return new RedirectView(request.getHeader("referer"), false);
			}
			return new RedirectView(afterRegister, true);
		}
	}

}
