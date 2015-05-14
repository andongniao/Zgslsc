package com.example.educonsult.adapters;

import java.util.ArrayList;

import javax.crypto.spec.IvParameterSpec;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.activitys.ShopcartActivity.shop;
import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.ShopBean;
import com.example.educonsult.beans.ShopItemBean;
import com.example.educonsult.tools.UITools;
import com.example.educonsult.tools.Util;

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
			item.tv_zongjia = (TextView) convertView.findViewById(R.id.shopcart_lv_lv_tv_zongjia);
			item.iv_jia = (ImageView) convertView.findViewById(R.id.shopcart_lv_lv_iv_jia);
			item.iv_jian = (ImageView) convertView.findViewById(R.id.shopcart_lv_lv_iv_jian);
			item.et_number = (EditText) convertView.findViewById(R.id.shopcart_lv_lv_et_number);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		ShopBean sb = (ShopBean) list.get(index);
		ArrayList<ShopItemBean> l = sb.getMall();

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
		//item.et_number.setText(b.getNum());
		item.et_number.setText(b.getNum()+"");
		item.tv_price.setText(b.getPrice());
		item.tv_title.setText(b.getTitle());
		int i_num=b.getNum();
		float i_price=Float.parseFloat(b.getPrice());
		float i_allmoney=i_num*i_price;
		item.tv_zongjia.setText(i_allmoney+"");
		try {
			item.iv_ic.setImageBitmap(Util.getBitmapForNet(b.getThumb()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return convertView;
	}

	class Item{
		TextView tv_title,tv_price,tv_zongjia;
		ImageView iv_jia,iv_jian,iv_ic;
		EditText et_number;
		CheckBox cb;
	}

}
