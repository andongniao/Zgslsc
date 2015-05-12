package com.example.educonsult.beans;

import java.util.ArrayList;

public class ListOrderBean extends BaseBean{
	private ArrayList<OrderBean>list_order;
	private ArrayList<OrderFields>list_key;
	public ArrayList<OrderBean> getList_order() {
		return list_order;
	}
	public void setList_order(ArrayList<OrderBean> list_order) {
		this.list_order = list_order;
	}
	public ArrayList<OrderFields> getList_key() {
		return list_key;
	}
	public void setList_key(ArrayList<OrderFields> list_key) {
		this.list_key = list_key;
	}
	

}
