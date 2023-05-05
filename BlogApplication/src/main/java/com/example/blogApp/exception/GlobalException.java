package com.example.blogApp.exception;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.blogApp.payload.ErrorDetails;

@ControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorDetails> handleResourceNptFoundException( ResourceNotFoundException exception,
			WebRequest webrequest){
		
		ErrorDetails errordetails = new ErrorDetails(new Date(), exception.getMessage(),
				webrequest.getDescription(false));
		return new ResponseEntity<>(errordetails,HttpStatus.NOT_FOUND);	
	}
	
	@ExceptionHandler(BlogApiException.class)
	public ResponseEntity<ErrorDetails> handleBlogApiFoundException( BlogApiException exception,
			WebRequest webrequest){
		
		ErrorDetails errordetails = new ErrorDetails(new Date(), exception.getMessage(),
				webrequest.getDescription(false));
		return new ResponseEntity<>(errordetails,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleinoutException( Exception exception,
			WebRequest webrequest){
		
		ErrorDetails errordetails = new ErrorDetails(new Date(), exception.getMessage(),
				webrequest.getDescription(false));
		return new ResponseEntity<>(errordetails,HttpStatus.INTERNAL_SERVER_ERROR);
		
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, 
			WebRequest request) {
		
		Map<String ,String > errors = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName =((FieldError)error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		    
		return new ResponseEntity<> (errors,HttpStatus.BAD_GATEWAY);
	}
	
	@ExceptionHandler(AccessDeniedException.class)
	public ResponseEntity<ErrorDetails> handleAccessDeniedException( 
			AccessDeniedException exception,
			WebRequest webrequest){
		
		ErrorDetails errordetails = new ErrorDetails(new Date(), exception.getMessage(),
				webrequest.getDescription(false));
		return new ResponseEntity<>(errordetails,HttpStatus.UNAUTHORIZED);
		
	}
	
	

}
