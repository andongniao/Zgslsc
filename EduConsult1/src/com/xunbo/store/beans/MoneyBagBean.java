package com.xunbo.store.beans;
/**
 * 钱包首页信息
 * @author Administrator
 *
 */
public class MoneyBagBean extends BaseBean{
	private String money;		//可以用的金额
	private String username;
	private double amount;			//体现中金额
	private String all_money;	//总金额
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getAll_money() {
		return all_money;
	}
	public void setAll_money(String all_money) {
		this.all_money = all_money;
	}
	
}
