package com.example.educonsult.activitys;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
import com.example.educonsult.adapters.MyOrderHomeAdapter;
import com.example.educonsult.beans.ListOrderBean;
import com.example.educonsult.beans.OrderBean;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.myviews.xlistview.XListView;
import com.example.educonsult.myviews.xlistview.XListView.IXListViewListener;
import com.example.educonsult.net.PostHttp;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.Util;

public class MyOrderActivity extends BaseActivity implements OnClickListener,IXListViewListener{
	private Context context;
	private LinearLayout ll_all,ll_pay,ll_send,ll_shouhuo,ll_comment,ll_isnull;
	private TextView tv_all,tv_show_all,tv_pay,tv_show_pay,tv_send,tv_show_send,
	tv_shouhuo,tv_show_shouhuo,tv_comment,tv_show_comment,tv_isnull;
	private XListView lv;
	private ArrayList<OrderBean> list;
	private ArrayList<View> view_list;
	private MyOrderHomeAdapter adapter;
	private Myorder myorder;
	private ImageView iv_top_r;
	private RelativeLayout rl_r;
	private Intent intent;
	private ThreadWithProgressDialog myPDT;
	private int type,addtype,pag,index;
	private String tag;
	private UserBean userbean;
	private ListOrderBean listbean;
	private boolean init;
	private OrderBean ob;
	private int add;//1关闭订单 2买家支付 3确认收货（确认支付）4确认发货5申请退款6评价订单

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTopLeftTv(R.string.myorder_title);
		topRightRVisible();
		topRightTGone();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_r = (ImageView) getTopRightView();
		iv_top_r.setBackgroundResource(R.drawable.top_home_bg);
		setContentXml(R.layout.myorder_home_layout);
		init();
		addlistener();
		/*************测试******** ********/
		myPDT = new ThreadWithProgressDialog();
		String  msg = getResources().getString(R.string.loding);
		myPDT.Run(context, new RefeshData(init,type,pag,tag),R.string.loding);//可取消
	}

	private void addlistener() {
		iv_top_r.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ExampleActivity.setCurrentTab(0);
				finish();
			}
		});
	}

	private void init() {
		context = this;
		init = true;
		userbean = MyApplication.mp.getUser();
		pag = 1;
		type=0;
		tag = userbean.getAuthstr();
		myorder =new Myorder() {
			@Override
			public void Order_Repay(OrderBean orderBean) {
				ob = orderBean;
				init = false;
				add = 3;
				ob = orderBean;
				myPDT.Run(context, new RefeshData(init,type,pag,tag),R.string.loding);//可取消
			}
			
			@Override
			public void Order_commit(OrderBean orderBean) {
				ob = orderBean;
				init = false;
				add = 6;
				intent = new Intent(context,PJOrderActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("itemid", orderBean.getItemid());
				intent.putExtra("statusid", orderBean.getItemid());
				startActivity(intent);
			}
			
			@Override
			public void Order_Refund(OrderBean orderBean) {
				ob = orderBean;
				init = false;
				add = 5;
				myPDT.Run(context, new RefeshData(init,type,pag,tag),R.string.loding);//可取消
			}
			
			@Override
			public void Order_Pay(OrderBean orderBean) {
				ob = orderBean;
				init = false;
				add = 2;
				myPDT.Run(context, new RefeshData(init,type,pag,tag),R.string.loding);//可取消
			}
			
			@Override
			public void Order_Fahuo(OrderBean orderBean) {
				ob = orderBean;
				init = false;
				add = 4;
				myPDT.Run(context, new RefeshData(init,type,pag,tag),R.string.loding);//可取消
			}
			
			@Override
			public void Order_Canale(OrderBean orderBean) {
				ob = orderBean;
				init = false;
				add= 1;
				myPDT.Run(context, new RefeshData(init,type,pag,tag),R.string.loding);//可取消
			}

			@Override
			public void Order_Detaile(OrderBean orderBean) {
				ob = orderBean;
				int t = Integer.parseInt(orderBean.getStatusid());
				if(t==5 || t==6 || t==7){
					intent = new Intent(context,ApplyInfoActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("itemid", orderBean.getItemid());
					intent.putExtra("statusid", orderBean.getStatusid());
					startActivity(intent);
				}else{
					intent = new Intent(context,MyOrderInfoActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("itemid", orderBean.getItemid());
					intent.putExtra("statusid", orderBean.getStatusid());
					startActivity(intent);
				}
				
			}
		};
		index = getIntent().getIntExtra("index", 0);
		view_list = new ArrayList<View>();
		ll_isnull = (LinearLayout) findViewById(R.id.myorder_home_ll_isnull);
		tv_isnull = (TextView) findViewById(R.id.myorder_home_tv_isnull);
		tv_isnull.setOnClickListener(this);
		lv = (XListView) findViewById(R.id.myorder_home_lv);
		lv.setEmptyView(ll_isnull);
		ll_all = (LinearLayout) findViewById(R.id.myorder_home_ll_all);
		ll_all.setOnClickListener(this);
		ll_pay = (LinearLayout) findViewById(R.id.myorder_home_ll_pay);
		ll_pay.setOnClickListener(this);
		ll_send = (LinearLayout) findViewById(R.id.myorder_home_ll_send);
		ll_send.setOnClickListener(this);
		ll_shouhuo = (LinearLayout) findViewById(R.id.myorder_home_ll_shouhuo);
		ll_shouhuo.setOnClickListener(this);
		ll_comment = (LinearLayout) findViewById(R.id.myorder_home_ll_comment);
		ll_comment.setOnClickListener(this);
		tv_all = (TextView) findViewById(R.id.myorder_home_tv_all);
		tv_pay = (TextView) findViewById(R.id.myorder_home_tv_pay);
		tv_send = (TextView) findViewById(R.id.myorder_home_tv_send);
		tv_shouhuo = (TextView) findViewById(R.id.myorder_home_tv_shouhuo);
		tv_comment = (TextView) findViewById(R.id.myorder_home_tv_comment);

		tv_show_all = (TextView) findViewById(R.id.myorder_home_tv_all_show);
		tv_show_pay = (TextView) findViewById(R.id.myorder_home_tv_pay_show);
		tv_show_send = (TextView) findViewById(R.id.myorder_home_tv_send_show);
		tv_show_shouhuo = (TextView) findViewById(R.id.myorder_home_tv_shouhuo_show);
		tv_show_comment = (TextView) findViewById(R.id.myorder_home_tv_comment_show);
		view_list.add(tv_show_all);
		view_list.add(tv_show_pay);
		view_list.add(tv_show_send);
		view_list.add(tv_show_shouhuo);
		view_list.add(tv_show_comment);
//		adapter = new MyOrderHomeAdapter(context, list,myorder);
//		lv.setAdapter(adapter);
		change(index);

		if(list!=null && list.size()>0){
			ll_isnull.setVisibility(View.GONE);
			lv.setVisibility(View.VISIBLE);
		}else{
			ll_isnull.setVisibility(View.VISIBLE);
			lv.setVisibility(View.GONE);
		}
		Util.SetRedNum(context, rl_r, 1);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myorder_home_ll_all:
			change(0);
			break;
		case R.id.myorder_home_ll_pay:
			change(1);
			break;
		case R.id.myorder_home_ll_send:
			change(2);
			break;
		case R.id.myorder_home_ll_shouhuo:
			change(3);
			break;
		case R.id.myorder_home_ll_comment:
			change(4);
			break;
		case R.id.myorder_home_tv_isnull:
			ExampleActivity.setCurrentTab(0);
			finish();
			break;
		}
	}

	private void change(int index){
		for(int i=0;i<view_list.size();i++){
			if(index==i){
				view_list.get(i).setVisibility(View.VISIBLE);
				type = i;
				init = true;
				myPDT.Run(context, new RefeshData(init,type,pag,tag),R.string.loding);//可取消
			}else{
				view_list.get(i).setVisibility(View.INVISIBLE);
			}
		}
	}
	public interface Myorder{
		//状态码 1待付款 2代发货 3待收货 4交易成功
		/**
		 * 取消（关闭）订单
		 * @param index
		 */
		void Order_Canale(OrderBean orderBean);
		/**
		 * 买家付款（预付款）
		 * @param index
		 */
		void Order_Pay(OrderBean orderBean);
		/**
		 * 买家确认收货（确认付款）
		 * @param index
		 */
		void Order_Repay(OrderBean orderBean);
		/**
		 * 卖家确认发货
		 * @param index
		 */
		void Order_Fahuo(OrderBean orderBean);
		/**
		 * 申请退货（退款）
		 * @param index
		 */
		void Order_Refund(OrderBean orderBean);
		/**
		 * 评价订单
		 * @param index
		 */
		void Order_commit(OrderBean orderBean);
		/**
		 * 订单详情（退款详情）
		 * @param index
		 */
		void Order_Detaile(OrderBean orderBean);
	}

	
	// 任务
		public class RefeshData implements ThreadWithProgressDialogTask {
			private int step;
			private int page;
			private String authstr;
			private boolean init;

			public RefeshData(boolean init,int step,int page,String authstr) {
				this.init = init;
				this.step = step;
				this.page = page; 
				this.authstr = authstr;
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
				if(listbean!=null){
					if("200".equals(listbean.getCode())){
						list = listbean.getList_order();
						adapter = new MyOrderHomeAdapter(context, list,myorder);
						lv.setAdapter(adapter);
					}else{
						Util.ShowToast(context, listbean.getMsg());
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
				PostHttp p = new PostHttp(context);
				if(init){
					listbean = s.getOrderList(step, page, authstr);
				}else{
					//1关闭订单 2买家支付 3确认收货（确认支付）4确认发货5申请退款6评价订单
					if(add==1){
						p.Order_close(ob.getItemid(), authstr);
					}else if(add==2){
						p.PayOrder(ob.getItemid(), ob.getCoupons(), authstr, "")
					}else if(add==3){
						
					}
				}
				return true;
			}
		}


	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		addtype = 1;
		
	}

	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		addtype = 2;
		
	}
	
	
	private void getData(){
		
	}
	private void onLoad() {
		lv.stopRefresh();
		lv.stopLoadMore();
		if(addtype==1){
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd   hh:mm:ss");
			String date = sDateFormat.format(new java.util.Date());
			lv.setRefreshTime(date);
		}
	}

	
	
}
