package com.xunbo.store.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.testin.agent.TestinAgent;
import com.umeng.analytics.MobclickAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.adapters.SearchAdapter;
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
	private Button btn_search,btn_clean;
	private TextView tv;
	private LinearLayout isnull;
	private ArrayList<String> l,ll;
	private ArrayList<String> data_list;
	private ArrayAdapter<String> arr_adapter;
	private SearchAdapter adapter;
	private boolean ishave;
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
				initDate(i_tp,0,3,l.get(arg2));
			}
		});
	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		//t = getIntent().getIntExtra("t", -1);
		
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
		btn_clean.setOnClickListener(this);
		tv = (TextView) findViewById(R.id.search_home_tv_history);
		isnull = (LinearLayout) findViewById(R.id.search_home_ll_list_is_null);
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
			btn_search.setBackgroundResource(R.drawable.search_lv_notnull_btn_bg);

		}else{
			btn_search.setBackgroundResource(R.drawable.search_lv_isnull_btn_bg);
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
		ll.add("产品");
		ll.add("分类");
		ll.add("产地");
		sp.setText(ll.get(0));
		


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
				t=arg2;
				/*if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(arg2,0,3,""),R.string.loding);//可取消
				}*/
				popu.dismiss();
			}
		});
		
		popu = new PopupWindow(v_fenlei, UITools.dip2px(context, 90f) ,LayoutParams.WRAP_CONTENT  );
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
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
					et.setText("");
					Log.i("tttttttttttttttttttttttt", t+","+s);
					initDate(t,0,3,s);
					}else{
						ishave = false;
						Toast.makeText(SearchHomeActivity.this, "关键字已经搜索过", 500).show();
					}
					
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
				btn_search.setBackgroundResource(R.drawable.search_lv_notnull_btn_bg);
				btn_clean.setVisibility(View.VISIBLE);
				tv.setVisibility(View.VISIBLE);
				isnull.setVisibility(View.GONE);
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
						
					//initDate();
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
			listProductBean=p.SeanchText(type, order, page, text);
			
			return true;
		}
	}
	void initDate(int type,int order,int page,String text){
		//list=listProductBean.getList();
		type+=1;
		SearchResultActivity.isproductfinish=false;
		intent=new Intent(context, SearchResultActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intent.putExtra("searchtype", type+"");
		intent.putExtra("searchorder", order+"");
		intent.putExtra("searchpage", page+"");
		intent.putExtra("searchtext", text);
		startActivity(intent);
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
	
}
