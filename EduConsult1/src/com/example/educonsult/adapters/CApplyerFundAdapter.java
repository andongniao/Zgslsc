package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.activitys.ShopcartActivity.shop;
import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.ShopBean;

public class CApplyerFundAdapter extends BaseAdapter implements OnClickListener{
	private Context context;
	private ArrayList<Integer>list;
	private LayoutInflater inflater;
	private Item item;


	public CApplyerFundAdapter(Context context,ArrayList<Integer>list){
		this.context = context;
		this.list = list;
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
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		
		
		
		return convertView;
	}
	class Item{
		private TextView tv_pname,tv_cname,tv_talk,tv_money,tv_num,tv_orderid;
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
