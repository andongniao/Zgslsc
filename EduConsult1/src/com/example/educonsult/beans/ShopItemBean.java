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
	private String price;		//�۸�
	private String thumb;		//ͼƬ
	private String amount;		//���
	private int num;			//����
	private ArrayList<CouponBean> coupon;//�Ż�ȯ
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
	public ArrayList<CouponBean> getCoupon() {
		return coupon;
	}
	public void setCoupon(ArrayList<CouponBean> coupon) {
		this.coupon = coupon;
	}

}
