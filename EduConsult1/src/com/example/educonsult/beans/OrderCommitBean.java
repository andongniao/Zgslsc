package com.example.educonsult.beans;

import java.util.ArrayList;

/**
 * 提交订单单个实体
 * @author Qzr
 *
 */
public class OrderCommitBean{
	private int trade;	//单个订单的订单ID
	private String coupon;	//单个订单的优惠券ID
	public int getTrade() {
		return trade;
	}
	public void setTrade(int trade) {
		this.trade = trade;
	}
	public String getCoupon() {
		return coupon;
	}
	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
	
}
