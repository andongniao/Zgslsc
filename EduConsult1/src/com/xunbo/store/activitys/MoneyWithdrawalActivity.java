package com.xunbo.store.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.testin.agent.TestinAgent;
import com.xunbo.store.ExampleActivity;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.activitys.SCProductActivity.RefeshData;
import com.xunbo.store.adapters.ProductAdapter;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.tools.Util;

public class MoneyWithdrawalActivity extends BaseActivity implements OnClickListener{
	private TextView carId,allMoney,num;
	private EditText money,ed_yanzhengma;
	private LinearLayout carInfo;
	private Context context;
	private Intent intent;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private ImageView carIc;
	private Button submit,yanzhengma;
	private boolean isAutoCode=true;
	private int startTime = 120;
	private Handler mHandler=new Handler();
	private int intType;
	private ThreadWithProgressDialog myPDT;
	private BaseBean baseBean;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		topRightTGone();
		setTitleTxt(R.string.money_withdrawal_title);
//		setTopLeftTv(R.string.money_withdrawal_title);
		setContentXml(R.layout.money_withdrawal);
		init();
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}

	void init(){
		TestinAgent.init(this);
		intType=1;
		myPDT=new ThreadWithProgressDialog();
		carId=(TextView)findViewById(R.id.money_withdrawal_carid);
		allMoney=(TextView)findViewById(R.id.money_withdrawal_allmoney);
		num=(TextView)findViewById(R.id.money_withdrawal_num);
		money=(EditText)findViewById(R.id.money_withdrawal_edmoney);
//		money.setOnClickListener(this);
		ed_yanzhengma=(EditText)findViewById(R.id.money_withdrawal_ed_yanzheng);
		carIc=(ImageView)findViewById(R.id.money_withdrawal_ic);
		carInfo=(LinearLayout)findViewById(R.id.money_withdrawal_car);
		carInfo.setOnClickListener(this);
		submit=(Button)findViewById(R.id.money_withdrawal_up);
		submit.setOnClickListener(this);
		yanzhengma=(Button)findViewById(R.id.money_withdrawal_yanzheng);
		yanzhengma.setOnClickListener(this);
		
	}
	Runnable runable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			// 10s倒数
			yanzhengma.setClickable(false);
			yanzhengma.setBackgroundResource(R.drawable.money_withdrawal_code_bg);
//			yanzhengma.setBackgroundResource(R.color.white);
			yanzhengma.setTextColor(R.color.fame_hui2); 
			yanzhengma.setPadding(15, 15, 15, 15);
			yanzhengma.setTextSize(16);
			--startTime;
			yanzhengma.setText(startTime + "s后重新获取");
			if (startTime <= 0) {
				AutoCode();
				return;
			}
			mHandler.postDelayed(this, 1000);
		}
	};
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.money_withdrawal_yanzheng:
			intType=2;
			if (isAutoCode == true) { // 按钮出于可点击状态，点击后读秒，并验证请求
				isAutoCode = false;
				yanzhengma.setClickable(false);
				yanzhengma.setBackgroundResource(R.drawable.money_withdrawal_code_bg);
//				yanzhengma.setBackgroundResource(R.color.white);
				yanzhengma.setTextColor(R.color.fame_hui2);
				yanzhengma.setPadding(15, 15, 15, 15);
				yanzhengma.setTextSize(16);
				yanzhengma.setText("正在发送...");
				initAdcode();

			} else {
				return;
			}
//			if(Util.detect(context)){
//				myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
//			}else{
//				Util.ShowToast(context, R.string.net_is_eor);
//			}
			
			break;
		case R.id.money_withdrawal_car:
			intent = new Intent(context,MoneyCarListActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.money_withdrawal_up:
			String moneyStr=money.getText().toString().trim();
			String numStr=num.getText().toString().trim();
			if(Util.IsNull(moneyStr)){
				int moneyNum=Integer.valueOf(moneyStr);
				if("0".equals(numStr)){
					Toast.makeText(context, "本日转出次数为0次", 200).show();
				}
				else{
					Toast.makeText(context, "转出成功", 200).show();
				}
				if(moneyNum>300){
					Toast.makeText(context, "转出金额大于300", 200).show();
				}
			}
			else{
				Toast.makeText(context, "转出金额出错", 200).show();
			}

			break;
		default:
			break;
		}
	}
	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			finish();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			if(intType==2){
				if(baseBean!=null){
					if("200".equals(baseBean.getCode())){
						initAdcode();
					}else if("300".equals(baseBean.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent i= new Intent(context,LoginActivity.class);
						startActivity(i);
						finish();
					}else{
						AutoCode();
						Util.ShowToast(context, baseBean.getMsg());
					}
				
				}else{
					AutoCode();
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			PostHttp p=new PostHttp(context);
			if(intType==2){
				baseBean=p.getMobileCode(MyApplication.mp.getCenterUserBean().getMobile());
//				baseBean=p.getMobileCode("18031820695");
			}
			return true;
		}
	}
	private void initAdcode(){
			yanzhengma.setClickable(false);
			yanzhengma.setBackgroundResource(R.drawable.money_withdrawal_code_bg);
//			yanzhengma.setBackgroundResource(R.color.white);
			yanzhengma.setTextColor(R.color.fame_hui2);
			yanzhengma.setPadding(15, 15, 15, 15);
			yanzhengma.setTextSize(16);
			yanzhengma.setText(startTime + "s后重新获取");
			yanzhengma.setEnabled(false);
			mHandler.postDelayed(runable, 1000);
	}
	private void AutoCode(){
		yanzhengma.setText("获取验证码");
		yanzhengma.setClickable(true);
		yanzhengma.setBackgroundResource(R.drawable.search_lv_isnull_btn_bg);
		yanzhengma.setTextColor(Color.WHITE);
		yanzhengma.setPadding(15, 15, 15, 15);
		yanzhengma.setTextSize(16);
		isAutoCode = true;
		startTime = 120;
	}
	
}
