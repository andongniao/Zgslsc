package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.activitys.ShopcartActivity.shop;
import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.ProductBean;
import com.example.educonsult.beans.ShopBean;
import com.example.educonsult.tools.Util;

public class SearchResultAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ProductBean>list;
	private LayoutInflater inflater;
	private Item item;


	public SearchResultAdapter(Context context,ArrayList<ProductBean>list){
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
			convertView = inflater.inflate(R.layout.search_result_item, null);
			item = new Item();
			item.iv = (ImageView) convertView.findViewById(R.id.search_result_item_iv);
			item.tv_title = (TextView) 
					convertView.findViewById(R.id.search_result_item_tv_title);
			item.tv_address = (TextView) 
					convertView.findViewById(R.id.search_result_item_tv_address);
			item.tv_price = (TextView) 
					convertView.findViewById(R.id.search_result_item_tv_price);
			item.tv_fukuan = (TextView) 
					convertView.findViewById(R.id.search_result_item_tv_fukuan);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		try {
			item.iv.setImageBitmap(Util.getBitmapForNet(list.get(position).getThumb()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		item.tv_title.setText(list.get(position).getTitle());
		item.tv_address.setText(list.get(position).getAreaname());
		item.tv_price.setText(list.get(position).getPrice());
		return convertView;
	}
	class Item{
		TextView tv_title,tv_price,tv_address,tv_fukuan;
		ImageView iv;
	}

}
