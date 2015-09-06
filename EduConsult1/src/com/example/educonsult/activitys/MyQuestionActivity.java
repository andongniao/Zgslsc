package com.example.educonsult.activitys;

import java.util.ArrayList;


import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.xunbo.store.adapters.KnowMyQuestionAdapter;
import com.xunbo.store.tools.Util;

public class MyQuestionActivity extends BaseActivity{
	private Context context;
	private ListView lv;
	private KnowMyQuestionAdapter adapter;
	private ArrayList<String>list;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTopLeftTv(R.string.tiwen_title);
		topRightLVisible();
		topRightRVisible();
		topRightTGone();
		setContentXml(R.layout.know_my_question_layout);
		init();
		addlistener();
	}

	private void addlistener() {
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Util.ShowToast(context, "0.0");
			}
		});
	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		list = new ArrayList<String>();
		list.add("1");
		list.add("11");
		list.add("111");
		list.add("1111");
		lv = (ListView) findViewById(R.id.know_my_question_lv);
		adapter = new KnowMyQuestionAdapter(context, list);
		lv.setAdapter(adapter);
	}


}
