package com.xunbo.store.beans;
/**
 * ����
 * @author Administrator
 *
 */
public class ShopInfoBean {
	private String userid;
	private String username;
	private String company;
	private int collect;		//�Ƿ��ղ�0Ϊ��1Ϊ��
	private String thumb;		//img
	private String vip;			//vip
	private String validated;	//ʵ����֤
	private String bond;		//��֤��
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public int getCollect() {
		return collect;
	}
	public void setCollect(int collect) {
		this.collect = collect;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getVip() {
		return vip;
	}
	public void setVip(String vip) {
		this.vip = vip;
	}
	public String getValidated() {
		return validated;
	}
	public void setValidated(String validated) {
		this.validated = validated;
	}
	public String getBond() {
		return bond;
	}
	public void setBond(String bond) {
		this.bond = bond;
	}
	
//	private String grade;		//������
//	private int describe;		//��������
//	private int service;		//��������
//	private int logistics;		//��������
//	private String totalgoods;	//��Ʒ����
	

}
