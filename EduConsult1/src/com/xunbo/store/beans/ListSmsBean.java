package com.xunbo.store.beans;

import java.util.ArrayList;

public class ListSmsBean extends BaseBean{
	private ArrayList<smseBean> list;
	public ArrayList<smseBean> getList() {
		return list;
	}
	public void setList(ArrayList<smseBean> list) {
		this.list = list;
	}



	class smseBean{
		private String title;
		private String itemid;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getItemid() {
			return itemid;
		}
		public void setItemid(String itemid) {
			this.itemid = itemid;
		}
		
	}
}
