package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.adapters.ProductPingjiaAdapter;
import com.example.educonsult.beans.ProductBean;
import com.example.educonsult.myviews.MyListview;
import com.example.educonsult.tools.Util;
import com.testin.agent.TestinAgent;

public class KnowDetaileActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private RelativeLayout rl_l,rl_r;
	private TextView tv_title,tv_name_title,tv_fenlei,tv_num,tv_time_title,
	tv_content,tv_name_huida,tv_time_huida,tv_commit,tv_more,tv_question,
	tv_price_l,tv_price_t,tv_price_r;
	private MyListview lv;
	private EditText et;
	private CheckBox cb;
	private Intent intent;
	private boolean type;
	private ArrayList<ProductBean> list;
	private ProductPingjiaAdapter adapter;


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
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				type = isChecked;
			}
		});
		
	}



	private void init() {
		TestinAgent.init(this);
		context = this;
		type = false;
		list = new ArrayList<ProductBean>();
		ProductBean p1 = new ProductBean();
		ProductBean p2 = new ProductBean();
		ProductBean p3 = new ProductBean();
		ProductBean p4 = new ProductBean();
		list.add(p1);
		list.add(p2);
		list.add(p3);
		list.add(p4);
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
	//	adapter = new ProductPingjiaAdapter(context, list);
	//	lv.setAdapter(adapter);
		cb = (CheckBox) findViewById(R.id.know_detaile_cb);
		et = (EditText) findViewById(R.id.know_detaile_et_content);
		findViewById(R.id.know_detaile_tv_more_jinxuan).setOnClickListener(this);
		findViewById(R.id.know_detaile_tv_question).setOnClickListener(this);
		findViewById(R.id.know_detaile_tv_commit).setOnClickListener(this);
		findViewById(R.id.know_detaile_ll_l_jinxuan).setOnClickListener(this);
		findViewById(R.id.know_detaile_ll_t_jinxuan).setOnClickListener(this);
		findViewById(R.id.know_detaile_ll_r_jinxuan).setOnClickListener(this);
		String s = getResources().getString(R.string.space);
		tv_content.setText(s+"·¢¼ÐÅ¶·³Âð°¡U°¡¼¸Å¶°¡‡å°¡À´¿´·ðºÅ·¢»õ¼ÒÀï·çÃ²·ÛÄ©");
	}



	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.know_detaile_tv_more_jinxuan:

			break;
		case R.id.know_detaile_tv_question:
			intent = new Intent(context,PutQuestionActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.know_detaile_tv_commit:
			Util.ShowToast(context, "commit");
			break;
		case R.id.know_detaile_ll_l_jinxuan:
			Toproduct();
			break;
		case R.id.know_detaile_ll_t_jinxuan:
			Toproduct();
			break;
		case R.id.know_detaile_ll_r_jinxuan:
			Toproduct();
			break;
		}

	}
	
	
	private void Toproduct(){
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}


}
