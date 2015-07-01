package com.xunbo.store.fragments;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apkplug.Bundle.BundleControl;
import org.apkplug.Bundle.OSGIServiceAgent;
import org.apkplug.Bundle.installCallback;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.xunbo.store.ExampleActivity;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.activitys.GqTwoActivity;
import com.xunbo.store.activitys.HomePagerActivity;
import com.xunbo.store.activitys.LoginActivity;
import com.xunbo.store.activitys.ProductDetaileActivity;
import com.xunbo.store.activitys.SearchHomeActivity;
import com.xunbo.store.activitys.SearchResultActivity;
import com.xunbo.store.activitys.StoreActivity;
import com.xunbo.store.activitys.XinjianActivity;
import com.xunbo.store.activitys.BDCardActivity.RefeshData;
import com.xunbo.store.adapters.HomeGridAdapter;
import com.xunbo.store.adapters.HomeLikeAdapter;
import com.xunbo.store.adapters.HomeRuzhuAdapter;
import com.xunbo.store.beans.HomeBean;
import com.xunbo.store.beans.ListUserBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.beans.UserBean;
import com.xunbo.store.myviews.MyGridView;
import com.xunbo.store.myviews.RefreshableView;
import com.xunbo.store.myviews.RefreshableView.RefreshListener;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class HomeLayoutFragment extends Fragment implements OnClickListener,RefreshListener{
	private MyGridView gv_siliao,gv_shouyao,gv_yzsb,gv_qxyz,gv_tianjiaji,gv_yuanliao,gv_qita;
	private LinearLayout lv_rem,lv_siliao,lv_shouyao,lv_yzsb,lv_qxyz,lv_tianjiaji,lv_yuanliao,lv_qita;
	private ImageView ima_top,ima_centent1,ima_centent2,ima_rem_left,ima_rem_r1,ima_rem_r2,ima_rem1,
	ima_rem2,ima_rem3,ima_rem4,ima_fenlei;
	private TextView tv_title1,tv_title2,tv_title3,tv_title4,tv_detaile1,tv_detaile2,
	tv_detaile3,tv_detaile4;
	private Context context;
	private ScrollView sc;
	private MyApplication mp;
	private View view;
	private Intent intent;
	private RelativeLayout top_rl;
	private Message msg;
	private HomeGridAdapter siliaoAdapter,shouyaoAdapter,yzsbAdapter,qxyzAdapter,
	tianjiajiAdapter,yuanliaoAdapter;
	private ThreadWithProgressDialog myPDT;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.home_layout, container, false);
		init();
		addlistener();
		return view;
	}


	private void init() {
		context = getActivity();
		mp=new MyApplication();
		myPDT=new ThreadWithProgressDialog();
		sc=(ScrollView)view.findViewById(R.id.home_layout_sc);
		top_rl = (RelativeLayout) view.findViewById(R.id.home_layout_rl_search);
		tv_title1=(TextView)view.findViewById(R.id.home_layout_tv_rem_title_1);
		tv_title2=(TextView)view.findViewById(R.id.home_layout_tv_rem_title_2);
		tv_title3=(TextView)view.findViewById(R.id.home_layout_tv_rem_title_3);
		tv_title4=(TextView)view.findViewById(R.id.home_layout_tv_rem_title_4);
		tv_detaile1=(TextView)view.findViewById(R.id.home_layout_tv_rem_detaile_1);
		tv_detaile2=(TextView)view.findViewById(R.id.home_layout_tv_rem_detaile_2);
		tv_detaile3=(TextView)view.findViewById(R.id.home_layout_tv_rem_detaile_3);
		tv_detaile4=(TextView)view.findViewById(R.id.home_layout_tv_rem_detaile_4);
		ima_fenlei=(ImageView)view.findViewById(R.id.home_layout_iv_cat);
		ima_fenlei.setOnClickListener(this);
		ima_top=(ImageView)view.findViewById(R.id.home_layout_iv_top);
		ima_centent1=(ImageView)view.findViewById(R.id.home_layout_iv_center1);
		ima_centent2=(ImageView)view.findViewById(R.id.home_layout_iv_center2);
		ima_rem_left=(ImageView)view.findViewById(R.id.home_layout_iv_rem_top_lf);
		ima_rem_r1=(ImageView)view.findViewById(R.id.home_layout_iv_rem_right__t);
		ima_rem_r2=(ImageView)view.findViewById(R.id.home_layout_iv_rem_right_b);
		ima_rem1=(ImageView)view.findViewById(R.id.home_layout_iv_rem_1);
		ima_rem2=(ImageView)view.findViewById(R.id.home_layout_iv_rem_2);
		ima_rem3=(ImageView)view.findViewById(R.id.home_layout_iv_rem_3);
		ima_rem4=(ImageView)view.findViewById(R.id.home_layout_iv_rem_4);
		lv_rem=(LinearLayout)view.findViewById(R.id.home_layout_ll_more_rem);
		lv_siliao=(LinearLayout)view.findViewById(R.id.home_layout_ll_more_siliao);
		lv_shouyao=(LinearLayout)view.findViewById(R.id.home_layout_ll_more_shouyao);
		lv_yzsb=(LinearLayout)view.findViewById(R.id.home_layout_ll_more_yangzhishebei);
		lv_qxyz=(LinearLayout)view.findViewById(R.id.home_layout_ll_more_chuqinyangzhi);
		lv_tianjiaji=(LinearLayout)view.findViewById(R.id.home_layout_ll_more_tianjiaji);
		lv_yuanliao=(LinearLayout)view.findViewById(R.id.home_layout_ll_more_yuanliao);
		lv_qita=(LinearLayout)view.findViewById(R.id.home_layout_ll_more_qita);
		lv_rem.setOnClickListener(this);
		lv_siliao.setOnClickListener(this);
		lv_shouyao.setOnClickListener(this);
		lv_yzsb.setOnClickListener(this);
		lv_qxyz.setOnClickListener(this);
		lv_tianjiaji.setOnClickListener(this);
		lv_yuanliao.setOnClickListener(this);
		lv_qita.setOnClickListener(this);
		gv_siliao=(MyGridView)view.findViewById(R.id.home_layout_gv_siliao);
		gv_shouyao=(MyGridView)view.findViewById(R.id.home_layout_gv_shouyao);
		gv_yzsb=(MyGridView)view.findViewById(R.id.home_layout_gv_yangzhishebei);
		gv_qxyz=(MyGridView)view.findViewById(R.id.home_layout_gv_chuqinyangzhi);
		gv_tianjiaji=(MyGridView)view.findViewById(R.id.home_layout_gv_tianjiaji);
		gv_yuanliao=(MyGridView)view.findViewById(R.id.home_layout_gv_yuanliao);
		gv_qita=(MyGridView)view.findViewById(R.id.home_layout_gv_qita);
//		siliaoAdapter,shouyaoAdapter,yzsbAdapter,qxyzAdapter,
//		tianjiajiAdapter,yuanliaoAdapter;
		siliaoAdapter=new HomeGridAdapter(context);
		shouyaoAdapter=new HomeGridAdapter(context);
		yzsbAdapter=new HomeGridAdapter(context);
		qxyzAdapter=new HomeGridAdapter(context);
		tianjiajiAdapter=new HomeGridAdapter(context);
		yuanliaoAdapter=new HomeGridAdapter(context);
		gv_qxyz.setAdapter(qxyzAdapter);
		gv_shouyao.setAdapter(shouyaoAdapter);
		gv_siliao.setAdapter(siliaoAdapter);
		gv_tianjiaji.setAdapter(tianjiajiAdapter);
		gv_yuanliao.setAdapter(yuanliaoAdapter);
		gv_yzsb.setAdapter(yzsbAdapter);
		
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
		
	}

	private void addlistener() {
		top_rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) { 
				intent = new Intent(context,SearchHomeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intent);
			}
		});
		gv_siliao.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
//		gv_qita.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		gv_qxyz.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		gv_shouyao.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		gv_tianjiaji.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		
		gv_yuanliao.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		gv_yzsb.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
//		gv_like.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				if(home!=null && home.getMylike()!=null && home.getMylike().size()>0){
//				productBean=home.getMylike().get(arg2);
//				Toproduct(productBean);
//				}
//			}
//		});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_layout_iv_cat:
			msg = HomePagerActivity.handler.obtainMessage();
			msg.obj = HomePagerActivity.SlidTag;
			HomePagerActivity.handler.sendMessage(msg);
			break;
		case R.id.home_layout_ll_more_chuqinyangzhi:
			
			break;
		case R.id.home_layout_ll_more_qita:
			break;
		case R.id.home_layout_ll_more_rem:
			break;
		case R.id.home_layout_ll_more_shouyao:
			break;
		case R.id.home_layout_ll_more_siliao:
			break;
		case R.id.home_layout_ll_more_tianjiaji:
			break;
		case R.id.home_layout_ll_more_yangzhishebei:
			break;
		case R.id.home_layout_ll_more_yuanliao:
			break;
			
		}
	}
 private void ToSearch(String text){
	 SearchResultActivity.isproductfinish=false;
	 intent=new Intent(context, SearchResultActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("searchtype", "1");
		intent.putExtra("searchorder", "0");
		intent.putExtra("searchtext", text);
		startActivity(intent);
 }
 private void ToGqTwo(String text){
	 intent=new Intent(context, GqTwoActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("searchtext", text);
		intent.putExtra("num", 0);
		startActivity(intent);
 }

	private void Toproduct(ProductBean productBean){
		
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//intent.putExtra("productdetaile", value);
		Bundle b=new Bundle();
		b.putSerializable("product", productBean);
		intent.putExtra("productbundle", b);
		startActivity(intent);
	}




	

	private void SetPrice(TextView v ,String s){
		v.setText("￥"+s);
	}


	@Override
	public void onRefresh(RefreshableView view) {
		// TODO Auto-generated method stub
		
		
	}
	




}
