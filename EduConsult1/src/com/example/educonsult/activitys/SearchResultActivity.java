package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.example.educonsult.adapters.SearchResultAdapter;
import com.example.educonsult.beans.ListProductBean;
import com.example.educonsult.beans.ProductBean;
import com.example.educonsult.net.PostHttp;
import com.example.educonsult.tools.Util;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;

public class SearchResultActivity extends Activity implements OnClickListener{
	protected int activityCloseEnterAnimation;
	protected int activityCloseExitAnimation;
	private ImageView iv_back,iv_num,iv_price,iv_renqi;
	private EditText et;
	private LinearLayout ll_zonghe,ll_xiaoliang,ll_price,ll_renqi,ll_isyes,ll_isnot;
	private PullToRefreshGridView gv;
	private Context context;
	private ArrayList<ProductBean> list;
	private SearchResultAdapter adapter;
	private ArrayList<View> list_view;
	private boolean num,price,renqi;
	private Intent intent;
	private ThreadWithProgressDialog myPDT;
	private ListProductBean listProductBean;
	private int type;
	private int order;
	private int page,addtype;
	private String text;
	private boolean islist,isfinish,isnext;
	private TextView tv_guanjian;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});
		int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);      
		activityStyle.recycle();
		activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
		activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
		activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
		activityStyle.recycle();
		//		topRightTGone();
		//		setTopLeftTv(R.string.search_title);
		setContentView(R.layout.search_result_layout);
		init();
		addlisteners();
	}

	private void addlisteners() {

		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if(isnext){
					
				Intent intent = new Intent(context,ProductDetaileActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				//intent.putExtra("productdetaile",list.get(arg2) );
				Bundle b=new Bundle();
				b.putSerializable("product", list.get(arg2));
				intent.putExtra("productbundle", b);
				startActivity(intent);
				}else{
					Util.ShowToast(context, "正在加载数据，请稍后。。。");
				}
			}
		});
		gv.setOnRefreshListener(new OnRefreshListener2<GridView>()
				{

			@Override
			public void onPullDownToRefresh(
					PullToRefreshBase<GridView> refreshView)
			{
				Log.e("TAG", "onPullDownToRefresh"); // Do work to
				String label = DateUtils.formatDateTime(
						getApplicationContext(),
						System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME
						| DateUtils.FORMAT_SHOW_DATE
						| DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy()
				.setLastUpdatedLabel(label);
				page = 1;
				isnext=false;
				new GetDataTask().execute();
			}

			@Override
			public void onPullUpToRefresh(
					PullToRefreshBase<GridView> refreshView)
			{
				addtype=1;
				page +=1;
				isnext=false;
				new GetDataTask().execute();
			}
				});
	}

	private void init() {
		context = this;
		intent=getIntent();
		order = 0;
		list = new ArrayList<ProductBean>();
		isfinish=true;
		isnext=true;
		/*intent.putExtra("searchtype", type);
		intent.putExtra("searchorder", order);
		intent.putExtra("searchpage", page);
		intent.putExtra("searchtext", text);*/
		//list =(ArrayList<ProductBean>)intent.getSerializableExtra("search");
		type=Integer.parseInt(intent.getStringExtra("searchtype"));
		order=Integer.parseInt(intent.getStringExtra("searchorder"));
		page=1;//=Integer.parseInt(intent.getStringExtra("searchpage"));
		text=intent.getStringExtra("searchtext");
		myPDT=new ThreadWithProgressDialog();
		tv_guanjian = (TextView) findViewById(R.id.search_result_guanjiazi);
		list_view = new ArrayList<View>();
		iv_back = (ImageView) findViewById(R.id.search_result_iv_back);
		iv_back.setOnClickListener(this);
		et = (EditText) findViewById(R.id.search_result_et);
		et.setOnClickListener(this);
		et.setText(text);
		ll_zonghe = (LinearLayout) findViewById(R.id.search_result_ll_zonghe);
		ll_zonghe.setOnClickListener(this);
		ll_xiaoliang = (LinearLayout) findViewById(R.id.search_result_ll_xiaoliang);
		ll_xiaoliang.setOnClickListener(this);
		ll_price = (LinearLayout) findViewById(R.id.search_result_ll_price);
		ll_price.setOnClickListener(this);
		ll_renqi = (LinearLayout) findViewById(R.id.search_result_ll_renqi);
		ll_renqi.setOnClickListener(this);
		iv_num = (ImageView) findViewById(R.id.search_result_iv_xiaoliang);
		iv_price = (ImageView) findViewById(R.id.search_result_iv_price);
		iv_renqi = (ImageView) findViewById(R.id.search_result_iv_renqi);
		list_view.add(iv_num);
		list_view.add(iv_price);
		list_view.add(iv_renqi);
		ll_isyes=(LinearLayout)findViewById(R.id.search_result_isyes);
		ll_isnot=(LinearLayout)findViewById(R.id.search_result_isnoll);
		ll_isyes.setVisibility(View.GONE);
		gv = (PullToRefreshGridView) findViewById(R.id.search_result_gv);
		initIndicator();
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(type,order,page,text),R.string.loding);//可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}
	private void initIndicator()
	{
		ILoadingLayout startLabels = gv
				.getLoadingLayoutProxy(true, false);
		startLabels.setPullLabel("下拉刷新");// 刚下拉时，显示的提示
		startLabels.setRefreshingLabel("正在刷新...");// 刷新时
		startLabels.setReleaseLabel("松开刷新...");// 下来达到一定距离时，显示的提示

		ILoadingLayout endLabels = gv.getLoadingLayoutProxy(
				false, true);
		endLabels.setPullLabel("获取更多");// 刚下拉时，显示的提示
		endLabels.setRefreshingLabel("正在刷新...");// 刷新时
		endLabels.setReleaseLabel("松开刷新...");// 下来达到一定距离时，显示的提示
	}


	public class RefeshData implements ThreadWithProgressDialogTask {
		int type,order,page;
		String text;
		public RefeshData(int type,int order,int page,String text) {
			this.type=type;
			this.order=order;
			this.page=page;
			this.text=text;
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			//			Toast.makeText(context, "cancle", 1000).show();
			if(isfinish){
				
				finish();
			}
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			isfinish=false;
			if(listProductBean!=null){
				if("200".equals(listProductBean.getCode())){
					//TODO	
					initDate();
				}else if("300".equals(listProductBean.getCode())){
					//TODO	
					Util.ShowToast(context,R.string.login_out_time);
					intent=new Intent(context, LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
				else{
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
			addtype = 2;
			if(page==1){
				listProductBean=p.SeanchText(type, order, page, text);
				list = new ArrayList<ProductBean>();
				list = 	listProductBean.getList();
			}else{
				list = new ArrayList<ProductBean>();
				for(int in=1;in<page+1;in++){
					listProductBean=p.SeanchText(type, order, in, text);
					list.addAll(listProductBean.getList());
				}
			}

			return true;
		}
	}
	void initDate(){
		if(list==null||list.size()==0){
			tv_guanjian.setText(text);
			ll_isnot.setVisibility(View.VISIBLE);
			ll_isyes.setVisibility(View.GONE);
			islist=false;
		}else{
			ll_isnot.setVisibility(View.GONE);
			ll_isyes.setVisibility(View.VISIBLE);
			//			list=listProductBean.getList();
			if(adapter==null){
				adapter = new SearchResultAdapter(context, list);
				gv.setAdapter(adapter);
			}else{
				if(addtype==1){
					adapter.SetData(list);
					adapter.notifyDataSetChanged();
				}else{
					adapter = new SearchResultAdapter(context, list);
					gv.setAdapter(adapter);
				}
			}
			islist=true;
		}
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_result_iv_back:
			finish();
			break;
		case R.id.search_result_et:
			
				
			Intent intent = new Intent(this,SearchHomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("type", 1);
			intent.putExtra("t", 1);
			startActivity(intent);
			
			break;
		case R.id.search_result_ll_zonghe:
			order = 0;
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(type,order,page,text),R.string.loding);//可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}

			break;
		case R.id.search_result_ll_xiaoliang:
			Change(0);
			int i=0;
			if(num){
				order=2;
			}else{
				order=1;
			}
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(type,order,page,text),R.string.loding);//可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			break;
		case R.id.search_result_ll_price:
			Change(1);
			if(price){
				order=4;
			}else{
				order=3;
			}
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(type,order,page,text),R.string.loding);//可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			break;
		case R.id.search_result_ll_renqi:
			Change(2);
			if(renqi){
				order=6;
			}else{
				order=5;
			}
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(type,order,page,text),R.string.loding);//可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			break;


		}
	}

	private void Change(int i){
		switch (i) {
		case 0:
			if(num){
				num = false;
				list_view.get(0).setBackgroundResource(R.drawable.jiantou_down);
			}else{
				num = true;
				list_view.get(0).setBackgroundResource(R.drawable.jiantou_up);
			}
			break;
		case 1:
			if(price){
				price = false;
				list_view.get(1).setBackgroundResource(R.drawable.jiantou_down);
			}else{
				price = true;
				list_view.get(1).setBackgroundResource(R.drawable.jiantou_up);
			}
			break;
		case 2:
			if(renqi){
				renqi = false;
				list_view.get(2).setBackgroundResource(R.drawable.jiantou_down);
			}else{
				renqi = true;
				list_view.get(2).setBackgroundResource(R.drawable.jiantou_up);
			}
			break;
		}
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
	}

	private class GetDataTask extends AsyncTask<Void, Void, Void>
	{

		@Override
		protected Void doInBackground(Void... params)
		{
			
			PostHttp p=new PostHttp(context);
			listProductBean=p.SeanchText(type, order, page, text);

			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{

			gv.onRefreshComplete();
			if(listProductBean!=null){
				if("200".equals(listProductBean.getCode())){
					//TODO	
					ArrayList<ProductBean> l = new ArrayList<ProductBean>();
					if(page==1){
						list = 	listProductBean.getList();
						initDate();
					}else{
						l = listProductBean.getList();
						list.addAll(l);
						if(l.size()>0){
							initDate();
						}else{
							Util.ShowToast(context, R.string.page_is_final);
						}
					}
				}else if("300".equals(listProductBean.getCode())){
					//TODO	
					Util.ShowToast(context,R.string.login_out_time);
					intent=new Intent(context, LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
				}
				else{
					if(page>1){
						page-=1;
					}
					Util.ShowToast(context, listProductBean.getMsg());
				}
			}else{
				if(page>1){
					page-=1;
				}
				Util.ShowToast(context, R.string.net_is_eor);
			}
			isnext=true;

		}
	}

}
