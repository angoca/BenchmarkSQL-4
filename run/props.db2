driver=com.ibm.db2.jcc.DB2Driver
conn=jdbc:db2://localhost:50001/bmarkdb
user=db2inst1
password=xxxxxxxx


warehouses=1
terminals=1
//To run specified transactions per terminal- runMins must equal zero
runTxnsPerTerminal=10
//To run for specified minutes- runTxnsPerTerminal must equal zero
runMins=0
//Number of total transactions per minute
limitTxnsPerMin=300

//The following five values must add up to 100
newOrderWeight=45
paymentWeight=43
orderStatusWeight=4
deliveryWeight=4
stockLevelWeight=4



