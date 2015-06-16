package com.xunbo.store.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.adapters.MyOrderInfoAdapter;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.beans.OrderDetaileBean;
import com.xunbo.store.myviews.MyListview;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class MyOrderInfoActivity extends BaseActivity implements
OnClickListener {
	private TextView tv_yunfeimoney,tv_ordermoney,tv_uname,tv_uphone,
	tv_uaddress,tv_cname,tv_yunfei,tv_rmoney,tv_oid,tv_otime,tv_no,tv_ok;
	private MyListview listview;
	private MyOrderInfoAdapter myOrderInfoAdapter;
	private ArrayList<Integer> list;
	private ThreadWithProgressDialog myPDT;
	private Context context;
	private String itemid;
	private int statusid;
	private LinearLayout ll_isshow;
	private boolean initdata;
	private String authstr,coupon,password;
	private OrderDetaileBean ordebean;
	private int tp;
	private PopupWindow popwindow;
	private View v_pop;
	private BaseBean baseBean;
	
	
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		topRightTGone();
		setTopLeftTv(R.string.myorderinfo_title);
		setContentXml(R.layout.myorderinfo);
		init();
	}
	private void init(){
		TestinAgent.init(this);
		context = this;
		initdata = true;
		authstr = MyApplication.mp.getUser().getAuthstr();
		itemid = getIntent().getStringExtra("itemid");
		coupon = getIntent().getStringExtra("coupon");
		statusid = Integer.parseInt(getIntent().getStringExtra("statusid"));
		myPDT = new ThreadWithProgressDialog();
		tv_yunfeimoney=(TextView)findViewById(R.id.myorderinfo_yunfeimoney);
		tv_yunfeimoney.setText("运费金额：￥");
		tv_ordermoney=(TextView)findViewById(R.id.myorderinfo_ordermoney);
		tv_ordermoney.setText("订单金额：+￥");
		tv_uname=(TextView)findViewById(R.id.myorderinfo_username);
		tv_uphone=(TextView)findViewById(R.id.myorderinfo_userphone);
		tv_uaddress=(TextView)findViewById(R.id.myorderinfo_useraddress);
		tv_cname=(TextView)findViewById(R.id.myorderinfo_computername);
		tv_yunfei=(TextView)findViewById(R.id.myorderinfo_yunfei);
		tv_rmoney=(TextView)findViewById(R.id.myorderinfo_realmoney);
		tv_oid=(TextView)findViewById(R.id.myorderinfo_orderid);
		tv_otime=(TextView)findViewById(R.id.myorderinfo_ordertime);
		tv_ok=(TextView)findViewById(R.id.myorderinfo_ok);
		tv_no=(TextView)findViewById(R.id.myorderinfo_quxiao);
		ll_isshow = (LinearLayout) findViewById(R.id.ll_isshow);
		if(statusid==1){
			ll_isshow.setVisibility(View.VISIBLE);
		}else{
			ll_isshow.setVisibility(View.GONE);
		}
		v_pop = LayoutInflater.from(context).inflate(R.layout.money_password, null);
		popwindow = new PopupWindow(v_pop, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		popwindow.setOutsideTouchable(true);
		popwindow.setFocusable(true);
		popwindow.setBackgroundDrawable(new BitmapDrawable());
		tv_ok.setOnClickListener(this);
		tv_no.setOnClickListener(this);
		listview=(MyListview)findViewById(R.id.myorderinfo_list);
		String ms = getResources().getString(R.string.loding);
		if(Util.detect(context)){
		myPDT.Run(context, new RefeshData(),ms,false);//可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.myorderinfo_ok:
			initdata = false;
			tp = 2;
			TextView money = (TextView) v_pop.findViewById(R.id.money_password_money);
			EditText et = (EditText) v_pop.findViewById(R.id.money_password_edpassword);
			money.setText("￥"+ordebean.getMoney());
			et.setText("");
			popwindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0, 0);
			Button btn_pay = (Button) v_pop.findViewById(R.id.money_password_yes);
			Button btn_cancle = (Button) v_pop.findViewById(R.id.money_password_no);	
			btn_cancle.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					popwindow.dismiss();
				}
			});
			btn_pay.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					EditText et = (EditText) v_pop.findViewById(R.id.money_password_edpassword);
					password = et.getText().toString();
					popwindow.dismiss();
					if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
					}else{
						Util.ShowToast(context, R.string.net_is_eor);
					}
				}
			});		
			break;
		case R.id.myorderinfo_quxiao:
			initdata = false;
			tp = 1;
			if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			break;
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
				if(initdata){
					if(ordebean!=null){
						if("200".equals(ordebean.getCode())){
							tv_yunfeimoney.setText("运费金额：￥"+ordebean.getFee());
							tv_ordermoney.setText("订单金额：+￥"+ordebean.getMoney());
							tv_uname.setText(ordebean.getBuyer_name());
							tv_uaddress.setText(ordebean.getBuyer_address());
							tv_uphone.setText(ordebean.getBuyer_mobile());
							tv_cname.setText(ordebean.getCompany());
							tv_yunfei.setText("￥"+ordebean.getFee());
							tv_rmoney.setText("￥"+ordebean.getMoney());
							tv_oid.setText("订单号："+ordebean.getItemid());
							tv_otime.setText("下单时间："+ordebean.getAddtime());
							myOrderInfoAdapter=new MyOrderInfoAdapter(context,ordebean);
							listview.setAdapter(myOrderInfoAdapter);
						}else if("300".equals(ordebean.getCode())){
							MyApplication.mp.setlogin(false);
							Util.ShowToast(context, R.string.login_out_time);
							Intent intent = new Intent(context,LoginActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							finish(); 
						}else{
							Util.ShowToast(context,ordebean.getMsg() );
						}
					}else{
						Util.ShowToast(context, R.string.net_is_eor);
					}
				}else{
					if(baseBean!=null){
						if("200".equals(baseBean.getCode())){
							Util.ShowToast(context, "操作成功！");
							MyOrderActivity.isinit =true;
							finish();
						}
						else if("300".equals(baseBean.getCode())){
							MyApplication.mp.setlogin(false);
							Util.ShowToast(context, R.string.login_out_time);
							Intent intent = new Intent(context,LoginActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							finish(); 
						}else{
							Util.ShowToast(context, baseBean.getMsg());
						}
					}else{
						Util.ShowToast(context, R.string.net_is_eor);
					}
				}
				
				return true;
			}

			@Override
			public boolean TaskMain() {
				// 访问
				Send s = new Send(context);
				PostHttp p = new PostHttp(context);
				if(initdata){
				ordebean = p.getOrderDetaile(itemid,authstr);
				}else{
					if(tp==1){
						baseBean = p.Order_close(itemid, authstr);
					}else{
						baseBean = p.PayOrder(itemid, coupon, authstr, password);
					}
				}
				
				return true;
			}
		}


}
