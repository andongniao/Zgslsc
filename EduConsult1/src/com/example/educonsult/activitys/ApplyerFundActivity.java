package com.example.educonsult.activitys;

import java.util.ArrayList;
import java.util.List;

import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.adapters.CApplyerFundAdapter;
import com.example.educonsult.myviews.MyListview;

public class ApplyerFundActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private Intent intent;
	private ImageView image,wayone,waytwo;
	private TextView tv_pname,tv_cname,tv_money,tv_num,tv_orderid
	,tv_appmoney,tv_talk,tv_break,tv_ok,tv_yunfei;
	private EditText ed;
	private LinearLayout ll_wayone,ll_waytwo;
	private List<org.osgi.framework.Bundle> bundles=null;
	private FrameworkInstance frame=null;
private MyListview listview;
private CApplyerFundAdapter applyerFundAdapter;
private ArrayList<Integer> list;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		setTopLeftTv(R.string.applyerfund_title);
		setContentXml(R.layout.applyrefund);
		init();

	}



	private void init() {
		context = this;
		//		private ImageView image,wayone,waytwo;
		//		private TextView tv_pname,tv_cname,tv_money,
		//tv_num,tv_orderid
		//		,tv_appmoney,tv_ed;
	frame = MyApplication.frame;
		wayone=(ImageView)findViewById(R.id.applyerfund_wayone_img);
		waytwo=(ImageView)findViewById(R.id.applyerfund_waytwo_img);
		ll_wayone=(LinearLayout)findViewById(R.id.applyerfund_wayone_lin);
		ll_wayone.setOnClickListener(this);
		ll_waytwo=(LinearLayout)findViewById(R.id.applyerfund_waytwo_lin);
		ll_waytwo.setOnClickListener(this);
		
		
		tv_break=(TextView)findViewById(R.id.applyerfund_break);
		tv_break.setOnClickListener(this);
		tv_ok=(TextView)findViewById(R.id.applyerfund_ok);
		tv_ok.setOnClickListener(this);
		ed=(EditText)findViewById(R.id.applyerfund_whyedt);
		listview=(MyListview)findViewById(R.id.applyrefund_list);
		list=new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		applyerFundAdapter=new CApplyerFundAdapter(this, list);
		listview.setAdapter(applyerFundAdapter);
		tv_appmoney=(TextView)findViewById(R.id.applyerfund_applyerfundmoney);
		tv_appmoney.setText("￥"+"");
		tv_yunfei=(TextView)findViewById(R.id.applyerfund_yunfeimoney);
		tv_yunfei.setText("（含运费￥"+""+"）");
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
			//			Toast.makeText(context, "启动"+bundle.getBundleActivity().split(",")[0],
			//				     Toast.LENGTH_SHORT).show();
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
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.applyerfund_break:
		finish();
			break;
		case R.id.applyerfund_ok:
		finish();
			break;

		case R.id.applyerfund_wayone_lin:
			wayone.setBackgroundResource(R.drawable.applyerfund_wayone);
			waytwo.setBackgroundResource(R.drawable.applyerfund_waytwo);


			break;
		case R.id.applyerfund_waytwo_lin:
			waytwo.setBackgroundResource(R.drawable.applyerfund_wayone);
			wayone.setBackgroundResource(R.drawable.applyerfund_waytwo);

			break;
		case R.id.applyrefun_talk:
			//已安装插件列表
			bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
			BundleContext context = frame.getSystemBundleContext();
			for(int i=0;i<context.getBundles().length;i++)
			{
				//获取已安装插件
				bundles.add(context.getBundles()[i]);        	        
			}

			//BundleContext context =frame.getSystemBundleContext();
			startor(bundles);
			break;


		}
	}



}
