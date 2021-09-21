package com.github.benchmarksql.jtpcc;

import java.text.SimpleDateFormat;

/**
 * Basic configuration parameters for jTPCC.
 * 
 * @author Raul Barbosa - 2003
 * @author Denis Lussier - 2004-2014
 */
public interface jTPCCConfig {
	/**
	 * Version of this application.
	 */
	public static String BENCHMARKSQL_VERSION = "4.2";

	/**
	 * New order code.
	 */
	public static int NEW_ORDER = 1;
	/**
	 * Payment code.
	 */
	public static int PAYMENT = 2;
	/**
	 * Order status code.
	 */
	public static int ORDER_STATUS = 3;
	/**
	 * Delivery code.
	 */
	public static int DELIVERY = 4;
	/**
	 * Stock level code.
	 */
	public static int STOCK_LEVEL = 5;

	public static String[] nameTokens = { "BAR", "OUGHT", "ABLE", "PRI", "PRES", "ESE", "ANTI", "CALLY", "ATION",
			"EING" };

	/**
	 * Date with simple date format - yyyy-MM-dd HH:mm:ss.
	 */
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * Quantity of rows before commit, when loading data. This is used for loading data.
	 */
	public static int configCommitCount = 10000; // commit every n records in LoadData

	/**
	 * Default quantity of warehouses. This is used for loading data.
	 */
	public static int configWhseCount = 10;
	/**
	 * Default quantity of items to configure per warehouse.  This is used for loading data. TODO This could be
	 * configurable with properties file.
	 */
	public static int configItemCount = 100000; // tpc-c std = 100,000
	/**
	 * Maximum number per warehouse to generate random numbers. This is used for loading data.
	 */
	public static int configDistPerWhse = 10; // tpc-c std = 10
	/**
	 * Maximum number of customer per warehouse. This is used for loading data.
	 */
	public static int configCustPerDist = 3000; // tpc-c std = 3,000
}
