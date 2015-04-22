package com.example.educonsult.adapters;

import java.util.ArrayList;

import com.example.educonsult.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class HomeRuzhuAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<String> list;
	private LayoutInflater inflater;
	private int index,len;
	
	
	public HomeRuzhuAdapter(Context context,ArrayList<String> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		index = -1;
	}
	public void SetData(int index){
		this.index = index;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 6;//list!=null?list.size():0;
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
		
		ImageView v = new ImageView(context);
		v.setBackgroundResource(R.drawable.ic_launcher);
		if(index != -1){
			if(index == position){
			v.setBackgroundResource(R.color.orn);
			}
			if(len==position && len!=index){
				v.setBackgroundResource(R.drawable.ic_launcher);
				len = index;
			}
		}else{
			if(index == position){
				v.setBackgroundResource(R.color.orn);
				len = index;
				}
		}
		return v;
	}
	

}
