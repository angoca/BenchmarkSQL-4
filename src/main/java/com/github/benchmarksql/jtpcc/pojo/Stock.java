package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;

/**
 * Each stock is distributed in 10 districts.
 */
public class Stock implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = 2517524050543329694L;
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
	/**
	 * Id of the stock. Part of the primary key.
	 */
	public int s_i_id;
	public int s_order_cnt;
	public int s_quantity;
	public int s_remote_cnt;
	/**
	 * Id of the warehouse. Part of the primary key.
	 */
	public int s_w_id;
	public float s_ytd;

	/**
	 * Gets the data, which 10% contain the string ORIGINAL.
	 * 
	 * @return Data.
	 */
	public String getS_data() {
		return s_data;
	}

	/**
	 * Gets the dist_01. Random string of 24 characters.
	 * 
	 * @return Dist_01.
	 */
	public String getS_dist_01() {
		return s_dist_01;
	}

	/**
	 * Gets the dist_02. Random string of 24 characters.
	 * 
	 * @return Dist_02.
	 */
	public String getS_dist_02() {
		return s_dist_02;
	}

	/**
	 * Gets the dist_03. Random string of 24 characters.
	 * 
	 * @return Dist_03.
	 */
	public String getS_dist_03() {
		return s_dist_03;
	}

	/**
	 * Gets the dist_04. Random string of 24 characters.
	 * 
	 * @return Dist_04.
	 */
	public String getS_dist_04() {
		return s_dist_04;
	}

	/**
	 * Gets the dist_05. Random string of 24 characters.
	 * 
	 * @return Dist_05.
	 */
	public String getS_dist_05() {
		return s_dist_05;
	}

	/**
	 * Gets the dist_06. Random string of 24 characters.
	 * 
	 * @return Dist_06.
	 */
	public String getS_dist_06() {
		return s_dist_06;
	}

	/**
	 * Gets the dist_07. Random string of 24 characters.
	 * 
	 * @return Dist_07.
	 */
	public String getS_dist_07() {
		return s_dist_07;
	}

	/**
	 * Gets the dist_08. Random string of 24 characters.
	 * 
	 * @return Dist_08.
	 */
	public String getS_dist_08() {
		return s_dist_08;
	}

	/**
	 * Gets the dist_09. Random string of 24 characters.
	 * 
	 * @return Dist_09.
	 */
	public String getS_dist_09() {
		return s_dist_09;
	}

	/**
	 * Gets the dist_10. Random string of 24 characters.
	 * 
	 * @return Dist_10.
	 */
	public String getS_dist_10() {
		return s_dist_10;
	}

	/**
	 * @return
	 */
	public int getS_i_id() {
		return s_i_id;
	}

	/**
	 * Gets the quantity of (home) orders.
	 * 
	 * @return Quantity of orders.
	 */
	public int getS_order_cnt() {
		return s_order_cnt;
	}

	/**
	 * Gets the quantity. Random numbers between 10 and 100.
	 * 
	 * @return Quantity.
	 */
	public int getS_quantity() {
		return s_quantity;
	}

	/**
	 * Gets the quantity of remote orders.
	 * 
	 * @return Quantity of remote orders.
	 */
	public int getS_remote_cnt() {
		return s_remote_cnt;
	}

	/**
	 * Gets the warehouse id.
	 * 
	 * @return Warehouse id.
	 */
	public int getS_w_id() {
		return s_w_id;
	}

	/**
	 * Gets the year to date value.
	 * 
	 * @return Year to date.
	 */
	public float getS_ytd() {
		return s_ytd;
	}

	/**
	 * Sets the data.
	 * 
	 * @param s_data data.
	 */
	public void setS_data(String s_data) {
		this.s_data = s_data;
	}

	/**
	 * Sets the dist_01.
	 * 
	 * @param s_dist_01 Dist_01.
	 */
	public void setS_dist_01(String s_dist_01) {
		this.s_dist_01 = s_dist_01;
	}

	/**
	 * Sets the dist_02.
	 * 
	 * @param s_dist_02 Dist_02.
	 */
	public void setS_dist_02(String s_dist_02) {
		this.s_dist_02 = s_dist_02;
	}

	/**
	 * Sets the dist_03.
	 * 
	 * @param s_dist_03 Dist_03.
	 */
	public void setS_dist_03(String s_dist_03) {
		this.s_dist_03 = s_dist_03;
	}

	/**
	 * Sets the dist_04.
	 * 
	 * @param s_dist_04 Dist_04.
	 */
	public void setS_dist_04(String s_dist_04) {
		this.s_dist_04 = s_dist_04;
	}

	/**
	 * Sets the dist_05.
	 * 
	 * @param s_dist_05 Dist_05.
	 */
	public void setS_dist_05(String s_dist_05) {
		this.s_dist_05 = s_dist_05;
	}

	/**
	 * Sets the dist_06.
	 * 
	 * @param s_dist_06 Dist_06.
	 */
	public void setS_dist_06(String s_dist_06) {
		this.s_dist_06 = s_dist_06;
	}

	/**
	 * Sets the dist_07.
	 * 
	 * @param s_dist_07 Dist_07.
	 */
	public void setS_dist_07(String s_dist_07) {
		this.s_dist_07 = s_dist_07;
	}

	/**
	 * Sets the dist_08.
	 * 
	 * @param s_dist_08 Dist_08.
	 */
	public void setS_dist_08(String s_dist_08) {
		this.s_dist_08 = s_dist_08;
	}

	/**
	 * Sets the dist_09.
	 * 
	 * @param s_dist_09 Dist_09.
	 */
	public void setS_dist_09(String s_dist_09) {
		this.s_dist_09 = s_dist_09;
	}

	/**
	 * Sets the dist_10.
	 * 
	 * @param s_dist_10 Dist_10.
	 */
	public void setS_dist_10(String s_dist_10) {
		this.s_dist_10 = s_dist_10;
	}

	/**
	 * @param s_i_id
	 */
	public void setS_i_id(int s_i_id) {
		this.s_i_id = s_i_id;
	}

	/**
	 * Sets the quantity of (home) orders.
	 * 
	 * @param s_order_cnt Quantity of orders.
	 */
	public void setS_order_cnt(int s_order_cnt) {
		this.s_order_cnt = s_order_cnt;
	}

	/**
	 * Sets the quantity. Random numbers between 10 and 100.
	 * 
	 * @param s_quantity Quantity.
	 */
	public void setS_quantity(int s_quantity) {
		this.s_quantity = s_quantity;
	}

	/**
	 * Sets the quantity of remote orders.
	 * 
	 * @param s_remote_cnt Quantity of remote orders.
	 */
	public void setS_remote_cnt(int s_remote_cnt) {
		this.s_remote_cnt = s_remote_cnt;
	}

	/**
	 * Sets the warehouse id.
	 * 
	 * @param s_w_id Warehouse id.
	 */
	public void setS_w_id(int s_w_id) {
		this.s_w_id = s_w_id;
	}

	/**
	 * Sets the year to date value.
	 * 
	 * @param s_ytd Year to date.
	 */
	public void setS_ytd(float s_ytd) {
		this.s_ytd = s_ytd;
	}

	/**
	 * Describes the stock.
	 */
	@Override
	public String toString() {
		StringBuffer ret = new StringBuffer("");
		ret.append("\n***************** Stock ********************");
		ret.append("\n*       s_i_id = " + s_i_id);
		ret.append("\n*       s_w_id = " + s_w_id);
		ret.append("\n*   s_quantity = " + s_quantity);
		ret.append("\n*        s_ytd = " + s_ytd);
		ret.append("\n*  s_order_cnt = " + s_order_cnt);
		ret.append("\n* s_remote_cnt = " + s_remote_cnt);
		ret.append("\n*       s_data = " + s_data);
		ret.append("\n*    s_dist_01 = " + s_dist_01);
		ret.append("\n*    s_dist_02 = " + s_dist_02);
		ret.append("\n*    s_dist_03 = " + s_dist_03);
		ret.append("\n*    s_dist_04 = " + s_dist_04);
		ret.append("\n*    s_dist_05 = " + s_dist_05);
		ret.append("\n*    s_dist_06 = " + s_dist_06);
		ret.append("\n*    s_dist_07 = " + s_dist_07);
		ret.append("\n*    s_dist_08 = " + s_dist_08);
		ret.append("\n*    s_dist_09 = " + s_dist_09);
		ret.append("\n*    s_dist_10 = " + s_dist_10);
		ret.append("\n**********************************************");
		return ret.toString();
	}

}
