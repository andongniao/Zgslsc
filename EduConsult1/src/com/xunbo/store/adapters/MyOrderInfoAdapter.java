package com.xunbo.store.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xunbo.store.R;
import com.xunbo.store.beans.OrderDetaileBean;
import com.xunbo.store.tools.Util;

public class MyOrderInfoAdapter extends BaseAdapter{
	private Context context;
	private OrderDetaileBean bean;
	private LayoutInflater inflater;
	private Item item;


	public MyOrderInfoAdapter(Context context,OrderDetaileBean bean){
		this.context = context;
		this.bean = bean;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
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
			convertView = inflater.inflate(R.layout.myorderinfo_item, null);
			item = new Item();
			//TODO 
			item.iv = (ImageView) convertView.findViewById(R.id.myorderinfo_item_ic);
			item.tv_title = (TextView) convertView.findViewById(R.id.myorderinfo_item_pname);
			item.tv_price = (TextView) convertView.findViewById(R.id.myorderinfo_item_money);
			item.tv_num = (TextView) convertView.findViewById(R.id.myorderinfo_item_num);
			item.tv_title=(TextView)convertView.findViewById(R.id.myorderinfo_item_talk);
			item.tv_unit = (TextView) convertView.findViewById(R.id.myorderinfo_item_unit);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		Util.Getbitmap(item.iv , bean.getThumb());
		item.tv_title.setText(bean.getTitle());
		item.tv_price.setText(bean.getPrice());
		item.tv_num.setText("ÊýÁ¿"+bean.getNumber()+"¼þ");
		item.tv_unit.setText(bean.getUnit());
		
		return convertView;
	}
	class Item{
		TextView tv_title,tv_price,tv_num,tv_unit;
		ImageView iv;
	}

}
