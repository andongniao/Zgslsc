package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.example.educonsult.R;
import com.example.educonsult.activitys.ShopcartActivity;
import com.example.educonsult.activitys.ShopcartActivity.shop;
import com.example.educonsult.beans.ShopBean;
import com.example.educonsult.myviews.SwipeMenuListView.SwipeMenu;
import com.example.educonsult.myviews.SwipeMenuListView.SwipeMenuCreator;
import com.example.educonsult.myviews.SwipeMenuListView.SwipeMenuItem;
import com.example.educonsult.myviews.SwipeMenuListView.SwipeMenuListView;
import com.example.educonsult.myviews.SwipeMenuListView.SwipeMenuListView.OnMenuItemClickListener;
import com.example.educonsult.tools.Util;

public class ShopcartHomeAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<Object>list;
	private LayoutInflater inflater;
	private Item item;
	private ShopcartLvAdapter adapter;
	private shop shop;

	public ShopcartHomeAdapter(Context context,ArrayList<Object>list, shop shop){
		this.context = context;
		this.list = list;
		this.shop = shop;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<Object>list){
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
			convertView = inflater.inflate(R.layout.shopcart_home_lv_item, null);
			item = new Item();
			//TODO 
			item.cb = (CheckBox) convertView.findViewById(R.id.shopcart_home_lv_cb);
			item.lv = (SwipeMenuListView) convertView.findViewById(R.id.shopcart_home_lv_lv);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		ShopBean s = (ShopBean) list.get(position);

		item.cb.setChecked(s.isIsclick());
		item.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				shop.click(isChecked, position, -1);
			}
		});
		
		
		SwipeMenuCreator creator = new SwipeMenuCreator() {

			@Override
			public void create(SwipeMenu menu) {
				SwipeMenuItem deleteItem = new SwipeMenuItem(
						context.getApplicationContext());
				deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
						0x3F, 0x25)));
				deleteItem.setWidth(dp2px(90));
				deleteItem.setIcon(R.drawable.del_icon_normal);
				menu.addMenuItem(deleteItem);
			}
		};
		item.lv.setMenuCreator(creator);
//		ShopBean sb = (ShopBean) list.get(position);
//		ArrayList<Object> l = sb.getList();
		adapter = new ShopcartLvAdapter(context,list,position, shop);
		item.lv.setAdapter(adapter);
		Util.setListViewHeightBasedOnChildren(item.lv);
		item.lv.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
//					if (Util.detect(context)) {
//
//					} else {
//
//					}
					break;
				}
			}
		});
		return convertView;
	}
	class Item{
		CheckBox cb;
		SwipeMenuListView lv;
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}
}
