package com.example.educonsult.beans;

import java.util.ArrayList;

public class ProdectDetaileBean extends BaseBean{
	private MallInfoBean mallinfo;
	private ArrayList<ProductBean> recommend;
	private ArrayList<ProductBean> buyedlist;
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
