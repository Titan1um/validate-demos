package live.validate.jun.controller;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import live.validate.jun.service.Validator;


@RestController
@CrossOrigin
public class IsAController {
	
	@GetMapping(value = "/selfDefine")
	@ResponseBody
	public String selfDefine(HttpServletRequest req) {
		System.out.println("sd");
		return new Validator().getSDCallBack(req);
	}

	@GetMapping(value = "/outerValidate",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String outerValidate(HttpServletRequest req) {
		System.out.println("ov");
		return new Validator().getOVCallBack(req);
	}
	
	@GetMapping(value = "/ov",produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String outerValidateTest(ServletRequest req,ServletResponse resp) throws Exception {
		System.out.println("ov");
		return new Validator().execute(req, resp);
	}
	
}
