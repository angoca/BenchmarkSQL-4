BenchmarkSQL
============

BenchmarkSQL runs a TPC-C like test against relational databases to compare
performance across versions, settings, and vendors.
This application is supported by different database manager systems:

 * PostgreSQL.
 * IBM Db2 (LUW).

This tool can be easily extended to use another RDBMS.
You can check the documentation to know how to port it.

* Access the [Wiki](https://github.com/ECI-SGBD/BenchmarkSQL-4/wiki).
* Reading the HOW-TO-RUN files under the `docs` directory.

This is a fork of the BenchmarkSQL 4.
BenchmarkSQL has most recent versions;
however, in order to port
[BenchmarkSQL 5](https://github.com/petergeoghegan/benchmarksql) to any RDBMS,
it requires some changes at the Java code to make it work and
[BenchmarkSQL 6](https://github.com/pgsql-io/benchmarksql) is still in
development, which implies the documentation is still limited.
For this reason, these newer versions are not as generic as the 4th one where
you only need to adapt the sql files.

BenchmarkSQL is open source, distributed under the GNU General Public License
version 2.0 (GPLv2) license.

