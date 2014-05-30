*********************************************************************
Change Log:

Version 2.3.5  2013-01-29 denisl
   - Cleanup the formatting & content of README.txt

Version 2.3.4  2013-01-29 denisl
   - Default build is now with JDK 1.6 and JDBC 4 Postgres 9.2 driver
   - Remove outdated JDBC 3 drivers (for JDK 1.5).  You can run as 
     before by a JDBC4 driver from any supported vendor.
   - Remove ExecJDBC warning about trying to rollback when in 
     autocommit mode
   - Remove the extraneous COMMIT statemnts from the DDL scripts 
     since ExecJDBC runs in autocommit mode
   - Fix the version number displayed in the console

Version 2.3.3  2010-11-19 sjm  
   - Added DB2 LUW V9.7 support, and supercedes patch 2983892
   - No other changes from 2.3.2

*********************************************************************



Instructions for building
-------------------------

Use of JDK 1.6 is recommended, build with the "ant" command from the
base directory.


Instructions for running
------------------------
The below scripts all use relative paths, but, they depend on JAVA_HOME
environment variable being set so that the correct runtime can be found.

JDBC drivers and sample "?.properties" files are included to make it 
extremely easy for you to test out the performance of PostgreSQL, 
EnterpriseDB, MySQL, Oracle, DB2, or SQL Server in your environment.

1. Go to the 'run' directory, edit the appropriate "??.properties" 
   file to point to the database instance you'd like to test.   

2. Run the "sqlTableCreates" to create the base tables.

        $ ./runSQL.sh pg.properties sqlTableCreates


3. Run the "loadData" command file to load all of the default data 
   for a benchmark:


  A.) Approximately half a million rows in total will be loaded 
      across 9 tables per Warehouse.  (The default is numWarehouses=1)  
      A decent little test size of data totaling about 1 GB is 10 
      warehouses as follows:

        $ ./loadData.sh pg.properties numWarehouses 10

      NOTE: "loadData" will truncate all tables prior to inserting 
            data through the JDBC program
      
      There is also a "sqlTableDrops" script if you need it.

         $ ./runSQL.sh pg.properties sqlTableDrops

  B.) Alternatively, for PostgreSQL & DB2, you may choose to generate the 
      load data out to CSV files where it can be efficiently be 
      bulk loaded into the database as many times as required by your 
      testing.

      $ ./loadData.sh pg.properties numWarehouses 10 fileLocation ./csv/   
        
      These CSV files can be bulk loaded as follows:
        $  ./runSQL.sh pg.properties sqlTableCopies

      You may truncate the data via:

        $  ./runSQL.h pg.properties sqlTableTruncates

4. Run the "runSQL" command file to execute the SQL script 
   "sqlIndexCreates" to create the primary keys & other indexes 
   on the tables.

        $  ./runSQL.sh pg.properties sqlIndexCreates


5. Run the "runBenchmark" command file to execute the swing GUI 
   application to test the database.  Don't forget to set the number of 
   warehouses equal to the number you created in step 3. For each run, a 
   report will be placed in run/reports.  A sample report is included.

       $  ./runBenchmark.sh pg.properties

6. Operational Notes to minimize problems:  
   (a) executing runBenchmark will start the GUI. 
       Click the Database button to view properties file settings. No 
         changes are needed if the properties settings are correct.
       Click the Terminals button and specify desired settings. 
             Specify the same number of warehouses as you created.
             Select either "Minutes" or "Transactions per terminal" 
             and blank out the other setting.
       Click the Weights button and specify desired settings
       Click the Controls button, then click Create Terminals. One 
       DB connection per Terminal is created. Click Start Transactions 
       to start the benchmark.
   (b) If changing the number of terminals between runs, it is best 
       to close the GUI window and re-execute runBenchmark .
   (c) If the benchmark runs properly, all database connections are 
       terminated at completion. You may need to manually
       terminate connections if this is not the case
   (d) When done, close the GUI window 
