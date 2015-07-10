package com.xunbo.store.activitys;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.adapters.HomeSlidAdapter;
import com.xunbo.store.adapters.ProductAdapter;
import com.xunbo.store.adapters.SCStoreAdapter;
import com.xunbo.store.beans.CenterShopBean;
import com.xunbo.store.beans.ListSCProductBean;
import com.xunbo.store.beans.SCProductBean;
import com.xunbo.store.myviews.MyListview;
import com.xunbo.store.myviews.xlistview.XListView;
import com.xunbo.store.myviews.xlistview.XListView.IXListViewListener;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.tools.Util;

public class SCProductActivity extends BaseActivity implements OnClickListener,IXListViewListener{
	private RelativeLayout reaLayout;
	private TextView allquery;
	private XListView product_list;
	private MyListview list_way;
	private ListView list_2,lv_l;
	private ProductAdapter productAdapter;
	private Context context;
	private PopupWindow popu;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private Intent intent;
	private HomeSlidAdapter adapter_r;
	private LinearLayout lin,ll_isno;
	public static boolean isrezoom;
	public Myorder myorder;
	private ThreadWithProgressDialog myPDT;
	private ListSCProductBean listProductBean;
	private String authstr;
	private ArrayList<SCProductBean> list;
	private int page,ppage,addtype;
	private boolean isshow;
	private Handler handler;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		topRightTGone();
		setTitleTxt(R.string.scproduct_title);
		//setTopLeftTv(R.string.scproduct_title);
		setContentXml(R.layout.scproduct);
		init();
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
		
	}
	void init(){
		TestinAgent.init(this);
		ppage=1;
		myPDT=new ThreadWithProgressDialog();
		isshow=false;
		authstr=MyApplication.mp.getUser().getAuthstr();
		product_list=(XListView)findViewById(R.id.scproduct_list);
		ll_isno=(LinearLayout)findViewById(R.id.scproduct_isnull);
		product_list.setPullRefreshEnable(true);
		product_list.setPullLoadEnable(true);
		product_list.setXListViewListener(this);
		product_list.setEmptyView(ll_isno);
		//allquery=(TextView)findViewById(R.id.mybusinesspartners_allquery);
		myorder = new Myorder() {

			@Override
			public void delte(int index) {
				list.remove(index);
				productAdapter.SetData(list);
				productAdapter.notifyDataSetChanged();
			}

			@Override
			public void finish() {
				// TODO Auto-generated method stub
				MyApplication.mp.setlogin(false);
				Util.ShowToast(context, R.string.login_out_time);
				Intent i= new Intent(context,LoginActivity.class);
				startActivity(i);
				finish();
			}
		};
		
//		list_money.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
		/*inflater=LayoutInflater.from(context);
		v_fenlei = inflater.inflate(R.layout.know_slidemenu_view, null);
		lin=(LinearLayout)v_fenlei.findViewById(R.id.know_slid_view_ll_r);
		lin.setVisibility(View.GONE);
		list_2=(ListView)v_fenlei.findViewById(R.id.know_slid_view_lv_r);
		list_2.setVisibility(View.GONE);
		lv_l = (ListView) v_fenlei.findViewById(R.id.know_slid_view_lv_l);
		ArrayList<String>ll = new ArrayList<String>();
		adapter_r = new HomeSlidAdapter(context, ll,2);
		lv_l.setAdapter(adapter_r);
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
		popu.update();*/
		if(list!=null && list.size()>0){
			ll_isno.setVisibility(View.GONE);
			product_list.setVisibility(View.VISIBLE);
		}else{
			ll_isno.setVisibility(View.VISIBLE);
			product_list.setVisibility(View.GONE);
		}
		handler = new Handler(){
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what==1){
					if(ppage==1){
						list = (ArrayList<SCProductBean>) msg.obj;
					}else{
						ArrayList<SCProductBean> ll = (ArrayList<SCProductBean>) msg.obj;
						list.addAll(ll);
						if(ll.size()==0){
							Util.ShowToast(context, R.string.page_is_final);
							ppage-=1;
						}
					}
					if(productAdapter!=null){
						productAdapter.SetData(list);
						productAdapter.notifyDataSetChanged();
					}else{
						productAdapter=new ProductAdapter(context, list,myorder,isshow);
						product_list.setAdapter(productAdapter);
					}
				}else if(msg.what==2){
					intent = new Intent(context,LoginActivity.class);
					startActivity(intent);
					finish();
				}else{
					String s = (String) msg.obj;
					Util.ShowToast(context, s);
				}
				isshow=false;
				productAdapter.SetIsShow(isshow);
				product_list.stopRefresh();
				product_list.stopLoadMore();
				if(addtype==1){
					SimpleDateFormat sDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd   hh:mm:ss");
					String date = sDateFormat.format(new java.util.Date());
					product_list.setRefreshTime(date);
				}
			}
		};
		
	}
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
		case R.id.mybusinesspartners_rela:
			
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
	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			finish();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			if(listProductBean!=null){
				if("200".equals(listProductBean.getCode())){
					list=listProductBean.getList();
					productAdapter=new ProductAdapter(context, list,myorder,isshow);
					product_list.setAdapter(productAdapter);
				}else if("300".equals(listProductBean.getCode())){
					MyApplication.mp.setlogin(false);
					Util.ShowToast(context, R.string.login_out_time);
					Intent i= new Intent(context,LoginActivity.class);
					startActivity(i);
					finish();
				}else{
					Util.ShowToast(context, listProductBean.getMsg());
				}
			
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			PostHttp p=new PostHttp(context);
//			ListProductBean getCenterProduct(int page,String authstr)
			listProductBean=p.getCenterProduct(1, authstr);
			return true;
		}
	}
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		ppage=1;
		addtype=1;
		isshow=true;
		productAdapter.SetIsShow(isshow);
		getDate();
		
	}
	@Override
	public void onLoadMore() {
		// TODO Auto-generated method stub
		ppage+=1;
		addtype=2;
		isshow=true;
		productAdapter.SetIsShow(isshow);
		getDate();
		
	}
	private void getDate(){

		new Thread(){public void run() {
			PostHttp p=new PostHttp(context);
			listProductBean=p.getCenterProduct(ppage, authstr);
			Message msg=handler.obtainMessage();
			if(listProductBean!=null){
				if("200".equals(listProductBean.getCode())){
					ArrayList<SCProductBean> lc=listProductBean.getList();
					msg.what=1;
					msg.obj=lc;
				}else{
					msg.what=2;
					msg.obj=listProductBean.getMsg();
				}
			}else{
				String ss = context.getResources().getString(R.string.net_is_eor);
				msg.what=2;
				msg.obj = ss;
			}
			handler.sendMessage(msg);
		
		};}.start();
	
	}

}
