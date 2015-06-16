package com.xunbo.store.activitys;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.adapters.KnowMyQuestionAdapter;
import com.xunbo.store.tools.Util;

public class MyQuestionActivity extends BaseActivity{
	private Context context;
	private RelativeLayout rl_top_l,rl_top_r;
	private TextView tv_zixun;
	private ListView lv;
	private KnowMyQuestionAdapter adapter;
	private ArrayList<String>list;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTopLeftTv(R.string.tiwen_title);
		topRightLVisible();
		topRightRVisible();
		topRightTGone();
		setContentXml(R.layout.know_my_question_layout);
		init();
		addlistener();
	}

	private void addlistener() {
		tv_zixun.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//已安装插件列表
				List<org.osgi.framework.Bundle> bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
				BundleContext context =MyApplication.frame.getSystemBundleContext();
				for(int i=0;i<context.getBundles().length;i++){
					//获取已安装插件
					bundles.add(context.getBundles()[i]);        	        
				}
				startor(bundles);
			}
		});
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Util.ShowToast(context, "0.0");
			}
		});
	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		list = new ArrayList<String>();
		list.add("1");
		list.add("11");
		list.add("111");
		list.add("1111");
		rl_top_l = (RelativeLayout) getTopLightRl();
		rl_top_r = (RelativeLayout) getTopRightRl();
		tv_zixun = (TextView) findViewById(R.id.know_my_question_tv);
		lv = (ListView) findViewById(R.id.know_my_question_lv);
		adapter = new KnowMyQuestionAdapter(context, list);
		lv.setAdapter(adapter);
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
			Intent i=new Intent();
			i.setClassName(context, bundle.getBundleActivity().split(",")[0]);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}else{

			Toast.makeText(context, "该插件没有配置BundleActivity",
					Toast.LENGTH_SHORT).show();
		}
	}

}
