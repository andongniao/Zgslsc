package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.activitys.ShopcartActivity.shop;
import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.ShopBean;

public class OrderLvAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ShopBean>list;
	private LayoutInflater inflater;
	private Item item;


	public OrderLvAdapter(Context context,ArrayList<ShopBean>list, int index){
		this.context = context;
		this.list = list;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = inflater.inflate(R.layout.order_two_lv_item, null);
			item = new Item();
			//TODO 
			item.iv = (ImageView) convertView.findViewById(R.id.order_two_lv_item_iv);
			item.iv_uhui = (ImageView) convertView.findViewById(R.id.order_two_lv_item_iv_uhui);
			item.tv_title = (TextView) convertView.findViewById(R.id.order_two_lv_item_tv_title);
			item.tv_price = (TextView) convertView.findViewById(R.id.order_two_lv_item_tv_price);
			item.tv_num = (TextView) convertView.findViewById(R.id.order_two_lv_item_tv_num);
			item.tv_zongjia = (TextView) convertView.findViewById(R.id.order_two_lv_item_tv_zongjia);
			item.tv_uhui = (TextView) convertView.findViewById(R.id.order_two_lv_item_tv_uhui);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		
		
		
		return convertView;
	}
	class Item{
		TextView tv_title,tv_price,tv_zongjia,tv_num,tv_uhui;
		ImageView iv,iv_uhui;
	}

}
