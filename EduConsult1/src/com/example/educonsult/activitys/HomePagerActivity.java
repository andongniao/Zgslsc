package com.example.educonsult.activitys;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.beans.ListUserBean;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.fragments.HomeFragment;
import com.example.educonsult.fragments.HomePageFragmentMenu;
import com.example.educonsult.tools.DiskLruCache;
import com.example.educonsult.tools.FileUtils;
import com.example.educonsult.tools.ImageUtils;
import com.example.educonsult.tools.StringUtils;
import com.example.educonsult.tools.Util;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class HomePagerActivity extends SlidingFragmentActivity implements
OnClickListener {
	private long exitTime = 0;
	private Context context;
	public static String SlidTag="finish";
	public static Handler handler;
	private Util util;
	private String filename = "test";
	private ListUserBean listUserBean;
	private DiskLruCache mDiskLruCache;
	private String url1 = "http://pica.nipic.com/2007-12-18/200712189215503_2.jpg";
	private String url2 = "http://tupian.enterdesk.com/2012/0621/gha/10/www.enterdesk.comq3.jpg.680.510.jpg";



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
			final ArrayList<UserBean> l = new ArrayList<UserBean>();
			UserBean b1 = new UserBean();
			b1.setName("0.0");
			b1.setBmp(url1);
			UserBean b2 = new UserBean();
			b2.setName("0.0");
			b2.setBmp(url2);
			l.add(b1);
			l.add(b2);
			listUserBean.setList(l);
			util.saveObject(listUserBean, filename);
//			final String imgURL = url1;
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
}
