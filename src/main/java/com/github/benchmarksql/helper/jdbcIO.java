package com.github.benchmarksql.helper;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.github.benchmarksql.jtpcc.pojo.NewOrder;
import com.github.benchmarksql.jtpcc.pojo.Oorder;
import com.github.benchmarksql.jtpcc.pojo.OrderLine;

/**
 * Execute JDBC statements.
 * 
 * @author Denis Lussier - 2004-2014
 */
public final class jdbcIO {
	/**
	 * Logger.
	 */
	private static final Logger log = LogManager.getLogger(jdbcIO.class);

	/**
	 * Inserts an order.
	 * 
	 * @param ordrPrepStmt Already prepared statement to insert.
	 * @param oorder       Object order to insert to take the values from.
	 */
	public void insertOrder(final PreparedStatement ordrPrepStmt, final Oorder oorder) {

		try {

			ordrPrepStmt.setInt(1, oorder.getO_id());
			ordrPrepStmt.setInt(2, oorder.getO_w_id());
			ordrPrepStmt.setInt(3, oorder.getO_d_id());
			ordrPrepStmt.setInt(4, oorder.getO_c_id());
			ordrPrepStmt.setInt(5, oorder.getO_carrier_id());
			ordrPrepStmt.setInt(6, oorder.getO_ol_cnt());
			ordrPrepStmt.setInt(7, oorder.getO_all_local());
			final Timestamp entry_d = new Timestamp(oorder.getO_entry_d());
			ordrPrepStmt.setTimestamp(8, entry_d);

			ordrPrepStmt.addBatch();

		} catch (SQLException se) {
			log.error("Error in SQL", se);
		}

	} // end insertOrder()

	/**
	 * Inserts a new order.
	 * 
	 * @param nworPrepStmt Already prepared statement to insert.
	 * @param new_order    Object new order to take the values from.
	 */
	public void insertNewOrder(final PreparedStatement nworPrepStmt, final NewOrder new_order) {

		try {
			nworPrepStmt.setInt(1, new_order.getNo_w_id());
			nworPrepStmt.setInt(2, new_order.getNo_d_id());
			nworPrepStmt.setInt(3, new_order.getNo_o_id());

			nworPrepStmt.addBatch();

		} catch (SQLException se) {
			log.error("Error in SQL", se);
		}

	} // end insertNewOrder()

	/**
	 * Inserts an order line.
	 * 
	 * @param orlnPrepStmt Already prepared statement to insert.
	 * @param order_line   Object order line to take the values from.
	 */
	public void insertOrderLine(final PreparedStatement orlnPrepStmt, final OrderLine order_line) {

		try {
			orlnPrepStmt.setInt(1, order_line.getOl_w_id());
			orlnPrepStmt.setInt(2, order_line.getOl_d_id());
			orlnPrepStmt.setInt(3, order_line.getOl_o_id());
			orlnPrepStmt.setInt(4, order_line.getOl_number());
			orlnPrepStmt.setLong(5, order_line.getOl_i_id());

			final Timestamp delivery_d = new Timestamp(order_line.getOl_delivery_d());
			orlnPrepStmt.setTimestamp(6, delivery_d);

			orlnPrepStmt.setDouble(7, order_line.getOl_amount());
			orlnPrepStmt.setLong(8, order_line.getOl_supply_w_id());
			orlnPrepStmt.setDouble(9, order_line.getOl_quantity());
			orlnPrepStmt.setString(10, order_line.getOl_dist_info());

			orlnPrepStmt.addBatch();

		} catch (SQLException se) {
			log.error("Error in SQL", se);
		}

	} // end insertOrderLine()

} // end class jdbcIO()
