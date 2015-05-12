package com.example.educonsult.adapters;

import java.util.ArrayList;

import com.example.educonsult.R;
import com.example.educonsult.beans.MoneyDetaileBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MoneyQueryAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<MoneyDetaileBean> list;
	private LayoutInflater inflater;
	private Myitem myitem;
	private MoneyDetaileBean money;
	
	
	
	public MoneyQueryAdapter(Context context,ArrayList<MoneyDetaileBean> list){
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
	public MoneyDetaileBean getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = inflater.inflate(R.layout.money_query_queryitem, null);
			myitem = new Myitem();
			myitem.money = (TextView) convertView.findViewById(R.id.money_query_queryitem_money);
			myitem.queryway = (TextView) convertView.findViewById(R.id.money_query_queryitem_way);
			myitem.time = (TextView) convertView.findViewById(R.id.money_query_queryitem_time);
			myitem.querymoney = (TextView) convertView.findViewById(R.id.money_query_queryitem_querymoney);
			convertView.setTag(myitem);
		}else{
			myitem = (Myitem) convertView.getTag();
		}
		money=list.get(position);
		//myitem.money.setText(money.get)

		return convertView;
	}
	
class Myitem{
	TextView money,queryway,time,querymoney;
	
}
}
