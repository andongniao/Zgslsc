package com.example.educonsult.activitys;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.umeng.analytics.MobclickAgent;

public class AboutActivity extends BaseActivity{
	private long exitTime = 0;
	private View tv_top_tight;
	private Context context;
	private ImageView iv_top_r;
	private RelativeLayout rl_r;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		goneTopLeft();
		topRightTGone();
		//		topRightRVisible();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_r = (ImageView) getTopRightView();
		iv_top_r.setBackgroundResource(R.drawable.top_xx_bg);
		setTitleTxt(R.string.about_title);
		setContentXml(R.layout.about_layout);
		init();

	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		//		Util.SetRedNum(context, rl_r, 1);

	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
			if((System.currentTimeMillis()-exitTime) > 2000){  
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();                                
				exitTime = System.currentTimeMillis();   
			} else {
				finish();
				System.exit(0);
			}
			return true;   
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("AboutActivity"); 
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("AboutActivity"); 
		MobclickAgent.onResume(this);
	}

}
