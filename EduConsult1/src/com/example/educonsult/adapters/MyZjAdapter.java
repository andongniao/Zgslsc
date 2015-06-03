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

public class MyZjAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ProductBean>list;
	private LayoutInflater inflater;
	private Item item;


	public MyZjAdapter(Context context,ArrayList<ProductBean>list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<ProductBean>list){
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
			item.tv_util=(TextView)convertView.findViewById(R.id.myzj_item_utile);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		
//		try {
			//item.iv.setImageBitmap(Util.getBitmapForNet(list.get(position).getThumb()));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	   Util.Getbitmap(item.iv, list.get(position).getThumb());
		item.tv_address.setText(list.get(position).getArea());
		String s="";
		if(Util.IsNull(list.get(position).getUnit())){
			s=list.get(position).getUnit();
		}
		item.tv_util.setText(s);
		item.tv_price.setText("гд"+list.get(position).getPrice());
		item.tv_title.setText(list.get(position).getTitle());
		return convertView;
	}
	class Item{
		TextView tv_title,tv_price,tv_address,tv_time,tv_util;
		ImageView iv;
	}

}
