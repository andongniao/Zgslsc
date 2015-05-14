package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.ExampleActivity;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.adapters.ApplyOrderHomeAdapter;
import com.example.educonsult.beans.ListOrderBean;
import com.example.educonsult.beans.OrderBean;
import com.example.educonsult.net.PostHttp;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.Util;

public class ApplyOrderActivity extends BaseActivity{
	private Context context;
	private ListView lv;
	private ApplyOrderHomeAdapter adapter;
	private Intent intent;
	private ThreadWithProgressDialog myPDT;
	private String itemid,authstr;
	private ListOrderBean lb;
	private MyApply apply;
	private LinearLayout ll_isnull;
	private ArrayList<OrderBean> list;
	private TextView see;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTopLeftTv(R.string.applyinfo_title);
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
		context = this;
		itemid = getIntent().getStringExtra("itemid");
		authstr = MyApplication.mp.getUser().getAuthstr();
		myPDT = new ThreadWithProgressDialog();
		String  msg = getResources().getString(R.string.loding);
		lv = (ListView) findViewById(R.id.apply_home_lv);
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
	}

	// 任务
	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			//			Toast.makeText(context, "cancle", 1000).show();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(lb!=null){
				if("200".equals(lb.getCode())){
					list = lb.getList_order();
					if(adapter!=null){
						adapter.SetData(list);
						adapter.notifyDataSetChanged();
					}else{
						adapter = new ApplyOrderHomeAdapter(context, list, apply);
						lv.setAdapter(adapter);
					}
				}else if("300".equals(lb.getCode())){
					MyApplication.mp.setlogin(false);
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
			// 访问
			PostHttp p = new PostHttp(context);
			Send s = new Send(context);
			lb = s.getOrderRefundList(1, authstr);
			return true;
		}
	}

	public interface MyApply{
		void detaile(String itemid);
	}


}
