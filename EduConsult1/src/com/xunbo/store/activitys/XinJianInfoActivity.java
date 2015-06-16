package com.xunbo.store.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.beans.UserBean;
import com.xunbo.store.beans.XinJianDetaileBean;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class XinJianInfoActivity extends BaseActivity{
	private Intent intent;
	private Context context;
	private String xinjianid;
	private TextView title,content;
	private XinJianDetaileBean xinjianbean;
	private UserBean bean;
	private ThreadWithProgressDialog myPDT;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//topRightRVisible();
		topRightTGone();
		setTopLeftTv(R.string.xinjianinfo_title);
		setContentXml(R.layout.xinjianinfo);
		init();
	}
	void init(){
		TestinAgent.init(this);
		context=this;
		myPDT = new ThreadWithProgressDialog();
		intent=getIntent();
		xinjianid=intent.getStringExtra("xinjianid");
		bean=MyApplication.mp.getUser();
		title=(TextView)findViewById(R.id.xinjianinfo_text);
		content=(TextView)findViewById(R.id.xinjianinfo_content);
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
		
	}
	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
//			Toast.makeText(context, "cancle", 1000).show();
			finish();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(xinjianbean!=null){
				if("200".equals(xinjianbean.getCode())){
					title.setText(xinjianbean.getTitle());
					content.setText(Html.fromHtml(xinjianbean.getContent()));
				}else if("300".equals(xinjianbean.getCode())){
					MyApplication.mp.setlogin(false);
					Util.ShowToast(context, R.string.login_out_time);
					intent = new Intent(context,LoginActivity.class);
					startActivity(intent);
					finish();
				}else{
					Util.ShowToast(context, xinjianbean.getMsg());
				}
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			
			
			
			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			Send s = new Send(context);
			xinjianbean = s.getXinjianDetaile(xinjianid, bean.getAuthstr());
			return true;
		}
	}

}
