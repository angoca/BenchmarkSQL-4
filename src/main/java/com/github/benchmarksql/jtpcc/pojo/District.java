package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

public class District implements Serializable {

	/**
	 * Generated Id.
	 * 
	 */
	private static final long serialVersionUID = 2576074839582492020L;
	public int d_id;
	public int d_w_id;
	public int d_next_o_id;
	public float d_ytd;
	public float d_tax;
	public String d_name;
	public String d_street_1;
	public String d_street_2;
	public String d_city;
	public String d_state;
	public String d_zip;

	public String toString() {
		String ret = "";
		ret += "\n***************** District ********************";
		ret += "\n*        d_id = " + d_id;
		ret += "\n*      d_w_id = " + d_w_id;
		ret += "\n*       d_ytd = " + d_ytd;
		ret += "\n*       d_tax = " + d_tax;
		ret += "\n* d_next_o_id = " + d_next_o_id;
		ret += "\n*      d_name = " + d_name;
		ret += "\n*  d_street_1 = " + d_street_1;
		ret += "\n*  d_street_2 = " + d_street_2;
		ret += "\n*      d_city = " + d_city;
		ret += "\n*     d_state = " + d_state;
		ret += "\n*       d_zip = " + d_zip;
		ret += "\n**********************************************";
		return (ret);
	}

} // end District