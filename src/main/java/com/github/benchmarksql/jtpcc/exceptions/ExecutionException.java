package com.github.benchmarksql.jtpcc.exceptions;

/**
 * Exception raised during execution.
 * 
 * @author Andres Gomez
 */
public class ExecutionException extends Exception {

	/**
	 * Descriptive message of the exception.
	 */
	private String message;

	/**
	 * Default constructor describing the reason.
	 * 
	 * @param message Descriptive message of the exception.
	 */
	public ExecutionException(final String message) {
		this.message = message;
	}

	/**
	 * Generated ID for serialization.
	 */
	private static final long serialVersionUID = 361264612937917893L;

	@Override
	public String toString() {
		String ret = null;
		if (this.message != null) {
			ret = message;
		}
		return ret + super.toString();
	}
}
