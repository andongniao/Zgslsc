package com.example.educonsult.activitys;


import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.xunbo.store.MyApplication;
import com.xunbo.store.adapters.CatHomelvAdapter;
import com.xunbo.store.adapters.CatgvAdapter;
import com.xunbo.store.beans.ListFenleiBean;
import com.xunbo.store.beans.ListProductBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.tools.Util;

public class CatDetaileActivity extends BaseActivity implements OnClickListener{
	private ExpandableListView lv;
	private CatHomelvAdapter adapter;
	private Context context;
	private ListFenleiBean bean;
	private GridView gv;
	private CatgvAdapter gvAdapter;
	private ArrayList<ProductBean> list;
	private ListProductBean listbean;
	private Intent intent;
	private ThreadWithProgressDialog myPDT;
	private String id;
	private Cat cat;
	private long exitTime = 0;
	private LinearLayout ll_isnull;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		goneTopLeft();
		topRightTGone();
		setTitleTxt("分类详情");
		setContentXml(R.layout.cat_home);
		
		init();
	}

	private void init() {
		context = this;
		Util u = new Util(context);
		myPDT = new ThreadWithProgressDialog();
		cat = new Cat() {
			
			@Override
			public void sreach(String catid) {
				id = catid;
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
		};
		findViewById(R.id.cat_home_et_inpu).setOnClickListener(this);
		bean = (ListFenleiBean) u.readObject(MyApplication.mp.FenleiName);
		ll_isnull = (LinearLayout) findViewById(R.id.cat_home_ll_isnull);
		gv = (GridView) findViewById(R.id.cat_home_gv);
		lv = (ExpandableListView) findViewById(R.id.lv);
		/* 隐藏默认箭头显示 */  
		lv.setGroupIndicator(null);  
		/* 隐藏垂直滚动条 */  
		lv.setVerticalScrollBarEnabled(false);  
		adapter = new CatHomelvAdapter(context, bean,cat);
		lv.setAdapter(adapter);
		
		
	}
	
	
	// 任务
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
			if(listbean!=null){
				if("200".equals(listbean.getCode())){
					list = listbean.getList();
					if(list.size()==0){
						ll_isnull.setVisibility(View.VISIBLE);
						gv.setVisibility(View.GONE);
					}else{
						gv.setVisibility(View.VISIBLE);
						ll_isnull.setVisibility(View.GONE);
						if(gvAdapter!=null){
							gvAdapter.setData(list);
							gvAdapter.notifyDataSetChanged();
						}else{
							gvAdapter = new CatgvAdapter(context, list);
							gv.setAdapter(gvAdapter);
						}
					}
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



			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			PostHttp p = new PostHttp(context);
			listbean = p.Seanchcat(id);
			return true;
		}
	}
	
	public interface Cat{
		void sreach(String catid);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
			if((System.currentTimeMillis()-exitTime) > 2000){  
				Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
				exitTime = System.currentTimeMillis();   
			} else {
				finish();
				System.exit(0);
			}
			return true;   
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cat_home_et_inpu:
			intent = new Intent(context,SearchHomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			context.startActivity(intent);
			break;

		default:
			break;
		}
	}

}
