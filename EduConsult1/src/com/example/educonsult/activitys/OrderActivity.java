package com.example.educonsult.activitys;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;
import com.xunbo.store.MyApplication;
import com.xunbo.store.adapters.OrderHomeAdapter;
import com.xunbo.store.adapters.TextItemListAdapter;
import com.xunbo.store.beans.AddressBean;
import com.xunbo.store.beans.ListAddressBean;
import com.xunbo.store.beans.ListOrderCommit;
import com.xunbo.store.beans.ListShopBean;
import com.xunbo.store.beans.PayBean;
import com.xunbo.store.beans.ShopBean;
import com.xunbo.store.beans.UserBean;
import com.xunbo.store.myviews.MyListview;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.UITools;
import com.xunbo.store.tools.Util;

@SuppressWarnings("unused")
public class OrderActivity extends BaseActivity implements OnClickListener{
	private ScrollView scrollView;
	private Context context;
	private LinearLayout ll_address,ll_add;
	private Button button;
	private MyListview lv;
	private TextView tv_shouhuoren,tv_shoujihao,tv_address,tv_zongjia,tv_ok;
	private ArrayList<Integer>list,listdizhi;
	private OrderHomeAdapter adapter;
	private Intent intent;
	private Handler mHandler = null;
	private String mMode = "01";
	private String Tn = "201504281334390000902";
	private static final String TN_URL_01 = "http://202.101.25.178:8080/sim/gettn";
	private PopupWindow popu;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ListView list_2,lv_l;
	private TextItemListAdapter adapter_r;
	private int mscreenwidth;
	private DisplayMetrics dm;
	private TextView tv_allmoney;
	private EditText et_pass;
	private Button b_no,b_yes;
	private ListShopBean shopbean,shoporder;
	private ThreadWithProgressDialog myPDT;
	private PayBean baseBean;
	private UserBean userBean;
	private ListOrderCommit listOrderCommit;
	private int inttype;
	private String strpass;
	private ArrayList<ShopBean> shopBeans;
	private OrderHomeAdapter orderHomeAdapter;
	private ListAddressBean listAddressBean;
	private ArrayList<AddressBean> addressBeans;
	private AddressBean addressBean;
	public static boolean isinit;
	private String money;
	private HashMap<Integer, ArrayList<String>> couponmap;
	@Override
	
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		setTitleTxt(R.string.order_title);
		setContentXml(R.layout.order_home_layout);
		init();
		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				String tn = "";
				if (msg.obj == null || ((String) msg.obj).length() == 0) {
					//			            AlertDialog.Builder builder = new AlertDialog.Builder(this);
					//			            builder.setTitle("错误提示");
					//			            builder.setMessage("");
					//			            builder.setNegativeButton("确定",
					//			                    new DialogInterface.OnClickListener() {
					//			                        @Override
					//			                        public void onClick(DialogInterface dialog, int which) {
					//			                            dialog.dismiss();
					//			                          }
					//			                      });
					//			            builder.create().show();
					Util.ShowToast(context, "网络连接失败,请重试!");
				} else {
					tn = (String) msg.obj;
					/************************************************* 
					 * 
					 *  步骤2：通过银联工具类启动支付插件 
					 *  
					 ************************************************/
					UPPayAssistEx.startPayByJAR(OrderActivity.this, PayActivity.class, null, null,
							tn, mMode);
				}
			}
		};
	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		intent=getIntent();
		Bundle b=intent.getBundleExtra("shopcartbundle");
		shopbean=(ListShopBean)b.getSerializable("shopcarbean");
		shoporder=(ListShopBean)b.getSerializable("shopcaroder");
		money = b.getString("money");

		//		intent.putExtra("shopcartbundle", b);
		//		shopbean=(ListShopBean)intent.getSerializableExtra("shopcarbean"); 
		//		shoporder=(ListShopBean)intent.getSerializableExtra("shopcaroder");
		shopBeans=shopbean.getList();
		myPDT =new  ThreadWithProgressDialog();
		userBean=MyApplication.mp.userbean;
		dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		listdizhi = new ArrayList<Integer>();
		scrollView = (ScrollView) findViewById(R.id.order_home_sc);
		scrollView.scrollTo(0, 10);
		ll_address = (LinearLayout) findViewById(R.id.order_home_ll_address);
		ll_address.setOnClickListener(this);
		ll_add=(LinearLayout)findViewById(R.id.order_home_ll_add_address);
		button=(Button)findViewById(R.id.order_home_ll_add);
		button.setOnClickListener(this);
		tv_shouhuoren = (TextView) findViewById(R.id.order_tv_shouhuoren);
		tv_shoujihao= (TextView) findViewById(R.id.order_tv_shoujihao);
		tv_address = (TextView) findViewById(R.id.order_tv_dizhi);
		tv_zongjia = (TextView) findViewById(R.id.order_tv_zongjia);
		tv_zongjia.setText(money);
		tv_ok = (TextView) findViewById(R.id.order_tv_ok);
		tv_ok.setOnClickListener(this);
		lv = (MyListview) findViewById(R.id.order_home_lv);
		lv.setFocusable(false);
		couponmap=new HashMap<Integer, ArrayList<String>>();
		adapter = new OrderHomeAdapter(context, shopBeans,couponmap);
		lv.setAdapter(adapter);
		inttype=0;
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_home_ll_add:
			intent = new Intent(context,AddressNewSaveActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("type", 2);
			startActivity(intent);
			
			break;
		case R.id.order_home_ll_address:
			//TODO 
			Intent id = new Intent(this,AddressActivity.class);
			id.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			Bundle b=new Bundle();
			b.putSerializable("orderbundle", listAddressBean);
			id.putExtra("upaddress", b);
			startActivity(id);
			break;
		case R.id.order_tv_ok:
			String s;
			boolean istrue=false;
			if(addressBean!=null){
				for(int i=0;i<couponmap.size();i++){
					for(int j=0;j<couponmap.get(i).size();j++){
						s=couponmap.get(i).get(j);
						for(int k=0;k<couponmap.size();k++){
							for(int m=0;m<couponmap.get(k).size();m++){
								if(s.equals(couponmap.get(k).get(m))){
									istrue=true;
									break;
								}
								
							}
						}
					}
				}
				if(!istrue){
					
					inttype=1;
					if(Util.detect(context)){
						myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
					}else{
						Util.ShowToast(context, R.string.net_is_eor);
					}
				}else{
					Util.ShowToast(context, "每张优惠券只能使用一次哟！");
				}
			}else{
				Util.ShowToast(context, "收货地址不能为空");
			}
			break;
		case R.id.money_password_no:

			popu.dismiss();
			ShopcartActivity.ischange=true;
			finish();
			break;
		case R.id.money_password_yes:
			inttype=2;
			strpass=et_pass.getText().toString();
			if(Util.IsNull(strpass)){
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				Util.ShowToast(context, R.string.money_password_nopass);
			}
			break;

		}

	}
	public class RefeshData implements ThreadWithProgressDialogTask {
		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			return false; 
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(inttype==1){
				if(listOrderCommit!=null){
					String code = listOrderCommit.getCode();
					String m = listOrderCommit.getMsg();
					if("200".equals(code)){
						ShopcartActivity.ischange=true;
						setpopuwindow();
						//finish();
					}else if("300".equals(code)){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent i= new Intent(context,LoginActivity.class);
						startActivity(i);
						finish();

					}else{
						if(Util.IsNull(m)){
							Util.ShowToast(context, m);
						}
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			if(inttype==2){

				if(baseBean!=null){
					if("200".equals(baseBean.getCode())){
						if("0".equals(baseBean.getType())){
							Util.ShowToast(context, "支付成功");
							finish();
						}else{
							Util.ShowToast(context, baseBean.getMsg());
							intent = new Intent(context,RechargeActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(intent);
						}
					}else if("300".equals(baseBean.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						intent = new Intent(context,LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}else{
						Util.ShowToast(context, baseBean.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			if(inttype==0){
				if(listAddressBean!=null){
					String code = listAddressBean.getCode();
					String m = listAddressBean.getMsg();
					if("200".equals(code)){
						addressBeans=listAddressBean.getList();
						if(addressBeans.size()==0){
							ll_add.setVisibility(View.VISIBLE);
							ll_address.setVisibility(View.GONE);
						}else{

							for(int i=0;i<addressBeans.size();i++){
								if("1".equals(addressBeans.get(i).getIsdefault())){
									addressBean=addressBeans.get(i);
								}
							}
							if(addressBean!=null){
								tv_shouhuoren.setText(addressBean.getTruename());
								tv_shoujihao.setText(addressBean.getMobile());
								tv_address.setText(addressBean.getAddress());
							}

						}
					}else if("300".equals(code)){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent i= new Intent(context,LoginActivity.class);
						startActivity(i);
						finish();
					}else{
						if(Util.IsNull(m)){
							Util.ShowToast(context, m);
						}
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}

			return true;
		}



		@Override
		public boolean TaskMain() {
			PostHttp p=new PostHttp(context);
			// ListOrderCommit CommitOrder
			if(inttype==1){

				listOrderCommit=p.CommitOrder(shopbean, userBean.getAuthstr());
			}else if(inttype==2){
				baseBean=p.PayOrder(listOrderCommit,userBean.getAuthstr() , strpass);
			}else if(inttype==0){
				Send s=new Send(context);
				listAddressBean=s.getAddressList(userBean.getAuthstr());
			}

			return true;
		}
	}
	@SuppressLint("InflateParams") private void setpopuwindow(){
		inflater=LayoutInflater.from(context);
		v_fenlei = inflater.inflate(R.layout.money_password, null);
		mscreenwidth=dm.widthPixels;
		popu = new PopupWindow(v_fenlei, mscreenwidth-UITools.dip2px(context, 30), LayoutParams.WRAP_CONTENT);
		popu.setFocusable(true);
		//popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setBackgroundDrawable(getResources().getDrawable(R.drawable.regist_et_bg));
		popu.setOutsideTouchable(true);
		popu.showAtLocation(v_fenlei, Gravity.CENTER, 0, 0);
		tv_allmoney=(TextView)v_fenlei.findViewById(R.id.money_password_money);
		et_pass=(EditText)v_fenlei.findViewById(R.id.money_password_edpassword);
		b_no=(Button)v_fenlei.findViewById(R.id.money_password_no);
		b_yes=(Button)v_fenlei.findViewById(R.id.money_password_yes);
		tv_allmoney.setText(money);
		b_no.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ShopcartActivity.ischange =true;
				finish();
			}
		});
		b_yes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				strpass = et_pass.getText().toString();
				inttype=2;
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
		});


	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (data == null) {
			return;
		}

		String msg = "";
		int statu=-1;
		String str = data.getExtras().getString("pay_result");
		if (str.equalsIgnoreCase("success")) {
			msg = "支付成功！";
			statu = 1;
		} else if (str.equalsIgnoreCase("fail")) {
			msg = "支付失败！";
			statu = 2;
		} else if (str.equalsIgnoreCase("cancel")) {
			msg = "用户取消了支付";
			statu = 3;
		}

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("支付结果通知");
		builder.setMessage(msg);
		builder.setInverseBackgroundForced(true);
		//builder.setCustomTitle();
		builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
		if(statu==1){
			finish();
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		if(isinit){
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			isinit = false;
		}
	}


}
