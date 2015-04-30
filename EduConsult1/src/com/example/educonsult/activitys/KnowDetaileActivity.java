package com.example.educonsult.activitys;

import com.example.educonsult.R;
import com.example.educonsult.myviews.MyListview;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class KnowDetaileActivity extends BaseActivity implements OnClickListener{
	private RelativeLayout rl_l,rl_r;
	private TextView tv_title,tv_name_title,tv_fenlei,tv_num,tv_time_title,
	tv_content,tv_name_huida,tv_time_huida,tv_commit,tv_more,tv_question,
	tv_price_l,tv_price_t,tv_price_r;
	private MyListview lv;
	private EditText et;
	private CheckBox cb;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTopLeftTv(R.string.know_title);
		topRightLVisible();
		topRightRVisible();
		topRightTGone();
		setContentXml(R.layout.know_detaile_layout);
		init();
		addlistriner();
	}



	private void addlistriner() {
		// TODO Auto-generated method stub
		
	}



	private void init() {
		tv_title = (TextView) findViewById(R.id.know_detaile_title);
		tv_name_title = (TextView) findViewById(R.id.know_detaile_tv_name_title);
		tv_fenlei = (TextView) findViewById(R.id.know_detaile_tv_fenlei);
		tv_num = (TextView) findViewById(R.id.know_detaile_tv_see_num);
		tv_time_title = (TextView) findViewById(R.id.know_detaile_tv_time_title);
		tv_content = (TextView) findViewById(R.id.know_detaile_tv_content);
		tv_price_l = (TextView) findViewById(R.id.know_detaile_tv_price_l_jinxuan);
		tv_price_t = (TextView) findViewById(R.id.know_detaile_tv_price_t_jinxuan);
		tv_price_r = (TextView) findViewById(R.id.know_detaile_tv_price_r_jinxuan);
		lv = (MyListview) findViewById(R.id.know_detaile_lv);
		cb = (CheckBox) findViewById(R.id.know_detaile_cb);
		et = (EditText) findViewById(R.id.know_detaile_et_content);
		findViewById(R.id.know_detaile_tv_more_jinxuan).setOnClickListener(this);
		findViewById(R.id.know_detaile_tv_question).setOnClickListener(this);
		findViewById(R.id.know_detaile_tv_commit).setOnClickListener(this);
		findViewById(R.id.know_detaile_ll_l_jinxuan).setOnClickListener(this);
		findViewById(R.id.know_detaile_ll_t_jinxuan).setOnClickListener(this);
		findViewById(R.id.know_detaile_ll_r_jinxuan).setOnClickListener(this);
		tv_content.setText("\u3000\u3000\u3000\u3000\u3000\u3000\u3000\u3000\u3000\u3000\u3000\u3000\u3000\u3000\u3000\u3000"+"232.0.212313213131321564654135156466465464646464654465445646446543513");
	}



	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	

}
