--#SET TERMINATOR ;

-- Creates all tables.
--
-- Author: Andres Gomez

SET CURRENT SCHEMA benchmarksql;

CREATE SCHEMA benchmarksql;

CREATE TABLE warehouse (
  w_id        INTEGER        NOT NULL,
  w_ytd       DECIMAL(12,2),
  w_tax       DECIMAL(4,4),
  w_name      VARCHAR(10),
  w_street_1  VARCHAR(20),
  w_street_2  VARCHAR(20),
  w_city      VARCHAR(20),
  w_state     CHAR(2),
  w_zip       CHAR(9)
);

CREATE TABLE district (
  d_w_id       INTEGER        NOT NULL,
  d_id         INTEGER        NOT NULL,
  d_ytd        DECIMAL(12,2),
  d_tax        DECIMAL(4,4),
  d_next_o_id  INTEGER,
  d_name       VARCHAR(10),
  d_street_1   VARCHAR(20),
  d_street_2   VARCHAR(20),
  d_city       VARCHAR(20),
  d_state      CHAR(2),
  d_zip        CHAR(9)
);

CREATE TABLE customer (
  c_w_id         INTEGER        NOT NULL,
  c_d_id         INTEGER        NOT NULL,
  c_id           INTEGER        NOT NULL,
  c_discount     DECIMAL(4,4),
  c_credit       CHAR(2),
  c_last         VARCHAR(16),
  c_first        VARCHAR(16),
  c_credit_lim   DECIMAL(12,2),
  c_balance      DECIMAL(12,2),
  c_ytd_payment  FLOAT,
  c_payment_cnt  INTEGER,
  c_delivery_cnt INTEGER,
  c_street_1     VARCHAR(20),
  c_street_2     VARCHAR(20),
  c_city         VARCHAR(20),
  c_state        CHAR(2),
  c_zip          CHAR(9),
  c_phone        CHAR(16),
  c_since        TIMESTAMP,
  c_middle       CHAR(2),
  c_data         VARCHAR(500)
);

CREATE TABLE history (
  hist_id  INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
  h_c_id   INTEGER,
  h_c_d_id INTEGER,
  h_c_w_id INTEGER,
  h_d_id   INTEGER,
  h_w_id   INTEGER,
  h_date   TIMESTAMP,
  h_amount DECIMAL(6,2),
  h_data   VARCHAR(24)
);

CREATE TABLE oorder (
  o_w_id       INTEGER       NOT NULL,
  o_d_id       INTEGER       NOT NULL,
  o_id         INTEGER       NOT NULL,
  o_c_id       INTEGER,
  o_carrier_id INTEGER,
  o_ol_cnt     DECIMAL(2,0),
  o_all_local  DECIMAL(1,0),
  o_entry_d    TIMESTAMP
);

CREATE TABLE new_order (
  no_w_id  INTEGER NOT NULL,
  no_d_id  INTEGER NOT NULL,
  no_o_id  INTEGER NOT NULL
);

CREATE TABLE order_line (
  ol_w_id         INTEGER       NOT NULL,
  ol_d_id         INTEGER       NOT NULL,
  ol_o_id         INTEGER       NOT NULL,
  ol_number       INTEGER       NOT NULL,
  ol_i_id         INTEGER       NOT NULL,
  ol_delivery_d   TIMESTAMP,
  ol_amount       DECIMAL(6,2),
  ol_supply_w_id  INTEGER,
  ol_quantity     DECIMAL(2,0),
  ol_dist_info    CHAR(24)
);

CREATE TABLE stock (
  s_w_id       INTEGER       NOT NULL,
  s_i_id       INTEGER       NOT NULL,
  s_quantity   DECIMAL(4,0),
  s_ytd        DECIMAL(8,2),
  s_order_cnt  INTEGER,
  s_remote_cnt INTEGER,
  s_data       VARCHAR(50),
  s_dist_01    CHAR(24),
  s_dist_02    CHAR(24),
  s_dist_03    CHAR(24),
  s_dist_04    CHAR(24),
  s_dist_05    CHAR(24),
  s_dist_06    CHAR(24),
  s_dist_07    CHAR(24),
  s_dist_08    CHAR(24),
  s_dist_09    CHAR(24),
  s_dist_10    CHAR(24)
);

CREATE TABLE item (
  i_id     INTEGER       NOT NULL,
  i_name   VARCHAR(24),
  i_price  DECIMAL(5,2),
  i_data   VARCHAR(50),
  i_im_id  INTEGER
);

