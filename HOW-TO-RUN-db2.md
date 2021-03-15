
Instructions for running BenchmarkSQL on Db2 (LUW)
--------------------------------------------------

# Requirements.

Use of JDK7 is required.

## Create a database and the credentials to access.

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

    db2 grant dbadm on database to user benchmrk

## Adjust the BenchmarkSQL configuration file

A sample JDBC connection property files is provided for IBM Db2 (LUW) called
`db2.properties`.

Go to the `run/db2` directory, edit the `db2.properties` file to point to the
database you would like to test.   

# Build the schema and initial database load

Change to the `run` directory.

Execute the `sqlTableCreates.sql` to create the base tables.

    $ ./runSQL db2/db2.properties db2/sqlTableCreates.sql

Run the Loader command file to load all of the default data for a benchmark.

 * Approximately half a million rows (per warehouse) will be loaded across 9
 tables.
 
    $ ./runLoader db2/db2.properties numWarehouses 1

NOTE: You should run the `sqlTableTruncates.sql` script if your tables are not
already empty.
      
 * Alternatively, you may choose to generate the load data out to CSV files
 where it can be efficiently bulk loaded into the database as many times as
 required by your testing.

    $ ./runLoader db2.properties numWarehouses 1 fileLocation /tmp/csv/        

These CSV files can be bulk loaded as follows:

    $ ./runSQL db2.properties db2/sqlTableCopies.sql

You may truncate the data via:

    $ ./runSQL db2.properties db2/sqlTableTruncates.sql

In both cases, run the `runSQL` command file to execute the SQL script
`sqlIndexCreates.sql` to create the primary keys & other indexes on the tables.

    $ ./runSQL db2.properties db2/sqlIndexCreates.sql

When you restart the test, and you will reload the data, you could delete the
indexes before this:

    $ ./runSQL db2.properties db2/sqlIndexDrops.sql

# Run the configured benchmark

Run the `runBenchmark` command file to test the database.
This command will create terminals and automatically start the transaction
based on the parameters set in `db2.properties`. 

    $ ./runBenchmark db2/db2.properties

# Scale the benchmark configuration

Configure the following elements according to the workload:

 * Bufferpools.
 * Tablespaces.
 * Table properties.
 * Indexes.
 * Transaction logs.

# Clean the environment

To clean the database, you can run.

    $ ./runSQL db2/db2.properties db2/sqlTableDrops.sql


