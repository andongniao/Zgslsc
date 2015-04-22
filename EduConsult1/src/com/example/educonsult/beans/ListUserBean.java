package com.example.educonsult.beans;

import java.io.Serializable;
import java.util.ArrayList;

public class ListUserBean implements Serializable{
	private ArrayList<UserBean>list;

	public ArrayList<UserBean> getList() {
		return list;
	}

	public void setList(ArrayList<UserBean> list) {
		this.list = list;
	}
	
}
