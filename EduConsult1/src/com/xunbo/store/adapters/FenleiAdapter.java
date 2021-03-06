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

import com.example.educonsult.R;

@SuppressLint("ResourceAsColor")
public class FenleiAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<Integer>list;
	private LayoutInflater inflater;
	private Item item;
	private int index;


	public FenleiAdapter(Context context,ArrayList<Integer>list){
		this.context = context;
		this.list = list;
		index = 2;
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
		if(index == position){
			item.tv.setTextColor(context.getResources().getColor(R.color.base_top_title_bg));
			item.tv_show.setVisibility(View.VISIBLE);
			switch (position) {
			case 0:
//				item.ll.setBackgroundColor(context.getResources().getColor(R.color.fenlei_bg));
//				item.iv.setBackgroundResource(R.drawable.home_l);
				item.tv.setText(context.getResources().getString(R.string.home_slid_home));
				item.tv.setVisibility(View.INVISIBLE);
//				item.tv.setEnabled(false);
				break;
			case 1:
				//item.ll.setBackgroundColor(context.getResources().getColor(R.color.fenlei_bg));
				//item.iv.setBackgroundResource(R.drawable.all_l);
				item.tv.setText(context.getResources().getString(R.string.home_slid_all));
				break;
			case 2:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.fenlei1_bg2));
				item.iv.setBackgroundResource(R.drawable.siliao_l);
				item.tv.setText(context.getResources().getString(R.string.home_slid_siliao));
				item.tv.setTextColor(R.color.fame_hui3);
				break;
			case 3:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.fenlei1_bg2));
				item.iv.setBackgroundResource(R.drawable.shouyao_l);
				item.tv.setText(context.getResources().getString(R.string.home_slid_shouyao));
				item.tv.setTextColor(R.color.fame_hui3);
				break;
			case 4:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.fenlei1_bg2));
				item.iv.setBackgroundResource(R.drawable.shebei_l);
				item.tv.setText(context.getResources().getString(R.string.home_slid_yanzhi));
				item.tv.setTextColor(R.color.fame_hui3);
				break;
			case 5:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.fenlei1_bg2));
				item.iv.setBackgroundResource(R.drawable.xunchu_l);
				item.tv.setText(context.getResources().getString(R.string.home_slid_xunchu));
				item.tv.setTextColor(R.color.fame_hui3);
				break;
			case 6:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.fenlei1_bg2));
				item.iv.setBackgroundResource(R.drawable.tianjiaji_l);
				item.tv.setText(context.getResources().getString(R.string.home_slid_tianjiaji));
				item.tv.setTextColor(R.color.fame_hui3);
				break;
			case 7:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.fenlei1_bg2));
				item.iv.setBackgroundResource(R.drawable.yuanliao_l);
				item.tv.setText(context.getResources().getString(R.string.home_slid_yuanliao));
				item.tv.setTextColor(R.color.fame_hui3);
				break;
			case 8:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.fenlei1_bg2));
				item.iv.setBackgroundResource(R.drawable.other_l);
				item.tv.setText(context.getResources().getString(R.string.home_slid_other));
				item.tv.setTextColor(R.color.fame_hui3);
				break;
			}
		}else{
			item.tv.setTextColor(context.getResources().getColor(R.color.white));
			item.tv_show.setVisibility(View.INVISIBLE);
			switch (position) {
			case 0:
//				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_top_title_bg));
//				item.iv.setBackgroundResource(R.drawable.home_q);
//				item.tv.setText(context.getResources().getString(R.string.home_slid_home));
				item.tv.setText(context.getResources().getString(R.string.home_slid_home));
				item.tv.setVisibility(View.INVISIBLE);
				break;
			case 1:
//				item.ll.setBackgroundColor(context.getResources().getColor(R.color.fenlei_bg));
//				item.iv.setBackgroundResource(R.drawable.all_q);
				item.tv.setText(context.getResources().getString(R.string.home_slid_all));
				break;
			case 2:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_top_title_bg));
				item.iv.setBackgroundResource(R.drawable.siliao_q);
				item.tv.setText(context.getResources().getString(R.string.home_slid_siliao));
				break;
			case 3:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_top_title_bg));
				item.iv.setBackgroundResource(R.drawable.shouyao_q);
				item.tv.setText(context.getResources().getString(R.string.home_slid_shouyao));
				break;
			case 4:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_top_title_bg));
				item.iv.setBackgroundResource(R.drawable.shebei_q);
				item.tv.setText(context.getResources().getString(R.string.home_slid_yanzhi));
				break;
			case 5:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_top_title_bg));
				item.iv.setBackgroundResource(R.drawable.xunchu_q);
				item.tv.setText(context.getResources().getString(R.string.home_slid_xunchu));
				break;
			case 6:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_top_title_bg));
				item.iv.setBackgroundResource(R.drawable.tianjiaji_q);
				item.tv.setText(context.getResources().getString(R.string.home_slid_tianjiaji));
				break;
			case 7:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_top_title_bg));
				item.iv.setBackgroundResource(R.drawable.yuanliao_q);
				item.tv.setText(context.getResources().getString(R.string.home_slid_yuanliao));
				break;
			case 8:
				item.ll.setBackgroundColor(context.getResources().getColor(R.color.base_top_title_bg));
				item.iv.setBackgroundResource(R.drawable.other_q);
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
