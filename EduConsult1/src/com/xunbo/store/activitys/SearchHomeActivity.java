package com.xunbo.store.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
import com.testin.agent.TestinAgent;
import com.umeng.analytics.MobclickAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.activitys.SearchResultActivity.RefeshData;
import com.xunbo.store.adapters.HomeGridAdapter;
import com.xunbo.store.adapters.SearchAdapter;
import com.xunbo.store.adapters.SearchResultAdapter;
import com.xunbo.store.adapters.TextItemCenterListAdapter;
import com.xunbo.store.beans.ListProductBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.tools.UITools;
import com.xunbo.store.tools.Util;

public class SearchHomeActivity extends BaseActivity implements OnClickListener{
	private long exitTime = 0;
	private Context context;
	private Editor er;
	private ListView lv,lv_l;;
	private TextView sp;
	private EditText et;
	private Button btn_search,btn_clean,btn_top_clean;
	private TextView tv;
	private LinearLayout  ll_zonghe,ll_xiaoliang,ll_price,ll_renqi,isnull,isyes,isresult;
	private ArrayList<String> l,ll;
	private ArrayList<String> data_list;
	private ArrayAdapter<String> arr_adapter;
	private SearchAdapter adapter;
	private boolean ishave,isnext;
	private int t=0;
	private PopupWindow popu;
	private LayoutInflater inflater;
	private View v_fenlei;
	private SearchAdapter adapter_r;
	private static TextItemCenterListAdapter textItemCenterListAdapter;
    private static int postion;
    private ThreadWithProgressDialog myPDT;
    private ListProductBean listProductBean;
    private ArrayList<ProductBean> list;
    private Intent intent;
    private PullToRefreshGridView gv;
    private int type;
	private int order;
	private int page,addtype;
	private String text;
	private SearchResultAdapter searchResultAdapter;
	private boolean num,price,renqi;
	private ArrayList<View> list_view;
	private ImageView iv_back,iv_num,iv_price,iv_renqi;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone(); 
		
//		int type = getIntent().getIntExtra("type", -1);
//		if(type==-1){
//			goneTopLeft();
//		}
		setContentXml(R.layout.search_home_layout);
		setTitleTxt(R.string.search_title);
		init();
		addlistener();
	}

	private void addlistener() {
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
//				Intent intent = new Intent(context,SearchResultActivity.class); 
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(intent);
				String tp = MyApplication.sp.getString("s_fenlei"+arg2, "");
				/*if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(Integer.parseInt(tp),0,3,l.get(arg2)),R.string.loding);//可取消
				}*/
				int i_tp=0;
				if(!Util.IsNull(tp)){
					i_tp=0;
				}else{
					i_tp=Integer.parseInt(tp);
				}
				et.setText(l.get(arg2));
				initDate(i_tp,0,page,l.get(arg2));
			}
		});
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
		TestinAgent.init(this);
		context = this;
		//t = getIntent().getIntExtra("t", -1);
		page=1;
		order=0;
		type=0;
		myPDT=new ThreadWithProgressDialog();
		er = MyApplication.sp.edit();
		sp = (TextView) findViewById(R.id.search_home_sp);
		sp.setOnClickListener(this);
		et = (EditText) findViewById(R.id.search_home_et_inpu);
		et.setTextColor(getResources().getColor(R.color.black));
		lv = (ListView) findViewById(R.id.search_home_lv);
		btn_search = (Button) findViewById(R.id.search_home_btn_seatch);
		btn_search.setOnClickListener(this);
		btn_clean = (Button) findViewById(R.id.search_home_btn_clean);
//		btn_clean.setTextColor(getResources().getColor(R.color.black));
//		btn_clean.setBackgroundResource(R.color.white);
		btn_top_clean=(Button)findViewById(R.id.search_home_button_clear);
		btn_top_clean.setOnClickListener(this);
		btn_clean.setOnClickListener(this);
		tv = (TextView) findViewById(R.id.search_home_tv_history);
		isnull = (LinearLayout) findViewById(R.id.search_home_ll_list_is_null);
		isyes=(LinearLayout) findViewById(R.id.search_home_ll_isyes);
		isresult=(LinearLayout)findViewById(R.id.search_home_ll_reasult);
		
		ll_zonghe = (LinearLayout) findViewById(R.id.search_home_ll_zonghe);
		ll_zonghe.setOnClickListener(this);
		ll_xiaoliang = (LinearLayout) findViewById(R.id.search_home_ll_xiaoliang);
		ll_xiaoliang.setOnClickListener(this);
		ll_price = (LinearLayout) findViewById(R.id.search_home_ll_price);
		ll_price.setOnClickListener(this);
		ll_renqi = (LinearLayout) findViewById(R.id.search_home_ll_renqi);
		ll_renqi.setOnClickListener(this);
		
		list_view = new ArrayList<View>();
		iv_num = (ImageView) findViewById(R.id.search_home_iv_xiaoliang);
		iv_price = (ImageView) findViewById(R.id.search_home_iv_price);
		iv_renqi = (ImageView) findViewById(R.id.search_home_iv_renqi);
		list_view.add(iv_num);
		list_view.add(iv_price);
		list_view.add(iv_renqi);
		gv = (PullToRefreshGridView) findViewById(R.id.search_home_gv);
		initIndicator();
		
		lv.setEmptyView(isnull);
		
		int s = MyApplication.sp.getInt("size", 0);
		l = new ArrayList<String>();
		for(int i = 0;i<s;i++){
			String v = MyApplication.sp.getString("search"+i, "");
			if(v!=null && !"".equals(v)){
				l.add(v);
			}
		}
		if(l!=null && l.size()>0){
			adapter = new SearchAdapter(context, l);//new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, l);
			tv.setVisibility(View.VISIBLE);
			btn_clean.setVisibility(View.VISIBLE);
			lv.setAdapter(adapter);
			//			isnull.setVisibility(visibility)
//			btn_search.setBackgroundResource(R.drawable.search_lv_notnull_btn_bg);

		}else{
//			btn_search.setBackgroundResource(R.drawable.search_lv_isnull_btn_bg);
			tv.setVisibility(View.GONE);
			btn_clean.setVisibility(View.GONE);
		}

		data_list = new ArrayList<String>();
		data_list.add("产品");
		data_list.add("产地");
		data_list.add("分类");

		/*//适配器
		arr_adapter= new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
		//设置样式
		arr_adapter.setDropDownViewResource(R.layout.drop_down_item);//(android.R.layout.simple_spinner_dropdown_item);
		//加载适配器
		sp.setAdapter(arr_adapter);*/
		ll = new ArrayList<String>();
		ll.add("产  品");
		ll.add("分  类");
		ll.add("产  地");
		sp.setText(ll.get(0));


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

	private void setPopuwindowCenter(Context contexts,ArrayList<String> list,TextView lin){
		inflater=LayoutInflater.from(contexts);
		v_fenlei = inflater.inflate(R.layout.moneycar_list, null);
		lv_l = (ListView) v_fenlei.findViewById(R.id.moneycar_list_list);
		textItemCenterListAdapter = new TextItemCenterListAdapter(context, list);
		lv_l.setAdapter(textItemCenterListAdapter);
	
		lv_l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				sp.setText(ll.get(arg2));
				type=arg2;
				/*if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(arg2,0,3,""),R.string.loding);//可取消
				}*/
				popu.dismiss();
			}
		});
		
		popu = new PopupWindow(v_fenlei, UITools.dip2px(context, 90f) ,LayoutParams.WRAP_CONTENT  );
		popu.setFocusable(true);
		popu.setBackgroundDrawable(getResources().getDrawable(R.drawable.search_kuang));
		popu.setOutsideTouchable(true);
		popu.showAsDropDown(lin);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.search_home_sp:
			//int num=Util.setPopuwindowCenter(context,ll, sp);
			setPopuwindowCenter(context, ll, sp);
			//popu.showAsDropDown(sp);
			break;
		case R.id.search_home_button_clear:
			et.setText("");
			break;
		case R.id.search_home_btn_clean:
			for(int i = 0;i<l.size();i++){
				er.putString("search"+i,"");
				er.putString("s_fenlei"+i,"");
				er.commit();
			}
			l.clear();
			l = new ArrayList<String>();
			er.putInt("size", 0);
			er.commit();
			adapter.SetData(l);
			adapter.notifyDataSetChanged();
			isnull.setVisibility(View.VISIBLE);
			btn_clean.setVisibility(View.GONE);
			tv.setVisibility(View.GONE);
			btn_search.setBackgroundResource(R.drawable.search_lv_isnull_btn_bg);
			break;
		case R.id.search_home_btn_seatch:
			String s = et.getText().toString().trim();
			if(l!=null){
				for(int i = 0;i<l.size();i++){
					if(l.size()>0){
						if(s!=null && !"".equals(s)){
							if(s.equals(l.get(i))){
								ishave = true;
								break;
							}
						}
					}else{
						if(s!=null && !"".equals(s)){
							er.putString("search"+0,et.getText().toString().trim());
							er.putString("s_fenlei"+(l.size()),t+"");
							er.putInt("size", 1);
							er.commit();
							l.add(s);
						}
					}
				}
				if(Util.IsNull(s)){
					if(!ishave){
						
					er.putString("search"+(l.size()),et.getText().toString().trim());
					er.putString("s_fenlei"+(l.size()),t+"");
					l.add(s);
					er.putInt("size", l.size());
					er.commit();
					ishave = false;
					//et.setText("");
					Log.i("tttttttttttttttttttttttt", t+","+s);
					}else{
						ishave = false;
//						Toast.makeText(SearchHomeActivity.this, "关键字已经搜索过", 500).show();
					}
					initDate(type,0,page,s);
					
					/*if(Util.detect(context)){
						myPDT.Run(context, new RefeshData(t,0,3,s),R.string.loding);//可取消
					}*/
				}else{
					Toast.makeText(SearchHomeActivity.this, "关键字不能为空", 500).show();
				}
			}
			if(l.size()>0){
				if(adapter!=null){
					adapter.SetData(l);
					adapter.notifyDataSetChanged();
				}else{
					adapter = new SearchAdapter(context, l);
					lv.setAdapter(adapter);
				}
//				btn_search.setBackgroundResource(R.drawable.search_lv_notnull_btn_bg);
				btn_clean.setVisibility(View.VISIBLE);
				tv.setVisibility(View.VISIBLE);
				isnull.setVisibility(View.GONE);
				
			}
			price=false;
			renqi=false;
			num=false;
			list_view.get(0).setBackgroundResource(R.drawable.jiantou_down);
			list_view.get(1).setBackgroundResource(R.drawable.jiantou_down);
			list_view.get(2).setBackgroundResource(R.drawable.jiantou_down);
			break;
			case R.id.search_home_ll_zonghe:
				order = 0;
				text=et.getText().toString();
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData((type+1),order,page,text),R.string.loding);//可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
				price=false;
				renqi=false;
				num=false;
				list_view.get(0).setBackgroundResource(R.drawable.jiantou_down);
				list_view.get(1).setBackgroundResource(R.drawable.jiantou_down);
				list_view.get(2).setBackgroundResource(R.drawable.jiantou_down);
				

				break;
			case R.id.search_home_ll_xiaoliang:
				text=et.getText().toString();
				Change(0);
				int i=0;
				if(num){
					order=2;
				}else{
					order=1;
				}
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData((type+1),order,page,text),R.string.loding);//可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
				break;
			case R.id.search_home_ll_price:
				text=et.getText().toString();
				Change(1);
				if(price){
					order=4;
				}else{
					order=3;
				}
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData((type+1),order,page,text),R.string.loding);//可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
				break;
			case R.id.search_home_ll_renqi:
				text=et.getText().toString();
				Change(2);
				if(renqi){
					order=6;
				}else{
					order=5;
				}
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData((type+1),order,page,text),R.string.loding);//可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
				break;


		}
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
			finish();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(listProductBean!=null){
				if("200".equals(listProductBean.getCode())){
					//TODO	
						
					setDate();
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
//			listProductBean=p.SeanchText(type, order, page, text);
			addtype=2;
			if(page==1){
				listProductBean=p.SeanchText(type, order, page, text);
				list = 	listProductBean.getList();
			}else{
				list.clear();
				for(int in=1;in<page+1;in++){
					listProductBean=p.SeanchText(type, order, in, text);
					list.addAll(listProductBean.getList());
				}
			}
			return true;
		}
	}
	void setDate(){
//		list=listProductBean.getList();
		if(list.size()>0){
			if(searchResultAdapter!=null){
				if(addtype==1){
					
				searchResultAdapter.SetData(list);
				searchResultAdapter.notifyDataSetChanged();
				}else{
					searchResultAdapter=new SearchResultAdapter(context, list);
					gv.setAdapter(searchResultAdapter);
				}
			}else{
				searchResultAdapter=new SearchResultAdapter(context, list);
				gv.setAdapter(searchResultAdapter);
			}
		}
		else{
			Util.ShowToast(context, "没有搜到您要的东西！");
		}
		
		
	}
	void initDate(int type,int order,int page,String text){
		//list=listProductBean.getList();
		type+=1;
//		SearchResultActivity.isproductfinish=false;
//		intent=new Intent(context, SearchResultActivity.class);
//		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//		intent.putExtra("searchtype", type+"");
//		intent.putExtra("searchorder", order+"");
//		intent.putExtra("searchpage", page+"");
//		intent.putExtra("searchtext", text);
//		startActivity(intent);
		isyes.setVisibility(View.GONE);
		isresult.setVisibility(View.VISIBLE);
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(type,order,page,text),R.string.loding);//可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){  
	    	if(t==-1){
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	    	}else{
	    		finish();
	    	}
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("search"); 
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("search"); 
		MobclickAgent.onResume(this);
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
						setDate();
					}else{
						l = listProductBean.getList();
						list.addAll(l);
						if(l.size()>0){
							setDate();
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
	private void Change(int j){
		switch (j) {
		case 0:
			if(num){
				num = false;
				list_view.get(0).setBackgroundResource(R.drawable.jiantou_down);
				list_view.get(1).setBackgroundResource(R.drawable.jiantou_down);
				list_view.get(2).setBackgroundResource(R.drawable.jiantou_down);
			}else{
				num = true;
				list_view.get(0).setBackgroundResource(R.drawable.jiantou_up);
				list_view.get(1).setBackgroundResource(R.drawable.jiantou_down);
				list_view.get(2).setBackgroundResource(R.drawable.jiantou_down);
			}
			price=false;
			renqi=false;
			break;
		case 1:
			if(price){
				price = false;
				
				list_view.get(1).setBackgroundResource(R.drawable.jiantou_down);
				list_view.get(0).setBackgroundResource(R.drawable.jiantou_down);
				list_view.get(2).setBackgroundResource(R.drawable.jiantou_down);
			}else{
				price = true;
				list_view.get(1).setBackgroundResource(R.drawable.jiantou_up);
				list_view.get(2).setBackgroundResource(R.drawable.jiantou_down);
				list_view.get(0).setBackgroundResource(R.drawable.jiantou_down);
			}
			num=false;
			renqi=false;
			break;
		case 2:
			if(renqi){
				renqi = false;
				list_view.get(2).setBackgroundResource(R.drawable.jiantou_down);
				list_view.get(1).setBackgroundResource(R.drawable.jiantou_down);
				list_view.get(0).setBackgroundResource(R.drawable.jiantou_down);
			}else{
				renqi = true;
				list_view.get(2).setBackgroundResource(R.drawable.jiantou_up);
				list_view.get(1).setBackgroundResource(R.drawable.jiantou_down);
				list_view.get(0).setBackgroundResource(R.drawable.jiantou_down);
			}
			num=false;
			price=false;
			break;
		}
	}
}
