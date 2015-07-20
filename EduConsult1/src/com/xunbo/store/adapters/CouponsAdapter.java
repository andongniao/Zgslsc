
package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.educonsult.R;

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
			myitem.num = (TextView) convertView.findViewById(R.id.coupons_item_time);
			myitem.money = (TextView) convertView.findViewById(R.id.coupons_item_money2);
			myitem.time = (TextView) convertView.findViewById(R.id.coupons_item_time1);
			myitem.mustmoney = (TextView) convertView.findViewById(R.id.coupons_item_money1);
			convertView.setTag(myitem);
		}else{
			myitem = (Myitem) convertView.getTag();
		}
			myitem.num.setText("NO."+(position+1)+" Ωÿ÷π"+"2015.8.31"+"»’");
			myitem.mustmoney.setText("3000");
			myitem.money.setText("£§"+"100");
			myitem.time.setText(R.string.coupons_tvtime1);
		return convertView;
	}
	
class Myitem{
	TextView money,mustmoney,num,time;
	
}
}
