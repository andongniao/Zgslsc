package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.educonsult.R;
import com.example.educonsult.adapters.UpaddressAdapter;

public class UpAddressActivity extends BaseActivity{
	private ListView lv;
	private Context context;
	private ArrayList<Integer> list;
	private UpaddressAdapter adapter;
	private ImageView iv_top_right;
	private Intent intent; 
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightRVisible();
		iv_top_right = (ImageView) getTopRightView();
		iv_top_right.setBackgroundResource(R.drawable.address_guanli_btn);
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
		iv_top_right.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				intent = new Intent(context,AddressActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
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
