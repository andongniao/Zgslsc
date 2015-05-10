package com.example.educonsult.activitys;

import java.util.ArrayList;

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.R;
import com.example.educonsult.adapters.TextItemListAdapter;
import com.example.educonsult.tools.Util;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MyInfoActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_head,ll_friend,ll_mycard,ll_diqu,ll_qiye,ll_geren,ll_two_diqu;
	private Context context;
	private Intent intent;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	public static boolean isread;
	private TextView mycardNum,tv_name,tv_level,tv_rname,tv_diqu
	,tv_cname,tv_cway,tv_cphone,t_rname,t_diqu,t_phone,t_pingzhong,t_siliao,t_person;
	private EditText ed_dizhi,ed_phone,e_dizhi,e_computer,e_price,e_product,e_num;
	private Button button;
	private int cardNum=0;
	private ArrayList<String> list;
	private PopupWindow popu;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ListView list_2,lv_l;
	private TextItemListAdapter adapter_r;
	

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightLVisible();
		topRightRVisible();
		topRightTGone();
		rl_l = (RelativeLayout) getTopLightRl();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_l = (ImageView) getTopLightView();
		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		setTopLeftTv(R.string.myinfo_title);
		setContentXml(R.layout.myinfo);
		init();
		addlistener();
	}

	private void addlistener() {
		rl_l.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(context,XinjianActivity.class);
				intent.putExtra("flag", "myinfo");
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

	private void init() {
		context = this;
		ll_head = (LinearLayout) findViewById(R.id.myinfo_ll_head);
		ll_head.setOnClickListener(this);
		ll_friend = (LinearLayout) findViewById(R.id.myinfo_ll_friend);
		ll_friend.setOnClickListener(this);
		ll_mycard = (LinearLayout) findViewById(R.id.myinfo_ll_mycard);
		ll_mycard.setOnClickListener(this);
		ll_diqu=(LinearLayout)findViewById(R.id.myinfo_ll_qq);
		ll_diqu.setOnClickListener(this);
		ll_two_diqu=(LinearLayout)findViewById(R.id.myinfo_ll_two_diqu);
		ll_two_diqu.setOnClickListener(this);
		button=(Button)findViewById(R.id.myinfo_ok);
		button.setOnClickListener(this);
		ll_qiye=(LinearLayout)findViewById(R.id.myinfo_ll_one_line);
		ll_geren=(LinearLayout)findViewById(R.id.myinfo_ll_two_line);
		mycardNum=(TextView)findViewById(R.id.myinfo_ll_mycard_num);
		if(cardNum==0){
			mycardNum.setText("未绑定");
			mycardNum.setTextColor(getResources().getColor(R.color.red));
		}else{
			mycardNum.setText(cardNum+"张");
		}
		Util.SetRedNum(context, rl_l, 1);
		
		tv_cname=(TextView)findViewById(R.id.myinfo_tv_gongsim);
		tv_cphone=(TextView)findViewById(R.id.myinfo_tv_gongsid);
		tv_cway=(TextView)findViewById(R.id.myinfo_tv_gongsitype);
		tv_diqu=(TextView)findViewById(R.id.myinfo_tv_qq);
		tv_level=(TextView)findViewById(R.id.myinfo_tv_live);
		tv_name=(TextView)findViewById(R.id.myinfo_tv_name);
		tv_rname=(TextView)findViewById(R.id.myinfo_tv_email);
		ed_dizhi=(EditText)findViewById(R.id.myinfo_tv_people);
		ed_phone=(EditText)findViewById(R.id.myinfo_tv_lianxidianhua);
		
//		t_rname,t_diqu,t_phone,t_pingzhong,t_siliao,t_person;
//		private EditText ed_dizhi,ed_phone,e_dizhi,e_computer,e_price,e_product,e_num;
		t_diqu=(TextView)findViewById(R.id.myinfo_two_tv_diqu);
		t_person=(TextView)findViewById(R.id.myinfo_two_tv_person);
		t_rname=(TextView)findViewById(R.id.myinfo_two_tv_rname);
		t_phone=(TextView)findViewById(R.id.myinfo_two_tv_phone);
		t_pingzhong=(TextView)findViewById(R.id.myinfo_two_tv_pingzhong);
		t_siliao=(TextView)findViewById(R.id.myinfo_two_tv_usesiliao);
		e_dizhi=(EditText)findViewById(R.id.myinfo_two_tv_xiangxi);
		e_computer=(EditText)findViewById(R.id.myinfo_two_tv_computer);
		e_price=(EditText)findViewById(R.id.myinfo_two_tv_price);
		e_product=(EditText)findViewById(R.id.myinfo_two_tv_product);
		e_num=(EditText)findViewById(R.id.myinfo_two_tv_num);
		
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
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.myinfo_ll_head:
			
			break;
		case R.id.myinfo_ll_qq:
			//地区
			popu.showAsDropDown(ll_diqu);
			break;
		case R.id.myinfo_ll_two_diqu:
			//地区
			popu.showAsDropDown(ll_two_diqu);
			break;
		case R.id.myinfo_ll_mycard:
			intent = new Intent(context,BDCardActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.myinfo_ll_friend:
			intent = new Intent(context,MyBusinessperntActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		

		case R.id.myinfo_ok:
		break;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(isread){
			Util.SetRedGone(context, rl_l);
			isread = false;
		}
	}

}
