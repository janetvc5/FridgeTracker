package org.springframework.fridgetracker.system;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CrashController implements ErrorController {
	private final Logger logger = LoggerFactory.getLogger(CrashController.class);
	 
	@RequestMapping("/error")
	public String handleError(HttpServletRequest r) {
		logger.info("Error thrown: "+r.toString());
	    return "Error thrown: "+r.toString();
	}
	
	//For throws defined by us
	@ExceptionHandler(APIException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public APIException handleAPIException(APIException e) {
        APIException response = new APIException(e.getErrorCode(),e.getMessage());
        return response;
    }
	 
	 
	@Override
	public String getErrorPath() {
	    return "/error";
	}
}
