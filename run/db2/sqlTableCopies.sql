--#SET TERMINATOR ;

-- Loads the already generated data.
--
-- Author: Andres Gomez

SET CURRENT SCHEMA benchmarksql;

-- LOAD messages will be placed in files, 1 per LOAD, in <db2_instance_home>/sqllib/tmp
-- fully qualified path to the load files required; change paths below as needed.

CALL SYSPROC.ADMIN_CMD('LOAD FROM /tmp/csv/warehouse.csv  OF DEL MESSAGES ON SERVER REPLACE INTO warehouse  COPY NO'); 
CALL SYSPROC.ADMIN_CMD('LOAD FROM /tmp/csv/item.csv       OF DEL MESSAGES ON SERVE  REPLACE INTO item       COPY NO'); 
CALL SYSPROC.ADMIN_CMD('LOAD FROM /tmp/csv/stock.csv      OF DEL MESSAGES ON SERVER REPLACE INTO stock      COPY NO'); 
CALL SYSPROC.ADMIN_CMD('LOAD FROM /tmp/csv/district.csv   OF DEL MESSAGES ON SERVER REPLACE INTO district   COPY NO'); 
CALL SYSPROC.ADMIN_CMD('LOAD FROM /tmp/csv/customer.csv   OF DEL MESSAGES ON SERVER REPLACE INTO customer   COPY NO'); 
CALL SYSPROC.ADMIN_CMD('LOAD FROM /tmp/csv/cust-hist.csv  OF DEL MESSAGES ON SERVER REPLACE INTO history    COPY NO'); 
CALL SYSPROC.ADMIN_CMD('LOAD FROM /tmp/csv/order.csv      OF DEL MESSAGES ON SERVER REPLACE INTO oorder     COPY NO'); 
CALL SYSPROC.ADMIN_CMD('LOAD FROM /tmp/csv/order-line.csv OF DEL MESSAGES ON SERVER REPLACE INTO order_line COPY NO'); 
CALL SYSPROC.ADMIN_CMD('LOAD FROM /tmp/csv/new-order.csv  OF DEL MESSAGES ON SERVER REPLACE INTO new_order  COPY NO'); 
