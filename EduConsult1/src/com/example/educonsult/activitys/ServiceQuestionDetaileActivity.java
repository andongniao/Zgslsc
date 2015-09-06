package com.example.educonsult.activitys;

import android.os.Bundle;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.xunbo.store.tools.Util;

public class ServiceQuestionDetaileActivity extends BaseActivity{
	public static boolean isread;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
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
	}




	private void addlistener() {
		// TODO Auto-generated method stub
		
	}
}
