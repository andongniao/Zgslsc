package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.adapters.ShopcartHomeAdapter;
import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.ListShopBean;
import com.example.educonsult.beans.ShopBean;
import com.example.educonsult.beans.ShopItemBean;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.net.PostHttp;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.Util;
import com.example.educonsult.beans.QuerenOrderBean;

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
	private UserBean bean;
	private ListShopBean shopbean;
	private ArrayList<ShopBean> shoplist;
	private ThreadWithProgressDialog myPDT;
	private BaseBean besebean;
	private boolean islist,clearb;
	public static boolean ischange;
	
	private int inttype=0;
	private int clearposition,clearindex,deposition;
	private QuerenOrderBean querenOrderBean;
	private ListShopBean listShopBean; 

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
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
		}

	}


	private void init() {
		context = this;
		type = 0;
		Util.SetRedNum(context, rl_r, 1);
		bean=MyApplication.mp.getUser();
		myPDT=new ThreadWithProgressDialog();
		//list = new ArrayList<ShopBean>();
		
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
				inttype=1;
				
				clearb=b;
				clearindex=index;
				clearposition=postion;
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
				}
				
			}

			@Override
			public void add1(int index, int postion) {
//				ShopBean s = (ShopBean) list.get(index);
//				ShopItemBean bean = (ShopItemBean) s.getMall().get(postion);
				int i = Integer.parseInt(list.get(index).getMall().get(postion).getAmount());
				i+=1;
				list.get(index).getMall().get(postion).setAmount(""+i);
				adapter.SetData(list);
				adapter.notifyDataSetChanged();
			}

			@Override
			public void jian1(int index, int postion) {
				int i = Integer.parseInt(list.get(index).getMall().get(postion).getAmount());
				i-=1;
				list.get(index).getMall().get(postion).setAmount(""+i);
				adapter.SetData(list);
				adapter.notifyDataSetChanged();
			}
			@Override
			public void delete(int index, int postion) {
				deposition=postion;
				if(Util.detect(context)){
					myPDT.Run(context, new deleteRefeshData(list,postion,index),R.string.loding);//不可取消
				}
				
			}
			
		};
		
		
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
						for(int j=0;j<s.getMall().size();j++){
							ShopItemBean b = (ShopItemBean) s.getMall().get(j);
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
		void delete(int index,int postion);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.shopcart_home_tv_jiesuan:
			inttype=3;
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
			}
			
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
	private void initDate(){
		Log.i("initDate---------------------------", "fffffffffffffffffffffffffffffffffffff");
		

		/*ShopBean b1 = new ShopBean();
		b1.setIsclick(false);
		ShopItemBean a1 = new ShopItemBean();
		a1.setIsclick(false);
		a1.setAmount(""+0);
		ShopItemBean a2 = new ShopItemBean();
		a2.setIsclick(false);
		a2.setAmount(""+0);
		ArrayList<ShopItemBean >l = new ArrayList<ShopItemBean>();
		l.add(a1);
		l.add(a2);
		b1.setMall(l);
		list.add(b1);


		ShopBean b2= new ShopBean();
		b2.setIsclick(false);
		ShopItemBean a = new ShopItemBean();
		a.setAmount(""+0);
		a.setIsclick(false);
		ArrayList<ShopItemBean>l2 = new ArrayList<ShopItemBean>();
		l2.add(a);
		b2.setMall(l2);
		list.add(b2);*/


		
		if(list!=null){
			if(list.size()!=0){
				ll_jeisuan.setVisibility(View.VISIBLE);
				ll_isnull.setVisibility(View.GONE);
				lv.setVisibility(View.VISIBLE);
				adapter = new ShopcartHomeAdapter(context, list,shop);
				lv.setAdapter(adapter);
				lv.setEmptyView(ll_isnull);
			}else{
				ll_jeisuan.setVisibility(View.GONE);
				ll_isnull.setVisibility(View.VISIBLE);
				lv.setVisibility(View.GONE);
			}
		}
	}
	public class deleteRefeshData implements ThreadWithProgressDialogTask {
		private ArrayList<ShopItemBean> shopitembeanlist;
		
		private int position,index;
		private final ArrayList<ShopBean> lists;
		public deleteRefeshData(  ArrayList<ShopBean> lists,int position,int index){
			this.lists=lists;
			this.index=index;
			this.position=position;
		}

		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub
			Send s=new Send(context);
			//shopbean=s.getCartlist(bean.getAuthstr());
			Log.i("index______posstion", index+"---------------"+position);
			if(position!=-1){
				String sss=lists.get(index).getMall().get(position).getItemid();
				besebean=s.CartDel(lists.get(index).getMall().get(position).getItemid(), bean.getAuthstr());
			}
			else{
				besebean=s.CartDel(lists.get(index).getCompanyid(), bean.getAuthstr());
			}
			if(besebean!=null){
				String code = besebean.getCode();
				String m = besebean.getMsg();
				if("200".equals(code)){
					
					if(lists.get(index).getMall()==null||lists.get(index).getMall().size()==0){
						if(Util.detect(context)){
							myPDT.Run(context, new deleteRefeshData(lists, -1, index),R.string.loding);//不可取消
						}
					}
					
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

		@Override
		public boolean OnTaskDismissed() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(besebean!=null){
				String code = besebean.getCode();
				String m = besebean.getMsg();
				if("200".equals(code)&&position==-1||"200".equals(code)){
					list.get(index).getMall().remove(list.get(index).getMall().get(deposition));
					int size = list.get(index).getMall().size();
					if(size==0){
						list.remove(list.get(index));
					}
					adapter.SetData(list);
					adapter.notifyDataSetChanged();
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
					}else{
						ll_jeisuan.setVisibility(View.GONE);
						ll_isnull.setVisibility(View.VISIBLE);
						lv.setVisibility(View.GONE);

					}
					Util.ShowToast(context, "删除成功！");
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

	public class RefeshData implements ThreadWithProgressDialogTask {
		public RefeshData(){
		}

		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub
			Send s=new Send(context);
			PostHttp p=new PostHttp(context);
			if(inttype==0){
				shopbean=s.getCartlist(bean.getAuthstr());
			}else if(inttype==1){
				besebean=s.CartClear(bean.getAuthstr());
			}else if(inttype==3){
//				querenOrderBean=p.Jiesuan(shopbean, bean.getAuthstr());
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
			//任务完成后
			if(querenOrderBean!=null){
				String code=querenOrderBean.getCode();
				String m=querenOrderBean.getMsg();
				
				if("200".equals(code)){
//					
					listShopBean=querenOrderBean.getList();
					
					Intent intent = new Intent(context,OrderActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("shopcaroder", listShopBean);
					intent.putExtra("shopcarbean", shopbean);
					startActivity(intent);
					
					
				}else{
					if(Util.IsNull(m)){
						Util.ShowToast(context, m);
					}
				}
				
			}
			
			else if(shopbean!=null){
				String code = shopbean.getCode();
				String m = shopbean.getMsg();
				if("200".equals(code)){
//					
					list=shopbean.getList();
					initDate();
					
					
				}else{
					if(Util.IsNull(m)){
						Util.ShowToast(context, m);
					}
				}
			}else if(besebean!=null){
				String code = besebean.getCode();
				String m = besebean.getMsg();
				if("200".equals(code)){
//					
					ShopBean s = (ShopBean) list.get(clearindex);
					int num = 0;
					if(clearposition!=-1){
						ShopItemBean bb = s.getMall().get(clearposition);
						bb.setIsclick(clearb);
						for(int i=0;i<s.getMall().size();i++){
								ShopItemBean ba = (ShopItemBean) s.getMall().get(i);;
								if(ba.isIsclick()){
									num+=1;
							}
						}
							if(num==s.getMall().size()){
								s.setIsclick(true);
								type = 0;
							}else{
								type = 1;
								s.setIsclick(false);
							}
					}else{
						s.setIsclick(clearb);
						for(int i=0;i<s.getMall().size();i++){
							ShopItemBean ba = (ShopItemBean) s.getMall().get(i);;
							ba.setIsclick(clearb);
						}
					}
					adapter.SetData(list);
					adapter.notifyDataSetChanged();
					len=0;
					for(int i=0;i<list.size();i++){
						ShopItemBean sb = (ShopItemBean) list.get(i);
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
					Util.ShowToast(context,"清空购物车");
					
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
	
	@Override
	protected void onResume() {
		super.onResume();
		if(ischange){
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
			}
			ischange =false;
		}
	}
	
}
