package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

public class Item implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = 3640594164299091999L;
	public String i_data;
	public int i_id; // PRIMARY KEY
	public int i_im_id;
	public String i_name;
	public float i_price;

	/**
	 * Gets the brand information.
	 * 
	 * @return Brand information.
	 */
	public String getI_data() {
		return i_data;
	}

	/**
	 * Gets the id of the item.
	 * 
	 * @return Item id.
	 */
	public int getI_id() {
		return i_id;
	}

	/**
	 * Gets the image id.
	 * 
	 * @return Image id.
	 */
	public int getI_im_id() {
		return i_im_id;
	}

	/**
	 * Gets the name of the item.
	 * 
	 * @return Item's name.
	 */
	public String getI_name() {
		return i_name;
	}

	/**
	 * Gets the price of the item.
	 * 
	 * @return Price of the item.
	 */
	public float getI_price() {
		return i_price;
	}

	/**
	 * Sets the brand information.
	 * 
	 * @param i_data Brand information.
	 */
	public void setI_data(String i_data) {
		this.i_data = i_data;
	}

	/**
	 * Sets the id of the item.
	 * 
	 * @param i_id Item id.
	 */
	public void setI_id(int i_id) {
		this.i_id = i_id;
	}

	/**
	 * Set the image id.
	 * 
	 * @param i_im_id Image id.
	 */
	public void setI_im_id(int i_im_id) {
		this.i_im_id = i_im_id;
	}

	/**
	 * Sets the name of the item.
	 * 
	 * @param i_name Item's name.
	 * 
	 */
	public void setI_name(String i_name) {
		this.i_name = i_name;
	}

	/**
	 * Sets the price of the item.
	 * 
	 * @param i_price Price of the item.
	 */
	public void setI_price(float i_price) {
		this.i_price = i_price;
	}

	/**
	 * Describes the warehouse.
	 */
	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer("");
		ret.append("\n***************** Item ********************");
		ret.append("\n*    i_id = " + i_id);
		ret.append("\n*  i_name = " + i_name);
		ret.append("\n* i_price = " + i_price);
		ret.append("\n*  i_data = " + i_data);
		ret.append("\n* i_im_id = " + i_im_id);
		ret.append("\n**********************************************");
		return ret.toString();
	}

}
