package com.example.educonsult.activitys;

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

public class ApplyInfoActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private Intent intent;
	private TextView tv_name,tv_cname,tv_money,tv_time,tv_way,tv_applying,tv_why,tv_text;
	private LinearLayout ll_talk;
	private int time=5;
private List<org.osgi.framework.Bundle> bundles=null;
	private FrameworkInstance frame=null;


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		setTopLeftTv(R.string.applyinfo_title);
		setContentXml(R.layout.applyinfo);
		init();

	}



	private void init() {
		context = this;
//		private TextView tv_name,tv_cname,tv_money,
//		tv_time,tv_way
//		,tv_talk,tv_applying,tv_why;
//		private LinearLayout ll_talk;
	frame = MyApplication.frame;
		
		tv_name=(TextView)findViewById(R.id.applyinfo_shopkeeper);
		tv_text=(TextView)findViewById(R.id.applyinfo_apptexttime);
		tv_cname=(TextView)findViewById(R.id.applyinfo_computername);
		tv_money=(TextView)findViewById(R.id.applyinfo_applymoney);
		tv_time=(TextView)findViewById(R.id.applyinfo_applytime);
		tv_way=(TextView)findViewById(R.id.applyinfo_applyway);
		tv_applying=(TextView)findViewById(R.id.applyinfo_applying);
		ll_talk=(LinearLayout)findViewById(R.id.applyinfo_talk_lin);
		ll_talk.setOnClickListener(this);
		tv_why=(TextView)findViewById(R.id.applyinfo_applywhy);
		tv_text.setText("（若"+time+"天内商家未处理，退款申请将达成并退款至您的银行卡中。）");
		
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
	

		
		case R.id.applyinfo_talk_lin:
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
