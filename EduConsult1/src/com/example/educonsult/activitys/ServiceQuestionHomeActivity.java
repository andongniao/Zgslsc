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
import android.widget.RelativeLayout;

import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.xunbo.store.adapters.ServiceQuestionAdapter;
import com.xunbo.store.tools.Util;

public class ServiceQuestionHomeActivity extends BaseActivity{
	private Context context;
	private Intent intent;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	public static boolean isread;
	private ListView lv;
	private ServiceQuestionAdapter adapter;
	private ArrayList<String> list;
	private String title;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		topRightLVisible();
//		topRightRVisible();
		topRightTGone();
//		rl_l = (RelativeLayout) getTopLightRl();
//		rl_r = (RelativeLayout) getTopRightRl();
//		iv_top_l = (ImageView) getTopLightView();
//		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
//		iv_top_t = (ImageView) getTopRightView();
//		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		title = getIntent().getStringExtra("title");
		if(Util.IsNull(title)){
			setTopLeftTv(title);
		}
		setContentXml(R.layout.service_question_layout);
		init();
		addlistener();
		
	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		lv = (ListView) findViewById(R.id.service_question_home_lv);
		/************************/
		list = new ArrayList<String>();
		list.add(""+1);
		list.add(""+2);
		list.add(""+3);
		list.add(""+4);
		adapter = new ServiceQuestionAdapter(context, list);
		lv.setAdapter(adapter);
		
	}

	private void addlistener() {
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				intent = new Intent(context,ServiceQuestionDetaileActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("title", title);
				startActivity(intent);
			}
		});
	}

}
