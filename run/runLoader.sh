#java -cp .:../lib/postgresql-9.3-1101.jdbc41.jar:../dist/BenchmarkSQL-4.1.jar -Dprop=$1 LoadData $2 $3 $4 $5
java -cp .:../lib/connector-api-1.5.jar:../lib/jaybird-jdk18-3.0.15.jar:../dist/BenchmarkSQL-4.1.jar -Dprop=$1 LoadData $2 $3 $4 $5
