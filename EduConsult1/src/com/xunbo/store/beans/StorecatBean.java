package com.xunbo.store.beans;
/**
 * 店铺自定义分类
 * @author Qzr
 *
 */
public class StorecatBean {
	private String typeid;		 //分类id
	private String listorder;	 //自定义排序
	private String typename;	//分类名
	public String getTypeid() {
		return typeid;
	}
	public void setTypeid(String typeid) {
		this.typeid = typeid;
	}
	public String getListorder() {
		return listorder;
	}
	public void setListorder(String listorder) {
		this.listorder = listorder;
	}
	public String getTypename() {
		return typename;
	}
	public void setTypename(String typename) {
		this.typename = typename;
	}

}
