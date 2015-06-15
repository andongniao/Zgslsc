package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.adapters.HomeSlidAdapter;
import com.example.educonsult.adapters.MyOrderHomeAdapter;
import com.example.educonsult.adapters.SCStoreAdapter;
import com.example.educonsult.beans.CenterShopBean;
import com.example.educonsult.beans.ListCenterShopBean;
import com.example.educonsult.beans.OrderBean;
import com.example.educonsult.myviews.MyListview;
import com.example.educonsult.myviews.xlistview.XListView;
import com.example.educonsult.myviews.xlistview.XListView.IXListViewListener;
import com.example.educonsult.net.PostHttp;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.Util;
import com.testin.agent.TestinAgent;

public class SCStoreActivity extends BaseActivity implements OnClickListener,IXListViewListener{
	private LinearLayout reaLayout,ll_isno;
	private TextView allway;
	private ListView product_list;
	private MyListview list_way;
	private ListView list_2,lv_l;
	private SCStoreAdapter scStoreAdapter;
	private Context context;
	private ArrayList<String> list;
	private PopupWindow popu;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private Intent intent;
	private HomeSlidAdapter adapter_r;
	private LinearLayout lin;
	public static boolean isrezoom;
	public Myorder myorder;
	private ThreadWithProgressDialog myPDT;
	private int page,ppage;
	private ListCenterShopBean listCenterShopBean;
	private ArrayList<CenterShopBean> centerShopBeans,centerShopBeans2;
	private XListView lv;
	private Handler handler;
	private String authstr;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		topRightTGone();
		setTopLeftTv(R.string.scstore_title);
		setContentXml(R.layout.scstore);
		init();
		
	
		
	}
	void init(){
		TestinAgent.init(this);
		authstr = MyApplication.mp.getUser().getAuthstr();
		page=1;
		ppage=1;
		reaLayout=(LinearLayout)findViewById(R.id.scstore_allway_lin);
		reaLayout.setOnClickListener(SCStoreActivity.this);
		allway=(TextView)findViewById(R.id.scstore_allway);
		ll_isno=(LinearLayout)findViewById(R.id.scstore_isnull);
//		list_money.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		lv=(XListView)findViewById(R.id.scstore_lv);
		lv.setPullRefreshEnable(true);
		lv.setPullLoadEnable(true);
		lv.setXListViewListener(this);
		lv.setEmptyView(ll_isno);
		inflater=LayoutInflater.from(context);
		v_fenlei = inflater.inflate(R.layout.know_slidemenu_view, null);
		lin=(LinearLayout)v_fenlei.findViewById(R.id.know_slid_view_ll_r);
		lin.setVisibility(View.GONE);
		list_2=(ListView)v_fenlei.findViewById(R.id.know_slid_view_lv_r);
		list_2.setVisibility(View.GONE);
		lv_l = (ListView) v_fenlei.findViewById(R.id.know_slid_view_lv_l);
		ArrayList<String>ll = new ArrayList<String>();
//		adapter_r = new HomeSlidAdapter(context, ll,2);
//		lv_l.setAdapter(adapter_r);
		lv_l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				popu.dismiss();
			}
		});
		popu = new PopupWindow(v_fenlei, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setOutsideTouchable(true);
		popu.update();
		
		myPDT = new ThreadWithProgressDialog();
		if(MyApplication.mp.islogin){
			if(Util.detect(context)){
				//			myPDT.Run(context, new RefeshData(bean.getType(),bean.getAuthstr()),msg,false);//不可取消
				myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
		}else{
			Intent i = new Intent(context,LoginActivity.class);
			startActivity(i);
			finish();
		}

		myorder = new Myorder() {

			@Override
			public void delte(int index) {
				list.remove(index);
				scStoreAdapter.SetData(centerShopBeans);
				scStoreAdapter.notifyDataSetChanged();
			}

			@Override
			public void finish() {
				// TODO Auto-generated method stub
				MyApplication.mp.setlogin(false);
				Util.ShowToast(context, R.string.login_out_time);
				intent = new Intent(context,LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish(); 
			}
		};
		
	
		if(centerShopBeans!=null && list.size()>0){
			ll_isno.setVisibility(View.GONE);
			lv.setVisibility(View.VISIBLE);
		}else{
			ll_isno.setVisibility(View.VISIBLE);
			lv.setVisibility(View.GONE);
		}
		handler = new Handler(){
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what==1){
					if(ppage==1){
						centerShopBeans = (ArrayList<CenterShopBean>) msg.obj;
					}else{
						ArrayList<CenterShopBean> ll = (ArrayList<CenterShopBean>) msg.obj;
						centerShopBeans.addAll(ll);
						if(ll.size()==0){
							Util.ShowToast(context, R.string.page_is_final);
							ppage-=1;
						}
					}
					if(scStoreAdapter!=null){
						scStoreAdapter.SetData(centerShopBeans);
						scStoreAdapter.notifyDataSetChanged();
					}else{
						scStoreAdapter=new SCStoreAdapter(context, centerShopBeans,myorder);
						lv.setAdapter(scStoreAdapter);
					}
				}else if(msg.what==2){
					intent = new Intent(context,LoginActivity.class);
					startActivity(intent);
					finish();
				}else{
					String s = (String) msg.obj;
					Util.ShowToast(context, s);
				}
				lv.stopRefresh();
				lv.stopLoadMore();
			}
		};
		
	}

	public class RefeshData implements ThreadWithProgressDialogTask {
		public RefeshData(){
		}

		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub
			PostHttp p=new PostHttp(context);
			if(page==1){
				listCenterShopBean=p.getCenterShop(page,authstr);
			}else{
				listCenterShopBean=p.getCenterShop(1,authstr);
				centerShopBeans=listCenterShopBean.getList();
				for(int i=1;i<page+1;i++){
					listCenterShopBean=p.getCenterShop(page,authstr);
					ArrayList<CenterShopBean> s=listCenterShopBean.getList();
					centerShopBeans.addAll(s);
				}
				
			
			}
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			if(listCenterShopBean!=null){
				String code = listCenterShopBean.getCode();
				String m = listCenterShopBean.getMsg();
				if("200".equals(code)){
					if(page==1){
						
						centerShopBeans=listCenterShopBean.getList();
						scStoreAdapter=new SCStoreAdapter(context, centerShopBeans,myorder);
						lv.setAdapter(scStoreAdapter);
					}else{
						if(scStoreAdapter==null){
							scStoreAdapter=new SCStoreAdapter(context, centerShopBeans,myorder);
							lv.setAdapter(scStoreAdapter);	
						}else{
							scStoreAdapter.SetData(centerShopBeans);
							scStoreAdapter.notifyDataSetChanged();
						}
					}
					//initDate();
				}else if("300".equals(code)){
					MyApplication.mp.setlogin(false);
					Util.ShowToast(context, R.string.login_out_time);
					intent = new Intent(context,LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish(); 
				}else{
					if(Util.IsNull(m)){
						Util.ShowToast(context, m);
					}
				}	
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			return true;

		}

	}
	private void initDate(){}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isrezoom){
			Toast.makeText(context, "重新加载数据", 1).show();
			isrezoom=false;
		}
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.scstore_allway_lin:
			
//			pp_top_fenlei.showAtLocation(ll_all, Gravity.TOP, 0, ll_all.getHeight());
				popu.showAsDropDown(reaLayout);
			
			break;

		default:
			break;
		}
	}
	public interface Myorder{
		void delte(int index);
		void finish();
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		ppage=1;
		getDate();
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		ppage+=1;
		getDate();
	}
	private void getDate(){

		new Thread(){public void run() {
			PostHttp p=new PostHttp(context);
			listCenterShopBean=p.getCenterShop(ppage,authstr);
			Message msg=handler.obtainMessage();
			if(listCenterShopBean!=null){
				if("200".equals(listCenterShopBean.getCode())){
					ArrayList<CenterShopBean> lc=listCenterShopBean.getList();
					msg.what=1;
					msg.obj=lc;
				}else{
					msg.what=2;
					msg.obj=listCenterShopBean.getMsg();
				}
			}else{
				String ss = context.getResources().getString(R.string.net_is_eor);
				msg.what=3;
				msg.obj = ss;
			}
			handler.sendMessage(msg);
		
		};}.start();
	
	}

}
