These are the instructions to add a new RDBMS.

# run directory

You should create a new directory for the RDBMS.
You can copy the files from Postgres and modify them according to your database.

# Files under run directory

 * You should have a generic properties file for your RDMBS.
 This should not have any specific name, in order to be easily adapted to other
 environment.
 * You should adapt all the sql files.
 Probably, the SQL from Postgres will work from the beginning in any other
 RDBMS; however, you probably will want to tune them to specific features
 proprietary syntax of your RDBMS.

# lib directory

You should include the JDBC driver.
Optionally, you can push it, depending of its license.