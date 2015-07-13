package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.xunbo.store.adapters.MoneyCarListAdapter;

public class MoneyCarListActivity extends BaseActivity{
	private ListView lv;
	private Context context;
	private ArrayList<Integer> list;
	private MoneyCarListAdapter adapter;
	private ImageView iv_top_right;
	private Intent intent; 
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		topRightTGone();
		setTopLeftTv(R.string.moneycar_list_title);
		setContentXml(R.layout.moneycar_list);
		init();
		addlistener();
	}


	private void init() {
		TestinAgent.init(this);
		context = this;
		list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		lv = (ListView) findViewById(R.id.moneycar_list_list);
		adapter = new MoneyCarListAdapter(context, list, 0);
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
