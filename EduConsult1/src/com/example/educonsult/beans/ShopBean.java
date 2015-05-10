package com.example.educonsult.beans;

import java.util.ArrayList;

public class ShopBean extends ShopItemBean{
	private String name;
	private ArrayList<ShopItemBean>list;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<ShopItemBean> getList() {
		return list;
	}
	public void setList(ArrayList<ShopItemBean> list) {
		this.list = list;
	}

}
