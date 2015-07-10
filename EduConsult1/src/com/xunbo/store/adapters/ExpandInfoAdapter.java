package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xunbo.store.R;
import com.xunbo.store.beans.FenleiBean;
import com.xunbo.store.beans.ListFenleiBean;

public class ExpandInfoAdapter extends BaseExpandableListAdapter {  
	LinearLayout mGroupLayout;  
	private Context context;
	private ListFenleiBean list;
	private ArrayList<FenleiBean> group;
	private ArrayList<FenleiBean> child;
	private ArrayList<FenleiBean> child_3;
	//++++++++++++++++++++++++++++++++++++++++++++  
	// child's stub  
	public ExpandInfoAdapter(Context context,ListFenleiBean list){
		this.context = context;
		this.list = list;
		group = list.getList();
	}

	@Override  
	public Object getChild(int groupPosition, int childPosition) {  
		// TODO Auto-generated method stub  
		return group.get(groupPosition).getCatid();  
	}  

	@Override  
	public long getChildId(int groupPosition, int childPosition) {  
		// TODO Auto-generated method stub  
		return childPosition;  
	}  

	@Override  
	public int getChildrenCount(int groupPosition) {  
		// TODO Auto-generated method stub  
		return group.get(groupPosition).getChild().size();  
	}  

	@Override  
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View v,  
			ViewGroup parent) {  
		// TODO Auto-generated method stub  
//		group = list.get
		child = group.get(groupPosition).getChild();
//		if(child!=null && child.size()>0){
//			
//		}
//		
//		
//		TextView txt_child = new TextView(context);  
//		txt_child.setText(group.get(groupPosition).getCatname());  
//		MyExplandableListView lv = LayoutInflater.from(context).inflate(resource, root)
		v = LayoutInflater.from(context).inflate(R.layout.cat_home, null);    
		ExpandableListView lv = (ExpandableListView) v.findViewById(R.id.lv);
		/* 隐藏默认箭头显示 */  
		lv.setGroupIndicator(null);  
		/* 隐藏垂直滚动条 */  
		lv.setVerticalScrollBarEnabled(false);  
		Three three = new Three(child);
		lv.setAdapter(three);
		
		return v;  
	}  

	@Override  
	public Object getGroup(int groupPosition) {  
		// TODO Auto-generated method stub  
		return group.get(groupPosition);  
	}  

	@Override  
	public int getGroupCount() {  
		// TODO Auto-generated method stub  
		return group.size();  
	}  

	@Override  
	public long getGroupId(int groupPosition) {  
		// TODO Auto-generated method stub  
		return groupPosition;  
	}  

	@Override  
	public View getGroupView(int groupPosition, boolean isExpanded,  View convertView, ViewGroup parent) {  
		// TODO Auto-generated method stub  
		TextView txt_group;  
		if(null == convertView){  
			convertView = LayoutInflater.from(context).inflate(R.layout.group_item, null);    
		}  
		/*判断是否group张开，来分别设置背景图*/  
		if(isExpanded){  
			convertView.setBackgroundResource(R.drawable.about_lv);  
		}else{  
			convertView.setBackgroundResource(R.drawable.about_l);  
		}  

		txt_group = (TextView)convertView.findViewById(R.id.id_group_txt);  
		txt_group.setText(group.get(groupPosition).getCatname());  
		return convertView;  
	}  

	@Override  
	public boolean isChildSelectable(int arg0, int arg1) {  
		// TODO Auto-generated method stub  
		return true;  
	}  

	@Override  
	public boolean hasStableIds() {  
		// TODO Auto-generated method stub  
		return false;  
	}  

	
	class Three extends BaseExpandableListAdapter {  
		LinearLayout mGroupLayout;  
//		private Context context;
//		private ListFenleiBean list;
		private ArrayList<FenleiBean> g;
		private ArrayList<FenleiBean> c;
		
		private Three(ArrayList<FenleiBean> g){
			this.g = g;
		}


		@Override  
		public Object getChild(int groupPosition, int childPosition) {  
			// TODO Auto-generated method stub  
			return g.get(groupPosition).getChild();  
		}  

		@Override  
		public long getChildId(int groupPosition, int childPosition) {  
			// TODO Auto-generated method stub  
			return childPosition;  
		}  

		@Override  
		public int getChildrenCount(int groupPosition) {  
			// TODO Auto-generated method stub  
			return g.get(groupPosition).getChild().size();  
		}  

		@Override  
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView,  
				ViewGroup parent) {  
			// TODO Auto-generated method stub  
//			group = list.get
			
			c = g.get(groupPosition).getChild();
			
			TextView txt_child = new TextView(context);  
			txt_child.setText(c.get(childPosition).getCatname());  

			return convertView;  
		}  

		@Override  
		public Object getGroup(int groupPosition) {  
			// TODO Auto-generated method stub  
			return g.get(groupPosition);  
		}  

		@Override  
		public int getGroupCount() {  
			// TODO Auto-generated method stub  
			return 1;  
		}  

		@Override  
		public long getGroupId(int groupPosition) {  
			// TODO Auto-generated method stub  
			return groupPosition;  
		}  

		@Override  
		public View getGroupView(int groupPosition, boolean isExpanded,  View convertView, ViewGroup parent) {  
			// TODO Auto-generated method stub  
			TextView txt_group;  
//			if(null == convertView){  
				convertView = LayoutInflater.from(context).inflate(R.layout.group_item, null);    
//			}  
			/*判断是否group张开，来分别设置背景图*/  
//			if(isExpanded){  
//				convertView.setBackgroundResource(R.drawable.about_lv);  
//			}else{  
//				convertView.setBackgroundResource(R.drawable.about_l);  
//			}  

			txt_group = (TextView)convertView.findViewById(R.id.id_group_txt);  
			txt_group.setText(g.get(groupPosition).getCatname());  
			return convertView;  
		}  

		@Override  
		public boolean isChildSelectable(int arg0, int arg1) {  
			// TODO Auto-generated method stub  
			return true;  
		}  

		@Override  
		public boolean hasStableIds() {  
			// TODO Auto-generated method stub  
			return false;  
		}
	}
}