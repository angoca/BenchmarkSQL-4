package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

public class Oorder implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = -4630864674295770034L;
	public int o_id;
	public int o_w_id;
	public int o_d_id;
	public int o_c_id;
	public int o_carrier_id;
	public int o_ol_cnt;
	public int o_all_local;
	public long o_entry_d;

	public String toString() {
		java.sql.Timestamp entry_d = new java.sql.Timestamp(o_entry_d);

		String ret = "";
		ret += "\n***************** Oorder ********************";
		ret += "\n*         o_id = " + o_id;
		ret += "\n*       o_w_id = " + o_w_id;
		ret += "\n*       o_d_id = " + o_d_id;
		ret += "\n*       o_c_id = " + o_c_id;
		ret += "\n* o_carrier_id = " + o_carrier_id;
		ret += "\n*     o_ol_cnt = " + o_ol_cnt;
		ret += "\n*  o_all_local = " + o_all_local;
		ret += "\n*    o_entry_d = " + entry_d;
		ret += "\n**********************************************";
		return ret;
	}

} // end Oorder