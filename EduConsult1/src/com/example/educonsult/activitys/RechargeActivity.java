package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.beans.ListChargeBankBean;
import com.example.educonsult.beans.TnResultBean;
import com.example.educonsult.net.PostHttp;
import com.example.educonsult.tools.Util;
import com.testin.agent.TestinAgent;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

public class RechargeActivity extends BaseActivity implements OnClickListener{
	private TextView carId;
	private EditText money;
	private LinearLayout carInfo;
	private Context context;
	private Intent intent;
	private ImageView carIc;
	private Button submit;
	private String mMode = "01";
	private String Tn = "";
	private String authstr;
	private ThreadWithProgressDialog myPDT;
	private ListChargeBankBean lcb;
	private TnResultBean tnbean;
	private ArrayList<String> list;
	private boolean init;
	private String bank,amount;


	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		topRightTGone();
		setTopLeftTv(R.string.recharge_title);
		setContentXml(R.layout.recharge);
		init();
		if(Util.detect(context)){
			//			myPDT.Run(context, new RefeshData(bean.getType(),bean.getAuthstr()),msg,false);//不可取消
			myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}
	void init(){
		TestinAgent.init(this);
		context =this;
		init =true;
		bank = "chinapay";
		list = new ArrayList<String>();
		authstr = MyApplication.mp.getUser().getAuthstr();
		carId=(TextView)findViewById(R.id.recharge_tv_bankname);
		money=(EditText)findViewById(R.id.recharge_edmoney);
		carIc=(ImageView)findViewById(R.id.recharge_ic);
		carInfo=(LinearLayout)findViewById(R.id.recharge_car);
		carInfo.setOnClickListener(this);
		submit=(Button)findViewById(R.id.recharge_up);
		submit.setOnClickListener(this);
		myPDT = new ThreadWithProgressDialog();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.money_withdrawal_edmoney:
			break;
		case R.id.recharge_car:
//			intent = new Intent(context,MoneyCarListActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
			break;
		case R.id.recharge_up:
			amount=money.getText().toString().trim();

			if(Util.IsNull(amount)){
				init = false;
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(), R.string.loding);
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
//								UPPayAssistEx.startPayByJAR(RechargeActivity.this, PayActivity.class, null, null,
//										"201505181716561050548", mMode);
			}else{
				Util.ShowToast(context, "请保证信息完整");
			}
			break;
		default:
			break;
		}
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}

		String msg = "";
		/*
		 * 支付控件返回字符串:success、fail、cancel
		 *      分别代表支付成功，支付失败，支付取消
		 */
		int statu=-1;
		String str = data.getExtras().getString("pay_result");
		if (str.equalsIgnoreCase("success")) {
			msg = "支付成功！";
			statu = 1;
		} else if (str.equalsIgnoreCase("fail")) {
			msg = "支付失败！";
			statu = 2;
		} else if (str.equalsIgnoreCase("cancel")) {
			msg = "用户取消了支付";
			statu = 3;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("支付结果通知");
		builder.setMessage(msg);
		builder.setInverseBackgroundForced(true);
		//builder.setCustomTitle();
		builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
		if(statu==1){
			finish();
		}
	}

	public class RefeshData implements ThreadWithProgressDialogTask {
		public RefeshData(){
		}

		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub
			PostHttp p =new PostHttp(context);
			if(init){
				lcb = p.getBanks(authstr);
			}else{
				tnbean = p.getTn(bank, amount, authstr);
			}
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(init){
				if(lcb!=null){
					if("200".equals(lcb.getCode())){
						for(int i=0;i<lcb.getList().size();i++){
							list.add(lcb.getList().get(i).getName());
						}
						carId.setText("中国银联");
					}else if("300".equals(lcb.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent i = new Intent(context,LoginActivity.class);
						startActivity(i);
						finish();
					}else{
						Util.ShowToast(context, lcb.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				if(tnbean!=null){
					if("200".equals(tnbean.getCode())){
						Tn = tnbean.getTn();
						UPPayAssistEx.startPayByJAR(RechargeActivity.this, PayActivity.class, null, null,
								Tn, mMode);
					}else if("300".equals(tnbean.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent i = new Intent(context,LoginActivity.class);
						startActivity(i);
						finish();
					}else{
						Util.ShowToast(context, tnbean.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			return true;

		}

	}


}
