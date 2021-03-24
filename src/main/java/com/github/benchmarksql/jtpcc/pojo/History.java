package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

public class History implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = 7605103867655461879L;
	public int hist_id;
	public int h_c_id;
	public int h_c_d_id;
	public int h_c_w_id;
	public int h_d_id;
	public int h_w_id;
	public long h_date;
	public float h_amount;
	public String h_data;

	public String toString() {
		String ret = "";
		ret += "\n***************** History ********************";
		ret += "\n*   h_c_id = " + hist_id;
		ret += "\n*   h_c_id = " + h_c_id;
		ret += "\n* h_c_d_id = " + h_c_d_id;
		ret += "\n* h_c_w_id = " + h_c_w_id;
		ret += "\n*   h_d_id = " + h_d_id;
		ret += "\n*   h_w_id = " + h_w_id;
		ret += "\n*   h_date = " + h_date;
		ret += "\n* h_amount = " + h_amount;
		ret += "\n*   h_data = " + h_data;
		ret += "\n**********************************************";
		return (ret);
	}

} // end History