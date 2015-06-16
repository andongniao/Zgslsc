package com.xunbo.store.beans;

/**
 * 个人中心收藏产品，收藏店铺，待付款，待收货，待评价，待退款数量实体
 * @author Qzr
 *
 */
public class CenterCountBean extends BaseBean{
	private String product;
	private String shop;
	private String paying;
	private String receiving;
	private String pingjia;
	private String tuikuan;
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getShop() {
		return shop;
	}
	public void setShop(String shop) {
		this.shop = shop;
	}
	public String getPaying() {
		return paying;
	}
	public void setPaying(String paying) {
		this.paying = paying;
	}
	public String getReceiving() {
		return receiving;
	}
	public void setReceiving(String receiving) {
		this.receiving = receiving;
	}
	public String getPingjia() {
		return pingjia;
	}
	public void setPingjia(String pingjia) {
		this.pingjia = pingjia;
	}
	public String getTuikuan() {
		return tuikuan;
	}
	public void setTuikuan(String tuikuan) {
		this.tuikuan = tuikuan;
	}


}
