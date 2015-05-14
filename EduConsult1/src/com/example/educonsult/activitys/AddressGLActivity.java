package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.activitys.GqTwoActivity.RefeshData;
import com.example.educonsult.adapters.TextItemListAdapter;
import com.example.educonsult.beans.AddressBean;
import com.example.educonsult.beans.AreaBean;
import com.example.educonsult.beans.ListAreaBean;
import com.example.educonsult.myviews.BadgeView;
import com.example.educonsult.tools.UITools;
import com.example.educonsult.tools.Util;

public class AddressGLActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_diqu,ll_jiedao,ll_address,ll_shouhuoren,ll_number,ll_youbian;
	private TextView tv_diqu,tv_jiedao,tv_delete,tv_save;
	private EditText tv_address,tv_shouhuoren,tv_number,tv_youbian;
	private CheckBox cb_set;
	private boolean isok;
//	private ImageView iv_top_l,iv_top_t;
	private Intent intent;
	String num;
	
	private ArrayList<String> list;
	private TextItemListAdapter adapter_r;
	private PopupWindow popu;
	private ListView list_2,lv_l;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private Context context;
	private ThreadWithProgressDialog myPDT;
	private AddressBean bean;
	private ListAreaBean lare;
	private ArrayList<AreaBean>listsheng,listshi,listxian;
	
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
//		topRightLVisible();
//		topRightRVisible();
//		iv_top_l = (ImageView) getTopLightView();
//		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
//		iv_top_t = (ImageView) getTopRightView();
//		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		setTopLeftTv(R.string.address_title);
		setContentXml(R.layout.address_update);
		init();
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		}
	}

	private void init() {
		context=this;
		bean = (AddressBean) getIntent().getExtras().get("address");
		myPDT = new ThreadWithProgressDialog();
		String  msg = getResources().getString(R.string.loding);
		num=intent.getStringExtra("addressnum");
		isok = false;
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
		tv_address.setText(bean.getAddress());
		tv_shouhuoren = (EditText) findViewById(R.id.address_up_tv_shouhuoren);
		tv_shouhuoren.setText(bean.getTruename());
		tv_number = (EditText) findViewById(R.id.address_up_tv_phone_number);
		tv_number.setText(bean.getMobile());
		tv_youbian = (EditText) findViewById(R.id.address_up_tv_youbian);
		tv_youbian.setText(bean.getPostcode());
		tv_delete = (TextView) findViewById(R.id.address_up_tv_delete);
		tv_delete.setOnClickListener(this);
		tv_save = (TextView) findViewById(R.id.address_up_tv_save);
		tv_save.setOnClickListener(this);
		cb_set = (CheckBox) findViewById(R.id.address_up_cb_set_default);
		cb_set.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isok = isChecked;
			}
		});
		if(num.equals("1")){//修改
			tv_delete.setVisibility(View.GONE);
		}
		Util u = new Util(context);
		String filename = MyApplication.AreaName;
		if(u.isExistDataCache(filename) && u.isReadDataCache(filename)){
			lare = (ListAreaBean) u.readObject(filename);
			listsheng = lare.getList();
		}
		
		list=new ArrayList<String>();
//		for(int i=0;i<){
			
//		}
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
				
				popu.dismiss();
			}
		});
		
		popu = new PopupWindow(v_fenlei,LayoutParams.FILL_PARENT , UITools.dip2px(context, 300f));
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setOutsideTouchable(true);
		popu.setFocusable(true);
		popu.showAsDropDown(lin);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.address_up_ll_diqu:
			setpopuwindow(context, list, ll_diqu);
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

			break;
		case R.id.address_up_tv_delete:

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
				return true;
			}

			@Override
			public boolean TaskMain() {
				// 访问
				
				
				
				
				
				return true;
			}
		}

	

}
