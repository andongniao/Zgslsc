package com.example.educonsult.activitys;

import java.util.ArrayList;

import com.example.educonsult.R;
import com.example.educonsult.adapters.XinjianAdapter;
import com.example.educonsult.tools.Util;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

public class XinjianActivity extends BaseActivity{
	private ListView lv;
	private Context context;
	private ArrayList<Object> list;
	private XinjianAdapter adapter;
	private LinearLayout ll_isnull;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightRVisible();
		topRightTGone();
		setTopLeftTv(R.string.xinjian_title);
		setContentXml(R.layout.xinjian);
		init();
		String flag = getIntent().getStringExtra("flag");
		if(Util.IsNull(flag)){
			if("ghhome".equals(flag)){
				GqHomeActivity.isread = true;
			}else if("address".equals(flag)){
				AddressActivity.isread = true;
			}else if("service".equals(flag)){
				ServiceCenterActivity.isread = true;
			}
			else if("qianbao".equals(flag)){
				QianBaoActivity.isread = true;
			}
			else if("myinfo".equals(flag)){
				MyInfoActivity.isread = true;
			}
			else if("gqtwo".equals(flag)){
				GqTwoActivity.isread = true;
			}
//			else if("gqtwo".equals(flag)){
//				GqTwoActivity.isread = true;
//			}
		}
	}

	private void init() {
		context = this;
		lv = (ListView) findViewById(R.id.xinjian_lv);
		ll_isnull = (LinearLayout) findViewById(R.id.xinjian_ll_isnull);
		list = new ArrayList<Object>();
		list.add(1);
		list.add(2);
		lv.setEmptyView(ll_isnull);
		if(list!=null){
			if(list.size()>0){
				ll_isnull.setVisibility(View.GONE);
				lv.setVisibility(View.VISIBLE);
			}else{
				ll_isnull.setVisibility(View.VISIBLE);
				lv.setVisibility(View.GONE);
			}
		}
		adapter = new XinjianAdapter(context, list);
		lv.setAdapter(adapter);
	}

}
