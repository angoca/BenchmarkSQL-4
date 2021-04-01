package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

public class OrderLine implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = -7555840082316032135L;
	public int ol_w_id;
	public int ol_d_id;
	public int ol_o_id;
	public int ol_number;
	public int ol_i_id;
	public int ol_supply_w_id;
	public int ol_quantity;
	public long ol_delivery_d;
	public float ol_amount;
	public String ol_dist_info;

	public String toString() {
		String ret = "";
		ret += "\n***************** OrderLine ********************";
		ret += "\n*        ol_w_id = " + ol_w_id;
		ret += "\n*        ol_d_id = " + ol_d_id;
		ret += "\n*        ol_o_id = " + ol_o_id;
		ret += "\n*      ol_number = " + ol_number;
		ret += "\n*        ol_i_id = " + ol_i_id;
		ret += "\n*  ol_delivery_d = " + ol_delivery_d;
		ret += "\n*      ol_amount = " + ol_amount;
		ret += "\n* ol_supply_w_id = " + ol_supply_w_id;
		ret += "\n*    ol_quantity = " + ol_quantity;
		ret += "\n*   ol_dist_info = " + ol_dist_info;
		ret += "\n**********************************************";
		return ret;
	}

} // end OrderLine