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
import android.widget.RelativeLayout;

import com.example.educonsult.R;
import com.example.educonsult.adapters.CouponsAdapter;
import com.example.educonsult.myviews.MyListview;
import com.example.educonsult.tools.Util;

public class CouponsActivity extends BaseActivity {
	private MyListview list_coupons;
	
	private Context context;
	private ArrayList<String> list;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private Intent intent;
	private CouponsAdapter couponsAdapter;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		//topRightLVisible();
		topRightRVisible();
		topRightTGone();
		//rl_l = (RelativeLayout) getTopLightRl();
		rl_r = (RelativeLayout) getTopRightRl();
		/*iv_top_l = (ImageView) getTopLightView();
		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);*/
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_xx_bg);
		setTopLeftTv(R.string.coupons_title);
		setContentXml(R.layout.coupons);
		init();
		addlistener();
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
		
		list_coupons=(MyListview)findViewById(R.id.coupons_list);
		couponsAdapter=new CouponsAdapter(this);
		list_coupons.setAdapter(couponsAdapter);
		list_coupons.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Util.ShowToast(context, arg2+"");
			}
		});
		
		
		
		
	}
	

}
