package com.example.educonsult.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.beans.AreaBean;
import com.example.educonsult.beans.ListAreaBean;
import com.example.educonsult.beans.ListComment;
import com.example.educonsult.beans.ListFenleiBean;
import com.example.educonsult.beans.ListMoneyBean;
import com.example.educonsult.beans.ListProductBean;
import com.example.educonsult.beans.ProdectDetaileBean;
import com.example.educonsult.net.PostHttp;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.Util;

public class WelcomeActivity extends Activity{
	protected int activityCloseEnterAnimation;

	protected int activityCloseExitAnimation;
	private Handler hand;
	private AreaBean list;
	private Context context;
	private ListAreaBean lb;
	private ThreadWithProgressDialog myPDT;
	private String auth = "kn769osgurrqjrl8ljuk0boik4";
	private String filename = MyApplication.AreaName; 
	private Util u;
	private ProdectDetaileBean bean;
	private ListFenleiBean lf;
	private boolean ct,fl;
	ListComment lbn;
	ListProductBean l;
	ListMoneyBean lmoney;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});
		int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);      
		activityStyle.recycle();
		activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
		activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
		activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
		activityStyle.recycle();
		setContentView(R.layout.welcome);
		hand = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if (msg.what == 1) {
					Intent intent = new Intent(WelcomeActivity.this,
							LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				}
			}
		};
		init();

	}
	private void init() {
		context = this;
		ct = false;
		fl = false;
		myPDT = new ThreadWithProgressDialog();
		String msg = "初始化...";
		u = new Util(context);
		//		if(u.isExistDataCache(filename)&& u.isReadDataCache(filename)){
		//			Finish2min();
		//		}else{
		//			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		myPDT.Run(context, new RefeshData(),msg,false);//不可取消
		//		}
	}
	private void Finish2min(){
		new Thread() {
			public void run() {
				try {
					sleep(2000);
					Message msg = hand.obtainMessage();
					msg.what = 1;
					hand.sendMessage(msg);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			};
		}.start();
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
			if(lb!=null){
				if("200".equals(lb.getCode())){
					u.saveObject(lb, filename);
					ct = true;
				}else{
					Util.ShowToast(context, lb.getMsg());
				}
			}
			//if(bean!=null){
			//	if("200".equals(bean.getCode())){
			//		Intent intent = new Intent(WelcomeActivity.this,
			//				LoginActivity.class);
			//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//		startActivity(intent);
			//		finish();
			//	}
			//}
//			if(lf!=null){
//				if("200".equals(lf.getCode())){
//					u.saveObject(lf, filename);
//					fl = true;			
//				}else{
//					Util.ShowToast(context, lf.getMsg());
//				}
//			}
//			if(ct && fl){
//				Intent intent = new Intent(WelcomeActivity.this,
//						LoginActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(intent);
//				finish();
//			}else{
//				Util.ShowToast(context, "初始化失败,请保证网络通畅");
//			}
//			if(lbn!=null){
//				if("200".equals(lbn.getCode())){
//					Tologin();
//				}
//			}
//			if(l!=null){
//				if("200".equals(l.getCode())){
//					
//			if(lf!=null){
//				if("200".equals(lf.getCode())){
//					u.saveObject(lf, filename);
//					fl = true;			
//				}else{
//					Util.ShowToast(context, lf.getMsg());
//				}
//			}
//			if(lmoney!=null){
//				if("200".equals(lmoney.getCode())){
//					
//				}
//			}
			Tologin();
			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			Send s = new Send(context);
//			lb = s.GetArea();
			//			bean = s.GetProductDetaile("512");
//			lf = s.GetFenlei();
//			lbn = s.GetComment("53", 1,"2");
//			l = s.getCenterRecommend();
//			s.getXinjianDetaile("1057", auth);
			PostHttp p = new PostHttp(context);
			p.doPost();
			return true;
		}
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
	}
	private void Tologin(){
		Intent intent = new Intent(WelcomeActivity.this,
				LoginActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}
	
}
