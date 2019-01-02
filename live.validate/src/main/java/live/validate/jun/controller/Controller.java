package live.validate.jun.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import live.validate.jun.service.Validator;

@RestController
public class Controller {
	
	
	@GetMapping("/selfDefine")
	public String selfDefine(HttpServletRequest req) {
		return Validator.getSDCallBack(req);
	}
	
	@GetMapping("/outerValidate")
	public String outerValidate(HttpServletRequest req) {
		return Validator.getOVCallBack(req);
	}
}
