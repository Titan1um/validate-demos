package live.validate.jun.controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import live.validate.jun.service.Validator;

@Controller
public class testController {

	@GetMapping("/ov")
	public String test(ServletRequest req,ServletResponse resp) throws Exception {
		Validator.execute(req, resp);
		return null;
	}
}
