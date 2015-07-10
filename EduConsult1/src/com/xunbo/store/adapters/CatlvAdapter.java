package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xunbo.store.R;
import com.xunbo.store.activitys.CatDetaileActivity.Cat;
import com.xunbo.store.beans.FenleiBean;
import com.xunbo.store.beans.ListFenleiBean;
import com.xunbo.store.myviews.MyListview;
import com.xunbo.store.tools.Util;

public class CatlvAdapter extends BaseExpandableListAdapter {
	private Context context;
	private ListFenleiBean list;
	private ArrayList<FenleiBean> group;
	private ArrayList<FenleiBean> child;
	private LayoutInflater inflater;
	public MyHalderItem halderItem;
	private Item item;
	private int index,width,height,w;
	private CatHomelvAdapter catHomelvAdapter;
	private Cat cat;

	public CatlvAdapter(Context context,ListFenleiBean list, int index, LayoutInflater inflater2,
			int groupPosition, CatHomelvAdapter catHomelvAdapter, int we, Cat cat){
		this.context = context;
		this.catHomelvAdapter = catHomelvAdapter;
		this.list = list;
		this.index = index;
		this.w = we;
		group = list.getList().get(index).getChild();
		inflater = inflater2;//LayoutInflater.from(context);
		width = (int) (Util.getWidth((Activity)context) / 9.7);
	} 


	public int getItemHeigith(int groupPosition) {
		int height;
		height = getHeigith(groupPosition);
//				(w + UITools.dip2px(context, 30))
//				* (list.getList().get(index).getChild().get(groupPosition).getChild().size());// names[groupPosition].length;
		return height;
	}
	public void seth(int type){
		if(type==1){
			halderItem.lv.setVisibility(View.GONE);	
		}else{
			halderItem.lv.setVisibility(View.VISIBLE);
		}
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		//		child = group.get(groupPosition).getChild();
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		child = group.get(groupPosition).getChild();
		return child!=null?child.get(childPosition).getCatid():null;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View v, ViewGroup parent) {
		child = group.get(groupPosition).getChild();
		if(v==null){
			halderItem = new MyHalderItem();
			v = inflater.inflate(R.layout.cat_home_3, null);
			halderItem.lv = (MyListview) v.findViewById(R.id.lv_3);
			v.setTag(halderItem);
		}else{
			halderItem =(MyHalderItem) v.getTag();
		}
		Catlv3Adapter ap = new Catlv3Adapter(context, child);
		halderItem.lv.setAdapter(ap);
		halderItem.lv.setFocusable(false);
//		LinearLayout.LayoutParams params = //new LayoutParams(LayoutParams.FILL_PARENT, 200);
//				new LayoutParams(
//						Util.getWidth((Activity)context), 5);//UITools.dip2px((Activity)context, 104));
//		halderItem.lv.setLayoutParams(params);
		///////////////////////////////////////////////////////////////////////
		
		
		
		
		return v;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return group.get(groupPosition).getChild();
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return group!=null?group.size():0;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return group.get(groupPosition).getCatid();
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View v, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(v==null){
			item = new Item();
			v = inflater.inflate(R.layout.group_item, null);
			item.tv = (TextView)v.findViewById(R.id.id_group_txt);  
			item.iv = (ImageView)v.findViewById(R.id.id_group_img);
			v.setTag(item);
		}else{
			item = (Item) v.getTag();
		}
		item.tv.setText(group.get(groupPosition).getCatname());  
		if(isExpanded){
			Util.ShowToast(context, ""+groupPosition);
			item.tv.setTextColor(context.getResources().getColor(R.color.red));
			item.iv.setBackgroundResource(R.drawable.jt_down);
		}else{
			item.tv.setTextColor(context.getResources().getColor(R.color.black));
			item.iv.setBackgroundResource(R.drawable.jt_left);
		}
		
		return v;
	}
	
	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	} 


	public class MyHalderItem {
		public MyListview lv;
	}
	private class Item{
		TextView tv;
		ImageView iv;
	}
	public int getHeigith(int groupPosition) {
		int height,width;
		width = (int) (Util.getWidth((Activity)context) / 9.7);
//		if(index==3 && groupPosition==2){
//		height = (w)
//				* (group.get(groupPosition).getChild().size());// names[groupPosition].length;
//		}else{
			height = (w)
					* (group.get(groupPosition).getChild().size())+10;// names[groupPosition].length;
//		}
		return height;
	}
}