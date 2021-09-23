#myCP="../lib/postgresql-9.3-1101.jdbc41.jar"
myCP="../lib/connector-api-1.5.jar:../lib/jaybird-jdk18-3.0.15.jar"
myCP="$myCP:../dist/BenchmarkSQL-4.1.jar"

myOPTS="-Dprop=$1"
myOPTS="$myOPTS -DcommandFile=$2"

java -cp .:$myCP $myOPTS ExecJDBC
