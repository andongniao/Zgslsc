package com.example.educonsult.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.tools.Util;

public class LoginActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private EditText et_username,et_password;
	private CheckBox cb_jizhu;
	private Button btn_login;
	private TextView tv_wangji,tv_free,tv_noaclogin;
	private boolean isremb;
	private Editor er ;
	private PopupWindow ppw;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightLVisible();
		topRightRVisible();
		setTopLeftTv(R.string.login_login);
		setContentXml(R.layout.login_layout);
		init();
	}

	private void init() {
		context = this;
		er = MyApplication.sp.edit();
		isremb = MyApplication.sp.getBoolean("isremb", false);
		et_username = (EditText) findViewById(R.id.login_et_username);
		et_username.setOnClickListener(this);
		et_password = (EditText) findViewById(R.id.login_et_password);
		et_password.setOnClickListener(this);
		cb_jizhu = (CheckBox) findViewById(R.id.login_cb_jizhu);
		cb_jizhu.setOnClickListener(this);
		tv_wangji = (TextView) findViewById(R.id.login_tv_wangji);
		tv_wangji.setOnClickListener(this);
		tv_free = (TextView) findViewById(R.id.login_tv_regist);
		tv_free.setOnClickListener(this);
		tv_noaclogin = (TextView) findViewById(R.id.login_tv_noaclogin);
		tv_noaclogin.setOnClickListener(this);
		btn_login = (Button) findViewById(R.id.login_btn_login);
		btn_login.setOnClickListener(this);
		cb_jizhu.setChecked(isremb);
		if(isremb){
			String n = MyApplication.sp.getString("name", "");
			String p = MyApplication.sp.getString("pass", "");
			et_username.setText(n);
			et_password.setText(p);
			/*****************************************************/
			//			Intent i = new Intent(this, ExampleActivity.class);
			//			UserBean b = new UserBean();
			//			i.putExtra("user", b);
			//			startActivity(i);
			/*****************************************************/
		}


		View v = LayoutInflater.from(context).inflate(R.layout.gq_popw_price, null);
		Button b = (Button) v.findViewById(R.id.gq_popw_price_btn_ok);
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(ppw!=null && ppw.isShowing()){
					ppw.dismiss();
				}
			}
		});
		ppw = new PopupWindow(v, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT, true);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_et_username:

			break;
		case R.id.login_et_password:

			break;
		case R.id.login_cb_jizhu:
			isremb = cb_jizhu.isChecked();
			break;
		case R.id.login_tv_wangji:
			ppw.showAsDropDown(tv_wangji);
			break;
		case R.id.login_tv_regist:
			Intent i = new Intent(this, RegistActivity.class);
			startActivity(i);
			break;
		case R.id.login_tv_noaclogin:
//			isremb = MyApplication.sp.getBoolean("isremb", false);
//			Toast.makeText(context, "isremb"+isremb, 200).show();
			Intent in = new Intent(this, ProductDetaileActivity.class);
			startActivity(in);
			break;
		case R.id.login_btn_login:
			String name = et_username.getText().toString().trim();
			String pass = et_password.getText().toString().trim();
			if(Util.IsNull(name)&&Util.IsNull(pass)){
				er.putBoolean("isremb", isremb);
				if(isremb){
					er.putString("name", name);
					er.putString("pass", pass);
				}else{
					er.putString("name", "");
					er.putString("pass", "");
				}
				er.commit();
				Toast.makeText(context, "login_ok", 200).show();
			}else{
				Toast.makeText(context, "check_please", 200).show();
			}
			break;
		}
	}
}
