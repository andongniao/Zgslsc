package com.example.educonsult.beans;

import java.util.ArrayList;

public class ShopBean extends ShopItemBean{
	private ArrayList<ShopItemBean>mall;
	private String company;
	private String companyid;
	private String note;
	public ArrayList<ShopItemBean> getMall() {
		return mall;
	}
	public void setMall(ArrayList<ShopItemBean> mall) {
		this.mall = mall;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
}
