package com.ajs.demo.banking.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

	@ExceptionHandler(LowBalanceException.class)
	public ResponseEntity handleException(LowBalanceException e) {
		return handleException(e, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(SavingsAccountException.class)
	public ResponseEntity handleException(SavingsAccountException e) {
		return handleException(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}


	@ExceptionHandler(SavingsAccountNotFoundException.class)
	public ResponseEntity handleException(SavingsAccountNotFoundException e) {
		return handleException(e, HttpStatus.NOT_FOUND);
	}

	private ResponseEntity handleException(Exception e, HttpStatus status) {
		String localizedMessage = e.getLocalizedMessage();
		LOGGER.error(localizedMessage, e);
		return ResponseEntity.status(status).body(localizedMessage);
	}
}
