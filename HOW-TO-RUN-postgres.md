
Instructions for running BenchmarkSQL on PostgreSQL
---------------------------------------------------

# Requirements

Use of JDK7 is required.

## Create a database and the credentials to access.

As the user `postgres`, create the `benchmarksql` user with correct permissions.

	postgres=# CREATE USER benchmarksql WITH SUPERUSER PASSWORD 'password';
	postgres=# GRANT ALL PRIVILEGES ON DATABASE postgres TO benchmarksql;

# Adjust the BenchmarkSQL configuration file

A sample JDBC connection property files is provided for PostgreSQL called `postgres.properties`.

Go to the `run/postgres` directory, edit the `postgres.properties` file to point to the
database instance you would like to test.   

# Build the schema and initial database load

Change to the `run/postgres` directory.

Execute the `sqlTableCreates.sql` to create the base tables.

    runSQL postgres.properties sqlTableCreates.sql

Run the Loader command file to load all of the default data for a benchmark.

 * Approximately half a million rows (per warehouse) will be loaded across 9
 tables.

    runLoader postgres.properties numWarehouses 1

NOTE: You should run the `sqlTableTruncates.sql` script if your tables are not
already empty.
      
 * Alternatively, you may choose to generate the load data out to CSV files
 where it can be efficiently bulk loaded into the database as many times as
 desired by your testing.

    runLoader postgres.properties numWarehouses 1 fileLocation /tmp/csv/   
        
These CSV files can be bulk loaded as follows:

    runSQL postgres.properties sqlTableCopies.sql

You may truncate the data via:

    runSQL postgres.properties sqlTableTruncates.sql

In both cases, run the `runSQL` command file to execute the SQL script
`sqlIndexCreates.sql` to create the primary keys & other indexes on the tables.

    runSQL postgres.properties sqlIndexCreates.sql

When you restart the test, and you will reload the data, you could delete the
indexes before this:

    runSQL postgres.properties sqlIndexDrops.sql

# Run the configured benchmark

Run the `runBenchmark` command file to test the database.
This command will create terminals and automatically start the transaction
based on the parameters set in `postgres.properties`. 

    runBenchmark postgres.properties

# Scale the benchmark configuration

ToDo

As an automated process, you can run the `automate` script that will this whole
process several times, and shows the time expended in each iteration.
# Clean the environment

To clean the database, you can run.

    runSQL postgres.properties sqlTableDrops.sql

