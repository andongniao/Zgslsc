package com.example.educonsult.beans;

import java.io.Serializable;

public class BaseBean implements Serializable{
	private boolean isclick;
	private String name;
	private int num;
	public boolean isIsclick() {
		return isclick;
	}
	public void setIsclick(boolean isclick) {
		this.isclick = isclick;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	
}
