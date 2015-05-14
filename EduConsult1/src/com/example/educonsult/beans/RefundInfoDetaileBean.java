package com.example.educonsult.beans;
/**
 * 退款详情接口实体
 * @author Qzr
 *
 */
public class RefundInfoDetaileBean extends BaseBean{
	private String companyid;	//公司ID
	private String company;		//公司名
	private String truename;	//法人名（掌柜）
	private String updatetime;	//申请时间
	private String status;		//处理状态
	private String money;		//金额
	private String content;		//说明
	public String getCompanyid() {
		return companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getTruename() {
		return truename;
	}
	public void setTruename(String truename) {
		this.truename = truename;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
