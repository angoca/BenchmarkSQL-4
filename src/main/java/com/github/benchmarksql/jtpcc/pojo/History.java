package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

public class History implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = 7605103867655461879L;
	public float h_amount;
	public int h_c_d_id;
	public int h_c_id;
	public int h_c_w_id;
	public int h_d_id;
	public String h_data;
	public long h_date;
	public int h_w_id;
	public int hist_id;

	/**
	 * Gets the payment amount.
	 * 
	 * @return Payment amount.
	 */
	public float getH_amount() {
		return h_amount;
	}

	/**
	 * Gets the district id.
	 * 
	 * @return District id.
	 */
	public int getH_c_d_id() {
		return h_c_d_id;
	}

	/**
	 * Gets the customer id.
	 * 
	 * @return Customer id.
	 */
	public int getH_c_id() {
		return h_c_id;
	}

	/**
	 * Gets the warehouse id.
	 * 
	 * @return Warehouse id.
	 */
	public int getH_c_w_id() {
		return h_c_w_id;
	}

	/**
	 * Gets the district id.
	 * 
	 * @return District id.
	 */
	public int getH_d_id() {
		return h_d_id;
	}

	/**
	 * Gets miscellaneous information.
	 * 
	 * @return Miscellaneous information.
	 */
	public String getH_data() {
		return h_data;
	}

	/**
	 * Gets the date.
	 * 
	 * @return Date.
	 */
	public long getH_date() {
		return h_date;
	}

	/**
	 * Gets the warehouse id.
	 * 
	 * @return Warehouse id.
	 */
	public int getH_w_id() {
		return h_w_id;
	}

	/**
	 * Gets the history id.
	 * 
	 * @return History id.
	 */
	public int getHist_id() {
		return hist_id;
	}

	/**
	 * Sets the payment amount.
	 * 
	 * @param h_amount Payment amount.
	 */
	public void setH_amount(float h_amount) {
		this.h_amount = h_amount;
	}

	/**
	 * Sets the district id.
	 * 
	 * @param h_c_d_id District id.
	 */
	public void setH_c_d_id(int h_c_d_id) {
		this.h_c_d_id = h_c_d_id;
	}

	/**
	 * Sets the customer id.
	 * 
	 * @param h_c_id Customer id.
	 */
	public void setH_c_id(int h_c_id) {
		this.h_c_id = h_c_id;
	}

	/**
	 * Sets the warehouse id.
	 * 
	 * @return Warehouse id.
	 * @param h_c_w_id Warehouse id.
	 */
	public void setH_c_w_id(int h_c_w_id) {
		this.h_c_w_id = h_c_w_id;
	}

	/**
	 * Sets the district id.
	 * 
	 * @param h_d_id District id.
	 */
	public void setH_d_id(int h_d_id) {
		this.h_d_id = h_d_id;
	}

	/**
	 * Sets miscellaneous information.
	 * 
	 * @param h_data Miscellaneous information.
	 */
	public void setH_data(String h_data) {
		this.h_data = h_data;
	}

	/**
	 * Sets the date.
	 * 
	 * @param h_date Date.
	 */
	public void setH_date(long h_date) {
		this.h_date = h_date;
	}

	/**
	 * Sets the warehouse id.
	 * 
	 * @param h_w_id Warehouse id.
	 */
	public void setH_w_id(int h_w_id) {
		this.h_w_id = h_w_id;
	}

	/**
	 * Sets the history id.
	 * 
	 * @param hist_id History id.
	 */
	public void setHist_id(int hist_id) {
		this.hist_id = hist_id;
	}

	/**
	 * Describes the warehouse.
	 */
	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer("");
		ret.append("\n***************** History ********************");
		ret.append("\n*   h_c_id = " + hist_id);
		ret.append("\n*   h_c_id = " + h_c_id);
		ret.append("\n* h_c_d_id = " + h_c_d_id);
		ret.append("\n* h_c_w_id = " + h_c_w_id);
		ret.append("\n*   h_d_id = " + h_d_id);
		ret.append("\n*   h_w_id = " + h_w_id);
		ret.append("\n*   h_date = " + h_date);
		ret.append("\n* h_amount = " + h_amount);
		ret.append("\n*   h_data = " + h_data);
		ret.append("\n**********************************************");
		return ret.toString();
	}

}
