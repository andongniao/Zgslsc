package com.example.educonsult.activitys;

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.R;
import com.example.educonsult.tools.Util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyInfoActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_head,ll_friend,ll_mycard;
	private Context context;
	private Intent intent;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	public static boolean isread;
	private TextView mycardNum;
	private int cardNum=0;


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightLVisible();
		topRightRVisible();
		topRightTGone();
		rl_l = (RelativeLayout) getTopLightRl();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_l = (ImageView) getTopLightView();
		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		setTopLeftTv(R.string.myinfo_title);
		setContentXml(R.layout.myinfo);
		init();
		addlistener();
	}

	private void addlistener() {
		rl_l.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(context,XinjianActivity.class);
				intent.putExtra("flag", "myinfo");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		rl_r.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ExampleActivity.setCurrentTab(0);
				finish();
			}
		});
	}

	private void init() {
		context = this;
		ll_head = (LinearLayout) findViewById(R.id.myinfo_ll_head);
		ll_head.setOnClickListener(this);
		ll_friend = (LinearLayout) findViewById(R.id.myinfo_ll_friend);
		ll_friend.setOnClickListener(this);
		ll_mycard = (LinearLayout) findViewById(R.id.myinfo_ll_mycard);
		ll_mycard.setOnClickListener(this);
		mycardNum=(TextView)findViewById(R.id.myinfo_ll_mycard_num);
		if(cardNum==0){
			mycardNum.setText("Œ¥∞Û∂®");
			mycardNum.setTextColor(getResources().getColor(R.color.red));
		}else{
			mycardNum.setText(cardNum+"’≈");
		}
		Util.SetRedNum(context, rl_l, 1);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myinfo_ll_head:

			break;
		case R.id.myinfo_ll_mycard:
			intent = new Intent(context,BDCardActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.myinfo_ll_friend:
			intent = new Intent(context,MyBusinessperntActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(isread){
			Util.SetRedGone(context, rl_l);
			isread = false;
		}
	}

}
