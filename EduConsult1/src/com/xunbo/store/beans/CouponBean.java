package com.xunbo.store.beans;

import java.io.Serializable;

/**
 *	�Ż�ȯʵ�� 
 * @author Qzr
 *
 */
public class CouponBean implements Serializable{
	private boolean isck;
	private String id;//�Ż�ȯiD
	private String userid;
	private String couponid;
	private String value;
	private String payment;//ֻ�м۸���ڴ����Ե�ʱ�����ʹ���Ż�ȯ
	private String rangecatid;
	private String repeats;
	private String expiretime;//ʱ���ʽ2015-08-05
	private String used;
	private String itemid;
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
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCouponid() {
		return couponid;
	}
	public void setCouponid(String couponid) {
		this.couponid = couponid;
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
	public String getRepeats() {
		return repeats;
	}
	public void setRepeats(String repeats) {
		this.repeats = repeats;
	}
	public String getExpiretime() {
		return expiretime;
	}
	public void setExpiretime(String expiretime) {
		this.expiretime = expiretime;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	
}
