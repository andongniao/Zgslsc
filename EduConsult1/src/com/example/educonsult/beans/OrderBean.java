package com.example.educonsult.beans;
/**
 * �����б�ʵ��
 * @author Administrator
 *
 */
public class OrderBean {
	private String itemid;	//����ID
	private String title;	//������Ʒ����
	private String thumb;	//������Ʒ����ͼ
	private String addtime;	//�µ�ʱ��
	private String price;	//����
	private String number;	//����
	private String status;	//״̬
	private String statusid;//״̬�� 1������ 2������ 3���ջ� 4���׳ɹ�
	private String company;	//��ҵ����
	private String unit;	//��λ
	private String money;	//���
	private String fee;		//�����ѣ��˷ѣ�
	private String coupons;	//�Ż�ȯ
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusid() {
		return statusid;
	}
	public void setStatusid(String statusid) {
		this.statusid = statusid;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getCoupons() {
		return coupons;
	}
	public void setCoupons(String coupons) {
		this.coupons = coupons;
	}

}
