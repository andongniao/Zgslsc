package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.educonsult.R;
import com.xunbo.store.beans.FenleiBean;

public class Catlv3Adapter extends BaseAdapter{
	private Context context;
	private ArrayList<FenleiBean>list;
	private LayoutInflater inflater;
	private Item item;
	private int index;


	public Catlv3Adapter(Context context,ArrayList<FenleiBean> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
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
	public View getView(final int position, View v, ViewGroup parent) {
//		TextView v = new TextView(context);
//		v.setText(list.get(position).getCatname());
		
		if(v==null){
			item = new Item();
			v = inflater.inflate(R.layout.group_item, null);
			item.tv_text = (TextView)v.findViewById(R.id.id_group_txt);  
			v.setTag(item);
		}else{
			item = (Item) v.getTag();
		}
		item.tv_text.setText(list.get(position).getCatname());  
		item.tv_text.setMaxEms(5);
//		LinearLayout ll = (LinearLayout)v.findViewById(R.id.id_group_ll);  

		return v;
	}
	class Item{
		TextView tv_text;
	}

}
