package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.xunbo.store.R;
import com.xunbo.store.activitys.MyZjActivity.myzj;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.tools.Util;

public class MyZjAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ProductBean>list;
	private LayoutInflater inflater;
	private Item item;
	private int type;
	private myzj myzj;


	public MyZjAdapter(Context context,ArrayList<ProductBean>list,int type, myzj myzj){
		this.context = context;
		this.list = list;
		this.type = type;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<ProductBean>list,int type){
		this.list = list;
		this.type = type;
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
			convertView = inflater.inflate(R.layout.myzj_lv_item, null);
			item = new Item();
			item.iv = (ImageView) convertView.findViewById(R.id.myzj_lv_ic);
			item.tv_title = (TextView) 
					convertView.findViewById(R.id.myzj_lv_tv_title);
			item.tv_subtitle = (TextView) 
					convertView.findViewById(R.id.myzj_lv_tv_subtitle);
			item.tv_price = (TextView) 
					convertView.findViewById(R.id.myzj_lv_tv_price);
			item.tv_util=(TextView)convertView.findViewById(R.id.myzj_lv_tv_uint);
			item.cb = (CheckBox)convertView.findViewById(R.id.myzj_lv_cb);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}

		Util.Getbitmap(item.iv, list.get(position).getThumb());
		String s="";
		if(Util.IsNull(list.get(position).getUnit())){
			s=list.get(position).getUnit();
		}
		item.tv_util.setText(s);
		item.tv_price.setText("гд"+list.get(position).getPrice());
		item.tv_title.setText(list.get(position).getTitle());
		if(Util.IsNull(list.get(position).getSubtitle())){
			item.tv_subtitle.setVisibility(View.VISIBLE);
		item.tv_subtitle.setText(list.get(position).getSubtitle());
		}
		if(type==0){
			item.cb.setVisibility(View.GONE);
		}else{
			item.cb.setVisibility(View.VISIBLE);
		}
		item.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					myzj.addlist(list.get(position).getItemid());
				}else{
					myzj.removelist(list.get(position).getItemid());
				}
			}
		});
		
		return convertView;
	}
	class Item{
		TextView tv_title,tv_price,tv_subtitle,tv_util;
		ImageView iv;
		CheckBox cb;
	}

}
