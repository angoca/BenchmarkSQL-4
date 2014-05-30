#
"$JAVA_HOME/bin/java" -cp ../lib/edb-jdbc14-8_0_3_14.jar:../lib/ojdbc14.jar:../lib/postgresql-8.0.309.jdbc3.jar:../dist/BenchmarkSQL-2.2.jar -Dprop=$1 -DcommandFile=$2 ExecJDBC 
