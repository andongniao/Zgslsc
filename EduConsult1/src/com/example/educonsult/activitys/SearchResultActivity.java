package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.educonsult.R;
import com.example.educonsult.adapters.SearchResultAdapter;

public class SearchResultActivity extends Activity implements OnClickListener{
	protected int activityCloseEnterAnimation;
	protected int activityCloseExitAnimation;
	private ImageView iv_back,iv_num,iv_price,iv_renqi;
	private EditText et;
	private LinearLayout ll_zonghe,ll_xiaoliang,ll_price,ll_renqi;
	private GridView gv;
	private Context context;
	private ArrayList<Integer> list;
	private SearchResultAdapter adapter;
	private ArrayList<View> list_view;
	private boolean num,price,renqi;

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
		setContentView(R.layout.search_result_layout);
		init();
		addlisteners();
	}

	private void addlisteners() {

		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(context,ProductDetaileActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}

	private void init() {
		context = this;
		list = new ArrayList<Integer>();
		for(int i = 0;i<10;i++){
			list.add(i);
		}
		list_view = new ArrayList<View>();
		iv_back = (ImageView) findViewById(R.id.search_result_iv_back);
		iv_back.setOnClickListener(this);
		et = (EditText) findViewById(R.id.search_result_et);
		et.setOnClickListener(this);
		ll_zonghe = (LinearLayout) findViewById(R.id.search_result_ll_zonghe);
		ll_zonghe.setOnClickListener(this);
		ll_xiaoliang = (LinearLayout) findViewById(R.id.search_result_ll_xiaoliang);
		ll_xiaoliang.setOnClickListener(this);
		ll_price = (LinearLayout) findViewById(R.id.search_result_ll_price);
		ll_price.setOnClickListener(this);
		ll_renqi = (LinearLayout) findViewById(R.id.search_result_ll_renqi);
		ll_renqi.setOnClickListener(this);
		iv_num = (ImageView) findViewById(R.id.search_result_iv_xiaoliang);
		iv_price = (ImageView) findViewById(R.id.search_result_iv_price);
		iv_renqi = (ImageView) findViewById(R.id.search_result_iv_renqi);
		list_view.add(iv_num);
		list_view.add(iv_price);
		list_view.add(iv_renqi);

		gv = (GridView) findViewById(R.id.search_result_gv);
		adapter = new SearchResultAdapter(context, list);
		gv.setAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_result_iv_back:
			finish();
			break;
		case R.id.search_result_et:
			Intent intent = new Intent(this,SearchHomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("type", 1);
			intent.putExtra("t", 1);
			startActivity(intent);
			break;
		case R.id.search_result_ll_zonghe:

			break;
		case R.id.search_result_ll_xiaoliang:
			Change(0);
			break;
		case R.id.search_result_ll_price:
			Change(1);
			break;
		case R.id.search_result_ll_renqi:
			Change(2);
			break;

		}
	}

	private void Change(int i){
		switch (i) {
		case 0:
			if(num){
				num = false;
				list_view.get(0).setBackgroundResource(R.drawable.jiantou_down);
			}else{
				num = true;
				list_view.get(0).setBackgroundResource(R.drawable.jiantou_up);
			}
			break;
		case 1:
			if(price){
				price = false;
				list_view.get(1).setBackgroundResource(R.drawable.jiantou_down);
			}else{
				price = true;
				list_view.get(1).setBackgroundResource(R.drawable.jiantou_up);
			}
			break;
		case 2:
			if(renqi){
				renqi = false;
				list_view.get(2).setBackgroundResource(R.drawable.jiantou_down);
			}else{
				renqi = true;
				list_view.get(2).setBackgroundResource(R.drawable.jiantou_up);
			}
			break;
		}
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
	}

}
