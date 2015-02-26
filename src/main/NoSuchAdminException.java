package main;

public class NoSuchAdminException extends Exception {

	public static final String MESSAGE = "That command does not exist.";

	public String getMessage() {
		return MESSAGE;
	}

}
