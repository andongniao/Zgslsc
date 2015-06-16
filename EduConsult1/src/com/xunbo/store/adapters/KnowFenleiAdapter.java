package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xunbo.store.R;

@SuppressLint("ResourceAsColor") public class KnowFenleiAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<Integer>list;
	private LayoutInflater inflater;
	private Item item;
	private int index;


	public KnowFenleiAdapter(Context context,ArrayList<Integer>list){
		this.context = context;
		this.list = list;
		index = -1;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(int index){
		this.index = index;
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
			convertView = inflater.inflate(R.layout.fenlei_item, null);
			item = new Item();
			item.ll = (LinearLayout) convertView.findViewById(R.id.fenlei_item_ll);
			item.iv = (ImageView) convertView.findViewById(R.id.fenlei_item_iv);
			item.tv = (TextView) 
					convertView.findViewById(R.id.fenlei_item_tv);
			item.tv_show = (TextView) 
					convertView.findViewById(R.id.fenlei_item_tv_show);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		item.iv.setVisibility(View.GONE);
		if(index == position){
			item.tv.setTextColor(context.getResources().getColor(R.color.base_top_title_bg));
			item.tv_show.setVisibility(View.VISIBLE);
			switch (position) {
			/*case 0:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_hui));
				item.tv.setText(context.getResources().getString(R.string.home_slid_home));
				break;*/
			case 0:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_hui));
				item.tv.setText(context.getResources().getString(R.string.home_slid_all));
				break;
			case 1:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_hui));
				item.tv.setText(context.getResources().getString(R.string.home_slid_siliao));
				break;
			case 2:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_hui));
				item.tv.setText(context.getResources().getString(R.string.home_slid_shouyao));
				break;
			case 3:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_hui));
				item.tv.setText(context.getResources().getString(R.string.home_slid_yanzhi));
				break;
			case 4:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_hui));
				item.tv.setText(context.getResources().getString(R.string.home_slid_xunchu));
				break;
			case 5:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_hui));
				item.tv.setText(context.getResources().getString(R.string.home_slid_tianjiaji));
				break;
			case 6:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_hui));
				item.tv.setText(context.getResources().getString(R.string.home_slid_yuanliao));
				break;
			case 7:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_hui));
				item.tv.setText(context.getResources().getString(R.string.home_slid_other));
				break;
			}
		}else{
			item.tv.setTextColor(context.getResources().getColor(R.color.black));
			item.tv_show.setVisibility(View.INVISIBLE);
			switch (position) {
			/*case 0:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.white));
				item.tv.setText(context.getResources().getString(R.string.home_slid_home));
				break;*/
			case 0:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.white));
				item.tv.setText(context.getResources().getString(R.string.home_slid_all));
				break;
			case 1:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.white));
				item.tv.setText(context.getResources().getString(R.string.home_slid_siliao));
				break;
			case 2:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.white));
				item.tv.setText(context.getResources().getString(R.string.home_slid_shouyao));
				break;
			case 3:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.white));
				item.tv.setText(context.getResources().getString(R.string.home_slid_yanzhi));
				break;
			case 4:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.white));
				item.tv.setText(context.getResources().getString(R.string.home_slid_xunchu));
				break;
			case 5:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.white));
				item.tv.setText(context.getResources().getString(R.string.home_slid_tianjiaji));
				break;
			case 6:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.white));
				item.tv.setText(context.getResources().getString(R.string.home_slid_yuanliao));
				break;
			case 7:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.white));
				item.tv.setText(context.getResources().getString(R.string.home_slid_other));
				break;
			}
		}







		return convertView;
	}
	class Item{
		LinearLayout ll;
		TextView tv,tv_show;
		ImageView iv;
	}

}
