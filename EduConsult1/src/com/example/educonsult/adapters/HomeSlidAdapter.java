package com.example.educonsult.adapters;

import java.util.ArrayList;

import com.example.educonsult.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ResourceAsColor") 
public class HomeSlidAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<String> list;
	private LayoutInflater inflater;
	private int index;
	private Myitem myitem;
	private int type;


	public HomeSlidAdapter(Context context,ArrayList<String> list,int type){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		index = -1;
		this.type = type;
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

	@SuppressLint("ResourceAsColor") @Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = inflater.inflate(R.layout.home_slid_view_l, null);
			myitem = new Myitem();
			myitem.tv_title = (TextView) convertView.findViewById(R.id.home_slid_view_l_tv_title);
			myitem.tv_r = (TextView) convertView.findViewById(R.id.home_slid_view_l_tv_r);
			myitem.ll = (LinearLayout) convertView.findViewById(R.id.home_slid_view_l_ll);
			convertView.setTag(myitem);
		}else{
			myitem = (Myitem) convertView.getTag();
		}
		if(type==1){
			for(int i=0;i<6;i++){
				if(i==position){
					myitem.ll.setBackgroundResource(R.color.base_top_title_bg);
					myitem.tv_title.setTextColor(context.getResources().getColor(R.color.white));//R.color.white);
					myitem.tv_r.setVisibility(View.INVISIBLE);
				}
			}
			if(index == position){
				myitem.ll.setBackgroundResource(R.color.orn);
				myitem.tv_title.setTextColor(context.getResources().getColor(R.color.base_top_title_bg));
				myitem.tv_r.setVisibility(View.VISIBLE);
			}
		}else if(type==2){
			for(int i=0;i<6;i++){
				if(index==position){
					myitem.ll.setBackgroundColor(context.getResources().getColor(R.color.base_hui));
					myitem.tv_title.setTextColor(context.getResources().getColor(R.color.base_top_title_bg));
				}else{
					myitem.ll.setBackgroundColor(context.getResources().getColor(R.color.white));
					myitem.tv_title.setTextColor(context.getResources().getColor(R.color.black));
				}
				
			}
		}
		return convertView;
	}
	class Myitem{
		TextView tv_title,tv_r;
		LinearLayout ll;
	}

}
