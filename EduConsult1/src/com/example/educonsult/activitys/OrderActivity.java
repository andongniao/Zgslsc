package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
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
import android.widget.Toast;

import com.example.educonsult.R;
import com.example.educonsult.adapters.OrderHomeAdapter;
import com.example.educonsult.adapters.TextItemListAdapter;
import com.example.educonsult.myviews.MyListview;
import com.example.educonsult.tools.UITools;
import com.example.educonsult.tools.Util;
import com.unionpay.UPPayAssistEx;
import com.unionpay.uppay.PayActivity;

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
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		setTopLeftTv(R.string.order_title);
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
					//			                        }
					//			                    });
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
		context = this;
		list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
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
		tv_ok = (TextView) findViewById(R.id.order_tv_ok);
		tv_ok.setOnClickListener(this);
		lv = (MyListview) findViewById(R.id.order_home_lv);
		lv.setFocusable(false);
		adapter = new OrderHomeAdapter(context, list);
		lv.setAdapter(adapter);
		if(listdizhi.size()==0){
			ll_add.setVisibility(View.VISIBLE);
			ll_address.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.order_home_ll_add:
			intent = new Intent(context,AddressGLActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("addressnum", "1");
			startActivity(intent);
			break;
		case R.id.order_home_ll_address:
			//TODO 
			Intent id = new Intent(this,UpAddressActivity.class);
			id.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(id);
			break;
		case R.id.order_tv_ok:
			//			ExampleActivity.setCurrentTab(3);
//			new Thread(){ 
//				@Override
//				public void run() {
//					String tn = null;
//					InputStream is;
//					try {
//
//						String url = TN_URL_01;
//
//						URL myURL = new URL(url);
//						URLConnection ucon = myURL.openConnection();
//						ucon.setConnectTimeout(120000);
//						is = ucon.getInputStream();
//						int i = -1;
//						ByteArrayOutputStream baos = new ByteArrayOutputStream();
//						while ((i = is.read()) != -1) {
//							baos.write(i);
//						}
//
//						tn = baos.toString();
//						is.close();
//						baos.close();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//
//					Message msg = mHandler.obtainMessage();
//					msg.obj = tn;
//					mHandler.sendMessage(msg);
//				}}.start();  
			/************************************************* 
			 * 
			 *  步骤2：通过银联工具类启动支付插件 
			 *  
			 ************************************************/
			/*UPPayAssistEx.startPayByJAR(OrderActivity.this, PayActivity.class, null, null,
					Tn, mMode);*/
			if(listdizhi.size()==0){
				Util.ShowToast(context, R.string.money_password_noadress);
			}else{
				
				setpopuwindow();
			}
				break;
		case R.id.money_password_no:
			popu.dismiss();
			break;
		case R.id.money_password_yes:
			String strpass=et_pass.getText().toString();
			if(Util.IsNull(strpass)){
				
			}else{
				Util.ShowToast(context, R.string.money_password_nopass);
			}
			break;

		}

	}
	private void setpopuwindow(){
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
		b_no.setOnClickListener(this);
		b_yes=(Button)v_fenlei.findViewById(R.id.money_password_yes);
		b_yes.setOnClickListener(this);
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/************************************************* 
		 * 
		 *  步骤3：处理银联手机支付控件返回的支付结果 
		 *  
		 ************************************************/
		if (data == null) {
			return;
		}

		String msg = "";
		/*
		 * 支付控件返回字符串:success、fail、cancel
		 *      分别代表支付成功，支付失败，支付取消
		 */
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


}
