package com.xunbo.store.activitys;

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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.astuetz.PagerSlidingTabStrip;
import com.testin.agent.TestinAgent;
import com.xunbo.store.ExampleActivity;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.adapters.GqAdapter;
import com.xunbo.store.adapters.HomeRuzhuAdapter;
import com.xunbo.store.adapters.HomeSlidAdapter;
import com.xunbo.store.adapters.KnowFenleiAdapter;
import com.xunbo.store.beans.CompanyBean;
import com.xunbo.store.beans.FenleiBean;
import com.xunbo.store.beans.ListCompanyBean;
import com.xunbo.store.beans.ListFenleiBean;
import com.xunbo.store.beans.ListProductBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.myviews.MyGridView;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class GqTwoActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private ScrollView scrollView;
	private PopupWindow popupWindow;
	private boolean isshow;
	private HomeRuzhuAdapter adapter;
	//	private GridView gv_dh;
	private MyGridView gv_pp,gv_dh,gv_dh_xq;
	private ArrayList<CompanyBean>list;
	private PagerSlidingTabStrip tabs;
	private ViewPager pager;
	private LinearLayout add_ll,jp_ll;
	private TextView tv_b,tv_text,tv_l,tv_t,tv_r,util_l,util_t,util_r;
	private ArrayList<View> l;
	private int pos = 0;
	private GqAdapter gqAdapter;
	private Intent intent;
	private LayoutInflater inflater;
	public View ll_gqtwo_popu;
	private LinearLayout ll_jingxuan_l,ll_jingxuan_t,ll_jingxuan_r;
	//public LinearLayout ll_gqtwo_popu;
	private ImageView iv_top_l,iv_top_t,gqtwo_1,gqtwo_2,ima_l,ima_r,ima_t;
	private RelativeLayout rl_l,rl_r;
	public static boolean isread;
	private ThreadWithProgressDialog myPDT;
	private HomeSlidAdapter adapter_r;
	private KnowFenleiAdapter adapter_l;
	private ListFenleiBean listFenleiBean;
	private String filename=MyApplication.FenleiName;
	private ArrayList<FenleiBean> fenleilist, listchile;
	private Util u;
	private int page,num;
	private String text,gqtext;
	private ListProductBean listProductBean,ProductBeanJP;
	private ArrayList<ProductBean> ProductBeans,productBeansJP;
	private ListCompanyBean listCompanyBean;
	private ArrayList<CompanyBean> companyBeans;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		topRightLVisible();
		topRightRVisible();
		topRightTGone();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		String title = getIntent().getStringExtra("searchtext");
		pos=getIntent().getIntExtra("num",0);


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
				Toproduct(ProductBeans.get(arg2));
			}
		});

	}
	private void init() {
		TestinAgent.init(this);
		context = this;
		myPDT=new ThreadWithProgressDialog();
		
		
		intent=getIntent();
		text=intent.getStringExtra("searchtext");
		num=1;
		page=1;
		l = new ArrayList<View>();
		list = new ArrayList<CompanyBean>();
		scrollView = (ScrollView) findViewById(R.id.gq_two_sc);
		ll_jingxuan_l = (LinearLayout) findViewById(R.id.gq_two_ll_jingxuan_l);
		ll_jingxuan_l.setOnClickListener(this);
		ll_jingxuan_t = (LinearLayout) findViewById(R.id.gq_two_ll_jingxuan_t);
		ll_jingxuan_t.setOnClickListener(this);
		ll_jingxuan_r = (LinearLayout) findViewById(R.id.gq_two_ll_jingxuan_r);
		ll_jingxuan_r.setOnClickListener(this);
 
		tv_l=(TextView)findViewById(R.id.gq_two_tv_jx_l);
		util_l=(TextView)findViewById(R.id.gq_two_tv_util_l);
		tv_t=(TextView)findViewById(R.id.gq_two_tv_jx_t);
		util_t=(TextView)findViewById(R.id.gq_two_tv_util_t);
		tv_r=(TextView)findViewById(R.id.gq_two_tv_jx_r);
		util_r=(TextView)findViewById(R.id.gq_two_tv_util_r);
		ima_l=(ImageView)findViewById(R.id.gq_two_ima_jx_l);
		ima_r=(ImageView)findViewById(R.id.gq_two_ima_jx_r);
		ima_t=(ImageView)findViewById(R.id.gq_two_ima_jx_t);
		
		jp_ll=(LinearLayout)findViewById(R.id.gq_two_ll_jingxuan);
		add_ll = (LinearLayout) findViewById(R.id.gq_two_ll_addview);
		gv_pp = (MyGridView) findViewById(R.id.gq_two_gv_pp);
		
		gv_dh_xq = (MyGridView) findViewById(R.id.gq_two_gv_dh_xq);
		
		u=new Util(context);
		if(u.isExistDataCache(filename) && u.isReadDataCache(filename)){
			listFenleiBean=(ListFenleiBean)u.readObject(filename);
			fenleilist=listFenleiBean.getList();
			setFenleichild();
		}else{
				num=0;
		}
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(num),R.string.loding);//可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
		

	}
	private void setFenleichild(){
		for(int i=0;i<fenleilist.size();i++){
			if(fenleilist.get(i).getCatname().equals(text)){
				listchile=fenleilist.get(i).getChild();
			}
		}
		
		for(int i = 0;i<listchile.size();i++){
			View v = LayoutInflater.from(context).inflate(R.layout.gq_two_add_view, null);//new ImageView(context);
			tv_b = (TextView) v.findViewById(R.id.gq_two_add_tv_down);
			tv_text=(TextView)v.findViewById(R.id.gq_two_add_tv_title);
			tv_text.setText(listchile.get(i).getCatname());
			
			v.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			v.setOnClickListener(new MyOnClickListener(i));
			add_ll.addView(v);
			l.add(tv_b);
		}
		if(l.size()>0){
			l.get(pos).setBackgroundResource(R.color.orn);
			gqtext=listchile.get(pos).getCatname();
		}
	}
	class MyOnClickListener implements OnClickListener{
		private int x = 0;
		public MyOnClickListener(int x){
			this.x=x;
		}
		@Override
		public void onClick(View v) {
			gqtext=listchile.get(x).getCatname();
			if(pos==-1){
				l.get(x).setBackgroundResource(R.color.orn);
			}else{
				l.get(pos).setBackgroundResource(R.color.black);	
				l.get(x).setBackgroundResource(R.color.orn);
			}
			pos = x;
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(num),R.string.loding);//可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
		}

	}
	private void Toproduct(ProductBean bean){
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Bundle b=new Bundle();
		b.putSerializable("product", bean);
		intent.putExtra("productbundle", b);
		startActivity(intent);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.gq_two_ll_jingxuan_l:
//			Toproduct();
			break;
		case R.id.gq_two_ll_jingxuan_t:
//			Toproduct();
			break;
		case R.id.gq_two_ll_jingxuan_r:
//			Toproduct();
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
		private int num;
		public RefeshData(int num) {
			this.num=num;
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			finish();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			int toastnum = 0,toastnum2=0;
			//任务完成后
			
			if(num==0){
				if(listFenleiBean!=null){
					if("200".equals(listFenleiBean.getCode())){
						u.saveObject(listFenleiBean, filename);
						fenleilist=listFenleiBean.getList();
						setFenleichild();
					}else if("300".equals(listFenleiBean.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent i = new Intent(GqTwoActivity.this,LoginActivity.class);
						startActivity(i);
						finish();
					}else{
						
						Util.ShowToast(context, listFenleiBean.getMsg());
					}
				}else{
					toastnum=1;
					//Util.ShowToast(context, "初始化失败,请保证网络通畅后重试");
				}
				if(listProductBean!=null){
					if("200".equals(listProductBean.getCode())){
						//TODO	
						initDate();
					}else if("300".equals(listProductBean.getCode())){
						//TODO	
						Util.ShowToast(context,R.string.login_out_time);
						intent=new Intent(context, LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
					else{
						Util.ShowToast(context, listProductBean.getMsg());
					}
				}else{
//					if(toastnum!=1){
//					Util.ShowToast(context, R.string.net_is_eor);
//					}else{
						toastnum=1;
//					}
				}
				if(ProductBeanJP!=null){
					if("200".equals(ProductBeanJP.getCode())){
						//TODO	
						initDateJP();
					}else if("300".equals(ProductBeanJP.getCode())){
						//TODO	
						Util.ShowToast(context,R.string.login_out_time);
						intent=new Intent(context, LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
					else{
						Util.ShowToast(context, ProductBeanJP.getMsg());
					}	
				}else{
//					if(toastnum!=2){
//						
//					Util.ShowToast(context, R.string.net_is_eor);
//					}else {
						toastnum=1;
//					}
				}
				if(listCompanyBean!=null){
					if("200".equals(listCompanyBean.getCode())){
						//TODO	
						initDatePP();
					}else if("300".equals(listCompanyBean.getCode())){
						//TODO	
						Util.ShowToast(context,R.string.login_out_time);
						intent=new Intent(context, LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
					else{
						Util.ShowToast(context, listCompanyBean.getMsg());
					}	
				}else{
//					if(toastnum!=2){
//						
//					Util.ShowToast(context, R.string.net_is_eor);
//					}else {
						toastnum=1;
//					}
				}
				if(toastnum==1){
					Util.ShowToast(context, R.string.net_is_eor);
				}
				
			}
			if(num==1){
				if(listProductBean!=null){
					if("200".equals(listProductBean.getCode())){
						//TODO	
						initDate();
					}else if("300".equals(listProductBean.getCode())){
						//TODO	
						Util.ShowToast(context,R.string.login_out_time);
						intent=new Intent(context, LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
					else{
						toastnum2=1;
						//Util.ShowToast(context, listProductBean.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
				if(ProductBeanJP!=null){
					if("200".equals(ProductBeanJP.getCode())){
						//TODO	
						initDateJP();
					}else if("300".equals(ProductBeanJP.getCode())){
						//TODO	
						Util.ShowToast(context,R.string.login_out_time);
						intent=new Intent(context, LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
					else{
						Util.ShowToast(context, ProductBeanJP.getMsg());
					}	
				}else{
//					if(toastnum!=2){
//						
//					Util.ShowToast(context, R.string.net_is_eor);
//					}else {
						toastnum2=1;
//					}
				}
				if(listCompanyBean!=null){
					if("200".equals(listCompanyBean.getCode())){
						//TODO	
						initDatePP();
					}else if("300".equals(listCompanyBean.getCode())){
						//TODO	
						Util.ShowToast(context,R.string.login_out_time);
						intent=new Intent(context, LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}
					else{
						Util.ShowToast(context, listCompanyBean.getMsg());
					}	
				}else{
//					if(toastnum!=2){
//						
//					Util.ShowToast(context, R.string.net_is_eor);
//					}else {
						toastnum2=1;
//					}
				}
				if(toastnum2==1){
					Util.ShowToast(context, R.string.net_is_eor);
				}
				
			}
			return true;
		}

		@Override
		public boolean TaskMain() {
			Send s = new Send(context);
			if(num==0){
				listFenleiBean = s.GetFenlei();
				PostHttp p=new PostHttp(context);
				if(page==1){
					listProductBean=p.SeanchText(1, 0, 1, gqtext);
					ProductBeans = new ArrayList<ProductBean>();
					ProductBeans = 	listProductBean.getList();
				}else{
					ProductBeans = new ArrayList<ProductBean>();
					for(int in=1;in<page+1;in++){
						listProductBean=p.SeanchText(3, 0, in, gqtext);
						ProductBeans.addAll(listProductBean.getList());
					}
				}
			}
			else if(num==1){
				PostHttp p=new PostHttp(context);
				if(page==1){
					listProductBean=p.SeanchText(1, 0, 1, gqtext);
					ProductBeans = new ArrayList<ProductBean>();
					ProductBeans = 	listProductBean.getList();
				}else{
					ProductBeans = new ArrayList<ProductBean>();
					for(int in=1;in<page+1;in++){
						listProductBean=p.SeanchText(3, 0, in, gqtext);
						ProductBeans.addAll(listProductBean.getList());
					}
				}
			}
			ProductBeanJP=s.getGQRecommend();
			listCompanyBean=s.getGQbrand();
			return true;
		}
	}
	private void initDate(){
		gqAdapter = new GqAdapter(context, ProductBeans);
		gv_dh_xq.setAdapter(gqAdapter);
		gqAdapter.notifyDataSetChanged();
	}
	private void initDateJP(){
		productBeansJP=ProductBeanJP.getList();
		if(productBeansJP.size()>0){
			jp_ll.setVisibility(View.VISIBLE);
			Util.Getbitmap(ima_l, productBeansJP.get(0).getThumb());
			Util.Getbitmap(ima_r, productBeansJP.get(1).getThumb());
			Util.Getbitmap(ima_t, productBeansJP.get(2).getThumb());
			tv_l.setText(productBeansJP.get(0).getPrice());
			util_l.setText(productBeansJP.get(0).getUnit());
			tv_r.setText(productBeansJP.get(1).getPrice());
			util_r.setText(productBeansJP.get(1).getUnit());
			tv_t.setText(productBeansJP.get(2).getPrice());
			util_t.setText(productBeansJP.get(2).getUnit());
		}
	}
	private void initDatePP(){
		companyBeans=listCompanyBean.getList();
		adapter = new HomeRuzhuAdapter(context, companyBeans);
		gv_pp.setAdapter(adapter);
	}



}
