package com.example.educonsult.fragments;


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
import android.os.Environment;
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
import com.example.educonsult.ExampleActivity;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.activitys.GqHomeActivity;
import com.example.educonsult.activitys.GqTwoActivity;
import com.example.educonsult.activitys.HomePagerActivity;
import com.example.educonsult.activitys.KnowHomeActivity;
import com.example.educonsult.activitys.LoginActivity;
import com.example.educonsult.activitys.NewHomeActivity;
import com.example.educonsult.activitys.ProductDetaileActivity;
import com.example.educonsult.activitys.StoreActivity;
import com.example.educonsult.activitys.XinjianActivity;
import com.example.educonsult.activitys.ZhanhuiHomeActivity;
import com.example.educonsult.adapters.HomeLikeAdapter;
import com.example.educonsult.adapters.HomeRuzhuAdapter;
import com.example.educonsult.beans.HomeBean;
import com.example.educonsult.beans.ListAreaBean;
import com.example.educonsult.beans.ListUserBean;
import com.example.educonsult.beans.ProdectDetaileBean;
import com.example.educonsult.beans.ProductBean;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.myviews.MyGridView;
import com.example.educonsult.myviews.RefreshableView;
import com.example.educonsult.myviews.RefreshableView.RefreshListener;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.Util;
import com.example.educonsult.tools.FileUtil.filter.apkFilter;
import com.example.educonsult.tools.FileUtil.filter.isFilesFilter;

public class HomeFragment extends Fragment implements OnClickListener,RefreshListener{
	private MyGridView gv_like,gv_ruzhu,gv;
	private View view;
	private HomeLikeAdapter likeadapter;
	private HomeRuzhuAdapter ruzhuadapter;
	private ArrayList<ProductBean> list;
	private Context context;
	private ScrollView sc;
	private MyApplication mp;
	private LinearLayout ll_gq,ll_news,ll_know,ll_zhanhui,ll_pinpai,ll_zhaobiao,ll_team,ll_leimu,ll_sl,ll_sy,ll_shebei,
	ll_chuqin,ll_tianjia,ll_yuanliao,ll_tuijian_l,ll_tuijian_one,ll_tuijian_two,ll_tuijian_three,
	ll_tuijian_four,ll_tuijian_b_l,ll_tuijian_b_t,ll_tuijian_b_r,ll_search,
	ll_hot_l,ll_hot_t,ll_hot_r
	,ll_hot_b_l,ll_hot_b_t,ll_hot_b_r;
	private RelativeLayout top_rl;
	private TextView tv_m_jingpin,tv_m_hot,tv_m_ruzhu,
	tv_tj_l_title,tv_tj_l_price,tv_tj_t_title,tv_tj_t_price,tv_tj_r_title,tv_tj_r_price,
	tv_hot_l_title,tv_hot_l_price,tv_hot_t_title,tv_hot_t_price,tv_hot_r_title,tv_hot_r_price;
	private ImageView iv_tj_l,iv_tj_t,iv_tj_r,iv_tj_t_l,iv_tj_1,iv_tj_2,iv_tj_3,iv_tj_4,
	iv_hot_top_l,iv_hot_top_t,iv_hot_top_r,iv_hot_b_l,iv_hot_b_t,iv_hot_b_r,
	iv_fenlei,iv_ad1,iv_ad2;
	private EditText et_search;
	private Intent intent;
	private FrameworkInstance frame=null;
	private String name = "ChatUIDemo.apk";
	private List<org.osgi.framework.Bundle> bundles=null;
	private String title;
	private ListUserBean listUserBean;
	private Message msg;
	private ThreadWithProgressDialog myPDT;
	private HomeBean home;
	private ArrayList<ImageView>list_rem,list_hot,list_com;
	private ArrayList<TextView>list_tv_rem_title,list_tv_rem_price,list_tv_hot_title,list_tv_hot_price;
	private RefreshableView refreshableView;
	private Handler handler;
	private String filename = "home";
	private ProductBean productBean;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.homefragment, container, false);
		init();
		addlistener();
		return view;
	}


	private void init() {
		context = getActivity();
		list_rem = new ArrayList<ImageView>();
		list_hot = new ArrayList<ImageView>();
		list_com = new ArrayList<ImageView>();
		list_tv_rem_title = new ArrayList<TextView>();
		list_tv_rem_price = new ArrayList<TextView>();
		list_tv_hot_title = new ArrayList<TextView>();
		list_tv_hot_price = new ArrayList<TextView>();
		frame=MyApplication.frame;
		top_rl = (RelativeLayout) view.findViewById(R.id.home_top_rl_right);
//		Util.SetRedNum(context, top_rl, 1);
		mp = MyApplication.mp;
		ProductBean bean = new ProductBean();
		mp.SaveSee(bean);
		mp.GetSee();
//		apkFilter apkFilter=new apkFilter(new isFilesFilter(null));
//		String path = Environment.getExternalStorageDirectory().getPath()+"/"+name;
//		try {
//			boolean b = Tosd(name,  path);
//			//调用osgi插件安装服务安装插件
//
//			boolean a = MyApplication.sp.getBoolean("isinstaed", false); 
//			if(b && !a){
//				install(path,new myinstallCallback());
//			}
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		refreshableView = (RefreshableView) view.findViewById(R.id.home_rview);
		refreshableView.setRefreshListener(this);
		sc = (ScrollView) view.findViewById(R.id.home_sc);
		iv_ad1 = (ImageView) view.findViewById(R.id.home_iv_ad1);
		iv_ad2 = (ImageView) view.findViewById(R.id.home_iv_ad2);
		/***********rem**************/
		iv_tj_t_l =(ImageView) view.findViewById(R.id.home_iv_tuijian_top_l);
		list_rem.add(iv_tj_t_l);
		iv_tj_1 =(ImageView) view.findViewById(R.id.home_iv_tuijian_1);
		list_rem.add(iv_tj_1);
		iv_tj_2 =(ImageView) view.findViewById(R.id.home_iv_tuijian_2);
		list_rem.add(iv_tj_2);
		iv_tj_3 =(ImageView) view.findViewById(R.id.home_iv_tuijian_3);
		list_rem.add(iv_tj_3);
		iv_tj_4 =(ImageView) view.findViewById(R.id.home_iv_tuijian_4);
		list_rem.add(iv_tj_4);
		iv_tj_l =(ImageView) view.findViewById(R.id.home_iv_tuijian_l);
		list_rem.add(iv_tj_l);
		iv_tj_t =(ImageView) view.findViewById(R.id.home_iv_tuijian_t);
		list_rem.add(iv_tj_t);
		iv_tj_r =(ImageView) view.findViewById(R.id.home_iv_tuijian_r);
		list_rem.add(iv_tj_r);
		

		tv_tj_l_title = (TextView) view.findViewById(R.id.home_tv_tuijian_l_title);
		tv_tj_t_title = (TextView) view.findViewById(R.id.home_tv_tuijian_t_title);
		tv_tj_r_title = (TextView) view.findViewById(R.id.home_tv_tuijian_r_title);
		list_tv_rem_title.add(tv_tj_l_title);
		list_tv_rem_title.add(tv_tj_t_title);
		list_tv_rem_title.add(tv_tj_r_title);

		tv_tj_l_price = (TextView) view.findViewById(R.id.home_tv_tuijian_l_price);
		tv_tj_t_price = (TextView) view.findViewById(R.id.home_tv_tuijian_t_price);
		tv_tj_r_price = (TextView) view.findViewById(R.id.home_tv_tuijian_r_price);
		list_tv_rem_price.add(tv_tj_l_price);
		list_tv_rem_price.add(tv_tj_t_price);
		list_tv_rem_price.add(tv_tj_r_price);

		/************hot*******************/
		iv_hot_top_l =(ImageView) view.findViewById(R.id.home_iv_hot__top_l);
		list_hot.add(iv_hot_top_l);
		iv_hot_top_t =(ImageView) view.findViewById(R.id.home_iv_hot__top_t);
		list_hot.add(iv_hot_top_t);
		iv_hot_top_r =(ImageView) view.findViewById(R.id.home_iv_hot__top_r);
		list_hot.add(iv_hot_top_r);
		iv_hot_b_l =(ImageView) view.findViewById(R.id.home_iv_hot_b_l);
		list_hot.add(iv_hot_b_l);
		iv_hot_b_l =(ImageView) view.findViewById(R.id.home_iv_hot_b_t);
		list_hot.add(iv_hot_b_t);
		iv_hot_b_l =(ImageView) view.findViewById(R.id.home_iv_hot_b_r);
		list_hot.add(iv_hot_b_r);

		tv_hot_l_title = (TextView) view.findViewById(R.id.home_iv_hot_b_l_title);
		tv_hot_t_title = (TextView) view.findViewById(R.id.home_iv_hot_b_t_title);
		tv_hot_r_title = (TextView) view.findViewById(R.id.home_iv_hot_b_r_title);
		list_tv_hot_title.add(tv_hot_l_title);
		list_tv_hot_title.add(tv_hot_t_title);
		list_tv_hot_title.add(tv_hot_r_title);

		tv_hot_l_price = (TextView) view.findViewById(R.id.home_iv_hot_b_l_price);
		tv_hot_t_price = (TextView) view.findViewById(R.id.home_iv_hot_b_t_price);
		tv_hot_r_price = (TextView) view.findViewById(R.id.home_iv_hot_b_r_price);
		list_tv_hot_price.add(tv_hot_l_price);
		list_tv_hot_price.add(tv_hot_t_price);
		list_tv_hot_price.add(tv_hot_r_price);
		
		

		




		et_search = (EditText) view.findViewById(R.id.title_iv_search);
		et_search.setOnClickListener(this);
		ll_search = (LinearLayout) view.findViewById(R.id.home_ll_top_search);
		ll_search.setOnClickListener(this);
		list = new ArrayList<ProductBean>();
		iv_fenlei = (ImageView) view.findViewById(R.id.title_left_iv);
		iv_fenlei.setOnClickListener(this);
		ll_gq = (LinearLayout) view.findViewById(R.id.home_gongqiu_ll);
		ll_gq.setOnClickListener(this);
		ll_news = (LinearLayout) view.findViewById(R.id.home_news_ll);
		ll_news.setOnClickListener(this);
		ll_know = (LinearLayout) view.findViewById(R.id.home_zhidao_ll);
		ll_know.setOnClickListener(this);
		ll_zhanhui = (LinearLayout) view.findViewById(R.id.home_zhanhui_ll);
		ll_zhanhui.setOnClickListener(this);
		ll_pinpai = (LinearLayout) view.findViewById(R.id.home_pinpai_ll);
		ll_pinpai.setOnClickListener(this);
		ll_zhaobiao = (LinearLayout) view.findViewById(R.id.home_zhaobiao_ll);
		ll_zhaobiao.setOnClickListener(this);
		ll_team = (LinearLayout) view.findViewById(R.id.home_team_ll);
		ll_team.setOnClickListener(this);
		ll_leimu = (LinearLayout) view.findViewById(R.id.home_leimu_ll);
		ll_leimu.setOnClickListener(this);
		ll_sl = (LinearLayout) view.findViewById(R.id.home_siliao_ll);
		ll_sl.setOnClickListener(this);
		ll_sy = (LinearLayout) view.findViewById(R.id.home_shouyao_ll);
		ll_sy.setOnClickListener(this);
		ll_shebei = (LinearLayout) view.findViewById(R.id.home_yangzhi_ll);
		ll_shebei.setOnClickListener(this);
		ll_chuqin = (LinearLayout) view.findViewById(R.id.home_chuqin_ll);
		ll_chuqin.setOnClickListener(this);
		ll_tianjia = (LinearLayout) view.findViewById(R.id.home_tianjiaji_ll);
		ll_tianjia.setOnClickListener(this);
		ll_yuanliao = (LinearLayout) view.findViewById(R.id.home_yuanliao_ll);
		ll_yuanliao.setOnClickListener(this);
		tv_m_jingpin = (TextView) view.findViewById(R.id.home_tv_more_jingpin);
		tv_m_jingpin.setOnClickListener(this);
		tv_m_jingpin.setVisibility(View.GONE);
		tv_m_hot = (TextView) view.findViewById(R.id.home_tv_more_hot);
		tv_m_hot.setOnClickListener(this);
		tv_m_hot.setVisibility(View.GONE);
		tv_m_ruzhu = (TextView) view.findViewById(R.id.home_tv_more_ruzhu);
		tv_m_ruzhu.setOnClickListener(this);
		tv_m_ruzhu.setVisibility(View.GONE);


		ll_tuijian_l = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_l);
		ll_tuijian_l.setOnClickListener(this);
		ll_tuijian_one = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_l_one);
		ll_tuijian_one.setOnClickListener(this);
		ll_tuijian_two = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_l_two);
		ll_tuijian_two.setOnClickListener(this);
		ll_tuijian_three = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_l_three);
		ll_tuijian_three.setOnClickListener(this);
		ll_tuijian_four = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_l_four);
		ll_tuijian_four.setOnClickListener(this);
		ll_tuijian_b_l = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_b_l);
		ll_tuijian_b_l.setOnClickListener(this);
		ll_tuijian_b_t = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_b_t);
		ll_tuijian_b_t.setOnClickListener(this);
		ll_tuijian_b_r = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_b_r);
		ll_tuijian_b_r.setOnClickListener(this);

		ll_hot_l = (LinearLayout) view.findViewById(R.id.home_ll_hot_l);
		ll_hot_l.setOnClickListener(this);
		ll_hot_t = (LinearLayout) view.findViewById(R.id.home_ll_hot_t);
		ll_hot_t.setOnClickListener(this);
		ll_hot_r = (LinearLayout) view.findViewById(R.id.home_ll_hot_r);
		ll_hot_r.setOnClickListener(this);
		ll_hot_b_l = (LinearLayout) view.findViewById(R.id.home_ll_hot_b_l);
		ll_hot_b_l.setOnClickListener(this);
		ll_hot_b_t = (LinearLayout) view.findViewById(R.id.home_ll_hot_b_t);
		ll_hot_b_t.setOnClickListener(this);
		ll_hot_b_r = (LinearLayout) view.findViewById(R.id.home_ll_hot_b_r);
		ll_hot_b_r.setOnClickListener(this);
		/**************************************************/
		final Util util = new Util(context);
//		if(util.isExistDataCache(filename) && util.isReadDataCache(filename)){
//			listUserBean = (ListUserBean) util.readObject(filename);
//			MyApplication.bean = listUserBean.getList().get(0);
//			//			Bitmap b = util.getBitmaoForCahe(MyApplication.bean.getBmp());
//			//			iv_hot_l.setImageBitmap(b);
//		}
//
//
//
//		final String url = MyApplication.bean.getBmp();


		//		Util.Getbitmap(iv_hot_l, url);



		gv_like = (MyGridView) view.findViewById(R.id.home_ulike_gv);
		gv_ruzhu = (MyGridView) view.findViewById(R.id.home_ruzhu_gv);
		//		ruzhuadapter = new HomeRuzhuAdapter(context, list);
		//		gv_ruzhu.setAdapter(ruzhuadapter);


		if(util.isNetworkConnected()){
			myPDT = new ThreadWithProgressDialog();
			String  msg = getResources().getString(R.string.loding);
					myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
//			myPDT.Run(context, new RefeshData(),msg,false);//不可取消
		}else{
			if(util.isExistDataCache(filename) && util.isReadDataCache(filename)){
				home = (HomeBean) util.readObject(filename);
				if(home!=null){
					initdata(home);
				}
			}
			util.ShowToast(context, R.string.net_is_eor);
		}
		/*************测试****************/
		UserBean b = MyApplication.mp.getUser();
		if(b!=null){
		System.out.println("MyApplication.mp.bean.getAuthstr()=="+b.getAuthstr());
		}
//		if(util.isExistDataCache(MyApplication.AreaName) && util.isReadDataCache(MyApplication.AreaName)){
//			ListAreaBean lb = (ListAreaBean) util.readObject(MyApplication.AreaName);
//			if(lb!=null){
//			util.ShowToast(context, lb.getList().get(12).getChild().get(0).getChild().get(0).getArename());
//			}else{
//				util.ShowToast(context, "errrrrrrrr");
//			}
//		}

		
		
		handler = new Handler(){
			@Override
			public void handleMessage(Message m) {
				super.handleMessage(m);
				if(m.what==1){
					refreshableView.finishRefresh();
					home = (HomeBean) m.obj;
					if(home!=null){
					initdata(home);
					}
				}else{
					refreshableView.finishRefresh();
				}
			}
		};
		
	}

	private void addlistener() {
		top_rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
//				intent = new Intent(context,XinjianActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				context.startActivity(intent);

			}
		});
		gv_like.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(home!=null && home.getMylike()!=null && home.getMylike().size()>0){
				productBean=home.getMylike().get(arg2);
				Toproduct(productBean);
				}
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
		case R.id.title_left_iv:
//			Util.ShowToast(context, R.string.maimeng);
			msg = HomePagerActivity.handler.obtainMessage();
			msg.obj = HomePagerActivity.SlidTag;
			HomePagerActivity.handler.sendMessage(msg);
			break;
		case R.id.home_gongqiu_ll:
			Util.ShowToast(context, R.string.maimeng);
//			intent = new Intent(context,GqHomeActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
			break;
		case R.id.home_news_ll:
			Util.ShowToast(context, R.string.maimeng);
//			intent = new Intent(context,NewHomeActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
			break;
		case R.id.home_zhidao_ll:
			Util.ShowToast(context, R.string.maimeng);
//			intent = new Intent(context,KnowHomeActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
			break;

		case R.id.home_zhanhui_ll:
			Util.ShowToast(context, R.string.maimeng);
//			intent = new Intent(context,ZhanhuiHomeActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
			break;
		case R.id.home_pinpai_ll:
			Util.ShowToast(context, R.string.maimeng);
			break;
		case R.id.home_zhaobiao_ll:
			Util.ShowToast(context, R.string.maimeng);
			break;

		case R.id.home_team_ll:
			Util.ShowToast(context, R.string.maimeng);
			//已安装插件列表
//			bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
//			BundleContext context =frame.getSystemBundleContext();
//			for(int i=0;i<context.getBundles().length;i++)
//			{
//				//获取已安装插件
//				bundles.add(context.getBundles()[i]);        	        
//			}
//
//			//			BundleContext context =frame.getSystemBundleContext();
//			startor(bundles);

			break;
		case R.id.home_leimu_ll:
			msg = HomePagerActivity.handler.obtainMessage();
			msg.obj = HomePagerActivity.SlidTag;
			HomePagerActivity.handler.sendMessage(msg);

			break;
		case R.id.home_siliao_ll:
			Util.ShowToast(context, R.string.maimeng);
//			intent = new Intent(this.context,GqTwoActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			title = getResources().getString(R.string.home_siliao);
//			intent.putExtra("title", title);
//			startActivity(intent);
			break;
		case R.id.home_shouyao_ll:
			Util.ShowToast(context, R.string.maimeng);
//			intent = new Intent(this.context,GqTwoActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			title = getResources().getString(R.string.home_shouyao);
//			intent.putExtra("title", title);
//			startActivity(intent);
			break;
		case R.id.home_yangzhi_ll:
			Util.ShowToast(context, R.string.maimeng);
//			intent = new Intent(this.context,GqTwoActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			title = getResources().getString(R.string.home_yangzhishebei);
//			intent.putExtra("title", title);
//			startActivity(intent);
			break;
		case R.id.home_chuqin_ll:
			Util.ShowToast(context, R.string.maimeng);
//			intent = new Intent(this.context,GqTwoActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			title = getResources().getString(R.string.home_chuqinyangzhi);
//			intent.putExtra("title", title);
//			startActivity(intent);
			break;
		case R.id.home_tianjiaji_ll:
			Util.ShowToast(context, R.string.maimeng);
//			intent = new Intent(this.context,GqTwoActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			title = getResources().getString(R.string.home_tianjiaji);
//			intent.putExtra("title", title);
//			startActivity(intent);
			break;
		case R.id.home_yuanliao_ll:
			Util.ShowToast(context, R.string.maimeng);
//			intent = new Intent(this.context,GqTwoActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			title = getResources().getString(R.string.home_yuanliao);
//			intent.putExtra("title", title);
//			startActivity(intent);
			break;
		case R.id.home_tv_more_jingpin:
			//			intent = new Intent(this.context,GqTwoActivity.class);
			//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//			title = getResources().getString(R.string.home_tuijian);
			//			intent.putExtra("title", title);
			//			startActivity(intent);
			break;
		case R.id.home_tv_more_hot:

			break;
		case R.id.home_tv_more_ruzhu:

			break;


		case R.id.home_ll_tuijian_l:
			if(home!=null && home.getRecommend()!=null && home.getRecommend().size()>0){
			productBean=home.getRecommend().get(0);
			Toproduct(productBean);
			}
			break;
		case R.id.home_ll_tuijian_l_one:
			if(home!=null && home.getRecommend()!=null && home.getRecommend().size()>0){
			productBean=home.getRecommend().get(1);
			Toproduct(productBean);
			}
			break;
		case R.id.home_ll_tuijian_l_two:
			if(home!=null && home.getRecommend()!=null && home.getRecommend().size()>0){
			productBean=home.getRecommend().get(2);
			Toproduct(productBean);
			}
			break;
		case R.id.home_ll_tuijian_l_three:
			if(home!=null && home.getRecommend()!=null && home.getRecommend().size()>0){
			productBean=home.getRecommend().get(3);
			Toproduct(productBean);
			}
			break;
		case R.id.home_ll_tuijian_l_four:
			if(home!=null && home.getRecommend()!=null && home.getRecommend().size()>0){
			productBean=home.getRecommend().get(4);
			Toproduct(productBean);
			}
			break;
		case R.id.home_ll_tuijian_b_l:
			if(home!=null && home.getRecommend()!=null && home.getRecommend().size()>0){
			productBean=home.getRecommend().get(5);
			Toproduct(productBean);
			}
			break;
		case R.id.home_ll_tuijian_b_t:
			if(home!=null && home.getRecommend()!=null && home.getRecommend().size()>0){
			productBean=home.getRecommend().get(6);
			Toproduct(productBean);
			}
			break;
		case R.id.home_ll_tuijian_b_r:
			if(home!=null && home.getRecommend()!=null && home.getRecommend().size()>0){
			productBean=home.getRecommend().get(7);
			Toproduct(productBean);
			}
			break;
		case R.id.home_ll_top_search:
			ExampleActivity.setCurrentTab(1);
			break;
		case R.id.title_iv_search:
			ExampleActivity.setCurrentTab(1);
			break;



		case R.id.home_ll_hot_l:
			if(home!=null && home.getHot()!=null && home.getHot().size()>0){
			productBean=home.getHot().get(0);
			Toproduct(productBean);
			}
			break;

		case R.id.home_ll_hot_t:
			if(home!=null && home.getHot()!=null && home.getHot().size()>0){
			productBean=home.getHot().get(1);
			Toproduct(productBean);
			}
			break;
		case R.id.home_ll_hot_r:
			if(home!=null && home.getHot()!=null && home.getHot().size()>0){
			productBean=home.getHot().get(2);
			Toproduct(productBean);
			}
			break;
		case R.id.home_ll_hot_b_l:
			if(home!=null && home.getHot()!=null && home.getHot().size()>0){
			productBean=home.getHot().get(3);
			Toproduct(productBean);
			}
			break;
		case R.id.home_ll_hot_b_t:
			if(home!=null && home.getHot()!=null && home.getHot().size()>0){
			productBean=home.getHot().get(4);
			Toproduct(productBean);
			}
			break;
		case R.id.home_ll_hot_b_r:
			if(home!=null && home.getHot()!=null && home.getHot().size()>0){
			productBean=home.getHot().get(5);
			Toproduct(productBean);
			}
			break;






		}
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

	/**
	 * 安装插件回调函数
	 */
	class myinstallCallback implements installCallback{

		@Override
		public void callback(int arg0, org.osgi.framework.Bundle arg1) {
			if(arg0==installCallback.stutas5||arg0==installCallback.stutas7){
				Editor et = MyApplication.sp.edit();
				et.putBoolean("isinstaed", true);
				et.commit();
				String s =String.format("插件安装 %s ：\n %s",stutasToStr(arg0),showBundle(arg1));
				Toast.makeText(context, s, 200).show();
				System.out.println(s);
				return;
			}
			else{
				String info1="安装状态:"+arg0;
				String s ="插件安装失败 ："+this.stutasToStr(arg0);
				Toast.makeText(context, s, 3000).show();
				System.out.println(s);

			}

		}
		/**
		 * 信息由 http://www.apkplug.com/javadoc/bundledoc1.5.3/
		 * org.apkplug.Bundle 
		 *      接口 installCallback 提供
		 * @param stutas
		 * @return
		 */
		private String stutasToStr(int stutas){
			if(stutas==installCallback.stutas){
				return "缺少SymbolicName";
			}else if(stutas==installCallback.stutas1){
				return "已是最新版本";
			}else if(stutas==installCallback.stutas2){
				return "版本号不正确";
			}else if(stutas==installCallback.stutas3){
				return " 版本相等";
			}else if(stutas==installCallback.stutas4){
				return "无法获取正确的证书";
			}else if(stutas==installCallback.stutas5){
				return "更新成功";
			}else if(stutas==installCallback.stutas6){
				return "证书不一致";
			}else if(stutas==installCallback.stutas7){
				return "安装成功";
			}
			return "状态信息不正确";
		}
	}

	public String showBundle(org.osgi.framework.Bundle b){
		StringBuffer sb=new StringBuffer();
		sb.append("\n插件名称:"+b.getName());
		sb.append("\n插件应用名称:"+b.getSymbolicName());
		sb.append("\n插件版本:"+b.getVersion());
		sb.append("\n插件ID:"+b.getBundleId());
		sb.append("\n插件当前状态:"+b.getState());
		sb.append("\n插件启动Activity:"+b.getBundleActivity());
		return sb.toString();
	}
	public void startor(List<org.osgi.framework.Bundle> list){
		org.osgi.framework.Bundle bundle=list.get(1);
		if(bundle.getState()!=bundle.ACTIVE){
			//判断插件是否已启动
			try {
				bundle.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(bundle.getBundleActivity()!=null){
			//			Toast.makeText(context, "启动"+bundle.getBundleActivity().split(",")[0],
			//				     Toast.LENGTH_SHORT).show();
			Intent i=new Intent();
			i.setClassName(context, bundle.getBundleActivity().split(",")[0]);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}else{

			Toast.makeText(context, "该插件没有配置BundleActivity",
					Toast.LENGTH_SHORT).show();
		}
	}


	public boolean copyApkFromAssets(Context context, String fileName, String path) {  
		boolean copyIsFinish = false;  
		try {  
			InputStream is = context.getAssets().open(fileName);  
			File file = new File(path);  
			file.createNewFile();  
			FileOutputStream fos = new FileOutputStream(file);  
			byte[] temp = new byte[1024];  
			int i = 0;  
			while ((i = is.read(temp)) > 0) {  
				fos.write(temp, 0, i);  
			}  
			fos.close();  
			is.close();  
			copyIsFinish = true;  
		} catch (IOException e) {  
			e.printStackTrace();  
		}  
		return copyIsFinish;  
	}  
	public String getRealPathFromURI(Uri contentUri) {
		String res = null;
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
		if(cursor.moveToFirst()){;
		int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
		res = cursor.getString(column_index);
		}
		cursor.close();
		return res;
	}
	/**
	 * 安装本地插件服务调用
	 * @param path
	 * @param callback   安装插件的回掉函数
	 * @throws Exception
	 */
	public void install(String path,installCallback callback) throws Exception{
		System.out.println("安装 :"+path);
		BundleContext mcontext=frame.getSystemBundleContext();
		OSGIServiceAgent<BundleControl> agent=new OSGIServiceAgent<BundleControl>(mcontext,BundleControl.class);
		//插件启动级别为1(会自启) 并且不检查插件版本是否相同都安装
		agent.getService().install(mcontext, path,callback, 1,false,false,false);
	}
	public boolean Tosd(String fileName,String path) {
		InputStream is;
		try {
			is = context.getAssets().open(fileName);
			File file = new File(path);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] temp = new byte[1024];
			int i = 0;
			while ((i = is.read(temp)) > 0) {
				fos.write(temp, 0, i);
			}
			fos.close();
			is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	} 
	@Override
	public void onResume() {
		if(MyApplication.isopen){
			//已安装插件列表
			bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
			BundleContext context =frame.getSystemBundleContext();
			for(int i=0;i<context.getBundles().length;i++)
			{
				//获取已安装插件
				bundles.add(context.getBundles()[i]);        	        
			}

			//			BundleContext context =frame.getSystemBundleContext();
			startor(bundles);
			//			MyApplication.isopen = f
		}
		super.onResume();
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
			if(home!=null){
			initdata(home);
			}else{
				Util.ShowToast(context, "error");
			}
			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			Send send = new Send(context);
			home = send.RequestHome();
			return true;
		}
	}

	private void SetPrice(TextView v ,String s){
		v.setText("￥"+s);
	}


	@Override
	public void onRefresh(RefreshableView view) {
		if (Util.detect(context)) {
			new Thread(){
				@Override
				public void run() {
					super.run();
					Send send = new Send(context);
					home = send.RequestHome();
					Message m = handler.obtainMessage();
					if(home!=null){
					m.obj = home;
					m.what=1;
					}else{
						m.what=2;
					}
					handler.sendMessage(m);
				}
			}.start();
		} else {
			handler.postDelayed(new Runnable() {
				@Override
				public void run() {
					Util.ShowToast(context, R.string.net_is_eor);
					refreshableView.finishRefresh();
				}
			}, 500);
		}
	}

	private void initdata(HomeBean home){
		if("200".equals(home.getCode())){
			Util u = new Util(context);
			u.saveObject(home, "home");
			//					Util.ShowToast(context, "success");
			Util.Getbitmap(iv_ad1, home.getAd().get(0));
			Util.Getbitmap(iv_ad2, home.getAd().get(1));
			ruzhuadapter = new HomeRuzhuAdapter(context, home.getCompany());
			gv_ruzhu.setAdapter(ruzhuadapter);
			likeadapter = new HomeLikeAdapter(context, home.getMylike());
			gv_like.setAdapter(likeadapter);
			sc.scrollTo(0, 1);
			for(int i =0;i<home.getRecommend().size();i++){
				String url = home.getRecommend().get(i).getThumb();
				Util.Getbitmap(list_rem.get(i), url);
				if(i>=5){
					String t = home.getRecommend().get(i).getTitle();
					String p = home.getRecommend().get(i).getPrice();
					list_tv_rem_title.get((i-5)).setText(t);
					SetPrice(list_tv_rem_price.get((i-5)), p);
				}
			}
			for(int j=0;j<home.getHot().size();j++){
				String url = home.getHot().get(j).getThumb();
				Util.Getbitmap(list_hot.get(j), url);
				if(j>=3){
					String t = home.getHot().get(j).getTitle();
					String p = home.getHot().get(j).getPrice();
					list_tv_hot_title.get((j-3)).setText(t);
					SetPrice(list_tv_hot_price.get((j-3)), p);
//					Util.ShowToast(context, ""+home.getHot().get(j).getItemid());
				}
			}
			u.saveObject(home, filename);
		}else if("300".equals(home.getCode())){
			intent = new Intent(context,LoginActivity.class);
			startActivity(intent);
		getActivity().finish();
		}else{
			if(home!=null){
				String msg = home.getMsg();
				if(Util.IsNull(msg)){
					Util.ShowToast(context, msg);
				}
			}
		}
	}

}
