package com.xunbo.store.beans;

import java.io.Serializable;

/**
 *	优惠券实体 
 * @author Qzr
 *
 */
public class CouponBean implements Serializable{
	private boolean isck;
	private String id;//优惠券iD
	private String value;//面值
	private String payment;//只有价格大于此属性的时候可以使用优惠券
	private String rangecatid;
	private String rangecatname;
	private String expiretime;//时间格式2015-08-05
	public boolean isIsck() {
		return isck;
	}
	public void setIsck(boolean isck) {
		this.isck = isck;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getRangecatid() {
		return rangecatid;
	}
	public void setRangecatid(String rangecatid) {
		this.rangecatid = rangecatid;
	}
	public String getRangecatname() {
		return rangecatname;
	}
	public void setRangecatname(String rangecatname) {
		this.rangecatname = rangecatname;
	}
	public String getExpiretime() {
		return expiretime;
	}
	public void setExpiretime(String expiretime) {
		this.expiretime = expiretime;
	}
	
}
