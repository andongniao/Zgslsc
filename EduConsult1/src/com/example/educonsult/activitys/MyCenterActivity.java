package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.baidu.autoupdatesdk.BDAutoUpdateSDK;
import com.baidu.autoupdatesdk.UICheckUpdateCallback;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.umeng.analytics.MobclickAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.beans.CenterCountBean;
import com.xunbo.store.beans.CenterUserBean;
import com.xunbo.store.beans.ListProductBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.beans.UserBean;
import com.xunbo.store.myviews.BadgeView;
import com.xunbo.store.myviews.CircleImageView;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;


public class MyCenterActivity extends BaseActivity implements OnClickListener{
	private long exitTime = 0;
	private Context context;
	private Intent intent;
	@SuppressWarnings("unused")
	private LinearLayout ll_zhifu,ll_cp,ll_mima,ll_dp,ll_zj,ll_zf,ll_fh,ll_sh,ll_pj,ll_order,ll_address,
	ll_qianbao,ll_xinjian,ll_jifen,ll_youhuiquan,ll_fenxiang,ll_fuwu,ll_update,ll_tuijian,ll_logout
	,ll_ll_fahuo,ll_ll_shouhou,ll_ll_shouhuo,ll_ll_pingjia;
	@SuppressWarnings("unused")
	private ImageView iv_zhifu,iv_fahuo,iv_shouhuo,iv_pingjia;
	private CircleImageView icv_head;
	private TextView tv_version,tv_name,tv_cp,tv_dp;
	private UserBean bean;
	private CenterUserBean cbean;
	private CenterCountBean countbean;
	private ThreadWithProgressDialog myPDT;
	@SuppressWarnings("unused")
	private String msg;
	private String zjnum;
	private Util u;
	private ListProductBean listProductBean;
	private ArrayList<ProductBean> productBeans;
	private TextView tv_zuji;
	private ProgressDialog dialog;
	private int inittype;
	public  static boolean ischanged;
	private BadgeView badge1,badge2,badge3,badge4;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		goneTopLeft();
		topRightTGone();
		setTitleTxt(R.string.mycenger_title);
		setContentXml(R.layout.mycenter);
		init();
	}

	private void init() {
		TestinAgent.init(this);
		ischanged = false;
		context = this;
		u=new Util(context);
		dialog = new ProgressDialog(this);
		dialog.setIndeterminate(true);
		listProductBean=(ListProductBean)u.readObject(MyApplication.Seejilu);
		if(listProductBean==null){
			listProductBean=new ListProductBean();
			productBeans=new ArrayList<ProductBean>();
			zjnum="0";
		}else{

			productBeans=listProductBean.getList();
			zjnum=productBeans.size()+"";
		}
		tv_version = (TextView) findViewById(R.id.mycenter_home_tv_version);
		tv_name = (TextView) findViewById(R.id.mycenter_home_tv_name);
		icv_head = (CircleImageView) findViewById(R.id.mycenter_home_civ_head);
		icv_head.setOnClickListener(this);
		iv_zhifu = (ImageView) findViewById(R.id.mycenter_home_btn_zhifu);
		iv_fahuo = (ImageView) findViewById(R.id.mycenter_home_btn_fahuo);
		iv_shouhuo = (ImageView) findViewById(R.id.mycenter_home_btn_shouhuo);
		iv_pingjia = (ImageView) findViewById(R.id.mycenter_home_btn_pingjia);
		ll_cp = (LinearLayout) findViewById(R.id.mycenter_home_ll_cp);
		ll_cp.setOnClickListener(this);
		ll_dp = (LinearLayout) findViewById(R.id.mycenter_home_ll_dp);
		ll_dp.setOnClickListener(this);
		ll_zj = (LinearLayout) findViewById(R.id.mycenter_home_ll_zj);
		ll_zj.setOnClickListener(this);
		ll_zf = (LinearLayout) findViewById(R.id.mycenter_home_ll_zhifu);
		ll_zf.setOnClickListener(this);
		ll_fh = (LinearLayout) findViewById(R.id.mycenter_home_ll_fahuuo);
		ll_fh.setOnClickListener(this);
		ll_fh.setVisibility(View.GONE);
		ll_sh = (LinearLayout) findViewById(R.id.mycenter_home_ll_shouhuo);
		ll_sh.setOnClickListener(this);
		ll_pj = (LinearLayout) findViewById(R.id.mycenter_home_ll_pingjia);
		ll_pj.setOnClickListener(this);
		ll_logout = (LinearLayout) findViewById(R.id.mycenter_home_ll_logout);
		ll_logout.setOnClickListener(this);
		findViewById(R.id.mycenter_home_ll_shouhou).setOnClickListener(this);
		ll_order = (LinearLayout) findViewById(R.id.mycenter_home_ll_order);
		ll_order.setOnClickListener(this);
		ll_address = (LinearLayout) findViewById(R.id.mycenter_home_ll_address);
		ll_address.setOnClickListener(this);
		ll_qianbao = (LinearLayout) findViewById(R.id.mycenter_home_ll_qianbao);
		ll_qianbao.setOnClickListener(this);
		ll_xinjian = (LinearLayout) findViewById(R.id.mycenter_home_ll_xinjian);
		ll_xinjian.setOnClickListener(this);
		ll_jifen = (LinearLayout) findViewById(R.id.mycenter_home_ll_jifen);
		ll_jifen.setVisibility(View.GONE);
		ll_jifen.setOnClickListener(this);
		ll_fenxiang = (LinearLayout) findViewById(R.id.mycenter_home_ll_fenxiang);
		ll_fenxiang.setOnClickListener(this);
		ll_fenxiang.setVisibility(View.GONE);
		ll_fuwu = (LinearLayout) findViewById(R.id.mycenter_home_ll_fuwu);
		ll_fuwu.setOnClickListener(this);
		ll_update = (LinearLayout) findViewById(R.id.mycenter_home_ll_update);
		ll_update.setOnClickListener(this);
		ll_tuijian = (LinearLayout) findViewById(R.id.mycenter_home_ll_tuijian);
		ll_tuijian.setOnClickListener(this);
		ll_youhuiquan = (LinearLayout) findViewById(R.id.myinfo_ll_youhuiquan);
		ll_youhuiquan.setOnClickListener(this);
				ll_youhuiquan.setVisibility(View.GONE);
		ll_mima = (LinearLayout) findViewById(R.id.mycenter_home_ll_mima);
		//		ll_mima.setVisibility(View.GONE);
		ll_mima.setOnClickListener(this);
		ll_zhifu=(LinearLayout)findViewById(R.id.mycenter_home_btn_zhifu_lin);
		ll_ll_fahuo=(LinearLayout)findViewById(R.id.mycenter_home_btn_fahuo_lin);
		ll_ll_pingjia=(LinearLayout)findViewById(R.id.mycenter_home_btn_pingjia_lin);
		ll_ll_shouhou=(LinearLayout)findViewById(R.id.mycenter_home_btn_shouhou_lin);
		ll_ll_shouhuo=(LinearLayout)findViewById(R.id.mycenter_home_btn_shouhuo_lin);

		tv_zuji=(TextView)findViewById(R.id.mycenter_home_tv_zj);
		tv_zuji.setText(zjnum);
		tv_cp=(TextView)findViewById(R.id.mycenter_home_tv_cp);
		tv_dp=(TextView)findViewById(R.id.mycenter_home_tv_dp);

		PackageManager manager;
		PackageInfo info = null;
		manager = this.getPackageManager();
		try {
			info = manager.getPackageInfo(this.getPackageName(), 0);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String vserion = info.versionName;
		tv_version.setText(vserion);
		bean=MyApplication.mp.getUser();
		inittype = 1;
		myPDT = new ThreadWithProgressDialog();
		msg = "加载中...";
		if(MyApplication.mp.islogin){
			if(Util.detect(context)){
				//			myPDT.Run(context, new RefeshData(bean.getType(),bean.getAuthstr()),msg,false);//不可取消
				myPDT.Run(context, new RefeshData(bean.getType(),bean.getAuthstr()),R.string.loding);//不可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
		}else{
			Intent i = new Intent(context,LoginActivity.class);
			startActivity(i);
			finish();
		}
	}
	public class RefeshData implements ThreadWithProgressDialogTask {
		private int type;
		private String authstr;
		public RefeshData(int type,String authstr){
			this.type=type;
			this.authstr=authstr;
		}

		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub
			if(inittype==1){
				Send s=new Send(context);
				PostHttp p=new PostHttp(context);
				countbean = p.getCenterCount(authstr);
				if(countbean!=null){
					if("200".equals(countbean.getCode())){
						cbean=s.getMyinfo(type, authstr);
					}else if("300".equals(countbean.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						intent = new Intent(context,LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish(); 
					}else{
						Util.ShowToast(context, countbean.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				
			}

			return true;
		}

		@Override
		public boolean OnTaskDismissed() {	
			// TODO Auto-generated method stub
			inittype = 1;
			return false;
		}

		@SuppressWarnings("unused")
		@Override
		public boolean OnTaskDone() {
			if(inittype==2){
				BDAutoUpdateSDK.uiUpdateAction(context, new MyUICheckUpdateCallback());
			}else if(inittype==1){
				int num=0;
				if(countbean!=null && "200".equals(countbean.getCode())){
					if(cbean!=null){
						String code = cbean.getCode();
						String m = cbean.getMsg();
						if("200".equals(code)){
							MyApplication.mp.setCenterUserBean(cbean);
							Util.Getbitmap(icv_head, cbean.getImg());
							tv_name.setText(cbean.getTruename());
							initDate();
						}else if("300".equals(code)){
							MyApplication.mp.setlogin(false);
							Util.ShowToast(context, R.string.login_out_time);
							intent = new Intent(context,LoginActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							finish(); 
						}else{
							if(Util.IsNull(m)){
								Util.ShowToast(context, m);
							}
						}	
					}else{
						num=1;
						Util.ShowToast(context, R.string.net_is_eor);
					}
				}
			}
			return true;

		}

	}
	private void initDate(){
		tv_cp.setText(countbean.getProduct());
		tv_dp.setText(countbean.getShop());
		if(!"0".equals(countbean.getPaying())){
			int num = Integer.parseInt(countbean.getPaying());
			if(badge1!=null){
				badge1.toggle();
			}
			badge1 = new BadgeView(context, ll_zhifu);
			badge1.setText(""+num);
			badge1.show();
		}else{
			if(badge1!=null){
				badge1.toggle();
			}
		}
		if(!"0".equals(countbean.getPingjia())){
			int num = Integer.parseInt(countbean.getPingjia());
			if(badge2!=null){
				badge2.toggle();
			}
			badge2 = new BadgeView(context, ll_ll_pingjia);
			badge2.setText(""+num);
			badge2.show();
		}else{
			if(badge2!=null){
				badge2.toggle();
			}
		}
		if(!"0".equals(countbean.getReceiving())){

			int num = Integer.parseInt(countbean.getReceiving());
			if(badge3!=null){
				badge3.toggle();
			}
			badge3 = new BadgeView(context, ll_ll_shouhou);
			badge3.setText(""+num);
			badge3.show();
		}else{
			if(badge3!=null){
				badge3.toggle();
			}
		}
		if(!"0".equals(countbean.getTuikuan())){

			int num = Integer.parseInt(countbean.getTuikuan());
			if(badge4!=null){
				badge4.toggle();
			}
			badge4 = new BadgeView(context, ll_ll_shouhuo);
			badge4.setText(""+num);
			badge4.show();
			
		}else{
			if(badge4!=null){
				badge4.toggle();
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mycenter_home_civ_head:
			if(cbean!=null){
				if("200".equals(cbean.getCode())){
					intent = new Intent(context,MyInfoActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
			}
			break;
		case R.id.mycenter_home_ll_cp:
			intent = new Intent(context,SCProductActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_dp:
			intent = new Intent(context,SCStoreActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.myinfo_ll_youhuiquan:
			intent = new Intent(context,CouponsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_zj:
			MyZjActivity.isfinish=false;
			intent = new Intent(context,MyZjActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			//			Util.ShowToast(context, R.string.maimeng);
			break;
		case R.id.mycenter_home_ll_zhifu:
			intent = new Intent(context,MyOrderActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("index", 1);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_fahuuo:
			intent = new Intent(context,MyOrderActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("index", 2);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_shouhuo:
			intent = new Intent(context,MyOrderActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("index", 2);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_pingjia:
			intent = new Intent(context,MyOrderActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("index", 3);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_order:
			intent = new Intent(context,MyOrderActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_address:
			intent = new Intent(context,AddressActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_qianbao:
			intent = new Intent(context,QianBaoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_mima:
			intent = new Intent(context,PasswordActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_xinjian:
			intent = new Intent(context,XinjianActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_jifen:
			break;
		case R.id.mycenter_home_ll_fenxiang:
			break;
		case R.id.mycenter_home_ll_fuwu:
			intent = new Intent(context,ServiceCenterActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_update:
			//			dialog.show();
			inittype=2;
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(bean.getType(),bean.getAuthstr()),R.string.loding);//不可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			break;
		case R.id.mycenter_home_ll_tuijian:
			intent = new Intent(context,MyCenterTuijianActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_shouhou:
			intent = new Intent(context,ApplyOrderActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_logout:
			MyApplication.mp.setlogin(false);
			intent = new Intent(context,LoginActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			finish();
			break;
		}
	}
	private class MyUICheckUpdateCallback implements UICheckUpdateCallback {

		@Override
		public void onCheckComplete() {
			inittype = 1;
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
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("center"); 
		MobclickAgent.onResume(this);
		listProductBean=(ListProductBean)u.readObject(MyApplication.Seejilu);
		if(listProductBean==null){
			listProductBean=new ListProductBean();
			productBeans=new ArrayList<ProductBean>();
			zjnum="0";
		}else{
			productBeans=listProductBean.getList();
			zjnum=productBeans.size()+"";
		}
		tv_zuji.setText(zjnum);
		inittype = 1;
		if(ischanged){
			if(Util.detect(context)){
				//			myPDT.Run(context, new RefeshData(bean.getType(),bean.getAuthstr()),msg,false);//不可取消
				myPDT.Run(context, new RefeshData(bean.getType(),bean.getAuthstr()),R.string.loding);//不可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			ischanged = false;
		}
	}
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("center"); 
		MobclickAgent.onPause(context);
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
