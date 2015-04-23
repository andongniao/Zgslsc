package com.example.educonsult.activitys;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educonsult.R;
import com.example.educonsult.tools.Util;

public class RegistActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private TextView tv_qiye,tv_geren,tv_tiaokuan;
	private EditText et_name,et_pass,et_pass_re,et_gongsi,et_people,et_phone;
	private RadioButton rb_man,rb_woman;
	private Button btn_regist;
	private CheckBox cb_read;
	private int type;
	private boolean isread;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightLVisible();
		topRightRVisible();
		setTopLeftTv(R.string.regist_title);
		setContentXml(R.layout.regist_layout);
		init();
		addlistener();
	}


	private void init() {
		context = this;
		type = 1;
		isread = false;
		tv_qiye = (TextView) findViewById(R.id.regist_tv_qiye);
		tv_qiye.setOnClickListener(this);
		tv_geren = (TextView) findViewById(R.id.regist_tv_geren);
		tv_geren.setOnClickListener(this);
		tv_tiaokuan = (TextView) findViewById(R.id.regist_tv_tiaokuan);
		tv_tiaokuan.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
		tv_tiaokuan.setOnClickListener(this);
		et_name = (EditText) findViewById(R.id.regist_et_username);
		et_name.setOnClickListener(this);
		et_pass = (EditText) findViewById(R.id.regist_et_password);
		et_pass.setOnClickListener(this);
		et_pass_re = (EditText) findViewById(R.id.regist_et_password_re);
		et_pass_re.setOnClickListener(this);
		et_gongsi = (EditText) findViewById(R.id.regist_et_gongsi);
		et_gongsi.setOnClickListener(this);
		et_people = (EditText) findViewById(R.id.regist_et_lianxi_ren);
		et_people.setOnClickListener(this);
		et_phone = (EditText) findViewById(R.id.regist_et_lianxi_dianhua);
		et_phone.setOnClickListener(this);
		btn_regist = (Button) findViewById(R.id.regist_btn_regist);
		btn_regist.setOnClickListener(this);
		rb_man = (RadioButton) findViewById(R.id.regist_rb_man);
		rb_woman = (RadioButton) findViewById(R.id.regist_rb_woman);

		cb_read = (CheckBox) findViewById(R.id.regist_cb_read);

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
		case R.id.regist_btn_regist:
			String name = et_name.getText().toString().trim();
			String pass = et_pass.getText().toString().trim();
			String pass_re = et_pass_re.getText().toString().trim();
			String gs = et_gongsi.getText().toString().trim();
			String people = et_people.getText().toString().trim();
			String phone = et_phone.getText().toString().trim();
			if(Util.IsNull(name) && Util.IsNull(pass) && Util.IsNull(pass_re) && Util.IsNull(gs)
					&& Util.IsNull(people) && Util.IsNull(phone)){
				if(pass.equals(pass_re)){
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
				}
			}else{
				Toast.makeText(context, R.string.regist_inpu_error, 200).show();
			}

			break;

		}
	}

}
