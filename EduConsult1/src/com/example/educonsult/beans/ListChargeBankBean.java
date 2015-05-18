package com.example.educonsult.beans;

import java.io.Serializable;
import java.util.ArrayList;

import com.example.educonsult.beans.BaseBean;
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
