package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.umeng.analytics.MobclickAgent;
import com.xunbo.store.adapters.CouponsAdapter;

public class CouponsActivity extends BaseActivity {
	private ListView list_coupons;
	
	private Context context;
	private ArrayList<String> list;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private Intent intent;
	private CouponsAdapter couponsAdapter;
	private LinearLayout ll_isno;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		//topRightLVisible();
		//topRightRVisible();
		topRightTGone();
		//rl_l = (RelativeLayout) getTopLightRl();
	//	rl_r = (RelativeLayout) getTopRightRl();
		/*iv_top_l = (ImageView) getTopLightView();
		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);*/
		//iv_top_t = (ImageView) getTopRightView();
		//iv_top_t.setBackgroundResource(R.drawable.top_xx_bg);
		setTitleTxt(R.string.coupons_title);
		setContentXml(R.layout.coupons);
		init();
		//addlistener();
	}

	private void addlistener() {
		rl_r.setOnClickListener(new OnClickListener() {

			@Override	
			public void onClick(View v) {
				intent = new Intent(context,XinjianActivity.class);
				intent.putExtra("flag", "qianbao");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
	}
	void init(){
		TestinAgent.init(this);
		ll_isno=(LinearLayout)findViewById(R.id.coupons_ll_isnull);
		list_coupons=(ListView)findViewById(R.id.coupons_list);
		couponsAdapter=new CouponsAdapter(this);
		list_coupons.setAdapter(couponsAdapter);
		list=new ArrayList<String>();
		list_coupons.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
			}
		});
		if(list!=null){
			if(list.size()>0){
				ll_isno.setVisibility(View.GONE);
				list_coupons.setVisibility(View.VISIBLE);
			}else{
				ll_isno.setVisibility(View.VISIBLE);
				list_coupons.setVisibility(View.GONE);
			}
		}
		
		
		
	}
	

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("CouponsActivity"); 
		MobclickAgent.onPause(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("CouponsActivity"); 
		MobclickAgent.onResume(this);
	}

}
