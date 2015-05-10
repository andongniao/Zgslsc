package com.example.educonsult.activitys;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.ExampleActivity;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.beans.MoneyDetaileBean;
import com.example.educonsult.net.Send;
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
import android.widget.Toast;

public class QianBaoActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_chaxun,ll_tixian;
	private TextView tv;
	private Context context;
	private Intent intent;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	public static boolean isread;
	private UserBean bean;
	private MoneyDetaileBean moneybean;

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
		setTopLeftTv(R.string.qignbao_title);
		setContentXml(R.layout.qianbao);
		init();
		addlistener();
	}

	private void addlistener() {
		rl_l.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(context,XinjianActivity.class);
				intent.putExtra("flag", "qianbao");
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
		bean = MyApplication.mp.getUser();
		ll_chaxun = (LinearLayout) findViewById(R.id.qianbao_ll_chaxun);
		ll_chaxun.setOnClickListener(this);
		ll_tixian = (LinearLayout) findViewById(R.id.qianbao_ll_tixian);
		ll_tixian.setOnClickListener(this);
		tv = (TextView) findViewById(R.id.qianbao_tv_zonge);
		tv.setOnClickListener(this);
		Util.SetRedNum(context, rl_l, 1);
	}
	

	@Override
	public void onClick(View v) {
		//Intent intent;
		switch (v.getId()) {
		case R.id.qianbao_ll_chaxun:
			intent = new Intent(this,MoneyQueryActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.qianbao_ll_tixian:
			intent = new Intent(this,MoneyWithdrawalActivity.class);
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
