package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educonsult.R;
import com.example.educonsult.adapters.HomeSlidAdapter;
import com.example.educonsult.adapters.SCStoreAdapter;
import com.example.educonsult.myviews.MyListview;
import com.testin.agent.TestinAgent;

public class PasswordActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_login,ll_zhifu;
	private Context context;
	private Intent intent;



	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		topRightTGone();
		setTopLeftTv(R.string.possword_title);
		setContentXml(R.layout.possword);
		init();

	}
	void init(){
		TestinAgent.init(this);
		ll_login=(LinearLayout)findViewById(R.id.possword_login_lin);
		ll_login.setOnClickListener(this);
		ll_zhifu=(LinearLayout)findViewById(R.id.possword_zhifu_lin);
		ll_zhifu.setOnClickListener(this);

	}
	

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.possword_login_lin:
			intent = new Intent(context,PasswordXGActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("possword","1");
			startActivity(intent);

			break;
		case R.id.possword_zhifu_lin:
			intent = new Intent(context,PasswordXGActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("possword","2");
			startActivity(intent);
			break;
		default:
			break;
		}
	}
	
}
