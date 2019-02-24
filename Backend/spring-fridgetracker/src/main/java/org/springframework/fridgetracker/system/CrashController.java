package org.springframework.fridgetracker.system;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CrashController implements ErrorController {
	private final Logger logger = LoggerFactory.getLogger(CrashController.class);
	 
	@RequestMapping("/error")
	public String handleError(HttpServletRequest r) {
	    logger.info("Error thrown: "+r.toString());
	    return "Error thrown: "+r.toString();
	}
	 
	@Override
	public String getErrorPath() {
	    return "/error";
	}
}
