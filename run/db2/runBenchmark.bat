@echo off

:: 1) Database properties file.
:: 2) SQL script file.
::
:: Author: Andres Gomez.

set MY_CLASSPATH=..\target\benchmarksql*.jar;..\lib\log4j-1.2.17.jar;..\lib\apache-log4j-extras-1.1.jar;..\lib\db2jcc4.jar

set MY_PROPERTIES=-Dprop=%1

"%JAVA_HOME%\bin\java" -cp %MY_CLASSPATH% %MY_PROPERTIES% com.github.benchmarksql.jtpcc.jTPCC
