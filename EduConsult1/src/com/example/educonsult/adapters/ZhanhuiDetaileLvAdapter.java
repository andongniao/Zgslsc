package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.beans.ListUserBean;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.myviews.CircleImageView;
import com.example.educonsult.tools.Util;

public class ZhanhuiDetaileLvAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<Integer>list;
	private LayoutInflater inflater;
	private Item item;


	public ZhanhuiDetaileLvAdapter(Context context,ArrayList<Integer>list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<Integer>list){
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
			convertView = inflater.inflate(R.layout.zhanhui_detaile_lv_item, null);
			item = new Item();
			item.head = (CircleImageView) 
					convertView.findViewById(R.id.zhanhui_detaile_lv_item_iv_head);
			item.iv = (ImageView) 
					convertView.findViewById(R.id.zhanhui_detaile_lv_item_iv_levle);
			item.tv_name = (TextView) 
					convertView.findViewById(R.id.zhanhui_detaile_lv_item_tv_name);
			item.tv_content = (TextView) 
					convertView.findViewById(R.id.zhanhui_detaile_lv_item_tv_content);
			item.tv_time = (TextView) 
					convertView.findViewById(R.id.zhanhui_detaile_lv_item_tv_time);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		String filename = "test";
		Util util = new Util(context);
		if(util.isExistDataCache(filename)&& util.isReadDataCache(filename)){
			ListUserBean listUserBean = (ListUserBean) util.readObject(filename);
			UserBean bean;
			if(position%2==0){
				bean = listUserBean.getList().get(0);
			}else{
				bean = listUserBean.getList().get(0);
			}
//			Util.Getbitmap(item.head, bean.getBmp());
		}
		return convertView;
	}
	class Item{
		TextView tv_name,tv_content,tv_time;
		ImageView iv;
		CircleImageView head;
	}

}
