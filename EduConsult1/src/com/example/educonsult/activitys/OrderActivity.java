package com.example.educonsult.activitys;

import java.util.ArrayList;

import com.example.educonsult.R;
import com.example.educonsult.adapters.OrderHomeAdapter;
import com.example.educonsult.myviews.MyListview;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class OrderActivity extends BaseActivity implements OnClickListener{
	private ScrollView scrollView;
	private Context context;
	private LinearLayout ll_address;
	private MyListview lv;
	private TextView tv_shouhuoren,tv_shoujihao,tv_address,tv_zongjia,tv_ok;
	private ArrayList<Integer>list;
	private OrderHomeAdapter adapter;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		setTopLeftTv(R.string.order_title);
		setContentXml(R.layout.order_home_layout);
		init();
	}

	private void init() {
		context = this;
		list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		scrollView = (ScrollView) findViewById(R.id.order_home_sc);
		scrollView.scrollTo(0, 10);
		ll_address = (LinearLayout) findViewById(R.id.order_home_ll_address);
		ll_address.setOnClickListener(this);
		tv_shouhuoren = (TextView) findViewById(R.id.order_tv_shouhuoren);
		tv_shoujihao= (TextView) findViewById(R.id.order_tv_shoujihao);
		tv_address = (TextView) findViewById(R.id.order_tv_dizhi);
		tv_zongjia = (TextView) findViewById(R.id.order_tv_zongjia);
		tv_ok = (TextView) findViewById(R.id.order_tv_ok);
		tv_ok.setOnClickListener(this);
		lv = (MyListview) findViewById(R.id.order_home_lv);
		lv.setFocusable(false);
		adapter = new OrderHomeAdapter(context, list);
		lv.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_home_ll_address:
			//TODO 
			Intent id = new Intent(this,UpAddressActivity.class);
			id.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(id);
			break;
		case R.id.order_tv_ok:

			break;

		}

	}

}
