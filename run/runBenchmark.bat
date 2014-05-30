"%JAVA_HOME%\bin\java" -server -Xms128M -Xmx128M -cp ..\lib\postgresql-9.2-1002.jdbc4.jar;..\dist\BenchmarkSQL-2.3.jar  -Dprop=%1 jTPCC
