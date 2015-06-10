package com.example.educonsult.activitys;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
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
import com.example.educonsult.beans.QuerenOrderBean;
import com.example.educonsult.beans.ShopBean;
import com.example.educonsult.beans.ShopItemBean;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.net.PostHttp;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.Util;
import com.testin.agent.TestinAgent;

public class ShopcartActivity extends BaseActivity implements OnClickListener{
	private long exitTime = 0;
	private ListView lv;
	private Context context;
	private ArrayList<ShopBean> list;
	private ShopcartHomeAdapter adapter;
	private LinearLayout ll_isnull,ll_jeisuan;
	private TextView tv_jiesuan,tv_heji;
	private CheckBox cb_all;
	private shop shop;
	private int type,len,cl;
	private ImageView iv_top_t;
	private RelativeLayout rl_r;
	private UserBean bean;
	private ListShopBean shopbean,shopbean2;
	private ArrayList<ShopBean> shoplist,shoplist2;
	private ArrayList<ShopItemBean> shopitemlist;
	private ThreadWithProgressDialog myPDT;
	private BaseBean besebean;
	private boolean islist,clearb,ischoose;
	public static boolean ischange;
	private float sum,i_price;
	private int inttype=0;
	private int clearposition,clearindex,deposition,showtp;
	private QuerenOrderBean querenOrderBean;
	private ListShopBean listShopBean; 
	private Intent intent;
	private ShopBean shopbeansil;
	private String strsum;


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		goneTopLeft();
		topRightRVisible();
		topRightTGone();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.del_icon_normal);
		setTitleTxt(R.string.shopcart_title);
		setContentXml(R.layout.shopcart_home_view);
		init();
		addlistener();
		if(MyApplication.mp.islogin){
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
		}else{
			Intent i = new Intent(context,LoginActivity.class);
			startActivity(i);
			finish();
		}

	}


	private void init() {
		TestinAgent.init(this);
		context = this;
		type = 0;
		showtp = 0;
		//		Util.SetRedNum(context, rl_r, 1);
		bean=MyApplication.mp.getUser();
		myPDT=new ThreadWithProgressDialog();
		list = new ArrayList<ShopBean>();
		shoplist2=new ArrayList<ShopBean>();

		shopbean2=new ListShopBean();
		lv = (ListView) findViewById(R.id.shopcart_home_lv);
		ll_isnull = (LinearLayout) findViewById(R.id.shopcart_home_ll_isnull);
		ll_jeisuan = (LinearLayout) findViewById(R.id.shopcart_home_ll_show);
		tv_jiesuan = (TextView) findViewById(R.id.shopcart_home_tv_jiesuan);
		tv_heji=(TextView)findViewById(R.id.shopcart_home_view_tv_heji);
		tv_jiesuan.setOnClickListener(this);
		cb_all = (CheckBox) findViewById(R.id.shopcart_home_cb_all);
		ll_jeisuan.setVisibility(View.GONE);
		ll_isnull.setVisibility(View.GONE);
		tv_heji.setText("￥"+sum);
		shop = new shop() {

			@Override
			public void click(boolean b,int index,int postion) {
				inttype=1;
				clearb=b;
				clearindex=index;
				clearposition=postion;
				/*if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
				}*/

				//	

				ShopBean s = (ShopBean) list.get(clearindex);
				int num = 0;
				if(clearposition!=-1){
					ShopItemBean bb = s.getMall().get(clearposition);
					bb.setIsclick(clearb);
					for(int i=0;i<s.getMall().size();i++){
						ShopItemBean ba = (ShopItemBean) s.getMall().get(i);
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
				adapter.SetData(list,showtp);
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
				}
				else{
					cl = 1;
					cb_all.setChecked(false);
				}
				sum=0;
				int i_num;
				for(int i=0;i<list.size();i++){
					for(int j=0;j<list.get(i).getMall().size();j++){
						if(list.get(i).getMall().get(j).isIsclick()){

							i_num=list.get(i).getMall().get(j).getNum();
							i_price=Float.parseFloat(list.get(i).getMall().get(j).getPrice());
							sum=i_num*i_price+sum;
						}
					}
				}
				strsum=sum+"";
				strsum=strsum.substring(0,strsum.indexOf(".")+2);
				tv_heji.setText("￥"+strsum);



			}

			@Override
			public void add1(int index, int postion) {
				//				ShopBean s = (ShopBean) list.get(index);
				//				ShopItemBean bean = (ShopItemBean) s.getMall().get(postion);
				int i =list.get(index).getMall().get(postion).getNum();
				i+=1;
				list.get(index).getMall().get(postion).setNum(i);
				adapter.SetData(list,showtp);
				adapter.notifyDataSetChanged();
				sum=0;
				int i_num;
				for(int f=0;f<list.size();f++){
					for(int j=0;j<list.get(f).getMall().size();j++){
						if(list.get(f).getMall().get(j).isIsclick()){

							i_num=list.get(f).getMall().get(j).getNum();
							i_price=Float.parseFloat(list.get(f).getMall().get(j).getPrice());
							sum=i_num*i_price+sum;
						}
					}
				}
				strsum=sum+"";
				strsum=strsum.substring(0,strsum.indexOf(".")+2);
				tv_heji.setText("￥"+strsum);
			}

			@Override
			public void jian1(int index, int postion) {
				int i = list.get(index).getMall().get(postion).getNum();
				i-=1;
				list.get(index).getMall().get(postion).setNum(i);
				adapter.SetData(list,showtp);
				adapter.notifyDataSetChanged();
				int i_num;
				for(int f=0;f<list.size();f++){
					for(int j=0;j<list.get(f).getMall().size();j++){
						if(list.get(f).getMall().get(j).isIsclick()){

							i_num=list.get(f).getMall().get(j).getNum();
							i_price=Float.parseFloat(list.get(f).getMall().get(j).getPrice());
							sum=i_num*i_price+sum;
						}
					}
				}
				strsum=sum+"";
				strsum=strsum.substring(0,strsum.indexOf(".")+2);
				tv_heji.setText("￥"+strsum);
			}
			@Override
			public void delete(int index, int postion) {
				deposition=postion;
				if(Util.detect(context)){
					myPDT.Run(context, new deleteRefeshData(list,postion,index),R.string.loding);//不可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}

			}

		};


	}

	private void addlistener() {
		iv_top_t.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(list!=null){
					if(list.size()>0){
						if(showtp==0){
							showtp=1;
							cb_all.setVisibility(View.GONE);
							ll_jeisuan.setVisibility(View.GONE);
							adapter.SetData(list, showtp);
							adapter.notifyDataSetChanged();
						}else{
							showtp=0;
							cb_all.setVisibility(View.VISIBLE);
							ll_jeisuan.setVisibility(View.VISIBLE);
							adapter.SetData(list, showtp);
							adapter.notifyDataSetChanged();
						}
					}
				}
			}
		});
		cb_all.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean isChecked  = cb_all.isChecked();
				for(int i=0;i<list.size();i++){
					ShopBean s = (ShopBean) list.get(i);
					s.setIsclick(isChecked);
					for(int j=0;j<s.getMall().size();j++){
						ShopItemBean b = (ShopItemBean) s.getMall().get(j);
						b.setIsclick(isChecked);
					}
				}
				sum=0;
				int i_num;
				for(int i=0;i<list.size();i++){
					for(int j=0;j<list.get(i).getMall().size();j++){
						if(list.get(i).getMall().get(j).isIsclick()){

							i_num=list.get(i).getMall().get(j).getNum();
							i_price=Float.parseFloat(list.get(i).getMall().get(j).getPrice());
							sum=i_num*i_price+sum;
						}
					}
				}
				strsum=sum+"";
				strsum=strsum.substring(0,strsum.indexOf(".")+2);
				tv_heji.setText("￥"+strsum);
				adapter.SetData(list,showtp);
				adapter.notifyDataSetChanged();
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
			//			shoplist2=new ArrayList();
			//			shopitmelist=new ArrayList<ShopItemBean>();

			for(int i=0;i<list.size();i++){
				for(int j=0;j<list.get(i).getMall().size();j++){
					if(list.get(i).getMall().get(j).isIsclick()){
						ischoose=true;
						break;
					}
				}
			}
			if(ischoose){
				try {
					shoplist2= (ArrayList<ShopBean>) deepCopy(list);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				for(int i=0;i<shoplist2.size();i++){
					for(int j=0;j<shoplist2.get(i).getMall().size();j++){
						if(!shoplist2.get(i).getMall().get(j).isIsclick()){
							shoplist2.get(i).getMall().remove(shoplist2.get(i).getMall().get(j));
							j=-1;
						}
						if(shoplist2.get(i).getMall()!=null&&shoplist2.get(i).getMall().size()==0){
							shoplist2.remove(shoplist2.get(i));
							i=0;
							j=-1;
						}
					}
				}
				shopbean2.setList(shoplist2);
				if(shoplist2!=null&&shoplist2.size()>=1){

					if(Util.detect(context)){
						myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
					}else{
						Util.ShowToast(context, R.string.net_is_eor);
					}
				}else {
					Util.ShowToast(context, "请选择物品！");


				}
				ischoose=false;
			}else {
				Util.ShowToast(context, "请选择物品！");


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
		if(list!=null){
			if(list.size()!=0){
				ll_jeisuan.setVisibility(View.VISIBLE);
				ll_isnull.setVisibility(View.GONE);
				lv.setVisibility(View.VISIBLE);
				if(adapter!=null){
					adapter.SetData(list,showtp);
					adapter.notifyDataSetChanged();
				}else{
					adapter = new ShopcartHomeAdapter(context, list,shop,showtp);
					lv.setAdapter(adapter);
					lv.setEmptyView(ll_isnull);
				}
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
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(besebean!=null){
				String code = besebean.getCode();
				String m = besebean.getMsg();
				if("200".equals(code)){
					list.get(index).getMall().remove(list.get(index).getMall().get(position));
					int size = list.get(index).getMall().size();
					if(size==0){
						list.remove(list.get(index));
					}
					adapter.SetData(list,showtp);
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
					sum=0;
					int i_num;
					for(int i=0;i<list.size();i++){
						for(int j=0;j<list.get(i).getMall().size();j++){
							if(list.get(i).getMall().get(j).isIsclick()){

								i_num=list.get(i).getMall().get(j).getNum();
								i_price=Float.parseFloat(list.get(i).getMall().get(j).getPrice());
								sum=i_num*i_price+sum;
							}
						}
					}
					strsum=sum+"";
					strsum=strsum.substring(0,strsum.indexOf(".")+2);
					tv_heji.setText("￥"+strsum);
					if(list!=null && list.size()==0){
						showtp=0;
						cb_all.setVisibility(View.VISIBLE);
						adapter.SetData(list, showtp);
						adapter.notifyDataSetChanged();
					}
				}
				else if("300".equals(code)){
					intent = new Intent(context,LoginActivity.class);
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
				querenOrderBean=p.Jiesuan(shopbean2, bean.getAuthstr());
			}

			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			ischange = true;
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			if(inttype==3){
				if(querenOrderBean!=null){
					String code=querenOrderBean.getCode();
					String m=querenOrderBean.getMsg();
					if("200".equals(code)){
						//						
						listShopBean=querenOrderBean.getList();


						intent = new Intent(context,OrderActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						Bundle b=new Bundle();
						b.putSerializable("shopcaroder", listShopBean);
						b.putSerializable("shopcarbean", shopbean2);

						b.putString("money", tv_heji.getText().toString());
						intent.putExtra("shopcartbundle", b);
						startActivity(intent);


					}else if("300".equals(code)){
						intent=new Intent(context,LoginActivity.class);
						startActivity(intent);
						finish();
					}
					else{
						if(Util.IsNull(m)){
							Util.ShowToast(context, m);
						}
					}

				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			//任务完成后

			if(inttype==0){
				if(shopbean!=null){
					String code = shopbean.getCode();
					String m = shopbean.getMsg();
					if("200".equals(code)){
						//							
						list=shopbean.getList();
						initDate();


					}else if("300".equals(code)){
						Util.ShowToast(context, m);
						intent = new Intent(context,LoginActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
					}else{
						if(Util.IsNull(m)){
							Util.ShowToast(context, m);
						}
					}
				}
				else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			if(inttype==1){
				if(besebean!=null){
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
						adapter.SetData(list,showtp);
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
					}else if("300".equals(code)){
						intent=new Intent(context,LoginActivity.class);
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
			} 
			return true;

		}

	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onResume() {
		super.onResume();
		inttype=0;
		if(ischange){
			if(Util.detect(context)){
				cb_all.setChecked(false);
				myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			ischange =false;
		}
		if(list!=null){
			try {
				shoplist2= (ArrayList<ShopBean>) deepCopy(list);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	@SuppressWarnings("rawtypes")
	public List deepCopy(List src) throws IOException, ClassNotFoundException{ 
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream(); 
		ObjectOutputStream out = new ObjectOutputStream(byteOut); 
		out.writeObject(src); 
		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray()); 
		ObjectInputStream in =new ObjectInputStream(byteIn); 
		List dest = (List)in.readObject(); 
		return dest; 
	}

}
