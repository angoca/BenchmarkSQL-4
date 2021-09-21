-- Remove primary keys and indexes.

alter table benchmarksql.warehouse drop PRIMARY KEY;

alter table benchmarksql.district drop PRIMARY KEY;

alter table benchmarksql.customer drop PRIMARY KEY;

alter table benchmarksql.customer drop index ndx_customer_name;

-- History table has no primary key.

alter table benchmarksql.oorder drop PRIMARY KEY;

alter table benchmarksql.oorder drop index ndx_oorder_carrier;

alter table benchmarksql.new_order drop PRIMARY KEY;

alter table benchmarksql.order_line drop PRIMARY KEY;

alter table benchmarksql.stock drop PRIMARY KEY;

alter table benchmarksql.item drop PRIMARY KEY;