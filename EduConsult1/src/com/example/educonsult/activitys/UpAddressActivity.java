package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.educonsult.R;
import com.example.educonsult.adapters.UpaddressAdapter;

public class UpAddressActivity extends BaseActivity{
	private ListView lv;
	private Context context;
	private ArrayList<Integer> list;
	private UpaddressAdapter adapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightRVisible();
		topRightTGone();
		setTopLeftTv(R.string.address_title);
		setContentXml(R.layout.up_address_layout);
		init();
		addlistener();
	}


	private void init() {
		context = this;
		list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		lv = (ListView) findViewById(R.id.up_address_lv);
		adapter = new UpaddressAdapter(context, list, 0);
		lv.setAdapter(adapter);
	}
	private void addlistener() {
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				adapter.SetData(arg2);
				adapter.notifyDataSetChanged();
			}
		});
	}
}
