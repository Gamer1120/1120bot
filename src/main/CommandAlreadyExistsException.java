package main;

public class CommandAlreadyExistsException extends Exception {
	public static final String MESSAGE = "That command already exists.";

	public String getMessage() {
		return MESSAGE;
	}
}
