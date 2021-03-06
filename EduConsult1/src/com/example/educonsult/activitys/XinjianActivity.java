package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.adapters.XinjianAdapter;
import com.xunbo.store.beans.ListXinjianBean;
import com.xunbo.store.beans.UserBean;
import com.xunbo.store.beans.XinJianBean;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class XinjianActivity extends BaseActivity{
	private ListView lv;
	private Context context;
	private ArrayList<Object> list;
	private XinjianAdapter adapter;
	private LinearLayout ll_isnull;
	private Intent intent;
	private UserBean bean;
	private ListXinjianBean xinjianbean;
	private ArrayList<XinJianBean> xinjianlist;
	private ThreadWithProgressDialog myPDT;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//topRightRVisible();
		topRightTGone();
		setTitleTxt(R.string.xinjian_title);
		setContentXml(R.layout.xinjian);
		init();
		String flag = getIntent().getStringExtra("flag");
		if(Util.IsNull(flag)){
			if("ghhome".equals(flag)){
				GqHomeActivity.isread = true;
			}else if("address".equals(flag)){
				AddressActivity.isread = true;
			}else if("service".equals(flag)){
				ServiceCenterActivity.isread = true;
			}
			else if("qianbao".equals(flag)){
				QianBaoActivity.isread = true;
			}
			else if("myinfo".equals(flag)){
				MyInfoActivity.isread = true;
			}
			else if("gqtwo".equals(flag)){
				GqTwoActivity.isread = true;
			}
			else if("store".equals(flag)){
				StoreActivity.isread = true;
			}
		}
	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		bean=MyApplication.mp.getUser();
		myPDT=new ThreadWithProgressDialog();
		lv = (ListView) findViewById(R.id.xinjian_lv);
		ll_isnull = (LinearLayout) findViewById(R.id.xinjian_ll_isnull);
		list = new ArrayList<Object>();
		list.add(1);
		list.add(2);
		lv.setEmptyView(ll_isnull);
		if(list!=null){
			if(list.size()>0){
				ll_isnull.setVisibility(View.GONE);
				lv.setVisibility(View.VISIBLE);
			}else{
				ll_isnull.setVisibility(View.VISIBLE);
				lv.setVisibility(View.GONE);
			}
		}
		xinjianlist=new ArrayList<XinJianBean>();
		
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		}

	}
	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
//			Toast.makeText(context, "cancle", 1000).show();
			finish();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(xinjianbean!=null){
				if("200".equals(xinjianbean.getCode())){
					//TODO	
					xinjianlist=xinjianbean.getList();
					adapter = new XinjianAdapter(context, xinjianlist);
					lv.setAdapter(adapter);
					lv.setOnItemClickListener(new OnItemClickListener() {
						
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
								long arg3) {
							// TODO Auto-generated method stub
							intent=new Intent(context,XinJianInfoActivity.class);
							intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.putExtra("xinjianid",xinjianlist.get(arg2).getItemid());
							startActivity(intent);
						}
					});
				}else if("300".equals(xinjianbean.getCode())){
					MyApplication.mp.setlogin(false);
					Util.ShowToast(context, R.string.login_out_time);
					intent = new Intent(context,LoginActivity.class);
					startActivity(intent);
					finish();
				}else{
					Util.ShowToast(context, xinjianbean.getMsg());
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
			xinjianbean=s.getXinjianlist(bean.getAuthstr());
			return true;
		}
	}


}
