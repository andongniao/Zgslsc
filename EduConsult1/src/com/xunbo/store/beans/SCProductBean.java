package com.xunbo.store.beans;

/**
 * ��Ʒʵ��
 * @author Qzr
 *
 */
public class SCProductBean extends BaseBean{
	private String cid;//��Ʒid
	private String title;//��Ʒ����
	private String unit;//��λ
	private String thumb;//����ͼ
	private String price;//����
	private String username;//
	private String collected;//
	private String type;//����
	private String company;//
	private String shopname;//��˾Ψһ��ʶ
	private String time;
	private String linkurl;
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public String getTitle() {
		return title;
	}
	public String getShopname() {
		return shopname;
	}
	public void setShopname(String shopname) {
		this.shopname = shopname;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCollected() {
		return collected;
	}
	public void setCollected(String collected) {
		this.collected = collected;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getLinkurl() {
		return linkurl;
	}
	public void setLinkurl(String linkurl) {
		this.linkurl = linkurl;
	}
	
	
}
