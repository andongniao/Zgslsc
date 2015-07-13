package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.educonsult.R;
import com.xunbo.store.beans.XinJianBean;

public class XinjianAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<XinJianBean> list;
	private LayoutInflater inflater;
	private int index;
	private Itemview itemview;
    private XinJianBean xinjian; 	
	public XinjianAdapter(Context context,ArrayList<XinJianBean> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		index = -1;
	}
	public void SetData(ArrayList<XinJianBean> list){
		this.list = list;
	}
	
	@Override
	public int getCount() {
		return list!=null?list.size():0;
	}

	@Override
	public XinJianBean getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = inflater.inflate(R.layout.xinjian_item, null);
			itemview = new Itemview();
			itemview.tv = (TextView) convertView.findViewById(R.id.xinjian_item_tv_title);
			convertView.setTag(itemview);
		}else{
			itemview = (Itemview) convertView.getTag();
		}
//		itemview.tv.setText(list.get(position));
		xinjian=list.get(position);
		itemview.tv.setText(xinjian.getTitle());
		return convertView;
	}
	class Itemview{
		TextView tv;
	}

}
