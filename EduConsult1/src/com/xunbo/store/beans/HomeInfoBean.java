package com.xunbo.store.beans;

import java.util.ArrayList;
/**
 * 第二版首页信息实体
 * @author Qzr
 *
 */
public class HomeInfoBean extends BaseBean{
	private ArrayList<ProductBean> recommend;
	private ArrayList<HomeCatBean> cat;
	private ArrayList<String> ad;
	public ArrayList<ProductBean> getRecommend() {
		return recommend;
	}
	public void setRecommend(ArrayList<ProductBean> recommend) {
		this.recommend = recommend;
	}
	public ArrayList<HomeCatBean> getCat() {
		return cat;
	}
	public void setCat(ArrayList<HomeCatBean> cat) {
		this.cat = cat;
	}
	public ArrayList<String> getAd() {
		return ad;
	}
	public void setAd(ArrayList<String> ad) {
		this.ad = ad;
	}
	

}
