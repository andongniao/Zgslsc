package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.xunbo.store.R;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.tools.Util;

public class HomeGridAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ProductBean> list;
	private LayoutInflater inflater;
	private Myitem myitem;
	
	
	public HomeGridAdapter(Context context,ArrayList<ProductBean> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	public HomeGridAdapter(Context context){
		this.context = context;
		inflater = LayoutInflater.from(context);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		//return list!=null?list.size():0;
		return 6;
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
			convertView = inflater.inflate(R.layout.home_layout_gv_item, null);
			myitem = new Myitem();
			myitem.iv = (ImageView) convertView.findViewById(R.id.home_layout_gv_item_image);
			myitem.tv_title = (TextView) convertView.findViewById(R.id.home_layout_gv_item_title);
			myitem.tv_price = (TextView) convertView.findViewById(R.id.home_layout_gv_item_money);
			myitem.tv_detaile = (TextView) convertView.findViewById(R.id.home_layout_gv_item_detaile);
			convertView.setTag(myitem);
		}else{
			myitem = (Myitem) convertView.getTag();
		}
		return convertView;
	}
	
class Myitem{
	TextView tv_price,tv_title,tv_detaile;
	ImageView iv;
}
}
