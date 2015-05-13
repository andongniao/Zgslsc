package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.activitys.MyOrderActivity.Myorder;
import com.example.educonsult.beans.OrderBean;
import com.example.educonsult.tools.Util;

public class MyOrderLvAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<OrderBean>list;
	private LayoutInflater inflater;
	private Item item;
	private OrderBean orderBean;
	private Myorder myorder;


	public MyOrderLvAdapter(Context context,ArrayList<OrderBean>list,int index,Myorder myorder){
		this.context = context;
		this.list = list;
		orderBean = list.get(index);
		this.myorder = myorder;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<OrderBean>list){
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
			convertView = inflater.inflate(R.layout.myorder_two_lv_item, null);
			item = new Item();
			//TODO 
			item.iv = (ImageView) convertView.findViewById(R.id.myorder_two_lv_item_iv);
			item.tv_title = (TextView) convertView.findViewById(R.id.myorder_two_lv_item_tv_title);
			item.tv_price = (TextView) convertView.findViewById(R.id.myorder_two_lv_item_tv_price);
			item.tv_num = (TextView) convertView.findViewById(R.id.myorder_two_lv_item_tv_num);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		Bitmap bmp = null;
		try {
			bmp = Util.getBitmapForNet(orderBean.getThumb());
			if(bmp!=null){
				item.iv.setImageBitmap(bmp);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		item.tv_title.setText(orderBean.getTitle());
		item.tv_price.setText(orderBean.getPrice());
		item.tv_num.setText(orderBean.getNumber());
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				myorder.Order_Detaile(orderBean);
			}
		});
		
		return convertView;
	}
	class Item{
		TextView tv_title,tv_price,tv_num;
		ImageView iv;
	}

}
