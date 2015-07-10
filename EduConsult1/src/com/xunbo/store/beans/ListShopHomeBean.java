package com.xunbo.store.beans;

import java.util.ArrayList;
/**
 * µêÆÌÊ×Ò³ÊµÌå
 * @author Qzr
 *
 */
public class ListShopHomeBean extends BaseBean{
	private ShopInfoBean shopInfoBean;
	private ArrayList<ProductBean>recommend;
//	private ArrayList<ProductBean>list;
	public ShopInfoBean getShopInfoBean() {
		return shopInfoBean;
	}
	public void setShopInfoBean(ShopInfoBean shopInfoBean) {
		this.shopInfoBean = shopInfoBean;
	}
	public ArrayList<ProductBean> getRecommend() {
		return recommend;
	}
	public void setRecommend(ArrayList<ProductBean> recommend) {
		this.recommend = recommend;
	}
//	public ArrayList<ProductBean> getList() {
//		return list;
//	}
//	public void setList(ArrayList<ProductBean> list) {
//		this.list = list;
//	}
	

}
