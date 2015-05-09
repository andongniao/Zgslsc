package com.example.educonsult.activitys;

import java.util.ArrayList;

import com.example.educonsult.R;
import com.example.educonsult.adapters.MyOrderInfoAdapter;
import com.example.educonsult.myviews.MyListview;

import android.R.integer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MyOrderInfoActivity extends BaseActivity implements
OnClickListener {
	private TextView tv_yunfeimoney,tv_ordermoney,tv_uname,tv_uphone,
	tv_uaddress,tv_cname,tv_yunfei,tv_rmoney,tv_oid,tv_otime,tv_no,tv_ok;
	private MyListview listview;
	private MyOrderInfoAdapter myOrderInfoAdapter;
	private ArrayList<Integer> list;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		topRightTGone();
		setTopLeftTv(R.string.myorderinfo_title);
		setContentXml(R.layout.myorderinfo);
		init();
	}
	private void init(){
		tv_yunfeimoney=(TextView)findViewById(R.id.myorderinfo_yunfeimoney);
		tv_yunfeimoney.setText("运费金额：￥");
		tv_ordermoney=(TextView)findViewById(R.id.myorderinfo_ordermoney);
		tv_ordermoney.setText("订单金额：+￥");
		tv_uname=(TextView)findViewById(R.id.myorderinfo_username);
		tv_uphone=(TextView)findViewById(R.id.myorderinfo_userphone);
		tv_uaddress=(TextView)findViewById(R.id.myorderinfo_useraddress);
		tv_cname=(TextView)findViewById(R.id.myorderinfo_computername);
		tv_yunfei=(TextView)findViewById(R.id.myorderinfo_yunfei);
		tv_rmoney=(TextView)findViewById(R.id.myorderinfo_realmoney);
		tv_oid=(TextView)findViewById(R.id.myorderinfo_orderid);
		tv_otime=(TextView)findViewById(R.id.myorderinfo_ordertime);
		tv_ok=(TextView)findViewById(R.id.myorderinfo_ok);
		tv_no=(TextView)findViewById(R.id.myorderinfo_quxiao);
		tv_ok.setOnClickListener(this);
		tv_no.setOnClickListener(this);
		listview=(MyListview)findViewById(R.id.myorderinfo_list);
		list=new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		myOrderInfoAdapter=new MyOrderInfoAdapter(this, list);
		listview.setAdapter(myOrderInfoAdapter);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.myorderinfo_ok:

			break;
		case R.id.myorderinfo_quxiao:
			break;

		default:
			break;
		}
	}

}
