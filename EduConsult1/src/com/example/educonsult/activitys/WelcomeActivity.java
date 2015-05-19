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
import com.example.educonsult.ExampleActivity;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.beans.AreaBean;
import com.example.educonsult.beans.ListAreaBean;
import com.example.educonsult.beans.ListComment;
import com.example.educonsult.beans.ListFenleiBean;
import com.example.educonsult.beans.ListMoneyBean;
import com.example.educonsult.beans.ListOrderBean;
import com.example.educonsult.beans.ListOrderCommit;
import com.example.educonsult.beans.ListProductBean;
import com.example.educonsult.beans.ListShopBean;
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
		context = this;
		ct = false;
		fl = false;
		myPDT = new ThreadWithProgressDialog();
		String msg = "初始化...";
		u = new Util(context);
		if(u.isExistDataCache(filename_area)&& u.isReadDataCache(filename_area) ||
				u.isExistDataCache(filename_fen)&& u.isReadDataCache(filename_fen) ){
			Finish2min();
		}else{
			//			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
			myPDT.Run(context, new RefeshData(),msg,false);//不可取消
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
					u.saveObject(lb, filename_area);
					ct = true;
				}else{
					Util.ShowToast(context, lb.getMsg());
				}
			}
			//			if(bean!=null){
			//				if("200".equals(bean.getCode())){
			//					Intent intent = new Intent(WelcomeActivity.this,
			//							LoginActivity.class);
			//					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//					startActivity(intent);
			//					finish();
			//				}
			//			}
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
			}else{
				Util.ShowToast(context, "初始化失败,请保证网络通畅");
			}
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
			lb = s.GetArea();
			lf = s.GetFenlei();
			PostHttp p = new PostHttp(context);
			String authstr = "6p2uau4j93ahcfil0181adt8k4";
			//			bean = s.GetProductDetaile("512");
			//			lbn = s.GetComment("53", 1,"2");
			//			l = s.getCenterRecommend();
			//			s.getXinjianDetaile("1057", auth);
			//			s.CartAdd("53",1, authstr);
			//						ListShopBean l = s.getCartlist(authstr);
			//			p.Jiesuan(l, authstr);
			//			ListAddressBean l = s.getAddressList(authstr);
			//			AddressBean ab = l.getList().get(0);
			//			AddressBean ab = new AddressBean();
			//			p.addOneAddress(ab, authstr);
			//			p.editOneAddress(ab,authstr);
			//			p.SetDetaultAddress(ab,authstr);
			//			p.DelAddress(ab,authstr);
			//			ListBanksBean l = p.getBanksList(authstr);
			//			ListBanksCity lc = p.getBanksCityList(1,l.getProvince().get(0), authstr);
			//			ListBanksCity lxian = p.getBanksCityList(2,lc.getList().get(0).getCity(), authstr);
			//			String code  = "";
			//			if(lxian.getList().size()>0){
			//				code = lxian.getList().get(0).getAreacode();
			//			}else{
			//				code = lc.getList().get(0).getAreacode();
			//			}
			//			ListBanksBranch lbb = p.getBanksBeanchList(104,code , "", authstr);
			//			p.bindBankcart(lbb.getList().get(0).getBanknumber(), "0000000000000000000", "test", authstr, 104);
			//			ListOrderCommit lp = p.CommitOrder(l, authstr);
			//			final ListOrderBean lo = s.getOrderList(0,1,authstr);
			//			if(lo!=null){
			//			id = lo.getList_order().get(4).getItemid();
			//			c = lo.getList_order().get(4).getCoupons();
			//			p.Order_refund(, 1, "", authstr);
			//p.PayOrder(id,c, authstr,"123456");
			//			p.Order_pay(id, "123456", c, authstr);
			//			}
			//p.Order_sendgoods(id, 1, "", "", "", authstr);
			//			p.Order_getwuliulist("254", authstr);
			//p.Order_close("261", authstr);
			//p.Order_confirmpay(id, authstr);
			//			p.Order_refunddata("260", authstr);
			//			p.Order_refund("260", 1, "测试", authstr);
			//			p.Order_refundinfo("260", authstr);
			//			p.getOrderDetaile(lo.getList_order().get(0).getItemid(), authstr);
			//			p.Order_getpay("253", authstr);
			//			p.Order_comment("253", 3, "test", authstr);
			//			s.getOrderRefundList(2, authstr);
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
