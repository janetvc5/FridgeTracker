package org.springframework.fridgetracker.system;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CrashController extends ResponseEntityExceptionHandler {
	 
	/*
	 * Sends the time of request, message from exception, and details of the request.
	 * 
	 * The default throw returns internal server error, but this can be customized
	 */
	/**
	 * Acts as an exception handler, uses new ErrorResponse class to make exception messages easier for the frontend to read
	 * @param ex - The exception thrown
	 * @param r - The web request called by user
	 * @return - Returns an ErrorResponse type ResponseEntity
	 */
	@ExceptionHandler(Throwable.class)
	@ResponseBody
	public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest r) {
		ErrorResponse err = new ErrorResponse(new Date(), ex.getMessage(), r.getDescription(false));
		return new ResponseEntity<ErrorResponse>(err,HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
