package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.educonsult.R;
import com.xunbo.store.beans.AddressBean;

public class AddressHomeAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<AddressBean>list;
	private LayoutInflater inflater;
	private Item item;


	public AddressHomeAdapter(Context context,ArrayList<AddressBean>list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<AddressBean>list){
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = inflater.inflate(R.layout.address_lv_item_view, null);
			item = new Item();
			item.tv_name = (TextView) convertView.findViewById(R.id.address_item_tv_name);
			item.tv_default = (TextView) convertView.findViewById(R.id.address_item_tv_default);
			item.tv_number = (TextView) convertView.findViewById(R.id.address_item_tv_number);
			item.tv_address = (TextView) convertView.findViewById(R.id.address_item_tv_address);
			item.ll = (LinearLayout) convertView.findViewById(R.id.address_item_ll);
			item.iv = (ImageView) convertView.findViewById(R.id.address_item_iv);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		AddressBean b = list.get(position);
		item.tv_name.setText(b.getTruename());
		int i = Integer.parseInt(b.getIsdefault());
		if(i==1){//默认地址
			item.iv.setVisibility(View.VISIBLE);
			item.tv_name.setTextColor(context.getResources().getColor(R.color.white));
			item.tv_number.setTextColor(context.getResources().getColor(R.color.white));
			item.tv_address.setTextColor(context.getResources().getColor(R.color.white));
			item.ll.setBackgroundColor(context.getResources().getColor(R.color.address_lan));
		}else{
			item.iv.setVisibility(View.INVISIBLE);
			item.tv_name.setTextColor(context.getResources().getColor(R.color.black));
			item.tv_number.setTextColor(context.getResources().getColor(R.color.black));
			item.tv_address.setTextColor(context.getResources().getColor(R.color.black));
			item.ll.setBackgroundColor(context.getResources().getColor(R.color.white));
		}
		item.tv_number.setText(b.getMobile());
		item.tv_address.setText("收货地址："+b.getAddress());
		return convertView;
	}
	class Item{
		TextView tv_name,tv_default,tv_number,tv_address;
		ImageView iv;
		LinearLayout ll;
	}

}
