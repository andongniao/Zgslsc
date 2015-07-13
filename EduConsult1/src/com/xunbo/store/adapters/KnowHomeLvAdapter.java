package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.educonsult.R;

@SuppressLint("ResourceAsColor") public class KnowHomeLvAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<Integer>list;
	private LayoutInflater inflater;
	private Item item;
	private int index;


	public KnowHomeLvAdapter(Context context,ArrayList<Integer>list){
		this.context = context;
		this.list = list;
		index = 2;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(int index){
		this.index = index;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = inflater.inflate(R.layout.know_home_lv_item, null);
			item = new Item();
			item.tv_title = (TextView) 
					convertView.findViewById(R.id.know_home_lv_item_tv_title);
			item.tv_contene = (TextView) 
					convertView.findViewById(R.id.know_home_lv_item_tv_content);
			item.tv_num = (TextView) 
					convertView.findViewById(R.id.know_home_lv_item_tv_num);
			item.tv_lei = (TextView) 
					convertView.findViewById(R.id.know_home_lv_item_tv_lei);
			item.tv_time = (TextView) 
					convertView.findViewById(R.id.know_home_lv_item_tv_time);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		







		return convertView;
	}
	class Item{
		TextView tv_title,tv_contene,tv_num,tv_lei,tv_time;
	}

}
