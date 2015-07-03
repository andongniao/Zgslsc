package com.xunbo.store;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xunbo.store.activitys.HomePagerActivity;
import com.xunbo.store.activitys.MyCenterActivity;
import com.xunbo.store.activitys.MyZjActivity;
import com.xunbo.store.activitys.ShopcartActivity;


public class ExampleActivity extends TabHostActivity {
	protected int activityCloseEnterAnimation;

	protected int activityCloseExitAnimation;

	private List<TabItem> mItems;

	@Override
	protected void prepare() {
		TabItem Home = new TabItem(R.drawable.base_home, 
				new Intent(this, HomePagerActivity.class),"首  页   "); 

//		TabItem Search = new TabItem(R.drawable.base_search, new Intent(
//				this, SearchHomeActivity.class),"home");

		TabItem About = new TabItem(R.drawable.base_about, new Intent(
				this, MyZjActivity.class),"产  品  ");

		TabItem ShopCart = new TabItem(R.drawable.base_shopcart, new Intent(
				this, ShopcartActivity.class),"购物车  ");
		

		TabItem MyCenter = new TabItem(R.drawable.base_mycenter, new Intent(
				this, MyCenterActivity.class),"个人中心  ");
		mItems = new ArrayList<TabItem>();
		mItems.add(Home);
//		mItems.add(Search);
		mItems.add(About);
		mItems.add(ShopCart);
		mItems.add(MyCenter);

		@SuppressWarnings("deprecation")
		TabWidget tabWidget = getTabWidget();
		//		tabWidget.setDividerDrawable(R.drawable.tab_divider);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		MobclickAgent.setDebugMode(true);
//		MobclickAgent.openActivityDurationTrack(false);
		MobclickAgent.updateOnlineConfig(this);
		
		
		TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});
		int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);      
		activityStyle.recycle();
		activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
		activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
		activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
		activityStyle.recycle();
		setCurrentTab(0);
	}

	@Override
	protected void setTabItemTextView(ImageView textView,TextView tvtitle, int position) {
		textView.setPadding(3, 3, 3, 3);
		textView.setBackgroundResource(mItems.get(position).getBg());
		tvtitle.setTextColor(getResources().getColor(R.color.white));
		tvtitle.setText(mItems.get(position).getTitle());
//		tvtop.setText(mItems.get(position).getTitle());
//		tvtop.setHeight(10);
//		tvtitle.setVisibility(View.GONE);

	}

	@Override
	protected String getTabItemId(int position) {
		return mItems.get(position).getIntent().toString(); 
	}

	@Override
	protected Intent getTabItemIntent(int position) {

		return mItems.get(position).getIntent();
	}

	@Override
	protected int getTabItemCount() {
		return mItems.size();
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
	}
	
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart( "入口" );
		MobclickAgent.onResume(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd( "入口" );
		MobclickAgent.onPause(this);
	}
	
}