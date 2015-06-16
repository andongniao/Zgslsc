package com.xunbo.store.beans;

import java.util.ArrayList;

/**
 * 店铺分类列表实体
 * @author Qzr
 *
 */
public class ListStoreCatBean extends BaseBean{
	private ArrayList<StorecatBean> list;

	public ArrayList<StorecatBean> getList() {
		return list;
	}

	public void setList(ArrayList<StorecatBean> list) {
		this.list = list;
	}
}
