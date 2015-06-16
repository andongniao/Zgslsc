package com.xunbo.store.activitys;

import java.util.List;

import org.osgi.framework.BundleContext;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;

/**
 * 我要提问
 * @author Qzr
 *
 */
public class PutQuestionActivity extends Activity implements OnClickListener{
	private Context context;
	private EditText et_content,et_search;;
	private TextView tv_commit;
	private LinearLayout ll_zixun,ll_back;
	private CheckBox cb;
	private boolean isshow;
	private Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.know_put_question_layout);
		init();
	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		findViewById(R.id.put_question_et_top).setOnClickListener(this);
		findViewById(R.id.put_question_ll_back).setOnClickListener(this);
		//		findViewById(R.id.know_put_question_et).setOnClickListener(this);
		findViewById(R.id.put_question_tv_commit).setOnClickListener(this);
		findViewById(R.id.put_question_ll_tiwen_i).setOnClickListener(this);
		cb = (CheckBox) findViewById(R.id.put_question_cb);
		cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isshow = !isChecked;
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.put_question_et_top:
			intent = new Intent(context,SearchHomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("t", 1);
			startActivity(intent);
			break;
		case R.id.put_question_tv_commit:

			break;
		case R.id.put_question_ll_back:
			finish();
			break;
		case R.id.put_question_ll_tiwen_i:
			//已安装插件列表
			List<org.osgi.framework.Bundle> bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
			BundleContext context =MyApplication.frame.getSystemBundleContext();
			for(int i=0;i<context.getBundles().length;i++){
				//获取已安装插件
				bundles.add(context.getBundles()[i]);        	        
			}
			startor(bundles);
			
			break;
			//case R.id.put_question_et_top:
			//	
			//	break;
		}
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
