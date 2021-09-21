-- Creating primary keys and indexes.

alter table warehouse add constraint  
  primary key (w_id);  

alter table district add constraint  
  primary key (d_w_id, d_id);

alter table customer add constraint  
  primary key (c_w_id, c_d_id, c_id);

create index ndx_customer_name
  on customer(c_w_id, c_d_id, c_last, c_firs);

alter table oorder add constraint 
  primary key (o_w_id, o_d_id, o_id);

alter table new_order add constraint 
  primary key (no_w_id, no_d_id, no_o_id);

create index ndx_oorder_carrier
  on oorder(o_w_id, o_d_id, o_carrier_id, o_id);

alter table new_order add constraint pk_new_order 
  primary key (no_w_id, no_d_id, no_o_id);

alter table order_line add constraint 
  primary key (ol_w_id, ol_d_id, ol_o_id, ol_number);

alter table stock add constraint
  primary key (s_w_id, s_i_id);

alter table item add constraint
  primary key (i_id);
