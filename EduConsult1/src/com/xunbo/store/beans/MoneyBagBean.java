package com.xunbo.store.beans;
/**
 * Ǯ����ҳ��Ϣ
 * @author Administrator
 *
 */
public class MoneyBagBean extends BaseBean{
	private String money;		//�����õĽ��
	private String username;
	private double amount;			//�����н��
	private String all_money;	//�ܽ��
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
