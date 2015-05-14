package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.beans.ProductBean;
import com.example.educonsult.tools.Util;

public class HomeLikeAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ProductBean> list;
	private LayoutInflater inflater;
	private Myitem myitem;
	
	
	public HomeLikeAdapter(Context context,ArrayList<ProductBean> list){
		this.context = context;
		this.list = list;
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
			convertView = inflater.inflate(R.layout.home_like_adapter_view, null);
			myitem = new Myitem();
			myitem.iv = (ImageView) convertView.findViewById(R.id.home_like_iv);
			myitem.tv_title = (TextView) convertView.findViewById(R.id.home_like_tv_title);
			myitem.tv_price = (TextView) convertView.findViewById(R.id.home_like_tv_price);
			myitem.tv_unit = (TextView) convertView.findViewById(R.id.home_like_tv_unit);
			myitem.tv_address = (TextView) convertView.findViewById(R.id.home_like_tv_address);
			convertView.setTag(myitem);
		}else{
			myitem = (Myitem) convertView.getTag();
		}
		ProductBean p = list.get(position);
		Util.Getbitmap(myitem.iv, p.getThumb());
		myitem.tv_title.setText(p.getTitle());
		myitem.tv_price.setText(p.getPrice());
		myitem.tv_unit.setText(p.getUnit());
		myitem.tv_address.setText(p.getAreaname());
		return convertView;
	}
	
class Myitem{
	TextView tv_price,tv_title,tv_address,tv_unit;
	ImageView iv;
}
}
