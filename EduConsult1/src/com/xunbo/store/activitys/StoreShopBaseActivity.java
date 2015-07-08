package com.xunbo.store.activitys;

import java.util.ArrayList;
import java.util.zip.Inflater;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.activitys.OrderActivity.RefeshData;
import com.xunbo.store.adapters.StoreShopAdapter;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.myviews.MyGridView;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class StoreShopBaseActivity extends BaseActivity implements OnClickListener{
	private ImageView iv_hean,iv_shoucang,iv_home,iv_all,iv_new;
	private TextView title;
	private View v_home,v_all,v_new;
	private MyGridView gv_home,gv_all,gv_new;
	private Context context;
	private LinearLayout ll_add;
	private int showtype,page;
	private StoreShopAdapter adapter;
	private ArrayList<ProductBean> list_home,list_all,list_new;
	private TextView tv_seeall;
	private ThreadWithProgressDialog myPDT;
	private ScrollView sc;



	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		setTitleTxt(R.string.store_title);
		setContentXml(R.layout.storeshop_base_layout);
		init();
		addlistener();
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}

	private void addlistener() {
		// TODO Auto-generated method stub

	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		showtype = 1;
		page = 1;
		list_home = new ArrayList<ProductBean>();
		list_all = new ArrayList<ProductBean>();
		list_new = new ArrayList<ProductBean>();
		myPDT =new  ThreadWithProgressDialog();
		iv_hean = (ImageView) findViewById(R.id.storeshop_iv_head);
		iv_hean.setOnClickListener(this);
		iv_shoucang = (ImageView) findViewById(R.id.storeshop_iv_isshow);
		iv_shoucang.setOnClickListener(this);
		iv_home = (ImageView) findViewById(R.id.storeshop_iv_home);
		iv_home.setOnClickListener(this);
		iv_all = (ImageView) findViewById(R.id.storeshop_iv_all);
		iv_all.setOnClickListener(this);
		iv_new = (ImageView) findViewById(R.id.storeshop_iv_new);
		iv_new.setOnClickListener(this);
		sc = (ScrollView) findViewById(R.id.storeshop_sc);

		ll_add = (LinearLayout) findViewById(R.id.store_base_ll_addview);


		v_home = LayoutInflater.from(context).inflate(R.layout.storeshop_home_layout, null);
		v_all = LayoutInflater.from(context).inflate(R.layout.storeshop_all_layout, null);
		v_new = LayoutInflater.from(context).inflate(R.layout.storeshop_new_layout, null);

		ll_add.addView(v_home);
		ll_add.addView(v_all);
		ll_add.addView(v_new);

		gv_home = (MyGridView) v_home.findViewById(R.id.storeshop_home_gv);
		gv_all = (MyGridView) v_all.findViewById(R.id.storeshop_all_gv);
		gv_new = (MyGridView) v_new.findViewById(R.id.storeshop_new_gv);
		gv_home.setFocusable(false);
		gv_all.setFocusable(false);
		gv_new.setFocusable(false);
		adapter = new StoreShopAdapter(context, list_home);
		gv_home.setAdapter(adapter);
		gv_all.setAdapter(adapter);
		gv_new.setAdapter(adapter);
		

		v_home.findViewById(R.id.storeshop_home_tv_all).setOnClickListener(this);





		show(showtype);

	}

	private void show(int showtype) {
		switch (showtype) {
		case 1:
			iv_home.setBackgroundResource(R.drawable.storeshop_home_bg_r);
			iv_all.setBackgroundResource(R.drawable.storeshop_all_bg_b);
			iv_new.setBackgroundResource(R.drawable.storeshop_new_bg_b);
			v_home.setVisibility(View.VISIBLE);
			v_all.setVisibility(View.GONE);
			v_new.setVisibility(View.GONE);
			break;
		case 2:
			iv_home.setBackgroundResource(R.drawable.storeshop_home_bg_b);
			iv_all.setBackgroundResource(R.drawable.storeshop_all_bg_r);
			iv_new.setBackgroundResource(R.drawable.storeshop_new_bg_b);
			v_home.setVisibility(View.GONE);
			v_all.setVisibility(View.VISIBLE);
			v_new.setVisibility(View.GONE);
			break;
		case 3:
			iv_home.setBackgroundResource(R.drawable.storeshop_home_bg_b);
			iv_all.setBackgroundResource(R.drawable.storeshop_all_bg_b);
			iv_new.setBackgroundResource(R.drawable.storeshop_new_bg_r);
			v_home.setVisibility(View.GONE);
			v_all.setVisibility(View.GONE);
			v_new.setVisibility(View.VISIBLE);
			break;



		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.storeshop_home_tv_all:
			showtype = 2;
			show(showtype);
			sc.scrollTo(0, 10);
			break;
		case R.id.storeshop_iv_home:
			showtype = 1;
			show(showtype);

			break;
		case R.id.storeshop_iv_all:
			showtype = 2;
			show(showtype);

			break;
		case R.id.storeshop_iv_new:
			showtype = 3;
			show(showtype);
			
			break;

		}

	}

	public class RefeshData implements ThreadWithProgressDialogTask {
		public RefeshData() {
			
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			return false; 
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后

			return true;
		}



		@Override
		public boolean TaskMain() {

			return true;
		}
	}


}
