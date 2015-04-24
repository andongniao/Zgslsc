package com.example.educonsult.activitys;

import java.util.ArrayList;

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.R;
import com.example.educonsult.adapters.MyOrderHomeAdapter;
import com.example.educonsult.tools.Util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyOrderActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private LinearLayout ll_all,ll_pay,ll_send,ll_shouhuo,ll_comment,ll_isnull;
	private TextView tv_all,tv_show_all,tv_pay,tv_show_pay,tv_send,tv_show_send,
	tv_shouhuo,tv_show_shouhuo,tv_comment,tv_show_comment,tv_isnull;
	private ListView lv;
	private ArrayList<Integer> list;
	private ArrayList<View> view_list;
	private MyOrderHomeAdapter adapter;
	private int index;
	private Myorder myorder;
	private ImageView iv_top_r;
	private RelativeLayout rl_r;
	private Intent intent;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTopLeftTv(R.string.myorder_title);
		topRightRVisible();
		topRightTGone();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_r = (ImageView) getTopRightView();
		iv_top_r.setBackgroundResource(R.drawable.top_home_bg);
		setContentXml(R.layout.myorder_home_layout);
		init();
		addlistener();
	}

	private void addlistener() {
		iv_top_r.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ExampleActivity.setCurrentTab(0);
				finish();
			}
		});
	}

	private void init() {
		context = this;
		myorder = new Myorder() {

			@Override
			public void delte(int index) {
				list.remove(index);
				adapter.SetData(list);
				adapter.notifyDataSetChanged();
			}
		};
		index = getIntent().getIntExtra("index", 0);
		view_list = new ArrayList<View>();
		list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		ll_isnull = (LinearLayout) findViewById(R.id.myorder_home_ll_isnull);
		tv_isnull = (TextView) findViewById(R.id.myorder_home_tv_isnull);
		tv_isnull.setOnClickListener(this);
		lv = (ListView) findViewById(R.id.myorder_home_lv);
		lv.setEmptyView(ll_isnull);
		ll_all = (LinearLayout) findViewById(R.id.myorder_home_ll_all);
		ll_all.setOnClickListener(this);
		ll_pay = (LinearLayout) findViewById(R.id.myorder_home_ll_pay);
		ll_pay.setOnClickListener(this);
		ll_send = (LinearLayout) findViewById(R.id.myorder_home_ll_send);
		ll_send.setOnClickListener(this);
		ll_shouhuo = (LinearLayout) findViewById(R.id.myorder_home_ll_shouhuo);
		ll_shouhuo.setOnClickListener(this);
		ll_comment = (LinearLayout) findViewById(R.id.myorder_home_ll_comment);
		ll_comment.setOnClickListener(this);
		tv_all = (TextView) findViewById(R.id.myorder_home_tv_all);
		tv_pay = (TextView) findViewById(R.id.myorder_home_tv_pay);
		tv_send = (TextView) findViewById(R.id.myorder_home_tv_send);
		tv_shouhuo = (TextView) findViewById(R.id.myorder_home_tv_shouhuo);
		tv_comment = (TextView) findViewById(R.id.myorder_home_tv_comment);

		tv_show_all = (TextView) findViewById(R.id.myorder_home_tv_all_show);
		tv_show_pay = (TextView) findViewById(R.id.myorder_home_tv_pay_show);
		tv_show_send = (TextView) findViewById(R.id.myorder_home_tv_send_show);
		tv_show_shouhuo = (TextView) findViewById(R.id.myorder_home_tv_shouhuo_show);
		tv_show_comment = (TextView) findViewById(R.id.myorder_home_tv_comment_show);
		view_list.add(tv_show_all);
		view_list.add(tv_show_pay);
		view_list.add(tv_show_send);
		view_list.add(tv_show_shouhuo);
		view_list.add(tv_show_comment);
		adapter = new MyOrderHomeAdapter(context, list,myorder);
		lv.setAdapter(adapter);
		change(index);

		if(list!=null && list.size()>0){
			ll_isnull.setVisibility(View.GONE);
			lv.setVisibility(View.VISIBLE);
		}else{
			ll_isnull.setVisibility(View.VISIBLE);
			lv.setVisibility(View.GONE);
		}
		Util.SetRedNum(context, rl_r, 1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myorder_home_ll_all:
			change(0);
			break;
		case R.id.myorder_home_ll_pay:
			change(1);
			break;
		case R.id.myorder_home_ll_send:
			change(2);
			break;
		case R.id.myorder_home_ll_shouhuo:
			change(3);
			break;
		case R.id.myorder_home_ll_comment:
			change(4);
			break;
		case R.id.myorder_home_tv_isnull:
			ExampleActivity.setCurrentTab(0);
			finish();
			break;
		}
	}

	private void change(int index){
		for(int i=0;i<view_list.size();i++){
			if(index==i){
				view_list.get(i).setVisibility(View.VISIBLE);
			}else{
				view_list.get(i).setVisibility(View.INVISIBLE);
			}
		}
	}
	public interface Myorder{
		void delte(int index);
	}

}
