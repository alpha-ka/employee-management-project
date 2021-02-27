package com.alpha.employeelogin.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

//Component scan will scan this class 
//This annotation handles global exceptions
@ControllerAdvice
public class GlobalExceptionHandler {
	
	//Handle Specific Exceptions
	
	//This annotation handles specific exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException exception,WebRequest request)
	{
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
		
		return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
	}

	
	//Handle Specific Exception
	
	//This annotation handles specific exception
		@ExceptionHandler(APIException.class)
		public ResponseEntity<?> handleAPIException(APIException exception,WebRequest request)
		{
			ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
			
			return new ResponseEntity(errorDetails, HttpStatus.NOT_FOUND);
		}
		
	
	//Handle Global Exceptions
	//This annotation handles specific exception and sending custom response to the client
	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleException(Exception exception,WebRequest request)
	{
		ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), request.getDescription(false));
		
		return new ResponseEntity(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	
	
	
}
