package main;

public class NoSuchCommandException extends Exception {

	public static final String MESSAGE = "That command does not exist.";

	public String getMessage() {
		return MESSAGE;
	}

}
