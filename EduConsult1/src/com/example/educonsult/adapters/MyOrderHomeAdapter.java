package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.activitys.MyOrderActivity.Myorder;
import com.example.educonsult.myviews.MyListview;

public class MyOrderHomeAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<Integer>list;
	private LayoutInflater inflater;
	private Item item;
	private MyOrderLvAdapter adapter;
	private int n;
	private Myorder myorder;

	public MyOrderHomeAdapter(Context context,ArrayList<Integer>list,Myorder myorder){
		this.context = context;
		this.list = list;
		this.myorder = myorder;
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
			convertView = inflater.inflate(R.layout.myorder_home_lv_item, null);
			item = new Item();
			item.lv = (MyListview) convertView.findViewById(R.id.myorder_home_lv_item_lv);
			item.tv_title = (TextView) convertView.findViewById(R.id.myorder_home_lv_item_tv_title);
			item.tv_time = (TextView) 
					convertView.findViewById(R.id.myorder_home_lv_item_tv_time);
			item.tv_statu = (TextView) 
					convertView.findViewById(R.id.myorder_home_lv_item_tv_statu);
			item.tv_num = (TextView) 
					convertView.findViewById(R.id.myorder_home_lv_item_tv_num);
			item.tv_shifu = (TextView) 
					convertView.findViewById(R.id.myorder_home_lv_item_tv_zongjia);
			item.tv_day = (TextView) 
					convertView.findViewById(R.id.myorder_home_lv_item_tv_houhuodays);
			item.btn_l = (Button) 
					convertView.findViewById(R.id.myorder_home_lv_item_btn_l);
			item.btn_r = (Button) 
					convertView.findViewById(R.id.myorder_home_lv_item_btn_r);
			
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		ArrayList<Integer> l1 = new ArrayList<Integer>();
		l1.add(1);
		l1.add(2);
		ArrayList<Integer> l2 = new ArrayList<Integer>();
		l2.add(1);
		if((position/2)==0){
			adapter = new MyOrderLvAdapter(context, l1);
			n = l1.size();
		}else{
			adapter = new MyOrderLvAdapter(context, l2);
			n = l2.size();
		}
		item.lv.setAdapter(adapter);
		item.tv_num.setText("共"+n+"件商品");
		item.tv_day.setText("距离确认收货还有2天");
		item.btn_l.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myorder.delte(position);
			}
		});
		return convertView;
	}
	class Item{
		TextView tv_title,tv_time,tv_statu,tv_shifu,tv_num,tv_day;
		MyListview lv;
		Button btn_l,btn_r;
	}

}
