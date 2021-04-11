package com.github.benchmarksql.jtpcc;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.benchmarksql.jtpcc.exceptions.BenchmarkInitException;
import com.github.benchmarksql.jtpcc.exceptions.ExecutionException;


/**
 * Open Source Java implementation of a TPC-C like benchmark
 * 
 * @author Raul Barbosa - 2003
 * @author Denis Lussier - 2004-2014
 */
public class jTPCC implements jTPCCConfig {
	private static final Logger log = LogManager.getLogger(jTPCC.class);

	private jTPCCTerminal[] terminals;
	private String[] terminalNames;
	private Random random;
	private long terminalsStarted = 0;
	private long transactionCount;

	private long sessionStartTimestamp;
	private long sessionNextTimestamp = 0;
	private long sessionNextKounter = 0;
	private long sessionEndTargetTime = -1;
	private long fastNewOrderCounter;
	private long recentTpmTotal = 0;
	private boolean signalTerminalsRequestEndSent = false;
	private boolean databaseDriverLoaded = false;

	private FileOutputStream fileOutputStream;
	private PrintStream printStreamReport;
	private String sessionStart;
	private String sessionEnd;
	private int limPerMin_Terminal;

	/**
	 * Main method to run a benchmark. The database properties file is given as a
	 * JVM parameter: -Dprop=${1}.
	 * 
	 * @param args None.
	 */
	public static void main(String args[]) {
		new jTPCC();
	}

	private String getProp(Properties p, String pName) {
		String prop = p.getProperty(pName);
		log.warn("Term-00, {}={}", pName, prop);
		return prop;
	}

	public jTPCC() {

		// load the ini file
		Properties ini = new Properties();
		try {
			ini.load(new FileInputStream(System.getProperty("prop")));
		} catch (IOException e) {
			log.error("Term-00, could not load properties file");
		}

		log.info("Term-00, ");
		log.info("Term-00, +-------------------------------------------------------------+");
		log.error("Term-00, BenchmarkSQL v{}", BENCHMARKSQL_VERSION);
		log.info("Term-00, +-------------------------------------------------------------+");
		log.info("Term-00, ");
		String iDriver = getProp(ini, "driver");
		String iConn = getProp(ini, "conn");
		String iUser = getProp(ini, "user");
		String iPassword = ini.getProperty("password");

		log.info("Term-00, ");
		String iWarehouses = getProp(ini, "warehouses");
		String iTerminals = getProp(ini, "terminals");
		String iSchema = getProp(ini, "schema");
		if (iSchema == null) {
			iSchema = "benchmarksql.";
		} else if (iSchema.equals("")) {
			iSchema = "";
		} else {
			iSchema += ".";
		}

		String iRunTxnsPerTerminal = ini.getProperty("runTxnsPerTerminal");
		String iRunMins = ini.getProperty("runMins");
		if (Integer.parseInt(iRunTxnsPerTerminal) == 0 && Integer.parseInt(iRunMins) != 0) {
			log.warn("Term-00, runMins={}", iRunMins);
		} else if (Integer.parseInt(iRunTxnsPerTerminal) != 0 && Integer.parseInt(iRunMins) == 0) {
			log.warn("Term-00, runTxnsPerTerminal={}", iRunTxnsPerTerminal);
		} else {
			log.error("Term-00, Must indicate either transactions per terminal or number of run minutes!");
		}
		;
		String limPerMin = getProp(ini, "limitTxnsPerMin");
		log.info("Term-00, ");
		String iNewOrderWeight = getProp(ini, "newOrderWeight");
		String iPaymentWeight = getProp(ini, "paymentWeight");
		String iOrderStatusWeight = getProp(ini, "orderStatusWeight");
		String iDeliveryWeight = getProp(ini, "deliveryWeight");
		String iStockLevelWeight = getProp(ini, "stockLevelWeight");

		log.info("Term-00, ");

		if (Integer.parseInt(limPerMin) != 0) {
			limPerMin_Terminal = Integer.parseInt(limPerMin) / Integer.parseInt(iTerminals);
		} else {
			limPerMin_Terminal = -1;
		}

		boolean iRunMinsBool = false;

		this.random = new Random(System.currentTimeMillis());

		fastNewOrderCounter = 0;
		updateStatusLine();

		try {
			String driver = iDriver;
			log.warn("Loading database driver: '{}'...", driver);
			Class.forName(iDriver);
			databaseDriverLoaded = true;
		} catch (Exception ex) {
			log.error("Unable to load the database driver!");
			databaseDriverLoaded = false;
		}

		if (databaseDriverLoaded) {
			try {
				boolean limitIsTime = iRunMinsBool;
				int numTerminals = -1, transactionsPerTerminal = -1, numWarehouses = -1;
				int newOrderWeightValue = -1, paymentWeightValue = -1, orderStatusWeightValue = -1,
						deliveryWeightValue = -1, stockLevelWeightValue = -1;
				long executionTimeMillis = -1;

				try {
					if (Integer.parseInt(iRunMins) != 0 && Integer.parseInt(iRunTxnsPerTerminal) == 0) {
						iRunMinsBool = true;
					} else if (Integer.parseInt(iRunMins) == 0 && Integer.parseInt(iRunTxnsPerTerminal) != 0) {
						iRunMinsBool = false;
					} else {
						log.error("Quantity of minutes or quantity of transactions, but not both!");
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e1) {
					log.error("Must indicate either transactions per terminal or number of run minutes!", e1);
					throw new BenchmarkInitException(BenchmarkInitException.MinutesOrQuantity);
				}

				try {
					numWarehouses = Integer.parseInt(iWarehouses);
					if (numWarehouses <= 0) {
						log.error("Quantity of warehouses cannot be negative nor zero!");
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e1) {
					log.error("Invalid number of warehouses!", e1);
					throw new BenchmarkInitException(BenchmarkInitException.NumberOfWHs);
				}

				try {
					numTerminals = Integer.parseInt(iTerminals);
					if (numTerminals <= 0 || numTerminals > 10 * numWarehouses) {
						log.error("Invalid number of terminals per warehouse!");
						throw new NumberFormatException();
					}
				} catch (NumberFormatException e1) {
					log.error("Invalid number of terminals!", e1);
					throw new BenchmarkInitException(BenchmarkInitException.NumberOfTerminals);
				}

				if (Long.parseLong(iRunMins) != 0 && Integer.parseInt(iRunTxnsPerTerminal) == 0) {
					try {
						executionTimeMillis = Long.parseLong(iRunMins) * 60000;
						if (executionTimeMillis <= 0) {
							log.error("Quantity of minutes cannot be negative nor zero!");
							throw new NumberFormatException();
						}
					} catch (NumberFormatException e1) {
						log.error("Invalid number of minutes!", e1);
						throw new BenchmarkInitException(BenchmarkInitException.NumberOfMinutes);
					}
				} else {
					try {
						transactionsPerTerminal = Integer.parseInt(iRunTxnsPerTerminal);
						if (transactionsPerTerminal <= 0) {
							log.error("Quantity of transactions cannot be negative nor zero!");
							throw new NumberFormatException();
						}
					} catch (NumberFormatException e1) {
						log.error("Invalid number of transactions per terminal!", e1);
						throw new BenchmarkInitException(BenchmarkInitException.NumberOfTrxs);
					}
				}

				try {
					newOrderWeightValue = Integer.parseInt(iNewOrderWeight);
					paymentWeightValue = Integer.parseInt(iPaymentWeight);
					orderStatusWeightValue = Integer.parseInt(iOrderStatusWeight);
					deliveryWeightValue = Integer.parseInt(iDeliveryWeight);
					stockLevelWeightValue = Integer.parseInt(iStockLevelWeight);

					if (newOrderWeightValue < 0 || paymentWeightValue < 0 || orderStatusWeightValue < 0
							|| deliveryWeightValue < 0 || stockLevelWeightValue < 0)
						throw new NumberFormatException();
					else if (newOrderWeightValue == 0 && paymentWeightValue == 0 && orderStatusWeightValue == 0
							&& deliveryWeightValue == 0 && stockLevelWeightValue == 0)
						throw new NumberFormatException();
				} catch (NumberFormatException e1) {
					log.error("Invalid number in mix percentage!", e1);
					throw new BenchmarkInitException(BenchmarkInitException.PercentageMix);
				}

				if (newOrderWeightValue + paymentWeightValue + orderStatusWeightValue + deliveryWeightValue
						+ stockLevelWeightValue > 100) {
					log.error("Sum of mix percentage parameters exceeds 100%!");
					throw new BenchmarkInitException(BenchmarkInitException.PctgHigherThan100);
				}

				log.warn("Session started!");
				if (!limitIsTime) {
					log.info("Creating {} terminal(s) with {} transaction(s) per terminal...", numTerminals,
							transactionsPerTerminal);
				} else {
					log.info("Creating {} terminal(s) with {} minute(s) of execution...", numTerminals,
							executionTimeMillis / 60000);
				}
				log.info(
						"Transaction Weights: {}% New-Order, {}% Payment, {}% Order-Status, {}% Delivery, {}% Stock-Level",
						newOrderWeightValue, paymentWeightValue, orderStatusWeightValue, deliveryWeightValue,
						stockLevelWeightValue);

				log.info("Number of Terminals\t{}", numTerminals);

				terminals = new jTPCCTerminal[numTerminals];
				terminalNames = new String[numTerminals];
				terminalsStarted = numTerminals;
				try {
					String database = iConn;
					String username = iUser;
					String password = iPassword;

					int[][] usedTerminals = new int[numWarehouses][10];
					for (int i = 0; i < numWarehouses; i++)
						for (int j = 0; j < 10; j++)
							usedTerminals[i][j] = 0;

					for (int i = 0; i < numTerminals; i++) {
						int terminalWarehouseID;
						int terminalDistrictID;
						do {
							terminalWarehouseID = (int) randomNumber(1, numWarehouses);
							terminalDistrictID = (int) randomNumber(1, 10);
						} while (usedTerminals[terminalWarehouseID - 1][terminalDistrictID - 1] == 1);
						usedTerminals[terminalWarehouseID - 1][terminalDistrictID - 1] = 1;

						String terminalName = "Term-" + (i >= 9 ? "" + (i + 1) : "0" + (i + 1));
						Connection conn = null;
						log.info("Creating database connection for {}...", terminalName);
						conn = DriverManager.getConnection(database, username, password);
						conn.setAutoCommit(false);

						jTPCCTerminal terminal = new jTPCCTerminal(terminalName, terminalWarehouseID,
								terminalDistrictID, conn, transactionsPerTerminal, paymentWeightValue,
								orderStatusWeightValue, deliveryWeightValue, stockLevelWeightValue, numWarehouses,
								limPerMin_Terminal, this, iSchema);

						terminals[i] = terminal;
						terminalNames[i] = terminalName;
						log.info("{}\t{}", terminalName, terminalWarehouseID);
					}

					sessionEndTargetTime = executionTimeMillis;
					signalTerminalsRequestEndSent = false;

					log.info("Transaction\tWeight");
					log.info("% New-Order\t{}", newOrderWeightValue);
					log.info("% Payment\t{}", paymentWeightValue);
					log.info("% Order-Status\t{}", orderStatusWeightValue);
					log.info("% Delivery\t{}", deliveryWeightValue);
					log.info("% Stock-Level\t{}", stockLevelWeightValue);

					log.info("Transaction Number\tTerminal\tType\tExecution Time (ms)\t\tComment");

					log.info("Created {} terminal(s) successfully!", numTerminals);
					boolean dummvar = true;

					// ^Create Terminals, Start Transactions v //

					if (dummvar == true) {
						sessionStart = getCurrentTime();
						sessionStartTimestamp = System.currentTimeMillis();
						sessionNextTimestamp = sessionStartTimestamp;
						if (sessionEndTargetTime != -1)
							sessionEndTargetTime += sessionStartTimestamp;

						synchronized (terminals) {
							log.warn("Starting all terminals...");
							transactionCount = 1;
							for (int i = 0; i < terminals.length; i++)
								(new Thread(terminals[i])).start();

						}

						log.warn("All terminals started executing {}", sessionStart);
					}
				} catch (Exception e1) {
					log.error("This session ended with errors!");
					printStreamReport.close();
					fileOutputStream.close();

					throw new ExecutionException("This session ended with errors!");
				}

			} catch (Exception ex) {
			}
		}
		updateStatusLine();
	}

	private void signalTerminalsRequestEnd(boolean timeTriggered) {
		synchronized (terminals) {
			if (!signalTerminalsRequestEndSent) {
				if (timeTriggered)
					log.warn("The time limit has been reached.");
				log.warn("Signalling all terminals to stop...");
				signalTerminalsRequestEndSent = true;

				for (int i = 0; i < terminals.length; i++)
					if (terminals[i] != null)
						terminals[i].stopRunningWhenPossible();

				log.info("Waiting for all active transactions to end...");
			}
		}
	}

	public void signalTerminalEnded(jTPCCTerminal terminal, long countNewOrdersExecuted) {
		synchronized (terminals) {
			boolean found = false;
			terminalsStarted--;
			for (int i = 0; i < terminals.length && !found; i++) {
				if (terminals[i] == terminal) {
					terminals[i] = null;
					terminalNames[i] = "(" + terminalNames[i] + ")";
					found = true;
				}
			}
		}

		if (terminalsStarted == 0) {
			sessionEnd = getCurrentTime();
			System.currentTimeMillis();
			sessionEndTargetTime = -1;
			log.info("All terminals finished executing {}", sessionEnd);
			endReport();
		}
	}

	public void signalTerminalEndedTransaction(String terminalName, String transactionType, long executionTime,
			String comment, int newOrder) {
		transactionCount++;
		fastNewOrderCounter += newOrder;

		if (sessionEndTargetTime != -1 && System.currentTimeMillis() > sessionEndTargetTime) {
			signalTerminalsRequestEnd(true);
		}

		updateStatusLine();

	}

	private void endReport() {
		long currTimeMillis = System.currentTimeMillis();
		Runtime.getRuntime().freeMemory();
		Runtime.getRuntime().totalMemory();
		double tpmC = (6000000 * fastNewOrderCounter / (currTimeMillis - sessionStartTimestamp)) / 100.0;
		double tpmTotal = (6000000 * transactionCount / (currTimeMillis - sessionStartTimestamp)) / 100.0;

		log.info("Term-00, ");
		log.info("Term-00, ");
		log.warn("Term-00, Measured tpmC (NewOrders) = {}", tpmC);
		log.warn("Term-00, Measured tpmTOTAL = {}", tpmTotal);
		log.info("Term-00, Session Start     = {}", sessionStart);
		log.info("Term-00, Session End       = {}", sessionEnd);
		log.warn("Term-00, Transaction Count = {}", transactionCount - 1);

	}

	private long randomNumber(long min, long max) {
		return (long) (random.nextDouble() * (max - min + 1) + min);
	}

	private String getCurrentTime() {
		return dateFormat.format(new java.util.Date());
	}

	/**
	 * Prints in the standard output several values of the execution, and replaces
	 * the output with the most recent values.
	 */
	private void updateStatusLine() {
		// TODO Convert into log4j
		StringBuilder informativeText = new StringBuilder("");
		long currTimeMillis = System.currentTimeMillis();

		if (fastNewOrderCounter != 0) {
			double tpmTotal = (6000000 * transactionCount / (currTimeMillis - sessionStartTimestamp)) / 100.0;
			informativeText.append("Term-00, Running Average tpmTOTAL: " + tpmTotal + "    ");
		}

		if (currTimeMillis > sessionNextTimestamp) {
			sessionNextTimestamp += 5000; /* check this every 5 seconds */
			recentTpmTotal = (transactionCount - sessionNextKounter) * 12;
			sessionNextKounter = fastNewOrderCounter;
		}

		if (fastNewOrderCounter != 0) {
			informativeText.append("Current tpmTOTAL: " + recentTpmTotal + "    ");

		}

		long freeMem = Runtime.getRuntime().freeMemory() / (1024 * 1024);
		long totalMem = Runtime.getRuntime().totalMemory() / (1024 * 1024);
		long maxMem = Runtime.getRuntime().maxMemory() / (1024 * 1024);
		informativeText
				.append("Memory Usage: " + (totalMem - freeMem) + "MB / " + totalMem + "MB of max " + maxMem + "MB");

		// Keeps the last line of the console with the current status of the
		// execution.
		System.out.print(informativeText);
		for (int count = 0; count < 1 + informativeText.length(); count++) {
			System.out.print("\b");
		}
		informativeText.delete(0, informativeText.length());

	}

}
