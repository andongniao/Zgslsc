package com.xunbo.store.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class ListShopBean extends BaseBean implements Serializable{
	private ArrayList<ShopBean>list;

	public ArrayList<ShopBean> getList() {
		return list;
	}

	public void setList(ArrayList<ShopBean> list) {
		this.list = list;
	}
	

}
