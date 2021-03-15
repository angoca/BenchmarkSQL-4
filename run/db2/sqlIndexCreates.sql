--#SET TERMINATOR ;

-- Adds the primary keys and indexes in the tables.
--
-- Author: Andres Gomez

SET CURRENT SCHEMA benchmarksql;

ALTER TABLE warehouse ADD CONSTRAINT pk_warehouse 
  PRIMARY KEY (w_id);  

ALTER TABLE district ADD CONSTRAINT pk_district 
  PRIMARY KEY (d_w_id, d_id);

ALTER TABLE customer ADD CONSTRAINT pk_customer 
  PRIMARY KEY (c_w_id, c_d_id, c_id);

CREATE INDEX idx_customer_name 
  ON  customer (c_w_id, c_d_id, c_last, c_first);

-- History TABLE has no PRIMARY KEY.

ALTER TABLE oorder ADD CONSTRAINT pk_oorder 
  PRIMARY KEY (o_w_id, o_d_id, o_id);

CREATE unique INDEX idx_oorder_carrier 
  ON  oorder (o_w_id, o_d_id, o_carrier_id, o_id);
 
ALTER TABLE new_order ADD CONSTRAINT pk_new_order 
  PRIMARY KEY (no_w_id, no_d_id, no_o_id);

ALTER TABLE order_line ADD CONSTRAINT pk_order_line 
  PRIMARY KEY (ol_w_id, ol_d_id, ol_o_id, ol_number);

ALTER TABLE stock ADD CONSTRAINT pk_stock
  PRIMARY KEY (s_w_id, s_i_id);

ALTER TABLE item ADD CONSTRAINT pk_item
  PRIMARY KEY (i_id);

-- Calculates statistics in all tables with all indexes.

CALL SYSPROC.ADMIN_CMD('RUNSTATS ON TABLE warehouse AND INDEXES ALL');
CALL SYSPROC.ADMIN_CMD('RUNSTATS ON TABLE district AND INDEXES ALL');
CALL SYSPROC.ADMIN_CMD('RUNSTATS ON TABLE customer AND INDEXES ALL');
CALL SYSPROC.ADMIN_CMD('RUNSTATS ON TABLE history AND INDEXES ALL');
CALL SYSPROC.ADMIN_CMD('RUNSTATS ON TABLE oorder AND INDEXES ALL');
CALL SYSPROC.ADMIN_CMD('RUNSTATS ON TABLE new_order AND INDEXES ALL');
CALL SYSPROC.ADMIN_CMD('RUNSTATS ON TABLE order_line AND INDEXES ALL');
CALL SYSPROC.ADMIN_CMD('RUNSTATS ON TABLE stock AND INDEXES ALL');
CALL SYSPROC.ADMIN_CMD('RUNSTATS ON TABLE item AND INDEXES ALL');
