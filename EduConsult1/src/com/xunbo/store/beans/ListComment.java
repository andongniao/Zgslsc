package com.xunbo.store.beans;

import java.util.ArrayList;

public class ListComment extends BaseBean{
	private ArrayList<CommentBean> comlist;
	private ArrayList<CommentStar> comstar;
	public ArrayList<CommentBean> getComlist() {
		return comlist;
	}
	public void setComlist(ArrayList<CommentBean> comlist) {
		this.comlist = comlist;
	}
	public ArrayList<CommentStar> getComstar() {
		return comstar;
	}
	public void setComstar(ArrayList<CommentStar> comstar) {
		this.comstar = comstar;
	}
	
}
