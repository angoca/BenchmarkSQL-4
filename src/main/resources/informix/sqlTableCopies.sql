-- Loads the data into the tables. This prevents data regeneration.

LOAD FROM "/tmp/csv/item.csv" DELIMITER ","
  INSERT INTO item(i_id, i_name, i_price, i_data, i_im_id);

LOAD FROM "/tmp/csv/warehouse.csv" DELIMITER ","
  INSERT INTO warehouse(w_id, w_ytd, w_tax, w_name, w_street_1, w_street_2, w_city, w_state, w_zip) 

LOAD FROM "/tmp/csv/district.csv" DELIMITER ","
  INSERT INTO district(d_id, d_w_id, d_ytd, d_tax, d_next_o_id, d_name, d_street_1,d_street_2, d_city, d_state, d_zip);

LOAD FROM "/tmp/csv/stock.csv" DELIMITER ","
  INSERT INTO stock(s_i_id, s_w_id, s_quantity, s_ytd, s_order_cnt, s_remote_cnt, s_data, s_dist_01, s_dist_02, s_dist_03, s_dist_04, s_dist_05, s_dist_06, s_dist_07, s_dist_08, s_dist_09, s_dist_10);

LOAD FROM "/tmp/csv/customer.csv" DELIMITER ","
  INSERT INTO customer(c_id, c_d_id, c_w_id, c_discount, c_credit, c_last, c_first, c_credit_lim, c_balance, c_ytd_payment, c_payment_cnt, c_delivery_cnt, c_street_1, c_street_2, c_city, c_state, c_zip, c_phone, c_since, c_middle, c_data);

0LOAD FROM "/tmp/csv/history.csv" DELIMITER ","
  INSERT INTO history(hist_id, h_c_id, h_c_d_id, h_c_w_id, h_d_id, h_w_id, h_date, h_amount, h_data);

LOAD FROM "/tmp/csv/oorder.csv" DELIMITER ","
  INSERT INTO oorder(o_id, o_w_id, o_d_id, o_c_id, o_carrier_id, o_ol_cnt, o_all_local, o_entry_d);

LOAD FROM "/tmp/csv/order_line.csv" DELIMITER ","
  INSERT INTO order_line(ol_w_id, ol_d_id, ol_o_id, ol_number, ol_i_id, ol_delivery_d, ol_amount, ol_supply_w_id, ol_quantity, ol_dist_info);

LOAD FROM "/tmp/csv/new_order.csv" DELIMITER ","
  INSERT INTO new_order(no_w_id, no_d_id, no_o_id);

