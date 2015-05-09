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

public class SCStoreActivity extends BaseActivity implements OnClickListener{
	private LinearLayout reaLayout;
	private TextView allway;
	private ListView product_list;
	private MyListview list_way;
	private ListView list_2,lv_l;
	private SCStoreAdapter scStoreAdapter;
	private Context context;
	private ArrayList<String> list;
	private PopupWindow popu;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private Intent intent;
	private HomeSlidAdapter adapter_r;
	private LinearLayout lin;
	public static boolean isrezoom;
	public Myorder myorder;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		topRightTGone();
		setTopLeftTv(R.string.scstore_title);
		setContentXml(R.layout.scstore);
		init();
		
	}
	void init(){
		reaLayout=(LinearLayout)findViewById(R.id.scstore_allway_lin);
		reaLayout.setOnClickListener(SCStoreActivity.this);
		product_list=(ListView)findViewById(R.id.scstore_list);
		allway=(TextView)findViewById(R.id.scstore_allway);
		list=new ArrayList<String>();
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		myorder = new Myorder() {

			@Override
			public void delte(int index) {
				list.remove(index);
				scStoreAdapter.SetData(list);
				scStoreAdapter.notifyDataSetChanged();
			}
		};
		scStoreAdapter=new SCStoreAdapter(context, list,myorder);
		product_list.setAdapter(scStoreAdapter);
//		list_money.setOnItemClickListener(new OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
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
		case R.id.scstore_allway_lin:
			
//			pp_top_fenlei.showAtLocation(ll_all, Gravity.TOP, 0, ll_all.getHeight());
				popu.showAsDropDown(reaLayout);
			
			break;

		default:
			break;
		}
	}
	public interface Myorder{
		void delte(int index);
	}

}
