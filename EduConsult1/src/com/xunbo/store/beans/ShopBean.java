package com.xunbo.store.beans;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * ���ﳵ����ʵ��
 * @author Qzr
 *
 */
public class ShopBean extends ShopItemBean implements Serializable{
	private ArrayList<ShopItemBean>mall;	//��Ʒ�б�
	private String company;					//�������
	private String companyid;				//���ID
	private String note;					//�������
	private ArrayList<ExpressBean> express;//�˷�ģ��
	public ArrayList<ShopItemBean> getMall() {
		return mall;
	}
	public void setMall(ArrayList<ShopItemBean> mall) {
		this.mall = mall;
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
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public ArrayList<ExpressBean> getExpress() {
		return express;
	}
	public void setExpress(ArrayList<ExpressBean> express) {
		this.express = express;
	}
	
}
