package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.adapters.StoreShopAdapter;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.beans.ListProductBean;
import com.xunbo.store.beans.ListShopHomeBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.myviews.MyGridView;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.tools.Util;

public class StoreShopBaseActivity extends BaseActivity implements OnClickListener{
	private ImageView iv_hean,iv_shoucang,iv_home,iv_all,iv_new,iv_vip,iv_renzheng;
	private TextView title;
	private View v_home,v_all,v_new;
	private MyGridView gv_home,gv_all,gv_new;
	private Context context;
	private LinearLayout ll_add;
	private int showtype,page,type,t;
	private StoreShopAdapter adapter;
	private ArrayList<ProductBean> list_home,list_all,list_new;
	private TextView tv_more;
	private ThreadWithProgressDialog myPDT;
	private ScrollView sc;
	private ListShopHomeBean bean_home;
	private ListProductBean bean_all,bean_new;
	private String authstr,storeid,storename,url,id;
	private BaseBean bean_base;



	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		setTitleTxt(R.string.store_title);
		setContentXml(R.layout.storeshop_base_layout);
		init();
		addlistener();
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}

	private void addlistener() {
		// TODO Auto-generated method stub

	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		showtype = 1;
		page = 1;
		storeid = getIntent().getStringExtra("storeid");
		storename = getIntent().getStringExtra("storename");
		if(Util.IsNull(storeid)){
			id = storeid;
			type = 2;
		}else if(Util.IsNull(storename)){
			id = storename;
			type = 2;
		}
		url = getIntent().getStringExtra("url");
		authstr = MyApplication.mp.getUser().getAuthstr();
		//		list_home = new ArrayList<ProductBean>();
		//		list_all = new ArrayList<ProductBean>();
		//		list_new = new ArrayList<ProductBean>();
		myPDT =new  ThreadWithProgressDialog();
		iv_hean = (ImageView) findViewById(R.id.storeshop_iv_head);
		iv_hean.setOnClickListener(this);
		iv_shoucang = (ImageView) findViewById(R.id.storeshop_iv_isshow);
		iv_shoucang.setOnClickListener(this);
		iv_home = (ImageView) findViewById(R.id.storeshop_iv_home);
		iv_home.setOnClickListener(this);
		iv_all = (ImageView) findViewById(R.id.storeshop_iv_all);	
		iv_all.setOnClickListener(this);
		iv_new = (ImageView) findViewById(R.id.storeshop_iv_new);
		iv_new.setOnClickListener(this);
		sc = (ScrollView) findViewById(R.id.storeshop_sc);
		iv_vip = (ImageView) findViewById(R.id.storeshop_iv_vip);
		iv_renzheng = (ImageView) findViewById(R.id.storeshop_iv_renzheng);
		title = (TextView) findViewById(R.id.storeshop_tv_title);

		ll_add = (LinearLayout) findViewById(R.id.store_base_ll_addview);


		v_home = LayoutInflater.from(context).inflate(R.layout.storeshop_home_layout, null);
		v_all = LayoutInflater.from(context).inflate(R.layout.storeshop_all_layout, null);
		v_new = LayoutInflater.from(context).inflate(R.layout.storeshop_new_layout, null);

		ll_add.addView(v_home);
		ll_add.addView(v_all);
		ll_add.addView(v_new);

		gv_home = (MyGridView) v_home.findViewById(R.id.storeshop_home_gv);
		gv_all = (MyGridView) v_all.findViewById(R.id.storeshop_all_gv);
		gv_new = (MyGridView) v_new.findViewById(R.id.storeshop_new_gv);
		v_all.findViewById(R.id.storeshop_all_tv_more).setOnClickListener(this);
		gv_home.setFocusable(false);
		gv_all.setFocusable(false);
		gv_new.setFocusable(false);
		//		adapter = new StoreShopAdapter(context, list_home);
		//		gv_home.setAdapter(adapter);
		//		gv_all.setAdapter(adapter);
		//		gv_new.setAdapter(adapter);


		v_home.findViewById(R.id.storeshop_home_tv_all).setOnClickListener(this);





		show(showtype);

	}

	private void show(int showtype) {
		switch (showtype) {
		case 1:
			iv_home.setBackgroundResource(R.drawable.storeshop_home_bg_r);
			iv_all.setBackgroundResource(R.drawable.storeshop_all_bg_b);
			iv_new.setBackgroundResource(R.drawable.storeshop_new_bg_b);
			v_home.setVisibility(View.VISIBLE);
			v_all.setVisibility(View.GONE);
			v_new.setVisibility(View.GONE);
			break;
		case 2:
			iv_home.setBackgroundResource(R.drawable.storeshop_home_bg_b);
			iv_all.setBackgroundResource(R.drawable.storeshop_all_bg_r);
			iv_new.setBackgroundResource(R.drawable.storeshop_new_bg_b);
			v_home.setVisibility(View.GONE);
			v_all.setVisibility(View.VISIBLE);
			v_new.setVisibility(View.GONE);
			break;
		case 3:
			iv_home.setBackgroundResource(R.drawable.storeshop_home_bg_b);
			iv_all.setBackgroundResource(R.drawable.storeshop_all_bg_b);
			iv_new.setBackgroundResource(R.drawable.storeshop_new_bg_r);
			v_home.setVisibility(View.GONE);
			v_all.setVisibility(View.GONE);
			v_new.setVisibility(View.VISIBLE);
			break;



		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.storeshop_home_tv_all:
			showtype = 2;
			if(bean_all==null && list_all==null){
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				show(showtype);
			}
			sc.scrollTo(0, 10);
			break;
		case R.id.storeshop_iv_home:
			showtype = 1;
			if(bean_home==null && list_home==null){
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				show(showtype);
			}

			break;
		case R.id.storeshop_iv_all:
			showtype = 2;
			if(bean_all==null && list_all==null){
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				show(showtype);
			}

			break;
		case R.id.storeshop_iv_new:
			showtype = 3;
			if(bean_new==null && list_new==null){
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				show(showtype);
			}

			break;
		case R.id.storeshop_all_tv_more:
			showtype = 2;
			page+=1;
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}

			break;
		case R.id.storeshop_iv_isshow:
			showtype = 4;
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			break;

		}

	}

	public class RefeshData implements ThreadWithProgressDialogTask {
		public RefeshData() {

		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			return false; 
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(showtype==1){
				if(bean_home!=null){
					if("200".equals(bean_home.getCode())){
						show(showtype);
						list_home = bean_home.getRecommend();
						setData(gv_home,list_home);
						title.setText(bean_home.getShopInfoBean().getCompany());
						Util.Getbitmap(iv_hean, bean_home.getShopInfoBean().getThumb());
						if("1".equals(bean_home.getShopInfoBean().getVip())){
							iv_vip.setVisibility(View.VISIBLE);
						}
						if("1".equals(bean_home.getShopInfoBean().getValidated())){
							iv_renzheng.setVisibility(View.VISIBLE);
						}
						if(bean_home.getShopInfoBean().getCollect()==1){
							t=1;
							iv_shoucang.setBackgroundResource(R.drawable.shoucang_yeah);//未收藏（点击收藏）
						}else{
							t=2;
							iv_shoucang.setBackgroundResource(R.drawable.shoucang_nope);//已收藏（点击取消）
						}
					}else if("300".equals(bean_home.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent intent = new Intent(context,LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish(); 
					}else{
						Util.ShowToast(context, bean_home.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else if(showtype==2){
				if(bean_all!=null){
					if("200".equals(bean_all.getCode())){
						ArrayList<ProductBean> l = new ArrayList<ProductBean>();;
						show(showtype);
						if(page==1){
							list_all = new ArrayList<ProductBean>();
							list_all = bean_all.getList();
						}else{
							l = bean_all.getList();
							list_all.addAll(l);
							if(l.size()==0){
								Util.ShowToast(context, R.string.page_is_final);
							}
						}
							setData(gv_all, list_all);
						//TODO
					}else if("300".equals(bean_all.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent intent = new Intent(context,LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish(); 
					}else{
						page-=1;
						Util.ShowToast(context, bean_all.getMsg());
					}
				}else{
					page-=1;
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else if(showtype==3){
				if(bean_new!=null){
					if("200".equals(bean_new.getCode())){
						show(showtype);
						list_new = bean_new.getList();
						setData(gv_new, list_new);
						//TODO
					}else if("300".equals(bean_new.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent intent = new Intent(context,LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish(); 
					}else{
						Util.ShowToast(context, bean_new.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else if(showtype==4){
				if(bean_base!=null){
					if("200".equals(bean_base.getCode())){
						MyCenterActivity.ischanged = true;
						if(t==1){
							t = 2;
							iv_shoucang.setBackgroundResource(R.drawable.shoucang_nope);
						}else{
							t = 1;
							iv_shoucang.setBackgroundResource(R.drawable.shoucang_yeah);
						}
						//TODO
					}else if("300".equals(bean_base.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent intent = new Intent(context,LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish(); 
					}else{
						Util.ShowToast(context, bean_base.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}


			return true;
		}



		private void setData(MyGridView gv,
				ArrayList<ProductBean> list) {

			//			if(adapter!=null){
			//				adapter.setData(list);
			//				adapter.notifyDataSetChanged();
			//			}else{
			if(showtype==2 && page>1){
				adapter.setData(list);
				adapter.notifyDataSetChanged();
			}else{
				adapter = new StoreShopAdapter(context, list);
				gv.setAdapter(adapter);
			}
			//			}
		}

		@Override
		public boolean TaskMain() {
			PostHttp p = new PostHttp(context);
			if(showtype==1){
				bean_home = p.getShopHomeData(id, type,authstr);
			}else if(showtype==2){
				bean_all = p.getShopHomeAll(id, showtype, page,authstr);
			}else if(showtype==3){
				bean_new = p.getShopHomeNew(id, showtype,authstr);
			}else if(showtype==4){
				int is = Integer.parseInt(bean_home.getShopInfoBean().getUserid());
				bean_base = p.Shoucang(t, 2, is, authstr);
			}
			return true;
		}
	}


}
