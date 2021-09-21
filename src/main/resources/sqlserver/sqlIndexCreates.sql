-- Creating primary keys and indexes.

alter table benchmarksql.warehouse add constraint pk_warehouse 
  primary key (w_id);  

alter table benchmarksql.district add constraint pk_district 
  primary key (d_w_id, d_id);

alter table benchmarksql.customer add constraint pk_customer 
  primary key (c_w_id, c_d_id, c_id);

alter table benchmarksql.history add constraint pk_history 
  primary key (hist_id);

-- Setting value for sequence.
-- ALTER SEQUENCE hist_id_seq  RESTART WITH select max(hist_id) + 1 from benchmarksql.history;
DECLARE @max int
SELECT @max = max(hist_id) + 1 from benchmarksql.history
exec('ALTER SEQUENCE hist_id_seq RESTART WITH ' + @max);

alter table benchmarksql.oorder add constraint pk_oorder 
  primary key (o_w_id, o_d_id, o_id);
 
alter table benchmarksql.new_order add constraint pk_new_order 
  primary key (no_w_id, no_d_id, no_o_id);

alter table benchmarksql.order_line add constraint pk_order_line 
  primary key (ol_w_id, ol_d_id, ol_o_id, ol_number);

alter table benchmarksql.stock add constraint pk_stock
  primary key (s_w_id, s_i_id);

alter table benchmarksql.item add constraint pk_item
  primary key (i_id);

-- Performing a vacuum.
-- vacuum analyze; 
