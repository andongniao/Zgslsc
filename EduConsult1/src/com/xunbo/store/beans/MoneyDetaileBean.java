package com.xunbo.store.beans;

public class MoneyDetaileBean extends BaseBean{
	private String amount;		//֧����������
	private String balance;		//�ʽ����
	private String reason;		//�ʽ���Դ��ȥ��
	private String addtime;		//ʱ��
	private String bank;		//֧����ʽ
	private String note;		//��ע
	private String itemid;		//�ʽ���ˮ��
	
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	

}
