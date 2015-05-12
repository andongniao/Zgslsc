package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.ExampleActivity;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.activitys.MyCenterTuijianActivity.RefeshData;
import com.example.educonsult.adapters.GqAdapter;
import com.example.educonsult.adapters.HomeSlidAdapter;
import com.example.educonsult.adapters.KnowFenleiAdapter;
import com.example.educonsult.adapters.MyZjAdapter;
import com.example.educonsult.beans.ProductBean;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.Util;

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
	private Util u;

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
		context = this;
		isread = false;
		/*myPDT=new ThreadWithProgressDialog();
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//¿ÉÈ¡Ïû
		}*/
		u=new Util(context);
		
		productBeans=(ArrayList<ProductBean>)u.readObject(MyApplication.Seejilu);
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

	}


	@Override
	public void onClick(View v) {
		
	}
	
	private void Toproduct(ProductBean bean){
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("productdetaile", bean);
		startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(isread){
			Util.SetRedGone(context, rl_l);
			isread = false;
		}
	}
	
	
}
