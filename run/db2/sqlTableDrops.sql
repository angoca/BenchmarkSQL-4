--#SET TERMINATOR ;

-- Drops all tables.
--
-- Author: Andres Gomez

SET CURRENT SCHEMA benchmarksql;

DROP TABLE warehouse;

DROP TABLE item;

DROP TABLE stock;

DROP TABLE district;

DROP TABLE customer;

DROP TABLE oorder;

DROP TABLE order_line;

DROP TABLE history;

DROP TABLE new_order;

DROP SCHEMA benchmarksql RESTRICT;
