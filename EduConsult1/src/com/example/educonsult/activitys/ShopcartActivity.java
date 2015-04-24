package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.educonsult.R;
import com.example.educonsult.adapters.ShopcartHomeAdapter;
import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.ShopBean;
import com.example.educonsult.tools.Util;

public class ShopcartActivity extends BaseActivity implements OnClickListener{
	private long exitTime = 0;
	private ListView lv;
	private Context context;
	private ArrayList<ShopBean> list;
	private ShopcartHomeAdapter adapter;
	private LinearLayout ll_isnull,ll_jeisuan;
	private TextView tv_jiesuan;
	private CheckBox cb_all;
	private shop shop;
	private int type,len,cl;
	private ImageView iv_top_t;
	private RelativeLayout rl_r;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		goneTopLeft();
		topRightRVisible();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_xx_bg);
		setTitleTxt(R.string.shopcart_title);
		setContentXml(R.layout.shopcart_home_view);
		init();
		addlistener();
	}


	private void init() {
		context = this;
		Util.SetRedNum(context, rl_r, 1);
		type = 0;
		list = new ArrayList<ShopBean>();
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

			@Override
			public void add1(int index, int postion) {
//				ShopBean s = (ShopBean) list.get(index);
//				BaseBean bean = (BaseBean) s.getList().get(postion);
				int i = list.get(index).getList().get(postion).getNum();
				i+=1;
				list.get(index).getList().get(postion).setNum(i);
				adapter.SetData(list);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void jian1(int index, int postion) {
				int i = list.get(index).getList().get(postion).getNum();
				i-=1;
				list.get(index).getList().get(postion).setNum(i);
				adapter.SetData(list);
				adapter.notifyDataSetChanged();
			}
			
			
		};
		

		ShopBean b1 = new ShopBean();
		b1.setIsclick(false);
		BaseBean a1 = new BaseBean();
		a1.setIsclick(false);
		a1.setNum(0);
		BaseBean a2 = new BaseBean();
		a2.setIsclick(false);
		a2.setNum(0);
		ArrayList<BaseBean >l = new ArrayList<BaseBean>();
		l.add(a1);
		l.add(a2);
		b1.setList(l);
		list.add(b1);


		ShopBean b2= new ShopBean();
		b2.setIsclick(false);
		BaseBean a = new BaseBean();
		a.setNum(0);
		a.setIsclick(false);
		ArrayList<BaseBean>l2 = new ArrayList<BaseBean>();
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
		void add1( int index,int postion);
		void jian1(int index,int postion);
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
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}
