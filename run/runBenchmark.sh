#java -cp .:../lib/postgresql-9.3-1101.jdbc41.jar:../lib/log4j-1.2.17.jar:../lib/apache-log4j-extras-1.1.jar:../dist/BenchmarkSQL-4.1.jar -Dprop=$1 jTPCC
java -cp .:../lib/connector-api-1.5.jar:../lib/jaybird-jdk18-3.0.15.jar:../lib/log4j-1.2.17.jar:../lib/apache-log4j-extras-1.1.jar:../dist/BenchmarkSQL-4.1.jar -Dprop=$1 jTPCC
