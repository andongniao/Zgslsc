package com.example.educonsult.activitys;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.xunbo.store.ExampleActivity;
import com.xunbo.store.tools.Util;

public class ServiceCenterActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_zx_regist,ll_zx_shangjia,ll_zx_buy,ll_zx_zhuanjia,
	ll_wt_chaxun,ll_wt_order,ll_wt_regist,ll_wt_wuliu,ll_wt_qita,
	ll_show_regist,ll_show_shangjia,ll_show_buy,ll_show_zhuanjia,ll_show_wenti;
	private boolean regist,shangjia,buy,zhuanjia,wenti;
	private TextView tv_zhuce_1_online,tv_zhuce_1_qq,tv_zhuce_2_online,tv_zhuce_2_qq,
	tv_shangjia_1_online,tv_shangjia_1_qq,tv_shangjia_2_online,tv_shangjia_2_qq,
	tv_buy_1_online,tv_buy_1_qq,tv_buy_2_online,tv_buy_2_qq,
	tv_zhuanjia_1_online,tv_zhuanjia_1_qq,tv_zhuanjia_2_online,tv_zhuanjia_2_qq;
	private Context context;
	private Intent intent;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	public static boolean isread;
	private String title;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		topRightLVisible();
//		topRightRVisible();
		topRightTGone();
//		rl_l = (RelativeLayout) getTopLightRl();
//		rl_r = (RelativeLayout) getTopRightRl();
//		iv_top_l = (ImageView) getTopLightView();
//		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
//		iv_top_t = (ImageView) getTopRightView();
//		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		setTitleTxt(R.string.service_center_title);
		setContentXml(R.layout.service_center_layout);
		init();
		//addlistener();
	}

	private void addlistener() {
		rl_l.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(context,XinjianActivity.class);
				intent.putExtra("flag", "service");
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
	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		regist = true;
		shangjia = true;
		buy = true;
		zhuanjia = true;
		wenti = true;
		ll_zx_regist = (LinearLayout) findViewById(R.id.service_center_ll_zx_regist);
		ll_zx_regist.setOnClickListener(this);
		ll_zx_shangjia = (LinearLayout) findViewById(R.id.service_center_ll_zx_shangjia);
		ll_zx_shangjia.setOnClickListener(this);
		ll_zx_buy = (LinearLayout) findViewById(R.id.service_center_ll_zx_buy);
		ll_zx_buy.setOnClickListener(this);
		ll_zx_zhuanjia = (LinearLayout) findViewById(R.id.service_center_ll_zx_zhuanjia);
		ll_zx_zhuanjia.setOnClickListener(this);
		ll_wt_chaxun = (LinearLayout) findViewById(R.id.service_center_ll_wenti_chaxun);
		ll_wt_chaxun.setOnClickListener(this);
		ll_wt_order= (LinearLayout) findViewById(R.id.service_center_ll_wenti_order);
		ll_wt_order.setOnClickListener(this);
		ll_wt_regist = (LinearLayout) findViewById(R.id.service_center_ll_wenti_regist);
		ll_wt_regist.setOnClickListener(this);
		ll_wt_wuliu = (LinearLayout) findViewById(R.id.service_center_ll_wenti_wuliu);
		ll_wt_wuliu.setOnClickListener(this);
		ll_wt_qita = (LinearLayout) findViewById(R.id.service_center_ll_wenti_qita);
		ll_wt_qita.setOnClickListener(this);
		ll_show_regist = (LinearLayout) findViewById(R.id.service_center_ll_show_regist);
		ll_show_shangjia = (LinearLayout) findViewById(R.id.service_center_ll_show_shangjia);
		ll_show_buy = (LinearLayout) findViewById(R.id.service_center_ll_show_buy);
		ll_show_zhuanjia = (LinearLayout) findViewById(R.id.service_center_ll_show_zhuanjia);
		ll_show_wenti = (LinearLayout) findViewById(R.id.service_center_ll_show_wenti);

		tv_zhuce_1_online = (TextView) findViewById(R.id.service_center_tv_regist_one_line);
		tv_zhuce_1_online.setOnClickListener(new MyOnclickListener());
		tv_zhuce_1_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_zhuce_1_qq = (TextView) findViewById(R.id.service_center_tv_regist_one_qq);
		tv_zhuce_1_qq.setOnClickListener(new MyOnclickListener());
		tv_zhuce_1_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_zhuce_2_online = (TextView) findViewById(R.id.service_center_tv_regist_two_line);
		tv_zhuce_2_online.setOnClickListener(new MyOnclickListener());
		tv_zhuce_2_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_zhuce_2_qq = (TextView) findViewById(R.id.service_center_tv_regist_two_qq);
		tv_zhuce_2_qq.setOnClickListener(new MyOnclickListener());
		tv_zhuce_2_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);


		tv_shangjia_1_online = (TextView) findViewById(R.id.service_center_tv_shangjia_one_line);
		tv_shangjia_1_online.setOnClickListener(new MyOnclickListener());
		tv_shangjia_1_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_shangjia_1_qq = (TextView) findViewById(R.id.service_center_tv_shangjia_one_qq);
		tv_shangjia_1_qq.setOnClickListener(new MyOnclickListener());
		tv_shangjia_1_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_shangjia_2_online = (TextView) findViewById(R.id.service_center_tv_shangjia_two_line);
		tv_shangjia_2_online.setOnClickListener(new MyOnclickListener());
		tv_shangjia_2_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_shangjia_2_qq = (TextView) findViewById(R.id.service_center_tv_shangjia_two_qq);
		tv_shangjia_2_qq.setOnClickListener(new MyOnclickListener());
		tv_shangjia_2_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);


		tv_buy_1_online = (TextView) findViewById(R.id.service_center_tv_buy_one_line);
		tv_buy_1_online.setOnClickListener(new MyOnclickListener());
		tv_buy_1_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_buy_1_qq = (TextView) findViewById(R.id.service_center_tv_buy_one_qq);
		tv_buy_1_qq.setOnClickListener(new MyOnclickListener());
		tv_buy_1_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_buy_2_online = (TextView) findViewById(R.id.service_center_tv_buy_two_line);
		tv_buy_2_online.setOnClickListener(new MyOnclickListener());
		tv_buy_2_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_buy_2_qq = (TextView) findViewById(R.id.service_center_tv_buy_two_qq);
		tv_buy_2_qq.setOnClickListener(new MyOnclickListener());
		tv_buy_2_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);


		tv_zhuanjia_1_online = (TextView) findViewById(R.id.service_center_tv_zhuanjia_one_line);
		tv_zhuanjia_1_online.setOnClickListener(new MyOnclickListener());
		tv_zhuanjia_1_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_zhuanjia_1_qq = (TextView) findViewById(R.id.service_center_tv_zhuanjia_one_qq);
		tv_zhuanjia_1_qq.setOnClickListener(new MyOnclickListener());
		tv_zhuanjia_1_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_zhuanjia_2_online = (TextView) findViewById(R.id.service_center_tv_zhuanjia_two_line);
		tv_zhuanjia_2_online.setOnClickListener(new MyOnclickListener());
		tv_zhuanjia_2_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

		tv_zhuanjia_2_qq = (TextView) findViewById(R.id.service_center_tv_zhuanjia_two_qq);
		tv_zhuanjia_2_qq.setOnClickListener(new MyOnclickListener());
		tv_zhuanjia_2_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);


		Util.SetRedNum(context, rl_l, 1);






	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.service_center_ll_zx_regist:
			if(!regist){
				ll_show_regist.setVisibility(View.VISIBLE);
				regist = true;
			}else{
				ll_show_regist.setVisibility(View.GONE);
				regist = false;
			}
			break;
		case R.id.service_center_ll_zx_shangjia:
			if(!shangjia){
				ll_show_shangjia.setVisibility(View.VISIBLE);
				shangjia = true;
			}else{
				ll_show_shangjia.setVisibility(View.GONE);
				shangjia = false;
			}
			break;
		case R.id.service_center_ll_zx_buy:
			if(!buy){
				ll_show_buy.setVisibility(View.VISIBLE);
				buy = true;
			}else{
				ll_show_buy.setVisibility(View.GONE);
				buy = false;
			}

			break;
		case R.id.service_center_ll_zx_zhuanjia:
			if(!zhuanjia){
				ll_show_zhuanjia.setVisibility(View.VISIBLE);
				zhuanjia = true;
			}else{
				ll_show_zhuanjia.setVisibility(View.GONE);
				zhuanjia = false;
			}
			break;
		case R.id.service_center_ll_wenti_chaxun:
			if(!wenti){
				ll_show_wenti.setVisibility(View.VISIBLE);
				wenti = true;
			}else{
				ll_show_wenti.setVisibility(View.GONE);
				wenti = false;
			}
			break;
		case R.id.service_center_tv_regist_one_line:

			break;
		case R.id.service_center_ll_wenti_order:
			title = getResources().getString(R.string.service_center_wenti_order);
			ToQuestion(title);
			break;
		case R.id.service_center_ll_wenti_regist:
			title = getResources().getString(R.string.service_center_wenti_regist);
			ToQuestion(title);
			break;
		case R.id.service_center_ll_wenti_wuliu:
			title = getResources().getString(R.string.service_center_wenti_wuliu);
			ToQuestion(title);
			break;
		case R.id.service_center_ll_wenti_qita:
			title = getResources().getString(R.string.service_center_wenti_qita);
			ToQuestion(title);
			break;

		}
	}
	class MyOnclickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
//			//已安装插件列表
//			List<org.osgi.framework.Bundle> bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
//			BundleContext context =MyApplication.frame.getSystemBundleContext();
//			for(int i=0;i<context.getBundles().length;i++)
//			{
//				//获取已安装插件
//				bundles.add(context.getBundles()[i]);        	        
//			}
//
//			//				BundleContext context =frame.getSystemBundleContext();
//			startor(bundles);
			ShowDialog();
		}

	}
	public void startor(List<org.osgi.framework.Bundle> list){
		org.osgi.framework.Bundle bundle=list.get(1);
		if(bundle.getState()!=bundle.ACTIVE){
			//判断插件是否已启动
			try {
				bundle.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
	@Override
	protected void onResume() {
		super.onResume();
		if(isread){
			Util.SetRedGone(context, rl_l);
			isread = false;
		}
	}


	private void ToQuestion(String title){
		intent = new Intent(context,ServiceQuestionHomeActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("title", title);
		startActivity(intent);

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
									+ "14730409666"));
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
}
