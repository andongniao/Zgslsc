package com.xunbo.store.beans;

import java.util.ArrayList;
/**
 * 优惠券列表实体
 * @author Qzr
 *
 */
public class ListCouponBean extends BaseBean{
	private ArrayList<CouponBean> list;

	public ArrayList<CouponBean> getList() {
		return list;
	}

	public void setList(ArrayList<CouponBean> list) {
		this.list = list;
	}
	

}
