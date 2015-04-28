package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.astuetz.PagerSlidingTabStrip;
import com.example.educonsult.ExampleActivity;
import com.example.educonsult.R;
import com.example.educonsult.adapters.GqAdapter;
import com.example.educonsult.adapters.HomeRuzhuAdapter;
import com.example.educonsult.adapters.HomeSlidAdapter;
import com.example.educonsult.adapters.KnowFenleiAdapter;
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
	private int pos = 0;
	private GqAdapter gqAdapter;
	private Intent intent;
	private LayoutInflater inflater;
	private LinearLayout ll_jingxuan_l,ll_jingxuan_t,ll_jingxuan_r;
	//public LinearLayout ll_gqtwo_popu;
	private ImageView iv_top_l,iv_top_t,gqtwo_1,gqtwo_2;
	private RelativeLayout rl_l,rl_r;
	public static boolean isread;
	private ThreadWithProgressDialog myPDT;
	public View ll_gqtwo_popu;
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
		String title = getIntent().getStringExtra("title");


		if(Util.IsNull(title)){
			setTopLeftTv(title);
		}else{
			setTopLeftTv(R.string.gongqiu_title);
		}
		setContentXml(R.layout.gq_two);
		init();
		addlistener();
		/*************测试****************/
		myPDT = new ThreadWithProgressDialog();
		String  msg = getResources().getString(R.string.loding);
		myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
//		myPDT.Run(context, new RefeshData(),msg,false);//不可取消
	}
	private void addlistener() {
		rl_l.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(context,XinjianActivity.class);
				intent.putExtra("flag", "gqtwo");
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
		inflater = LayoutInflater.from(context);
		ll_gqtwo_popu=inflater.inflate(R.layout.gqtwo_popu_layout, null);
		gqtwo_1=(ImageView)ll_gqtwo_popu.findViewById(R.id.gqtwo_popu_layout_img1);
		gqtwo_2=(ImageView)ll_gqtwo_popu.findViewById(R.id.gqtwo_popu_layout_img2);
		gqtwo_1.setBackgroundResource(R.drawable.base_to_zj);
		gqtwo_2.setBackgroundResource(R.drawable.base_to_top);
		gqtwo_2.setVisibility(View.GONE);
		//View v = new ImageView(context);
		//v.setBackgroundResource(R.drawable.base_to_top);
		
		//View v=inflater.inflate(R.layout.gq_two, null);
		
		popupWindow = new PopupWindow(ll_gqtwo_popu, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		
		isshow = false;
		scrollView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				
				if(event.getAction()==MotionEvent.ACTION_MOVE){
					if(!isshow){
						if(scrollView.getScrollY()>100){
							popupWindow.showAtLocation(v, Gravity.BOTTOM|Gravity.RIGHT,20, 60);
							
							gqtwo_2.setVisibility(View.VISIBLE);
							isshow = true;
						}
					}else{
						if(scrollView.getScrollY()<=100){
							if(popupWindow!=null && popupWindow.isShowing()){
								//popupWindow.dismiss();
							gqtwo_2.setVisibility(View.GONE);
							}
							isshow = false;
						}
					}
				}
				return false;
			}
		});

		gqtwo_2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isshow){
					if(popupWindow!=null && popupWindow.isShowing()){
						//popupWindow.dismiss();
						gqtwo_2.setVisibility(View.GONE);
					}
					scrollView.scrollTo(10, 10);
					isshow = false;
				}
			}
		});
		gqtwo_1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//Intent intent=new Intent();
				//intent.setClass(packageContext, cls)
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
			v.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
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
		if(l.size()>0){
			l.get(0).setBackgroundResource(R.color.orn);
		}
		Util.SetRedNum(context, rl_l, 1);
	}
	class MyOnClickListener implements OnClickListener{
		private int x = 0;
		public MyOnClickListener(int x){
			this.x=x;
		}
		@Override
		public void onClick(View v) {
			Toast.makeText(context, "click"+x, Toast.LENGTH_SHORT).show();
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
	@Override
	protected void onResume() {
		super.onResume();
		if(isread){
			Util.SetRedGone(context, rl_l);
			isread = false;
		}
	}


	// 任务
	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
//			Toast.makeText(context, "cancle", 1000).show();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			Thread t = new Thread();
			try {
				t.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
			return true;
		}
	}



}
