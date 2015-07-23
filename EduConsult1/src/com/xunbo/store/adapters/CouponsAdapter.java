
package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.educonsult.R;
import com.xunbo.store.beans.CouponBean;

public class CouponsAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<CouponBean> list;
	private LayoutInflater inflater;
	private Myitem myitem;
	
	
	
	public CouponsAdapter(Context context,ArrayList<CouponBean> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	public CouponsAdapter(Context context){
		this.context = context;
		inflater = LayoutInflater.from(context);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = inflater.inflate(R.layout.coupons_itme, null);
			myitem = new Myitem();
			myitem.num = (TextView) convertView.findViewById(R.id.coupons_item_time);
			myitem.money = (TextView) convertView.findViewById(R.id.coupons_item_money2);
			myitem.time = (TextView) convertView.findViewById(R.id.coupons_item_time1);
			myitem.mustmoney = (TextView) convertView.findViewById(R.id.coupons_item_money1);
			myitem.type=(TextView)convertView.findViewById(R.id.coupons_item_type);
			convertView.setTag(myitem);
		}else{
			myitem = (Myitem) convertView.getTag();
		}
		
//		ng id;//优惠券iD
//		private String value;//面值
//		private String payment;//只有价格大于此属性的时候可以使用优惠券
//		private String rangecatid;
//		private String rangecatname;
//		private String expiretime;//时间格式2015-08-05
//		private int type;//1未使用2已过期
			myitem.num.setText("NO."+(position+1)+" 截止"+list.get(position).getExpiretime()+"日");
			String s=list.get(position).getPayment().substring(0, list.get(position).getPayment().indexOf("."));
			String c=list.get(position).getValue().substring(0, list.get(position).getValue().indexOf("."));
			
			myitem.mustmoney.setText(s);
			myitem.money.setText("￥"+c);
			myitem.type.setText(list.get(position).getRangecatname());
			if(list.get(position).getType()==1){
				myitem.time.setText(R.string.coupons_tvtime1);
			}else if(list.get(position).getType()==2){
				myitem.time.setText(R.string.coupons_tvtime2);
			}
			
		return convertView;
	}
	
class Myitem{
	TextView money,mustmoney,num,time,type;
	
}
}
