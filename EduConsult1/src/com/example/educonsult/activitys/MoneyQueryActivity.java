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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.R;
import com.example.educonsult.adapters.HomeSlidAdapter;
import com.example.educonsult.adapters.MoneyQueryAdapter;
import com.example.educonsult.adapters.TextItemListAdapter;
import com.example.educonsult.myviews.MyListview;

public class MoneyQueryActivity extends BaseActivity implements OnClickListener{
	private LinearLayout reaLayout;
	private TextView allquery;
	private ListView list_money;
	private MyListview list_way;
	private ListView list_2,lv_l;
	private MoneyQueryAdapter moneyQueryAdapter;
	private Context context;
	private ArrayList<String> list;
	private PopupWindow popu;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ImageView iv_top_l,iv_top_t,image;
	private RelativeLayout rl_l,rl_r;
	private Intent intent;
	private TextItemListAdapter adapter_r;
	private LinearLayout lin;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		topRightLVisible();
		topRightRVisible();
		topRightTGone();
		rl_l = (RelativeLayout) getTopLightRl();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_l = (ImageView) getTopLightView();
		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		setTopLeftTv(R.string.qianbao_query_title);
		setContentXml(R.layout.activity_money_query);
		init();
		addlistener();
	}

	private void addlistener() {
		rl_l.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(context,XinjianActivity.class);
				intent.putExtra("flag", "qianbao");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		rl_r.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ExampleActivity.setCurrentTab(0);
				finish();
			}
		});
	}
	void init(){
		reaLayout=(LinearLayout)findViewById(R.id.qianbao_query_rela);
		reaLayout.setOnClickListener(this);
		image=(ImageView)findViewById(R.id.qianbao_query_image);
		list_money=(ListView)findViewById(R.id.qianbao_query_list);
		allquery=(TextView)findViewById(R.id.qianbao_query_allquery);
		list=new ArrayList<String>();
		
		moneyQueryAdapter=new MoneyQueryAdapter(context, list);
		list_money.setAdapter(moneyQueryAdapter);
		list_money.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//
				intent = new Intent(context,MoneyQueryInfoActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
		inflater=LayoutInflater.from(context);
		v_fenlei = inflater.inflate(R.layout.moneycar_list, null);
		
		lv_l = (ListView) v_fenlei.findViewById(R.id.moneycar_list_list);
		ArrayList<String> ll = new ArrayList<String>();
		ll.add("全部收支");
		ll.add("收入");
		ll.add("支出");
		adapter_r = new TextItemListAdapter(context, ll);
		lv_l.setAdapter(adapter_r);
		lv_l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				
				popu.dismiss();
			}
		});
		popu = new PopupWindow(v_fenlei, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setOutsideTouchable(true);
		//popu.update();
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.qianbao_query_rela:
			
//			pp_top_fenlei.showAtLocation(ll_all, Gravity.TOP, 0, ll_all.getHeight());
				popu.showAsDropDown(reaLayout);
			
			break;

		default:
			break;
		}
	}

}
