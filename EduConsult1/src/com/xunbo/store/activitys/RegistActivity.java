package com.xunbo.store.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.adapters.TextItemListAdapter;
import com.xunbo.store.beans.AreaBean;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.beans.FenleiBean;
import com.xunbo.store.beans.ListAreaBean;
import com.xunbo.store.beans.ListFenleiBean;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.tools.UITools;
import com.xunbo.store.tools.Util;

public class RegistActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private TextView tv_qiye,tv_geren,tv_tiaokuan,tv_diqu,tv_pingzhong,tv_siliao,tv_product,tv_ctype;
	private EditText et_name,et_pass,et_pass_re,et_rname,et_dizhi,
	et_phone,et_compute,et_price,et_num,et_person,et_cname,et_cphone;
	private RadioButton rb_man,rb_woman;
	private Button btn_regist;
	private CheckBox cb_read;
	private int type,tp,tpopu;
	private boolean isread;
	private LinearLayout ll_diqu,ll_pingzhong,ll_siliao,ll_product,ll_ctype,ll_qiye,ll_geren;
	private ArrayList<String> list;
	private PopupWindow popu;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ListView list_2,lv_l;
	private TextItemListAdapter adapter_r;
	private int mscreenwidth;
	private DisplayMetrics dm;
	private boolean isdiqu,isctype,ispingzhong,issiliao,isproduct;
	private int ndiqu=0,diquid=-1,numdiqu,productid=-1,numproduct,nproduct=0;
	private ListAreaBean listareaBean;
	private Util u;
	private ArrayList<AreaBean> areaBeans,areaBean;
	private ThreadWithProgressDialog myPDT;
	private String name,pass,pass_re,phone,diqu,rname,dizhi,siliao,pingzhongString,
	computer,price,product,num,person,cname,ctype,cphone,catid;
	private BaseBean bean;
	String s="" ,p="";
	private ListFenleiBean listFenleiBean;
	private ArrayList<FenleiBean> fenleiBeans,fenleiBeans2;
	private ArrayList<AreaBean>listsheng,listshi,listxian;
	private ArrayList<FenleiBean>fenleiBeanone,fenleiBeanTwo,fenleiBeanThree;
	private int areaid;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//		topRightLVisible();
		//		topRightRVisible();
		topRightTGone();
		setTopLeftTv(R.string.regist_title);
		setContentXml(R.layout.regist_layout);
		init();
		addlistener();
	}


	private void init() {
		TestinAgent.init(this);
		dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		 myPDT=new ThreadWithProgressDialog();
		context = this;
		type = 1;
		tp = 1;
		u=new Util(context);
		listareaBean=(ListAreaBean)u.readObject(MyApplication.AreaName);
		listFenleiBean=(ListFenleiBean)u.readObject(MyApplication.FenleiName);
		if(listareaBean==null){
			areaBean=new ArrayList<AreaBean>();
		}else{
			areaBean=listareaBean.getList();
		}
		if(listFenleiBean==null){
			fenleiBeans=new ArrayList<FenleiBean>();
		}else{
			fenleiBeans=listFenleiBean.getList();
		}
		isread = false;
		tv_qiye = (TextView) findViewById(R.id.regist_tv_qiye);
		tv_qiye.setOnClickListener(this);
		tv_geren = (TextView) findViewById(R.id.regist_tv_geren);
		tv_geren.setOnClickListener(this);
		tv_qiye.setTextColor(getResources().getColor(R.color.black));
		tv_tiaokuan = (TextView) findViewById(R.id.regist_tv_tiaokuan);
		tv_tiaokuan.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
		tv_tiaokuan.setOnClickListener(this);
		et_name = (EditText) findViewById(R.id.regist_et_username);
		et_name.setOnClickListener(this);
		et_pass = (EditText) findViewById(R.id.regist_et_password);
		et_pass.setOnClickListener(this);
		et_pass_re = (EditText) findViewById(R.id.regist_et_password_re);
		et_pass_re.setOnClickListener(this);
		btn_regist = (Button) findViewById(R.id.regist_btn_regist);
		btn_regist.setOnClickListener(this);
		rb_man = (RadioButton) findViewById(R.id.regist_rb_man);
		rb_woman = (RadioButton) findViewById(R.id.regist_rb_woman);

		cb_read = (CheckBox) findViewById(R.id.regist_cb_read);

		//		ll_diqu,ll_pingzhong,ll_siliao,ll_product,ll_ctype;
		ll_diqu=(LinearLayout)findViewById(R.id.regist_ll_geren_diqu);
		ll_diqu.setOnClickListener(this);
		ll_pingzhong=(LinearLayout)findViewById(R.id.regist_ll_geren_pingzhong);
		ll_pingzhong.setOnClickListener(this);
		ll_siliao=(LinearLayout)findViewById(R.id.regist_ll_geren_siliao);
		ll_siliao.setOnClickListener(this);
		ll_ctype=(LinearLayout)findViewById(R.id.regist_ll_qiye_ctype);
		ll_ctype.setOnClickListener(this);
		ll_product=(LinearLayout)findViewById(R.id.regist_ll_geren_product);
		ll_product.setOnClickListener(this);
		ll_qiye=(LinearLayout)findViewById(R.id.regist_ll_qiye);
		ll_geren=(LinearLayout)findViewById(R.id.regist_ll_geren);
		//		tv_diqu,tv_pingzhong,tv_siliao,tv_product,tv_ctype;
		//		private EditText et_name,et_pass,et_pass_re,et_rname,et_diqu,et_dizhi,
		//		et_phone,etcompute,et_price,et_num,et_person,et_cname,et_cphone;
		tv_diqu=(TextView)findViewById(R.id.regist_ll_geren_tv_diqu);
		tv_pingzhong=(TextView)findViewById(R.id.regist_ll_geren_tv_pingzhong);
		tv_siliao=(TextView)findViewById(R.id.regist_ll_geren_tv_siliao);
		tv_product=(TextView)findViewById(R.id.regist_ll_geren_tv_product);
		et_rname=(EditText)findViewById(R.id.regist_ll_geren_rname);
		et_dizhi=(EditText)findViewById(R.id.regist_et_dizhi);
		et_phone=(EditText)findViewById(R.id.regist_ll_geren_phone);
		et_compute=(EditText)findViewById(R.id.regist_ll_geren_computer);
		et_price=(EditText)findViewById(R.id.regist_ll_geren_price);
		et_num=(EditText)findViewById(R.id.regist_ll_geren_num);
		et_person=(EditText)findViewById(R.id.regist_ll_geren_person);


		tv_ctype=(TextView)findViewById(R.id.regist_ll_qiye_tv_ctype);
		et_cname=(EditText)findViewById(R.id.regist_ll_qiye_cname);
		et_cphone=(EditText)findViewById(R.id.regist_ll_qiye_cphone);

		inflater=LayoutInflater.from(context);
		v_fenlei = inflater.inflate(R.layout.moneycar_list, null);

		lv_l = (ListView) v_fenlei.findViewById(R.id.moneycar_list_list);



	}
	private void setpopuwindow(final ArrayList<String> list,LinearLayout lin){
		adapter_r = new TextItemListAdapter(context, list);
		lv_l.setAdapter(adapter_r);
		lv_l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if(tpopu==1){
					isproduct=true;	
					/*tv_product.setText(list.get(arg2));
					catid=arg2+"";*/
					nproduct=arg2;
					productid=productid+1;
					p=p+list.get(arg2);
					tv_product.setText(p);
					adapter_r = new TextItemListAdapter(context, list);
					lv_l.setAdapter(adapter_r);
					
					
					popu.dismiss();
				}else if(tpopu==2){
					isdiqu=true;
					ndiqu=arg2;
					diquid=diquid+1;
					s=s+list.get(arg2);
					tv_diqu.setText(s);
					popu.dismiss();
				}else if(tpopu==3){
					ispingzhong=true;
					tv_pingzhong.setText(list.get(arg2));
					popu.dismiss();
				}else if(tpopu==4){
					issiliao=true;
					tv_siliao.setText(list.get(arg2));
					popu.dismiss();
				}else if(tpopu==5){
					isctype=true;
					tv_ctype.setText(list.get(arg2));
					popu.dismiss();
				}
				
			}
		});
		mscreenwidth=dm.widthPixels;
		popu = new PopupWindow(v_fenlei, mscreenwidth-UITools.dip2px(context, 30), LayoutParams.WRAP_CONTENT);
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setOutsideTouchable(true);
		popu.showAsDropDown(lin);
		
		
	}
	private void setpopuwindow(Context contexts,ListAreaBean larea,LinearLayout lin){
//		listsheng = lare.getList();
		final ArrayList<String> list = new ArrayList<String>(); 
		listsheng = larea.getList();
		for(int i=0;i<listsheng.size();i++){
			list.add(listsheng.get(i).getArename());
		}
		inflater=LayoutInflater.from(contexts);
		v_fenlei = inflater.inflate(R.layout.moneycar_list, null);
		lv_l = (ListView) v_fenlei.findViewById(R.id.moneycar_list_list);
		adapter_r = new TextItemListAdapter(context, list);
		lv_l.setAdapter(adapter_r);
		lv_l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				isdiqu=true;
				listshi = listsheng.get(arg2).getChild(); 
				areaid = listsheng.get(arg2).getAreaid();
				tv_diqu.setText(listsheng.get(arg2).getArename());
				list.clear();
				for(int i=0;i<listshi.size();i++){
					list.add(listshi.get(i).getArename());
				}
				adapter_r = new TextItemListAdapter(context, list);
				lv_l.setAdapter(adapter_r);
				adapter_r.notifyDataSetChanged();
				lv_l.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int ss, long arg3) {
						listxian = listshi.get(ss).getChild();
						areaid = listshi.get(ss).getAreaid();
						String a2 = tv_diqu.getText().toString();
						tv_diqu.setText(a2+listshi.get(ss).getArename());
						if(listxian!=null && listxian.size()>0){
							list.clear();
							for(int i=0;i<listxian.size();i++){
								list.add(listxian.get(i).getArename());
							}
							adapter_r = new TextItemListAdapter(context, list);
							lv_l.setAdapter(adapter_r);
							adapter_r.notifyDataSetChanged();
							lv_l.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int s3, long arg3) {
									areaid = listxian.get(s3).getAreaid();
									String a3 = tv_diqu.getText().toString();
									tv_diqu.setText(a3+listxian.get(s3).getArename());
									popu.dismiss();
								}
							});
						}else{
							popu.dismiss();
						}

					}
				});
				//				popu.dismiss();
			}
		});

		mscreenwidth=dm.widthPixels;
		popu = new PopupWindow(v_fenlei, mscreenwidth-UITools.dip2px(context, 30), LayoutParams.WRAP_CONTENT);
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setOutsideTouchable(true);
		popu.setFocusable(true);
		popu.showAsDropDown(lin);
	}
	private void setpopuwindow(Context contexts,ListFenleiBean listFenleiBean,LinearLayout lin){
//		listsheng = lare.getList();
		final ArrayList<String> list = new ArrayList<String>(); 
		fenleiBeanone = listFenleiBean.getList();
		for(int i=0;i<fenleiBeanone.size();i++){
			list.add(fenleiBeanone.get(i).getCatname());
		}
		inflater=LayoutInflater.from(contexts);
		v_fenlei = inflater.inflate(R.layout.moneycar_list, null);
		lv_l = (ListView) v_fenlei.findViewById(R.id.moneycar_list_list);
		adapter_r = new TextItemListAdapter(context, list);
		lv_l.setAdapter(adapter_r);
		lv_l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				isproduct=true;
				fenleiBeanTwo = fenleiBeanone.get(arg2).getChild(); 
				numproduct = fenleiBeanone.get(arg2).getCatid();
				tv_product.setText(fenleiBeanone.get(arg2).getCatname());
				list.clear();
				for(int i=0;i<fenleiBeanTwo.size();i++){
					list.add(fenleiBeanTwo.get(i).getCatname());
				}
				adapter_r = new TextItemListAdapter(context, list);
				lv_l.setAdapter(adapter_r);
				adapter_r.notifyDataSetChanged();
				lv_l.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int ss, long arg3) {
						fenleiBeanThree = fenleiBeanTwo.get(ss).getChild();
						nproduct = fenleiBeanTwo.get(ss).getCatid();
						String a2 = tv_product.getText().toString();
						tv_product.setText(a2+fenleiBeanTwo.get(ss).getCatname());
						if(fenleiBeanThree!=null && fenleiBeanThree.size()>0){
							list.clear();
							for(int i=0;i<fenleiBeanThree.size();i++){
								list.add(fenleiBeanThree.get(i).getCatname());
							}
							adapter_r = new TextItemListAdapter(context, list);
							lv_l.setAdapter(adapter_r);
							adapter_r.notifyDataSetChanged();
							lv_l.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int s3, long arg3) {
									nproduct = fenleiBeanThree.get(s3).getCatid();
									String a3 = tv_product.getText().toString();
									tv_product.setText(a3+fenleiBeanThree.get(s3).getCatname());
									popu.dismiss();
								}
							});
						}else{
							popu.dismiss();
						}

					}
				});
				//				popu.dismiss();
			}
		});

		mscreenwidth=dm.widthPixels;
		popu = new PopupWindow(v_fenlei, mscreenwidth-UITools.dip2px(context, 30), LayoutParams.WRAP_CONTENT);
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setOutsideTouchable(true);
		popu.setFocusable(true);
		popu.showAsDropDown(lin);
	}

	private void addlistener() {
		rb_man.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					type = 1;
//					Toast.makeText(context, "type=="+type, 200).show();
				}
			}
		});
		rb_woman.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					type = 2;
//					Toast.makeText(context, "type=="+type, 200).show();
				}
			}
		});
		cb_read.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isread = isChecked;
			}
		});

	}
void setrel(){
	for(int i=0;;i++){
		areaBeans=areaBeans.get(ndiqu).getChild();
		if(areaBeans!=null||areaBeans.size()!=0){
			for(int j=0;j<areaBeans.size();j++){
				list.add(areaBeans.get(j).getArename());
			}
			tpopu=2;
			setpopuwindow(list,ll_diqu);
			//popu.showAsDropDown(ll_diqu);
			s=s+list.get(ndiqu);
			numdiqu=areaBeans.get(ndiqu).getAreaid();
		}else{
			tv_diqu.setText(s);
			popu.dismiss();
			break;
		}
	}
	tv_diqu.setText(s);
	popu.dismiss();
}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regist_ll_geren_product:
			setpopuwindow(context, listFenleiBean, ll_product);
			break;
		case R.id.regist_ll_geren_diqu:
			setpopuwindow(context, listareaBean, ll_diqu);
			
			break;
		case R.id.regist_ll_geren_pingzhong:
			list = new ArrayList<String>();
			list.add("猪");
			list.add("蛋鸡");
			list.add("土鸡");
			list.add("肉鸡");
			list.add("牛");
			list.add("肉鸭");
			list.add("蛋鸭");
			list.add("鹌鹑");
			list.add("羊");
			list.add("犬");
			list.add("鱼");
			list.add("兔子");
			list.add("皮毛动物");
			list.add("其他经济动物");
			tpopu=3;
			setpopuwindow(list,ll_pingzhong);
			break;
		case R.id.regist_ll_geren_siliao:
			list = new ArrayList<String>();
			list.add("全价料");
			list.add("预混料");
			list.add("浓缩料");
			list.add("精料补充料料");
			list.add("自配");
			tpopu=4;
			setpopuwindow(list,ll_siliao);
			break;
		case R.id.regist_ll_qiye_ctype:
			list = new ArrayList<String>();
			list.add("企业单位");
			list.add("事业单位或社会团体");
			list.add("个人经营出");
			list.add("其他");
			tpopu=5;
			setpopuwindow(list,ll_ctype);
			break;
		case R.id.regist_btn_regist:
			name = et_name.getText().toString().trim();
			pass = et_pass.getText().toString();
			pass_re = et_pass_re.getText().toString();
			phone = et_phone.getText().toString().trim();
			diqu=tv_diqu.getText().toString().trim();
			rname=et_rname.getText().toString().trim();
			dizhi=et_dizhi.getText().toString().trim();

			siliao=tv_siliao.getText().toString().trim();
			pingzhongString=tv_pingzhong.getText().toString().trim();
			computer=et_compute.getText().toString().trim();
			price=et_price.getText().toString().trim();
			product=tv_product.getText().toString().trim();
			num=et_num.getText().toString().trim();
			person=et_person.getText().toString().trim();

			cname=et_cname.getText().toString().trim();
			ctype=tv_ctype.getText().toString().trim();
			cphone=et_cphone.getText().toString().trim();

			if(Util.IsNull(name) && Util.IsNull(pass) && Util.IsNull(pass_re)&& Util.IsNull(rname)
					&& Util.IsNull(phone)&&isdiqu&& Util.IsNull(dizhi)){
				if(tp==0){
					if (Util.IsNull(cname)&&isctype&&Util.IsNull(cphone)) {
						if(Util.ispassword(pass)){
							if(pass.equals(pass_re)){
								if(Util.isMobileNO(phone)){
									if(Util.detect(context)){
										myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
									}else{
										Util.ShowToast(context, R.string.net_is_eor);
									}
									
								}else{
									Toast.makeText(context, R.string.regist_phone_isfalse, 200).show();
								}

							}else{
								Toast.makeText(context, R.string.regist_password_need_is_one, 200).show();
							}
						}else{
							Toast.makeText(context, R.string.regist_password_mach, 200).show();
						}
						
						
					}else{
						Toast.makeText(context, R.string.regist_inpu_error, 200).show();
					}


				}else if(tp==1){
					if(ispingzhong&&issiliao&&Util.IsNull(computer)
							&&Util.IsNull(price)&&isproduct&&Util.IsNull(num)&&Util.IsNull(person)){
						if(Util.ispassword(pass)){
							if(pass.equals(pass_re)){
								if(Util.isMobileNO(phone)){
									if(Util.detect(context)){
										myPDT.Run(context, new RefeshData(),R.string.loding);//不可取消
									}else{
										Util.ShowToast(context, R.string.net_is_eor);
									}
								}else{
									Toast.makeText(context, R.string.regist_phone_isfalse, 200).show();
								}

							}else{
								Toast.makeText(context, R.string.regist_password_need_is_one, 200).show();
							}
						}else{
							Toast.makeText(context, R.string.regist_password_mach, 200).show();
						}
					}else{
						Toast.makeText(context, R.string.regist_inpu_error, 200).show();
					}


				}
				/*if(pass.equals(pass_re)){
					if(isread){
						Toast.makeText(context, R.string.regist_ok, 200).show();
						//TODO 跳转完善信息页面
						Intent intent = new Intent(context,RegistOKActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						finish();
					}else{
						Toast.makeText(context, R.string.regist_inpu_error, 200).show();
					}
				}else{
					Toast.makeText(context, R.string.regist_password_need_is_one, 200).show();
				}*/
			}else{
				Toast.makeText(context, R.string.regist_inpu_error, 200).show();
			}

			break;
		case R.id.regist_tv_qiye:
			tp = 0;
			tv_qiye.setBackgroundResource(R.drawable.regist_clikeed);
			tv_qiye.setTextColor(getResources().getColor(R.color.white));
			tv_geren.setTextColor(getResources().getColor(R.color.black));
			tv_geren.setBackgroundResource(R.drawable.zcbq1);
			ll_geren.setVisibility(View.GONE);
			ll_qiye.setVisibility(View.VISIBLE);
			break;
		case R.id.regist_tv_geren:
			tp = 1;
			tv_geren.setBackgroundResource(R.drawable.regist_clikeed);
			tv_geren.setTextColor(getResources().getColor(R.color.white));
			tv_qiye.setBackgroundResource(R.color.regist_bg);
			tv_qiye.setBackgroundResource(R.drawable.zcbq1);
			//(R.drawable.regist_unclick);
			tv_qiye.setTextColor(getResources().getColor(R.color.black));
			ll_geren.setVisibility(View.VISIBLE);
			ll_qiye.setVisibility(View.GONE);
			break;
		}
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
			if(bean!=null){
				String code = bean.getCode();
				String m = bean.getMsg();
				if("200".equals(code)){
					Intent i = new Intent(context,RegistOKActivity.class);
					startActivity(i);
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



		@Override
		public boolean TaskMain() {

//			Send s = new Send(context);
//			bean = s.Login(username, password);
			PostHttp p=new PostHttp(context);
//			password 密码
//			truename 真实姓名
//			gender 性别 1是男，2是女
//			areaid 所在地
//			address 详细地址 养殖户必填参数
//			yzpz 养殖品种
//			syzl 使用饲料
//			xsycj 现在使用哪个厂家饲料
//			kjsjw 可以接受的价格
//			catid 急需产品分类 养殖户选填参数
//			bankcard 所用银行卡
//			yzsl 养殖数量
//			tjr 推荐人

			if(tp==1){
				bean=p.Regist(5, name, pass, rname, type, areaid+"", dizhi,phone, pingzhongString, siliao, computer,
						price, numproduct+"", "",num ,person);
			}else{
				bean=p.Regist(6, name, pass, rname, type, areaid+"", dizhi, phone,cname, ctype, cphone);
			}
			

			return true;
		}
	}


}
