package com.xunbo.store.beans;

import java.util.ArrayList;

public class ProdectDetaileBean extends BaseBean{
	private MallInfoBean mallinfo;//产品详情
	private ArrayList<ProductBean> recommend;//推荐列表
	private ArrayList<ProductBean> buyedlist;//购买过的
	public MallInfoBean getMallinfo() {
		return mallinfo;
	}
	public void setMallinfo(MallInfoBean mallinfo) {
		this.mallinfo = mallinfo;
	}
	public ArrayList<ProductBean> getRecommend() {
		return recommend;
	}
	public void setRecommend(ArrayList<ProductBean> recommend) {
		this.recommend = recommend;
	}
	public ArrayList<ProductBean> getBuyedlist() {
		return buyedlist;
	}
	public void setBuyedlist(ArrayList<ProductBean> buyedlist) {
		this.buyedlist = buyedlist;
	}
	
	
}
