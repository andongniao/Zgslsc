package com.xunbo.store.activitys;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
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
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.adapters.StoreAdapter;
import com.xunbo.store.adapters.StoreFenleiAdapter;
import com.xunbo.store.beans.ListShopHomeBean;
import com.xunbo.store.beans.ListStoreCatBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.myviews.CircleImageView;
import com.xunbo.store.myviews.MyGridView;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.tools.Util;

public class StoreActivity extends Activity implements OnClickListener{
	protected int activityCloseEnterAnimation;

	protected int activityCloseExitAnimation;
	private Context context;
	private LinearLayout ll_addview;
	private RelativeLayout rl_l;
	private View v_fenlei,v_home,v_result;
	private LayoutInflater inflater;
	private TextView tv_h_title,tv_h_name,tv_h_comment,tv_h_number,
	tv_h_miaoshu,tv_h_service,tv_h_wuliu,tv_result_title
	,tv_pro_name,tv_price,tv_unit,tv_more,tv_online;
	private ImageView iv_home_shoucang,iv_home_head,iv_rem;
	private ScrollView home_sc;
	private List<org.osgi.framework.Bundle> bundles=null;
	private int showstatu,type;
	private Intent intent;
	public static boolean isread;
	private MyGridView gv_home,gv_result;
	private AddInterface addInterface;
	private ArrayList<ProductBean> list;
	private StoreAdapter home_adapter;
	private  ListView fenlei_lv;
	private StoreFenleiAdapter fenleiadapter;
	private ThreadWithProgressDialog myPDT;
	private int page,index,len,w,h;
	private String authstr,storeid,storename,url;
	private ListShopHomeBean bean;
	private CircleImageView hean_iv;
	private ArrayList<ProductBean> list_pro,list_rem;
	private ListStoreCatBean catbean;
	private PopupWindow popupWindow;
	private boolean isshow;
	private View v;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});
		int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);      
		activityStyle.recycle();
		activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
		activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
		activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
		activityStyle.recycle();
		setContentView(R.layout.store_base_layout);
		init();
		addlistener();
		
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}

	private void init() {
		TestinAgent.init(this);
		myPDT = new ThreadWithProgressDialog();
		context = this;
		addInterface = new AddInterface() {

			@Override
			public void Add2Shopcart(int index) {
				//TODO 
			}
		};
		page = 1;
		index = 0;
		storeid = getIntent().getStringExtra("storeid");
		storename = getIntent().getStringExtra("storename");
		url = getIntent().getStringExtra("url");
		authstr = MyApplication.mp.getUser().getAuthstr();
		showstatu = 1;
		type = 1;
		ll_addview = (LinearLayout) findViewById(R.id.store_base_ll_addview);
		findViewById(R.id.sotre_base_tv_fenlei).setOnClickListener(this);
		tv_online = (TextView) findViewById(R.id.sotre_base_tv_online);
		tv_online.setOnClickListener(this);
		findViewById(R.id.sotre_base_tv_call).setOnClickListener(this);
		findViewById(R.id.store_base_ll_back).setOnClickListener(this);



		inflater = LayoutInflater.from(context);
		v_home = inflater.inflate(R.layout.store_home_layout, null);
		v_fenlei = inflater.inflate(R.layout.store_fenlei_layout, null);
		v_result = inflater.inflate(R.layout.store_result_layout, null);
		ll_addview.addView(v_home);
		ll_addview.addView(v_fenlei);
		ll_addview.addView(v_result);
		v_home.findViewById(R.id.store_home_iv_left).setOnClickListener(this);
		v_home.findViewById(R.id.store_home_iv_right).setOnClickListener(this);
		gv_home = (MyGridView) v_home.findViewById(R.id.store_home_gv);
		tv_h_title = (TextView) v_home.findViewById(R.id.store_home_tv_title);
		tv_h_name = (TextView) v_home.findViewById(R.id.store_home_tv_name);
		tv_h_comment = (TextView) v_home.findViewById(R.id.store_home_tv_comment);
		tv_h_number = (TextView) v_home.findViewById(R.id.store_home_tv_number);
		tv_h_miaoshu = (TextView) v_home.findViewById(R.id.store_home_tv_miaoshu);
		tv_h_service = (TextView) v_home.findViewById(R.id.store_home_tv_service);
		tv_h_wuliu = (TextView) v_home.findViewById(R.id.store_home_tv_wuliu);
		tv_price = (TextView) v_home.findViewById(R.id.store_home_tv_price);
		tv_more = (TextView) v_home.findViewById(R.id.store_home_tv_more);
		tv_more.setOnClickListener(this);
		tv_pro_name = (TextView) v_home.findViewById(R.id.store_home_tv_pro_name);
		tv_unit = (TextView) v_home.findViewById(R.id.store_home_tv_danwei);
		iv_rem = (ImageView) v_home.findViewById(R.id.store_home_iv_title);


		home_sc = (ScrollView) v_home.findViewById(R.id.store_home_sc);
		hean_iv = (CircleImageView) v_home.findViewById(R.id.mycenter_home_civ_head);
		v_home.findViewById(R.id.store_home_ll_top_pro).setOnClickListener(this);


		fenlei_lv = (ListView) v_fenlei.findViewById(R.id.store_fenlei_lv); 
	

		gv_result = (MyGridView) v_result.findViewById(R.id.store_result_gv);
		tv_result_title = (TextView) v_result.findViewById(R.id.store_result_tv_title);
		
		isshow = false;
		v = new ImageView(context);
		v.setBackgroundResource(R.drawable.base_to_top);
		popupWindow = new PopupWindow(v, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		DisplayMetrics  dm = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(dm);  
		w = dm.widthPixels;  
		h = dm.heightPixels; 




		/*************************/
		list = new  ArrayList<ProductBean>();
		ProductBean p1 = new ProductBean();
		list.add(p1);
		ProductBean p2 = new ProductBean();
		list.add(p2);
		ProductBean p3 = new ProductBean();
		list.add(p3);
		ProductBean p4 = new ProductBean();
		list.add(p4);
		Util.SetRedNum(context, rl_l, 1);
		type=1;
		showView(type);
		gv_result.setAdapter(home_adapter);

	}



	private void addlistener() {
		tv_result_title.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				type = 1;
				showView(type);
			}
		});
		gv_home.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toproduct(list_pro.get(arg2));
			}
		});
		fenlei_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				type = 3;
				showView(type);
				//TODO
			}
		});
		gv_result.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				Toproduct();
			}
		});
		home_sc.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_MOVE){
					if(!isshow){
						if(home_sc.getScrollY()>100){
							popupWindow.showAtLocation(tv_online, Gravity.BOTTOM,w/2-20, 120);
							isshow = true;
						}
					}else{
						if(home_sc.getScrollY()<=100){
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
					home_sc.scrollTo(10, 10);
					isshow = false;
				}
			}
		});
		

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.store_home_ll_top_pro:
			Toproduct(list_rem.get(index));
			break;
		case R.id.sotre_base_tv_fenlei:
			type = 2;
			showView(type);
			if(catbean==null){
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(),R.string.loding);
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			
			
			break;
		case R.id.sotre_base_tv_online:
//			//已安装插件列表
//			bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
//			BundleContext context =MyApplication.frame.getSystemBundleContext();
//			for(int i=0;i<context.getBundles().length;i++)
//			{
//				//获取已安装插件
//				bundles.add(context.getBundles()[i]);        	        
//			}
//
//			//			BundleContext context =frame.getSystemBundleContext();
//			startor(bundles);
			break;
		case R.id.sotre_base_tv_call:
			ShowDialog();
			break;
			//		case R.id.store_base_ll_back:
			//			intent = new Intent(this,XinjianActivity.class);
			//			break;
		case R.id.store_base_ll_back:
			if(showstatu==1){
				//				ExampleActivity.setCurrentTab(3);
				finish();
			}else{
				type=1;
				showView(type);
			}
			break;

		case R.id.store_home_iv_left:
			if(len>0){
				if(index==0){
					index=len-1;
				}else{
					index-=1;
				}
				showre();
			}
			break;
		case R.id.store_home_iv_right:
			if(len>0){
				if(index==len-1){
					index=0;
				}else{
					index+=1;
				}
				showre();
			}
			break;
		case R.id.store_home_tv_more:
			page+=1;
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(),R.string.loding);
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			break;
			
			//		case R.id.sotre_base_ll_back:
			//			finish();
			//			break;


		}
	}
	public void startor(List<org.osgi.framework.Bundle> list){
		org.osgi.framework.Bundle bundle=list.get(1);
		if(bundle.getState()!=bundle.ACTIVE){
			//判断插件是否已启动
			try {
				bundle.start();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(bundle.getBundleActivity()!=null){
			Intent i=new Intent();
			i.setClassName(context, bundle.getBundleActivity().split(",")[0]);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}else{

			Toast.makeText(context, "该插件没有配置BundleActivity",
					Toast.LENGTH_SHORT).show();
		}
	}
	private void showView(int i) {
		showstatu = i;
		switch (i) {
		case 1:
			v_home.setVisibility(View.VISIBLE);
			v_fenlei.setVisibility(View.GONE);
			v_result.setVisibility(View.GONE);
			break;
		case 2:
			v_home.setVisibility(View.GONE);
			v_fenlei.setVisibility(View.VISIBLE);
			v_result.setVisibility(View.GONE);
			break;
		case 3:
			v_home.setVisibility(View.GONE);
			v_fenlei.setVisibility(View.GONE);
			v_result.setVisibility(View.VISIBLE);
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
	public interface AddInterface{
		void Add2Shopcart(int index);
	}
	private void ShowDialog() {
		AlertDialog alertDialog;
		String title = "";
		title = "是否拨打电话?";
		alertDialog = new AlertDialog.Builder(this)
		.setTitle(title)
		.setIcon(null)
		.setPositiveButton("取消",
				new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog,
					int which) {
			}
		})
		.setNegativeButton("确定",
				new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog,
					int which) {
				Intent phoneIntent = new Intent(
						"android.intent.action.CALL",
						Uri.parse("tel:"
								+ 86666666));
				startActivity(phoneIntent);
				//					Uri uri = Uri.parse("smsto:"
				//							+ MyApplication.smsnumber);
				//					Intent ii = new Intent(
				//							Intent.ACTION_SENDTO, uri);
				//					ii.putExtra("sms_body", "");
				//					startActivity(ii);
			}
		}).create();
		alertDialog.show();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
			if(showstatu==1){
				//				ExampleActivity.setCurrentTab(3);
				finish();
			}else{
				type = 1;
				showView(type);
			}
			return true;   
		}
		return super.onKeyDown(keyCode, event);
	}



	@Override
	public void finish() {
		super.finish();
		overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
	}




	public class RefeshData implements ThreadWithProgressDialogTask {
		public RefeshData(){
		}

		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub
			PostHttp p=new PostHttp(context);
			if(type==1){
				if(Util.IsNull(storeid)){
					bean = p.getShopHomeData(storeid,1,page);
				}else{
					bean = p.getShopHomeData(storename,2,page);
				}
			}else if(type==2){
				catbean = p.getShopCat(storeid, "");
			}
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			// TODO Auto-generated method stub
			if(page!=1){
				page-=1;
			}
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			if(type==1){
			if(bean!=null){
				if("200".equals(bean.getCode())){
					tv_more.setVisibility(View.VISIBLE);
					if(page==1){
					Util.Getbitmap(hean_iv,url);
					list_pro = bean.getList();
					home_adapter = new StoreAdapter(context, list_pro);
					gv_home.setAdapter(home_adapter);
					gv_home.setFocusable(false);
					tv_h_comment.setText(""+bean.getShopInfoBean().getGrade());
					tv_h_miaoshu.setText(""+bean.getShopInfoBean().getDescribe());
					tv_h_service.setText(""+bean.getShopInfoBean().getService());
					tv_h_wuliu.setText(""+bean.getShopInfoBean().getLogistics());
					list_rem = bean.getRecommend();
					len = list_rem.size();
					if(len>0){
						
					showre();	
					}
					home_sc.scrollTo(0, 1);
					}else{
						if(bean.getList().size()>0){
						list_pro.addAll(bean.getList());
						if(home_adapter==null){
						home_adapter = new StoreAdapter(context, list_pro);
						gv_home.setAdapter(home_adapter);
						}else{
							home_adapter.setData(list_pro);
							home_adapter.notifyDataSetChanged();
						}
						}else{
							Util.ShowToast(context, R.string.page_is_final);
						}
//						gv_home.setFocusable(false);
					}

				}else if("300".equals(bean.getCode())){
					MyApplication.mp.setlogin(false);
					Util.ShowToast(context, R.string.login_out_time);
					intent = new Intent(context,LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish(); 
				}else{
					if(page!=1){
						page-=1;
					}
					Util.ShowToast(context, bean.getMsg());
				}

			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			}else if(type==2){
				if(catbean!=null){
					if("200".equals(catbean.getCode())){
						if(catbean.getList().size()>0){
						fenleiadapter = new StoreFenleiAdapter(context, catbean.getList());
						fenlei_lv.setAdapter(fenleiadapter);
						}else{
							Util.ShowToast(context, "商家尚未定义分类");
						}
					}else if("300".equals(catbean.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						intent = new Intent(context,LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish(); 
					}else{
						Util.ShowToast(context, catbean.getMsg());
					}

				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			return true;

		}

	}
	private void showre(){
		tv_pro_name.setText(list_rem.get(index).getTitle());
		tv_price.setText("￥"+list_rem.get(index).getPrice());
		tv_unit.setText(list_rem.get(index).getUnit());
		Util.Getbitmap(iv_rem, list_rem.get(index).getThumb());
	}

	private void Toproduct(ProductBean bean){
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//intent.putExtra("productdetaile", value);
		Bundle b=new Bundle();
		b.putSerializable("product", bean);
		intent.putExtra("productbundle", b);
		startActivity(intent);
	}

}
