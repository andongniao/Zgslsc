package com.xunbo.store.activitys;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.testin.agent.TestinAgent;
import com.xunbo.store.R;
import com.xunbo.store.fragments.HomeFragment;
import com.xunbo.store.fragments.HomePageFragmentMenu;
import com.xunbo.store.tools.Util;

public class HomePagerActivity extends SlidingFragmentActivity implements
OnClickListener {
	private long exitTime = 0;
	private Context context;
	public static String SlidTag="finish";
	public static Handler handler;
	private Util util;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TestinAgent.init(this);
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(SlidTag.equals(msg.obj)){
					toggle();
				}
			}
		};
		// ���ò໬Ч��
		setBehindContentView(R.layout.homepage_menu);
		setMenuFragment(); 
		setSilMenu();

		setContentView(R.layout.activity_home_pager);

		InItObj();

	}

	public void InItObj() {
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.fragmentlinear, new HomeFragment()).commit();
		context = this;
		util = new Util(context);
	}


	/**
	 * ���ػ���ҳ��
	 */
	public void setMenuFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.menu_layout, new HomePageFragmentMenu());
		ft.commit();
	}

	/**
	 * ���û���Ч��
	 */
	public void setSilMenu() {
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);// ���ô��Ҳ໮��
		//		sm.setSecondaryMenu(R.layout.slidemenu_view);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.5f);
		sm.setGravity(Gravity.CENTER);
		sm.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.transparent));
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		sm.setBehindScrollScale(0);

	}


	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

		switch (arg0.getId()) {
		//		case R.id.user_btn:
		//			toggle();// �������ջ��Ҳ��������
		//			Intent intent = new Intent(this,SearchHomeActivity.class);
		//			startActivity(intent);
		//			break;

		default:
			break;
		}

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
}