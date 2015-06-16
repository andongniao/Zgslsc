package com.xunbo.store.activitys;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.testin.agent.TestinAgent;
import com.xunbo.store.ExampleActivity;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.beans.AreaBean;
import com.xunbo.store.beans.ListAreaBean;
import com.xunbo.store.beans.ListComment;
import com.xunbo.store.beans.ListFenleiBean;
import com.xunbo.store.beans.ListMoneyBean;
import com.xunbo.store.beans.ListProductBean;
import com.xunbo.store.beans.ProdectDetaileBean;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class WelcomeActivity extends Activity{
	protected int activityCloseEnterAnimation;

	protected int activityCloseExitAnimation;
	private Handler hand;
	private AreaBean list;
	private Context context;
	private ListAreaBean lb;
	private ThreadWithProgressDialog myPDT;
	private String auth = "kn769osgurrqjrl8ljuk0boik4";
	private String filename_area = MyApplication.AreaName; 
	private String filename_fen = MyApplication.FenleiName; 
	private Util u;
	private ProdectDetaileBean bean;
	private ListFenleiBean lf;
	private boolean ct,fl;
	ListComment lbn;
	ListProductBean l;
	ListMoneyBean lmoney;
	String id,c;

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
					Intent intent;
					if(!MyApplication.mp.islogin){
					intent = new Intent(WelcomeActivity.this,
							LoginActivity.class);
					}else{
						intent = new Intent(WelcomeActivity.this,
								ExampleActivity.class);
					}
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish();
				}
			}
		};
		init();

	}
	private void init() {
		TestinAgent.init(this);
		context = this;
		ct = false;
		fl = false;
		myPDT = new ThreadWithProgressDialog();
		String msg = "��ʼ��...";
		u = new Util(context);
		if(u.isExistDataCache(filename_area)&& u.isReadDataCache(filename_area) ||
				u.isExistDataCache(filename_fen)&& u.isReadDataCache(filename_fen) ){
			Finish2min();
		}else{
			//			myPDT.Run(context, new RefeshData(),R.string.loding);//��ȡ��
			myPDT.Run(context, new RefeshData(),msg,false);//����ȡ��
		}
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


	// ����
	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//����ȡ��
			//				Toast.makeText(context, "cancle", 1000).show();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//������ɺ�
			if(lb!=null){
				if("200".equals(lb.getCode())){
					u.saveObject(lb, filename_area);
					ct = true;
				}else{
					Util.ShowToast(context, lb.getMsg());
				}
			}
			if(lf!=null){
				if("200".equals(lf.getCode())){
					u.saveObject(lf, filename_fen);
					fl = true;			
				}else{
					Util.ShowToast(context, lf.getMsg());
				}
			}
			if(ct && fl){
				Intent intent = new Intent(WelcomeActivity.this,
						LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
				Tologin();
			}else{
				Util.ShowToast(context, "��ʼ��ʧ��,�뱣֤����ͨ��������");
			}
			return true;
		}

		@Override
		public boolean TaskMain() {
			// ����
			Send s = new Send(context);
			lb = s.GetArea();
			lf = s.GetFenlei();
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