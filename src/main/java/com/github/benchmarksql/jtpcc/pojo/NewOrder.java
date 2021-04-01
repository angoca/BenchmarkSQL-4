package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

public class NewOrder implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = -5735456083947093098L;
	public int no_w_id;
	public int no_d_id;
	public int no_o_id;

	public String toString() {
		String ret = "";
		ret += "\n***************** NewOrder ********************";
		ret += "\n*      no_w_id = " + no_w_id;
		ret += "\n*      no_d_id = " + no_d_id;
		ret += "\n*      no_o_id = " + no_o_id;
		ret += "\n**********************************************";
		return ret;
	}

} // end NewOrder