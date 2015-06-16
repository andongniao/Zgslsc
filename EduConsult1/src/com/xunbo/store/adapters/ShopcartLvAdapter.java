package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xunbo.store.R;
import com.xunbo.store.activitys.ShopcartActivity.shop;
import com.xunbo.store.beans.ShopBean;
import com.xunbo.store.beans.ShopItemBean;
import com.xunbo.store.tools.Util;

public class ShopcartLvAdapter extends BaseAdapter{
	private Context context;	
	private ArrayList<ShopBean>list;
	private LayoutInflater inflater;
	private Item item;
	private int index;
	private shop shop;
	private int type;


	public ShopcartLvAdapter(Context context,ArrayList<ShopBean>list, int index,shop shop,int type){
		this.context = context;
		this.list = list;
		this.index = index;
		this.shop = shop;
		this.type = type;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<ShopBean>list,int type){
		this.list = list;
		this.type = type;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		ShopBean sb = (ShopBean) list.get(index);
		ArrayList<ShopItemBean> l = sb.getMall();
		return l!=null?l.size():0;
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
			convertView = inflater.inflate(R.layout.shopcart_lv_lv_item, null);
			item = new Item();
			//TODO 
			item.iv_ic=(ImageView)convertView.findViewById(R.id.shopcart_lv_lv_ic);
			item.cb = (CheckBox) convertView.findViewById(R.id.shopcart_lv_lv_cb);
			item.tv_title = (TextView) convertView.findViewById(R.id.shopcart_lv_lv_tv_title);
			item.tv_price = (TextView) convertView.findViewById(R.id.shopcart_lv_lv_tv_price);
			item.tv_unit = (TextView) convertView.findViewById(R.id.shopcart_lv_lv_tv_uint);
			item.tv_zongjia = (TextView) convertView.findViewById(R.id.shopcart_lv_lv_tv_zongjia);
			item.iv_jia = (ImageView) convertView.findViewById(R.id.shopcart_lv_lv_iv_jia);
			item.iv_jian = (ImageView) convertView.findViewById(R.id.shopcart_lv_lv_iv_jian);
			item.et_number = (EditText) convertView.findViewById(R.id.shopcart_lv_lv_et_number);
			item.tv_delete = (TextView) convertView.findViewById(R.id.shopcart_lv_lv_tv_delete);
			item.ll_del = (LinearLayout) convertView.findViewById(R.id.shopcart_lv_lv_ll_delete);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		if(type==1){
			item.ll_del.setVisibility(View.VISIBLE);
			item.cb.setVisibility(View.VISIBLE);
		}else{
			item.ll_del.setVisibility(View.GONE);
			item.cb.setVisibility(View.GONE);
		}
		if(type==0){
			item.cb.setVisibility(View.VISIBLE);
		}else{
			item.cb.setVisibility(View.GONE);
		}
		ShopBean sb = (ShopBean) list.get(index);
		ArrayList<ShopItemBean> l = sb.getMall();

		final ShopItemBean b = (ShopItemBean) l.get(position);

		item.cb.setChecked(b.isIsclick());
		
		item.cb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(b.isIsclick()){
					shop.click(false, index, position);
				}else{
					shop.click(true, index, position);
				}
			}
		});
		item.iv_jia.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				int i = Integer.parseInt(item.et_number.getText().toString());
//				i+=1;
//				item.et_number.setText(""+i);
				int amount=Integer.parseInt(b.getAmount());
				int i = b.getNum();
				if(i>=1&&i<amount){
					shop.add1(index, position);
				}
				//Util.ShowToast(context, "jia");
			}
		});
		item.iv_jian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int amount=Integer.parseInt(b.getAmount());;
				int i = b.getNum();
				if(i>1&&i<=amount){
					shop.jian1(index, position);
				}
			}
		});
		item.ll_del.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (Util.detect(context)) {
					shop.delete(index, position);
				} else {
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
		});
		//item.et_number.setText(b.getNum());
		item.et_number.setText(b.getNum()+"");
		item.tv_price.setText("��"+b.getPrice());
		item.tv_unit.setText(b.getUnit());
		item.tv_title.setText(b.getTitle());
		int i_num=b.getNum();
		float i_price=Float.parseFloat(b.getPrice());
		float i_allmoney=i_num*i_price;
		item.tv_zongjia.setText("��"+i_allmoney);
		Util.Getbitmap(item.iv_ic, b.getThumb());
		
		return convertView;
	}

	class Item{
		TextView tv_title,tv_price,tv_zongjia,tv_unit,tv_delete;
		ImageView iv_jia,iv_jian,iv_ic,iv_del;
		EditText et_number;
		CheckBox cb;
		LinearLayout ll_del;
	}

}
