package com.xunbo.store.beans;

import java.util.ArrayList;
/**
 * 钱包收入详情列表实体
 * @author Administrator
 *
 */
public class ListMoneyBean extends BaseBean{
	private ArrayList<MoneyDetaileBean>list;

	public ArrayList<MoneyDetaileBean> getList() {
		return list;
	}

	public void setList(ArrayList<MoneyDetaileBean> list) {
		this.list = list;
	}
	

}
