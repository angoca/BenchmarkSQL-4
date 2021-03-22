package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

public class Customer implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = -8122701038727177452L;
	public int c_id;
	public int c_d_id;
	public int c_w_id;
	public int c_payment_cnt;
	public int c_delivery_cnt;
	public long c_since;
	public float c_discount;
	public float c_credit_lim;
	public float c_balance;
	public float c_ytd_payment;
	public String c_credit;
	public String c_last;
	public String c_first;
	public String c_street_1;
	public String c_street_2;
	public String c_city;
	public String c_state;
	public String c_zip;
	public String c_phone;
	public String c_middle;
	public String c_data;

	public String toString() {
		java.sql.Timestamp since = new java.sql.Timestamp(c_since);

		String ret = "";
		ret += "\n***************** Customer ********************";
		ret += "\n*           c_id = " + c_id;
		ret += "\n*         c_d_id = " + c_d_id;
		ret += "\n*         c_w_id = " + c_w_id;
		ret += "\n*     c_discount = " + c_discount;
		ret += "\n*       c_credit = " + c_credit;
		ret += "\n*         c_last = " + c_last;
		ret += "\n*        c_first = " + c_first;
		ret += "\n*   c_credit_lim = " + c_credit_lim;
		ret += "\n*      c_balance = " + c_balance;
		ret += "\n*  c_ytd_payment = " + c_ytd_payment;
		ret += "\n*  c_payment_cnt = " + c_payment_cnt;
		ret += "\n* c_delivery_cnt = " + c_delivery_cnt;
		ret += "\n*     c_street_1 = " + c_street_1;
		ret += "\n*     c_street_2 = " + c_street_2;
		ret += "\n*         c_city = " + c_city;
		ret += "\n*        c_state = " + c_state;
		ret += "\n*          c_zip = " + c_zip;
		ret += "\n*        c_phone = " + c_phone;
		ret += "\n*        c_since = " + since;
		ret += "\n*       c_middle = " + c_middle;
		ret += "\n*         c_data = " + c_data;
		ret += "\n**********************************************";
		return (ret);
	}

} // end Customer