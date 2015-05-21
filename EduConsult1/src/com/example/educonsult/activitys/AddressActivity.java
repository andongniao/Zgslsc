package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.ExampleActivity;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.adapters.AddressHomeAdapter;
import com.example.educonsult.beans.AddressBean;
import com.example.educonsult.beans.ListAddressBean;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.Util;

public class AddressActivity extends BaseActivity implements OnClickListener{
	private ListView lv;
	private LinearLayout ll_show;
	private Button btn_add;
	private ImageButton ibtn_add;
	private AddressHomeAdapter adapter;
	private Context context;
	private Intent intent;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	public static boolean isread,isinit;
	private ThreadWithProgressDialog myPDT;
	private ListAddressBean lisetbean;
	private ArrayList<AddressBean>list;
	private boolean iscancle;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		//		topRightLVisible(); 
		topRightRVisible();
		rl_l = (RelativeLayout) getTopLightRl();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_l = (ImageView) getTopLightView();
		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		setTopLeftTv(R.string.address_title);
		setContentXml(R.layout.address_home);
		init();
		addlistener();
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}
	private void addlistener() {
		rl_l.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(context,XinjianActivity.class);
				intent.putExtra("flag", "address");
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
		myPDT = new ThreadWithProgressDialog();
		lv = (ListView) findViewById(R.id.address_home_lv);
		lv.setEmptyView(ll_show);
		ll_show = (LinearLayout) findViewById(R.id.address_home_ll_show);
		ll_show.setVisibility(View.GONE);
		btn_add = (Button) findViewById(R.id.address_home_btn_add_address);
		btn_add.setOnClickListener(this);
		btn_add.setVisibility(View.GONE);
		ibtn_add = (ImageButton) findViewById(R.id.address_home_ibtn_add_address);
		ibtn_add.setOnClickListener(this);
		if(list!=null){
			if(list.size()>0){
				btn_add.setVisibility(View.VISIBLE);
				lv.setVisibility(View.VISIBLE);
				ll_show.setVisibility(View.GONE);
				btn_add.setVisibility(View.VISIBLE);
			}else{
				btn_add.setVisibility(View.GONE);
				lv.setVisibility(View.GONE);
				btn_add.setVisibility(View.GONE);
				ll_show.setVisibility(View.VISIBLE);
			}
		}
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				intent = new Intent(context,AddressGLActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("address", list.get(arg2));
				intent.putExtra("addressnum", "0");
				startActivity(intent);
			}
		});
		//		Util.SetRedNum(context, rl_l, 1);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.address_home_btn_add_address:
			if(!iscancle){
				intent = new Intent(context,AddressGLActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("addressnum", "1");
				startActivity(intent);
			}
			break;
		case R.id.address_home_ibtn_add_address:
			intent = new Intent(context,AddressGLActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("addressnum", "1");
			intent.putExtra("isture", true);
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
		}else if(isinit){
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			isinit = false;
		}
	}

	// 任务
	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			iscancle = true;
			//				Toast.makeText(context, "cancle", 1000).show();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(lisetbean!=null){
				if("200".equals(lisetbean.getCode())){
					list = lisetbean.getList();
					if(adapter!=null){
						adapter.SetData(list);
						adapter.notifyDataSetChanged();
					}else{
						adapter = new AddressHomeAdapter(context, list);
						lv.setAdapter(adapter);
					}
					if(list.size()>0){
						btn_add.setVisibility(View.VISIBLE);
						lv.setVisibility(View.VISIBLE);
						ll_show.setVisibility(View.GONE);
					}else{
						btn_add.setVisibility(View.GONE);
						lv.setVisibility(View.GONE);
						ll_show.setVisibility(View.VISIBLE);
					}
				}else if("300".equals(lisetbean.getCode())){
					MyApplication.mp.setlogin(false);
					Util.ShowToast(context, R.string.login_out_time);
					intent = new Intent(context,LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish(); 
				}else{
					Util.ShowToast(context, lisetbean.getMsg());
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
			lisetbean = s.getAddressList(MyApplication.mp.getUser().getAuthstr());
			return true;
		}
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
		OrderActivity.isinit = true;
	}

}
