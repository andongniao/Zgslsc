package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.educonsult.R;

public class KnowMyQuestionAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<String> list;
	private LayoutInflater inflater;
	private Item item;

	public KnowMyQuestionAdapter(Context context,ArrayList<String> list){
		this.context = context;
		this.list = list;
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
	public View getView(int position, View v, ViewGroup parent) {
		if(v==null){
			v = inflater.inflate(R.layout.know_my_question_lv_item, null);
			item = new Item();
			item.tv = (TextView) v.findViewById(R.id.know_my_question_item_tv_title);
			v.setTag(item);
		}else{
			item = (Item) v.getTag();
		}

		return v;
	}
	class Item{
		TextView tv;
	}
}
