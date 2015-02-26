package main;

public class AdminAlreadyExistsException extends Exception {
	public static final String MESSAGE = "That command already exists.";

	public String getMessage() {
		return MESSAGE;
	}
}
