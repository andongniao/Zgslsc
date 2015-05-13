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
import com.example.educonsult.adapters.KnowFenleiAdapter;

public class KnowSlidMenu extends Fragment {
	private View view,v;
	private ListView lv_l,lv_r;
	private LinearLayout ll_r;
	private Context context;
	private ArrayList<String> list;
	private HomeSlidAdapter adapter_r;
	private KnowFenleiAdapter adapter_l;
	private LinearLayout addll,ll_mycenter,ll_about;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.know_slidemenu_view, container, false);
		init();
		addlistener();
		return view;
	}

	private void addlistener() {
		
	}

	private void init() {
		context = getActivity();
		list = new ArrayList<String>();
		lv_l = (ListView) view.findViewById(R.id.know_slid_view_lv_l);
		ll_r = (LinearLayout) view.findViewById(R.id.know_slid_view_ll_r);
		ArrayList<Integer> l = new ArrayList<Integer>();
		for(int i=0;i<8;i++){
			l.add(i);
		}
		adapter_l = new KnowFenleiAdapter(context, l);
		lv_l.setAdapter(adapter_l);
		lv_r = (ListView) view.findViewById(R.id.know_slid_view_lv_r);
		//adapter_r = new HomeSlidAdapter(context, list,2);
		//lv_r.setAdapter(adapter_r);
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
