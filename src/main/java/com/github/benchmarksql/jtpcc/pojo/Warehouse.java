package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

public class Warehouse implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = 933859184143183210L;
	public int w_id; // PRIMARY KEY
	public float w_ytd;
	public float w_tax;
	public String w_name;
	public String w_street_1;
	public String w_street_2;
	public String w_city;
	public String w_state;
	public String w_zip;

	public String toString() {
		String ret = "";
		ret += "\n***************** Warehouse ********************";
		ret += "\n*       w_id = " + w_id;
		ret += "\n*      w_ytd = " + w_ytd;
		ret += "\n*      w_tax = " + w_tax;
		ret += "\n*     w_name = " + w_name;
		ret += "\n* w_street_1 = " + w_street_1;
		ret += "\n* w_street_2 = " + w_street_2;
		ret += "\n*     w_city = " + w_city;
		ret += "\n*    w_state = " + w_state;
		ret += "\n*      w_zip = " + w_zip;
		ret += "\n**********************************************";
		return ret;
	}

} // end Warehouse