package me.jbreaux.register;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.view.RedirectView;
import javax.servlet.http.HttpServletRequest;
import org.springframework.jdbc.core.JdbcTemplate;
//import javax.sql.DataSource;

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
		return new RedirectView(afterRegister, true);
	}

}
