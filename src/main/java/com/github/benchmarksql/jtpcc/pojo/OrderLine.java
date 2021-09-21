package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

public class OrderLine implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = -7555840082316032135L;
	private float ol_amount;
	private int ol_d_id;
	private long ol_delivery_d;
	private String ol_dist_info;
	private int ol_i_id;
	private int ol_number;
	private int ol_o_id;
	private int ol_quantity;
	private int ol_supply_w_id;
	private int ol_w_id;

	/**
	 * Default constructor.
	 */
	public OrderLine() {
		// Empty
	}

	/**
	 * Gets the amount of items.
	 * 
	 * @return Amount of items.
	 */
	public float getOl_amount() {
		return ol_amount;
	}

	/**
	 * Gets the district Id. 20 unique IDs.
	 * 
	 * @return District id.
	 */
	public int getOl_d_id() {
		return ol_d_id;
	}

	/**
	 * Gets the delivery date. It can be null.
	 * 
	 * @return Delivery date.
	 */
	public long getOl_delivery_d() {
		return ol_delivery_d;
	}

	/**
	 * Gets the information about the district.
	 * 
	 * @return District information.
	 */
	public String getOl_dist_info() {
		return ol_dist_info;
	}

	/**
	 * Gets the item id. 200 000 unique ids.
	 * 
	 * @return Item id.
	 */
	public int getOl_i_id() {
		return ol_i_id;
	}

	/**
	 * Gets the order number.
	 * 
	 * @return Order number.
	 */
	public int getOl_number() {
		return ol_number;
	}

	/**
	 * Gets the order id. 10 000 000 unique ids.
	 * 
	 * @return Order id.
	 */
	public int getOl_o_id() {
		return ol_o_id;
	}

	/**
	 * Gets the quantity for the order line.
	 * 
	 * @return Quantity.
	 */
	public int getOl_quantity() {
		return ol_quantity;
	}

	/**
	 * Gets the warehouse id that supplies.
	 * 
	 * @return Warehouse id.
	 */
	public int getOl_supply_w_id() {
		return ol_supply_w_id;
	}

	/**
	 * Gets the warehouse id. 2 ** quantity of warehouse unique IDs.
	 * 
	 * @return Warehouse id.
	 */
	public int getOl_w_id() {
		return ol_w_id;
	}

	/**
	 * Sets the amount of items.
	 * 
	 * @param ol_amount Amount of items.
	 */
	public void setOl_amount(float ol_amount) {
		this.ol_amount = ol_amount;
	}

	/**
	 * Sets the district Id. 20 unique IDs.
	 * 
	 * @param ol_d_id District id.
	 */
	public void setOl_d_id(int ol_d_id) {
		this.ol_d_id = ol_d_id;
	}

	/**
	 * Sets the delivery date. It can be null.
	 * 
	 * @param ol_delivery_d Delivery date.
	 */
	public void setOl_delivery_d(long ol_delivery_d) {
		this.ol_delivery_d = ol_delivery_d;
	}

	/**
	 * Sets the information about the district.
	 * 
	 * @param ol_dist_info District information.
	 */
	public void setOl_dist_info(String ol_dist_info) {
		this.ol_dist_info = ol_dist_info;
	}

	/**
	 * Sets the item id. 200 000 unique ids.
	 * 
	 * @param ol_i_id Item id.
	 */
	public void setOl_i_id(int ol_i_id) {
		this.ol_i_id = ol_i_id;
	}

	/**
	 * Sets the order number.
	 * 
	 * @param ol_number Order number.
	 */
	public void setOl_number(int ol_number) {
		this.ol_number = ol_number;
	}

	/**
	 * Sets the order id. 10 000 000 unique ids.
	 * 
	 * @return Order id.
	 * @param ol_o_id Order id.
	 */
	public void setOl_o_id(int ol_o_id) {
		this.ol_o_id = ol_o_id;
	}

	/**
	 * Sets the quantity for the order line.
	 * 
	 * @param ol_quantity Quantity.
	 */
	public void setOl_quantity(int ol_quantity) {
		this.ol_quantity = ol_quantity;
	}

	/**
	 * Sets the warehouse id that supplies. s *
	 * 
	 * @param ol_supply_w_id Warehouse id.
	 */
	public void setOl_supply_w_id(int ol_supply_w_id) {
		this.ol_supply_w_id = ol_supply_w_id;
	}

	/**
	 * ets the warehouse id.
	 * 
	 * @param ol_w_id Warehouse id.
	 */
	public void setOl_w_id(int ol_w_id) {
		this.ol_w_id = ol_w_id;
	}

	/**
	 * Describes the order line.
	 */
	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer("");
		ret.append("\n***************** OrderLine ********************");
		ret.append("\n*        ol_w_id = " + ol_w_id);
		ret.append("\n*        ol_d_id = " + ol_d_id);
		ret.append("\n*        ol_o_id = " + ol_o_id);
		ret.append("\n*      ol_number = " + ol_number);
		ret.append("\n*        ol_i_id = " + ol_i_id);
		ret.append("\n*  ol_delivery_d = " + ol_delivery_d);
		ret.append("\n*      ol_amount = " + ol_amount);
		ret.append("\n* ol_supply_w_id = " + ol_supply_w_id);
		ret.append("\n*    ol_quantity = " + ol_quantity);
		ret.append("\n*   ol_dist_info = " + ol_dist_info);
		ret.append("\n**********************************************");
		return ret.toString();
	}

}
