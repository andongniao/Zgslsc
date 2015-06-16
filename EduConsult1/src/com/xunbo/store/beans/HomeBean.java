package com.xunbo.store.beans;

import java.util.ArrayList;


/**
 * Ê×Ò³ÊµÌå
 * @author Qzr
 *
 */
public class HomeBean extends BaseBean{
	private ArrayList<ProductBean> recommend;
	private ArrayList<ProductBean> hot;
	private ArrayList<CompanyBean> company ;
	private ArrayList<ProductBean> mylike;
	private ArrayList<String> ad;
	public ArrayList<ProductBean> getRecommend() {
		return recommend;
	}
	public void setRecommend(ArrayList<ProductBean> recommend) {
		this.recommend = recommend;
	}
	public ArrayList<ProductBean> getHot() {
		return hot;
	}
	public void setHot(ArrayList<ProductBean> hot) {
		this.hot = hot;
	}
	public ArrayList<CompanyBean> getCompany() {
		return company;
	}
	public void setCompany(ArrayList<CompanyBean> company) {
		this.company = company;
	}
	public ArrayList<ProductBean> getMylike() {
		return mylike;
	}
	public void setMylike(ArrayList<ProductBean> mylike) {
		this.mylike = mylike;
	}
	public ArrayList<String> getAd() {
		return ad;
	}
	public void setAd(ArrayList<String> ad) {
		this.ad = ad;
	}
	

}
