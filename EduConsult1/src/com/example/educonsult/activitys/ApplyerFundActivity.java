package com.example.educonsult.activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.umeng.analytics.MobclickAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.adapters.CApplyerFundAdapter;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.beans.RefundInfoBean;
import com.xunbo.store.myviews.MyListview;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.tools.Util;

public class ApplyerFundActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private Intent intent;
	private ImageView wayone,waytwo;
	@SuppressWarnings("unused")
	private TextView tv_pname,tv_cname,tv_money,tv_num,tv_orderid
	,tv_appmoney,tv_talk,tv_break,tv_ok,tv_yunfei;
	private EditText ed;
	private LinearLayout ll_wayone,ll_waytwo;
	private MyListview listview;
	private CApplyerFundAdapter applyerFundAdapter;
	private ThreadWithProgressDialog myPDT;
	private String itemid,authstr,content;
	private boolean initdata;
	private RefundInfoBean rebean;
	private BaseBean bean;


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		setTitleTxt(R.string.applyerfund_title);
		setContentXml(R.layout.applyrefund);
		init();
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}



	private void init() {
		TestinAgent.init(this);
		context = this;
		initdata = true;
		itemid = getIntent().getStringExtra("itemid");
		authstr = MyApplication.mp.getUser().getAuthstr();
		myPDT = new ThreadWithProgressDialog();
		wayone=(ImageView)findViewById(R.id.applyerfund_wayone_img);
		waytwo=(ImageView)findViewById(R.id.applyerfund_waytwo_img);
		ll_wayone=(LinearLayout)findViewById(R.id.applyerfund_wayone_lin);
		ll_wayone.setOnClickListener(this);
		ll_waytwo=(LinearLayout)findViewById(R.id.applyerfund_waytwo_lin);
		ll_waytwo.setOnClickListener(this);
		findViewById(R.id.applyerfund_tv_online).setOnClickListener(this);

		tv_break=(TextView)findViewById(R.id.applyerfund_break);
		tv_break.setOnClickListener(this);
		tv_ok=(TextView)findViewById(R.id.applyerfund_tv_shenqing);
		tv_ok.setOnClickListener(this);
		ed=(EditText)findViewById(R.id.applyerfund_whyedt);
		listview=(MyListview)findViewById(R.id.applyrefund_list);
		tv_appmoney=(TextView)findViewById(R.id.applyerfund_applyerfundmoney);
		tv_yunfei=(TextView)findViewById(R.id.applyerfund_yunfeimoney);
//		tv_yunfei.setText("（含运费￥"+""+"）");
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.applyerfund_break:
			finish();
			break;
		case R.id.applyerfund_tv_shenqing:
			content = ed.getText().toString().trim();
			if(Util.IsNull(content)){
				initdata = false;
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				Util.ShowToast(context, "请填写有效退款说明");
			}
			break;

		case R.id.applyerfund_wayone_lin:
			wayone.setBackgroundResource(R.drawable.applyerfund_wayone);
			waytwo.setBackgroundResource(R.drawable.applyerfund_waytwo);


			break;
		case R.id.applyerfund_waytwo_lin:
			waytwo.setBackgroundResource(R.drawable.applyerfund_wayone);
			wayone.setBackgroundResource(R.drawable.applyerfund_waytwo);

			break;
		case R.id.applyerfund_tv_online:
			ShowDialog();
			break;


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
			if(initdata){
				if(rebean!=null){
					if("200".equals(rebean.getCode())){
						tv_appmoney.setText("￥"+rebean.getMoney());
						if(applyerFundAdapter!=null){
							applyerFundAdapter.SetData(rebean);
							applyerFundAdapter.notifyDataSetChanged();
						}else{
						applyerFundAdapter=new CApplyerFundAdapter(context, rebean);
						listview.setAdapter(applyerFundAdapter);
						}
					}else if("300".equals(rebean.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						intent = new Intent(context,LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish(); 
					}else{
						Util.ShowToast(context, rebean.getMsg());
					}

				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				if(bean!=null){
					if("200".equals(bean.getCode())){
						Util.ShowToast(context, "操作成功！");
						MyOrderActivity.isinit = true;
						finish();
					}else if("300".equals(bean.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						intent = new Intent(context,LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish(); 
					}else{
						Util.ShowToast(context, bean.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			PostHttp p = new PostHttp(context);
			if(initdata){
				rebean = p.Order_refunddata(itemid, authstr);
			}else{
				bean = p.Order_refund(itemid, 1, content, authstr);
			}



			return true;
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart( "ApplyerFundActivity" );
		MobclickAgent.onResume(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd( "ApplyerFundActivity" );
		MobclickAgent.onPause(this);
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
				String tel = context.getResources().getString(R.string.tel);
				Intent phoneIntent = new Intent(
						"android.intent.action.CALL",
						Uri.parse("tel:"
								+ tel));
				startActivity(phoneIntent);
			}
		}).create();
		alertDialog.show();
	}
}
