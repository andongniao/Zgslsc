package com.example.educonsult.beans;

import java.util.ArrayList;

/**
 * ���ﳵ��Ʒʵ��
 * @author Qzr
 *
 */
public class ShopItemBean {
	private boolean isclick;	//���������Ƿ�ѡ
	private String itemid;		//��ƷID
	private String title;		//��Ʒ����
	private String unit;		//��λ
	private String thumb;		//ͼƬ
	private String price;		//�۸�
	private String amount;		//���
	private int num;			//����
	private int number;
	private String username;
	private String alt;
	private String status;
	private ArrayList<CouponBean> coupons;//�Ż�ȯ
	public boolean isIsclick() {
		return isclick;
	}
	public void setIsclick(boolean isclick) {
		this.isclick = isclick;
	}
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
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAlt() {
		return alt;
	}
	public void setAlt(String alt) {
		this.alt = alt;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ArrayList<CouponBean> getCoupons() {
		return coupons;
	}
	public void setCoupons(ArrayList<CouponBean> coupons) {
		this.coupons = coupons;
	}
	
}
