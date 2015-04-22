package com.example.educonsult.activitys;

import com.example.educonsult.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

public class MyInfoActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_head,ll_friend,ll_youhuiquan;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightLVisible();
		topRightRVisible();
		topRightTGone();
		setTopLeftTv(R.string.myinfo_title);
		setContentXml(R.layout.myinfo);
		init();
	}

	private void init() {
		ll_head = (LinearLayout) findViewById(R.id.myinfo_ll_head);
		ll_head.setOnClickListener(this);
		ll_friend = (LinearLayout) findViewById(R.id.myinfo_ll_friend);
		ll_friend.setOnClickListener(this);
		ll_youhuiquan = (LinearLayout) findViewById(R.id.myinfo_ll_youhuiquan);
		ll_youhuiquan.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myinfo_ll_head:

			break;
		case R.id.myinfo_ll_friend:

			break;
		case R.id.myinfo_ll_youhuiquan:

			break;

		}
	}

}
