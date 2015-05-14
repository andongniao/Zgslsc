package com.example.educonsult.adapters;

import java.util.ArrayList;

import com.example.educonsult.R;
import com.example.educonsult.beans.AddressBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		AddressBean b = list.get(position);
		item.tv_name.setText(b.getTruename());
		int i = Integer.parseInt(b.getIsdefault());
		if(i==1){
			item.tv_default.setTextColor(context.getResources().getColor(R.color.black));
		}else{
			item.tv_default.setTextColor(context.getResources().getColor(R.color.orn));
		}
		item.tv_number.setText(b.getMobile());
		item.tv_address.setText(b.getAddress());
		return convertView;
	}
	class Item{
		TextView tv_name,tv_default,tv_number,tv_address;
	}

}
