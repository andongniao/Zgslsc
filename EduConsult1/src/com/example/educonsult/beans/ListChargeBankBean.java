package com.example.educonsult.beans;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.educonsult.beans.BaseBean;
/**
 * ��ֵ�������б�ʵ��
 * @author Qzr
 *
 */
public class ListChargeBankBean extends BaseBean implements Serializable{
	private ArrayList<ChargeBankBean> list;

	public ArrayList<ChargeBankBean> getList() {
		return list;
	}

	public void setList(ArrayList<ChargeBankBean> list) {
		this.list = list;
	}
	
}
