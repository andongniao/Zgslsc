package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.LinearLayout.LayoutParams;

import com.xunbo.store.R;
import com.xunbo.store.beans.FenleiBean;
import com.xunbo.store.beans.ListFenleiBean;
import com.xunbo.store.tools.UITools;
import com.xunbo.store.tools.Util;

public class CatHomelvAdapter extends BaseExpandableListAdapter {
	private Context context;
	private ListFenleiBean list;
	private ArrayList<FenleiBean> group;
	private ArrayList<FenleiBean> child;
	private LayoutInflater inflater;
	public MyHalderItem halderItem;
	private Item item;
	private int w;
	private ExpandableListView lv;
	
	public CatHomelvAdapter(Context context,ListFenleiBean list){
		this.context = context;
		this.list = list;
		group = list.getList();
		inflater = LayoutInflater.from(context);
	} 

	public void changeHeight(int type){
		int hh = lv.getHeight();;
		if(type==1){
			hh=hh/2;
		}
		LinearLayout.LayoutParams params = //new LayoutParams(LayoutParams.FILL_PARENT, 200);
				new LayoutParams(
						Util.getWidth((Activity)context), hh);//UITools.dip2px((Activity)context, 104));
		lv.setLayoutParams(params);
		this.notifyDataSetChanged();
	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return group.get(groupPosition).getChild().get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return group.get(groupPosition).getChild().get(childPosition).getCatid();
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		child = group.get(groupPosition).getChild();
		if (convertView == null) {
			halderItem = new MyHalderItem();
			convertView = inflater.inflate(R.layout.cat_home_2,
					null);
			halderItem.exListView = (ExpandableListView) convertView
					.findViewById(R.id.lv_2);
			
			/* 隐藏默认箭头显示 */  
			halderItem.exListView.setGroupIndicator(null);  
			/* 隐藏垂直滚动条 */  
			halderItem.exListView.setVerticalScrollBarEnabled(false);  
			convertView.setTag(halderItem);
		} else {
			halderItem = (MyHalderItem) convertView.getTag();
		}
//			if(){
//				
//			}
		final CatlvAdapter adapter = new CatlvAdapter(
				context, list, groupPosition, inflater,groupPosition,this,w);
		LinearLayout.LayoutParams params = //new LayoutParams(LayoutParams.FILL_PARENT, 200);
				new LayoutParams(
						LinearLayout.LayoutParams.FILL_PARENT, getHeigith(groupPosition));//UITools.dip2px((Activity)context, 104));
		halderItem.exListView.setLayoutParams(params);
		lv = halderItem.exListView;
		halderItem.exListView.setAdapter(adapter);
		halderItem.exListView
		.setOnGroupExpandListener(new OnGroupExpandListener() {
			ExpandableListView listView = halderItem.exListView;
			CatlvAdapter itemAdapter = adapter;

			@Override
			public void onGroupExpand(int groupPosition) {
				LinearLayout.LayoutParams layoutParams = (LayoutParams) listView
						.getLayoutParams();
				int h = layoutParams.height
						+ itemAdapter.getItemHeigith(groupPosition);
				LinearLayout.LayoutParams layoutParam = new LayoutParams(
						LinearLayout.LayoutParams.FILL_PARENT, h);
				listView.setLayoutParams(layoutParam);
//				itemAdapter.seth(1);
			}
		});
		halderItem.exListView
		.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			ExpandableListView listView = halderItem.exListView;
			CatlvAdapter itemAdapter = adapter;

			@Override
			public void onGroupCollapse(int groupPosition) {
				LinearLayout.LayoutParams layoutParams = (LayoutParams) listView
						.getLayoutParams();
				int h = layoutParams.height
						- itemAdapter.getItemHeigith(groupPosition);
				LinearLayout.LayoutParams layoutParam = new LayoutParams(
						LinearLayout.LayoutParams.FILL_PARENT, h);//Util.getWidth((Activity)context)
				listView.setLayoutParams(layoutParam);
//				itemAdapter.seth(2);
			}
		});
		
		return convertView;
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
		return group!=null?group.get(groupPosition).getCatid():0;
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
			item.ll = (LinearLayout)v.findViewById(R.id.id_group_ll);
			v.setTag(item);
		}else{
			item = (Item) v.getTag();
		}
		item.tv.setText(group.get(groupPosition).getCatname());  
		if(isExpanded){
			item.iv.setBackgroundResource(R.drawable.jt_down);
		}else{
			item.iv.setBackgroundResource(R.drawable.jt_left);
		}
		w = item.ll.getHeight();
		
		
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
		public ExpandableListView exListView;
	}
	private class Item{
		TextView tv;
		ImageView iv;
		LinearLayout ll;
	}
	public int getHeigith(int groupPosition) {
		int height,width;
		width = (int) (Util.getWidth((Activity)context) / 9.7);
		height = (w)
				* group.get(groupPosition).getChild().size()+5;// names[groupPosition].length;
		return height;
	}
	
	public int getHeigith(int groupPosition,int pos) {
		int height,width;
		width = (int) (Util.getWidth((Activity)context) / 9.7);
		height = (w)
				* (group.get(groupPosition).getChild().size()
						+group.get(groupPosition).getChild().get(pos).getChild().size())+5;// names[groupPosition].length;
		return height;
	}
}