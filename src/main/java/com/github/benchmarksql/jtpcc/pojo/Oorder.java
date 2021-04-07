package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Oorder implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = -4630864674295770034L;
	private int o_all_local;
	private int o_c_id;
	private int o_carrier_id;
	private int o_d_id;
	private long o_entry_d;
	private int o_id;
	private int o_ol_cnt;
	private int o_w_id;

	/**
	 * Default constructor.
	 */
	public Oorder() {
		// Empty
	}

	/**
	 * Returns 1 if all order lines are local. 0 otherwise.
	 * 
	 * @return Type of order lines.
	 */
	public int getO_all_local() {
		return o_all_local;
	}

	/**
	 * Gets the customer id.
	 * 
	 * @return customer id.
	 */
	public int getO_c_id() {
		return o_c_id;
	}

	/**
	 * Gets the id of the carrier. A random number between 1 and 10.
	 * 
	 * @return Carrier id.
	 */
	public int getO_carrier_id() {
		return o_carrier_id;
	}

	/**
	 * Gets the district id.
	 * 
	 * @return District id.
	 */
	public int getO_d_id() {
		return o_d_id;
	}

	/**
	 * Gets the entry date of the order.
	 * 
	 * @return Entry date.
	 */
	public long getO_entry_d() {
		return o_entry_d;
	}

	/**
	 * Gets the id of the order.
	 * 
	 * @return Id of the order.
	 */
	public int getO_id() {
		return o_id;
	}

	/**
	 * Gets the number of items.
	 * 
	 * @return Number of items.
	 */
	public int getO_ol_cnt() {
		return o_ol_cnt;
	}

	/**
	 * Gets the warehouse id.
	 * 
	 * @return Warehouse id.
	 */
	public int getO_w_id() {
		return o_w_id;
	}

	/**
	 * Sets the type of orders.
	 * 
	 * @param o_all_local Only local orders - 1, otherwise 0.
	 */
	public void setO_all_local(int o_all_local) {
		this.o_all_local = o_all_local;
	}

	/**
	 * Sets the customer id.
	 * 
	 * @param o_c_id customer id.
	 */
	public void setO_c_id(int o_c_id) {
		this.o_c_id = o_c_id;
	}

	/**
	 * Sets the id of the carrier.
	 * 
	 * @param o_carrier_id Carrier id.
	 */
	public void setO_carrier_id(int o_carrier_id) {
		this.o_carrier_id = o_carrier_id;
	}

	/**
	 * Sets the district id.
	 * 
	 * @param o_d_id District id.
	 */
	public void setO_d_id(int o_d_id) {
		this.o_d_id = o_d_id;
	}

	/**
	 * Sets the entry date of the order.
	 * 
	 * @param o_entry_d Entry date.
	 */
	public void setO_entry_d(long o_entry_d) {
		this.o_entry_d = o_entry_d;
	}

	/**
	 * Sets the id of the order.
	 * 
	 * @param o_id Id of the order.
	 */
	public void setO_id(int o_id) {
		this.o_id = o_id;
	}

	/**
	 * Sets the number of items.
	 * 
	 * @param o_ol_cnt Number of items.
	 */
	public void setO_ol_cnt(int o_ol_cnt) {
		this.o_ol_cnt = o_ol_cnt;
	}

	/**
	 * Sets the warehouse id.
	 * 
	 * @param o_w_id Warehouse id.
	 */
	public void setO_w_id(int o_w_id) {
		this.o_w_id = o_w_id;
	}

	/**
	 * Describes the warehouse.
	 */
	@Override
	public String toString() {
		Timestamp entry_d = new Timestamp(o_entry_d);

		StringBuffer ret = new StringBuffer("");
		ret.append("\n***************** Oorder ********************");
		ret.append("\n*         o_id = " + o_id);
		ret.append("\n*       o_w_id = " + o_w_id);
		ret.append("\n*       o_d_id = " + o_d_id);
		ret.append("\n*       o_c_id = " + o_c_id);
		ret.append("\n* o_carrier_id = " + o_carrier_id);
		ret.append("\n*     o_ol_cnt = " + o_ol_cnt);
		ret.append("\n*  o_all_local = " + o_all_local);
		ret.append("\n*    o_entry_d = " + entry_d);
		ret.append("\n**********************************************");
		return ret.toString();
	}

}
