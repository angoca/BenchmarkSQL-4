/*
 * jTPCC - Open Source Java implementation of a TPC-C like benchmark
 *
 * Copyright (C) 2003, Raul Barbosa
 * Copyright (C) 2004-2013, Denis Lussier
 * Copyright (C) 2013, Cady Motyka
 *
 */

import org.apache.log4j.*;
 
import java.io.*;
import java.sql.*;
import java.util.*;
import java.text.*;
  

public class jTPCC implements jTPCCConfig
{
    private static org.apache.log4j.Logger log = Logger.getLogger(jTPCC.class);

    private int currentlyDisplayedTerminal;

    private jTPCCTerminal[] terminals;
    private String[] terminalNames;
    private boolean terminalsBlockingExit = false;
    private Random random;
    private long terminalsStarted = 0, sessionCount = 0, transactionCount;

    private long newOrderCounter, sessionStartTimestamp, sessionEndTimestamp, sessionNextTimestamp=0, sessionNextKounter=0;
    private long sessionEndTargetTime = -1, fastNewOrderCounter, recentTpmC=0;
    private boolean signalTerminalsRequestEndSent = false, databaseDriverLoaded = false;

    private FileOutputStream fileOutputStream;
    private PrintStream printStreamReport;
    private String sessionStart, sessionEnd;

    private double tpmC;

    public static void main(String args[])
    {
        PropertyConfigurator.configure("log4j.xml");
        new jTPCC();
    }

    private String getProp (Properties p, String pName)
    {
        String prop =  p.getProperty(pName);
        printMessage(pName + "=" + prop);
        return(prop);
    }


    public jTPCC()
    {

        // load the ini file
        Properties ini = new Properties();
        try {
          ini.load( new FileInputStream(System.getProperty("prop")));
        } catch (IOException e) {
          errorMessage("could not load properties file");
        }
                                                                               
        printMessage("");
        printMessage("+-------------------------------------------------------------+");
        printMessage("              BenchmarkSQL v" + JTPCCVERSION);
        printMessage("+-------------------------------------------------------------+");
        printMessage(" (c) 2003, Raul Barbosa");
        printMessage(" (c) 2004-2013, Denis Lussier");
        printMessage(" (c) 2013, Cady Motyka");
        printMessage("+-------------------------------------------------------------+");
        printMessage("");
        String  iDriver             = getProp(ini,"driver");
        String  iConn               = getProp(ini,"conn");
        String  iUser               = getProp(ini,"user");
        String  iPassword           = ini.getProperty("password");
        String  iWarehouses         = getProp(ini,"warehouses");
        String  iTerminals          = getProp(ini,"terminals");
        String  iPaymentWeight      = getProp(ini,"paymentWeight");
        String  iOrderStatusWeight  = getProp(ini,"orderStatusWeight");
        String  iDeliveryWeight     = getProp(ini,"deliveryWeight");
        String  iStockLevelWeight   = getProp(ini,"stockLevelWeight");
        String  iRunTxnsPerTerminal = getProp(ini,"runTxnsPerTerminal");
        String  iRunMins            = getProp(ini,"runMins");
        String  sRunMinsBool        = getProp(ini,"runMinsBool");
        printMessage("");

        boolean iRunMinsBool = false;
        if (sRunMinsBool.equals("true")) iRunMinsBool = true;
                                                                               
        this.random = new Random(System.currentTimeMillis());
        
        fastNewOrderCounter = 0;
        
        
        try
        {
            String driver = iDriver;
            printMessage("Loading database driver: \'" + driver + "\'...");
            Class.forName(iDriver);
            databaseDriverLoaded = true;
        }
        catch(Exception ex)
        {
            errorMessage("Unable to load the database driver!");
            databaseDriverLoaded = false;
        }
        
        
        if(databaseDriverLoaded)
        {
            try
            {
                boolean limitIsTime = iRunMinsBool;
                int numTerminals = -1, transactionsPerTerminal = -1, numWarehouses = -1;
                int paymentWeightValue = -1, orderStatusWeightValue = -1, deliveryWeightValue = -1, stockLevelWeightValue = -1;
                long executionTimeMillis = -1;
                
                try
                {
                    numWarehouses = Integer.parseInt(iWarehouses);
                    if(numWarehouses <= 0)
                        throw new NumberFormatException();
                }
                catch(NumberFormatException e1)
                {
                    errorMessage("Invalid number of warehouses!");
                    throw new Exception();
                }
                
                try
                {
                    numTerminals = Integer.parseInt(iTerminals);
                    if(numTerminals <= 0 || numTerminals > 10*numWarehouses)
                        throw new NumberFormatException();
                }
                catch(NumberFormatException e1)
                {
                    errorMessage("Invalid number of terminals!");
                    throw new Exception();
                }
                
                if(limitIsTime)
                {
                    try
                    {
                        executionTimeMillis = Long.parseLong(iRunMins) * 60000;
                        if(executionTimeMillis <= 0)
                            throw new NumberFormatException();
                    }
                    catch(NumberFormatException e1)
                    {
                        errorMessage("Invalid number of minutes!");
                        throw new Exception();
                    }
                }
                else
                {
                    try
                    {
                        transactionsPerTerminal = Integer.parseInt(iRunTxnsPerTerminal);
                        if(transactionsPerTerminal <= 0)
                            throw new NumberFormatException();
                    }
                    catch(NumberFormatException e1)
                    {
                        errorMessage("Invalid number of transactions per terminal!");
                        throw new Exception();
                    }
                }
                
                try
                {
                    paymentWeightValue = Integer.parseInt(iPaymentWeight);
                    orderStatusWeightValue = Integer.parseInt(iOrderStatusWeight);
                    deliveryWeightValue = Integer.parseInt(iDeliveryWeight);
                    stockLevelWeightValue = Integer.parseInt(iStockLevelWeight);
                    
                    if(paymentWeightValue < 0 || orderStatusWeightValue < 0 || deliveryWeightValue < 0 || stockLevelWeightValue < 0)
                        throw new NumberFormatException();
                }
                catch(NumberFormatException e1)
                {
                    errorMessage("Invalid number in mix percentage!");
                    throw new Exception();
                }
                
                if(paymentWeightValue + orderStatusWeightValue + deliveryWeightValue + stockLevelWeightValue > 100)
                {
                    errorMessage("Sum of mix percentage parameters exceeds 100%!");
                    throw new Exception();
                }
                
                newOrderCounter = 0;
                printMessage("Session started!");
                if(!limitIsTime)
                    printMessage("Creating " + numTerminals + " terminal(s) with " + transactionsPerTerminal + " transaction(s) per terminal...");
                else
                    printMessage("Creating " + numTerminals + " terminal(s) with " + (executionTimeMillis/60000) + " minute(s) of execution...");
                printMessage("Transaction Weights: " + (100 - (paymentWeightValue + orderStatusWeightValue + deliveryWeightValue + stockLevelWeightValue)) + "% New-Order, " + paymentWeightValue + "% Payment, " + orderStatusWeightValue + "% Order-Status, " + deliveryWeightValue + "% Delivery, " + stockLevelWeightValue + "% Stock-Level");
                
                printMessage("Number of Terminals\t" + numTerminals);
                
                terminals = new jTPCCTerminal[numTerminals];
                terminalNames = new String[numTerminals];
                terminalsStarted = numTerminals;
                try
                {
                    String database = iConn;
                    String username = iUser;
                    String password = iPassword;
                    
                    int[][] usedTerminals = new int[numWarehouses][10];
                    for(int i = 0; i < numWarehouses; i++)
                        for(int j = 0; j < 10; j++)
                            usedTerminals[i][j] = 0;
                    
                    for(int i = 0; i < numTerminals; i++)
                    {
                        int terminalWarehouseID;
                        int terminalDistrictID;
                        do
                        {
                            terminalWarehouseID = (int)randomNumber(1, numWarehouses);
                            terminalDistrictID = (int)randomNumber(1, 10);
                        }
                        while(usedTerminals[terminalWarehouseID-1][terminalDistrictID-1] == 1);
                        usedTerminals[terminalWarehouseID-1][terminalDistrictID-1] = 1;
                        
                        String terminalName = "Term-" + (i>=9 ? ""+(i+1) : "0"+(i+1));
                        Connection conn = null;
                        printMessage("Creating database connection for " + terminalName + "...");
                        conn = DriverManager.getConnection(database, username, password);
                        conn.setAutoCommit(false);
                        
                        jTPCCTerminal terminal = new jTPCCTerminal
                        (terminalName, terminalWarehouseID, terminalDistrictID, conn,
                         transactionsPerTerminal, paymentWeightValue, orderStatusWeightValue,
                         deliveryWeightValue, stockLevelWeightValue, numWarehouses, this);
                        
                        terminals[i] = terminal;
                        terminalNames[i] = terminalName;
                        printMessage(terminalName + "\t" + terminalWarehouseID);
                    }
                    
                    sessionEndTargetTime = executionTimeMillis;
                    signalTerminalsRequestEndSent = false;
                    
                    printMessage
                    ("\nTransaction\tWeight\n% New-Order\t" +
                     (100 - (paymentWeightValue + orderStatusWeightValue + deliveryWeightValue + stockLevelWeightValue)) +
                     "\n% Payment\t" + paymentWeightValue + "\n% Order-Status\t" + orderStatusWeightValue +
                     "\n% Delivery\t" + deliveryWeightValue + "\n% Stock-Level\t" + stockLevelWeightValue);
                    
                    printMessage("\n\nTransaction Number\tTerminal\tType\tExecution Time (ms)\t\tComment");
                    
                    printMessage("Created " + numTerminals + " terminal(s) successfully!");
                    boolean dummvar = true;
                   
                    
                    
                    //^Create Terminals, Start Transactions v //
                    
                    if(dummvar==true){
                    sessionStart = getCurrentTime();
                    sessionStartTimestamp = System.currentTimeMillis();
                    sessionNextTimestamp = sessionStartTimestamp;
                    if(sessionEndTargetTime != -1)
                        sessionEndTargetTime += sessionStartTimestamp;
                    
                    synchronized(terminals)
                    {
                        printMessage("Starting all terminals...");
                        transactionCount = 1;
                        for(int i = 0; i < terminals.length; i++)
                            (new Thread(terminals[i])).start();
                    }
                    
                    printMessage("All terminals started executing " + sessionStart);
                    }
                }
                
                catch(Exception e1)
                {
                    errorMessage("\nThis session ended with errors!");
                    printStreamReport.close();
                    fileOutputStream.close();
                    
                    throw new Exception();
                }
                
            }
            catch(Exception ex)
            {
            }
        }
    }

    private void signalTerminalsRequestEnd(boolean timeTriggered)
    {
        synchronized(terminals)
        {
            if(!signalTerminalsRequestEndSent)
            {
                if(timeTriggered)
                    printMessage("The time limit has been reached.");
                printMessage("Signalling all terminals to stop...");
                signalTerminalsRequestEndSent = true;
                
                for(int i = 0; i < terminals.length; i++)
                    if(terminals[i] != null)
                        terminals[i].stopRunningWhenPossible();

                printMessage("Waiting for all active transactions to end...");
            }
        }
    }

    public void signalTerminalEnded(jTPCCTerminal terminal, long countNewOrdersExecuted)
    {
        synchronized(terminals)
        {
            boolean found = false;
            terminalsStarted--;
            for(int i = 0; i < terminals.length && !found; i++)
            {
                if(terminals[i] == terminal)
                {
                    terminals[i] = null;
                    terminalNames[i] = "(" + terminalNames[i] + ")";
                    newOrderCounter += countNewOrdersExecuted;
                    found = true;
                }
            }
        }

        if(terminalsStarted == 0)
        {
            sessionEnd = getCurrentTime();
            sessionEndTimestamp = System.currentTimeMillis();
            sessionEndTargetTime = -1;
            printMessage("All terminals finished executing " + sessionEnd);
            endReport();
            terminalsBlockingExit = false;
            printMessage("Session finished!");
        }
    }

    public void signalTerminalEndedTransaction(String terminalName, String transactionType, long executionTime, String comment, int newOrder)
    {
        transactionCount++;
        fastNewOrderCounter += newOrder;

        if(sessionEndTargetTime != -1 && System.currentTimeMillis() > sessionEndTargetTime)
        {
            signalTerminalsRequestEnd(true);
        }

    }

    private void endReport()
    {
        long currTimeMillis = System.currentTimeMillis();
        long freeMem = Runtime.getRuntime().freeMemory() / (1024*1024);
        long totalMem = Runtime.getRuntime().totalMemory() / (1024*1024);
        tpmC = (6000000*fastNewOrderCounter/(currTimeMillis - sessionStartTimestamp))/100.0;
        
        printMessage("");
        printMessage("Running Average tpmC: " + tpmC + "      ");
        printMessage("Memory Usage: " + (totalMem - freeMem) + "MB / " + totalMem + "MB");
        printMessage("");
        printMessage("");
        printMessage("Measured tpmC     = " + tpmC);
        printMessage("Session Start     = " + sessionStart );
        printMessage("Session End       = " + sessionEnd);
        printMessage("Transaction Count = " + (transactionCount-1));
        printMessage(""); 
        
    }

    private void printMessage(String message)
    {
        log.info("Term-00 " + message);
    }

    private void errorMessage(String message)
    {
        log.error("Term-00 "+ message);
    }

    private void exit()
    {
        System.exit(0);
    }

    private long randomNumber(long min, long max)
    {
        return (long)(random.nextDouble() * (max-min+1) + min);
    }

    private String getCurrentTime()
    {
        return dateFormat.format(new java.util.Date());
    }

    private String getFileNameSuffix()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new java.util.Date());
    }

}
