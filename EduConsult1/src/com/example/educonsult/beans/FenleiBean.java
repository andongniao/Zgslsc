package com.example.educonsult.beans;

import java.util.ArrayList;

public class FenleiBean extends BaseBean {
	private int catid;
	private String catname;
	private ArrayList<FenleiBean> child;
	public int getCatid() {
		return catid;
	}
	public void setCatid(int catid) {
		this.catid = catid;
	}
	public String getCatname() {
		return catname;
	}
	public void setCatname(String catname) {
		this.catname = catname;
	}
	public ArrayList<FenleiBean> getChild() {
		return child;
	}
	public void setChild(ArrayList<FenleiBean> child) {
		this.child = child;
	}
	

}
