package com.example.educonsult.activitys;


import com.example.educonsult.R;

import android.os.Bundle;

public class ChatActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		setContentXml(R.layout.chat_layout);
	}

}
