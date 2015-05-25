package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.educonsult.R;
import com.example.educonsult.R.id;
import com.example.educonsult.activitys.GqTwoActivity.MyOnClickListener;
import com.example.educonsult.adapters.GqAdapter;
import com.example.educonsult.adapters.ZhanhuiHomeAdapter;
import com.example.educonsult.adapters.ZhanhuilvAdapter;
import com.example.educonsult.myviews.MyGridView;
import com.example.educonsult.myviews.MyListview;
import com.testin.agent.TestinAgent;

public class ZhanhuiHomeActivity extends Activity implements OnClickListener{
	protected int activityCloseEnterAnimation;

	protected int activityCloseExitAnimation;
	
	private Context context;
	private LinearLayout ll_back,ll_jinxuan_l,ll_jinxuan_t,ll_jinxuan_r,ll_add;
	private ImageView iv_up_hot,iv_down_hot,iv_up_tuijian,iv_down_tuijian;
	private TextView tv_title;
	private MyGridView gv_hot,gv_tuijian;
	private ZhanhuiHomeAdapter adapter_gv;
	private ArrayList<Integer> list;
	private ArrayList<View> list_view;
	private int pos = -1;
	private ScrollView scrollView;
	private PopupWindow popupWindow;
	private boolean isshow;
	private MyListview lv;
	private ZhanhuilvAdapter adapter_lv;
	private Intent intent;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		TestinAgent.init(this);
		TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});
		int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);      
		activityStyle.recycle();
		activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
		activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
		activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
		activityStyle.recycle();
		
		
		setContentView(R.layout.zhanhui_home_layout);
		init();
		addlistener();
	}

	private void init() {
		context = this;
		list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list_view = new ArrayList<View>();
		scrollView = (ScrollView) findViewById(R.id.zhanhui_home_sc);
		ll_add = (LinearLayout) findViewById(R.id.zhanhui_home_ll_addview);
		ll_back = (LinearLayout) findViewById(R.id.zhanhui_top_ll_back);
		ll_back.setOnClickListener(this);
		tv_title = (TextView) findViewById(R.id.zhanhui_top_tv_back);
		tv_title.setText(R.string.zhanhui_title);
		gv_hot = (MyGridView) findViewById(R.id.zhanhui_home_gv_hot);
		gv_tuijian = (MyGridView) findViewById(R.id.zhanhui_home_gv_tuijian);
		adapter_gv = new ZhanhuiHomeAdapter(context, list);
		gv_hot.setAdapter(adapter_gv);
		gv_tuijian.setAdapter(adapter_gv);
		
		lv = (MyListview) findViewById(R.id.zhanhui_home_lv);
		adapter_lv = new ZhanhuilvAdapter(context, list);
		lv.setAdapter(adapter_lv);
		
		for(int i = 0;i<10;i++){
			View v = LayoutInflater.from(context).inflate(R.layout.gq_two_add_view, null);//new ImageView(context);
			TextView tv_b = (TextView) v.findViewById(R.id.gq_two_add_tv_down);
			v.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT));
			v.setOnClickListener(new MyOnClickListener(i));
			ll_add.addView(v);
			list_view.add(tv_b);
		}
		
		 
	}
	
	private void addlistener() {
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				intent = new Intent(context,ZhanhuiDetaileActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
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
				list_view.get(x).setBackgroundResource(R.color.orn);
			}else{
				list_view.get(pos).setBackgroundResource(R.color.black);	
				list_view.get(x).setBackgroundResource(R.color.orn);
			}
			pos = x;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.zhanhui_top_ll_back:
			finish();
			break;

		}
	}
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
	}

}
