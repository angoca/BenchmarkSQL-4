package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

public class Item implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = 3640594164299091999L;
	public int i_id; // PRIMARY KEY
	public int i_im_id;
	public float i_price;
	public String i_name;
	public String i_data;

	public String toString() {
		String ret = "";
		ret += "\n***************** Item ********************";
		ret += "\n*    i_id = " + i_id;
		ret += "\n*  i_name = " + i_name;
		ret += "\n* i_price = " + i_price;
		ret += "\n*  i_data = " + i_data;
		ret += "\n* i_im_id = " + i_im_id;
		ret += "\n**********************************************";
		return (ret);
	}

} // end Item