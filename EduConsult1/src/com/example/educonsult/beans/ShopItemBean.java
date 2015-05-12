package com.example.educonsult.beans;

import java.util.ArrayList;

/**
 * 购物车单品实体
 * @author Qzr
 *
 */
public class ShopItemBean {
	private boolean isclick;	//本地属性是否勾选
	private String itemid;		//商品ID
	private String title;		//商品名称
	private String unit;		//单位
	private String price;		//价格
	private String thumb;		//图片
	private String amount;		//库存
	private int num;			//数量
	private ArrayList<CouponBean> coupon;//优惠券
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
