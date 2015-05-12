package com.example.educonsult.beans;

import java.util.ArrayList;

public class ListBanksBean extends BaseBean{
	private ArrayList<BanksBean> banks;
	private ArrayList<String> province;
	public ArrayList<BanksBean> getBanks() {
		return banks;
	}
	public void setBanks(ArrayList<BanksBean> banks) {
		this.banks = banks;
	}
	public ArrayList<String> getProvince() {
		return province;
	}
	public void setProvince(ArrayList<String> province) {
		this.province = province;
	}
	
	

}
