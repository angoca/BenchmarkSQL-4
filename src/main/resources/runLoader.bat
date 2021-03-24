@echo off

:: Executes the given SQL with the given database properties.
::
:: 1) Database properties file.
::
:: This script received a pair of properties:
:: * numWarehouses + Quantity of warehouses to load.
:: * fileLocation + Location of the file to load.
::
:: Author: Andres Gomez.

set MY_CLASSPATH=.;..\BenchmarkSQL.jar;..\lib\*

set MY_PROPERTIES=-Dprop=%1

"%JAVA_HOME%\bin\java" -cp %MY_CLASSPATH% %MY_PROPERTIES% LoadData %2 %3 %4 %5
