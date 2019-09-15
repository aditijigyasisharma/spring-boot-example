package com.ajs.demo.banking.exceptions;

public class LowBalanceException extends RuntimeException {


	private String message;



	public LowBalanceException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}



}
