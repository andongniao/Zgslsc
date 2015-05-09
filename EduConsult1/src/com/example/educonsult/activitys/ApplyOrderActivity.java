package com.example.educonsult.activitys;

import java.util.ArrayList;

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.R;
import com.example.educonsult.adapters.ApplyOrderHomeAdapter;
import com.example.educonsult.adapters.MyOrderHomeAdapter;
import com.example.educonsult.tools.Util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ApplyOrderActivity extends BaseActivity{
	private Context context;
	private ListView lv;
	private ArrayList<Integer> list;
	private ApplyOrderHomeAdapter adapter;
	private Myorder myorder;
	private Intent intent;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		setTopLeftTv(R.string.applyinfo_title);
		topRightTGone();
		
		setContentXml(R.layout.apply_home_layout);
		init();
		
	}

	

	private void init() {
		context = this;
		myorder = new Myorder() {

			@Override
			public void delte(int index) {
				list.remove(index);
				adapter.SetData(list);
				adapter.notifyDataSetChanged();
			}
		};
		
		list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		
		lv = (ListView) findViewById(R.id.apply_home_lv);
		//lv.setEmptyView(ll_isnull);
		
		
		
		adapter = new ApplyOrderHomeAdapter(context, list,myorder);
		lv.setAdapter(adapter);
		
	}

	

	
	public interface Myorder{
		void delte(int index);
	}

}
