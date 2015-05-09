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

public class MoneyCarListAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<Integer>list;
	private LayoutInflater inflater;
	private Item item;
	private int index;


	public MoneyCarListAdapter(Context context,ArrayList<Integer>list,int index){
		this.context = context;
		this.list = list;
		this.index = index;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(int index){
		this.index = index;
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
			convertView = inflater.inflate(R.layout.moneycar_list_item, null);
			item = new Item();
			item.iv = (ImageView) convertView.findViewById(R.id.moneycar_list_item_ima);
			item.ic=(ImageView)convertView.findViewById(R.id.moneycar_list_item_ic);
			item.tv_name = (TextView) convertView.findViewById(R.id.moneycar_list_item_name);
			item.tv_id = (TextView) convertView.findViewById(R.id.moneycar_list_item_carid);
			convertView.setTag(item);
			
		}else{
			item = (Item) convertView.getTag();
		}
		if(index == position){
			item.iv.setVisibility(View.VISIBLE);
		}else{
			item.iv.setVisibility(View.INVISIBLE);
		}
		
		
		return convertView;
	}
	class Item{
		TextView tv_name,tv_id;
		ImageView ic,iv;
	}

}
