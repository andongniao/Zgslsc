package com.example.educonsult.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.ExampleActivity;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.beans.CenterUserBean;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.Util;

public class MyInfoActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_head,ll_friend,ll_mycard;
	private Context context;
	private Intent intent;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	public static boolean isread;
	private TextView mycardNum;
	private int cardNum=0;
	private ThreadWithProgressDialog myPDT;
	private CenterUserBean centerbean;
	private UserBean bean;


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightLVisible();
		topRightRVisible();
		topRightTGone();
		rl_l = (RelativeLayout) getTopLightRl();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_l = (ImageView) getTopLightView();
		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		setTopLeftTv(R.string.myinfo_title);
		setContentXml(R.layout.myinfo);
		init();
		addlistener();
//		if(Util.detect(context)){
//			myPDT.Run(context, new RefeshData(),msg,false);//不可取消
//		}
		
		
		
	}

	private void addlistener() {
		rl_l.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(context,XinjianActivity.class);
				intent.putExtra("flag", "myinfo");
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

	private void init() {
		context = this;
		bean = MyApplication.mp.getUser();
		ll_head = (LinearLayout) findViewById(R.id.myinfo_ll_head);
		ll_head.setOnClickListener(this);
		ll_friend = (LinearLayout) findViewById(R.id.myinfo_ll_friend);
		ll_friend.setOnClickListener(this);
		ll_mycard = (LinearLayout) findViewById(R.id.myinfo_ll_mycard);
		ll_mycard.setOnClickListener(this);
		mycardNum=(TextView)findViewById(R.id.myinfo_ll_mycard_num);
		if(cardNum==0){
			mycardNum.setText("未绑定");
			mycardNum.setTextColor(getResources().getColor(R.color.red));
		}else{
			mycardNum.setText(cardNum+"张");
		}
		Util.SetRedNum(context, rl_l, 1);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myinfo_ll_head:

			break;
		case R.id.myinfo_ll_mycard:
			intent = new Intent(context,BDCardActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.myinfo_ll_friend:
			intent = new Intent(context,MyBusinessperntActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(isread){
			Util.SetRedGone(context, rl_l);
			isread = false;
		}
	}
	
	
	
	
	// 任务
		public class RefeshData implements ThreadWithProgressDialogTask {

			public RefeshData() {
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
				if(centerbean!=null){
					if("200".equals(centerbean.getCode())){
						//TODO	
					}else{
						Util.ShowToast(context, centerbean.getMsg());
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
				centerbean = s.getMyinfo(bean.getType(), bean.getAuthstr());
				return true;
			}
		}
	

}
