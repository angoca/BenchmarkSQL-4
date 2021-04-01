/**
 * 
 */
package com.github.benchmarksql.jtpcc.exceptions;

/**
 * Exception raised when the properties file is incorrect and the benchmark
 * cannot start.
 * 
 * @author Andres Gomez
 */
public final class BenchmarkInitException extends Exception {

	/**
	 * Type of exception: Quantity of minutes and quantity of transactions were
	 * specified.
	 */
	public static final int MinutesOrQuantity = 1;
	/**
	 * Type of exception: The number of minutes is incorrect.
	 */
	public static final int NumberOfMinutes = 2;
	/**
	 * Type of exception: The number of terminals is incorrect
	 */
	public static final int NumberOfTerminals = 3;
	/**
	 * Type of exception: The number of transactions is incorrect.
	 */
	public static final int NumberOfTrxs = 4;
	/**
	 * Type of exception: The number of warehouses is incorrect.
	 */
	public static final int NumberOfWHs = 5;
	/**
	 * Type of exception: The sum of percentages should be equal to 100.
	 */
	public static final int PctgHigherThan100 = 6;
	/**
	 * Type of exception: The percentage is invalid.
	 */
	public static final int PercentageMix = 7;
	/**
	 * Generated ID for serialization.
	 */
	private static final long serialVersionUID = 8777907683561589424L;
	/**
	 * Description of the message.
	 */
	private String message;
	/**
	 * Type of initialization error.
	 */
	private int type;

	/**
	 * Creates the initialization exception with a type of error. This makes
	 * reference to the properties file.
	 * <ul>
	 * <li>1 - It should indicate quantity of transaction of number of minutes.</li>
	 * <li>2 - The number of minutes is incorrect.</li>
	 * <li>3 - The number of terminals is incorrect.</li>
	 * <li>4 - The number of transactions is incorrect.</li>
	 * <li>5 - The number of warehouses is incorrect.</li>
	 * </ul>
	 * 
	 * @param type Type of error.
	 */
	public BenchmarkInitException(final int type) {
		super();
		this.setType(type);
	}

	/**
	 * Creates the exception when the whole file is invalid.
	 * 
	 * @param string Description of the error.
	 */
	public BenchmarkInitException(final String message) {
		super();
		this.setMessage(message);
	}

	/**
	 * Retrieves the message of the exception, if exist.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Retrieves the reason of this exception.
	 * <ul>
	 * <li>1 - It should indicate quantity of transaction of number of minutes.</li>
	 * <li>2 - The number of minutes is incorrect.</li>
	 * <li>3 - The number of terminals is incorrect.</li>
	 * <li>4 - The number of transactions is incorrect.</li>
	 * <li>5 - The number of warehouses is incorrect.</li>
	 * </ul>
	 * 
	 * @return Type of the exception.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Changes the message of the exception.
	 * 
	 * @param message Message that describes the exception.
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * Sets the type of this exception.
	 * <ul>
	 * <li>1 - It should indicate quantity of transaction of number of minutes.</li>
	 * <li>2 - The number of minutes is incorrect.</li>
	 * <li>3 - The number of terminals is incorrect.</li>
	 * <li>4 - The number of transactions is incorrect.</li>
	 * <li>5 - The number of warehouses is incorrect.</li>
	 * </ul>
	 * 
	 * @param type Number that identifies the type of exception.
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * Prints the exception.
	 */
	@Override
	public String toString() {
		String ret = "Type = " + this.getType();
		if (this.getMessage() != null) {
			ret = "Message = " + this.getMessage();
		}
		ret += super.toString();
		return ret;
	}

}
