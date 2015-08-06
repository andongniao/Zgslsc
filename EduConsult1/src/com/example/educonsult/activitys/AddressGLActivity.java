package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.umeng.analytics.MobclickAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.adapters.TextItemListAdapter;
import com.xunbo.store.beans.AddressBean;
import com.xunbo.store.beans.AreaBean;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.beans.ListAreaBean;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.UITools;
import com.xunbo.store.tools.Util;

public class AddressGLActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_diqu,ll_jiedao,ll_address,ll_shouhuoren,ll_number,ll_youbian;
	private TextView tv_diqu,tv_jiedao,tv_delete,tv_save;
	private EditText tv_address,tv_shouhuoren,tv_number,tv_youbian;
	private CheckBox cb_set;
	private boolean isok;
	private Intent intent;
	String num;
	private int isdetault;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private Context context;
	private ThreadWithProgressDialog myPDT;
	private AddressBean bean;
	private Util u;
	private int type = 1,ttp;
	private String diqu,dizhi,person,mob,postcode;
	private BaseBean beanresult;
	private boolean init,isture;
	public static final int REQUSET = 1;  



	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		topRightRVisible();
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		setTitleTxt(R.string.address_title);
		setContentXml(R.layout.address_update);
		init();
		addlinstener();
	}

	private void addlinstener() {
		iv_top_t.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AddressGLActivity.this,  
						AddressNewSaveActivity.class);  
				//发送意图标示为REQUSET=1
				intent.putExtra("type", 1);
				intent.putExtra("from", 2);
				intent.putExtra("newsave", bean);
				startActivityForResult(intent, REQUSET);  
			}
		});
	}

	private void init() {
		TestinAgent.init(this);
		context=this;
		u = new Util(context);
		bean = (AddressBean) getIntent().getExtras().get("address");
		myPDT = new ThreadWithProgressDialog();
		String  msg = getResources().getString(R.string.loding);
		num=getIntent().getStringExtra("addressnum");
		isture = getIntent().getBooleanExtra("isture", false);
		//		isok = false;
		ll_diqu = (LinearLayout) findViewById(R.id.address_up_ll_diqu);
		ll_diqu.setOnClickListener(this);
		ll_jiedao = (LinearLayout) findViewById(R.id.address_up_ll_jiedao);
		ll_jiedao.setOnClickListener(this);
		ll_address = (LinearLayout) findViewById(R.id.address_up_ll_detaile);
		ll_address.setOnClickListener(this);
		ll_shouhuoren = (LinearLayout) findViewById(R.id.address_up_ll_shouhuoren);
		ll_shouhuoren.setOnClickListener(this);
		ll_number = (LinearLayout) findViewById(R.id.address_up_ll_phone_number);
		ll_number.setOnClickListener(this);
		ll_youbian = (LinearLayout) findViewById(R.id.address_up_ll_youbian);
		ll_youbian.setOnClickListener(this);
		tv_diqu = (TextView) findViewById(R.id.address_up_tv_diqu);
		//tv_diqu.setOnClickListener(this);
		//		tv_jiedao = (TextView) findViewById(R.id.address_up_tv_jiedao);
		//		tv_jiedao.setOnClickListener(this);
		tv_address = (EditText) findViewById(R.id.address_up_tv_detaile);
		tv_shouhuoren = (EditText) findViewById(R.id.address_up_tv_shouhuoren);

		tv_number = (EditText) findViewById(R.id.address_up_tv_phone_number);

		tv_youbian = (EditText) findViewById(R.id.address_up_tv_youbian);

		tv_delete = (TextView) findViewById(R.id.address_up_tv_delete);
		tv_delete.setOnClickListener(this);
		tv_save = (TextView) findViewById(R.id.address_up_tv_save);
		tv_save.setOnClickListener(this);
		cb_set = (CheckBox) findViewById(R.id.address_up_cb_set_default);
		cb_set.setVisibility(View.GONE);
		cb_set.setChecked(true);
		cb_set.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if(!isture){
					if(cb_set.isChecked()){
						cb_set.setChecked(true);
						isdetault = 1;
					}else{
						cb_set.setChecked(false);
						isdetault = 0;
					}
				}else{
					isdetault = 1;
					cb_set.setChecked(true);
				}
				Util.ShowToast(context, ""+isdetault);
			}
		});
//		if(num.equals("1")){//修改
//			tv_delete.setVisibility(View.GONE);
//		}else{
//			tv_delete.setVisibility(View.GONE);
//		}
		if(bean!=null){
			initData();
		}
	}
	private void initData() {
		tv_youbian.setText(bean.getPostcode());
		tv_number.setText(bean.getMobile());
		tv_shouhuoren.setText(bean.getTruename());
		tv_address.setText(bean.getAddress());
		tv_diqu.setText((Html.fromHtml(bean.getArea())));
		if("1".equals(bean.getIsdefault())){
			tv_save.setVisibility(View.GONE);
		}else{
			tv_save.setVisibility(View.VISIBLE);
		}

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.address_up_ll_diqu:
			//			setpopuwindow(context, lare, ll_diqu);
			break;
		case R.id.address_up_ll_jiedao:

			break;
		case R.id.address_up_ll_detaile:

			break;
		case R.id.address_up_ll_shouhuoren:

			break;
		case R.id.address_up_ll_phone_number:

			break;
		case R.id.address_up_ll_youbian:

			break;
		case R.id.address_up_tv_save:
			diqu = tv_diqu.getText().toString();
			dizhi = tv_address.getText().toString();
			person = tv_shouhuoren.getText().toString();
			postcode = tv_youbian.getText().toString();
			mob = tv_number.getText().toString();
			if(Util.IsNull(diqu) &&Util.IsNull(dizhi) &&Util.IsNull(person) &&Util.IsNull(postcode) &&
					Util.IsNull(mob)){
				if(Util.isMobileNO(mob)){
					if(Util.detect(context)){//设为默认地址
						init = false;
						myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
					}else{
						Util.ShowToast(context, R.string.net_is_eor);
					}
				}else{
					Util.ShowToast(context, "请检查手机号格式");
				}
			}else{
				Util.ShowToast(context, "请保证信息完整");
			}
			break;
		case R.id.address_up_tv_delete:
			if(Util.detect(context)){//删除地址
				init = true;
				myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}

			break;

		}
	}


	// 任务
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
			if(init){
				if(beanresult!=null){
					if("200".equals(beanresult.getCode())){
						init = false;
						AddressActivity.isinit = true;
						Intent intent=new Intent();  
						intent.putExtra("statu", 2);  
						intent.putExtra("ok", bean);  
						setResult(RESULT_OK, intent); 
						finish();
					}else if("300".equals(beanresult.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						intent = new Intent(context,LoginActivity.class);
						startActivity(intent);
						finish();
					}else{
						Util.ShowToast(context, beanresult.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				if(beanresult!=null){
					if("200".equals(beanresult.getCode())){
						Intent intent=new Intent();  
						if(num.equals("1")){
							intent.putExtra("statu", 1);  
							Util.ShowToast(context, "添加成功！");
						}else if(num.equals("0")){
							intent.putExtra("statu", 0);  
							Util.ShowToast(context, "修改成功！");
						}
						OrderActivity.isinit = true;
						AddressActivity.isinit = true;
						intent.putExtra("ok", bean);  
						setResult(RESULT_OK, intent);  
						finish();
					}else if("300".equals(beanresult.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						intent = new Intent(context,LoginActivity.class);
						startActivity(intent);
						finish();
					}else{
						Util.ShowToast(context, beanresult.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}

			}
			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			PostHttp p = new PostHttp(context);
			AddressBean ab = new AddressBean();
			String authstr = MyApplication.mp.getUser().getAuthstr();
			if(!init){
				ab.setAddress(dizhi);
				ab.setArea(diqu);
				ab.setMobile(mob);
				ab.setTruename(person);
				ab.setPostcode(postcode);
				ab.setAreaid(bean.getAreaid());
				ab.setItemid(bean.getItemid());
				ab.setIsdefault(""+1);
				beanresult = p.SetDetaultAddress(bean, authstr);//editOneAddress(ab, authstr);
			}else{
				beanresult = p.deleteOneAddress(bean.getItemid(), authstr);
			}

			return true;
		}
	}


	//	public void showpop(){
	//		adapter_r = new TextItemListAdapter(context, list);
	//		lv_l.setAdapter(adapter_r);
	//	}
	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onPageStart( "AddressGLActivity" );
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd( "AddressGLActivity" );
		MobclickAgent.onPause(this);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {  
			bean = (AddressBean) data.getExtras().get("result"); 
			initData();
		}
	}

}
