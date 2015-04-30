package com.example.educonsult.activitys;

import android.os.Bundle;

import com.example.educonsult.R;

public class XinJianInfoActivity extends BaseActivity{
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//topRightRVisible();
		topRightTGone();
		setTopLeftTv(R.string.xinjianinfo_title);
		setContentXml(R.layout.xinjianinfo);
	}

}
