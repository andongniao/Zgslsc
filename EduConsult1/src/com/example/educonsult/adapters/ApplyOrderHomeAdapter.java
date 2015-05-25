package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.activitys.ApplyOrderActivity.MyApply;
import com.example.educonsult.beans.OrderBean;
import com.example.educonsult.myviews.MyListview;

public class ApplyOrderHomeAdapter extends BaseAdapter implements
OnClickListener{
	private Context context;
	private ArrayList<OrderBean>list;
	private LayoutInflater inflater;
	private Item item;
	private OrderRefundAdapter adapter;
	private int n;
	private MyApply apply;
	private boolean isloding;

	public ApplyOrderHomeAdapter(Context context,ArrayList<OrderBean>list,MyApply apply,boolean isloding){
		this.context = context;
		this.list = list;
		this.isloding = isloding;
		this.apply = apply;
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
			convertView = inflater.inflate(R.layout.apply_home_lv_item, null);
			item = new Item();
			item.lv = (MyListview) convertView.findViewById(R.id.apply_home_lv_item_lv);
			item.tv_title = (TextView) convertView.findViewById(R.id.apply_home_lv_item_tv_title);
			//tv_title,tv_applying,tv_allmoney,tv_orderid,tv_quxiao,tv_xiugai
			item.tv_orderid = (TextView) 
					convertView.findViewById(R.id.apply_home_orderid);
			item.tv_applying = (TextView) 
					convertView.findViewById(R.id.apply_home_lv_item_tv_on_line);
			item.tv_allmoney = (TextView) 
					convertView.findViewById(R.id.applyinfo_home_lv_item_tv_uhui);
			item.tv_quxiao = (TextView) 
					convertView.findViewById(R.id.apply_home_lv_item_tv_quxiao);
			item.tv_quxiao.setOnClickListener(this);
			item.tv_xiugai = (TextView) 
					convertView.findViewById(R.id.apply_home_lv_item_tv_xiugai);
			item.tv_xiugai.setOnClickListener(this);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		OrderBean bean = list.get(position);
		item.tv_title.setText(bean.getCompany());
		item.tv_orderid.setText(bean.getItemid());
		item.tv_allmoney.setText("гд"+bean.getMoney());
		item.tv_xiugai.setVisibility(View.INVISIBLE);
		item.tv_quxiao.setVisibility(View.INVISIBLE);
		item.tv_applying.setText(bean.getStatus());
		
		
		
		adapter = new OrderRefundAdapter(context, bean,apply,isloding);
		item.lv.setAdapter(adapter);

		return convertView;
	}
	class Item{
		TextView tv_title,tv_applying,tv_allmoney,tv_orderid,tv_quxiao,tv_xiugai;
		MyListview lv;

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.apply_home_lv_item_tv_quxiao:

			break;
		case R.id.apply_home_lv_item_tv_xiugai:

			break;


		default:
			break;
		}
	}

}
