package com.example.educonsult.beans;

import java.io.Serializable;

public class BaseBean implements Serializable{
	private boolean isclick;
	private String name;
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

	
	
}
