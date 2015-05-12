package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.educonsult.R;
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
	private ArrayList<ShopBean>list;
	private LayoutInflater inflater;
	private Item item;
	private ShopcartLvAdapter adapter;
	private shop shop;
	private ShopBean shopbean;

	public ShopcartHomeAdapter(Context context,ArrayList<ShopBean>list, shop shop){
		this.context = context;
		this.list = list;
		this.shop = shop;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<ShopBean>list){
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
	public View getView(final int index, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = inflater.inflate(R.layout.shopcart_home_lv_item, null);
			item = new Item();
			//TODO 
			item.heji=(TextView)convertView.findViewById(R.id.shopcart_home_tv_heji);
			item.cb = (CheckBox) convertView.findViewById(R.id.shopcart_home_lv_cb);
			item.lv = (SwipeMenuListView) convertView.findViewById(R.id.shopcart_home_lv_lv);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		final ShopBean s = (ShopBean) list.get(index);

		item.cb.setChecked(s.isIsclick());
		item.cb.setText(s.getCompany());
		item.heji.setText(s.getNote());
//		item.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				shop.click(isChecked, position, -1);
//			}
//		});
		item.cb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(s.isIsclick()){
					shop.click(false, index, -1);
				}else{
					shop.click(true, index, -1);
				}
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
		adapter = new ShopcartLvAdapter(context,list,index, shop);
		item.lv.setAdapter(adapter);
		Util.setListViewHeightBasedOnChildren(item.lv);
		item.lv.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			@Override
			public void onMenuItemClick(int position, SwipeMenu menu, int index) {
				switch (index) {
				case 0:
					if (Util.detect(context)) {
						shop.delete(index, position);
					} else {

					}
					break;
				}
			}
		});
		return convertView;
	}
	class Item{
		CheckBox cb;
		SwipeMenuListView lv;
		TextView heji,computer;
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}
}
