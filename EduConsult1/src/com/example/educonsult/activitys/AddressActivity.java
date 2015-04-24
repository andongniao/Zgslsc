package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.R;
import com.example.educonsult.adapters.AddressHomeAdapter;
import com.example.educonsult.myviews.BadgeView;
import com.example.educonsult.tools.Util;

public class AddressActivity extends BaseActivity implements OnClickListener{
	private ListView lv;
	private LinearLayout ll_show;
	private Button btn_add;
	private ImageButton ibtn_add;
	private AddressHomeAdapter adapter;
	private Context context;
	private ArrayList<Object>list;
	private Intent intent;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	public static boolean isread;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		topRightLVisible();
		topRightRVisible();
		rl_l = (RelativeLayout) getTopLightRl();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_l = (ImageView) getTopLightView();
		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		setTopLeftTv(R.string.address_title);
		setContentXml(R.layout.address_home);
		init();
		addlistener();
	}
	private void addlistener() {
		rl_l.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(context,XinjianActivity.class);
				intent.putExtra("flag", "address");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		rl_r.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ExampleActivity.setCurrentTab(0);
				finish();
			}
		});
	}
	private void init() {
		context = this;
		lv = (ListView) findViewById(R.id.address_home_lv);
		lv.setEmptyView(ll_show);
		ll_show = (LinearLayout) findViewById(R.id.address_home_ll_show);
		ll_show.setVisibility(View.GONE);
		btn_add = (Button) findViewById(R.id.address_home_btn_add_address);
		btn_add.setOnClickListener(this);
		ibtn_add = (ImageButton) findViewById(R.id.address_home_ibtn_add_address);
		ibtn_add.setOnClickListener(this);
		list = new ArrayList<Object>();
		list.add(1);
		list.add(2);
		adapter = new AddressHomeAdapter(context, list);
		lv.setAdapter(adapter);
		if(list!=null){
			if(list.size()>0){
				btn_add.setVisibility(View.VISIBLE);
				lv.setVisibility(View.VISIBLE);
				ll_show.setVisibility(View.GONE);
			}else{
				btn_add.setVisibility(View.GONE);
				lv.setVisibility(View.GONE);
				ll_show.setVisibility(View.VISIBLE);
			}
		}
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				intent = new Intent(context,AddressGLActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		Util.SetRedNum(context, rl_l, 1);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.address_home_btn_add_address:
			intent = new Intent(context,AddressGLActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.address_home_ibtn_add_address:
			intent = new Intent(context,AddressGLActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;

		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		if(isread){
			Util.SetRedGone(context, rl_l);
			isread = false;
		}
	}

}
