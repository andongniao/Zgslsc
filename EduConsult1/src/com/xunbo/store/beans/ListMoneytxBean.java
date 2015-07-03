package com.xunbo.store.beans;

import java.util.ArrayList;

/**
 * 可提现银行卡信息列表实体
 * @author Qzr
 *
 */
public class ListMoneytxBean extends BaseBean{
	private ArrayList<MoneyTxBean>list;

	public ArrayList<MoneyTxBean> getList() {
		return list;
	}

	public void setList(ArrayList<MoneyTxBean> list) {
		this.list = list;
	}

}
