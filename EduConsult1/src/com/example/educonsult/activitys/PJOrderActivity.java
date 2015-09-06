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

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.tools.Util;


public class PJOrderActivity extends BaseActivity implements OnClickListener {
	private LinearLayout ll_l,ll_t,ll_r; 
	private EditText ed;
	private Button button;
	private ImageView imaone,imatwo,imathree;
	@SuppressWarnings("unused")
	private int isgood=5;
	private Context context;
	private ThreadWithProgressDialog myPDT;
	private BaseBean bean;
	private String itemid;
	private int star;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setTitleTxt(R.string.pingjiadingdan_title);
		setContentXml(R.layout.pjorder);
		init();
		topRightTGone();
	}
	void init(){
		TestinAgent.init(this);
		context=this;
		star = 3;
		myPDT = new ThreadWithProgressDialog();
		ll_l=(LinearLayout)findViewById(R.id.pjorder_one_lin);
		ll_l.setOnClickListener(this);
		ll_t=(LinearLayout)findViewById(R.id.pjorder_two_lin);
		ll_t.setOnClickListener(this);
		ll_r=(LinearLayout)findViewById(R.id.pjorder_three_lin);
		ll_r.setOnClickListener(this);
		ed=(EditText)findViewById(R.id.pjorder_ed);
		button=(Button)findViewById(R.id.pjorder_ok);
		button.setOnClickListener(this);
		imaone=(ImageView)findViewById(R.id.pjorder_one);
		imathree=(ImageView)findViewById(R.id.pjorder_three);
		imatwo=(ImageView)findViewById(R.id.pjorder_two);
		isgood=5;
		itemid = getIntent().getStringExtra("itemid");
	}
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.pjorder_one_lin:
			imaone.setBackgroundResource(R.drawable.check_ed);
			imatwo.setBackgroundResource(R.drawable.check_un);
			imathree.setBackgroundResource(R.drawable.check_un);
			isgood=1;
			star = 3;
			break;
		case R.id.pjorder_two_lin:
			imatwo.setBackgroundResource(R.drawable.check_ed);
			imaone.setBackgroundResource(R.drawable.check_un);
			imathree.setBackgroundResource(R.drawable.check_un);
			isgood=3;
			star = 2;
			break;
		case R.id.pjorder_three_lin:
			imathree.setBackgroundResource(R.drawable.check_ed);
			imatwo.setBackgroundResource(R.drawable.check_un);
			imaone.setBackgroundResource(R.drawable.check_un);
			isgood=5;
			star = 1;
			break;
		case R.id.pjorder_ok:
			String edStr=ed.getText().toString().trim();
			if(Util.IsNull(edStr)){
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(edStr),R.string.loding);//不可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				Util.ShowToast(context, "评论信息不能为空");
			}
			break;

		default:
			break;
		}
	}
	// 任务
	public class RefeshData implements ThreadWithProgressDialogTask {
		private String edStr;
		public RefeshData(String edStr) {
			this.edStr =edStr;
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			//				Toast.makeText(context, "cancle", 1000).show();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(bean!=null){
				if("200".equals(bean.getCode())){
					Util.ShowToast(context, "评价完成！");	
					MyOrderActivity.isinit = true;
					finish();
				}else if("300".equals(bean.getCode())){
					MyApplication.mp.setlogin(false);
					Util.ShowToast(context, R.string.login_out_time);
					Intent i= new Intent(context,LoginActivity.class);
					startActivity(i);
					finish();
				}else{
					Util.ShowToast(context, bean.getMsg());	
				}
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			PostHttp p  = new PostHttp(context);
			if(Util.IsNull(itemid)){
				bean = p.Order_comment(itemid, star, edStr, MyApplication.mp.getUser().getAuthstr());
			}


			return true;
		}
	}
}
