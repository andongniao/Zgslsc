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

import com.example.educonsult.MyApplication;
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
	private OrderBean o;
	private boolean isloding;

	public MyOrderHomeAdapter(Context context,ArrayList<OrderBean>list,Myorder myorder,boolean isloding){
		this.context = context;
		this.list = list;
		this.isloding = isloding;
		this.myorder = myorder;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<OrderBean>list){
		this.list = list;
	}
	public void SetBoolean(boolean isloding){
		this.isloding = isloding;
		if(adapter!=null){
			adapter.SetBoolean(isloding);
		}
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
		o = list.get(position);
		item.tv_title.setText(o.getCompany());
		item.tv_time.setText(o.getAddtime());
		item.tv_statu.setText(o.getStatus());
		item.tv_num.setText("共"+1+"件商品");
		int n = Integer.parseInt(o.getNumber());
		double p = Double.parseDouble(o.getPrice());
		item.tv_shifu.setText("￥"+String.valueOf(p*n));
		adapter = new MyOrderLvAdapter(context,o,position,myorder,isloding);
		item.lv.setAdapter(adapter);
		//		item.tv_day.setText("距离确认收货还有2天");
		item.btn_l.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isloding){
					OrderBean b = list.get(position);
					int t = Integer.parseInt(b.getStatusid());
					if(t == 1){
						myorder.Order_Canale(b);
					}else if(t==2){
						myorder.Order_Fahuo(b);
					}else if(t==3){
						myorder.Order_Repay(b);
					}
				}else{
					Util.ShowToast(context, "正在加载，请稍后...");
				}
			}
		});
		item.btn_r.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!isloding){
					OrderBean b = list.get(position);
					int t = Integer.parseInt(b.getStatusid());
					if(t == 1){
						myorder.Order_Pay(b);
					}else if(t==2){
						myorder.Order_Canale(b);
					}else if(t==3){
						myorder.Order_Refund(b);
					}else if(t==4){
						if(b.getIscomment()==0){
							myorder.Order_commit(b);
						}
					}
				}else{
					Util.ShowToast(context, "正在加载，请稍后...");
				}
			}
		});
		type = Integer.parseInt(o.getStatusid());
		if(type==1){
			item.btn_l.setVisibility(View.VISIBLE);
			item.btn_r.setVisibility(View.VISIBLE);
			item.btn_r.setText("立即付款");
			item.btn_r.setTextColor(context.getResources().getColor(R.color.white));
			item.btn_r.setBackgroundResource(R.drawable.orn_bg_line);
			item.btn_l.setText("取消订单");
			item.btn_l.setTextColor(context.getResources().getColor(R.color.black));
			item.btn_l.setBackgroundResource(R.drawable.order_et_bg_line);
		}else if(type == 2){
			item.btn_l.setVisibility(View.GONE);
			item.btn_r.setVisibility(View.VISIBLE);
			item.btn_r.setText("关闭交易");
			item.btn_r.setTextColor(context.getResources().getColor(R.color.black));
			item.btn_r.setBackgroundResource(R.drawable.order_et_bg_line);
			if(MyApplication.mp.getUser().getType()==0){
				//				item.btn_l.setVisibility(View.VISIBLE);
				item.btn_l.setText("确认发货");
				item.btn_l.setTextColor(context.getResources().getColor(R.color.white));
				item.btn_l.setBackgroundResource(R.drawable.search_lv_isnull_btn_bg);
			}else{
			}
		}else if(type == 3){
			item.btn_l.setVisibility(View.VISIBLE);
			item.btn_r.setVisibility(View.VISIBLE);
			item.btn_r.setText("申请退款");
			item.btn_r.setTextColor(context.getResources().getColor(R.color.black));
			item.btn_r.setBackgroundResource(R.drawable.order_et_bg_line);
			item.btn_l.setText("确认收货");
			item.btn_l.setTextColor(context.getResources().getColor(R.color.white));
			item.btn_l.setBackgroundResource(R.drawable.search_lv_isnull_btn_bg);
		}else if(type == 4){
			int isc = o.getIscomment();
			if(isc==0){
				item.btn_l.setVisibility(View.GONE);
				item.btn_r.setVisibility(View.VISIBLE);
				item.btn_r.setText("评价订单");
				item.btn_r.setTextColor(context.getResources().getColor(R.color.black));
				item.btn_r.setBackgroundResource(R.drawable.order_et_bg_line);
			}else{
				item.btn_l.setVisibility(View.GONE);
				item.btn_r.setVisibility(View.GONE);
			}
			//			item.btn_l.setText("确认发货");
			//			item.btn_l.setTextColor(context.getResources().getColor(R.color.white));
			//			item.btn_l.setBackgroundResource(R.drawable.search_lv_isnull_btn_bg);
		}else{
			item.btn_l.setVisibility(View.GONE);
			item.btn_r.setVisibility(View.GONE);
		}


		return convertView;
	}
	class Item{
		TextView tv_title,tv_time,tv_statu,tv_shifu,tv_num,tv_day;
		MyListview lv;
		Button btn_l,btn_r;
	}

}
