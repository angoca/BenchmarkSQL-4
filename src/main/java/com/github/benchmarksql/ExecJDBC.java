package com.github.benchmarksql;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.github.benchmarksql.jtpcc.jTPCCUtil;
import com.github.benchmarksql.jtpcc.exceptions.BenchmarkInitException;
import com.github.benchmarksql.jtpcc.exceptions.ExecutionException;

/**
 * Command line program to process SQL DDL statements, from a text input file,
 * to any JDBC Data Source.
 * 
 * @author Denis Lussier - 2004-2014
 */
public class ExecJDBC {

	/**
	 * Main method to execute a SQL file. The name of the file is passed as a JVM
	 * parameter: -Dprop=${1} -DcommandFile=${2}
	 * 
	 * @param args None.
	 */
	public static void main(String[] args) {

		Connection conn = null;
		Statement stmt = null;
		String rLine = null;
		StringBuffer sql = new StringBuffer();

		try {

			Properties ini = new Properties();
			if (System.getProperty("prop") == null) {
				throw new BenchmarkInitException("ERROR: Properties file is invalid.");
			}
			ini.load(new FileInputStream(System.getProperty("prop")));

			// Register jdbcDriver
			Class.forName(ini.getProperty("driver"));

			// make connection
			conn = DriverManager.getConnection(ini.getProperty("conn"), ini.getProperty("user"),
					ini.getProperty("password"));
			conn.setAutoCommit(true);

			// Create Statement
			stmt = conn.createStatement();

			// Statement terminator.
			final String termValue = ini.getProperty("terminator");
			char term;
			if (termValue == null) {
				term = ';';
			} else {
				term = termValue.charAt(0);
			}

			// Open inputFile
			if (jTPCCUtil.getSysProp("commandFile", null) == null) {
				throw new ExecutionException("ERROR: Invalid SQL script.");
			}
			BufferedReader in = new BufferedReader(new FileReader(jTPCCUtil.getSysProp("commandFile", null)));

			// loop thru input file and concatenate SQL statement fragments
			while ((rLine = in.readLine()) != null) {

				String line = rLine.trim();

				if (line.length() != 0) {
					if (line.startsWith("--")) {
						System.out.println(line); // print comment line
					} else {
						sql.append(line);
						if (line.endsWith(Character.toString(term))) {
							execJDBC(stmt, sql, term);
							sql = new StringBuffer();
						} else {
							sql.append("\n");
						}
					}

				} // end if

			} // end while

			in.close();

		} catch (IOException ie) {
			System.out.println(ie.getMessage());

		} catch (SQLException se) {
			System.out.println(se.getMessage());

		} catch (Exception e) {
			e.printStackTrace();

			// exit Cleanly
		} finally {
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			} // end finally

		} // end try

	} // end main

	static void execJDBC(Statement stmt, StringBuffer sql, char term) {

		System.out.println(sql);

		try {

			stmt.execute(sql.toString().replace(term, ' '));

		} catch (SQLException se) {
			System.out.println(se.getMessage());
		} // end try

	} // end execJDBCCommand

} // end ExecJDBC Class
