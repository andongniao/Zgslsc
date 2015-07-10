package com.xunbo.store.activitys;


import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.GridView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.adapters.AddressHomeAdapter;
import com.xunbo.store.adapters.CatHomelvAdapter;
import com.xunbo.store.adapters.CatgvAdapter;
import com.xunbo.store.beans.ListFenleiBean;
import com.xunbo.store.beans.ListProductBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class CatDetaileActivity extends BaseActivity {
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
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		goneTopLeft();
		topRightTGone();
		setTitleTxt("分类详情");
		setContentXml(R.layout.cat_home);
		
		init();
//		if(Util.detect(context)){
//			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
//		}else{
//			Util.ShowToast(context, R.string.net_is_eor);
//		}
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
		
		bean = (ListFenleiBean) u.readObject(MyApplication.mp.FenleiName);
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
					if(gvAdapter!=null){
						gvAdapter.setData(list);
						gvAdapter.notifyDataSetChanged();
					}else{
						gvAdapter = new CatgvAdapter(context, list);
						gv.setAdapter(gvAdapter);
					}
					if(list.size()==0){
						Util.ShowToast(context, "nope");
					}
//					if(list.size()>0){
//						btn_add.setVisibility(View.VISIBLE);
//						lv.setVisibility(View.VISIBLE);
//						ll_show.setVisibility(View.GONE);
//					}else{
//						btn_add.setVisibility(View.GONE);
//						lv.setVisibility(View.GONE);
//						ll_show.setVisibility(View.VISIBLE);
//					}
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

}
