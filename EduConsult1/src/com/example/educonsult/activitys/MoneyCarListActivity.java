package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.adapters.MoneyCarListAdapter;
import com.xunbo.store.beans.ListMoneytxBean;
import com.xunbo.store.beans.MoneyTxBean;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class MoneyCarListActivity extends BaseActivity{
	private ListView lv;
	private Context context;
	private MoneyCarListAdapter adapter;
	private ImageView iv_top_right;
	private Intent intent; 
	private ThreadWithProgressDialog myPDT;
	private ListMoneytxBean listMoneytxBean;
	private ArrayList<MoneyTxBean>list;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		topRightTGone();
		setTopLeftTv(R.string.moneycar_list_title);
		setContentXml(R.layout.moneycar_list);
		init();
		addlistener();
	}


	private void init() {
		TestinAgent.init(this);
		myPDT=new ThreadWithProgressDialog();
		context = this;
	
		lv = (ListView) findViewById(R.id.moneycar_list_list);
		
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
		
	}
	private void addlistener() {
	
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				adapter.SetData(arg2);
				adapter.notifyDataSetChanged();
			}
		});
	}
	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			finish();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			if(listMoneytxBean!=null){
				if("200".equals(listMoneytxBean.getCode())){
					list=listMoneytxBean.getList();
					if(list!=null&&list.size()!=0){
						adapter = new MoneyCarListAdapter(context, list, 0);
						lv.setAdapter(adapter);
					}   

				}else if("300".equals(listMoneytxBean.getCode())){
					MyApplication.mp.setlogin(false);
					Util.ShowToast(context, R.string.login_out_time);
					Intent i= new Intent(context,LoginActivity.class);
					startActivity(i);
					finish();
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			PostHttp p=new PostHttp(context);
			Send s=new Send(context);
			listMoneytxBean=s.getTxBankList(MyApplication.mp.getUser().getAuthstr());
			return true;
		}
	}
}
