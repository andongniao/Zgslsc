package com.xunbo.store.beans;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * 充值界银行列表实体
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
