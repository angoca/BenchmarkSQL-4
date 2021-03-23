@echo off

:: Executes the given SQL with the given database properties.
::
:: 1) Database properties file.
:: 2) SQL script file.
::
:: Author: Andres Gomez.

set MY_CLASSPATH=.;..\BenchmarkSQL.jar;..\lib\*

set MY_PROPERTIES=-Dprop=%1 -DcommandFile=%2

"%JAVA_HOME%\bin\java" -cp %MY_CLASSPATH% %MY_PROPERTIES% com.github.benchmarksql.ExecJDBC
