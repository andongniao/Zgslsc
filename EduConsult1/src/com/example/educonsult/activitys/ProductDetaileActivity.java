package com.example.educonsult.activitys;

import java.util.ArrayList;

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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.R;
import com.example.educonsult.adapters.HomeLikeAdapter;
import com.example.educonsult.adapters.ProductPingjiaAdapter;
import com.example.educonsult.myviews.MyListview;

public class ProductDetaileActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private ScrollView scrollView;
	private LinearLayout ll_addshopcart,ll_gopay,ll_as_l,ll_as_t,ll_as_r,
	ll_paied_l,ll_paied_t,ll_paied_r,ll_add_chanpin,ll_add_pingjia,ll_add_dianpu,
	ll_add_view_chanpin,ll_add_view_pingjia,ll_add_view_dianpu;
	private boolean isshow;
	private PopupWindow popupWindow;
	private int w,h,lh;
	private Intent intent;
	private TextView chanpin,pingjia,dianpu;
	private GridView gridView;
	private MyListview listView;
	private ProductPingjiaAdapter pingjiaAdapter;
	private ArrayList<String> list;
	private HomeLikeAdapter homeLikeAdapter;
	

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightLVisible();
		topRightRVisible();
		setTopLeftTv(R.string.product_detaile_title);
		setContentXml(R.layout.product_detail);
		init();
		addlistener();
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
		ll_add_chanpin=(LinearLayout)findViewById(R.id.product_detaile_ll_chanpin);
		ll_add_chanpin.setOnClickListener(this);
		ll_add_pingjia=(LinearLayout)findViewById(R.id.product_detaile_ll_pingjia);
		ll_add_pingjia.setOnClickListener(this);
		ll_add_dianpu=(LinearLayout)findViewById(R.id.product_detaile_ll_dianputuijian);
		ll_add_dianpu.setOnClickListener(this);
		ll_add_view_chanpin=(LinearLayout)findViewById(R.id.product_detaile_ll_add_view_chanpin);
		ll_add_view_pingjia=(LinearLayout)findViewById(R.id.product_detaile_ll_add_view_pingjia);
		ll_add_view_dianpu=(LinearLayout)findViewById(R.id.product_detaile_ll_add_view_dianputuijian);
		chanpin=(TextView)findViewById(R.id.product_detaile_tv_chanpin);
		pingjia=(TextView)findViewById(R.id.product_detaile_tv_pingjia);
		dianpu=(TextView)findViewById(R.id.product_detaile_tv_dianutuijian );
		list=new ArrayList<String>();
		listView=(MyListview)findViewById(R.id.product_detaile_ll_add_view_list);
		pingjiaAdapter=new ProductPingjiaAdapter(this, list);
		listView.setAdapter(pingjiaAdapter);
		gridView=(GridView)findViewById(R.id.product_detaile_all_view_dianputuijian_gv);
		homeLikeAdapter = new HomeLikeAdapter(context, list);
		gridView.setAdapter(homeLikeAdapter);
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
	private void addlistener() {
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toproduct();
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
		case R.id.product_detaile_ll_chanpin:
			ll_add_view_chanpin.setVisibility(View.VISIBLE);
			ll_add_view_pingjia.setVisibility(View.GONE);
			ll_add_view_dianpu.setVisibility(View.GONE);
			chanpin.setTextColor(getResources().getColor(R.color.orn));
			pingjia.setTextColor(getResources().getColor(R.color.black));
			dianpu.setTextColor(getResources().getColor(R.color.black));
			break;
		case R.id.product_detaile_ll_pingjia:
			ll_add_view_chanpin.setVisibility(View.GONE);
			ll_add_view_pingjia.setVisibility(View.VISIBLE);
			ll_add_view_dianpu.setVisibility(View.GONE);
			chanpin.setTextColor(getResources().getColor(R.color.black));
			pingjia.setTextColor(getResources().getColor(R.color.orn));
			dianpu.setTextColor(getResources().getColor(R.color.black));
			break;
		case R.id.product_detaile_ll_dianputuijian:
			ll_add_view_chanpin.setVisibility(View.GONE);
			ll_add_view_pingjia.setVisibility(View.GONE);
			ll_add_view_dianpu.setVisibility(View.VISIBLE);
			chanpin.setTextColor(getResources().getColor(R.color.black));
			pingjia.setTextColor(getResources().getColor(R.color.black));
			dianpu.setTextColor(getResources().getColor(R.color.orn));
			break;
			
			
		}
	}
	private void Toproduct(){
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}


}
