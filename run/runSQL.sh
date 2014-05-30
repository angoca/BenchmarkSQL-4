myCP="../lib/postgresql-9.2-1003.jdbc4.jar"
myCP="$myCP:../dist/BenchmarkSQL-4.0.jar"

myOPTS="-Dprop=$1"
myOPTS="$myOPTS -DcommandFile=$2"

java -cp .:$myCP $myOPTS ExecJDBC
