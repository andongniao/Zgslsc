package com.xunbo.store.beans;

import java.util.ArrayList;

public class ListOrderWuliu extends BaseBean{
	private ArrayList<String> send_types;
	private String send_time;
	public ArrayList<String> getSend_types() {
		return send_types;
	}
	public void setSend_types(ArrayList<String> send_types) {
		this.send_types = send_types;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}
	

}
