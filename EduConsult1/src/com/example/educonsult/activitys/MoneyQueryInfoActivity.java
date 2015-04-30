package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.DialogInterface;
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

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.R;
import com.example.educonsult.adapters.HomeSlidAdapter;
import com.example.educonsult.adapters.MoneyQueryAdapter;
import com.example.educonsult.myviews.MyListview;

public class MoneyQueryInfoActivity extends BaseActivity {
	private RelativeLayout reaLayout;
	private TextView allquery,tvid,tvway,tvwaymoney,tvmoneyway,tvtime
	,tvmoney,tvbeizhu;
	private MyListview list_money,list_way;
	private ListView list_2,lv_l;
	private MoneyQueryAdapter moneyQueryAdapter;
	private Context context;
	private ArrayList<String> list;
	private PopupWindow popu;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private Intent intent;
	private HomeSlidAdapter adapter_r;
	private LinearLayout lin,linbeizhu;
	private int isbeizhu=1;
	
	
	
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
		setTopLeftTv(R.string.moneyquery_info_title);
		setContentXml(R.layout.moneyquery_info);
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
		//tvid,tvway,tvwaymoney,tvmoneyway,tvtime
		//,tvmoney;
		
		tvid=(TextView)findViewById(R.id.moneyquery_info_id);
		tvway=(TextView)findViewById(R.id.moneyquery_info_way);
		tvwaymoney=(TextView)findViewById(R.id.moneyquery_info_waymoney);
		tvmoneyway=(TextView)findViewById(R.id.moneyquery_info_moneyway);
		tvtime=(TextView)findViewById(R.id.moneyquery_info_time);
		tvmoney=(TextView)findViewById(R.id.moneyquery_info_money);
		tvbeizhu=(TextView)findViewById(R.id.moneyquery_info_beizhu1);
		
		linbeizhu=(LinearLayout)findViewById(R.id.moneyqueryinfo_beizhu);
		//如果传过来的值1,备注是隐藏的   是收入方式，不是一则是支付方式
		if(isbeizhu!=1){
			linbeizhu.setVisibility(View.INVISIBLE);
		}
	}
	
	

}
