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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.umeng.analytics.MobclickAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.adapters.AddressHomeAdapter;
import com.xunbo.store.beans.AddressBean;
import com.xunbo.store.beans.ListAddressBean;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class AddressActivity extends BaseActivity implements OnClickListener{
	private ListView lv;
	private LinearLayout ll_show;
	private Button btn_add;
	private ImageButton ibtn_add;
	private AddressHomeAdapter adapter;
	private Context context;
	private Intent intent;
	private RelativeLayout rl_l;
	public static boolean isread,isinit;
	private ThreadWithProgressDialog myPDT;
	private ListAddressBean lisetbean;
	private ArrayList<AddressBean>list;
	private boolean iscancle;
	public static final int REQUSET = 100;  
	public static AddressBean bean;  
	private int index,statu;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		setTitleTxt(R.string.address_title);
		setContentXml(R.layout.address_home);
		init();
		addlistener();
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//��ȡ��
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}
	private void addlistener() {
		/*rl_l.setOnClickListener(new OnClickListener() {

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
		});*/
	}
	private void init() {
		TestinAgent.init(this);
		context = this;
		index = -1;
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
				index = arg2;
				intent = new Intent(context,AddressGLActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("address", list.get(arg2));
				intent.putExtra("addressnum", "0");
				startActivityForResult(intent, REQUSET);
			}
		});
		//		Util.SetRedNum(context, rl_l, 1);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.address_home_btn_add_address:
			if(!iscancle){
				intent = new Intent(context,AddressNewSaveActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("type", 2);
				intent.putExtra("from", 1);
				startActivityForResult(intent, REQUSET);
			}
			break;
		case R.id.address_home_ibtn_add_address:
			intent = new Intent(context,AddressNewSaveActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("type", 2);
			intent.putExtra("from", 1);
			startActivityForResult(intent, REQUSET);
			break;

		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("AddressActivity"); 
		MobclickAgent.onResume(context);
		if(isread){
			Util.SetRedGone(context, rl_l);
			isread = false;
		}else if(isinit){
//			if(statu==4){
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(),R.string.loding);//��ȡ��
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
//			}else {
//				if(bean!=null){
//					list.remove(index);
//					list.add(index, bean);
//					bean=null;
//					adapter.SetData(list);
//					adapter.notifyDataSetChanged();
//				}
//				list.remove(index);
//				list.add(index, bean);
//			}else {
//			}
			isinit = false;
		}
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPageEnd("AddressActivity"); 
		MobclickAgent.onPause(context);
	}

	// ����
	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//����ȡ��
			iscancle = true;
			//				Toast.makeText(context, "cancle", 1000).show();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//������ɺ�
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
			// ����
			Send s = new Send(context);
			lisetbean = s.getAddressList(MyApplication.mp.getUser().getAuthstr());
			return true;
		}
	}


	@Override
	protected void onDestroy() {
		OrderActivity.isinit = true;
		if(myPDT!=null){
			if(myPDT.getCustomDialog()!=null && myPDT.getCustomDialog().isShowing()){
				myPDT.getCustomDialog().dismiss();
			}
			myPDT=null;
		}
		super.onDestroy();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {  
			AddressBean bean = (AddressBean) data.getExtras().get("ok"); 
			//			if(index==1){
			statu = (Integer) data.getExtras().get("statu");
			if(statu==1){
				for(int i=0;i<list.size();i++){
					if(index==i){
						list.get(i).setIsdefault("1");
					}else{
						list.get(i).setIsdefault("0");
					}
				}
			}else if(statu==0){
//				list.add(bean);
			}else if(statu==2){
				list.remove(index);
			}else if(statu==3){//�޸�
				list.remove(index);
				list.add(index, bean);
			}
			adapter.SetData(list);
			adapter.notifyDataSetChanged();
			//			}
		}
	}

}
