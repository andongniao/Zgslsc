package com.example.educonsult.fragments;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
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

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.R;
import com.example.educonsult.activitys.HomePagerActivity;
import com.example.educonsult.adapters.FenleiAdapter;
import com.example.educonsult.adapters.HomeSlidAdapter;

public class HomePageFragmentMenu extends Fragment {
	private View view,v;
	private ListView lv_l,lv_r;
	private LinearLayout ll_r;
	private Context context;
	private ArrayList<String> list;
	private HomeSlidAdapter adapter_r;
	private FenleiAdapter adapter_l;
	private LinearLayout addll,ll_mycenter,ll_about;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.slidemenu_view, container, false);
		init();
		addlistener();
		return view;
	}

	private void addlistener() {
		ll_mycenter.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ExampleActivity.setCurrentTab(2);
				Message msg = HomePagerActivity.handler.obtainMessage();
				msg.obj = HomePagerActivity.SlidTag;
				HomePagerActivity.handler.sendMessage(msg);
			}
		});
		ll_about.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ExampleActivity.setCurrentTab(4);
				Message msg = HomePagerActivity.handler.obtainMessage();
				msg.obj = HomePagerActivity.SlidTag;
				HomePagerActivity.handler.sendMessage(msg);
			}
		});
	}

	private void init() {
		context = getActivity();
		list = new ArrayList<String>();
		ll_mycenter = (LinearLayout) view.findViewById(R.id.slid_view_ll_mycenter);
		ll_about = (LinearLayout) view.findViewById(R.id.slid_view_ll_about);
		lv_l = (ListView) view.findViewById(R.id.slid_view_lv_l);
		ll_r = (LinearLayout) view.findViewById(R.id.slid_view_ll_r);
		ArrayList<Integer> l = new ArrayList<Integer>();
		for(int i=0;i<8;i++){
			l.add(i);
		}
		adapter_l = new FenleiAdapter(context, l);
		lv_l.setAdapter(adapter_l);
		lv_r = (ListView) view.findViewById(R.id.slid_view_lv_r);
		adapter_r = new HomeSlidAdapter(context, list,2);
		lv_r.setAdapter(adapter_r);
		lv_l.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(arg2>1){
					ll_r.setVisibility(View.VISIBLE);
					adapter_l.SetData(arg2);
					adapter_l.notifyDataSetChanged();
				}
			}
		});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

}
