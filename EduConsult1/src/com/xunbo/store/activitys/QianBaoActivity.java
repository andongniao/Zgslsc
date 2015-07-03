package com.xunbo.store.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.testin.agent.TestinAgent;
import com.xunbo.store.ExampleActivity;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.beans.MoneyBagBean;
import com.xunbo.store.beans.MoneyDetaileBean;
import com.xunbo.store.beans.UserBean;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class QianBaoActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_chaxun,ll_tixian;
	private TextView tv_all,tv_keyong,tv_tixian;
	private Context context;
	private Intent intent;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	public static boolean isread;
	private UserBean bean;
	private MoneyDetaileBean moneybean;
	private ThreadWithProgressDialog myPDT;
	private MoneyBagBean bagBean;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//		topRightLVisible();
		topRightTGone();
		rl_l = (RelativeLayout) getTopLightRl();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_l = (ImageView) getTopLightView();
		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		setTitleTxt(R.string.qignbao_title);
		setContentXml(R.layout.qianbao);
		init();
		addlistener();
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}

	private void addlistener() {
//		rl_l.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				intent = new Intent(context,XinjianActivity.class);
//				intent.putExtra("flag", "qianbao");
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(intent);
//			}
//		});
		rl_r.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ExampleActivity.setCurrentTab(0);
				finish();
			}
		});
	}
	private void init() {
		TestinAgent.init(this);
		context = this;
		myPDT = new ThreadWithProgressDialog();
		String  msg = getResources().getString(R.string.loding);
		bean = MyApplication.mp.getUser();
		ll_chaxun = (LinearLayout) findViewById(R.id.qianbao_ll_chaxun);
		ll_chaxun.setOnClickListener(this);
		ll_tixian = (LinearLayout) findViewById(R.id.qianbao_ll_tixian);
		ll_tixian.setOnClickListener(this);
		tv_all = (TextView) findViewById(R.id.qianbao_tv_zonge);
		tv_keyong = (TextView) findViewById(R.id.qianbao_tv_keyong);
		tv_tixian = (TextView) findViewById(R.id.qianbao_tv_tixian);
		//		Util.SetRedNum(context, rl_l, 1);
	}


	@Override
	public void onClick(View v) {
		//Intent intent;
		switch (v.getId()) {
		case R.id.qianbao_ll_chaxun:
						intent = new Intent(this,MoneyQueryActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
			break;
		case R.id.qianbao_ll_tixian:
						intent = new Intent(this,MoneyWithdrawalActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
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
			//				Toast.makeText(context, "cancle", 1000).show();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(bagBean!=null){
				if("200".equals(bagBean.getCode())){
					tv_all.setText("￥"+bagBean.getAll_money());
					tv_keyong.setText("￥"+bagBean.getMoney());
					tv_tixian.setText("￥"+bagBean.getAmount());

				}else if("300".equals(bagBean.getCode())){
					MyApplication.mp.setlogin(false);
					Util.ShowToast(context, R.string.login_out_time);
					Intent i= new Intent(context,LoginActivity.class);
					startActivity(i);
					finish();
				}else{

				}
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}


			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			Send s = new Send(context);
			bagBean = s.getMoney(MyApplication.money_home, MyApplication.mp.getUser().getAuthstr());

			return true;
		}
	}

}
