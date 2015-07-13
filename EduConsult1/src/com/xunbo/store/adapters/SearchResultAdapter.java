package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.educonsult.R;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.tools.Util;

public class SearchResultAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ProductBean>list;
	private LayoutInflater inflater;
	private Myitem myitem;


	public SearchResultAdapter(Context context,ArrayList<ProductBean>list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<ProductBean>list){
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list!=null?list.size():0;
//		return 1;
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
		Log.i("listshifouyouzhi", list.get(position).toString());
		if(convertView==null){
			convertView = inflater.inflate(R.layout.home_layout_gv_item, null);
			myitem = new Myitem();
			myitem.iv = (ImageView) convertView.findViewById(R.id.home_layout_gv_item_image);
			myitem.tv_title = (TextView) convertView.findViewById(R.id.home_layout_gv_item_title);
			myitem.tv_price = (TextView) convertView.findViewById(R.id.home_layout_gv_item_money);
			myitem.tv_detaile = (TextView) convertView.findViewById(R.id.home_layout_gv_item_detaile);
			convertView.setTag(myitem);
		}else{
			myitem = (Myitem) convertView.getTag();
		}
		if(Util.IsNull(list.get(position).getApppic())){
			Util.Getbitmap(myitem.iv, list.get(position).getApppic());
		}else{
			Util.Getbitmap(myitem.iv, list.get(position).getThumb());
		}
		if(Util.IsNull(list.get(position).getSubtitle())){
			myitem.tv_detaile.setVisibility(View.VISIBLE);
			myitem.tv_detaile.setText(list.get(position).getSubtitle());
		}
		myitem.tv_price.setText("гд"+list.get(position).getPrice());
		myitem.tv_title.setText(list.get(position).getTitle());
		return convertView;
	}
	
	class Myitem{
		TextView tv_price,tv_title,tv_detaile;
		ImageView iv;
	}
}
