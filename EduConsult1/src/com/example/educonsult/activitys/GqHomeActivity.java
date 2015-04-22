package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.adapters.GqAdapter;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.myviews.MyGridView;

public class GqHomeActivity extends BaseActivity implements OnClickListener{
	private TextView tv_all,tv_diqu,tv_zhineng,tv_shaixuan,tv_more_tuijian,tv_more_paied,
	tv_price_l_tuijian,tv_price_t_tuijian,tv_price_r_tuijian,
	tv_price_l_paied,tv_price_t_paied,tv_price_r_paied;
	private ImageView iv_l_tuijian,iv_t_tuijian,iv_r_tuijian,
	iv_l_paied,iv_t_paied,iv_r_paied,iv_topl,iv_topr;
	private LinearLayout ll_l_tuijian,ll_t_tuijian,ll_r_tuijian,
	ll_l_paied,ll_t_paied,ll_r_paied;
	private ScrollView scrollView;
	private PopupWindow popupWindow;
	private boolean isshow;
	private Context context;
	private MyGridView gridView;
	private GqAdapter adapter;
	private ArrayList<Integer>list;
	private Intent intent;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightLVisible();
		topRightRVisible();
		topRightTGone();
		iv_topl = (ImageView) getTopLightView();
		iv_topr = (ImageView) getTopRightView();
		setTopLeftTv(R.string.gongqiu_title);
		setContentXml(R.layout.gq_home_layout);
		init();
		addlistener();
		//		/**********set***********/
		UserBean b = new UserBean();
		b.setName("121");
		String l = b.toString();
		MyApplication.bean = b;
		/**********get***********/
		UserBean a = MyApplication.bean;
		String s = a.getName();





	}

	private void addlistener() {
		iv_topl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "left", 200).show();
			}
		});
		iv_topr.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(context, "right", 200).show();
			}
		});

		View v = new ImageView(context);
		v.setBackgroundResource(R.drawable.ic_launcher);
		popupWindow = new PopupWindow(v, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		isshow = false;
		scrollView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_MOVE){
					if(!isshow){
						if(scrollView.getScrollY()>100){
							popupWindow.showAtLocation(v, Gravity.BOTTOM|Gravity.RIGHT,20, 60);
							isshow = true;
						}
					}else{
						if(scrollView.getScrollY()<=100){
							if(popupWindow!=null && popupWindow.isShowing()){
								popupWindow.dismiss();
							}
							isshow = false;
						}
					}
				}
				return false;
			}
		});

		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isshow){
					if(popupWindow!=null && popupWindow.isShowing()){
						popupWindow.dismiss();
					}
					scrollView.scrollTo(10, 10);
					Toast.makeText(context, "µ±Ç°Î»ÖÃ"+scrollView.getScrollY(), Toast.LENGTH_SHORT).show();
					isshow = false;
				}
			}
		});
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toproduct();
			}
		});

	}

	private void init() {
		context = this;
		scrollView = (ScrollView) findViewById(R.id.gq_home_sc);
		scrollView.scrollTo(0, 10);
		tv_all = (TextView) findViewById(R.id.gq_home_tv_all);
		tv_all.setOnClickListener(this);
		tv_diqu = (TextView) findViewById(R.id.gq_home_tv_diqu);
		tv_diqu.setOnClickListener(this);
		tv_zhineng = (TextView) findViewById(R.id.gq_home_tv_zhineng);
		tv_zhineng.setOnClickListener(this);
		tv_shaixuan = (TextView) findViewById(R.id.gq_home_tv_shaixuan);
		tv_shaixuan.setOnClickListener(this);
		tv_more_tuijian = (TextView) findViewById(R.id.gq_home_tv_more_tuijian);
		tv_more_tuijian.setOnClickListener(this);
		tv_more_paied = (TextView) findViewById(R.id.gq_home_tv_more_paied);
		tv_more_paied.setOnClickListener(this);
		
		ll_l_tuijian = (LinearLayout) findViewById(R.id.gq_home_ll_l_tuijian);
		ll_l_tuijian.setOnClickListener(this);
		ll_t_tuijian = (LinearLayout) findViewById(R.id.gq_home_ll_t_tuijian);
		ll_t_tuijian.setOnClickListener(this);
		ll_r_tuijian = (LinearLayout) findViewById(R.id.gq_home_ll_r_tuijian);
		ll_r_tuijian.setOnClickListener(this);
		ll_l_paied = (LinearLayout) findViewById(R.id.gq_home_ll_l_paied);
		ll_l_paied.setOnClickListener(this);
		ll_t_paied = (LinearLayout) findViewById(R.id.gq_home_ll_t_paied);
		ll_t_paied.setOnClickListener(this);
		ll_r_paied = (LinearLayout) findViewById(R.id.gq_home_ll_r_paied);
		ll_r_paied.setOnClickListener(this);

		iv_l_tuijian = (ImageView) findViewById(R.id.gq_home_iv_l_tuijian);
		iv_t_tuijian = (ImageView) findViewById(R.id.gq_home_iv_t_tuijian);
		iv_r_tuijian = (ImageView) findViewById(R.id.gq_home_iv_r_tuijian);
		iv_l_paied = (ImageView) findViewById(R.id.gq_home_iv_l_paied);
		iv_t_paied = (ImageView) findViewById(R.id.gq_home_iv_t_paied);
		iv_r_paied = (ImageView) findViewById(R.id.gq_home_iv_r_paied);
		tv_price_l_tuijian = (TextView) findViewById(R.id.gq_home_tv_price_l_tuijian);
		tv_price_t_tuijian = (TextView) findViewById(R.id.gq_home_tv_price_t_tuijian);
		tv_price_r_tuijian = (TextView) findViewById(R.id.gq_home_tv_price_r_tuijian);
		tv_price_l_paied = (TextView) findViewById(R.id.gq_home_tv_price_l_paied);
		tv_price_t_paied = (TextView) findViewById(R.id.gq_home_tv_price_t_paied);
		tv_price_r_paied = (TextView) findViewById(R.id.gq_home_tv_price_r_paied);

		gridView = (MyGridView) findViewById(R.id.gq_home_gv);
		list = new ArrayList<Integer>();
		for(int i=0;i<10;i++){
			list.add(i);
		}
		adapter = new GqAdapter(context, list);
		gridView.setAdapter(adapter);
		gridView.setFocusable(false);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gq_home_tv_all:
			intent = new Intent(this,GqTwoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.gq_home_tv_diqu:
			intent = new Intent(this,GqTwoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.gq_home_tv_zhineng:
			intent = new Intent(this,GqTwoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
			
		case R.id.gq_home_tv_shaixuan:
			intent = new Intent(this,GqTwoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
			
		case R.id.gq_home_ll_l_tuijian:
			Toproduct();
			break;
		case R.id.gq_home_ll_t_tuijian:
			Toproduct();
			break;
		case R.id.gq_home_ll_r_tuijian:
			Toproduct();
			break;
		case R.id.gq_home_ll_l_paied:
			Toproduct();
			break;
		case R.id.gq_home_ll_t_paied:
			Toproduct();
			break;
		case R.id.gq_home_ll_r_paied:
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
