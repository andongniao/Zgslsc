package com.example.educonsult.activitys;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.educonsult.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.testin.agent.TestinAgent;
import com.xunbo.store.fragments.KnowFragment;
import com.xunbo.store.fragments.KnowSlidMenu;

public class KnowHomeActivity extends SlidingFragmentActivity implements OnClickListener {
	protected int activityCloseEnterAnimation;
	protected int activityCloseExitAnimation;
	public static String SlidTag="finish";
	public static Handler handler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TestinAgent.init(this);
		TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});
		int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);      
		activityStyle.recycle();
		activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
		activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
		activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
		activityStyle.recycle();
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(SlidTag.equals(msg.obj)){
					toggle();
				}
				if(msg.what==1){
					finish();
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
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.fragmentlinear, new KnowFragment()).commit();
	}


	/**
	 * 加载划出页面
	 */
	public void setMenuFragment() {
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction ft = fm.beginTransaction();
		ft.replace(R.id.menu_layout, new KnowSlidMenu());
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
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
	}

}
