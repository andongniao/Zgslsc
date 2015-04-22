package com.example.educonsult.activitys;

import java.util.ArrayList;

import com.easemob.chat.core.e.c;
import com.example.educonsult.R;
import com.example.educonsult.adapters.ZhanhuiDetaileLvAdapter;
import com.example.educonsult.myviews.MyListview;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

public class ZhanhuiDetaileActivity extends BaseActivity{
	private  MyListview lv;
	private Context context;
	private ArrayList<Integer> list;
	private ZhanhuiDetaileLvAdapter adapter;
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightRVisible();
		setTopLeftTv(R.string.zhanhui_detaile_title);
		setContentXml(R.layout.zhanhui_detaile_layout);
		init();
	}

	private void init() {
		context = this;
		list = new ArrayList<Integer>();
		for(int i = 0;i<5;i++){
			list.add(i);
		}
		lv = (MyListview) findViewById(R.id.zhanhui_detaile_lv);
		adapter = new ZhanhuiDetaileLvAdapter(context, list);
		lv.setAdapter(adapter);
	}

}
