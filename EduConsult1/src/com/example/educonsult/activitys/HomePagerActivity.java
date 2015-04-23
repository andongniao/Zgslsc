package com.example.educonsult.activitys;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;

import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.beans.ListUserBean;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.fragments.HomeFragment;
import com.example.educonsult.fragments.HomePageFragmentMenu;
import com.example.educonsult.tools.DiskLruCache;
import com.example.educonsult.tools.Util;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class HomePagerActivity extends SlidingFragmentActivity implements
OnClickListener {
	private Context context;
	public static String SlidTag="finish";
	public static Handler handler;
	private Util util;
	private String filename = "test";
	private ListUserBean listUserBean;
	private DiskLruCache mDiskLruCache;
	private String url = "http://pica.nipic.com/2007-12-18/200712189215503_2.jpg";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
		getSupportFragmentManager().beginTransaction()
		.replace(R.id.fragmentlinear, new HomeFragment()).commit();
		context = this;
		util = new Util(context);
		listUserBean = new ListUserBean();
		File file = util.getDiskCacheDir(context, "bitmap");
		if (!file.exists()) {
			file.mkdirs();
		}
		try {
			mDiskLruCache = DiskLruCache.open(file, util.getAppVersion(context), 1, 10 * 1024 * 1024);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(util.isExistDataCache(filename) && util.isReadDataCache(filename)){
			listUserBean = (ListUserBean) util.readObject(filename);
			MyApplication.bean = listUserBean.getList().get(0);
		}else{
			ArrayList<UserBean> l = new ArrayList<UserBean>();
			UserBean b = new UserBean();
			b.setName("0.0");
			b.setBmp(url);
			l.add(b);
			listUserBean.setList(l);
			util.saveiamgetoload(url, mDiskLruCache);
			util.saveObject(listUserBean, filename);
		}
		
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
}
