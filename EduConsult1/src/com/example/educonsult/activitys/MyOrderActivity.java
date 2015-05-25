package com.example.educonsult.activitys;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.ExampleActivity;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.adapters.MyOrderHomeAdapter;
import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.ListOrderBean;
import com.example.educonsult.beans.OrderBean;
import com.example.educonsult.beans.PayBean;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.myviews.xlistview.XListView;
import com.example.educonsult.myviews.xlistview.XListView.IXListViewListener;
import com.example.educonsult.net.PostHttp;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.Util;

@SuppressWarnings("unused")
@SuppressLint("InflateParams") public class MyOrderActivity extends BaseActivity implements OnClickListener,IXListViewListener{
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
	private int addtype,pag,index,scrolledX,scrolledY;
	private String tag;
	private UserBean userbean;
	private ListOrderBean listbean;
	private boolean init;
	private OrderBean ob;
	private int add;//1关闭订单 2买家支付 3确认收货（确认支付）4确认发货5申请退款6评价订单
	private PopupWindow popwindow;
	private BaseBean baseBean;
	private PayBean paybean;
	private String initdataing,password;
	private View v_pop;
	private int ttp,ppage,step;
	private Handler handler;
	public static boolean isinit;
	private EditText et_pass;

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
		//		myPDT.Run(context, new RefeshData(init,type,pag,tag),R.string.loding);//可取消
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

	@SuppressWarnings("deprecation")
	private void init() {
		context = this;
		init = true;
		userbean = MyApplication.mp.getUser();
		pag = 1;
		step = 0;
		ppage = 1;
		tag = userbean.getAuthstr();
		initdataing = "更新数据中...";
		myPDT = new ThreadWithProgressDialog();
		String  msg = getResources().getString(R.string.loding);
		myorder =new Myorder() {
			@Override
			public void Order_Repay(OrderBean orderBean) {
				ob = orderBean;
				init = false;
				add = 3;
				ob = orderBean;
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(init,pag,tag),R.string.loding);//可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
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
				//				myPDT.Run(context, new RefeshData(init,type,pag,tag),R.string.loding);//可取消
				intent = new Intent(context,ApplyerFundActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("itemid", orderBean.getItemid());
				intent.putExtra("statusid", orderBean.getItemid());
				startActivity(intent);
			}

			@Override
			public void Order_Pay(OrderBean orderBean) {
				ob = orderBean;
				TextView money = (TextView) v_pop.findViewById(R.id.money_password_money);
				et_pass = (EditText) v_pop.findViewById(R.id.money_password_edpassword);
				double mo = Double.parseDouble(ob.getPrice())*Integer.parseInt(ob.getNumber());
				money.setText(""+mo);
				popwindow.showAtLocation(getWindow().getDecorView(), Gravity.CENTER,0, 0);
				Button btn_pay = (Button) v_pop.findViewById(R.id.money_password_yes);
				Button btn_cancle = (Button) v_pop.findViewById(R.id.money_password_no);	
				btn_cancle.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						popwindow.dismiss();
						et_pass.setText("");
					}
				});
				btn_pay.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						password = et_pass.getText().toString();
						init = false;
						add = 2;
						popwindow.dismiss();
						et_pass.setText("");
						if(Util.detect(context)){

							myPDT.Run(context, new RefeshData(init,pag,tag),R.string.loding);//可取消
						}
						else{
							Util.ShowToast(context, R.string.net_is_eor);
						}					}
				});		

			}

			@Override
			public void Order_Fahuo(OrderBean orderBean) {
				ob = orderBean;
				init = false;
				add = 4;
				intent = new Intent(context,ConfirmTheDeliveryActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("itemid", orderBean.getItemid());
				intent.putExtra("statusid", orderBean.getStatusid());
				startActivity(intent);
				//				myPDT.Run(context, new RefeshData(init,type,pag,tag),R.string.loding);//可取消
			}

			@Override
			public void Order_Canale(OrderBean orderBean) {
				ob = orderBean;
				init = false;
				add= 1;
				if(Util.detect(context)){
					
				myPDT.Run(context, new RefeshData(init,pag,tag),R.string.loding);//可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
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
					intent.putExtra("coupon", orderBean.getCoupons());
					startActivity(intent);
				}else{
					intent = new Intent(context,MyOrderInfoActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("itemid", orderBean.getItemid());
					intent.putExtra("statusid", orderBean.getStatusid());
					intent.putExtra("coupon", orderBean.getCoupons());
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
		lv.setPullRefreshEnable(true);
		lv.setPullLoadEnable(true);
		lv.setXListViewListener(this);
		lv.setEmptyView(ll_isnull);
		ll_all = (LinearLayout) findViewById(R.id.myorder_home_ll_all);
		ll_all.setOnClickListener(this);
		ll_pay = (LinearLayout) findViewById(R.id.myorder_home_ll_pay);
		ll_pay.setOnClickListener(this);
		ll_send = (LinearLayout) findViewById(R.id.myorder_home_ll_send);
		ll_send.setOnClickListener(this);
		ll_send.setVisibility(View.GONE);
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
		tv_show_comment = (TextView) findViewById(R.id.myorder_home_tv_comment_show);
		view_list.add(tv_show_all);
		view_list.add(tv_show_pay);

		tv_show_send = (TextView) findViewById(R.id.myorder_home_tv_send_show);
//		view_list.add(tv_show_send);
		tv_show_shouhuo = (TextView) findViewById(R.id.myorder_home_tv_shouhuo_show);
		view_list.add(tv_show_shouhuo);

//		if(userbean.getType()==0){
//			ll_send.setVisibility(View.VISIBLE);
//			ll_shouhuo.setVisibility(View.GONE);
//		}else{
//			ll_shouhuo.setVisibility(View.VISIBLE);
//			ll_send.setVisibility(View.GONE);
//		}

		view_list.add(tv_show_comment);
		v_pop = LayoutInflater.from(context).inflate(R.layout.money_password, null);
		popwindow = new PopupWindow(v_pop, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		popwindow.setOutsideTouchable(true);
		popwindow.setFocusable(true);
		popwindow.setBackgroundDrawable(new BitmapDrawable());
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
		//		lv.setOnScrollListener(new OnScrollListener() {   
		//			  
		//		    /**  
		//		     * 滚动状态改变时调用  
		//		     */  
		//		    @Override  
		//		    public void onScrollStateChanged(AbsListView view, int scrollState) {   
		//		        // 不滚动时保存当前滚动到的位置   
		//		        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE) {  
		//		            if (currentMenuInfo != null) {   
		//		                scrolledX = view.getScrollX();   
		//		                scrolledY = view.getScrollY();   
		//		            }   
		//		        }   
		//		    }   
		//		  
		//		    /**  
		//		     * 滚动时调用  
		//		     */  
		//		    @Override  
		//		    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {   
		//		    }   
		//		});  
		//		Util.SetRedNum(context, rl_r, 1);
		handler = new Handler(){
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what==1){
					if(ppage==1){
						list = (ArrayList<OrderBean>) msg.obj;
					}else{
						ArrayList<OrderBean> ll = (ArrayList<OrderBean>) msg.obj;
						list.addAll(ll);
						if(ll.size()==0){
							Util.ShowToast(context, R.string.page_is_final);
							ppage-=1;
						}
					}
					if(adapter!=null){
						adapter.SetData(list);
						adapter.notifyDataSetChanged();
					}else{
						adapter = new MyOrderHomeAdapter(context, list, myorder);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myorder_home_ll_all:
			step = 0;
			change(0);
			break;
		case R.id.myorder_home_ll_pay:
			step = 1;
			change(1);
			break;
		case R.id.myorder_home_ll_send:
//			change(2);
			break;
		case R.id.myorder_home_ll_shouhuo:
			step = 3;
			change(2);
			break;
		case R.id.myorder_home_ll_comment:
			step = 4;
			change(3);
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
				if(i==0 ||i==1){
					step = i;
				}else if(i==2 ||i==3){
					step=i+1;
				}
				ttp = i;
				init = true;
				if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(init,pag,tag),R.string.loding);//可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
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
//		private int step;
		private int page;
		private String authstr;
		private boolean init;

		public RefeshData(boolean init,int page,String authstr) {
			this.init = init;
//			this.step = step;
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
			if(init){
				if(page==1){
					if(listbean!=null){
						if("200".equals(listbean.getCode())){
							list = listbean.getList_order();
							adapter = new MyOrderHomeAdapter(context, list,myorder);
							lv.setAdapter(adapter);
						}else if("300".equals(listbean.getCode())){
							MyApplication.mp.setlogin(false);
							Util.ShowToast(context, R.string.login_out_time);
							intent = new Intent(context,LoginActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
							finish(); 
						}else{
							Util.ShowToast(context, listbean.getMsg());
						}
					}else{
						Util.ShowToast(context, R.string.net_is_eor);
					}
				}else{
					adapter = new MyOrderHomeAdapter(context, list,myorder);
					lv.setAdapter(adapter);	
				}
			}else{
				if(paybean!=null){
					if("200".equals(paybean.getCode())){

						if(add==1){
							Util.ShowToast(context, "关闭成功");
						}else if(add==2){
							if("0".equals(paybean.getType())){
							Util.ShowToast(context, "支付成功");
							}else{
								Util.ShowToast(context, paybean.getMsg());
//								intent = new Intent(context,RechargeActivity.class);
//								intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//								startActivity(intent);
							}
						}else if(add==3){
							Util.ShowToast(context, "确认成功");	
						}
						init = true;
						if(Util.detect(context)){
						myPDT.Run(context, new RefeshData(init,pag,tag),initdataing,false);//可取消
						}else{
							Util.ShowToast(context, R.string.net_is_eor);
						}
					}else if("300".equals(baseBean.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						intent = new Intent(context,LoginActivity.class);
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
			if(init){
				if(page==1){
					listbean = s.getOrderList(step, page, authstr);
				}else{
					listbean = s.getOrderList(step, 1, authstr);
					list = listbean.getList_order();
					for(int i=1;i<page+1;i++){
						listbean = s.getOrderList(step, page, authstr);
						ArrayList<OrderBean> l = listbean.getList_order();
						list.addAll(l);
					}
				}
			}else{
				//1关闭订单 2买家支付 3确认收货（确认支付）4确认发货5申请退款6评价订单
				if(add==1){
					baseBean = p.Order_close(ob.getItemid(), authstr);
				}else if(add==2){
					paybean = p.PayOrder(ob.getItemid(), ob.getCoupons(), authstr, password);
				}else if(add==3){
					baseBean = p.Order_confirmpay(ob.getItemid(), authstr);
				}
			}
			return true;
		}
	}


	@Override
	public void onRefresh() {
		ppage=1;
		addtype = 1;
		getData();
	}

	@Override
	public void onLoadMore() {
		ppage+=1;
		addtype = 2;
		getData();
	}


	private void getData(){
		new Thread(){public void run() {
			Send s = new Send(context);
			listbean = s.getOrderList(ttp, ppage, userbean.getAuthstr());
			Message msg = handler.obtainMessage();
			if(listbean!=null){
				if("200".equals(listbean.getCode())){
					ArrayList<OrderBean>lt = listbean.getList_order();
					msg.what=1;
					msg.obj = lt;
				}else{
					msg.what=2;
					msg.obj = listbean.getMsg();
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
		if(addtype==1){
			SimpleDateFormat sDateFormat = new SimpleDateFormat(
					"yyyy-MM-dd   hh:mm:ss");
			String date = sDateFormat.format(new java.util.Date());
			lv.setRefreshTime(date);
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		if(isinit){
			init = true;
			if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(init,pag,tag),initdataing,false);//可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			isinit = false;
		}
	}


}
