package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.umeng.analytics.MobclickAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.adapters.CouponsAdapter;
import com.xunbo.store.beans.CouponBean;
import com.xunbo.store.beans.ListCouponBean;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class CouponsActivity extends BaseActivity {
	private ListView list_coupons;
	
	private Context context;
	private ArrayList<CouponBean> list;
	private Intent intent;
	private CouponsAdapter couponsAdapter;
	private LinearLayout ll_isno;
	private ThreadWithProgressDialog myPDT;
	private ListCouponBean listCouponBean;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		//topRightLVisible();
		//topRightRVisible();
		topRightTGone();
		//rl_l = (RelativeLayout) getTopLightRl();
	//	rl_r = (RelativeLayout) getTopRightRl();
		/*iv_top_l = (ImageView) getTopLightView();
		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);*/
		//iv_top_t = (ImageView) getTopRightView();
		//iv_top_t.setBackgroundResource(R.drawable.top_xx_bg);
		setTitleTxt(R.string.coupons_title);
		setContentXml(R.layout.coupons);
		init();
		//addlistener();
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}

	@SuppressWarnings("unused")
	private void addlistener() {
//		rl_r.setOnClickListener(new OnClickListener() {
//
//			@Override	
//			public void onClick(View v) {
//				intent = new Intent(context,XinjianActivity.class);
//				intent.putExtra("flag", "qianbao");
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(intent);
//			}
//		});
		
	}
	void init(){
		TestinAgent.init(this);
		myPDT=new ThreadWithProgressDialog();
		ll_isno=(LinearLayout)findViewById(R.id.coupons_ll_isnull);
		list_coupons=(ListView)findViewById(R.id.coupons_list);
		
		
		
	}
	

	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("CouponsActivity"); 
		MobclickAgent.onPause(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("CouponsActivity"); 
		MobclickAgent.onResume(this);
	}
	public class RefeshData implements ThreadWithProgressDialogTask {
		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			//				Toast.makeText(context, "cancle", 1000).show();
			return false; 
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
				if(listCouponBean!=null){
					String code=listCouponBean.getCode();
					String m=listCouponBean.getMsg();
					if("200".equals(code)){
						list=listCouponBean.getList();
						if(list!=null){
							if(list.size()>0){
								ll_isno.setVisibility(View.GONE);
								list_coupons.setVisibility(View.VISIBLE);
								couponsAdapter=new CouponsAdapter(context,list);
								list_coupons.setAdapter(couponsAdapter);
							}else{
								ll_isno.setVisibility(View.VISIBLE);
								list_coupons.setVisibility(View.GONE);
							}
						}
						
						
					}else if("300".equals(code)){
						intent=new Intent(context,LoginActivity.class);
						startActivity(intent);
						finish();
					}else{
						Util.ShowToast(context, m);
					}
					
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			
			
			return true;
		}



		@Override
		public boolean TaskMain() {
//			ListBanksBean getBanksList
			Send s=new Send(context);
			listCouponBean=s.getCouponlist(MyApplication.mp.getUser().getAuthstr());
			return true;
		}
	}

}
