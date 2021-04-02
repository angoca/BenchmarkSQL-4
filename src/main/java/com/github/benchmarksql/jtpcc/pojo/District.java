package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

public class District implements Serializable {

	/**
	 * Generated Id.
	 * 
	 */
	private static final long serialVersionUID = 2576074839582492020L;
	public String d_city;
	public int d_id;
	public String d_name;
	public int d_next_o_id;
	public String d_state;
	public String d_street_1;
	public String d_street_2;
	public float d_tax;
	public int d_w_id;
	public float d_ytd;
	public String d_zip;

	/**
	 * Gets the city.
	 * 
	 * @return City.
	 */
	public String getD_city() {
		return d_city;
	}

	/**
	 * Gets the district id.
	 * 
	 * @return District id.
	 */
	public int getD_id() {
		return d_id;
	}

	/**
	 * Gets the name.
	 * 
	 * @return Name of the district.
	 */
	public String getD_name() {
		return d_name;
	}

	/**
	 * Gets the next available order.
	 * 
	 * @return Next available order.
	 */
	public int getD_next_o_id() {
		return d_next_o_id;
	}

	/**
	 * Gets the state.
	 * 
	 * @return State.
	 */
	public String getD_state() {
		return d_state;
	}

	/**
	 * Gets the address of the district.
	 * 
	 * @return Address.
	 */
	public String getD_street_1() {
		return d_street_1;
	}

	/**
	 * Gets the complement of the address.
	 * 
	 * @return Complement address.
	 */
	public String getD_street_2() {
		return d_street_2;
	}

	/**
	 * Gets the tax value.
	 * 
	 * @return Tax value.
	 */
	public float getD_tax() {
		return d_tax;
	}

	/**
	 * Gets the warehouse id.
	 * 
	 * @return Warehouse id.
	 */
	public int getD_w_id() {
		return d_w_id;
	}

	/**
	 * Gets the year to date.
	 * 
	 * @return Year to date.
	 */
	public float getD_ytd() {
		return d_ytd;
	}

	/**
	 * Gets the zip code.
	 * 
	 * @return Zip code.
	 */
	public String getD_zip() {
		return d_zip;
	}

	/**
	 * Sets the city.
	 * 
	 * @param d_city City.
	 */
	public void setD_city(String d_city) {
		this.d_city = d_city;
	}

	/**
	 * Sets the district id.
	 * 
	 * @param d _id District id.
	 */
	public void setD_id(int d_id) {
		this.d_id = d_id;
	}

	/**
	 * Sets the name.
	 * 
	 * @param d_name Name of the district.
	 */
	public void setD_name(String d_name) {
		this.d_name = d_name;
	}

	/**
	 * Sets the next available order.
	 * 
	 * @param d_next_o_id Next available order.
	 */
	public void setD_next_o_id(int d_next_o_id) {
		this.d_next_o_id = d_next_o_id;
	}

	/**
	 * Sets the state.
	 * 
	 * @param d_state State.
	 */
	public void setD_state(String d_state) {
		this.d_state = d_state;
	}

	/**
	 * Sets the address of the district.
	 * 
	 * @param d_street_1 Address.
	 */
	public void setD_street_1(String d_street_1) {
		this.d_street_1 = d_street_1;
	}

	/**
	 * Sets the complement of the address.
	 * 
	 * @param d_street_2 Complement address.
	 */
	public void setD_street_2(String d_street_2) {
		this.d_street_2 = d_street_2;
	}

	/**
	 * Sets the tax value.
	 * 
	 * @param d_tax Tax value.
	 */
	public void setD_tax(float d_tax) {
		this.d_tax = d_tax;
	}

	/**
	 * Sets the warehouse id.
	 * 
	 * @param d_w_id Warehouse id.
	 */
	public void setD_w_id(int d_w_id) {
		this.d_w_id = d_w_id;
	}

	/**
	 * Sets the year to date.
	 * 
	 * @param d_ytd Year to date.
	 */
	public void setD_ytd(float d_ytd) {
		this.d_ytd = d_ytd;
	}

	/**
	 * Sets the zip code.
	 * 
	 * @param d_zip Zip code.
	 */
	public void setD_zip(String d_zip) {
		this.d_zip = d_zip;
	}

	/**
	 * Describes the warehouse.
	 */
	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer("");
		ret.append("\n***************** District ********************");
		ret.append("\n*        d_id = " + d_id);
		ret.append("\n*      d_w_id = " + d_w_id);
		ret.append("\n*       d_ytd = " + d_ytd);
		ret.append("\n*       d_tax = " + d_tax);
		ret.append("\n* d_next_o_id = " + d_next_o_id);
		ret.append("\n*      d_name = " + d_name);
		ret.append("\n*  d_street_1 = " + d_street_1);
		ret.append("\n*  d_street_2 = " + d_street_2);
		ret.append("\n*      d_city = " + d_city);
		ret.append("\n*     d_state = " + d_state);
		ret.append("\n*       d_zip = " + d_zip);
		ret.append("\n**********************************************");
		return ret.toString();
	}

}
