package com.example.educonsult.fragments;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
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

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.ExampleActivity;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.activitys.GqHomeActivity;
import com.example.educonsult.activitys.GqTwoActivity;
import com.example.educonsult.activitys.HomePagerActivity;
import com.example.educonsult.activitys.LoginActivity;
import com.example.educonsult.activitys.SearchResultActivity;
import com.example.educonsult.activitys.WelcomeActivity;
import com.example.educonsult.adapters.FenleiAdapter;
import com.example.educonsult.adapters.HomeSlidAdapter;
import com.example.educonsult.beans.FenleiBean;
import com.example.educonsult.beans.ListAreaBean;
import com.example.educonsult.beans.ListFenleiBean;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.Util;

public class HomePageFragmentMenu extends Fragment {
	private View view,v;
	private ListView lv_l,lv_r;
	private LinearLayout ll_r;
	private Context context;
	private ArrayList<String> list;
	private HomeSlidAdapter adapter_r;
	private FenleiAdapter adapter_l;
	private LinearLayout addll,ll_mycenter,ll_about;
	private String title;
	private Message msg;
	private ThreadWithProgressDialog myPDT;
	//private ListAreaBean listAreaBean;
	private ListFenleiBean listFenleiBean;
	private String filename=MyApplication.FenleiName;
	private Util u;
	private ArrayList<FenleiBean> fenleilist, listchile;
	
	
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
		u=new Util(context);
		if(u.isExistDataCache(filename) && u.isReadDataCache(filename)){
			listFenleiBean=(ListFenleiBean)u.readObject(filename);
			fenleilist=listFenleiBean.getList();
		}else{
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
			}
		}
//		fenleilist=new ArrayList<FenleiBean>();
		
		listchile=new ArrayList<FenleiBean>();
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
		
		
		lv_l.setOnItemClickListener(new OnItemClickListener() {
			Intent intent;
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				switch (arg2) {
				case 0:
					msg = HomePagerActivity.handler.obtainMessage();
					msg.obj = HomePagerActivity.SlidTag;
					HomePagerActivity.handler.sendMessage(msg);
					break;
				case 1:
//					intent=new Intent(context, GqHomeActivity.class);
//					startActivity(intent);
					msg = HomePagerActivity.handler.obtainMessage();
					msg.obj = HomePagerActivity.SlidTag;
					HomePagerActivity.handler.sendMessage(msg);
					break;
				case 2:
					ll_r.setVisibility(View.VISIBLE);
					adapter_l.SetData(arg2);
					adapter_l.notifyDataSetChanged();
					title="饲料";
					for(int i=0;i<fenleilist.size();i++){
						if(fenleilist.get(i).getCatname().equals(title) ||
								fenleilist.get(i).getCatid()==1918){
							listchile=fenleilist.get(i).getChild();
							adapter_r = new HomeSlidAdapter(context, listchile,2);
							lv_r.setAdapter(adapter_r);
							lv_r.setOnItemClickListener(new OnItemoclick(listchile));
						}
					}
					break;
				case 3:
					ll_r.setVisibility(View.VISIBLE);
					adapter_l.SetData(arg2);
					adapter_l.notifyDataSetChanged();
					title="兽药";
					for(int i=0;i<fenleilist.size();i++){
						if(fenleilist.get(i).getCatname().equals(title) ||
								fenleilist.get(i).getCatid()==2152){
							listchile=fenleilist.get(i).getChild();
							adapter_r = new HomeSlidAdapter(context, listchile,2);
							lv_r.setAdapter(adapter_r);
							lv_r.setOnItemClickListener(new OnItemoclick(listchile));
						}
					}
					break;
				case 4:
					ll_r.setVisibility(View.VISIBLE);
					adapter_l.SetData(arg2);
					adapter_l.notifyDataSetChanged();
					title="养殖设备与机械";
					for(int i=0;i<fenleilist.size();i++){
						if(fenleilist.get(i).getCatname().equals(title) ||
								fenleilist.get(i).getCatid()==2061){
							listchile=fenleilist.get(i).getChild();
							adapter_r = new HomeSlidAdapter(context, listchile,2);
							lv_r.setAdapter(adapter_r);
							lv_r.setOnItemClickListener(new OnItemoclick(listchile));
						}
					}
					break;
				case 5:
					ll_r.setVisibility(View.VISIBLE);
					adapter_l.SetData(arg2);
					adapter_l.notifyDataSetChanged();
					title="畜禽养殖";
					for(int i=0;i<fenleilist.size();i++){
						if(fenleilist.get(i).getCatname().equals(title) ||
								fenleilist.get(i).getCatid()==2099){
							listchile=fenleilist.get(i).getChild();
							adapter_r = new HomeSlidAdapter(context, listchile,2);
							lv_r.setAdapter(adapter_r);
							lv_r.setOnItemClickListener(new OnItemoclick(listchile));
						}
					}
					break;
				case 6:
					ll_r.setVisibility(View.VISIBLE);
					adapter_l.SetData(arg2);
					adapter_l.notifyDataSetChanged();
					title="添加剂";
					for(int i=0;i<fenleilist.size();i++){
						if(fenleilist.get(i).getCatname().equals(title) ||
								fenleilist.get(i).getCatid()==2021){
							listchile=fenleilist.get(i).getChild();
							adapter_r = new HomeSlidAdapter(context, listchile,2);
							lv_r.setAdapter(adapter_r);
							lv_r.setOnItemClickListener(new OnItemoclick(listchile));
						}
					}
					break;
				case 7:
					ll_r.setVisibility(View.VISIBLE);
					adapter_l.SetData(arg2);
					adapter_l.notifyDataSetChanged();
					title="饲料原料";
					for(int i=0;i<fenleilist.size();i++){
						if(fenleilist.get(i).getCatname().equals(title) ||
								fenleilist.get(i).getCatid()==1962){
							listchile=fenleilist.get(i).getChild();
							adapter_r = new HomeSlidAdapter(context, listchile,2);
							lv_r.setAdapter(adapter_r);
							lv_r.setOnItemClickListener(new OnItemoclick(listchile));
						}
					}
					break;
				

				default:
					break;
				}
				
			}
		});
		
		lv_r.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				/*Intent intent=new Intent(context,GqTwoActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("title", title);
				intent.putExtra("num", arg2);
				startActivity(intent);*/
				Util.ShowToast(context, R.string.maimeng);
			}
		});
	}

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}

	class OnItemoclick implements OnItemClickListener{
		private ArrayList<FenleiBean> listchile;
		public OnItemoclick(ArrayList<FenleiBean> listchile){
			this.listchile=listchile;
		}

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			msg = HomePagerActivity.handler.obtainMessage();
			msg.obj = HomePagerActivity.SlidTag;
			HomePagerActivity.handler.sendMessage(msg);
//			Util.ShowToast(context, R.string.maimeng);
			String name = listchile.get(arg2).getCatname();
			ToSearch(name);
			
		}
		
	}
	
	// 任务
	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			//				Toast.makeText(context, "cancle", 1000).show();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(listFenleiBean!=null){
				if("200".equals(listFenleiBean.getCode())){
					u.saveObject(listFenleiBean, filename);
				}else if("300".equals(listFenleiBean.getCode())){
					MyApplication.mp.setlogin(false);
					Util.ShowToast(context, R.string.login_out_time);
					Intent i = new Intent(getActivity(),LoginActivity.class);
					startActivity(i);
					getActivity().finish();
				}else{
					Util.ShowToast(context, listFenleiBean.getMsg());
				}
			}else{
				Util.ShowToast(context, "初始化失败,请保证网络通畅后重试");
			}
			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			Send s = new Send(context);
			listFenleiBean = s.GetFenlei();
			return true;
		}
	}
	 private void ToSearch(String text){
		 Intent intent=new Intent(context, SearchResultActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("searchtype", "1");
			intent.putExtra("searchorder", "0");
			intent.putExtra("searchtext", text);
			startActivity(intent);
	 }
	
}
