@echo off

:: Executes the given SQL with the given database properties.
::
:: 1) Database properties file.
:: 2) SQL script file.
::
:: Author: Andres Gomez.

set MY_CLASSPATH=..\dist\BenchmarkSQL.jar;..\lib\postgresql-9.3-1101.jdbc41.jar

set MY_PROPERTIES=-Dprop=%1 -DcommandFile=%2

"%JAVA_HOME%\bin\java" -cp %MY_CLASSPATH% %MY_PROPERTIES% com.github.benchmarksql.ExecJDBC
