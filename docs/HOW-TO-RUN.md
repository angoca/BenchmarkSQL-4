
Instructions for running BenchmarkSQL
-------------------------------------

Depending on the RDBMS, you need to modify a set of files to configure the
connection properties and the environment.

There is a file in the `docs` directory for each RDBMS that describes the details
to configure and execute BenchmarkSQL.

These are the requirements and global steps for any RDBMS.

# Requirements

* JDK7 or higher.
* Maven to builds the sources.
* Access to the RDBMS to test.

## Compile the BenchmarkSQL source code

This is a one-time step, to generate the jar file to execute BenchmarkSQL.
This is done with Maven:

    mvn

## Create a database and the credentials to access

In this step the database is created in the DMBS and a set of credentials are
generated.

# Adjust the BenchmarkSQL configuration file

Modify the corresponding file under `run/myRDBMS`.
Make sure you can connect to database with the credentials.

# Build the schema and initial database load

Creates the tables and other objects in the database.
Also, generates the data for each warehouse, or loads this data from files.
This is useful to restart the benchmark when using many warehouses.

# Run the configured benchmark

Run your first benchmark, to make sure everything is correct.

# Scale the benchmark configuration

Tune your database for the workload.

# Clean the environment

Once you have finished with the tests, you can clean the environment by deleting all data and objects created.

