package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug.IntToString;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
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
import com.example.educonsult.adapters.TextItemListAdapter;
import com.example.educonsult.beans.AreaBean;
import com.example.educonsult.beans.BanksBean;
import com.example.educonsult.beans.BanksBranchBean;
import com.example.educonsult.beans.BanksCityBean;
import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.ListAreaBean;
import com.example.educonsult.beans.ListBanksBean;
import com.example.educonsult.beans.ListBanksBranch;
import com.example.educonsult.beans.ListBanksCity;
import com.example.educonsult.net.PostHttp;
import com.example.educonsult.tools.UITools;
import com.example.educonsult.tools.Util;
import com.testin.agent.TestinAgent;

public class BDCardActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_carway,ll_carwhere,ll_carzhihang;
	private TextView tv_carway,tv_carwhere,tv_carzhihang,tv_name,tv_cid;
	private ListView list_2,lv_l;
	private Context context;
	private ArrayList<String> list1,list2,list3,list4;
	private PopupWindow popu,popu_carway,popu_carwhere,popu_carzhihang;
	private boolean ishave;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private Intent intent;
	
	private LinearLayout lin;
	public static boolean isrezoom;
	private TextItemListAdapter adapter_r;
	
	private ListAreaBean listAreaBean; 
	private Util u;
	private String filename;
	private ArrayList<AreaBean> areaBeans;
	
	private ListBanksCity listBanksCity;
	private ArrayList<BanksCityBean> banksCityBeans;
	private ThreadWithProgressDialog myPDT;
	private ListBanksBean listBanksBean;
	private ArrayList<BanksBean> banks;
	private ArrayList<String> provinces;
	private int intype=0,tnumm,citytype;
	private String province,bankKey,strCity,citynum,bankname,s_name,s_ctype,s_cid;
	private ListBanksBranch listBanksBranch;
	private ArrayList<BanksBranchBean> banksBranchBeans;
	
	private boolean istrue,isdizhi,iszhihang;
	private Button button;
	private BaseBean bean;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		topRightTGone();
		setTopLeftTv(R.string.bdmoneycar_title);
		setContentXml(R.layout.bdmoneycad);
		init();
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
		

	}
	void init(){
		TestinAgent.init(this);
		filename=MyApplication.AreaName;
		u=new Util(context);
		/*listAreaBean=(ListAreaBean)u.readObject(filename);
		areaBeans=listAreaBean.getList();*/
		myPDT=new ThreadWithProgressDialog();
		ll_carway=(LinearLayout)findViewById(R.id.bdmoneycar_carway_lin);
		ll_carway.setOnClickListener(this);
		ll_carwhere=(LinearLayout)findViewById(R.id.bdmoneycar_carwhere_lin);
		ll_carwhere.setOnClickListener(this);
		ll_carzhihang=(LinearLayout)findViewById(R.id.bdmoneycar_carzhihang_lin);
		ll_carzhihang.setOnClickListener(this);
		tv_carway=(TextView)findViewById(R.id.bdmoneycar_carway);
		tv_carwhere=(TextView)findViewById(R.id.bdmoneycar_carwhere);
		tv_carzhihang=(TextView)findViewById(R.id.bdmoneycar_carzhihang);
		button=(Button)findViewById(R.id.bdmoneycard_button1);
		button.setOnClickListener(this);
		tv_name=(TextView)findViewById(R.id.bdmoneycard_username);
		tv_cid=(TextView)findViewById(R.id.bdmoneycard_carid);


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
	

	private void setpopuwindow(Context contexts,ArrayList<String> list,LinearLayout lin){
		inflater=LayoutInflater.from(contexts);
		v_fenlei = inflater.inflate(R.layout.moneycar_list, null);
		lv_l = (ListView) v_fenlei.findViewById(R.id.moneycar_list_list);
		adapter_r = new TextItemListAdapter(context, list);
		lv_l.setAdapter(adapter_r);
		lv_l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(intype==0){
					isdizhi=true;
					bankKey=banks.get(arg2).getKey();
					tv_carway.setText(list1.get(arg2));
					popu.dismiss();
				}else if(intype==1){
					strCity="";
					province=provinces.get(arg2);
					strCity=strCity+province;
					tv_carwhere.setText(provinces.get(arg2));
					citytype=1;
					if(Util.detect(context)){
						myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
					}else{
						Util.ShowToast(context, R.string.net_is_eor);
					}
					
				}else if(intype==2){
					bankname=banksBranchBeans.get(arg2).getBanknumber();
					tv_carzhihang.setText(list3.get(arg2));
					popu.dismiss();
				}
				
				
			}
		});
		
		popu = new PopupWindow(v_fenlei,LayoutParams.FILL_PARENT , UITools.dip2px(context, 300f));
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setOutsideTouchable(true);
		popu.showAsDropDown(lin);
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		
		case R.id.bdmoneycard_button1:
			intype=3;
			s_name=tv_name.getText().toString().trim();
			s_cid=tv_cid.getText().toString().trim();
			if(Util.IsNull(s_cid)&&Util.IsNull(s_name)&&Util.IsNull(bankKey)&&Util.IsNull(bankname)){
				if(Util.detect(context)){
					
					myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
					
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				Util.ShowToast(context, R.string.regist_inpu_error);
			}
			break;
		case R.id.bdmoneycar_carway_lin:
			if(list1!=null&&list1.size()!=0){
				setpopuwindow(context, list1, ll_carway);
				
			}
			//popu_carway.showAsDropDown(ll_carway);
			//Toast.makeText(this,"11",500).show();
			

			break;
		case R.id.bdmoneycar_carwhere_lin:

//			list=new ArrayList<String>();
//			list.add("1");
//			list.add("2");
//			list.add("3");
//			list.add("4");
			if(isdizhi){
				
				intype=1;
				
				if(provinces!=null&&provinces.size()!=0){
					setpopuwindow(context, provinces, ll_carwhere);
//				
				}
			}
			
//			
			
			break;
		case R.id.bdmoneycar_carzhihang_lin:

			if(iszhihang){
				
				intype=2;
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			
			
			
//			Toast.makeText(this,"33",500).show();
			break;

		default:
			break;
		}
	}
	public interface Myorder{
		void delte(int index);
	}
	public class RefeshData implements ThreadWithProgressDialogTask {
		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			//				Toast.makeText(context, "cancle", 1000).show();
			return false; 
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(intype==0){
				if(listBanksBean!=null){
					String code=listBanksBean.getCode();
					String m=listBanksBean.getMsg();
					if("200".equals(code)){
						
						initDate();
					}else if("300".equals(code)){
						intent=new Intent(context,LoginActivity.class);
						startActivity(intent);
						finish();
					}else{
						Util.ShowToast(context, m);
					}
					
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			
			if(intype==1){
//				 ListBanksCity getBanksCityList
				
				if(listBanksCity!=null){
					if("200".equals(listBanksCity.getCode())){
						
						initCity();
						
					}else if("300".equals(listBanksCity.getCode())){
						intent=new Intent(context,LoginActivity.class);
						startActivity(intent);
						finish();
					}else{
						Util.ShowToast(context, listBanksCity.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			if(intype==2){
				if(listBanksBranch!=null){
					if("200".equals(listBanksBranch.getCode())){
						
						initCarCity();
					}else if("300".equals(listBanksBranch.getCode())){
						intent=new Intent(context,LoginActivity.class);
						startActivity(intent);
						finish();
					}else{
						Util.ShowToast(context, R.string.net_is_eor);
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			if(intype==3){
				if(bean!=null){
					if("200".equals(bean.getCode())){
						MyInfoActivity.isread=true;
						Util.ShowToast(context, "绑定成功");
						finish();
					}else if("300".equals(bean.getCode())){
						intent=new Intent(context,LoginActivity.class);
						startActivity(intent);
						finish();
					}else{
						Util.ShowToast(context, R.string.net_is_eor);
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			
			return true;
		}



		@Override
		public boolean TaskMain() {
			PostHttp p=new PostHttp(context);
//			ListBanksBean getBanksList
			if(intype==0){
				listBanksBean=p.getBanksList(MyApplication.mp.getUser().getAuthstr());
				
			}else if(intype==1){
				listBanksCity=p.getBanksCityList(citytype, province,MyApplication.mp.getUser().getAuthstr());
			}else if(intype==2){
//				listBanksBranch;
//				private ArrayList<BanksBranchBean> banksBranchBeans;
				listBanksBranch=p.getBanksBeanchList(Integer.parseInt(bankKey), citynum, "", MyApplication.mp.getUser().getAuthstr());
			}else if(intype==3){
//				 BaseBean bindBankcart
				bean=p.bindBankcart(bankname, s_cid, s_name, MyApplication.mp.getUser().getAuthstr(), Integer.parseInt(bankKey));
			}
			
			return true;
		}
	}
	private void initDate(){
		banks=listBanksBean.getBanks();
		provinces=listBanksBean.getProvince();
		if(banks!=null&&banks.size()!=0){
			list1=new ArrayList<String>();
			for(int i=0;i<banks.size();i++){
				list1.add(banks.get(i).getName());
			}
			
		}
		if(list3!=null&& list3.size()!=0){
			setpopuwindow(context, list3, ll_carzhihang);
//			intype=3;
//			tv_carzhihang.setText(list3.get(tnum));
		}
		
		
	}
	private void initCity(){
		banksCityBeans=listBanksCity.getList();
		list2=new ArrayList<String>();
		if(banksCityBeans!=null&& banksCityBeans.size()!=0){
			for(int i=0;i<banksCityBeans.size();i++){
				String s ="";
				if(Util.IsNull(banksCityBeans.get(i).getCity())){
					s = banksCityBeans.get(i).getCity();
					ishave = true;
				}else if(Util.IsNull(banksCityBeans.get(i).getAreaname())){
					s = banksCityBeans.get(i).getAreaname();
					ishave = false;
				}
				list2.add(s);
			}
		}
		if(list2!=null&&list2.size()!=0){
			adapter_r = new TextItemListAdapter(context, list2);
			adapter_r.notifyDataSetChanged();
			lv_l.setAdapter(adapter_r);
		if(ishave){
			lv_l.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0,
						View arg1, int arg2, long arg3) {
					// TODO Auto-generated method stub
					citynum=banksCityBeans.get(arg2).getAreacode();
					strCity=strCity+list2.get(arg2);
					tv_carwhere.setText(strCity);
					citytype=2;
					if(Util.detect(context)){
						
						myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
						
					}else{
						Util.ShowToast(context, R.string.net_is_eor);
					}
//					if(list2!=null&& list2.size()!=0){
//						adapter_r = new TextItemListAdapter(context, list2);
//						lv_l.setAdapter(adapter_r);
//						lv_l.setOnItemClickListener(new OnItemClickListener() {
//
//							@Override
//							public void onItemClick(AdapterView<?> arg0, View arg1,
//									int arg2, long arg3) {
//								// TODO Auto-generated method stub
//								citynum=banksCityBeans.get(arg2).getAreacode();
//								strCity=strCity+banksCityBeans.get(arg2).getCity();
//								tv_carwhere.setText(strCity);
//								
//								
//							}
//						});
//					}
				}
			});
		}else{
			popu.dismiss();
			istrue=true;
			
		}
			
			
				
		
		}else{
			popu.dismiss();
			istrue=true;

		}
	
		//intype=2;
		iszhihang=true;
		
	}
	private void initCarCity(){
		banksBranchBeans=listBanksBranch.getList();
		if(banksBranchBeans!=null&&banksBranchBeans.size()!=0){
			list3=new ArrayList<String>();
			for(int i=0;i<banksBranchBeans.size();i++){
				list3.add(banksBranchBeans.get(i).getName());
			}
			
			if(list3!=null&& list3.size()!=0){
				setpopuwindow(context, list3, ll_carzhihang);
//				intype=3;
//				tv_carzhihang.setText(list3.get(tnum));
			}
			
		}
	}

}
