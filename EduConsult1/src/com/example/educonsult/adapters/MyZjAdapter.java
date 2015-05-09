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

public class MyZjAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<Integer>list;
	private LayoutInflater inflater;
	private Item item;


	public MyZjAdapter(Context context,ArrayList<Integer>list){
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
			convertView = inflater.inflate(R.layout.myzj_item, null);
			item = new Item();
			item.iv = (ImageView) convertView.findViewById(R.id.myzj_item_iv);
			item.tv_title = (TextView) 
					convertView.findViewById(R.id.myzj_item_title);
			item.tv_address = (TextView) 
					convertView.findViewById(R.id.myzj_item_address);
			item.tv_price = (TextView) 
					convertView.findViewById(R.id.myzj_item_price);
			item.tv_time = (TextView) 
					convertView.findViewById(R.id.myzj_item_time);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		item.tv_price.setText("гд170");
		return convertView;
	}
	class Item{
		TextView tv_title,tv_price,tv_address,tv_time;
		ImageView iv;
	}

}
