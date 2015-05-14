package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.activitys.UpAddressActivity.UpAddress;
import com.example.educonsult.beans.AddressBean;

public class UpaddressAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<AddressBean> list;
	private LayoutInflater inflater;
	private Item item;
	private int index;
	private UpAddress upAddress;
	

	public UpaddressAdapter(Context context,ArrayList<AddressBean> list,int index,UpAddress upAddress){
		this.context = context;
		this.list = list;
		this.index = index;
		this.upAddress = upAddress;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<AddressBean> list){
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
			convertView = inflater.inflate(R.layout.up_address_lv_item, null);
			item = new Item();
			item.iv = (ImageView) convertView.findViewById(R.id.up_address_lv_item_iv);
			item.tv_name = (TextView) convertView.findViewById(R.id.up_address_lv_item_tv_name);
			item.tv_phone = (TextView) convertView.findViewById(R.id.up_address_lv_item_tv_phone);
			item.tv_address = (TextView) convertView.findViewById(R.id.up_address_lv_item_tv_address);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		item.tv_name.setText(list.get(position).getTruename());
		item.tv_phone.setText(list.get(position).getMobile());
		item.tv_address.setText(list.get(position).getAddress());
		int num=Integer.parseInt(list.get(position).getIsdefault());
		
		if(num == 1){
			item.iv.setVisibility(View.VISIBLE);
		}else{
			item.iv.setVisibility(View.INVISIBLE);
		}
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				upAddress.setAddidde(position);
			}
		});
		
		return convertView;
	}
	class Item{
		TextView tv_name,tv_phone,tv_address;
		ImageView iv;
	}

}
