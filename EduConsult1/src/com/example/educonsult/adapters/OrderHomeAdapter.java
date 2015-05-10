package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.activitys.ShopcartActivity;
import com.example.educonsult.activitys.ShopcartActivity.shop;
import com.example.educonsult.beans.ShopBean;
import com.example.educonsult.myviews.MyListview;
import com.example.educonsult.myviews.SwipeMenuListView.SwipeMenu;
import com.example.educonsult.myviews.SwipeMenuListView.SwipeMenuCreator;
import com.example.educonsult.myviews.SwipeMenuListView.SwipeMenuItem;
import com.example.educonsult.myviews.SwipeMenuListView.SwipeMenuListView;
import com.example.educonsult.myviews.SwipeMenuListView.SwipeMenuListView.OnMenuItemClickListener;
import com.example.educonsult.tools.Util;

public class OrderHomeAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<Integer>list;
	private LayoutInflater inflater;
	private Item item;
	private OrderLvAdapter adapter;
	private int n;

	public OrderHomeAdapter(Context context,ArrayList<Integer>list){
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
			convertView = inflater.inflate(R.layout.order_home_lv_item, null);
			item = new Item();
			item.lv = (MyListview) convertView.findViewById(R.id.order_home_lv_item_lv);
			item.et = (EditText) convertView.findViewById(R.id.order_home_lv_item_et_liuyan);
			item.tv_title = (TextView) convertView.findViewById(R.id.order_home_lv_item_tv_title);
			item.tv_num = (TextView) convertView.findViewById(R.id.order_home_lv_item_tv_num);
			item.tv_heji = (TextView) convertView.findViewById(R.id.order_home_lv_item_tv_heji);
			item.tv_peisong = (TextView) convertView.findViewById(R.id.order_home_lv_item_tv_peisong);
			item.tv_online = (TextView) convertView.findViewById(R.id.order_home_lv_item_tv_on_line);
			item.tv_qq = (TextView) convertView.findViewById(R.id.order_home_lv_item_tv_qq);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		item.tv_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
		item.tv_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
		ArrayList<Integer> l1 = new ArrayList<Integer>();
		l1.add(1);
		l1.add(2);
		ArrayList<Integer> l2 = new ArrayList<Integer>();
		l2.add(1);
		if(position==0){
			adapter = new OrderLvAdapter(context, l1);
			n = l1.size();
		}else{
			adapter = new OrderLvAdapter(context, l2);
			n = l2.size();
		}
		item.lv.setAdapter(adapter);
		item.tv_num.setText("共"+n+"件商品");
		return convertView;
	}
	class Item{
		TextView tv_title,tv_peisong,tv_heji,tv_num,tv_online,tv_qq;
		MyListview lv;
		EditText et;
	}

}
