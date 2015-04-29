package com.example.educonsult.beans;

import java.io.Serializable;

public class BaseBean implements Serializable{
	private int code;
	private String msg;
	private boolean isclick;
	private String name;
	private int num;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
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
