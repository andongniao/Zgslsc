package com.example.educonsult.adapters;

import java.util.ArrayList;

import org.w3c.dom.Text;

import com.example.educonsult.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<String> list;
	private LayoutInflater inflater;
	private int index;
	private Itemview itemview;
	
	
	public SearchAdapter(Context context,ArrayList<String> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		index = -1;
	}
	public void SetData(ArrayList<String>list){
		this.list = list;
	}
	
	@Override
	public int getCount() {
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
			convertView = inflater.inflate(R.layout.search_lv_view, null);
			itemview = new Itemview();
			itemview.tv_c = (TextView) convertView.findViewById(R.id.search_lv_view_tv_c);
			convertView.setTag(itemview);
		}else{
			itemview = (Itemview) convertView.getTag();
		}
		itemview.tv_c.setText(list.get(position));
		return convertView;
	}
	class Itemview{
		TextView tv_c;
	}

}
