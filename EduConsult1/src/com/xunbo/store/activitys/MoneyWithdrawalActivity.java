package com.xunbo.store.activitys;

import java.util.ArrayList;

import android.R.integer;
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
import com.handmark.pulltorefresh.library.internal.Utils;
import com.testin.agent.TestinAgent;
import com.xunbo.store.ExampleActivity;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.activitys.SCProductActivity.RefeshData;
import com.xunbo.store.adapters.ProductAdapter;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.beans.ListMoneytxBean;
import com.xunbo.store.beans.MoneyTxBean;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.net.Send;
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
	private ListMoneytxBean listMoneytxBean;
	private ArrayList<MoneyTxBean> moneyTxBeans;
	private String moneyStr,AdCodeStr;

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
			myPDT.Run(context, new RefeshData(),R.string.loding);//��ȡ��
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
//		carInfo.setOnClickListener(this);
		submit=(Button)findViewById(R.id.money_withdrawal_up);
		submit.setOnClickListener(this);
		yanzhengma=(Button)findViewById(R.id.money_withdrawal_yanzheng);
		yanzhengma.setOnClickListener(this);
		
	}
	Runnable runable = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			// 10s����
//			yanzhengma.setClickable(false);
//			yanzhengma.setFocusable(false);
			yanzhengma.setEnabled(true);
			yanzhengma.setBackgroundResource(R.drawable.money_withdrawal_code_bg);
//			yanzhengma.setBackgroundResource(R.color.white);
			yanzhengma.setTextColor(R.color.fame_hui2); 
			yanzhengma.setPadding(15, 15, 15, 15);
			yanzhengma.setTextSize(16);
			--startTime;
			yanzhengma.setText(startTime + "s�����»�ȡ");
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
			if (isAutoCode) { // ��ť���ڿɵ��״̬���������룬����֤����
				isAutoCode = false;
//				yanzhengma.setClickable(false);
//				yanzhengma.setFocusable(false);
				yanzhengma.setEnabled(false);
				yanzhengma.setBackgroundResource(R.drawable.money_withdrawal_code_bg);
//				yanzhengma.setBackgroundResource(R.color.white);
				yanzhengma.setTextColor(R.color.fame_hui2);
				yanzhengma.setPadding(15, 15, 15, 15);
				yanzhengma.setTextSize(16);
				yanzhengma.setText("���ڷ���...");
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(),R.string.loding);//��ȡ��
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
//				initAdcode();

			} else {
				return;
			}
			
			
			break;
		case R.id.money_withdrawal_car:
//			intent = new Intent(context,MoneyCarListActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
			break;
		case R.id.money_withdrawal_up:
			intType=3;
			moneyStr=money.getText().toString().trim();
			AdCodeStr=ed_yanzhengma.getText().toString().trim();
			if(Util.IsNull(AdCodeStr)){
				if(Util.IsNull(moneyStr)){
					if(Integer.parseInt(moneyStr)<=Integer.parseInt(moneyTxBeans.get(0).getAll_money())&&Integer.parseInt(moneyStr)>0){
						if(Util.detect(context)){
							myPDT.Run(context, new RefeshData(),R.string.loding);//��ȡ��
						}else{
							Util.ShowToast(context, R.string.net_is_eor);
						}
					}else{
						
						Util.ShowToast(context, "���ֽ��������н��");
					}
				}else{
					Util.ShowToast(context, "���ֽ���Ϊ��");
				}
				
			}else{
				Util.ShowToast(context, "��֤�벻��Ϊ��");
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
			if(intType==1){
				if(listMoneytxBean!=null){
					if("200".equals(listMoneytxBean.getCode())){
						initBank();
						
					}else if("300".equals(listMoneytxBean.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent i= new Intent(context,LoginActivity.class);
						startActivity(i);
						finish();
					}else{
						Util.ShowToast(context, R.string.net_is_eor);
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
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
			if(intType==3){
				if(baseBean!=null){
					if("200".equals(baseBean.getCode())){
						Util.ShowToast(context,"���ֳɹ�~");
						finish();
					}else if("300".equals(baseBean.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent i= new Intent(context,LoginActivity.class);
						startActivity(i);
						finish();
					}else{
						Util.ShowToast(context, baseBean.getMsg());
					}
				
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			return true;
		}

		@Override
		public boolean TaskMain() {
			// ����
			PostHttp p=new PostHttp(context);
			Send s=new Send(context);
			if(intType==1){
				listMoneytxBean=s.getTxBankList(MyApplication.mp.getUser().getAuthstr());
			}
			if(intType==2){
				baseBean=p.getMobileCode(MyApplication.mp.getCenterUserBean().getMobile());
//				baseBean=p.getMobileCode("18632179739");
			}
			if(intType==3){
				baseBean=s.getTx(moneyStr, MyApplication.mp.getUser().getAuthstr(),AdCodeStr);
			}
			return true;
		}
	}
	private void initAdcode(){
//			yanzhengma.setClickable(false);
			yanzhengma.setFocusable(false);
			yanzhengma.setBackgroundResource(R.drawable.money_withdrawal_code_bg);
//			yanzhengma.setBackgroundResource(R.color.white);
			yanzhengma.setTextColor(R.color.fame_hui2);
			yanzhengma.setPadding(15, 15, 15, 15);
			yanzhengma.setTextSize(16);
			yanzhengma.setText(startTime + "s�����»�ȡ");
			yanzhengma.setEnabled(false);
			mHandler.postDelayed(runable, 1000);
	}
	
	private void AutoCode(){
		yanzhengma.setText("��ȡ��֤��");
//		yanzhengma.setClickable(true);
//		yanzhengma.setFocusable(true);
		yanzhengma.setEnabled(true);
		yanzhengma.setBackgroundResource(R.drawable.search_lv_isnull_btn_bg);
		yanzhengma.setTextColor(Color.WHITE);
		yanzhengma.setPadding(15, 15, 15, 15);
		yanzhengma.setTextSize(16);
		isAutoCode = true;
		startTime = 120;
	}
	private void initBank(){
		moneyTxBeans=listMoneytxBean.getList();
		if(moneyTxBeans!=null&&moneyTxBeans.size()>0){
//			Util.Getbitmap(carIc,);
			carId.setText(moneyTxBeans.get(0).getBindAccNum());
			allMoney.setText(moneyTxBeans.get(0).getAll_money());
			if("��������".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.huaxia);
			}else if("�ӱ�����".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.hebei);
			}else if("�й�����".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.zhongguo);
			}else if("�й���������".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.gongshang);
			}else if("�й�ũҵ����".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.nongye);
			}else if("�й���������".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.jianshe);
			}else if("��ͨ����".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.jiaotong);
			}else if("��������".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.zhaoshang);
			}else if("��������".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.bohai);
			}else if("�㷢����".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.guangfa);
			}else if("ƽ������".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.pingan);
			}else if("����ʵҵ����".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.zhongxin);
			}else if("�й��������".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.guangda);
			}else if("�й���������".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.minsheng);
			}else if("��ҵ����".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.xingye);
			}else if("�Ϻ��ֶ���չ����".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.pufa);
			}else if("�й�������������".equals(moneyTxBeans.get(0).getBindOpenBank())){
				carIc.setBackgroundResource(R.drawable.youchu);
			}
		}else{
			Util.ShowToast(context, "����δ�����п�~");
			finish();
		}
		
	}
	
}
