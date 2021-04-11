package com.github.benchmarksql;
/*
 * Copyright (C) 2004-2014, Denis Lussier
 *
 * LoadData - Load Sample Data directly into database tables or create CSV files for
 *            each table that can then be bulk loaded (again & again & again ...)  :-)
 *
 *    Two optional parameter sets for the command line:
 *
 *                 numWarehouses=9999
 *
 *                 fileLocation=/temp/csv/
 *
 *    "numWarehouses" defaults to "1" and when "fileLocation" is omitted the generated
 *    data is loaded into the database tables directly.
 ***************************************************************************************
 */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;
import java.util.Random;

import com.github.benchmarksql.helper.jdbcIO;
import com.github.benchmarksql.jtpcc.jTPCCConfig;
import com.github.benchmarksql.jtpcc.jTPCCUtil;
import com.github.benchmarksql.jtpcc.pojo.Customer;
import com.github.benchmarksql.jtpcc.pojo.District;
import com.github.benchmarksql.jtpcc.pojo.History;
import com.github.benchmarksql.jtpcc.pojo.Item;
import com.github.benchmarksql.jtpcc.pojo.NewOrder;
import com.github.benchmarksql.jtpcc.pojo.Oorder;
import com.github.benchmarksql.jtpcc.pojo.OrderLine;
import com.github.benchmarksql.jtpcc.pojo.Stock;
import com.github.benchmarksql.jtpcc.pojo.Warehouse;

/**
 * Load Sample Data directly into database tables or create CSV files for each
 * table that can then be bulk loaded (again & again & again ...) :-)
 *
 * Two optional parameter sets for the command line:
 *
 * * numWarehouses=9999 * fileLocation=/temp/csv/
 *
 * "numWarehouses" defaults to "1" and when "fileLocation" is omitted the
 * generated data is loaded into the database tables directly.
 * 
 * @author Denis Lussier - 2004-2014
 */
public class LoadData implements jTPCCConfig {

	// *********** JDBC specific variables ***********************
	private static Connection conn = null;
	private static java.sql.Timestamp sysdate = null;
	private static PreparedStatement custPrepStmt;
	private static PreparedStatement distPrepStmt;
	private static PreparedStatement histPrepStmt;
	private static PreparedStatement itemPrepStmt;
	private static PreparedStatement nworPrepStmt;
	private static PreparedStatement ordrPrepStmt;
	private static PreparedStatement orlnPrepStmt;
	private static PreparedStatement stckPrepStmt;
	private static PreparedStatement whsePrepStmt;

	// ********** general vars **********************************
	private static java.util.Date now = null;
	private static java.util.Date startDate = null;
	private static java.util.Date endDate = null;

	private static Random gen;
	private static int numWarehouses = 0;
	private static String fileLocation = "";
	private static boolean outputFiles = false;
	private static PrintWriter out = null;
	private static long lastTimeMS = 0;

	/**
	 * Main method to generate the data in the database. The database properties
	 * file is given as a JVM parameter: -Dprop=${1}.
	 * 
	 * @param args None.
	 */
	public static void main(String[] args) {

		System.out.println("Starting BenchmarkSQL LoadData");

		System.out.println("----------------- Initialization -------------------");

		numWarehouses = configWhseCount;
		for (int i = 0; i < args.length; i++) {
			System.out.println(args[i]);
			String str = args[i];
			if (str.toLowerCase().startsWith("numwarehouses")) {
				String val = args[i + 1];
				numWarehouses = Integer.parseInt(val);
			}

			if (str.toLowerCase().startsWith("filelocation")) {
				fileLocation = args[i + 1];
				outputFiles = true;
			}
		}

		if (outputFiles == false) {
			initJDBC();
		}

		// seed the random number generator
		gen = new Random(System.currentTimeMillis());

		// ######################### MAINLINE ######################################
		startDate = new java.util.Date();
		System.out.println("");
		System.out.println("------------- LoadData StartTime = " + startDate + "-------------");

		long startTimeMS = new java.util.Date().getTime();
		lastTimeMS = startTimeMS;

		System.out.println("");
		long totalRows = loadWhse(numWarehouses);
		System.out.println("");
		totalRows += loadItem(configItemCount);
		System.out.println("");
		totalRows += loadStock(numWarehouses, configItemCount);
		System.out.println("");
		totalRows += loadDist(numWarehouses, configDistPerWhse);
		System.out.println("");
		totalRows += loadCust(numWarehouses, configDistPerWhse, configCustPerDist);
		System.out.println("");
		totalRows += loadOrder(numWarehouses, configDistPerWhse, configCustPerDist);

		long runTimeMS = (new java.util.Date().getTime()) + 1 - startTimeMS;
		endDate = new java.util.Date();
		System.out.println("");
		System.out.println("------------- LoadJDBC Statistics --------------------");
		System.out.println("     Start Time = " + startDate);
		System.out.println("       End Time = " + endDate);
		System.out.println("       Run Time = " + (int) runTimeMS / 1000 + " Seconds");
		System.out.println("    Rows Loaded = " + totalRows + " Rows");
		System.out.println("Rows Per Second = " + (totalRows / (runTimeMS / 1000)) + " Rows/Sec");
		System.out.println("------------------------------------------------------");

		// exit Cleanly
		try {
			if (outputFiles == false) {
				if (conn != null)
					conn.close();
			}
		} catch (SQLException se) {
			se.printStackTrace();
		} // end try

	} // end main

	static void transRollback() {
		if (outputFiles == false) {
			try {
				conn.rollback();
			} catch (SQLException se) {
				System.out.println(se.getMessage());
			}
		} else {
			out.close();
		}
	}

	static void transCommit() {
		if (outputFiles == false) {
			try {
				conn.commit();
			} catch (SQLException se) {
				System.out.println(se.getMessage());
				transRollback();
			}
		} else {
			out.close();
		}
	}

	static void initJDBC() {

		try {

			// load the ini file
			Properties ini = new Properties();
			ini.load(new FileInputStream(System.getProperty("prop")));

			// display the values we need
			System.out.println("driver=" + ini.getProperty("driver"));
			System.out.println("conn=" + ini.getProperty("conn"));
			System.out.println("user=" + ini.getProperty("user"));
			System.out.println("password=******");
			String schema = ini.getProperty("schema");
			if (schema == null) {
				schema = "benchmarksql.";
			} else {
				schema += ".";
			}

			// Register jdbcDriver
			Class.forName(ini.getProperty("driver"));

			// make connection
			conn = DriverManager.getConnection(ini.getProperty("conn"), ini.getProperty("user"),
					ini.getProperty("password"));
			conn.setAutoCommit(false);

			conn.createStatement();

			distPrepStmt = conn.prepareStatement("INSERT INTO " + schema + "district "
					+ " (d_id, d_w_id, d_ytd, d_tax, d_next_o_id, d_name, d_street_1, d_street_2, d_city, d_state, d_zip) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			itemPrepStmt = conn.prepareStatement("INSERT INTO " + schema + "item "
					+ " (i_id, i_name, i_price, i_data, i_im_id) " + "VALUES (?, ?, ?, ?, ?)");

			custPrepStmt = conn.prepareStatement("INSERT INTO " + schema + "customer " + " (c_id, c_d_id, c_w_id, "
					+ "c_discount, c_credit, c_last, c_first, c_credit_lim, "
					+ "c_balance, c_ytd_payment, c_payment_cnt, c_delivery_cnt, "
					+ "c_street_1, c_street_2, c_city, c_state, c_zip, " + "c_phone, c_since, c_middle, c_data) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			histPrepStmt = conn
					.prepareStatement("INSERT INTO " + schema + "history " + " (hist_id, h_c_id, h_c_d_id, h_c_w_id, "
							+ "h_d_id, h_w_id, " + "h_date, h_amount, h_data) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

			ordrPrepStmt = conn
					.prepareStatement("INSERT INTO " + schema + "oorder " + " (o_id, o_w_id,  o_d_id, o_c_id, "
							+ "o_carrier_id, o_ol_cnt, o_all_local, o_entry_d) " + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");

			orlnPrepStmt = conn.prepareStatement("INSERT INTO " + schema + "order_line "
					+ " (ol_w_id, ol_d_id, ol_o_id, " + "ol_number, ol_i_id, ol_delivery_d, "
					+ "ol_amount, ol_supply_w_id, ol_quantity, ol_dist_info) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			nworPrepStmt = conn.prepareStatement(
					"INSERT INTO " + schema + "new_order " + " (no_w_id, no_d_id, no_o_id) " + "VALUES (?, ?, ?)");

			stckPrepStmt = conn.prepareStatement("INSERT INTO " + schema + "stock "
					+ " (s_i_id, s_w_id, s_quantity, s_ytd, s_order_cnt, s_remote_cnt, s_data, "
					+ "s_dist_01, s_dist_02, s_dist_03, s_dist_04, s_dist_05, "
					+ "s_dist_06, s_dist_07, s_dist_08, s_dist_09, s_dist_10) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			whsePrepStmt = conn.prepareStatement("INSERT INTO " + schema + "warehouse "
					+ " (w_id, w_ytd, w_tax, w_name, w_street_1, w_street_2, w_city, w_state, w_zip) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");

		} catch (SQLException se) {
			System.out.println(se.getMessage());
			transRollback();

		} catch (Exception e) {
			e.printStackTrace();
			transRollback();

		} // end try

	} // end initJDBC()

	static int loadItem(int itemKount) {

		int k = 0;
		int t = 0;
		int randPct = 0;
		int len = 0;
		int startORIGINAL = 0;

		try {

			now = new java.util.Date();
			t = itemKount;
			System.out.println("Start Item Load for " + t + " Items @ " + now + " ...");

			if (outputFiles == true) {
				out = new PrintWriter(new FileOutputStream(fileLocation + "item.csv"));
				System.out.println("Writing Item file to: " + fileLocation + "item.csv");
			}

			Item item = new Item();

			for (int i = 1; i <= itemKount; i++) {

				item.setI_id(i);
				item.setI_name(jTPCCUtil.randomStr(jTPCCUtil.randomNumber(14, 24, gen)));
				item.setI_price((float) (jTPCCUtil.randomNumber(100, 10000, gen) / 100.0));

				// i_data
				randPct = jTPCCUtil.randomNumber(1, 100, gen);
				len = jTPCCUtil.randomNumber(26, 50, gen);
				if (randPct > 10) {
					// 90% of time i_data isa random string of length [26 .. 50]
					item.setI_data(jTPCCUtil.randomStr(len));
				} else {
					// 10% of time i_data has "ORIGINAL" crammed somewhere in middle
					startORIGINAL = jTPCCUtil.randomNumber(2, (len - 8), gen);
					item.setI_data(jTPCCUtil.randomStr(startORIGINAL - 1) + "ORIGINAL"
							+ jTPCCUtil.randomStr(len - startORIGINAL - 9));
				}

				item.setI_im_id(jTPCCUtil.randomNumber(1, 10000, gen));

				k++;

				if (outputFiles == false) {
					itemPrepStmt.setLong(1, item.getI_id());
					itemPrepStmt.setString(2, item.getI_name());
					itemPrepStmt.setDouble(3, item.getI_price());
					itemPrepStmt.setString(4, item.getI_data());
					itemPrepStmt.setLong(5, item.getI_im_id());
					itemPrepStmt.addBatch();

					if ((k % configCommitCount) == 0) {
						long tmpTime = new java.util.Date().getTime();
						String etStr = "  Elasped Time(ms): " + ((tmpTime - lastTimeMS) / 1000.000)
								+ "                    ";
						System.out.println(etStr.substring(0, 30) + "  Writing record " + k + " of " + t);
						lastTimeMS = tmpTime;
						itemPrepStmt.executeBatch();
						itemPrepStmt.clearBatch();
						transCommit();
					}
				} else {
					String str = "";
					str = str + item.getI_id() + ",";
					str = str + item.getI_name() + ",";
					str = str + item.getI_price() + ",";
					str = str + item.getI_data() + ",";
					str = str + item.getI_im_id();
					out.println(str);

					if ((k % configCommitCount) == 0) {
						long tmpTime = new java.util.Date().getTime();
						String etStr = "  Elasped Time(ms): " + ((tmpTime - lastTimeMS) / 1000.000)
								+ "                    ";
						System.out.println(etStr.substring(0, 30) + "  Writing record " + k + " of " + t);
						lastTimeMS = tmpTime;
					}
				}

			} // end for

			long tmpTime = new java.util.Date().getTime();
			String etStr = "  Elasped Time(ms): " + ((tmpTime - lastTimeMS) / 1000.000) + "                    ";
			System.out.println(etStr.substring(0, 30) + "  Writing final records " + k + " of " + t);
			lastTimeMS = tmpTime;

			if (outputFiles == false) {
				itemPrepStmt.executeBatch();
			}
			transCommit();
			now = new java.util.Date();
			System.out.println("End Item Load @  " + now);

		} catch (SQLException se) {
			System.out.println(se.getMessage());
			transRollback();
		} catch (Exception e) {
			e.printStackTrace();
			transRollback();
		}

		return (k);

	} // end loadItem()

	static int loadWhse(int whseKount) {

		try {

			now = new java.util.Date();
			System.out.println("Start Whse Load for " + whseKount + " Whses @ " + now + " ...");

			if (outputFiles == true) {
				out = new PrintWriter(new FileOutputStream(fileLocation + "warehouse.csv"));
				System.out.println("Writing Warehouse file to: " + fileLocation + "warehouse.csv");
			}

			Warehouse warehouse = new Warehouse();
			for (int i = 1; i <= whseKount; i++) {

				warehouse.setW_id(i);
				warehouse.setW_ytd(300000);

				// random within [0.0000 .. 0.2000]
				warehouse.setW_tax((float) ((jTPCCUtil.randomNumber(0, 2000, gen)) / 10000.0));

				warehouse.setW_name(jTPCCUtil.randomStr(jTPCCUtil.randomNumber(6, 10, gen)));
				warehouse.setW_street_1(jTPCCUtil.randomStr(jTPCCUtil.randomNumber(10, 20, gen)));
				warehouse.setW_street_2(jTPCCUtil.randomStr(jTPCCUtil.randomNumber(10, 20, gen)));
				warehouse.setW_city(jTPCCUtil.randomStr(jTPCCUtil.randomNumber(10, 20, gen)));
				warehouse.setW_state(jTPCCUtil.randomStr(3).toUpperCase());
				warehouse.setW_zip("123456789");

				if (outputFiles == false) {
					whsePrepStmt.setLong(1, warehouse.getW_id());
					whsePrepStmt.setDouble(2, warehouse.getW_ytd());
					whsePrepStmt.setDouble(3, warehouse.getW_tax());
					whsePrepStmt.setString(4, warehouse.getW_name());
					whsePrepStmt.setString(5, warehouse.getW_street_1());
					whsePrepStmt.setString(6, warehouse.getW_street_2());
					whsePrepStmt.setString(7, warehouse.getW_city());
					whsePrepStmt.setString(8, warehouse.getW_state());
					whsePrepStmt.setString(9, warehouse.getW_zip());
					whsePrepStmt.executeUpdate();
				} else {
					String str = "";
					str = str + warehouse.getW_id() + ",";
					str = str + warehouse.getW_ytd() + ",";
					str = str + warehouse.getW_tax() + ",";
					str = str + warehouse.getW_name() + ",";
					str = str + warehouse.getW_street_1() + ",";
					str = str + warehouse.getW_street_2() + ",";
					str = str + warehouse.getW_city() + ",";
					str = str + warehouse.getW_state() + ",";
					str = str + warehouse.getW_zip();
					out.println(str);
				}

			} // end for

			transCommit();
			now = new java.util.Date();

			long tmpTime = new java.util.Date().getTime();
			System.out.println("Elasped Time(ms): " + ((tmpTime - lastTimeMS) / 1000.000));
			lastTimeMS = tmpTime;
			System.out.println("End Whse Load @  " + now);

		} catch (SQLException se) {
			System.out.println(se.getMessage());
			transRollback();
		} catch (Exception e) {
			e.printStackTrace();
			transRollback();
		}

		return (whseKount);

	} // end loadWhse()

	static int loadStock(int whseKount, int itemKount) {

		int k = 0;
		int t = 0;
		int randPct = 0;
		int len = 0;
		int startORIGINAL = 0;

		try {

			now = new java.util.Date();
			t = (whseKount * itemKount);
			System.out.println("Start Stock Load for " + t + " units @ " + now + " ...");

			if (outputFiles == true) {
				out = new PrintWriter(new FileOutputStream(fileLocation + "stock.csv"));
				System.out.println("Writing Stock file to: " + fileLocation + "stock.csv");
			}

			Stock stock = new Stock();

			for (int i = 1; i <= itemKount; i++) {

				for (int w = 1; w <= whseKount; w++) {

					stock.setS_i_id(i);
					stock.setS_w_id(w);
					stock.setS_quantity(jTPCCUtil.randomNumber(10, 100, gen));
					stock.setS_ytd(0);
					stock.setS_order_cnt(0);
					stock.setS_remote_cnt(0);

					// s_data
					randPct = jTPCCUtil.randomNumber(1, 100, gen);
					len = jTPCCUtil.randomNumber(26, 50, gen);
					if (randPct > 10) {
						// 90% of time i_data isa random string of length [26 .. 50]
						stock.setS_data(jTPCCUtil.randomStr(len));
					} else {
						// 10% of time i_data has "ORIGINAL" crammed somewhere in middle
						startORIGINAL = jTPCCUtil.randomNumber(2, (len - 8), gen);
						stock.setS_data(jTPCCUtil.randomStr(startORIGINAL - 1) + "ORIGINAL"
								+ jTPCCUtil.randomStr(len - startORIGINAL - 9));
					}

					stock.setS_dist_01(jTPCCUtil.randomStr(24));
					stock.setS_dist_02(jTPCCUtil.randomStr(24));
					stock.setS_dist_03(jTPCCUtil.randomStr(24));
					stock.setS_dist_04(jTPCCUtil.randomStr(24));
					stock.setS_dist_05(jTPCCUtil.randomStr(24));
					stock.setS_dist_06(jTPCCUtil.randomStr(24));
					stock.setS_dist_07(jTPCCUtil.randomStr(24));
					stock.setS_dist_08(jTPCCUtil.randomStr(24));
					stock.setS_dist_09(jTPCCUtil.randomStr(24));
					stock.setS_dist_10(jTPCCUtil.randomStr(24));

					k++;
					if (outputFiles == false) {
						stckPrepStmt.setLong(1, stock.getS_i_id());
						stckPrepStmt.setLong(2, stock.getS_w_id());
						stckPrepStmt.setDouble(3, stock.getS_quantity());
						stckPrepStmt.setDouble(4, stock.getS_ytd());
						stckPrepStmt.setLong(5, stock.getS_order_cnt());
						stckPrepStmt.setLong(6, stock.getS_remote_cnt());
						stckPrepStmt.setString(7, stock.getS_data());
						stckPrepStmt.setString(8, stock.getS_dist_01());
						stckPrepStmt.setString(9, stock.getS_dist_02());
						stckPrepStmt.setString(10, stock.getS_dist_03());
						stckPrepStmt.setString(11, stock.getS_dist_04());
						stckPrepStmt.setString(12, stock.getS_dist_05());
						stckPrepStmt.setString(13, stock.getS_dist_06());
						stckPrepStmt.setString(14, stock.getS_dist_07());
						stckPrepStmt.setString(15, stock.getS_dist_08());
						stckPrepStmt.setString(16, stock.getS_dist_09());
						stckPrepStmt.setString(17, stock.getS_dist_10());
						stckPrepStmt.addBatch();
						if ((k % configCommitCount) == 0) {
							long tmpTime = new java.util.Date().getTime();
							String etStr = "  Elasped Time(ms): " + ((tmpTime - lastTimeMS) / 1000.000)
									+ "                    ";
							System.out.println(etStr.substring(0, 30) + "  Writing record " + k + " of " + t);
							lastTimeMS = tmpTime;
							stckPrepStmt.executeBatch();
							stckPrepStmt.clearBatch();
							transCommit();
						}
					} else {
						String str = "";
						str = str + stock.getS_i_id() + ",";
						str = str + stock.getS_w_id() + ",";
						str = str + stock.getS_quantity() + ",";
						str = str + stock.getS_ytd() + ",";
						str = str + stock.getS_order_cnt() + ",";
						str = str + stock.getS_remote_cnt() + ",";
						str = str + stock.getS_data() + ",";
						str = str + stock.getS_dist_01() + ",";
						str = str + stock.getS_dist_02() + ",";
						str = str + stock.getS_dist_03() + ",";
						str = str + stock.getS_dist_04() + ",";
						str = str + stock.getS_dist_05() + ",";
						str = str + stock.getS_dist_06() + ",";
						str = str + stock.getS_dist_07() + ",";
						str = str + stock.getS_dist_08() + ",";
						str = str + stock.getS_dist_09() + ",";
						str = str + stock.getS_dist_10();
						out.println(str);

						if ((k % configCommitCount) == 0) {
							long tmpTime = new java.util.Date().getTime();
							String etStr = "  Elasped Time(ms): " + ((tmpTime - lastTimeMS) / 1000.000)
									+ "                    ";
							System.out.println(etStr.substring(0, 30) + "  Writing record " + k + " of " + t);
							lastTimeMS = tmpTime;
						}
					}

				} // end for [w]

			} // end for [i]

			long tmpTime = new java.util.Date().getTime();
			String etStr = "  Elasped Time(ms): " + ((tmpTime - lastTimeMS) / 1000.000) + "                    ";
			System.out.println(etStr.substring(0, 30) + "  Writing final records " + k + " of " + t);
			lastTimeMS = tmpTime;
			if (outputFiles == false) {
				stckPrepStmt.executeBatch();
			}
			transCommit();

			now = new java.util.Date();
			System.out.println("End Stock Load @  " + now);

		} catch (SQLException se) {
			System.out.println(se.getMessage());
			transRollback();

		} catch (Exception e) {
			e.printStackTrace();
			transRollback();
		}

		return (k);

	} // end loadStock()

	static int loadDist(int whseKount, int distWhseKount) {

		int k = 0;
		int t = 0;

		try {

			now = new java.util.Date();

			if (outputFiles == true) {
				out = new PrintWriter(new FileOutputStream(fileLocation + "district.csv"));
				System.out.println("Writing District file to: " + fileLocation + "district.csv");
			}

			District district = new District();

			t = (whseKount * distWhseKount);
			System.out.println("Start District Data for " + t + " Dists @ " + now + " ...");

			for (int w = 1; w <= whseKount; w++) {

				for (int d = 1; d <= distWhseKount; d++) {

					district.setD_id(d);
					district.setD_w_id(w);
					district.setD_ytd(30000);

					// random within [0.0000 .. 0.2000]
					district.setD_tax((float) ((jTPCCUtil.randomNumber(0, 2000, gen)) / 10000.0));

					district.setD_next_o_id(3001);
					district.setD_name(jTPCCUtil.randomStr(jTPCCUtil.randomNumber(6, 10, gen)));
					district.setD_street_1(jTPCCUtil.randomStr(jTPCCUtil.randomNumber(10, 20, gen)));
					district.setD_street_2(jTPCCUtil.randomStr(jTPCCUtil.randomNumber(10, 20, gen)));
					district.setD_city(jTPCCUtil.randomStr(jTPCCUtil.randomNumber(10, 20, gen)));
					district.setD_state(jTPCCUtil.randomStr(3).toUpperCase());
					district.setD_zip("123456789");

					k++;
					if (outputFiles == false) {
						distPrepStmt.setLong(1, district.getD_id());
						distPrepStmt.setLong(2, district.getD_w_id());
						distPrepStmt.setDouble(3, district.getD_ytd());
						distPrepStmt.setDouble(4, district.getD_tax());
						distPrepStmt.setLong(5, district.getD_next_o_id());
						distPrepStmt.setString(6, district.getD_name());
						distPrepStmt.setString(7, district.getD_street_1());
						distPrepStmt.setString(8, district.getD_street_2());
						distPrepStmt.setString(9, district.getD_city());
						distPrepStmt.setString(10, district.getD_state());
						distPrepStmt.setString(11, district.getD_zip());
						distPrepStmt.executeUpdate();
					} else {
						String str = "";
						str = str + district.getD_id() + ",";
						str = str + district.getD_w_id() + ",";
						str = str + district.getD_ytd() + ",";
						str = str + district.getD_tax() + ",";
						str = str + district.getD_next_o_id() + ",";
						str = str + district.getD_name() + ",";
						str = str + district.getD_street_1() + ",";
						str = str + district.getD_street_2() + ",";
						str = str + district.getD_city() + ",";
						str = str + district.getD_state() + ",";
						str = str + district.getD_zip();
						out.println(str);
					}

				} // end for [d]

			} // end for [w]

			long tmpTime = new java.util.Date().getTime();
			String etStr = "  Elasped Time(ms): " + ((tmpTime - lastTimeMS) / 1000.000) + "                    ";
			System.out.println(etStr.substring(0, 30) + "  Writing record " + k + " of " + t);
			lastTimeMS = tmpTime;
			transCommit();
			now = new java.util.Date();
			System.out.println("End District Load @  " + now);

		} catch (SQLException se) {
			System.out.println(se.getMessage());
			transRollback();
		} catch (Exception e) {
			e.printStackTrace();
			transRollback();
		}

		return (k);

	} // end loadDist()

	static int loadCust(int whseKount, int distWhseKount, int custDistKount) {

		int k = 0;
		int t = 0;
		int i = 1;
		Customer customer = new Customer();
		History history = new History();
		PrintWriter outHist = null;

		try {

			now = new java.util.Date();

			if (outputFiles == true) {
				out = new PrintWriter(new FileOutputStream(fileLocation + "customer.csv"));
				// TODO convert into log4j
				System.out.println("Writing Customer file to: " + fileLocation + "customer.csv");
				outHist = new PrintWriter(new FileOutputStream(fileLocation + "cust-hist.csv"));
				System.out.println("Writing Customer History file to: " + fileLocation + "cust-hist.csv");
			}

			t = (whseKount * distWhseKount * custDistKount * 2);
			System.out.println("Start Cust-Hist Load for " + t + " Cust-Hists @ " + now + " ...");

			for (int w = 1; w <= whseKount; w++) {

				for (int d = 1; d <= distWhseKount; d++) {

					for (int c = 1; c <= custDistKount; c++) {

						sysdate = new java.sql.Timestamp(System.currentTimeMillis());

						customer.setC_id(c);
						customer.setC_d_id(d);
						customer.setC_w_id(w);

						// discount is random between [0.0000 ... 0.5000]
						customer.setC_discount((float) (jTPCCUtil.randomNumber(1, 5000, gen) / 10000.0));

						if (jTPCCUtil.randomNumber(1, 100, gen) <= 90) {
							customer.setC_credit("BC"); // 10% Bad Credit
						} else {
							customer.setC_credit("GC"); // 90% Good Credit
						}
						// customer.c_credit = "GC";

						customer.setC_last(jTPCCUtil.getLastName(gen));
						customer.setC_first(jTPCCUtil.randomStr(jTPCCUtil.randomNumber(8, 16, gen)));
						customer.setC_credit_lim(50000);

						customer.setC_balance(-10);
						customer.setC_ytd_payment(10);
						customer.setC_payment_cnt(1);
						customer.setC_delivery_cnt(0);

						customer.setC_street_1(jTPCCUtil.randomStr(jTPCCUtil.randomNumber(10, 20, gen)));
						customer.setC_street_2(jTPCCUtil.randomStr(jTPCCUtil.randomNumber(10, 20, gen)));
						customer.setC_city(jTPCCUtil.randomStr(jTPCCUtil.randomNumber(10, 20, gen)));
						customer.setC_state(jTPCCUtil.randomStr(3).toUpperCase());
						customer.setC_zip("123456789");

						customer.setC_phone("(732)744-1700");

						customer.setC_since(sysdate.getTime());
						customer.setC_middle("OE");
						customer.setC_data(jTPCCUtil.randomStr(jTPCCUtil.randomNumber(300, 500, gen)));

						history.setHist_id(i);
						i++;
						history.setH_c_id(c);
						history.setH_c_d_id(d);
						history.setH_c_w_id(w);
						history.setH_d_id(d);
						history.setH_w_id(w);
						history.setH_date(sysdate.getTime());
						history.setH_amount(10);
						history.setH_data(jTPCCUtil.randomStr(jTPCCUtil.randomNumber(10, 24, gen)));

						k = k + 2;
						if (outputFiles == false) {
							custPrepStmt.setLong(1, customer.getC_id());
							custPrepStmt.setLong(2, customer.getC_d_id());
							custPrepStmt.setLong(3, customer.getC_w_id());
							custPrepStmt.setDouble(4, customer.getC_discount());
							custPrepStmt.setString(5, customer.getC_credit());
							custPrepStmt.setString(6, customer.getC_last());
							custPrepStmt.setString(7, customer.getC_first());
							custPrepStmt.setDouble(8, customer.getC_credit_lim());
							custPrepStmt.setDouble(9, customer.getC_balance());
							custPrepStmt.setDouble(10, customer.getC_ytd_payment());
							custPrepStmt.setDouble(11, customer.getC_payment_cnt());
							custPrepStmt.setDouble(12, customer.getC_delivery_cnt());
							custPrepStmt.setString(13, customer.getC_street_1());
							custPrepStmt.setString(14, customer.getC_street_2());
							custPrepStmt.setString(15, customer.getC_city());
							custPrepStmt.setString(16, customer.getC_state());
							custPrepStmt.setString(17, customer.getC_zip());
							custPrepStmt.setString(18, customer.getC_phone());

							Timestamp since = new Timestamp(customer.getC_since());
							custPrepStmt.setTimestamp(19, since);
							custPrepStmt.setString(20, customer.getC_middle());
							custPrepStmt.setString(21, customer.getC_data());

							custPrepStmt.addBatch();

							histPrepStmt.setInt(1, history.getHist_id());
							histPrepStmt.setInt(2, history.getH_c_id());
							histPrepStmt.setInt(3, history.getH_c_d_id());
							histPrepStmt.setInt(4, history.getH_c_w_id());

							histPrepStmt.setInt(5, history.getH_d_id());
							histPrepStmt.setInt(6, history.getH_w_id());
							Timestamp hdate = new Timestamp(history.getH_date());
							histPrepStmt.setTimestamp(7, hdate);
							histPrepStmt.setDouble(8, history.getH_amount());
							histPrepStmt.setString(9, history.getH_data());

							histPrepStmt.addBatch();

							if ((k % configCommitCount) == 0) {
								long tmpTime = new java.util.Date().getTime();
								String etStr = "  Elasped Time(ms): " + ((tmpTime - lastTimeMS) / 1000.000)
										+ "                    ";
								System.out.println(etStr.substring(0, 30) + "  Writing record " + k + " of " + t);
								lastTimeMS = tmpTime;

								custPrepStmt.executeBatch();
								histPrepStmt.executeBatch();
								custPrepStmt.clearBatch();
								custPrepStmt.clearBatch();
								transCommit();
							}
						} else {
							String str = "";
							str = str + customer.getC_id() + ",";
							str = str + customer.getC_d_id() + ",";
							str = str + customer.getC_w_id() + ",";
							str = str + customer.getC_discount() + ",";
							str = str + customer.getC_credit() + ",";
							str = str + customer.getC_last() + ",";
							str = str + customer.getC_first() + ",";
							str = str + customer.getC_credit_lim() + ",";
							str = str + customer.getC_balance() + ",";
							str = str + customer.getC_ytd_payment() + ",";
							str = str + customer.getC_payment_cnt() + ",";
							str = str + customer.getC_delivery_cnt() + ",";
							str = str + customer.getC_street_1() + ",";
							str = str + customer.getC_street_2() + ",";
							str = str + customer.getC_city() + ",";
							str = str + customer.getC_state() + ",";
							str = str + customer.getC_zip() + ",";
							str = str + customer.getC_phone() + ",";
							Timestamp since = new Timestamp(customer.getC_since());
							str = str + since + ",";
							str = str + customer.getC_middle() + ",";
							str = str + customer.getC_data();
							out.println(str);

							str = "";
							str = str + history.getHist_id() + ",";
							str = str + history.getH_c_id() + ",";
							str = str + history.getH_c_d_id() + ",";
							str = str + history.getH_c_w_id() + ",";
							str = str + history.getH_d_id() + ",";
							str = str + history.getH_w_id() + ",";
							Timestamp hdate = new Timestamp(history.getH_date());
							str = str + hdate + ",";
							str = str + history.getH_amount() + ",";
							str = str + history.getH_data();
							outHist.println(str);

							if ((k % configCommitCount) == 0) {
								long tmpTime = new java.util.Date().getTime();
								String etStr = "  Elasped Time(ms): " + ((tmpTime - lastTimeMS) / 1000.000)
										+ "                    ";
								System.out.println(etStr.substring(0, 30) + "  Writing record " + k + " of " + t);
								lastTimeMS = tmpTime;

							}
						}

					} // end for [c]

				} // end for [d]

			} // end for [w]

			long tmpTime = new java.util.Date().getTime();
			String etStr = "  Elasped Time(ms): " + ((tmpTime - lastTimeMS) / 1000.000) + "                    ";
			System.out.println(etStr.substring(0, 30) + "  Writing record " + k + " of " + t);
			lastTimeMS = tmpTime;
			if (outputFiles == true) {
				out.close();
				outHist.close();
			} else {
				custPrepStmt.executeBatch();
				histPrepStmt.executeBatch();
				transCommit();
			}

			now = new java.util.Date();
			System.out.println("End Cust-Hist Data Load @  " + now);

		} catch (SQLException se) {
			System.out.println(se.getMessage());
			transRollback();
			if (outputFiles == true) {
				out.close();
				outHist.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			transRollback();
			if (outputFiles == true) {
				out.close();
				outHist.close();
			}
		}

		return (k);

	} // end loadCust()

	static int loadOrder(int whseKount, int distWhseKount, int custDistKount) {

		int k = 0;
		int t = 0;
		PrintWriter outO = null;
		PrintWriter outLine = null;
		PrintWriter outNewOrder = null;

		try {

			if (outputFiles == true) {
				outO = new PrintWriter(new FileOutputStream(fileLocation + "order.csv"));
				System.out.println("Writing Order file to: " + fileLocation + "order.csv");
				outLine = new PrintWriter(new FileOutputStream(fileLocation + "order-line.csv"));
				System.out.println("Writing OrderLine file to: " + fileLocation + "order-line.csv");
				outNewOrder = new PrintWriter(new FileOutputStream(fileLocation + "new-order.csv"));
				System.out.println("Writing NewOrder file to: " + fileLocation + "new-order.csv");
			}

			now = new java.util.Date();
			Oorder oorder = new Oorder();
			NewOrder new_order = new NewOrder();
			OrderLine order_line = new OrderLine();
			jdbcIO myJdbcIO = new jdbcIO();

			t = (whseKount * distWhseKount * custDistKount);
			t = (t * 11) + (t / 3);
			System.out.println("whse=" + whseKount + ", dist=" + distWhseKount + ", cust=" + custDistKount);
			System.out.println("Start Order-Line-New Load for approx " + t + " rows @ " + now + " ...");

			for (int w = 1; w <= whseKount; w++) {

				for (int d = 1; d <= distWhseKount; d++) {

					for (int c = 1; c <= custDistKount; c++) {

						oorder.setO_id(c);
						oorder.setO_w_id(w);
						oorder.setO_d_id(d);
						oorder.setO_c_id(jTPCCUtil.randomNumber(1, custDistKount, gen));
						oorder.setO_carrier_id(jTPCCUtil.randomNumber(1, 10, gen));
						oorder.setO_ol_cnt(jTPCCUtil.randomNumber(5, 15, gen));
						oorder.setO_all_local(1);
						oorder.setO_entry_d(System.currentTimeMillis());

						k++;
						if (outputFiles == false) {
							myJdbcIO.insertOrder(ordrPrepStmt, oorder);
						} else {
							String str = "";
							str = str + oorder.getO_id() + ",";
							str = str + oorder.getO_w_id() + ",";
							str = str + oorder.getO_d_id() + ",";
							str = str + oorder.getO_c_id() + ",";
							str = str + oorder.getO_carrier_id() + ",";
							str = str + oorder.getO_ol_cnt() + ",";
							str = str + oorder.getO_all_local() + ",";
							Timestamp entry_d = new java.sql.Timestamp(oorder.getO_entry_d());
							str = str + entry_d;
							outO.println(str);
						}

						// 900 rows in the NEW-ORDER table corresponding to the last
						// 900 rows in the ORDER table for that district (i.e., with
						// NO_O_ID between 2,101 and 3,000)

						if (c > 2100) {

							new_order.setNo_w_id(w);
							new_order.setNo_d_id(d);
							new_order.setNo_o_id(c);

							k++;
							if (outputFiles == false) {
								myJdbcIO.insertNewOrder(nworPrepStmt, new_order);
							} else {
								String str = "";
								str = str + new_order.getNo_w_id() + ",";
								str = str + new_order.getNo_d_id() + ",";
								str = str + new_order.getNo_o_id();
								outNewOrder.println(str);
							}

						} // end new order

						for (int l = 1; l <= oorder.getO_ol_cnt(); l++) {

							order_line.setOl_w_id(w);
							order_line.setOl_d_id(d);
							order_line.setOl_o_id(c);
							order_line.setOl_number(l); // ol_number
							order_line.setOl_i_id(jTPCCUtil.randomNumber(1, 100000, gen));
							order_line.setOl_delivery_d(oorder.getO_entry_d());

							if (order_line.getOl_o_id() < 2101) {
								order_line.setOl_amount(0);
							} else {
								// random within [0.01 .. 9,999.99]
								order_line.setOl_amount((float) (jTPCCUtil.randomNumber(1, 999999, gen) / 100.0));
							}

							order_line.setOl_supply_w_id(jTPCCUtil.randomNumber(1, numWarehouses, gen));
							order_line.setOl_quantity(5);
							order_line.setOl_dist_info(jTPCCUtil.randomStr(24));

							k++;
							if (outputFiles == false) {
								myJdbcIO.insertOrderLine(orlnPrepStmt, order_line);
							} else {
								String str = "";
								str = str + order_line.getOl_w_id() + ",";
								str = str + order_line.getOl_d_id() + ",";
								str = str + order_line.getOl_o_id() + ",";
								str = str + order_line.getOl_number() + ",";
								str = str + order_line.getOl_i_id() + ",";
								Timestamp delivery_d = new Timestamp(order_line.getOl_delivery_d());
								str = str + delivery_d + ",";
								str = str + order_line.getOl_amount() + ",";
								str = str + order_line.getOl_supply_w_id() + ",";
								str = str + order_line.getOl_quantity() + ",";
								str = str + order_line.getOl_dist_info();
								outLine.println(str);
							}

							if ((k % configCommitCount) == 0) {
								long tmpTime = new java.util.Date().getTime();
								String etStr = "  Elasped Time(ms): " + ((tmpTime - lastTimeMS) / 1000.000)
										+ "                    ";
								System.out.println(etStr.substring(0, 30) + "  Writing record " + k + " of " + t);
								lastTimeMS = tmpTime;
								if (outputFiles == false) {
									ordrPrepStmt.executeBatch();
									nworPrepStmt.executeBatch();
									orlnPrepStmt.executeBatch();
									ordrPrepStmt.clearBatch();
									nworPrepStmt.clearBatch();
									orlnPrepStmt.clearBatch();
									transCommit();
								}
							}

						} // end for [l]

					} // end for [c]

				} // end for [d]

			} // end for [w]

			System.out.println("  Writing final records " + k + " of " + t);

			if (outputFiles == true) {
				outO.close();
				outLine.close();
				outNewOrder.close();
			} else {
				ordrPrepStmt.executeBatch();
				nworPrepStmt.executeBatch();
				orlnPrepStmt.executeBatch();
				transCommit();
			}
			now = new java.util.Date();
			System.out.println("End Orders Load @  " + now);

		} catch (SQLException se) {
			System.out.println(se.getMessage());
			transRollback();
			if (outputFiles == true) {
				outO.close();
				outLine.close();
				outNewOrder.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			transRollback();
			if (outputFiles == true) {
				outO.close();
				outLine.close();
				outNewOrder.close();
			}
		}

		return (k);

	} // end loadOrder()

} // end LoadData Class
