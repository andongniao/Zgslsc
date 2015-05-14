package com.example.educonsult.beans;
/**
 * 订单详情
 * @author Qzr
 *
 */
public class OrderDetaileBean extends BaseBean{
	private String itemid;			//订单ID
	private String fee;				//运费
	private String money;			//金额
	private String price;			//单价
	private String buyer_name;		//收货人
	private String buyer_mobile;	//收货联系方式
	private String buyer_address;	//收货地址
	private String company;			//公司法人（掌柜）
	private String companyid;		//公司ID
	private String thumb;			//image
	private String title;			//标题
	private String number;			//数量
	private String unit;			//单位
	private String addtime;			//下单时间
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public String getFee() {
		return fee;
	}
	public void setFee(String fee) {
		this.fee = fee;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getBuyer_name() {
		return buyer_name;
	}
	public void setBuyer_name(String buyer_name) {
		this.buyer_name = buyer_name;
	}
	public String getBuyer_mobile() {
		return buyer_mobile;
	}
	public void setBuyer_mobile(String buyer_mobile) {
		this.buyer_mobile = buyer_mobile;
	}
	public String getBuyer_address() {
		return buyer_address;
	}
	public void setBuyer_address(String buyer_address) {
		this.buyer_address = buyer_address;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getThumb() {
		return thumb;
	}
	public void setThumb(String thumb) {
		this.thumb = thumb;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getAddtime() {
		return addtime;
	}
	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}
	
}
