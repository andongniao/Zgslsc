package com.example.educonsult.activitys;

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

import com.example.educonsult.R;
import com.example.educonsult.adapters.TextItemListAdapter;
import com.example.educonsult.tools.UITools;
import com.example.educonsult.tools.Util;

public class RegistActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private TextView tv_qiye,tv_geren,tv_tiaokuan,tv_diqu,tv_pingzhong,tv_siliao,tv_product,tv_ctype;
	private EditText et_name,et_pass,et_pass_re,et_rname,et_dizhi,
	et_phone,et_compute,et_price,et_num,et_person,et_cname,et_cphone;
	private RadioButton rb_man,rb_woman;
	private Button btn_regist;
	private CheckBox cb_read;
	private int type,tp;
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
	private int ndiqu=0,nctype=0,npingzhong=0,nsiliao=0,nproduct=0,nums;
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
		dm = new DisplayMetrics();
		this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		context = this;
		type = 2;
		tp = 2;
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
	private 	boolean setpopuwindow(ArrayList<String> list,LinearLayout lin,final int num){
		adapter_r = new TextItemListAdapter(context, list);
		lv_l.setAdapter(adapter_r);
		this.nums=num;
		lv_l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				nums=1;
				popu.dismiss();
			}
		});
		mscreenwidth=dm.widthPixels;
		popu = new PopupWindow(v_fenlei, mscreenwidth-UITools.dip2px(context, 30), LayoutParams.WRAP_CONTENT);
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setOutsideTouchable(true);
		popu.showAsDropDown(lin);
		if(nums==1){
			return true;
		}
		return false;
	}
	private void addlistener() {
		rb_man.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					type = 1;
					Toast.makeText(context, "type=="+type, 200).show();
				}
			}
		});
		rb_woman.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if(isChecked){
					type = 2;
					Toast.makeText(context, "type=="+type, 200).show();
				}
			}
		});
		cb_read.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isread = isChecked;
				Toast.makeText(context, "isread=="+isread, 200).show();
			}
		});

	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.regist_ll_geren_product:
			list = new ArrayList<String>();
			list.add("全部收支");
			list.add("收入");
			list.add("支出");
			isproduct=setpopuwindow(list,ll_product,nproduct);
			break;
		case R.id.regist_ll_geren_diqu:
			list = new ArrayList<String>();
			list.add("全部收支");
			list.add("收入");
			list.add("支出");
			isdiqu=setpopuwindow(list,ll_diqu,ndiqu);
			break;
		case R.id.regist_ll_geren_pingzhong:
			list = new ArrayList<String>();
			list.add("全部收支");
			list.add("收入");
			list.add("支出");
			ispingzhong=setpopuwindow(list,ll_pingzhong,npingzhong);
			break;
		case R.id.regist_ll_geren_siliao:
			list = new ArrayList<String>();
			list.add("全部收支");
			list.add("收入");
			list.add("支出");
			issiliao=setpopuwindow(list,ll_siliao,nsiliao);
			break;
		case R.id.regist_ll_qiye_ctype:
			list = new ArrayList<String>();
			list.add("全部收支");
			list.add("收入");
			list.add("支出");
			isctype=setpopuwindow(list,ll_ctype,nctype);
			break;
		case R.id.regist_btn_regist:
			String name = et_name.getText().toString().trim();
			String pass = et_pass.getText().toString();
			String pass_re = et_pass_re.getText().toString();
			String phone = et_phone.getText().toString().trim();
			String diqu=tv_diqu.getText().toString().trim();
			String rname=et_rname.getText().toString().trim();
			String dizhi=et_dizhi.getText().toString().trim();

			String siliao=tv_siliao.getText().toString().trim();
			String pingzhongString=tv_pingzhong.toString().trim();
			String computer=et_compute.toString().trim();
			String price=et_price.toString().trim();
			String product=tv_product.toString().trim();
			String num=et_num.toString().trim();
			String person=et_person.toString().trim();

			String cname=et_cname.toString().trim();
			String ctype=tv_ctype.toString().trim();
			String cphone=et_cphone.toString().trim();

			if(Util.IsNull(name) && Util.IsNull(pass) && Util.IsNull(pass_re) 
					&& Util.IsNull(phone)&&isdiqu){
				if(tp==1){
					if (Util.IsNull(cname)&&isctype&&Util.IsNull(cphone)) {
						if(Util.ispassword(pass)){
							if(pass.equals(pass_re)){
								if(Util.isMobileNO(phone)){
									
									
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


				}else if(tp==2){
					if(ispingzhong&&issiliao&&Util.IsNull(computer)
							&&Util.IsNull(price)&&isproduct&&Util.IsNull(num)&&Util.IsNull(person)){
						if(Util.ispassword(pass)){
							if(pass.equals(pass_re)){
								if(Util.isMobileNO(phone)){
									
									
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
			tp = 1;
			tv_qiye.setBackgroundResource(R.drawable.regist_clikeed);
			tv_qiye.setTextColor(getResources().getColor(R.color.white));
			//tv_qiye.setBackgroundResource(R.drawable.regist_butten_bg);
			//tv_qiye.setBackgroundResource(R.drawable.regist_button_bg);
			//				tv_geren.setBackgroundResource(R.drawable.regist_unclick);
			tv_geren.setTextColor(getResources().getColor(R.color.black));
			tv_geren.setBackgroundResource(R.drawable.zcbq1);
			ll_geren.setVisibility(View.GONE);
			ll_qiye.setVisibility(View.VISIBLE);
			break;
		case R.id.regist_tv_geren:
			tp = 2;
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

}
