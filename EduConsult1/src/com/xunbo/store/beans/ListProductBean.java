package com.xunbo.store.beans;

import java.util.ArrayList;
/**
 * 单品列表实体
 * @author Qzr
 *
 */
public class ListProductBean extends BaseBean{
	private ArrayList<ProductBean> list;

	public ArrayList<ProductBean> getList() {
		return list;
	}

	public void setList(ArrayList<ProductBean> list) {
		this.list = list;
	}
	
}
