package com.example.educonsult.beans;
/**
 * �˿�����ӿ�ʵ��
 * @author Qzr
 *
 */
public class RefundInfoDetaileBean extends BaseBean{
	private String companyid;	//��˾ID
	private String company;		//��˾��
	private String truename;	//���������ƹ�
	private String updatetime;	//����ʱ��
	private String status;		//����״̬
	private String money;		//���
	private String content;		//˵��
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
