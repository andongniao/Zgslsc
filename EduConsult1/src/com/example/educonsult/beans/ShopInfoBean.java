package com.example.educonsult.beans;
/**
 * ����
 * @author Administrator
 *
 */
public class ShopInfoBean {
	private String userid;
	private String username;
	private String company;
	private int collect;		//�Ƿ��ղ�1Ϊ��0Ϊ��
	private int grade;			//������
	private int describe;		//��������
	private int service;		//��������
	private int logistics;		//��������
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
