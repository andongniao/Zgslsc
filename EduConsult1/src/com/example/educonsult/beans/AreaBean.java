package com.example.educonsult.beans;

import java.util.ArrayList;

public class AreaBean extends BaseBean {
	private int areaid;
	private String arename;
	private ArrayList<AreaBean> child;
	public int getAreaid() {
		return areaid;
	}
	public void setAreaid(int areaid) {
		this.areaid = areaid;
	}
	public String getArename() {
		return arename;
	}
	public void setArename(String arename) {
		this.arename = arename;
	}
	public ArrayList<AreaBean> getChild() {
		return child;
	}
	public void setChild(ArrayList<AreaBean> child) {
		this.child = child;
	}
	

}
