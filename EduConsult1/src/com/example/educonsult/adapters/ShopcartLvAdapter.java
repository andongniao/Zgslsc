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
import com.example.educonsult.beans.ShopItemBean;

public class ShopcartLvAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ShopBean>list;
	private LayoutInflater inflater;
	private Item item;
	private int index;
	private shop shop;


	public ShopcartLvAdapter(Context context,ArrayList<ShopBean>list, int index,shop shop){
		this.context = context;
		this.list = list;
		this.index = index;
		this.shop = shop;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<ShopBean>list){
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		ShopBean sb = (ShopBean) list.get(index);
		ArrayList<ShopItemBean> l = sb.getList();
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
			item.cb = (CheckBox) convertView.findViewById(R.id.shopcart_lv_lv_cb);
			item.tv_title = (TextView) convertView.findViewById(R.id.shopcart_lv_lv_tv_title);
			item.tv_price = (TextView) convertView.findViewById(R.id.shopcart_lv_lv_tv_price);
			item.tv_zongjia = (TextView) convertView.findViewById(R.id.shopcart_lv_lv_tv_zongjia);
			item.iv_jia = (ImageView) convertView.findViewById(R.id.shopcart_lv_lv_iv_jia);
			item.iv_jian = (ImageView) convertView.findViewById(R.id.shopcart_lv_lv_iv_jian);
			item.et_number = (EditText) convertView.findViewById(R.id.shopcart_lv_lv_et_number);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		ShopBean sb = (ShopBean) list.get(index);
		ArrayList<ShopItemBean> l = sb.getList();

		final ShopItemBean b = (ShopItemBean) l.get(position);

		item.cb.setChecked(b.isIsclick());
//		item.cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//			
//			@Override
//			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//				shop.click(isChecked, index, position);
//			}
//		});
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
				shop.add1(index, position);
			}
		});
		item.iv_jian.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				int i = b.getNum();
				if(i>0){
					shop.jian1(index, position);
				}
			}
		});
		item.et_number.setText(list.get(index).getList().get(position).getNum()+"");
		return convertView;
	}
	class Item{
		TextView tv_title,tv_price,tv_zongjia;
		ImageView iv_jia,iv_jian;
		EditText et_number;
		CheckBox cb;
	}

}
