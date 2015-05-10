package com.example.educonsult.activitys;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.adapters.SearchAdapter;
import com.example.educonsult.adapters.TextItemCenterListAdapter;
import com.example.educonsult.adapters.TextItemListAdapter;
import com.example.educonsult.tools.UITools;
import com.example.educonsult.tools.Util;

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
	private int t;
	private PopupWindow popu;
	private LayoutInflater inflater;
	private View v_fenlei;
	private SearchAdapter adapter_r;
	private static TextItemCenterListAdapter textItemCenterListAdapter;
    private static int postion;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone(); 
		int type = getIntent().getIntExtra("type", -1);
		if(type==-1){
			goneTopLeft();
		}
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
				Intent intent = new Intent(context,SearchResultActivity.class); 
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
	}

	private void init() {
		context = this;
		t = getIntent().getIntExtra("t", -1);
		er = MyApplication.sp.edit();
		sp = (TextView) findViewById(R.id.search_home_sp);
		sp.setOnClickListener(this);
		et = (EditText) findViewById(R.id.search_home_et_inpu);
		et.setTextColor(getResources().getColor(R.color.black));
		lv = (ListView) findViewById(R.id.search_home_lv);
		btn_search = (Button) findViewById(R.id.search_home_btn_seatch);
		btn_search.setOnClickListener(this);
		btn_clean = (Button) findViewById(R.id.search_home_btn_clean);
		btn_clean.setTextColor(getResources().getColor(R.color.black));
		btn_clean.setBackgroundResource(R.color.white);
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
		data_list.add("分类");
		data_list.add("产地");

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
				popu.dismiss();
			}
		});
		
		popu = new PopupWindow(v_fenlei, UITools.dip2px(context, 90f) , UITools.dip2px(context, 300f));
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
							er.putInt("size", 1);
							er.commit();
							l.add(s);
						}
					}
				}
				if(!ishave && Util.IsNull(s)){
					er.putString("search"+(l.size()),et.getText().toString().trim());
					l.add(s);
					er.putInt("size", l.size());
					er.commit();
					ishave = false;
					et.setText("");
				}else{
					Toast.makeText(SearchHomeActivity.this, "error", 500).show();
					ishave = false;
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
}
