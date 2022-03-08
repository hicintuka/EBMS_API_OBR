package com.fonctionnels.web;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler
{
	

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request)
	{
		Map<String, String> errors = new HashMap<String, String>();
		
		ex.getBindingResult().getAllErrors().forEach(error->{
			String fieldname = ((FieldError) error).getField();
			if (error.getDefaultMessage().contains("not be empty"))
			{
				String msg = "ne doit pas etre vide";
				errors.put(fieldname, msg);
			}
			else 
			{
				String msg = error.getDefaultMessage();
				errors.put(fieldname, msg);
			}
		});
		
		return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
	}
}
