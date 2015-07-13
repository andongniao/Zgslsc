package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.educonsult.R;

public class TextItemListAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<String>list;
	private LayoutInflater inflater;
	private Item item;
	private int index;


	public TextItemListAdapter(Context context,ArrayList<String> list){
		this.context = context;
		this.list = list;
		this.index = index;
		inflater = LayoutInflater.from(context);
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list!=null?list.size():5;
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
			convertView = inflater.inflate(R.layout.mingxi_lv_item, null);
			item = new Item();
			item.tv_text= (TextView) convertView.findViewById(R.id.mingxi_lv_item_tv_c);
			convertView.setTag(item);
			
		}else{
			item = (Item) convertView.getTag();
		}
		item.tv_text.setText(list.get(position));
		
		
		return convertView;
	}
	class Item{
		TextView tv_text;
	}

}
