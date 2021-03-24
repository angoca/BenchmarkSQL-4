package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

public class Stock implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = 2517524050543329694L;
	public int s_i_id; // PRIMARY KEY 2
	public int s_w_id; // PRIMARY KEY 1
	public int s_order_cnt;
	public int s_remote_cnt;
	public int s_quantity;
	public float s_ytd;
	public String s_data;
	public String s_dist_01;
	public String s_dist_02;
	public String s_dist_03;
	public String s_dist_04;
	public String s_dist_05;
	public String s_dist_06;
	public String s_dist_07;
	public String s_dist_08;
	public String s_dist_09;
	public String s_dist_10;

	public String toString() {
		String ret = "";
		ret += "\n***************** Stock ********************";
		ret += "\n*       s_i_id = " + s_i_id;
		ret += "\n*       s_w_id = " + s_w_id;
		ret += "\n*   s_quantity = " + s_quantity;
		ret += "\n*        s_ytd = " + s_ytd;
		ret += "\n*  s_order_cnt = " + s_order_cnt;
		ret += "\n* s_remote_cnt = " + s_remote_cnt;
		ret += "\n*       s_data = " + s_data;
		ret += "\n*    s_dist_01 = " + s_dist_01;
		ret += "\n*    s_dist_02 = " + s_dist_02;
		ret += "\n*    s_dist_03 = " + s_dist_03;
		ret += "\n*    s_dist_04 = " + s_dist_04;
		ret += "\n*    s_dist_05 = " + s_dist_05;
		ret += "\n*    s_dist_06 = " + s_dist_06;
		ret += "\n*    s_dist_07 = " + s_dist_07;
		ret += "\n*    s_dist_08 = " + s_dist_08;
		ret += "\n*    s_dist_09 = " + s_dist_09;
		ret += "\n*    s_dist_10 = " + s_dist_10;
		ret += "\n**********************************************";
		return (

		ret);
	}

} // end Stock