package com.example.educonsult.activitys;

import com.example.educonsult.R;
import com.example.educonsult.myviews.BadgeView;

import android.os.Bundle;
import android.view.View;

public class AboutActivity extends BaseActivity{
	private View tv_top_tight;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		goneTopLeft();
		topRightTGone();
		topRightRVisible();
		setTitleTxt(R.string.about_title);
		setContentXml(R.layout.about_layout);
		init();
		
	}

	private void init() {
		tv_top_tight =  getTopRightView();
		BadgeView badge = new BadgeView(this, tv_top_tight);
		badge.setText("1");
		badge.show();
		
	}

}
