package com.xunbo.store.beans;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 购物车店铺实体
 * @author Qzr
 *
 */
public class ShopBean extends ShopItemBean implements Serializable{
	private ArrayList<ShopItemBean>mall;	//商品列表
	private String company;					//店家名称
	private String companyid;				//店家ID
	private String note;					//店家留言
	private ArrayList<ExpressBean> express;//运费模板
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
	public ArrayList<ExpressBean> getExpress() {
		return express;
	}
	public void setExpress(ArrayList<ExpressBean> express) {
		this.express = express;
	}
	
}
