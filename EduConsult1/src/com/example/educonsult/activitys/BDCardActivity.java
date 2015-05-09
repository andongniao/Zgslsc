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
import android.widget.Toast;

import com.example.educonsult.R;
import com.example.educonsult.adapters.HomeSlidAdapter;
import com.example.educonsult.adapters.SCStoreAdapter;
import com.example.educonsult.myviews.MyListview;

public class BDCardActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_carway,ll_carwhere,ll_carzhihang;
	private TextView tv_carway,tv_carwhere,tv_carzhihang;
	private ListView list_2,lv_l;
	private Context context;
	private ArrayList<String> list;
	private PopupWindow popu,popu_carway,popu_carwhere,popu_carzhihang;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private Intent intent;
	private HomeSlidAdapter adapter_r;
	private LinearLayout lin;
	public static boolean isrezoom;


	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		topRightTGone();
		setTopLeftTv(R.string.bdmoneycar_title);
		setContentXml(R.layout.bdmoneycad);
		init();

	}
	void init(){
		ll_carway=(LinearLayout)findViewById(R.id.bdmoneycar_carway_lin);
		ll_carway.setOnClickListener(this);
		ll_carwhere=(LinearLayout)findViewById(R.id.bdmoneycar_carwhere_lin);
		ll_carwhere.setOnClickListener(this);
		ll_carzhihang=(LinearLayout)findViewById(R.id.bdmoneycar_carzhihang_lin);
		ll_carzhihang.setOnClickListener(this);
		tv_carway=(TextView)findViewById(R.id.bdmoneycar_carway);
		tv_carwhere=(TextView)findViewById(R.id.bdmoneycar_carwhere);
		tv_carzhihang=(TextView)findViewById(R.id.bdmoneycar_carzhihang);
		list=new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");


		inflater=LayoutInflater.from(context);
		v_fenlei = inflater.inflate(R.layout.know_slidemenu_view, null);
		lin=(LinearLayout)v_fenlei.findViewById(R.id.know_slid_view_ll_r);
		lin.setVisibility(View.GONE);
		list_2=(ListView)v_fenlei.findViewById(R.id.know_slid_view_lv_r);
		list_2.setVisibility(View.GONE);
		lv_l = (ListView) v_fenlei.findViewById(R.id.know_slid_view_lv_l);
		ArrayList<String>ll = new ArrayList<String>();
		adapter_r = new HomeSlidAdapter(context, ll,2);
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
		popu.update();
		popu_carway = new PopupWindow(v_fenlei, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		popu_carway.setFocusable(true);
		popu_carway.setBackgroundDrawable(new BitmapDrawable());
		popu_carway.setOutsideTouchable(true);
		popu_carway.update();
		popu_carwhere = new PopupWindow(v_fenlei, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		popu_carwhere.setFocusable(true);
		popu_carwhere.setBackgroundDrawable(new BitmapDrawable());
		popu_carwhere.setOutsideTouchable(true);
		popu_carwhere.update();
		popu_carzhihang = new PopupWindow(v_fenlei, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		popu_carzhihang.setFocusable(true);
		popu_carzhihang.setBackgroundDrawable(new BitmapDrawable());
		popu_carzhihang.setOutsideTouchable(true);
		popu_carzhihang.update();


	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isrezoom){
			Toast.makeText(context, "重新加载数据", 1).show();
			isrezoom=false;
		}

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.bdmoneycar_carway_lin:

			//			pp_top_fenlei.showAtLocation(ll_all, Gravity.TOP, 0, ll_all.getHeight());
			popu_carway.showAsDropDown(ll_carway);
			Toast.makeText(this,"11",500).show();

			break;
		case R.id.bdmoneycar_carwhere_lin:

			//			pp_top_fenlei.showAtLocation(ll_all, Gravity.TOP, 0, ll_all.getHeight());
			popu_carwhere.showAsDropDown(ll_carwhere);
			Toast.makeText(this,"22",500).show();
			break;
		case R.id.bdmoneycar_carzhihang_lin:

			//	pp_top_fenlei.showAtLocation(ll_all, Gravity.TOP, 0, ll_all.getHeight());
			popu_carzhihang.showAsDropDown(ll_carzhihang);
			Toast.makeText(this,"33",500).show();
			break;

		default:
			break;
		}
	}
	public interface Myorder{
		void delte(int index);
	}

}
