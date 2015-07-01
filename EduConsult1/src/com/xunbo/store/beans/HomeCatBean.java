package com.xunbo.store.beans;

import java.util.ArrayList;
/**
 * 首页分类实体
 * @author Qzr
 *
 */
public class HomeCatBean {
	private int catid;
	private String name;
	private ArrayList<ProductBean> data;
	public int getCatid() {
		return catid;
	}
	public void setCatid(int catid) {
		this.catid = catid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<ProductBean> getData() {
		return data;
	}
	public void setData(ArrayList<ProductBean> data) {
		this.data = data;
	}
	
}
