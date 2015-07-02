package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xunbo.store.R;
import com.xunbo.store.activitys.ShopcartActivity.shop;
import com.xunbo.store.activitys.StoreActivity;
import com.xunbo.store.beans.ShopBean;
import com.xunbo.store.myviews.SwipeMenuListView.SwipeMenu;
import com.xunbo.store.myviews.SwipeMenuListView.SwipeMenuCreator;
import com.xunbo.store.myviews.SwipeMenuListView.SwipeMenuItem;
import com.xunbo.store.myviews.SwipeMenuListView.SwipeMenuListView;
import com.xunbo.store.tools.Util;

public class ShopcartHomeAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ShopBean>list;
	private LayoutInflater inflater;
	private Item item;
	private ShopcartLvAdapter adapter;
	private shop shop;
	private ShopBean shopbean;
	private int type;

	public ShopcartHomeAdapter(Context context,ArrayList<ShopBean>list, shop shop,int type){
		this.context = context;
		this.list = list;
		this.shop = shop;
		this.type = type;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<ShopBean>list,int type){
		this.list = list;
		this.type = type;
		if(adapter!=null){
			adapter.SetData(list, type);
		}
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
			item.title=(TextView)convertView.findViewById(R.id.shopcart_home_lv_tv_title);
			item.cb = (CheckBox) convertView.findViewById(R.id.shopcart_home_lv_cb);
			item.lv = (SwipeMenuListView) convertView.findViewById(R.id.shopcart_home_lv_lv);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		final ShopBean s = (ShopBean) list.get(index);
		float sum=0,i_price;
		int i_num;
		
		for(int i=0;i<s.getMall().size();i++){
			if(s.getMall().get(i).isIsclick()){
				
				i_num=s.getMall().get(i).getNum();
				i_price=Float.parseFloat(s.getMall().get(i).getPrice());
				sum=sum+i_num*i_price;
			}
		}
		if(type==0){
			item.cb.setVisibility(View.VISIBLE);
		}else{
			item.cb.setVisibility(View.GONE);
		}
		item.cb.setChecked(s.isIsclick());
		item.title.setText(s.getCompany()+">");
		item.heji.setText("гд"+sum);
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
		item.title.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,StoreActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("storeid", "");
				intent.putExtra("storename",s.getCompany());
				context.startActivity(intent);
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
//		item.lv.setMenuCreator(creator);
//		ShopBean sb = (ShopBean) list.get(position);
//		ArrayList<Object> l = sb.getList();
		adapter = new ShopcartLvAdapter(context,list,index, shop,type);
		item.lv.setAdapter(adapter);
		Util.setListViewHeightBasedOnChildren(item.lv);
//		item.lv.setOnMenuItemClickListener(new OnMenuItemClickListener() {
//			@Override
//			public void onMenuItemClick(int position, SwipeMenu menu, int i) {
//				switch (i) {
//				case 0:
//					break;
//				}
//			}
//		});
		return convertView;
	}
	class Item{
		CheckBox cb;
		SwipeMenuListView lv;
		TextView heji,computer,title;
	}

	private int dp2px(int dp) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
				context.getResources().getDisplayMetrics());
	}
}
