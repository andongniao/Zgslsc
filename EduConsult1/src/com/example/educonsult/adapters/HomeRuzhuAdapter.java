package com.example.educonsult.adapters;

import java.util.ArrayList;

import com.example.educonsult.R;
import com.example.educonsult.beans.CompanyBean;
import com.example.educonsult.tools.Util;

import android.content.ClipData.Item;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class HomeRuzhuAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<CompanyBean> list;
	private LayoutInflater inflater;
	private int index,len;
	private Item item;
	
	
	public HomeRuzhuAdapter(Context context,ArrayList<CompanyBean> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		index = -1;
	}
	public void SetData(ArrayList<CompanyBean> list){
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
		
//		ImageView v = new ImageView(context);
////		v.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
////		v.setBackgroundResource(R.drawable.ic_launcher);
//		if(index != -1){
//			if(index == position){
//			v.setBackgroundResource(R.color.orn);
//			}
//			if(len==position && len!=index){
//				v.setBackgroundResource(R.drawable.ic_launcher);
//				len = index;
//			}
//		}else{
//			if(index == position){
//				v.setBackgroundResource(R.color.orn);
//				len = index;
//				}
//		}
		if(convertView==null){
			convertView = inflater.inflate(R.layout.home_ruzhu_lv_item, null);
			item = new Item();
			item.iv = (ImageView) convertView.findViewById(R.id.home_ruzhu_lv_item_iv);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		Util.Getbitmap(item.iv, list.get(position).getThumb());
		return convertView;
	}
	
class Item{
	ImageView iv;
}
}
