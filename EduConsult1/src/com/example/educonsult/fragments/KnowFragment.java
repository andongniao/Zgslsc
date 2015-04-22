package com.example.educonsult.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.activitys.KnowHomeActivity;
import com.example.educonsult.activitys.ProductDetaileActivity;
import com.example.educonsult.adapters.KnowHomeLvAdapter;
import com.example.educonsult.adapters.ZhanhuiHomeAdapter;
import com.example.educonsult.myviews.MyGridView;
import com.example.educonsult.myviews.MyListview;

public class KnowFragment extends Fragment implements OnClickListener{
	private Context context;
	private View v;
	private Handler handler;
	private LinearLayout ll_back,ll_tuijian_l,ll_tuijian_t,ll_tuijian_r,ll_fenlei,ll_tiwen,
	ll_show_l,ll_show_t,ll_show_r;
	private TextView show_l,show_t,show_r;
	private MyGridView gridView;
	private MyListview lv;
	private ZhanhuiHomeAdapter adapter_gv;
	private ArrayList<Integer> list;
	private KnowHomeLvAdapter adapter_lv;
	private ArrayList<View>list_view;
	private Intent intent;
	private Message msg;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.know_home_layout, container, false);
		init();
		addlistener();
		return v;
	}

	private void init() {
		list_view = new ArrayList<View>();
		list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		list.add(3);
		context = getActivity();
		handler = KnowHomeActivity.handler;
		ll_back = (LinearLayout) v.findViewById(R.id.know_home_ll_back);
		ll_back.setOnClickListener(this);
		gridView = (MyGridView) v.findViewById(R.id.know_home_gv);
		adapter_gv = new ZhanhuiHomeAdapter(context, list);
		gridView.setAdapter(adapter_gv);
		lv = (MyListview) v.findViewById(R.id.know_home_lv);
		adapter_lv = new KnowHomeLvAdapter(context, list);
		lv.setAdapter(adapter_lv);
		show_l = (TextView) v.findViewById(R.id.know_home_tv_show_l);
		show_t = (TextView) v.findViewById(R.id.know_home_tv_show_t);
		show_r = (TextView) v.findViewById(R.id.know_home_tv_show_r);
		ll_show_l = (LinearLayout) v.findViewById(R.id.know_home_ll_tuijian);
		ll_show_t = (LinearLayout) v.findViewById(R.id.know_home_ll_huida);
		ll_show_r = (LinearLayout) v.findViewById(R.id.know_home_ll_like);
		
		ll_show_l.setOnClickListener(this);
		ll_show_t.setOnClickListener(this);
		ll_show_r.setOnClickListener(this);
		list_view.add(show_l);
		list_view.add(show_t);
		list_view.add(show_r);
		ll_tuijian_l = (LinearLayout) v.findViewById(R.id.know_home_ll_l_jinxuan);
		ll_tuijian_l.setOnClickListener(this);
		ll_tuijian_t = (LinearLayout) v.findViewById(R.id.know_home_ll_t_jinxuan);
		ll_tuijian_t.setOnClickListener(this);
		ll_tuijian_r = (LinearLayout) v.findViewById(R.id.know_home_ll_r_jinxuan);
		ll_tuijian_r.setOnClickListener(this);
		ll_fenlei = (LinearLayout) v.findViewById(R.id.know_home_ll_fenlei);
		ll_fenlei.setOnClickListener(this);
		ll_tiwen = (LinearLayout) v.findViewById(R.id.know_home_ll_tiwen_i);
		ll_tiwen.setOnClickListener(this);
		
	}

	private void addlistener() {
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				
			}
		});
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated	 method stub
				
			}
		});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.know_home_ll_back:
				msg = handler.obtainMessage();
				msg.what=1;
				handler.sendMessage(msg);
			break;
		case R.id.know_home_ll_l_jinxuan:
			ToProduct();
		break;
		case R.id.know_home_ll_t_jinxuan:
			ToProduct();
		break;
		case R.id.know_home_ll_r_jinxuan:
			ToProduct();
		break;
		case R.id.know_home_ll_tuijian:
			Change(0);
		break;
		case R.id.know_home_ll_huida:
			Change(1);
		break;
		case R.id.know_home_ll_like:
			Change(2);
		break;//
		case R.id.know_home_ll_fenlei:
			msg = handler.obtainMessage();
			msg.obj=KnowHomeActivity.SlidTag;
			handler.sendMessage(msg);
		break;
		case R.id.know_home_ll_tiwen_i:
			Change(2);
		break;
		}
	}

	private void Change(int index) {
		for(int i=0;i<list_view.size();i++){
			if(i==index){
				list_view.get(i).setVisibility(View.VISIBLE);
			}else{
				list_view.get(i).setVisibility(View.INVISIBLE);
			}
		}
	}

	private void ToProduct() {
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}

}
