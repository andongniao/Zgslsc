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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;
import com.xunbo.store.MyApplication;
import com.xunbo.store.beans.ListChargeBankBean;
import com.xunbo.store.beans.TnResultBean;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.tools.Util;

public class RechargeActivity extends BaseActivity implements OnClickListener{
	private TextView carId;
	private EditText money;
	private LinearLayout carInfo;
	private Context context;
	private Button submit;
	private String mMode = "00";
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
		setTitleTxt(R.string.recharge_title);
		setContentXml(R.layout.recharge);
		init();
		if(Util.detect(context)){
			//			myPDT.Run(context, new RefeshData(bean.getType(),bean.getAuthstr()),msg,false);//����ȡ��
			myPDT.Run(context, new RefeshData(),R.string.loding);//����ȡ��
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
			}else{
				Util.ShowToast(context, "�뱣֤��Ϣ����");
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
		 * ֧���ؼ������ַ���:success��fail��cancel
		 *      �ֱ����֧���ɹ���֧��ʧ�ܣ�֧��ȡ��
		 */
		int statu=-1;
		String str = data.getExtras().getString("pay_result");
		if (str.equalsIgnoreCase("success")) {
			msg = "֧���ɹ���";
			statu = 1;
		} else if (str.equalsIgnoreCase("fail")) {
			msg = "֧��ʧ�ܣ�";
			statu = 2;
		} else if (str.equalsIgnoreCase("cancel")) {
			msg = "�û�ȡ����֧��";
			statu = 3;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("֧�����֪ͨ");
		builder.setMessage(msg);
		builder.setInverseBackgroundForced(true);
		//builder.setCustomTitle();
		builder.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {
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
			//������ɺ�
			if(init){
				if(lcb!=null){
					if("200".equals(lcb.getCode())){
						for(int i=0;i<lcb.getList().size();i++){
							list.add(lcb.getList().get(i).getName());
						}
						carId.setText("�й�����");
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
