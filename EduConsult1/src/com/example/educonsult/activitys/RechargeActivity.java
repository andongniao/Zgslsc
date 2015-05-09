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

public class RechargeActivity extends BaseActivity implements OnClickListener{
	private TextView carId;
	private EditText money;
	private LinearLayout carInfo;
	private Context context;
	private Intent intent;
	private ImageView carIc;
	private Button submit;



	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		topRightTGone();
		setTopLeftTv(R.string.recharge_title);
		setContentXml(R.layout.recharge);
		init();
		
	}
	void init(){
		carId=(TextView)findViewById(R.id.recharge_carid);
		money=(EditText)findViewById(R.id.recharge_edmoney);
		carIc=(ImageView)findViewById(R.id.recharge_ic);
		carInfo=(LinearLayout)findViewById(R.id.recharge_car);
		carInfo.setOnClickListener(this);
		submit=(Button)findViewById(R.id.recharge_up);
		submit.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.money_withdrawal_edmoney:
			break;
		case R.id.recharge_car:
			intent = new Intent(context,MoneyCarListActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.recharge_up:
			String moneyStr=money.getText().toString().trim();
			
			if(Util.IsNull(moneyStr)){
				int moneyNum=Integer.valueOf(moneyStr);
				
			}
			else{
				
			}

			break;
		default:
			break;
		}
	}



}
