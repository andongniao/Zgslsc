package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.activitys.StoreActivity.AddInterface;
import com.example.educonsult.beans.ProductBean;
import com.example.educonsult.tools.Util;

public class StoreAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ProductBean> list;
	private LayoutInflater inflater;
	private Item item;
	
	public StoreAdapter(Context context,ArrayList<ProductBean> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
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
	public View getView(final int position, View v, ViewGroup parent) {
		if(v==null){
			v = inflater.inflate(R.layout.store_home_gv_item, null);
			item = new Item();
			item.iv = (ImageView) v.findViewById(R.id.store_home_item_iv);
			item.tv_title = (TextView) v.findViewById(R.id.store_home_item_tv_title);
			item.tv_type = (TextView) v.findViewById(R.id.store_home_item_tv_type);
			item.tv_price = (TextView) v.findViewById(R.id.store_home_item_tv_price);
			item.tv_danwei = (TextView) v.findViewById(R.id.store_home_item_tv_danwei);
			item.tv_add = (TextView) v.findViewById(R.id.store_home_item_tv_add2shopcart);
			item.ll = (LinearLayout) v.findViewById(R.id.store_home_item_ll);
			v.setTag(item);
		}else{
			item = (Item) v.getTag();
		}
//		if(type==1){
			item.tv_type.setTextColor(context.getResources().getColor(R.color.black));
			item.tv_add.setVisibility(View.GONE);
			item.ll.setBackgroundResource(R.color.base_hui);
//		}else{
//			item.tv_type.setTextColor(context.getResources().getColor(R.color.orn));
//			item.tv_add.setVisibility(View.VISIBLE);
//			item.ll.setBackgroundResource(R.color.orn);
//		}
		Util.Getbitmap(item.iv, list.get(position).getThumb());
		item.tv_title.setText(list.get(position).getTitle());
		item.tv_price.setText(list.get(position).getPrice());
		item.tv_danwei.setText(list.get(position).getUnit());
//		item.tv_title.setText(list.get(position).getTitle());
		
		
		
		return v;
	}
	
	
	class Item{
		LinearLayout ll;
		ImageView iv;
		TextView tv_title,tv_type,tv_price,tv_danwei,tv_add;
	}

}
