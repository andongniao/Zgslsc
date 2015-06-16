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
			item.tv_unit = (TextView) 
					convertView.findViewById(R.id.search_result_item_tv_unit);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		Util.Getbitmap(item.iv, list.get(position).getThumb());
		item.tv_title.setText(list.get(position).getTitle());
		item.tv_address.setText(list.get(position).getAreaname()); 
		String s ="";
		if(Util.IsNull(list.get(position).getUnit())){
			s=list.get(position).getUnit();
		}
		item.tv_price.setText("��"+list.get(position).getPrice());
		item.tv_unit.setText(s);
		return convertView;
	}
	class Item{
		TextView tv_title,tv_price,tv_address,tv_fukuan,tv_unit;
		ImageView iv;
	}

}