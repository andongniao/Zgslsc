package com.example.educonsult.activitys;

import com.example.educonsult.R;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class QianBaoActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_chaxun,ll_tixian;
	private TextView tv;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightLVisible();
		topRightRVisible();
		topRightTGone();
		setTopLeftTv(R.string.qignbao_title);
		setContentXml(R.layout.qianbao);
		init();
	}

	private void init() {
		ll_chaxun = (LinearLayout) findViewById(R.id.qianbao_ll_chaxun);
		ll_chaxun.setOnClickListener(this);
		ll_tixian = (LinearLayout) findViewById(R.id.qianbao_ll_tixian);
		ll_tixian.setOnClickListener(this);
		tv = (TextView) findViewById(R.id.qianbao_tv_zonge);
		tv.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.qianbao_ll_chaxun:
			
			break;
		case R.id.qianbao_ll_tixian:
			
			break;

		}
	}

}
