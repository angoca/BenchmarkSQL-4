package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

public class Warehouse implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = 933859184143183210L;
	public String w_city;
	public int w_id; // PRIMARY KEY

	public String w_name;

	public String w_state;

	public String w_street_1;

	public String w_street_2;

	public float w_tax;

	public float w_ytd;

	public String w_zip;

	/**
	 * @return
	 */
	public String getW_city() {
		return w_city;
	}

	/**
	 * @return
	 */
	public int getW_id() {
		return w_id;
	}

	/**
	 * @return
	 */
	public String getW_name() {
		return w_name;
	}

	/**
	 * @return
	 */
	public String getW_state() {
		return w_state;
	}

	/**
	 * @return
	 */
	public String getW_street_1() {
		return w_street_1;
	}

	/**
	 * @return
	 */
	public String getW_street_2() {
		return w_street_2;
	}

	/**
	 * @return
	 */
	public float getW_tax() {
		return w_tax;
	}

	/**
	 * @return
	 */
	public float getW_ytd() {
		return w_ytd;
	}

	/**
	 * @return
	 */
	public String getW_zip() {
		return w_zip;
	}

	/**
	 * @param w_city
	 */
	public void setW_city(String w_city) {
		this.w_city = w_city;
	}

	/**
	 * @param w_id
	 */
	public void setW_id(int w_id) {
		this.w_id = w_id;
	}

	/**
	 * @param w_name
	 */
	public void setW_name(String w_name) {
		this.w_name = w_name;
	}

	/**
	 * @param w_state
	 */
	public void setW_state(String w_state) {
		this.w_state = w_state;
	}

	/**
	 * @param w_street_1
	 */
	public void setW_street_1(String w_street_1) {
		this.w_street_1 = w_street_1;
	}

	/**
	 * @param w_street_2
	 */
	public void setW_street_2(String w_street_2) {
		this.w_street_2 = w_street_2;
	}

	/**
	 * @param w_tax
	 */
	public void setW_tax(float w_tax) {
		this.w_tax = w_tax;
	}

	/**
	 * @param w_ytd
	 */
	public void setW_ytd(float w_ytd) {
		this.w_ytd = w_ytd;
	}

	/**
	 * @param w_zip
	 */
	public void setW_zip(String w_zip) {
		this.w_zip = w_zip;
	}

	/**
	 *
	 */
	@Override
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