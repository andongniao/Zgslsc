package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.educonsult.R;
import com.example.educonsult.adapters.ShopcartHomeAdapter;
import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.ShopBean;

public class ShopcartActivity extends BaseActivity implements OnClickListener{
	private ListView lv;
	private Context context;
	private ArrayList<Object> list;
	private ShopcartHomeAdapter adapter;
	private LinearLayout ll_isnull,ll_jeisuan;
	private TextView tv_jiesuan;
	private CheckBox cb_all;
	private shop shop;
	private int type,len,cl;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		goneTopLeft();
		topRightRVisible();
		topRightTGone();
		setTitleTxt(R.string.shopcart_title);
		setContentXml(R.layout.shopcart_home_view);
		init();
		addlistener();
	}


	private void init() {
		context = this;
		type = 0;
		list = new ArrayList<Object>();
		lv = (ListView) findViewById(R.id.shopcart_home_lv);
		ll_isnull = (LinearLayout) findViewById(R.id.shopcart_home_ll_isnull);
		ll_jeisuan = (LinearLayout) findViewById(R.id.shopcart_home_ll_show);
		tv_jiesuan = (TextView) findViewById(R.id.shopcart_home_tv_jiesuan);
		tv_jiesuan.setOnClickListener(this);
		cb_all = (CheckBox) findViewById(R.id.shopcart_home_cb_all);
		ll_jeisuan.setVisibility(View.GONE);
		ll_isnull.setVisibility(View.GONE);
		
		shop = new shop() {
			
			@Override
			public void click(boolean b,int index,int postion) {
				ShopBean s = (ShopBean) list.get(index);
				int num = 0;
				if(postion!=-1){
					BaseBean bb = (BaseBean) s.getList().get(postion);
					bb.setIsclick(b);
					for(int i=0;i<s.getList().size();i++){
							BaseBean ba = (BaseBean) s.getList().get(i);;
							if(ba.isIsclick()){
								num+=1;
						}
					}
//					if(b){
						if(num==s.getList().size()){
							s.setIsclick(true);
							type = 0;
						}else{
							type = 1;
							s.setIsclick(false);
						}
//					}else{
//						if(num==0){
//						s.setIsclick(false);
//						}
//					}
				}else{
					if(type==0){
					s.setIsclick(b);
					for(int i=0;i<s.getList().size();i++){
						BaseBean ba = (BaseBean) s.getList().get(i);;
						ba.setIsclick(b);
					}
					}else{
						
					}
				}
				adapter.SetData(list);
				adapter.notifyDataSetChanged();
				len=0;
				for(int i=0;i<list.size();i++){
					ShopBean sb = (ShopBean) list.get(i);
					if(sb.isIsclick()){
						len+=1;
					}
				}
				if(len == list.size()){
					cl = 1;
					cb_all.setChecked(true);
					//TODO
				}else{
					cl = 1;
					cb_all.setChecked(false);
				}
				Toast.makeText(context, ""+len, 200).show();
			}
			
			
		};
		

		ShopBean b1 = new ShopBean();
		b1.setIsclick(false);
		BaseBean a1 = new BaseBean();
		a1.setIsclick(false);
		BaseBean a2 = new BaseBean();
		a2.setIsclick(false);
		ArrayList<Object >l = new ArrayList<Object>();
		l.add(a1);
		l.add(a2);
		b1.setList(l);
		list.add(b1);


		ShopBean b2= new ShopBean();
		b2.setIsclick(false);
		BaseBean a = new BaseBean();
		a.setIsclick(false);
		ArrayList<Object>l2 = new ArrayList<Object>();
		l2.add(a);
		b2.setList(l2);
		list.add(b2);


		adapter = new ShopcartHomeAdapter(context, list,shop);
		lv.setAdapter(adapter);
		lv.setEmptyView(ll_isnull);
		if(list!=null){
			if(list.size()>0){
				ll_jeisuan.setVisibility(View.VISIBLE);
				ll_isnull.setVisibility(View.GONE);
				lv.setVisibility(View.VISIBLE);
			}else{
				ll_jeisuan.setVisibility(View.GONE);
				ll_isnull.setVisibility(View.VISIBLE);
				lv.setVisibility(View.GONE);
			}
		}
	}

	private void addlistener() {
		cb_all.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(cl==1){
					cl=0;
				}else{
					
					for(int i=0;i<list.size();i++){
						ShopBean s = (ShopBean) list.get(i);
						s.setIsclick(isChecked);
						for(int j=0;j<s.getList().size();j++){
							BaseBean b = (BaseBean) s.getList().get(j);
							b.setIsclick(isChecked);
						}
					}
					adapter.SetData(list);
					adapter.notifyDataSetChanged();
				}
					
			}
		});
	}
	
	public static interface shop{
		void click(boolean b,int index,int postion);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shopcart_home_tv_jiesuan:
			Intent intent = new Intent(this,OrderActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;

		}
	}
	
}
