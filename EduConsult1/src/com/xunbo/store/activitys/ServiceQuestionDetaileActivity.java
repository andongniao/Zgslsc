package com.xunbo.store.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.testin.agent.TestinAgent;
import com.xunbo.store.R;
import com.xunbo.store.tools.Util;

public class ServiceQuestionDetaileActivity extends BaseActivity{
	private Context context;
	private Intent intent;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	public static boolean isread;
	private String title;
	private TextView tv_title,tv_content;
	
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
		String title = getIntent().getStringExtra("title");
		if(Util.IsNull(title)){
			setTopLeftTv(title);
		}
		setContentXml(R.layout.service_question_detaile);
		init();
		addlistener();
	}




	private void init() {
		TestinAgent.init(this);
		context = this;
		tv_title = (TextView) findViewById(R.id.service_question_detaile_tv_title);
		tv_content = (TextView) findViewById(R.id.service_question_detaile_tv_content);
	}




	private void addlistener() {
		// TODO Auto-generated method stub
		
	}
}
