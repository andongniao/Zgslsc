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
	private String value;//��ֵ
	private String payment;//ֻ�м۸���ڴ����Ե�ʱ�����ʹ���Ż�ȯ
	private String rangecatid;
	private String rangecatname;
	private String expiretime;//ʱ���ʽ2015-08-05
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
