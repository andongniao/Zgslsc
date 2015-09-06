package com.xunbo.store.fragments;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
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
import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.example.educonsult.R;
import com.example.educonsult.activitys.GqTwoActivity;
import com.example.educonsult.activitys.ProductDetaileActivity;
import com.example.educonsult.activitys.SearchHomeActivity;
import com.example.educonsult.activitys.SearchResultActivity;
import com.xunbo.store.MyApplication;
import com.xunbo.store.adapters.HomeGridAdapter;
import com.xunbo.store.beans.HomeCatBean;
import com.xunbo.store.beans.HomeInfoBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.myviews.MyGridView;
import com.xunbo.store.myviews.RefreshableView;
import com.xunbo.store.myviews.RefreshableView.RefreshListener;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class HomeLayoutFragment extends Activity implements OnClickListener,RefreshListener{
	private MyGridView gv_siliao,gv_shouyao,gv_yzsb,gv_qxyz,gv_tianjiaji,gv_yuanliao,gv_qita;
	private LinearLayout lv_rem,lv_siliao,lv_shouyao,lv_yzsb,lv_qxyz,lv_tianjiaji,lv_yuanliao,lv_qita,
	lv_rem1,lv_rem2,lv_rem3,lv_rem4;
	private ImageView ima_top,ima_centent1,ima_centent2,ima_rem_left,ima_rem_r1,ima_rem_r2,ima_rem1,
	ima_rem2,ima_rem3,ima_rem4,ima_fenlei;
	private TextView tv_title1,tv_title2,tv_title3,tv_title4,tv_detaile1,tv_detaile2,
	tv_detaile3,tv_detaile4;
	private Context context;
	private ScrollView sc;
	private MyApplication mp;
	private View view;
	private Intent intent;
	private RelativeLayout top_rl;
	private Message msg;
	private HomeGridAdapter siliaoAdapter,shouyaoAdapter,yzsbAdapter,qxyzAdapter,
	tianjiajiAdapter,yuanliaoAdapter;
	private ThreadWithProgressDialog myPDT;
	private HomeInfoBean homeInfoBean;
	private ArrayList<ProductBean> recommend,siliao,shouyao,yzsb,qxyz,tianjiaji,yuanliao;
	private ArrayList<HomeCatBean> cat;
	private ArrayList<String> ad;
	private Handler handler;
	private RefreshableView refreshableView;
	private boolean isdata,isshow;
	private int w,h;
	private PopupWindow popupWindow;
	private View v;
	private long exitTime = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_layout);
		init();
		addlistener();

	}


	private void init() {
		context =this;
		mp=new MyApplication();
		myPDT=new ThreadWithProgressDialog();
		DisplayMetrics  dm = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		w = dm.widthPixels;  
		h = dm.heightPixels; 
		v = new ImageView(context);
		v.setBackgroundResource(R.drawable.base_to_top);
		popupWindow = new PopupWindow(v, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		isshow = false;
		refreshableView=(RefreshableView)findViewById(R.id.home_layout_rview);
		refreshableView.setRefreshListener(this);
		sc=(ScrollView)findViewById(R.id.home_layout_sc);
		top_rl = (RelativeLayout) findViewById(R.id.home_layout_rl_search);
		tv_title1=(TextView)findViewById(R.id.home_layout_tv_rem_title_1);
		tv_title2=(TextView)findViewById(R.id.home_layout_tv_rem_title_2);
		tv_title3=(TextView)findViewById(R.id.home_layout_tv_rem_title_3);
		tv_title4=(TextView)findViewById(R.id.home_layout_tv_rem_title_4);
		tv_detaile1=(TextView)findViewById(R.id.home_layout_tv_rem_detaile_1);
		tv_detaile2=(TextView)findViewById(R.id.home_layout_tv_rem_detaile_2);
		tv_detaile3=(TextView)findViewById(R.id.home_layout_tv_rem_detaile_3);
		tv_detaile4=(TextView)findViewById(R.id.home_layout_tv_rem_detaile_4);
		ima_fenlei=(ImageView)findViewById(R.id.home_layout_iv_cat);
		ima_fenlei.setOnClickListener(this);
		ima_fenlei.setVisibility(View.INVISIBLE);
		ima_top=(ImageView)findViewById(R.id.home_layout_iv_top);
		ima_centent1=(ImageView)findViewById(R.id.home_layout_iv_center1);
		ima_centent2=(ImageView)findViewById(R.id.home_layout_iv_center2);
		ima_rem_left=(ImageView)findViewById(R.id.home_layout_iv_rem_top_lf);
		ima_rem_left.setOnClickListener(this);
		ima_rem_r1=(ImageView)findViewById(R.id.home_layout_iv_rem_right__t);
		ima_rem_r1.setOnClickListener(this);
		ima_rem_r2=(ImageView)findViewById(R.id.home_layout_iv_rem_right_b);
		ima_rem_r2.setOnClickListener(this);
		ima_rem1=(ImageView)findViewById(R.id.home_layout_iv_rem_1);
		ima_rem2=(ImageView)findViewById(R.id.home_layout_iv_rem_2);
		ima_rem3=(ImageView)findViewById(R.id.home_layout_iv_rem_3);
		ima_rem4=(ImageView)findViewById(R.id.home_layout_iv_rem_4);
		lv_rem=(LinearLayout)findViewById(R.id.home_layout_ll_more_rem);
		lv_siliao=(LinearLayout)findViewById(R.id.home_layout_ll_more_siliao);
		lv_shouyao=(LinearLayout)findViewById(R.id.home_layout_ll_more_shouyao);
		lv_yzsb=(LinearLayout)findViewById(R.id.home_layout_ll_more_yangzhishebei);
		lv_qxyz=(LinearLayout)findViewById(R.id.home_layout_ll_more_chuqinyangzhi);
		lv_tianjiaji=(LinearLayout)findViewById(R.id.home_layout_ll_more_tianjiaji);
		lv_yuanliao=(LinearLayout)findViewById(R.id.home_layout_ll_more_yuanliao);
		lv_qita=(LinearLayout)findViewById(R.id.home_layout_ll_more_qita);
		lv_rem1=(LinearLayout)findViewById(R.id.home_layout_ll_rem_b_1);
		lv_rem2=(LinearLayout)findViewById(R.id.home_layout_ll_rem_b_2);
		lv_rem3=(LinearLayout)findViewById(R.id.home_layout_ll_rem_b_3);
		lv_rem4=(LinearLayout)findViewById(R.id.home_layout_ll_rem_b_4);
		lv_rem1.setOnClickListener(this);
		lv_rem3.setOnClickListener(this);
		lv_rem2.setOnClickListener(this);
		lv_rem4.setOnClickListener(this);
		lv_rem.setOnClickListener(this);
		lv_siliao.setOnClickListener(this);
		lv_shouyao.setOnClickListener(this);
		lv_yzsb.setOnClickListener(this);
		lv_qxyz.setOnClickListener(this);
		lv_tianjiaji.setOnClickListener(this);
		lv_yuanliao.setOnClickListener(this);
		lv_qita.setOnClickListener(this);
		gv_siliao=(MyGridView)findViewById(R.id.home_layout_gv_siliao);
		gv_shouyao=(MyGridView)findViewById(R.id.home_layout_gv_shouyao);
		gv_yzsb=(MyGridView)findViewById(R.id.home_layout_gv_yangzhishebei);
		gv_qxyz=(MyGridView)findViewById(R.id.home_layout_gv_chuqinyangzhi);
		gv_tianjiaji=(MyGridView)findViewById(R.id.home_layout_gv_tianjiaji);
		gv_yuanliao=(MyGridView)findViewById(R.id.home_layout_gv_yuanliao);
		gv_qita=(MyGridView)findViewById(R.id.home_layout_gv_qita);
		gv_siliao.setFocusable(false);
		gv_yzsb.setFocusable(false);
		gv_qxyz.setFocusable(false);
		gv_shouyao.setFocusable(false);
		gv_tianjiaji.setFocusable(false);
		gv_yuanliao.setFocusable(false);
		gv_qita.setFocusable(false);
		handler = new Handler(){
			@Override
			public void handleMessage(Message m) {
				super.handleMessage(m);
				if(m.what==1){
					refreshableView.finishRefresh();
					homeInfoBean = (HomeInfoBean) m.obj;
					if(homeInfoBean!=null){
						initData(homeInfoBean);
					}
				}else{
					refreshableView.finishRefresh();
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
		};

		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}



	}

	private void addlistener() {
		sc.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_MOVE){
					if(!isshow){
						if(sc.getScrollY()>100){
							popupWindow.showAtLocation(gv_yuanliao, Gravity.BOTTOM,w/2-20, 120);
							isshow = true;
							//							ispopu=true;
						}
					}else{
						if(sc.getScrollY()<=100){
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
					sc.scrollTo(10, 10);
					isshow = false;
				}
			}
		});
		top_rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { 
				intent = new Intent(context,SearchHomeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intent);
			}
		});
		gv_siliao.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(siliao.get(arg2)!=null){
					Toproduct(siliao.get(arg2));
				}
			}
		});
		//		gv_qita.setOnItemClickListener(new OnItemClickListener() {
		//
		//			@Override
		//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
		//					long arg3) {
		//				// TODO Auto-generated method stub
		//				
		//			}
		//		});
		gv_qxyz.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(qxyz.get(arg2)!=null){

					Toproduct(qxyz.get(arg2));
				}
			}
		});
		gv_shouyao.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(shouyao.get(arg2)!=null){

					Toproduct(shouyao.get(arg2));
				}
			}
		});
		gv_tianjiaji.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(tianjiaji.get(arg2)!=null){

					Toproduct(tianjiaji.get(arg2));
				}
			}
		});

		gv_yuanliao.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(yuanliao.get(arg2)!=null){

					Toproduct(yuanliao.get(arg2));
				}
			}
		});
		gv_yzsb.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(yzsb.get(arg2)!=null){

					Toproduct(yzsb.get(arg2));
				}	
			}
		});
	}


	@Override
	public void onClick(View v) {
		if(isdata){
			switch (v.getId()) {
			case R.id.home_layout_iv_cat:
				break;
			case R.id.home_layout_ll_more_chuqinyangzhi:

				break;
			case R.id.home_layout_ll_more_qita:
				break;
			case R.id.home_layout_ll_more_rem:
				break;
			case R.id.home_layout_ll_more_shouyao:
				break;
			case R.id.home_layout_ll_more_siliao:
				break;
			case R.id.home_layout_ll_more_tianjiaji:
				break;
			case R.id.home_layout_ll_more_yangzhishebei:
				break;
			case R.id.home_layout_ll_more_yuanliao:
				break;
			case R.id.home_layout_iv_rem_top_lf:
				if(recommend.get(0)!=null){

					Toproduct(recommend.get(0));
				}
				break;
			case R.id.home_layout_iv_rem_right__t:
   				if(recommend.get(1)!=null){

					Toproduct(recommend.get(1));
				}
				break;
			case R.id.home_layout_iv_rem_right_b:
				if(recommend.get(2)!=null){

					Toproduct(recommend.get(2));
				}
				break;
			case R.id.home_layout_ll_rem_b_1:
				if(recommend.get(3)!=null){

					Toproduct(recommend.get(3));
				}
				break;
			case R.id.home_layout_ll_rem_b_2:
				if(recommend.get(4)!=null){
					Toproduct(recommend.get(4));
				}
				break;
			case R.id.home_layout_ll_rem_b_3:
				if(recommend.get(5)!=null){   
					Toproduct(recommend.get(5));
				}
				break;
			case R.id.home_layout_ll_rem_b_4:
				if(recommend.get(6)!=null){
					Toproduct(recommend.get(6));
				} 
				break;

			}
		}
	}
	private void ToSearch(String text){
		SearchResultActivity.isproductfinish=false;
		intent=new Intent(context, SearchResultActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("searchtype", "1");
		intent.putExtra("searchorder", "0");
		intent.putExtra("searchtext", text);
		startActivity(intent);
	}
	private void ToGqTwo(String text){
		intent=new Intent(context, GqTwoActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("searchtext", text);
		intent.putExtra("num", 0);
		startActivity(intent);
	}

	private void Toproduct(ProductBean productBean){
		//		popupWindow.dismiss();
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//intent.putExtra("productdetaile", value);
		Bundle b=new Bundle();
		b.putSerializable("product", productBean);
		intent.putExtra("productbundle", b);
		startActivity(intent);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if(isshow){

			popupWindow.dismiss();
		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		if(isshow){
			popupWindow.showAtLocation(gv_yuanliao, Gravity.BOTTOM,w/2-20, 120);
		}
	}



	private void SetPrice(TextView v ,String s){
		v.setText("￥"+s);
	}


	@Override
	public void onRefresh(RefreshableView view) {
		if (Util.detect(context)) {
			new Thread(){
				@Override
				public void run() {
					super.run();
					Send send = new Send(context);
					homeInfoBean = send.getHomeInfo();
					Message m = handler.obtainMessage();
					if(homeInfoBean!=null){
						m.obj = homeInfoBean;
						m.what=1;
					}else{
						m.what=2;
					}
					handler.sendMessage(m);
				}
			}.start();
		} else {
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					Util.ShowToast(context, R.string.net_is_eor);
					refreshableView.finishRefresh();
				}
			}, 500);
		}
	}

	public class RefeshData implements ThreadWithProgressDialogTask {
		public RefeshData(){

		}
		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub
			Send s=new Send(context);
			homeInfoBean=s.getHomeInfo();
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			// TODO Auto-generated method stub
			if(homeInfoBean!=null){
				if("200".equals(homeInfoBean.getCode())){
					initData(homeInfoBean);
					BDAutoUpdateSDK.uiUpdateAction(context, new MyUICheckUpdateCallback());
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			return true;
		}

	}
	private class MyUICheckUpdateCallback implements UICheckUpdateCallback {

		@Override
		public void onCheckComplete() {
		}

	}
	private void initData(HomeInfoBean homeInfoBean){
		isdata=true;
		recommend=homeInfoBean.getRecommend();
		cat=homeInfoBean.getCat();
		ad=homeInfoBean.getAd();
		if(ad!=null){
			if(ad.size()>0){

				Util.Getbitmap(ima_top, ad.get(0));
				Util.Getbitmap(ima_centent1, ad.get(1));
				Util.Getbitmap(ima_centent2, ad.get(2));
			}
		}
		if(recommend!=null){
			if(recommend.size()>0){
				if(Util.IsNull(recommend.get(0).getApppic())){
					Util.Getbitmap(ima_rem_left, recommend.get(0).getApppic());
				}else{
					Util.Getbitmap(ima_rem_left, recommend.get(0).getThumb());
				}
				if(Util.IsNull(recommend.get(1).getApppic())){
					Util.Getbitmap(ima_rem_r1, recommend.get(1).getApppic());
				}else{
					Util.Getbitmap(ima_rem_r1, recommend.get(1).getThumb());
				}
				if(Util.IsNull(recommend.get(2).getApppic())){
					Util.Getbitmap(ima_rem_r2, recommend.get(2).getApppic());
				}else{
					Util.Getbitmap(ima_rem_r2, recommend.get(2).getThumb());
				}
				if(Util.IsNull(recommend.get(3).getApppic())){
					Util.Getbitmap(ima_rem1, recommend.get(3).getApppic());
				}else{
					Util.Getbitmap(ima_rem1, recommend.get(3).getThumb());
				}
				if(Util.IsNull(recommend.get(4).getApppic())){
					Util.Getbitmap(ima_rem2, recommend.get(4).getApppic());
				}else{
					Util.Getbitmap(ima_rem2, recommend.get(4).getThumb());
				}
				if(Util.IsNull(recommend.get(5).getApppic())){
					Util.Getbitmap(ima_rem3, recommend.get(5).getApppic());
				}else{
					Util.Getbitmap(ima_rem3, recommend.get(5).getThumb());
				}
				if(Util.IsNull(recommend.get(6).getApppic())){
					Util.Getbitmap(ima_rem4, recommend.get(6).getApppic());
				}else{
					Util.Getbitmap(ima_rem4, recommend.get(6).getThumb());
				}
				if(Util.IsNull(recommend.get(3).getSubtitle())){
					//					tv_detaile1.setVisibility(View.VISIBLE);
					tv_detaile1.setText(recommend.get(3).getSubtitle());
				}
				if(Util.IsNull(recommend.get(4).getSubtitle())){
					//					tv_detaile2.setVisibility(View.VISIBLE);
					tv_detaile2.setText(recommend.get(4).getSubtitle());
				}
				if(Util.IsNull(recommend.get(5).getSubtitle())){
					//					tv_detaile3.setVisibility(View.VISIBLE);
					tv_detaile3.setText(recommend.get(5).getSubtitle());
				} 
				if(Util.IsNull(recommend.get(6).getSubtitle())){
					//					tv_detaile4.setVisibility(View.VISIBLE);
					tv_detaile4.setText(recommend.get(6).getSubtitle());
				}
				tv_title1.setText(recommend.get(3).getTitle());
				tv_title2.setText(recommend.get(4).getTitle());
				tv_title3.setText(recommend.get(5).getTitle());
				tv_title4.setText(recommend.get(6).getTitle());
			}
		}

		if(cat!=null){
			if(cat.size()>0){

				for(int i=0;i<cat.size();i++){
					if("饲料".equals(cat.get(i).getName())){
						if(siliaoAdapter!=null){
							siliaoAdapter.setProductBean(cat.get(i).getData());
							siliaoAdapter.notifyDataSetChanged();
						}else{
							siliaoAdapter=new HomeGridAdapter(context,cat.get(i).getData());
							gv_siliao.setAdapter(siliaoAdapter);
						}
						siliao=cat.get(i).getData();
					}else if("兽药".equals(cat.get(i).getName())){
						if(shouyaoAdapter!=null){
							shouyaoAdapter.setProductBean(cat.get(i).getData());
							shouyaoAdapter.notifyDataSetChanged();
						}else{
							shouyaoAdapter=new HomeGridAdapter(context,cat.get(i).getData());
							gv_shouyao.setAdapter(shouyaoAdapter);
						}
						shouyao=cat.get(i).getData();
					}else if("养殖设备".equals(cat.get(i).getName())){
						if(yzsbAdapter!=null){
							yzsbAdapter.setProductBean(cat.get(i).getData());
							yzsbAdapter.notifyDataSetChanged();
						}else{
							yzsbAdapter=new HomeGridAdapter(context,cat.get(i).getData());
							gv_yzsb.setAdapter(yzsbAdapter);
						}
						yzsb=cat.get(i).getData();
					}else if("畜禽养殖".equals(cat.get(i).getName())){
						if(qxyzAdapter!=null){
							qxyzAdapter.setProductBean(cat.get(i).getData());
							qxyzAdapter.notifyDataSetChanged();
						}else{
							qxyzAdapter=new HomeGridAdapter(context,cat.get(i).getData());
							gv_qxyz.setAdapter(qxyzAdapter);
						}
						qxyz=cat.get(i).getData();
					}else if("添加剂".equals(cat.get(i).getName())){
						if(tianjiajiAdapter!=null){
							tianjiajiAdapter.setProductBean(cat.get(i).getData());
							tianjiajiAdapter.notifyDataSetChanged();
						}else{
							tianjiajiAdapter=new HomeGridAdapter(context,cat.get(i).getData());
							gv_tianjiaji.setAdapter(tianjiajiAdapter);
						}
						tianjiaji=cat.get(i).getData();
					}else if("饲料原料".equals(cat.get(i).getName())){
						if(yuanliaoAdapter!=null){
							yuanliaoAdapter.setProductBean(cat.get(i).getData());
							yuanliaoAdapter.notifyDataSetChanged();
						}else{
							yuanliaoAdapter=new HomeGridAdapter(context,cat.get(i).getData());
							gv_yuanliao.setAdapter(yuanliaoAdapter);
						}
						yuanliao=cat.get(i).getData();
					}
				}
			}
		}


	}



	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
			if((System.currentTimeMillis()-exitTime) > 2000){  
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
				exitTime = System.currentTimeMillis();   
			} else {
				finish();
				System.exit(0);
			}
			return true;   
		}
		return super.onKeyDown(keyCode, event);
	}
	@Override
	protected void onDestroy() {
		if(myPDT!=null){
			if(myPDT.getCustomDialog()!=null && myPDT.getCustomDialog().isShowing()){
				myPDT.getCustomDialog().dismiss();
			}
			myPDT=null;
		}
		super.onDestroy();
	}
}
