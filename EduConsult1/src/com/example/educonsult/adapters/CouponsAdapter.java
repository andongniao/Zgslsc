package com.example.educonsult.adapters;

import java.util.ArrayList;

import com.example.educonsult.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CouponsAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<String> list;
	private LayoutInflater inflater;
	private Myitem myitem;
	
	
	
	public CouponsAdapter(Context context,ArrayList<String> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	public CouponsAdapter(Context context){
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;//list!=null?list.size():0;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = inflater.inflate(R.layout.coupons_itme, null);
			myitem = new Myitem();
			myitem.id = (TextView) convertView.findViewById(R.id.coupons_id);
			myitem.money = (TextView) convertView.findViewById(R.id.coupons_money);
			myitem.time = (TextView) convertView.findViewById(R.id.coupons_time);
			myitem.mustmoney = (TextView) convertView.findViewById(R.id.coupons_mustmoney);
			convertView.setTag(myitem);
		}else{
			myitem = (Myitem) convertView.getTag();
		}

		return convertView;
	}
	
class Myitem{
	TextView id,money,mustmoney,time;
	
}
}
