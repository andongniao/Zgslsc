package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xunbo.store.R;
import com.xunbo.store.beans.StorecatBean;

public class StoreFenleiAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<StorecatBean> list;
	private LayoutInflater inflater;
	private Itemview itemview;
	
	
	public StoreFenleiAdapter(Context context,ArrayList<StorecatBean> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<StorecatBean>list){
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list!=null?list.size():0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = inflater.inflate(R.layout.search_lv_view, null);
			itemview = new Itemview();
			itemview.ll = (LinearLayout) convertView.findViewById(R.id.search_home_item_ll);
			itemview.tv_c = (TextView) convertView.findViewById(R.id.search_lv_view_tv_c);
			convertView.setTag(itemview);
		}else{
			itemview = (Itemview) convertView.getTag();
		}
		itemview.ll.setBackgroundResource(R.color.white);
		itemview.tv_c.setText(list.get(position).getTypename());
		return convertView;
	}
	class Itemview{
		LinearLayout ll;
		TextView tv_c;
	}

}
