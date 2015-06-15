package com.example.educonsult.beans;


public class UserBean extends BaseBean{
	private String authstr;
	private int type;
	private String username;//ÓÃ»§Ãû
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAuthstr() {
		return authstr;
	}
	public void setAuthstr(String authstr) {
		this.authstr = authstr;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}

	

}
