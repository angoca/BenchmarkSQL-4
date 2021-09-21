package com.github.benchmarksql.jtpcc;

import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Utility functions for the Open Source Java implementation of the TPC-C
 * benchmark.
 * 
 * @author Raul Barbosa - 2003
 * @author Denis Lussier - 2004-2014
 */
public class jTPCCUtil implements jTPCCConfig {

	private static final Logger log = LogManager.getLogger(jTPCCUtil.class);

	public static String getSysProp(final String inSysProperty, final String defaultValue) {

		String outPropertyValue = null;

		try {
			outPropertyValue = System.getProperty(inSysProperty, defaultValue);
		} catch (Exception e) {
			log.error("Error Reading Required System Property '{}'", inSysProperty);
		}

		return outPropertyValue;

	} // end getSysProp

	public final static String randomStr(long strLen) {

		char freshChar;
		String freshString;
		freshString = "";

		while (freshString.length() < (strLen - 1)) {

			freshChar = (char) (Math.random() * 128);
			if (Character.isLetter(freshChar)) {
				freshString += freshChar;
			}
		}

		return freshString;

	} // end randomStr

	public final static String getCurrentTime() {
		return dateFormat.format(new java.util.Date());
	}

	public final static String formattedDouble(double d) {
		String dS = "" + d;
		return dS.length() > 6 ? dS.substring(0, 6) : dS;
	}

	public final static int getItemID(Random r) {
		return nonUniformRandom(8191, 1, 100000, r);
	}

	public final static int getCustomerID(Random r) {
		return nonUniformRandom(1023, 1, 3000, r);
	}

	public final static String getLastName(Random r) {
		int num = (int) nonUniformRandom(255, 0, 999, r);
		return nameTokens[num / 100] + nameTokens[(num / 10) % 10] + nameTokens[num % 10];
	}

	public final static int randomNumber(int min, int max, Random r) {
		return (int) (r.nextDouble() * (max - min + 1) + min);
	}

	public final static int nonUniformRandom(int x, int min, int max, Random r) {
		int ret = (((randomNumber(0, x, r) | randomNumber(min, max, r)) + randomNumber(0, x, r)) % (max - min + 1))
				+ min;
		return ret;
	}

} // end jTPCCUtil
