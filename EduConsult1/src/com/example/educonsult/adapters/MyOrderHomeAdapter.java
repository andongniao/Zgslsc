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
import com.example.educonsult.beans.OrderBean;
import com.example.educonsult.myviews.MyListview;
import com.example.educonsult.tools.Util;

public class MyOrderHomeAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<OrderBean>list;
	private LayoutInflater inflater;
	private Item item;
	private MyOrderLvAdapter adapter;
	private int n;
	private Myorder myorder;
	private int type;

	public MyOrderHomeAdapter(Context context,ArrayList<OrderBean>list,Myorder myorder){
		this.context = context;
		this.list = list;
		this.myorder = myorder;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<OrderBean>list){
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
		final OrderBean o = list.get(position);
		item.tv_title.setText(o.getCompany());
		item.tv_time.setText(o.getAddtime());
		item.tv_statu.setText(o.getStatus());
		item.tv_num.setText("共"+1+"件商品");
		item.tv_shifu.setText(o.getMoney());
		adapter = new MyOrderLvAdapter(context, list,position,myorder);
		item.lv.setAdapter(adapter);
//		item.tv_day.setText("距离确认收货还有2天");
		type = Integer.parseInt(o.getStatusid());
		if(type==1){
			item.btn_r.setText("立即付款");
			item.btn_r.setTextColor(context.getResources().getColor(R.color.white));
			item.btn_r.setBackgroundResource(R.color.orn);
			item.btn_l.setText("取消订单");
			item.btn_l.setTextColor(context.getResources().getColor(R.color.black));
			item.btn_l.setBackgroundResource(R.drawable.order_et_bg_line);
		}else if(type == 2){
			item.btn_r.setText("关闭交易");
			item.btn_r.setTextColor(context.getResources().getColor(R.color.black));
			item.btn_r.setBackgroundResource(R.drawable.order_et_bg_line);
			item.btn_l.setText("确认发货");
			item.btn_l.setTextColor(context.getResources().getColor(R.color.white));
			item.btn_l.setBackgroundResource(R.drawable.search_lv_isnull_btn_bg);
		}else if(type == 3){
			item.btn_r.setText("申请退款");
			item.btn_r.setTextColor(context.getResources().getColor(R.color.black));
			item.btn_r.setBackgroundResource(R.drawable.order_et_bg_line);
			item.btn_l.setText("确认收货");
			item.btn_l.setTextColor(context.getResources().getColor(R.color.white));
			item.btn_l.setBackgroundResource(R.drawable.search_lv_isnull_btn_bg);
		}else if(type == 4){
			item.btn_r.setText("评价订单");
			item.btn_r.setTextColor(context.getResources().getColor(R.color.black));
			item.btn_r.setBackgroundResource(R.drawable.order_et_bg_line);
//			item.btn_l.setText("确认发货");
//			item.btn_l.setTextColor(context.getResources().getColor(R.color.white));
//			item.btn_l.setBackgroundResource(R.drawable.search_lv_isnull_btn_bg);
			item.btn_l.setVisibility(View.GONE);
		}
		item.btn_l.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myorder.Order_Canale(o);
			}
		});
		item.btn_r.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				myorder.Order_Canale(o);
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
