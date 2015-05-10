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

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.adapters.GqAdapter;
import com.example.educonsult.adapters.HomeSlidAdapter;
import com.example.educonsult.adapters.KnowFenleiAdapter;
import com.example.educonsult.adapters.MyZjAdapter;
import com.example.educonsult.beans.UserBean;
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
				Toproduct();
			}
		});

	}

	private void init() {
		context = this;
		isread = false;
		

		gridView = (GridView) findViewById(R.id.myzj_gv);
		list = new ArrayList<Integer>();
		for(int i=0;i<10;i++){
			list.add(i);
		}
		adapter = new MyZjAdapter(context, list);
		gridView.setAdapter(adapter);
		//gridView.setFocusable(false);
		Util.SetRedNum(context, rl_l, 0);

	}

	@Override
	public void onClick(View v) {
		
	}
	
	private void Toproduct(){
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
