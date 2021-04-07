package com.github.benchmarksql.jtpcc.pojo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Object Customer in the TPC-C specification.
 * 
 * @author lussman
 */
public class Customer implements Serializable {

	/**
	 * Generated Id.
	 */
	private static final long serialVersionUID = -8122701038727177452L;
	private float c_balance;
	private String c_city;
	private String c_credit;
	private float c_credit_lim;
	private int c_d_id;
	private String c_data;
	private int c_delivery_cnt;
	private float c_discount;
	private String c_first;
	private int c_id;
	private String c_last;
	private String c_middle;
	private int c_payment_cnt;
	private String c_phone;
	private long c_since;
	private String c_state;
	private String c_street_1;
	private String c_street_2;
	private int c_w_id;
	private float c_ytd_payment;
	private String c_zip;

	/**
	 * Default constructor.
	 */
	public Customer() {
		// Empty
	}

	/**
	 * Gets the balance of the customer.
	 * 
	 * @return Balance of the customer.
	 */
	public final float getC_balance() {
		return c_balance;
	}

	/**
	 * Gets the name of the city. A random string between 10 to 20 characters.
	 * 
	 * @return Name of the city.
	 */
	public final String getC_city() {
		return c_city;
	}

	/**
	 * Gets the credit status of the client. TODO convert into enum.
	 * <ul>
	 * <li>GC - good.</li>
	 * <li>BC - bad</li>
	 * </ul>
	 * 
	 * @return Status of the client.
	 */
	public final String getC_credit() {
		return c_credit;
	}

	/**
	 * Gets the credit limit of the client.
	 * 
	 * @return Credit limit of the client.
	 */
	public final float getC_credit_lim() {
		return c_credit_lim;
	}

	/**
	 * Gets the district of the customer.
	 * 
	 * @return District of the customer.
	 */
	public final int getC_d_id() {
		return c_d_id;
	}

	/**
	 * Gets miscellaneous information.
	 * 
	 * @return Miscellaneous information.
	 */
	public final String getC_data() {
		return c_data;
	}

	/**
	 * Gets the quantity of deliveries.
	 * 
	 * @return Quantity of deliveries.
	 */
	public final int getC_delivery_cnt() {
		return c_delivery_cnt;
	}

	/**
	 * Gets the discount rate for the client.
	 * 
	 * @return Discount rate.
	 */
	public final float getC_discount() {
		return c_discount;
	}

	/**
	 * Gets the customer's first name. A random string between 8 and 16 characters.
	 * 
	 * @return Customer's first name.
	 */
	public final String getC_first() {
		return c_first;
	}

	/**
	 * Gets the customer id.
	 * 
	 * @return Id of the customer.
	 */
	public final int getC_id() {
		return c_id;
	}

	/**
	 * Gets the customer's last name.
	 * 
	 * @return Customer's last name.
	 */
	public final String getC_last() {
		return c_last;
	}

	/**
	 * Gets the middle name, which is "OE"
	 * 
	 * @return Middle name.
	 */
	public final String getC_middle() {
		return c_middle;
	}

	/**
	 * Gets the quantity of payments.
	 * 
	 * @return quantity of payments.
	 */
	public final int getC_payment_cnt() {
		return c_payment_cnt;
	}

	/**
	 * Gets the phone number. A random number of 16 digits.
	 * 
	 * @return Phone number.
	 */
	public final String getC_phone() {
		return c_phone;
	}

	/**
	 * Gets timestamp when the customer was populated.
	 * 
	 * @return Date/time when the customer was populated.
	 */
	public final long getC_since() {
		return c_since;
	}

	/**
	 * Sets the name of the state. Random string of 2 characters.
	 * 
	 * @return Name of the state.
	 */
	public final String getC_state() {
		return c_state;
	}

	/**
	 * Gets the customer's address. A random string between 10 to 20 characters.
	 * 
	 * @return Customer's address.
	 */
	public final String getC_street_1() {
		return c_street_1;
	}

	/**
	 * Gets the complement of the address. A random string between 10 to 20
	 * characters.
	 * 
	 * @return Complement of the address.
	 */
	public final String getC_street_2() {
		return c_street_2;
	}

	/**
	 * Gets the id of associated warehouse.
	 * 
	 * @return Id of associated warehouse.
	 */
	public final int getC_w_id() {
		return c_w_id;
	}

	/**
	 * Gets the year to date payment of the customer.
	 * 
	 * @return Year to date payment of the customer.
	 */
	public final float getC_ytd_payment() {
		return c_ytd_payment;
	}

	/**
	 * Gets the zip code. Defined at 4.3.2.7.
	 * 
	 * @return Zip code.
	 */
	public final String getC_zip() {
		return c_zip;
	}

	/**
	 * Sets the balance of the customer.
	 * 
	 * @param c_balance Balance of the customer.
	 */
	public final void setC_balance(final float c_balance) {
		this.c_balance = c_balance;
	}

	/**
	 * Sets the name of the city. A random string between 10 to 20 characters.
	 * 
	 * @param c_city Name of the city.
	 */
	public final void setC_city(final String c_city) {
		this.c_city = c_city;
	}

	/**
	 * Sets the credit status of the client.
	 * <ul>
	 * <li>GC - good.</li>
	 * <li>BC - bad</li>
	 * </ul>
	 * 
	 * @param c_credit Status of the client.
	 */
	public final void setC_credit(final String c_credit) {
		this.c_credit = c_credit;
	}

	/**
	 * Sets the credit limit of the client.
	 * 
	 * @param c_credit_lim Credit limit of the client.
	 */
	public final void setC_credit_lim(final float c_credit_lim) {
		this.c_credit_lim = c_credit_lim;
	}

	/**
	 * Sets the district of the customer.
	 * 
	 * @param c_d_id District of the customer.
	 */
	public final void setC_d_id(final int c_d_id) {
		this.c_d_id = c_d_id;
	}

	/**
	 * Sets miscellaneous information.
	 * 
	 * @param c_data Miscellaneous information.
	 */
	public final void setC_data(final String c_data) {
		this.c_data = c_data;
	}

	/**
	 * Sets the quantity of deliveries.
	 * 
	 * @param c_delivery_cnt Quantity of deliveries.
	 */
	public final void setC_delivery_cnt(final int c_delivery_cnt) {
		this.c_delivery_cnt = c_delivery_cnt;
	}

	/**
	 * Sets the discount rate for the client.
	 * 
	 * @param c_discount Discount rate.
	 */
	public final void setC_discount(final float c_discount) {
		this.c_discount = c_discount;
	}

	/**
	 * Sets the customer's first name. A random string between 8 and 16 characters.
	 * 
	 * @param c_first Customer's first name
	 */
	public final void setC_first(final String c_first) {
		this.c_first = c_first;
	}

	/**
	 * Sets the customer id.
	 * 
	 * @param c_id Id of the customer.
	 */
	public final void setC_id(final int c_id) {
		this.c_id = c_id;
	}

	/**
	 * Sets customer's last name. The generated name is defined in 4.3.2.3.
	 * 
	 * @param c_last Customer's last name.
	 */
	public final void setC_last(final String c_last) {
		this.c_last = c_last;
	}

	/**
	 * Sets the middle name.
	 * 
	 * @param c_middle Middle name.
	 */
	public final void setC_middle(final String c_middle) {
		this.c_middle = c_middle;
	}

	/**
	 * Sets the quantity of payments.
	 * 
	 * @param c_payment_cnt Quantity of payments.
	 */
	public final void setC_payment_cnt(final int c_payment_cnt) {
		this.c_payment_cnt = c_payment_cnt;
	}

	/**
	 * Sets the phone number.
	 * 
	 * @param c_phone Phone number.
	 */
	public final void setC_phone(final String c_phone) {
		this.c_phone = c_phone;
	}

	/**
	 * Sets timestamp when the customer was populated.
	 * 
	 * @param c_since Timestamp when the customer was populated.
	 */
	public final void setC_since(final long c_since) {
		this.c_since = c_since;
	}

	/**
	 * Gets the name of the state.
	 * 
	 * @param c_state Name of the state.
	 */
	public final void setC_state(final String c_state) {
		this.c_state = c_state;
	}

	/**
	 * Sets the customer's address.
	 * 
	 * @param c_street_1 Customer's address.
	 */
	public final void setC_street_1(final String c_street_1) {
		this.c_street_1 = c_street_1;
	}

	/**
	 * Sets the complement of the address.
	 * 
	 * @param c_street_2 Complement of the address.
	 */
	public final void setC_street_2(final String c_street_2) {
		this.c_street_2 = c_street_2;
	}

	/**
	 * Set the id of associated warehouse.
	 * 
	 * @param c_w_id Id of associated warehouse.
	 */
	public final void setC_w_id(final int c_w_id) {
		this.c_w_id = c_w_id;
	}

	/**
	 * Sets the year to date payment of the customer.
	 * 
	 * @param c_ytd_payment Year to date payment of the customer.
	 */
	public final void setC_ytd_payment(final float c_ytd_payment) {
		this.c_ytd_payment = c_ytd_payment;
	}

	/**
	 * Sets the zip code.
	 * 
	 * @param c_zip Zip code.
	 */
	public final void setC_zip(final String c_zip) {
		this.c_zip = c_zip;
	}

	/**
	 * Describes the customer.
	 */
	@Override
	public final String toString() {
		final Timestamp since = new Timestamp(getC_since());

		final StringBuffer ret = new StringBuffer("");
		ret.append("\n***************** Customer ********************");
		ret.append("\n*           c_id = " + this.getC_id());
		ret.append("\n*         c_d_id = " + this.getC_d_id());
		ret.append("\n*         c_w_id = " + this.getC_w_id());
		ret.append("\n*     c_discount = " + this.getC_discount());
		ret.append("\n*       c_credit = " + this.getC_credit());
		ret.append("\n*         c_last = " + this.getC_last());
		ret.append("\n*        c_first = " + this.getC_first());
		ret.append("\n*   c_credit_lim = " + this.getC_credit_lim());
		ret.append("\n*      c_balance = " + this.getC_balance());
		ret.append("\n*  c_ytd_payment = " + this.getC_ytd_payment());
		ret.append("\n*  c_payment_cnt = " + this.getC_payment_cnt());
		ret.append("\n* c_delivery_cnt = " + this.getC_delivery_cnt());
		ret.append("\n*     c_street_1 = " + this.getC_street_1());
		ret.append("\n*     c_street_2 = " + this.getC_street_2());
		ret.append("\n*         c_city = " + this.getC_city());
		ret.append("\n*        c_state = " + this.getC_state());
		ret.append("\n*          c_zip = " + this.getC_zip());
		ret.append("\n*        c_phone = " + this.getC_phone());
		ret.append("\n*        c_since = " + since);
		ret.append("\n*       c_middle = " + this.getC_middle());
		ret.append("\n*         c_data = " + this.getC_data());
		ret.append("\n**********************************************");
		return ret.toString();
	}

}
