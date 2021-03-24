-- Performs a truncation of all tables. Faster that a delete.
-- Valid for DB2 V9.7 or later.

truncate table BENCHMARKSQL.warehouse immediate;

truncate table BENCHMARKSQL.item immediate;

truncate table BENCHMARKSQL.stock immediate;

truncate table BENCHMARKSQL.district immediate;

truncate table BENCHMARKSQL.customer immediate;

truncate table BENCHMARKSQL.history immediate;

truncate table BENCHMARKSQL.oorder immediate;

truncate table BENCHMARKSQL.order_line immediate;

truncate table BENCHMARKSQL.new_order immediate;
