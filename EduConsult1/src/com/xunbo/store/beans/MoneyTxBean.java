package com.xunbo.store.beans;
/**
 * Ǯ���������п���Ϣʵ��
 * @author Qzr
 *
 */
public class MoneyTxBean {
	private String money;
	private String bank_money;
	private String bindOpenBank;//�������п�������
	private String bindAccNum;//���п���
	private String all_money;//�����ֽ��
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getBank_money() {
		return bank_money;
	}
	public void setBank_money(String bank_money) {
		this.bank_money = bank_money;
	}
	public String getBindOpenBank() {
		return bindOpenBank;
	}
	public void setBindOpenBank(String bindOpenBank) {
		this.bindOpenBank = bindOpenBank;
	}
	public String getBindAccNum() {
		return bindAccNum;
	}
	public void setBindAccNum(String bindAccNum) {
		this.bindAccNum = bindAccNum;
	}
	public String getAll_money() {
		return all_money;
	}
	public void setAll_money(String all_money) {
		this.all_money = all_money;
	}
	
}
