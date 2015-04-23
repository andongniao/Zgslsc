package com.example.educonsult.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.R;

public class ProductDetaileActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private ScrollView scrollView;
	private LinearLayout ll_addshopcart,ll_gopay,ll_as_l,ll_as_t,ll_as_r,
	ll_paied_l,ll_paied_t,ll_paied_r;
	private boolean isshow;
	private PopupWindow popupWindow;
	private int w,h,lh;
	private Intent intent;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightLVisible();
		topRightRVisible();
		setTopLeftTv(R.string.product_detaile_title);
		setContentXml(R.layout.product_detail);
		init();
	}

	private void init() {
		context = this;
		DisplayMetrics  dm = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(dm);  
		w = dm.widthPixels;  
		h = dm.heightPixels;    
		scrollView = (ScrollView) findViewById(R.id.product_detaile_sl);
		ll_as_l = (LinearLayout) findViewById(R.id.product_detaile_ll_tonglei_l);
		ll_as_l.setOnClickListener(this);
		ll_as_t = (LinearLayout) findViewById(R.id.product_detaile_ll_tonglei_t);
		ll_as_t.setOnClickListener(this);
		ll_as_r = (LinearLayout) findViewById(R.id.product_detaile_ll_tonglei_r);
		ll_as_r.setOnClickListener(this);
		ll_paied_l = (LinearLayout) findViewById(R.id.product_detaile_ll_shopped_l);
		ll_paied_l.setOnClickListener(this);
		ll_paied_t = (LinearLayout) findViewById(R.id.product_detaile_ll_shopped_t);
		ll_paied_t.setOnClickListener(this);
		ll_paied_r = (LinearLayout) findViewById(R.id.product_detaile_ll_shopped_r);
		ll_paied_r.setOnClickListener(this);
		ll_gopay = (LinearLayout) findViewById(R.id.product_detaile_ll_pay_now);
		ll_gopay.setOnClickListener(this);
		ll_addshopcart = (LinearLayout) findViewById(R.id.product_detaile_ll_add_shopcart);
		ll_addshopcart.setOnClickListener(this);
		View v = new ImageView(context);
		v.setBackgroundResource(R.drawable.ic_launcher);
		popupWindow = new PopupWindow(v, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		isshow = false;
		scrollView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_MOVE){
					if(!isshow){
						if(scrollView.getScrollY()>100){
							popupWindow.showAtLocation(ll_addshopcart, Gravity.BOTTOM,w/2-20, 120);
							isshow = true;
						}
					}else{
						if(scrollView.getScrollY()<=100){
							if(popupWindow!=null && popupWindow.isShowing()){
								popupWindow.dismiss();
							}
							isshow = false;
						}
					}
				}
				return false;
			}
		});

		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isshow){
					if(popupWindow!=null && popupWindow.isShowing()){
						popupWindow.dismiss();
					}
					scrollView.scrollTo(10, 10);
					Toast.makeText(context, "µ±Ç°Î»ÖÃ"+scrollView.getScrollY(), Toast.LENGTH_SHORT).show();
					isshow = false;
				}
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.product_detaile_ll_tonglei_l:
			Toproduct();
			break;
		case R.id.product_detaile_ll_tonglei_t:
			Toproduct();
			break;
		case R.id.product_detaile_ll_tonglei_r:
			Toproduct();
			break;
		case R.id.product_detaile_ll_shopped_l:
			Toproduct();
			break;
		case R.id.product_detaile_ll_shopped_t:
			Toproduct();
			break;
		case R.id.product_detaile_ll_shopped_r:
			Toproduct();
			break;
		case R.id.product_detaile_ll_pay_now:
			ExampleActivity.setCurrentTab(3);
			finish();
			break;
		case R.id.product_detaile_ll_add_shopcart:
			Toast.makeText(context, "ok", 1000).show();
			break;
			
			
		}
	}
	private void Toproduct(){
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}


}
