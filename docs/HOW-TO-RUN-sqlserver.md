
Instructions for running BenchmarkSQL on sqlserver
---------------------------------------------------

# Requirements

These are the requirements to run this application:

* Use of JDK7 or later.
* Maven to build the sources.
* SQl Server database.

## Maven build

This project is configured with Maven, you just need to build the package and
then change to `target` directory:

    mvn
    cd target

## Create a database and the credentials to access

As the user `sa`, create the `benchmarksql` login with correct permissions.

    sa=# CREATE DATABASE benchmarksqldb;
	sa=# CREATE LOGIN benchmarksql WITH PASSWORD = 'password';
    sa=# USE benchmarksqldb;
    sa=# GRANT CONTROL ON DATABASE::benchmarksqldb TO benchmarksql;
    sa=# GO

# Adjust the BenchmarkSQL configuration file

A sample JDBC connection property files is provided for SQL Server called
`sqlserver.properties`.

Go to the `run/sqlserver` directory, edit the `sqlserver.properties` file to point to
the database instance you would like to test.   

    cd run
    cd sqlserver
    vi sqlserver.properties

# Build the schema and initial database load

Change to the `run/sqlserver` directory.

    cd run
    cd sqlserver

Execute the `sqlTableCreates.sql` to create the base tables.

_Windows:_

    runSQL sqlserver\sqlserver.properties sqlserver\sqlTableCreates.sql

_Linux:_

    ./runSQL sqlserver/sqlserver.properties sqlserver/sqlTableCreates.sql

Run the Loader command file to load all of the default data for a benchmark.

 * Approximately half a million rows (per warehouse) will be loaded across 9
 tables.

To run the following command, indicating the quantity of warehouses:

_Windows:_

    runLoader sqlserver\sqlserver.properties numWarehouses 1

_Linux:_

    ./runLoader sqlserver/sqlserver.properties numWarehouses 1

NOTE: You should run the `sqlTableTruncates.sql` script if your tables are not
already empty.
      
 * Alternatively, you may choose to generate the load data out to CSV files
 where it can be efficiently bulk loaded into the database as many times as
 required by your testing.

To run the following command, indicating the location of the files.

_Windows:_

    runLoader sqlserver\sqlserver.properties numWarehouses 1 fileLocation c:\tmp\csv   

_Linux:_

    ./runLoader sqlserver/sqlserver.properties numWarehouses 1 fileLocation /tmp/csv/   

These CSV files can be bulk loaded as follows:

_Windows:_

    runSQL sqlserver\sqlserver.properties sqlserver\sqlTableCopies.sql

_Linux:_

    ./runSQL sqlserver/sqlserver.properties sqlserver/sqlTableCopies.sql

You may truncate the data via:

_Windows:_

    runSQL sqlserver\sqlserver.properties sqlserver\sqlTableTruncates.sql

_Linux:_

    ./runSQL sqlserver/sqlserver.properties sqlserver/sqlTableTruncates.sql

In both cases, run the `runSQL` command file to execute the SQL script
`sqlIndexCreates.sql` to create the primary keys & other indexes on the tables.

_Windows:_

    runSQL sqlserver\sqlserver.properties sqlserver\sqlIndexCreates.sql

_Linux:_

    ./runSQL sqlserver/sqlserver.properties sqlserver/sqlIndexCreates.sql

When you restart the test, and you will reload the data, you could delete the
indexes before this:

_Windows:_

    runSQL sqlserver\sqlserver.properties sqlserver\sqlIndexDrops.sql

_Linux:_

    ./runSQL sqlserver/sqlserver.properties sqlserver/sqlIndexDrops.sql

# Run the configured benchmark

Run the `runBenchmark` command file to test the database.
This command will create terminals and automatically start the transaction
based on the parameters set in `sqlserver.properties`. 

_Windows:_

    runBenchmark sqlserver\sqlserver.properties

_Linux:_

    ./runBenchmark sqlserver/sqlserver.properties

# Scale the benchmark configuration

ToDo

# Clean the environment

To clean the database, you can run.

_Windows:_

    runSQL sqlserver\sqlserver.properties sqlserver\sqlTableDrops.sql

_Linux:_

    ./runSQL sqlserver/sqlserver.properties sqlserver/sqlTableDrops.sql

