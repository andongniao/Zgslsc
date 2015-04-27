package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.adapters.GqAdapter;
import com.example.educonsult.adapters.HomeSlidAdapter;
import com.example.educonsult.adapters.KnowFenleiAdapter;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.myviews.MyGridView;
import com.example.educonsult.tools.Util;

public class GqHomeActivity extends BaseActivity implements OnClickListener{
	private TextView tv_more_tuijian,tv_more_paied,
	tv_price_l_tuijian,tv_price_t_tuijian,tv_price_r_tuijian,
	tv_price_l_paied,tv_price_t_paied,tv_price_r_paied;
	private ImageView iv_l_tuijian,iv_t_tuijian,iv_r_tuijian,
	iv_l_paied,iv_t_paied,iv_r_paied;
	private LinearLayout ll_l_tuijian,ll_t_tuijian,ll_r_tuijian,
	ll_l_paied,ll_t_paied,ll_r_paied,ll_all,ll_diqu,ll_zhineng,ll_shaixuan,lll_r;
	private ScrollView scrollView;
	private PopupWindow popupWindow,pp_top_fenlei;
	private boolean isshow;
	private Context context;
	private MyGridView gridView;
	private GqAdapter adapter;
	private ArrayList<Integer>list;
	private Intent intent;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	public static boolean isread;
	private View v_fenlei;
	private ListView lv_l,lv_r;
	private HomeSlidAdapter adapter_r;
	private KnowFenleiAdapter adapter_l;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightLVisible();
		topRightRVisible();
		topRightTGone();
		rl_l = (RelativeLayout) getTopLightRl();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_l = (ImageView) getTopLightView();
		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
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
		rl_l.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(context,XinjianActivity.class);
				intent.putExtra("flag", "ghhome");
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

		View v = new ImageView(context);
		v.setBackgroundResource(R.drawable.base_to_top);
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
		isread = false;
		v_fenlei =  LayoutInflater.from(context).inflate(R.layout.know_slidemenu_view, null);
		pp_top_fenlei = new PopupWindow(v_fenlei, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		pp_top_fenlei.setFocusable(true);
		pp_top_fenlei.setBackgroundDrawable(new BitmapDrawable());
		pp_top_fenlei.setOutsideTouchable(true);
		pp_top_fenlei.update();
		ArrayList<String>ll = new ArrayList<String>();
		lv_l = (ListView) v_fenlei.findViewById(R.id.know_slid_view_lv_l);
		lll_r = (LinearLayout) v_fenlei.findViewById(R.id.know_slid_view_ll_r);
		ArrayList<Integer> l = new ArrayList<Integer>();
		for(int i=0;i<8;i++){
			l.add(i);
		}
		adapter_l = new KnowFenleiAdapter(context, l);
		lv_l.setAdapter(adapter_l);
		lv_r = (ListView) v_fenlei.findViewById(R.id.know_slid_view_lv_r);
		adapter_r = new HomeSlidAdapter(context, ll,2);
		lv_r.setAdapter(adapter_r);
		lv_l.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(arg2>1){
					lll_r.setVisibility(View.VISIBLE);
					adapter_l.SetData(arg2);
					adapter_l.notifyDataSetChanged();
				}
			}
		});
		
		
		
		
		
		scrollView = (ScrollView) findViewById(R.id.gq_home_sc);
		scrollView.scrollTo(0, 10);
		ll_all = (LinearLayout) findViewById(R.id.gq_home_ll_all);
		ll_all.setOnClickListener(this);
		ll_diqu = (LinearLayout) findViewById(R.id.gq_home_ll_diqu);
		ll_diqu.setOnClickListener(this);
		ll_zhineng = (LinearLayout) findViewById(R.id.gq_home_ll_zhineng);
		ll_zhineng.setOnClickListener(this);
		ll_shaixuan = (LinearLayout) findViewById(R.id.gq_home_ll_shaixuan);
		ll_shaixuan.setOnClickListener(this);
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
		Util.SetRedNum(context, rl_l, 0);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gq_home_ll_all:
//			intent = new Intent(this,GqTwoActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
			if(pp_top_fenlei!=null && pp_top_fenlei.isShowing()){	
				pp_top_fenlei.dismiss();
			}else{
//			pp_top_fenlei.showAtLocation(ll_all, Gravity.TOP, 0, ll_all.getHeight());
				pp_top_fenlei.showAsDropDown(ll_all);
			}
			
			break;
		case R.id.gq_home_ll_diqu:
			intent = new Intent(this,GqTwoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.gq_home_ll_zhineng:
			intent = new Intent(this,GqTwoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
			
		case R.id.gq_home_ll_shaixuan:
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
	
	@Override
	protected void onResume() {
		super.onResume();
		if(isread){
			Util.SetRedGone(context, rl_l);
			isread = false;
		}
	}
	
	
}
