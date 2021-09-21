package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

public class NewOrder implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = -5735456083947093098L;
	private int no_d_id;
	private int no_o_id;
	private int no_w_id;

	/**
	 * Default constructor.
	 */
	public NewOrder() {
		// Empty
	}

	/**
	 * Gets the district id.
	 * 
	 * @return District id.
	 */
	public int getNo_d_id() {
		return no_d_id;
	}

	/**
	 * Gets the id of the order.
	 * 
	 * @return Order id.
	 */
	public int getNo_o_id() {
		return no_o_id;
	}

	/**
	 * Gets the warehouse id.
	 * 
	 * @return Warehouse id.
	 */
	public int getNo_w_id() {
		return no_w_id;
	}

	/**
	 * Sets the district id.
	 * 
	 * @param no_d_id District id.
	 */
	public void setNo_d_id(int no_d_id) {
		this.no_d_id = no_d_id;
	}

	/**
	 * sets the id of the order.
	 * 
	 * @param no_o_id Order id.
	 */
	public void setNo_o_id(int no_o_id) {
		this.no_o_id = no_o_id;
	}

	/**
	 * Sets the warehouse id.
	 * 
	 * @param no_w_id Warehouse id.
	 */
	public void setNo_w_id(int no_w_id) {
		this.no_w_id = no_w_id;
	}

	/**
	 * Describes the warehouse.
	 */
	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer("");
		ret.append("\n***************** NewOrder ********************");
		ret.append("\n*      no_w_id = " + no_w_id);
		ret.append("\n*      no_d_id = " + no_d_id);
		ret.append("\n*      no_o_id = " + no_o_id);
		ret.append("\n**********************************************");
		return ret.toString();
	}

}
