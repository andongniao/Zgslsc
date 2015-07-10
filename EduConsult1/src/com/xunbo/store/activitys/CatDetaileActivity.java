package com.xunbo.store.activitys;


import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.GridView;

import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.adapters.CatHomelvAdapter;
import com.xunbo.store.adapters.CatgvAdapter;
import com.xunbo.store.adapters.ExpandInfoAdapter;
import com.xunbo.store.adapters.StoreShopAdapter;
import com.xunbo.store.beans.ListFenleiBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.tools.Util;

public class CatDetaileActivity extends Activity {
	private ExpandableListView lv;
	private CatHomelvAdapter adapter;
	private Context context;
	private ListFenleiBean bean;
	private GridView gv;
	private CatgvAdapter gvAdapter;
	private ArrayList<ProductBean> list;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		setContentXml(R.layout.cat_home);
		setContentView(R.layout.cat_home);
		
		init();
	}

	private void init() {
		context = this;
		Util u = new Util(context);
		bean = (ListFenleiBean) u.readObject(MyApplication.mp.FenleiName);
		gv = (GridView) findViewById(R.id.cat_home_gv);
		lv = (ExpandableListView) findViewById(R.id.lv);
		/* 隐藏默认箭头显示 */  
		lv.setGroupIndicator(null);  
		/* 隐藏垂直滚动条 */  
		lv.setVerticalScrollBarEnabled(false);  
//		RelativeLayout rl = (RelativeLayout) findViewById(R.id.Base_Layout);
		adapter = new CatHomelvAdapter(context, bean);
		lv.setAdapter(adapter);
		list = new ArrayList<ProductBean>();
		for(int i=0;i<10;i++){
			ProductBean b = new ProductBean();
			b.setTitle("15612");
			b.setPrice("$$$");
			list.add(b);
		}
		gvAdapter = new CatgvAdapter(context, list);
		gv.setAdapter(gvAdapter);
		
		
		
	}

}
