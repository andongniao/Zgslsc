package com.example.educonsult.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.R;
import com.example.educonsult.tools.Util;

public class MoneyWithdrawalActivity extends BaseActivity implements OnClickListener{
	private TextView carId,allMoney,num;
	private EditText money;
	private LinearLayout carInfo;
	private Context context;
	private Intent intent;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private ImageView carIc;
	private Button submit;



	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		topRightLVisible();
		topRightRVisible();
		topRightTGone();
		rl_l = (RelativeLayout) getTopLightRl();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_l = (ImageView) getTopLightView();
		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		setTopLeftTv(R.string.money_withdrawal_title);
		setContentXml(R.layout.money_withdrawal);
		init();
		addlistener();
	}

	private void addlistener() {
		rl_l.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(context,XinjianActivity.class);
				intent.putExtra("flag", "qianbao");
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
	void init(){
		carId=(TextView)findViewById(R.id.money_withdrawal_carid);
		allMoney=(TextView)findViewById(R.id.money_withdrawal_allmoney);
		num=(TextView)findViewById(R.id.money_withdrawal_num);
		money=(EditText)findViewById(R.id.money_withdrawal_edmoney);
		money.setOnClickListener(this);
		carIc=(ImageView)findViewById(R.id.money_withdrawal_ic);
		carInfo=(LinearLayout)findViewById(R.id.money_withdrawal_car);
		carInfo.setOnClickListener(this);
		submit=(Button)findViewById(R.id.money_withdrawal_up);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.money_withdrawal_edmoney:
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



}
