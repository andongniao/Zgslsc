package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.activitys.ProductDetaileActivity;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.tools.Util;

public class StoreShopAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ProductBean> list;
	private LayoutInflater inflater;
	private Item item;

	public StoreShopAdapter(Context context,ArrayList<ProductBean> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	public void setData(ArrayList<ProductBean> list){
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
	public View getView(final int position, View v, ViewGroup parent) {
		if(v==null){
			v = inflater.inflate(R.layout.home_layout_gv_item, null);
			item = new Item();
			item.iv = (ImageView) v.findViewById(R.id.home_layout_gv_item_image);
			item.tv_title = (TextView) v.findViewById(R.id.home_layout_gv_item_title);
			item.tv_sub = (TextView) v.findViewById(R.id.home_layout_gv_item_detaile);
			item.tv_price = (TextView) v.findViewById(R.id.home_layout_gv_item_money);
			v.setTag(item);
		}else{
			item = (Item) v.getTag();
		}
		Util.Getbitmap(item.iv, list.get(position).getThumb());
		item.tv_title.setText(list.get(position).getTitle());
		item.tv_price.setText("гд"+list.get(position).getPrice());
		if(Util.IsNull(list.get(position).getSubtitle())){
			item.tv_sub.setText(list.get(position).getSubtitle());
			item.tv_sub.setVisibility(View.VISIBLE);
		}


		v.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context,ProductDetaileActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				Bundle b=new Bundle();
				b.putSerializable("product", list.get(position));
				intent.putExtra("productbundle", b);
				context.startActivity(intent);
			}
		});
		return v;
	}


	class Item{
		ImageView iv;
		TextView tv_title,tv_sub,tv_price;
	}

}
