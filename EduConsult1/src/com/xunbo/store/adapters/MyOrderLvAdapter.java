package com.xunbo.store.adapters;


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
import com.xunbo.store.beans.OrderBean;
import com.xunbo.store.tools.Util;

public class MyOrderLvAdapter extends BaseAdapter{
	private Context context;
	private LayoutInflater inflater;
	private Item item;
	private OrderBean orderBean;
	private Myorder myorder;
	private boolean isloding;


	public MyOrderLvAdapter(Context context,OrderBean orderBean,int index,Myorder myorder,boolean isloding){
		this.context = context;
		this.orderBean = orderBean;
		this.myorder = myorder;
		this.isloding = isloding;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(OrderBean orderBean){
		this.orderBean = orderBean;
	}
	public void SetBoolean(boolean isloding){
		this.isloding = isloding;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
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
			item.tv_unit = (TextView) convertView.findViewById(R.id.myorder_two_lv_item_tv_unit);
			item.tv_num = (TextView) convertView.findViewById(R.id.myorder_two_lv_item_tv_num);
			item.tv_order = (TextView) convertView.findViewById(R.id.myorder_two_lv_item_tv_order);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		Bitmap bmp = null;
		try {
			//			bmp = Util.getBitmapForNet(orderBean.getThumb());
			//			if(bmp!=null){
			//				item.iv.setImageBitmap(bmp);
			//			}
			Util.Getbitmap(item.iv, orderBean.getThumb());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		item.tv_title.setText(orderBean.getTitle());
		item.tv_price.setText(orderBean.getPrice());
		String s = "";
		if(Util.IsNull(orderBean.getUnit())){
			s = orderBean.getUnit();
		}
		item.tv_unit.setText(s);
		item.tv_num.setText("¹²"+orderBean.getNumber()+"¼þ");
		item.tv_order.setText(""+orderBean.getItemid());
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!isloding){
					myorder.Order_Detaile(orderBean);
				}else{
					Util.ShowToast(context, R.string.please_wait);
				}
			}
		});

		return convertView;
	}
	class Item{
		TextView tv_title,tv_price,tv_num,tv_unit,tv_order;
		ImageView iv;
	}

}
