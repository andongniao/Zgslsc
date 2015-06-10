package com.example.educonsult.beans;
/**
 * 店铺
 * @author Administrator
 *
 */
public class ShopInfoBean {
	private String userid;
	private String username;
	private String company;
	private int collect;		//是否收藏1为是0为否
	private int grade;			//好评率
	private int describe;		//描述评分
	private int service;		//服务评分
	private int logistics;		//物流评分
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
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public int getDescribe() {
		return describe;
	}
	public void setDescribe(int describe) {
		this.describe = describe;
	}
	public int getService() {
		return service;
	}
	public void setService(int service) {
		this.service = service;
	}
	public int getLogistics() {
		return logistics;
	}
	public void setLogistics(int logistics) {
		this.logistics = logistics;
	}

}
