package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xunbo.store.R;

public class ZhanhuilvAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<Integer>list;
	private LayoutInflater inflater;
	private Item item;


	public ZhanhuilvAdapter(Context context,ArrayList<Integer>list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<Integer>list){
		this.list = list;
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
			convertView = inflater.inflate(R.layout.zhanhui_lv_item, null);
			item = new Item();
			item.iv = (ImageView) convertView.findViewById(R.id.zhanhui_lv_item_iv);
			item.tv_title = (TextView) convertView.findViewById(R.id.zhanhui_lv_item_tv_title);
			item.tv_content = (TextView) convertView.findViewById(R.id.zhanhui_lv_item_tv_content);
			item.tv_address = (TextView) convertView.findViewById(R.id.zhanhui_lv_item_tv_address);
			item.tv_time = (TextView) convertView.findViewById(R.id.zhanhui_lv_item_tv_time);
			
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		return convertView;
	}
	class Item{
		TextView tv_title,tv_content,tv_address,tv_time;
		ImageView iv;
	}

}
