package com.xunbo.store.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
	private ListView lv;;
	private MyZjAdapter adapter;
	private ArrayList<String>list;
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
	private ImageView iv_top_t;
	private int type;
	private myzj myzj;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		topRightTGone();
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.del_icon_normal);
		iv_top_t.setVisibility(View.GONE);
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
		iv_top_t.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				productBeans = MyApplication.mp.deleteSee(list);
				type=0;
				iv_top_t.setVisibility(View.GONE);
				adapter.SetData(productBeans, type);
				adapter.notifyDataSetChanged();
			}
		});
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toproduct(productBeans.get(arg2));
			}
		});

		lv.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				if(type==0){
					type=1;
					adapter.SetData(productBeans, type);
				}
				return false;
			}
		});
		
	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		isread = false;
		isfinish=false;
		type = 0;
		list = new ArrayList<String>();
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
		lv = (ListView) findViewById(R.id.myzj_lv);
		
		
		myzj = new myzj() {
			
			@Override
			public void removelist(String itemid) {
				list.remove(itemid);
			}
			
			@Override
			public void addlist(String itemid) {
				list.add(itemid);
			}
		};
		
		if(productBeans==null||productBeans.size()==0){
			lv.setVisibility(View.GONE);
			
			
		}else{
			ll_not.setVisibility(View.GONE);
			adapter = new MyZjAdapter(context, productBeans,type,myzj);
			lv.setAdapter(adapter);
		}
		
		
		

	}

	public interface myzj{
		void addlist(String itemid);
		void removelist(String itemid);
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
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
			if(type==1){
				type=0;
				iv_top_t.setVisibility(View.GONE);
			}
			return true;   
		}
		return super.onKeyDown(keyCode, event);
	}
	
}
