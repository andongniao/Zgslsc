package com.example.educonsult;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.TabWidget;
import android.widget.Toast;

import com.example.educonsult.activitys.AboutActivity;
import com.example.educonsult.activitys.HomePagerActivity;
import com.example.educonsult.activitys.MyCenterActivity;
import com.example.educonsult.activitys.SearchHomeActivity;
import com.example.educonsult.activitys.ShopcartActivity;


public class ExampleActivity extends TabHostActivity {
	protected int activityCloseEnterAnimation;

	protected int activityCloseExitAnimation;

	private List<TabItem> mItems;

	@Override
	protected void prepare() {
		TabItem Home = new TabItem(R.drawable.base_home, 
				new Intent(this, HomePagerActivity.class)); 

		TabItem Search = new TabItem(R.drawable.base_search, new Intent(
				this, SearchHomeActivity.class));

		TabItem MyCenter = new TabItem(R.drawable.base_mycenter, new Intent(
				this, MyCenterActivity.class));

		TabItem ShopCart = new TabItem(R.drawable.base_shopcart, new Intent(
				this, ShopcartActivity.class));

		TabItem About = new TabItem(R.drawable.base_about, new Intent(
				this, AboutActivity.class));
		mItems = new ArrayList<TabItem>();
		mItems.add(Home);
		mItems.add(Search);
		mItems.add(MyCenter);
		mItems.add(ShopCart);
		mItems.add(About);

		@SuppressWarnings("deprecation")
		TabWidget tabWidget = getTabWidget();
		//		tabWidget.setDividerDrawable(R.drawable.tab_divider);

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
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
	protected void setTabItemTextView(ImageView textView, int position) {
		textView.setPadding(3, 3, 3, 3);
		textView.setBackgroundResource(mItems.get(position).getBg());

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
}