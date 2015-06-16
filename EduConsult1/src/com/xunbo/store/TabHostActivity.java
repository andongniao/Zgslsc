package com.xunbo.store;

import android.annotation.SuppressLint;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import android.widget.TextView;

@SuppressWarnings({ "unused", "deprecation" })
public abstract class TabHostActivity extends TabActivity {
	protected int activityCloseEnterAnimation;

	protected int activityCloseExitAnimation;

	private static TabHost mTabHost;
	private TabWidget mTabWidget;
	private LayoutInflater mLayoutflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// set theme because we do not want the shadow
		TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});
		int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);      
		activityStyle.recycle();
		activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
		activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
		activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
		activityStyle.recycle();
		setTheme(R.style.Theme_Tabhost);
		setContentView(R.layout.api_tab_host);

		mLayoutflater = getLayoutInflater();

		mTabHost = getTabHost();
		mTabWidget = getTabWidget();
		// mTabWidget.setStripEnabled(false); // need android2.2

		prepare();

		// initTop();
		initTabSpec();
	}

	// private void initTop() {
	// View child = getTop();
	// LinearLayout layout = (LinearLayout) findViewById(R.id.tab_top);
	// layout.addView(child);
	// }

	@SuppressLint("InflateParams")
	private void initTabSpec() {

		int count = getTabItemCount();

		for (int i = 0; i < count; i++) {
			// set text view
			View tabItem = mLayoutflater.inflate(R.layout.api_tab_item, null);

			ImageView tvTabItem = (ImageView) tabItem
					.findViewById(R.id.tab_item_iv);
			setTabItemTextView(tvTabItem, i);
			// set id
			String tabItemId = getTabItemId(i);
			// set tab spec
			TabSpec tabSpec = mTabHost.newTabSpec(tabItemId);
			tabSpec.setIndicator(tabItem);
			tabSpec.setContent(getTabItemIntent(i));

			mTabHost.addTab(tabSpec);
		}

	}

	/** 在初始化界面之前调用 */
	protected void prepare() {
		// do nothing or you override it
	}

	// /** 自定义头部布局 */
	// protected View getTop() {
	// // do nothing or you override it
	// return null;
	// }

	protected int getTabCount() {
		return mTabHost.getTabWidget().getTabCount();
	}

	/** 设置TabItem的图标和标题等 */
	abstract protected void setTabItemTextView(ImageView textView, int position);

	abstract protected String getTabItemId(int position);

	abstract protected Intent getTabItemIntent(int position);

	abstract protected int getTabItemCount();

	public static void setCurrentTab(int index) {
		mTabHost.setCurrentTab(index);
	}

	protected void focusCurrentTab(int index) {
		mTabWidget.focusCurrentTab(index);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
	}
}
