package com.example.educonsult.beans;

import java.util.ArrayList;
/**
 * 提交订单实体
 * @author Administrator
 *
 */
public class ListOrderCommit extends BaseBean{
	private ArrayList<OrderCommitBean> list;

	public ArrayList<OrderCommitBean> getList() {
		return list;
	}

	public void setList(ArrayList<OrderCommitBean> list) {
		this.list = list;
	}
	
}
