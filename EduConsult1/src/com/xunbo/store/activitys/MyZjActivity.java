package com.xunbo.store.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.adapters.MyZjAdapter;
import com.xunbo.store.beans.ListProductBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.tools.Util;

public class MyZjActivity extends BaseActivity implements OnClickListener{
	
	private Context context;
	private GridView gridView;
	private MyZjAdapter adapter;
	private ArrayList<Integer>list;
	private Intent intent;
	private RelativeLayout rl_l,rl_r;
	public static boolean isread;
	public View ll_gqtwo_popu;
	private LinearLayout ll_not;
	private ThreadWithProgressDialog myPDT;
	private ArrayList<ProductBean> productBeans;
	private ListProductBean listProductBean;
	private Util u;
	public static boolean isfinish;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		topRightTGone();
		
		setTopLeftTv(R.string.myzj_title);
		setContentXml(R.layout.myzj);
		init();
		addlistener();
		//		/**********set***********/
//		UserBean b = new UserBean();
//		b.setName("121");
//		String l = b.toString();
//		MyApplication.bean = b;
//		/**********get***********/
//		UserBean a = MyApplication.bean;
//		String s = a.getName();





	}

	private void addlistener() {
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toproduct(productBeans.get(arg2));
			}
		});

	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		isread = false;
		isfinish=false;
		myPDT=new ThreadWithProgressDialog();
		
		u=new Util(context);
		listProductBean=(ListProductBean)u.readObject(MyApplication.Seejilu);
		if(listProductBean==null){
			listProductBean=new ListProductBean();
			productBeans=new ArrayList<ProductBean>();
		}else{
			
			productBeans=listProductBean.getList();
		}
		ll_not=(LinearLayout)findViewById(R.id.myzj_ll_isnull);
		gridView = (GridView) findViewById(R.id.myzj_gv);
		if(productBeans==null||productBeans.size()==0){
			gridView.setVisibility(View.GONE);
			
			
		}else{
			ll_not.setVisibility(View.GONE);
			adapter = new MyZjAdapter(context, productBeans);
			gridView.setAdapter(adapter);
		}
		
		
		
		list = new ArrayList<Integer>();
		for(int i=0;i<10;i++){
			list.add(i);
		}
		//gridView.setFocusable(false);
		Util.SetRedNum(context, rl_l, 0);
		/*if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//��ȡ��
		}*/

	}


	@Override
	public void onClick(View v) {
		
	}
	
	private void Toproduct(ProductBean bean){
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Bundle b=new Bundle();
		b.putSerializable("product", bean);
		intent.putExtra("productbundle", b);
		startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(isread){
			Util.SetRedGone(context, rl_l);
			isread = false;
		}
		if(isfinish){
			isfinish=false;
			finish();
		}
	}
	
	
}