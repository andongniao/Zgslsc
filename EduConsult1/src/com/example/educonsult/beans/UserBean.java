package com.example.educonsult.beans;

import java.io.Serializable;

import android.graphics.Bitmap;

public class UserBean implements Serializable{
	private String name;
	private String bmp;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBmp() {
		return bmp;
	}
	public void setBmp(String bmp) {
		this.bmp = bmp;
	}

	

}
