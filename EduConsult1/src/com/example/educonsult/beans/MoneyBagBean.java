package com.example.educonsult.beans;
/**
 * Ǯ����ҳ��Ϣ
 * @author Administrator
 *
 */
public class MoneyBagBean extends BaseBean{
	private String money;		//�����õĽ��
	private String username;
	private int amount;			//�����н��
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getAll_money() {
		return all_money;
	}
	public void setAll_money(String all_money) {
		this.all_money = all_money;
	}
	
}
