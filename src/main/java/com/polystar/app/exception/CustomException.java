package com.polystar.app.exception;

public class CustomException extends Exception {

	public CustomException(String source, String description) {
		System.out.println("Exception is in: " + source + ". Description: " + description);
	}

}
