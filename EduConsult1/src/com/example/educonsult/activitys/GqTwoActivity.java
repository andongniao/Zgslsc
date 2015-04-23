package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
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
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.example.educonsult.R;
import com.example.educonsult.adapters.GqAdapter;
import com.example.educonsult.adapters.HomeRuzhuAdapter;
import com.example.educonsult.myviews.MyGridView;
import com.example.educonsult.tools.Util;

public class GqTwoActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private ScrollView scrollView;
	private PopupWindow popupWindow;
	private boolean isshow;
	private HomeRuzhuAdapter adapter;
	//	private GridView gv_dh;
	private MyGridView gv_pp,gv_dh,gv_dh_xq;
	private ArrayList<String>list;
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private LinearLayout add_ll;
	private TextView tv_b;
	private ArrayList<View> l;
	private int pos = -1;
	private GqAdapter gqAdapter;
	private Intent intent;
	private LinearLayout ll_jingxuan_l,ll_jingxuan_t,ll_jingxuan_r;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightLVisible();
		topRightRVisible();
		String title = getIntent().getStringExtra("title");
		if(Util.IsNull(title)){
			setTopLeftTv(title);
		}else{
		setTopLeftTv(R.string.gongqiu_title);
		}
		setContentXml(R.layout.gq_two);
		init();
		addlistener();
	}
	private void addlistener() {
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
		gv_dh_xq.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toproduct();
			}
		});

	}
	private void init() {
		context = this;
		l = new ArrayList<View>();
		list = new ArrayList<String>();
		adapter = new HomeRuzhuAdapter(context, list);
		scrollView = (ScrollView) findViewById(R.id.gq_two_sc);
		ll_jingxuan_l = (LinearLayout) findViewById(R.id.gq_two_ll_jingxuan_l);
		ll_jingxuan_l.setOnClickListener(this);
		ll_jingxuan_t = (LinearLayout) findViewById(R.id.gq_two_ll_jingxuan_t);
		ll_jingxuan_t.setOnClickListener(this);
		ll_jingxuan_r = (LinearLayout) findViewById(R.id.gq_two_ll_jingxuan_r);
		ll_jingxuan_r.setOnClickListener(this);

		add_ll = (LinearLayout) findViewById(R.id.gq_two_ll_addview);
		gv_pp = (MyGridView) findViewById(R.id.gq_two_gv_pp);
		gv_pp.setAdapter(adapter);
		gv_dh_xq = (MyGridView) findViewById(R.id.gq_two_gv_dh_xq);

		for(int i = 0;i<10;i++){
			View v = LayoutInflater.from(context).inflate(R.layout.gq_two_add_view, null);//new ImageView(context);
			tv_b = (TextView) v.findViewById(R.id.gq_two_add_tv_down);
			v.setLayoutParams(new LayoutParams(100, 80));
			v.setOnClickListener(new MyOnClickListener(i));
			add_ll.addView(v);
			l.add(tv_b);
			ArrayList<Integer> s = new ArrayList<Integer>();
			for(int o=0;o<10;o++){
				s.add(o);
			}
			gqAdapter = new GqAdapter(context, s);
			gv_dh_xq.setAdapter(gqAdapter);
		}
	}
	class MyOnClickListener implements OnClickListener{
		private int x = -1;
		public MyOnClickListener(int x){
			this.x=x;
		}
		@Override
		public void onClick(View v) {
			Toast.makeText(context, "click"+x, 1000).show();
			if(pos==-1){
				l.get(x).setBackgroundResource(R.color.orn);
			}else{
				l.get(pos).setBackgroundResource(R.color.black);	
				l.get(x).setBackgroundResource(R.color.orn);
			}
			pos = x;
		}

	}
	private void Toproduct(){
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gq_two_ll_jingxuan_l:
			Toproduct();
			break;
		case R.id.gq_two_ll_jingxuan_t:
			Toproduct();
			break;
		case R.id.gq_two_ll_jingxuan_r:
			Toproduct();
			break;

		}
	}


}
