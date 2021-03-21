
Instructions for running BenchmarkSQL on Db2 (LUW)
--------------------------------------------------

# Requirements

These are the requirements to run this application:

* Use of JDK7 is required.
* Maven to build the sources.
* IBM Db2 database.

## Maven build

This project is configured with Maven, you just need to build the package and
then change to `target` directory:

    mvn
    cd target

## Create a database and the credentials to access

### Root authority

Create a user in the operative system dedicated for the benchmark.
In Linux / UNIX should be (It requires root authority):

    # useradd benchmrk

Assign a password to the user:

    # passwd benchmrk

NOTE: If is not possible to create a new user, then the instance user can be
used for the benchmark.
Make sure you have the password of this user.

### Db2 instance authority

Logged with the instance user db2inst1 (or equivalent), create a database:

    db2 create database benchmrk

Grants Database Authority to the created user:

    db2 connect to benchmrk
    db2 grant dbadm on database to user benchmrk

# Adjust the BenchmarkSQL configuration file

A sample JDBC connection property files is provided for IBM Db2 (LUW) called
`db2.properties`.

Go to the `run/db2` directory, edit the `db2.properties` file to point to the
database you would like to test.   

    cd run
    cd db2

# Build the schema and initial database load

Change to the `run/db2` directory.

    cd run
    cd db2

Execute the `sqlTableCreates.sql` to create the base tables.

_Windows:_

    runSQL db2.properties sqlTableCreates.sql

_Linux:_

    ./runSQL db2.properties sqlTableCreates.sql

Run the Loader command file to load all of the default data for a benchmark.

 * Approximately half a million rows (per warehouse) will be loaded across 9
 tables.

To run the following command, indicating the quantity of warehouses:

_Windows:_

    runLoader db2.properties numWarehouses 1

_Linux:_

    ./runLoader db2.properties numWarehouses 1

NOTE: You should run the `sqlTableTruncates.sql` script if your tables are not
already empty.
      
 * Alternatively, you may choose to generate the load data out to CSV files
 where it can be efficiently bulk loaded into the database as many times as
 required by your testing.

To run the following command, indicating the location of the files.

_Windows:_

    runLoader db2.properties numWarehouses 1 fileLocation /tmp/csv/        

_Linux:_

    ./runLoader db2.properties numWarehouses 1 fileLocation /tmp/csv/        

These CSV files can be bulk loaded as follows:

_Windows:_

    runSQL db2.properties sqlTableCopies.sql

_Linux:_

    ./runSQL db2.properties sqlTableCopies.sql

You may truncate the data via:

_Windows:_

    runSQL db2.properties sqlTableTruncates.sql

_Linux:_

    ./runSQL db2.properties sqlTableTruncates.sql

In both cases, run the `runSQL` command file to execute the SQL script
`sqlIndexCreates.sql` to create the primary keys & other indexes on the tables.

_Windows:_

    runSQL db2.properties sqlIndexCreates.sql

_Linux:_

    ./runSQL db2.properties sqlIndexCreates.sql

When you restart the test, and you will reload the data, you could delete the
indexes before this:

_Windows:_

    runSQL db2.properties sqlIndexDrops.sql

_Linux:_

    ./runSQL db2.properties sqlIndexDrops.sql

# Run the configured benchmark

Run the `runBenchmark` command file to test the database.
This command will create terminals and automatically start the transaction
based on the parameters set in `db2.properties`. 

_Windows:_

    runBenchmark db2.properties

_Linux:_

    ./runBenchmark db2.properties

# Scale the benchmark configuration

Configure the following elements according to the workload:

 * Bufferpools.
 * Tablespaces.
 * Table properties.
 * Indexes.
 * Transaction logs.

# Clean the environment

To clean the database, you can run.

_Windows:_

    runSQL db2.properties sqlTableDrops.sql

_Linux:_

    ./runSQL db2.properties sqlTableDrops.sql

