package com.xunbo.store.beans;
/**
 * 钱包体现银行卡信息实体
 * @author Qzr
 *
 */
public class MoneyTxBean {
	private String money;
	private String bank_money;
	private String bindOpenBank;//所绑定银行卡开户行
	private String bindAccNum;//银行卡号
	private String all_money;//可提现金额
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
