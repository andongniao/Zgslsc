package com.xunbo.store.beans;

import java.io.Serializable;
import java.util.ArrayList;
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
