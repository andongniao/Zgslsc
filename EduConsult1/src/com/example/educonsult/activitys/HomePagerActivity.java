package com.example.educonsult.activitys;

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

import com.example.educonsult.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.testin.agent.TestinAgent;
import com.umeng.analytics.MobclickAgent;
import com.xunbo.store.fragments.HomePageFragmentMenu;

public class HomePagerActivity extends SlidingFragmentActivity implements
OnClickListener {
	private long exitTime = 0;
	public static String SlidTag="finish";
	public static Handler handler;



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
		// 设置侧滑效果
		setBehindContentView(R.layout.homepage_menu);
		setMenuFragment(); 
		setSilMenu();

		setContentView(R.layout.activity_home_pager);

		InItObj();

	}

	public void InItObj() {
//		getSupportFragmentManager().beginTransaction()
//		.replace(R.id.fragmentlinear, new HomeFragment()).commit();
//		getSupportFragmentManager().beginTransaction()
//		.replace(R.id.fragmentlinear, new HomeLayoutFragment()).commit();
	}


	/**
	 * 加载划出页面
	 */
	public void setMenuFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.menu_layout, new HomePageFragmentMenu());
		ft.commit();
	}

	/**
	 * 设置划出效果
	 */
	@SuppressWarnings("deprecation")
	public void setSilMenu() {
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT);// 设置从右侧划出
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
		//			toggle();// 弹出或收回右侧个人中心
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
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
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
		MobclickAgent.onPageEnd("home"); 
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("home"); 
		MobclickAgent.onResume(this);
	}
	
}
