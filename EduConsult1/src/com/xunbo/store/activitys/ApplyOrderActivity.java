package com.xunbo.store.activitys;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.testin.agent.TestinAgent;
import com.umeng.analytics.MobclickAgent;
import com.xunbo.store.ExampleActivity;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.adapters.ApplyOrderHomeAdapter;
import com.xunbo.store.beans.ListOrderBean;
import com.xunbo.store.beans.OrderBean;
import com.xunbo.store.myviews.xlistview.XListView;
import com.xunbo.store.myviews.xlistview.XListView.IXListViewListener;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;
/**
 * 退货订单列表
 * @author Qzr
 *
 */
public class ApplyOrderActivity extends BaseActivity implements IXListViewListener{
	private Context context;
	private XListView lv;
	private ApplyOrderHomeAdapter adapter;
	private Intent intent;
	private ThreadWithProgressDialog myPDT;
	private String itemid,authstr;
	private ListOrderBean lb;
	private MyApply apply;
	private LinearLayout ll_isnull;
	private ArrayList<OrderBean> list;
	private TextView see;
	private int addtype,page;
	private Handler handler;
	private boolean isloding;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTitleTxt(R.string.applyinfo_title);
		topRightTGone();
		setContentXml(R.layout.apply_home_layout);
		init();
		addlistener();
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}

	}



	private void addlistener() {
//		lv.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//			
//			}
//		});
//		
		see.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
				ExampleActivity.setCurrentTab(0);
			}
		});	
	}



	private void init() {
		TestinAgent.init(this);
		context = this;
		isloding = false;
		page =1;
		itemid = getIntent().getStringExtra("itemid");
		authstr = MyApplication.mp.getUser().getAuthstr();
		myPDT = new ThreadWithProgressDialog();
		String  msg = getResources().getString(R.string.loding);
		lv = (XListView) findViewById(R.id.apply_home_lv);
		lv.setPullRefreshEnable(true);
		lv.setPullLoadEnable(true);
		lv.setXListViewListener(this);
		ll_isnull = (LinearLayout) findViewById(R.id.apply_home_ll_isnull);
		lv.setEmptyView(ll_isnull);
		see = (TextView) findViewById(R.id.apply_home_tv_isnull);
		apply = new MyApply() {
			
			@Override
			public void detaile(String itemid) {
				intent = new Intent(context,ApplyInfoActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("itemid", itemid);
				startActivity(intent);
			}
		};
		handler = new Handler(){
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what==1){
					if(page==1){
						list = (ArrayList<OrderBean>) msg.obj;
					}else{
						ArrayList<OrderBean> ll = (ArrayList<OrderBean>) msg.obj;
						list.addAll(ll);
						if(ll.size()==0){
							Util.ShowToast(context, R.string.page_is_final);
							page-=1;
						}
					}
					if(adapter!=null){
						adapter.SetData(list);
						adapter.notifyDataSetChanged();
					}else{
						adapter = new ApplyOrderHomeAdapter(context, list, apply,isloding);
						lv.setAdapter(adapter);
					}
				}else if(msg.what==2){
					intent = new Intent(context,LoginActivity.class);
					startActivity(intent);
					finish();
				}else{
					String s = (String) msg.obj;
					Util.ShowToast(context, s);
				}
				onLoad();
			}
		};
	}

	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			if(lb!=null){
				if("200".equals(lb.getCode())){
					list = lb.getList_order();
					if(adapter!=null){
						adapter.SetData(list);
						adapter.notifyDataSetChanged();
					}else{
						adapter = new ApplyOrderHomeAdapter(context, list, apply,isloding);
						lv.setAdapter(adapter);
					}
				}else if("300".equals(lb.getCode())){
					MyApplication.mp.setlogin(false);
					Util.ShowToast(context, R.string.login_out_time);
					intent = new Intent(context,LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish(); 
				}else{
					Util.ShowToast(context, lb.getMsg());
				}
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}



			return true;
		}

		@Override
		public boolean TaskMain() {
			PostHttp p = new PostHttp(context);
			Send s = new Send(context);
			lb = s.getOrderRefundList(page, authstr);
			return true;
		}
	}

	public interface MyApply{
		void detaile(String itemid);
	}

	@Override
	public void onRefresh() {
		page=1;
		addtype = 2;
		isloding = true;
		adapter.SetBoolean(isloding);
		getData();
	}



	@Override
	public void onLoadMore() {
		page+=1;
		addtype = 2;
		isloding = true;
		adapter.SetBoolean(isloding);
		getData();
	}

	private void getData(){
		new Thread(){public void run() {
			Send s = new Send(context);
			lb = s.getOrderRefundList(page, authstr);
			Message msg = handler.obtainMessage();
			if(lb!=null){
				if("200".equals(lb.getCode())){
					ArrayList<OrderBean>lt = lb.getList_order();
					msg.what=1; 
					msg.obj = lt;
				}else if("200".equals(lb.getCode())){
					msg.what=3;
				}else{
					msg.what=2;
					msg.obj = lb.getMsg();
				}
			}else{
				String ss = context.getResources().getString(R.string.net_is_eor);
				msg.what=2;
				msg.obj = ss;
			}
			handler.sendMessage(msg);
		};}.start();
	}
	
	
	private void onLoad() {
		lv.stopRefresh();
		lv.stopLoadMore();
		isloding = false;
		adapter.SetBoolean(isloding);
		if(addtype==1){
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd   hh:mm:ss");
			String date = sDateFormat.format(new java.util.Date());
			lv.setRefreshTime(date);
		}
	}
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart( "ApplyOrderActivity" );
		MobclickAgent.onResume(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd( "ApplyOrderActivity" );
		MobclickAgent.onPause(this);
	}

}
