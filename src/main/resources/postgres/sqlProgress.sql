-- Queries all tables from the model, to see how it is performing.
-- It can be continuously executed from Bash, by issuing:
--   while [ true ] ; do psql -d postgres -t -f sqlProgress.sql | sed '/^$/d' ; sleep 1 ; done
--
-- Author: Andres Gomez

with progress (
  max_warehouses,
  warehouses,
  max_districts,
  districts,
  max_customers,
  customers,
  max_history,
  history,
  max_oorders,
  oorders,
  max_new_orders,
  new_orders,
  max_order_line,
  order_line,
  max_stock,
  stock,
  max_item,
  item
 ) as (
  select
   count(1), 'Warehouses',
   0, 'Districts',
   0, 'Customers',
   0, 'History',
   0, 'Oorder',
   0, 'New orders',
   0, 'Order line',
   0, 'Stock',
   0, 'Item'
  from benchmarksql.warehouse
  union
  select
   0, 'Warehouses',
   count(1), 'Districts',
   0, 'Customers',
   0, 'History',
   0, 'Oorder',
   0, 'New orders',
   0, 'Order line',
   0, 'Stock',
   0, 'Item'
  from benchmarksql.district
  union
  select
   0, 'Warehouses',
   0, 'Districts',
   count(1), 'Customers',
   0, 'History',
   0, 'Oorder',
   0, 'New orders',
   0, 'Order line',
   0, 'Stock',
   0, 'Item'
  from benchmarksql.customer
  union
  select
   0, 'Warehouses',
   0, 'Districts',
   0, 'Customers',
   count(1), 'History',
   0, 'Oorder',
   0, 'New orders',
   0, 'Order line',
   0, 'Stock',
   0, 'Item'
  from benchmarksql.history
  union
  select
   0, 'Warehouses',
   0, 'Districts',
   0, 'Customers',
   0, 'History',
   count(1), 'Oorder',
   0, 'New orders',
   0, 'Order line',
   0, 'Stock',
   0, 'Item'
  from benchmarksql.oorder
  union
  select
   0, 'Warehouses',
   0, 'Districts',
   0, 'Customers',
   0, 'History',
   0, 'Oorder',
   count(1), 'New orders',
   0, 'Order line',
   0, 'Stock',
   0, 'Item'
  from benchmarksql.new_order
  union
  select
   0, 'Warehouses',
   0, 'Districts',
   0, 'Customers',
   0, 'History',
   0, 'Oorder',
   0, 'New orders',
   count(1), 'Order line',
   0, 'Stock',
   0, 'Item'
  from benchmarksql.order_line
  union
  select
   0, 'Warehouses',
   0, 'Districts',
   0, 'Customers',
   0, 'History',
   0, 'Oorder',
   0, 'New orders',
   0, 'Order line',
   count(1), 'Stock',
   0, 'Item'
  from benchmarksql.stock
  union
  select
   0, 'Warehouses',
   0, 'Districts',
   0, 'Customers',
   0, 'History',
   0, 'Oorder',
   0, 'New orders',
   0, 'Order line',
   0, 'Stock',
   count(1), 'Item'
  from benchmarksql.item
)
select
 max(max_warehouses), warehouses,
 max(max_districts), districts,
 max(max_customers), customers,
 max(max_history), history,
 max(max_oorders), oorders,
 max(max_new_orders), new_orders,
 max(max_order_line), order_line,
 max(max_stock), stock,
 max(max_item), item
from progress
group by
 warehouses,
 districts,
 customers,
 history,
 oorders,
 new_orders,
 order_line,
 stock,
 item
;
