package com.example.educonsult.activitys;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.myviews.BadgeView;

public class AddressGLActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_diqu,ll_jiedao,ll_address,ll_shouhuoren,ll_number,ll_youbian;
	private TextView tv_diqu,tv_jiedao,tv_address,tv_shouhuoren,tv_number,tv_youbian
	,tv_delete,tv_save;
	private CheckBox cb_set;
	private boolean isok;
//	private ImageView iv_top_l,iv_top_t;

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
	}

	private void init() {
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
		tv_diqu.setOnClickListener(this);
		tv_jiedao = (TextView) findViewById(R.id.address_up_tv_jiedao);
		tv_jiedao.setOnClickListener(this);
		tv_address = (TextView) findViewById(R.id.address_up_tv_detaile);
		tv_address.setOnClickListener(this);
		tv_shouhuoren = (TextView) findViewById(R.id.address_up_tv_shouhuoren);
		tv_shouhuoren.setOnClickListener(this);
		tv_number = (TextView) findViewById(R.id.address_up_tv_phone_number);
		tv_number.setOnClickListener(this);
		tv_youbian = (TextView) findViewById(R.id.address_up_tv_youbian);
		tv_youbian.setOnClickListener(this);
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
//		BadgeView badge = new BadgeView(this, iv_top_l);
//		badge.setText(""+1);
//		badge.show();
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.address_up_ll_diqu:

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


}
