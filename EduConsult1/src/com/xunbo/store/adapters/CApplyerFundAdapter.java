package com.xunbo.store.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.educonsult.R;
import com.xunbo.store.beans.RefundInfoBean;
import com.xunbo.store.tools.Util;

@SuppressLint("InflateParams") public class CApplyerFundAdapter extends BaseAdapter implements OnClickListener{
	private RefundInfoBean rebean;
	private LayoutInflater inflater;
	private Item item;


	public CApplyerFundAdapter(Context context,RefundInfoBean rebean){
		this.rebean = rebean;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(RefundInfoBean rebean){
		this.rebean = rebean;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 1;
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
			convertView = inflater.inflate(R.layout.applyrefund_item, null);
			item = new Item();
			//TODO 
			item.iv = (ImageView) convertView.findViewById(R.id.applyrefund_ic);
			item.tv_talk=(TextView)convertView.findViewById(R.id.applyrefun_talk);
			item.tv_talk.setOnClickListener(this);
			item.iv=(ImageView)convertView.findViewById(R.id.applyrefund_ic);
			item.tv_pname=(TextView)convertView.findViewById(R.id.applyrefund_productname);
			item.tv_cname=(TextView)convertView.findViewById(R.id.applyrefund_computername);
			item.tv_money=(TextView)convertView.findViewById(R.id.applyrefun_money);
			item.tv_num=(TextView)convertView.findViewById(R.id.applyrefun_num);
			item.tv_orderid=(TextView)convertView.findViewById(R.id.applyerfund_orderid);
			item.tv_unit=(TextView)convertView.findViewById(R.id.applyrefun_money_unit);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		item.tv_talk.setVisibility(View.INVISIBLE);
		Util.Getbitmap(item.iv, rebean.getThumb());
		item.tv_pname.setText(rebean.getTitle());
		item.tv_cname.setText(rebean.getCompany());
		item.tv_money.setText(rebean.getPrice());
		item.tv_num.setText("��"+rebean.getNumber()+"��");
		item.tv_orderid.setText(rebean.getItemid());
		item.tv_unit.setText(rebean.getUnit());
		
		return convertView;
	}
	class Item{
		private TextView tv_pname,tv_cname,tv_talk,tv_money,tv_num,tv_orderid,tv_unit;
		ImageView iv;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.applyrefun_talk:
			
			break;

		default:
			break;
		}
		
	}

}
