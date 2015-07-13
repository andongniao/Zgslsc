package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;

import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.xunbo.store.adapters.ZhanhuiDetaileLvAdapter;
import com.xunbo.store.adapters.ZhanhuiHomeAdapter;
import com.xunbo.store.myviews.MyGridView;
import com.xunbo.store.myviews.MyListview;

public class NewDetaileActivity extends BaseActivity{
	private  MyListview lv;
	private Context context;
	private ArrayList<Integer> list;
	private ZhanhuiDetaileLvAdapter adapter;
	private MyGridView gv_tuijian;
	private ZhanhuiHomeAdapter adapter_gv;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightRVisible();
		setTopLeftTv(R.string.new_home_title);
		setContentXml(R.layout.new_detaile_layout);
		init();
	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		list = new ArrayList<Integer>();
		for(int i = 0;i<5;i++){
			list.add(i);
		}
		lv = (MyListview) findViewById(R.id.new_detaile_lv);
		adapter = new ZhanhuiDetaileLvAdapter(context, list);
		lv.setAdapter(adapter);
		/*gv_tuijian = (MyGridView) findViewById(R.id.new_da);
		adapter_gv = new ZhanhuiHomeAdapter(context, list);
		gv_tuijian.setAdapter(adapter_gv);*/
	}

}
