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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.umeng.analytics.MobclickAgent;
import com.xunbo.store.adapters.HomeSlidAdapter;
import com.xunbo.store.tools.UITools;

public class BusinesspartnersInfoActivity extends BaseActivity implements
OnClickListener{
	private Context context;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private Intent intent;
	private LinearLayout fenleilin;
	private TextView username,name,computername,chongzhi,sup,fenlei;
	private EditText beizhu;
	private String str="0";
	private LayoutInflater inflater;
	private View v_fenlei;
	private LinearLayout lin;
	private ListView list_2,lv_l;
	private HomeSlidAdapter adapter_r;
	private PopupWindow popu;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		topRightLVisible();
		topRightRVisible();
		topRightTGone();
		intent=getIntent();
		str=intent.getStringExtra("bus");
		rl_l = (RelativeLayout) getTopLightRl();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_l = (ImageView) getTopLightView();
		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		if(str.equals("1"))
			setTopLeftTv(R.string.businesspartnersinfo_titlexiugai);//修改商友
		else if(str.equals("2"))
			setTopLeftTv(R.string.businesspartnersinfo_titleadd);//添加商友
		setContentXml(R.layout.businesspartnersinfo_item);
		init();
		addlistener();
	}
	private void init() {
		TestinAgent.init(this);
		// TODO Auto-generated method stub
		//		private LinearLayout fenlei;
		//		private TextView username,name,computername;
		//		private EditText beizhu;
		fenleilin=(LinearLayout)findViewById(R.id.businesspartnersinfo_way_lin);
		fenleilin.setOnClickListener(this);
		chongzhi=(TextView)findViewById(R.id.businesspartnersinfo_re);
		chongzhi.setOnClickListener(this);
		username=(TextView)findViewById(R.id.businesspartnersinfo_username);
		name=(TextView)findViewById(R.id.businesspartnersinfo_name);
		computername=(TextView)findViewById(R.id.businesspartnersinfo_computername);
		beizhu=(EditText)findViewById(R.id.businesspartnersinfo_addbz);
		fenlei=(TextView)findViewById(R.id.businesspartnersinfo_way);
		sup=(TextView)findViewById(R.id.businesspartnersinfo_sup);
		if(str.equals("1")){
			sup.setText("确定");//修改商友
			//加载要修改的数据
		}
		else if(str.equals("2")){
			sup.setText("添加");//添加商友

		}
		sup.setOnClickListener(this);
		
		inflater=LayoutInflater.from(context);
		v_fenlei = inflater.inflate(R.layout.know_slidemenu_view, null);
		lin=(LinearLayout)v_fenlei.findViewById(R.id.know_slid_view_ll_r);
		lin.setVisibility(View.GONE);
		list_2=(ListView)v_fenlei.findViewById(R.id.know_slid_view_lv_r);
		list_2.setVisibility(View.GONE);
		lv_l = (ListView) v_fenlei.findViewById(R.id.know_slid_view_lv_l);
		ArrayList<String>ll = new ArrayList<String>();
//		adapter_r = new HomeSlidAdapter(context, ll,2);
//		lv_l.setAdapter(adapter_r);
		lv_l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				popu.dismiss();
			}
		});
		popu = new PopupWindow(v_fenlei, UITools.dip2px(BusinesspartnersInfoActivity.this,240f), LayoutParams.WRAP_CONTENT);
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setOutsideTouchable(true);
		popu.update();
	}

	private void addlistener() {
		// TODO Auto-generated method stub

	}
	/* (non-Javadoc)
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.businesspartnersinfo_way_lin:
			popu.showAsDropDown(fenleilin);
			break;
		case R.id.businesspartnersinfo_sup:
			
			break;
		case R.id.businesspartnersinfo_addbz:
			
			break;
		case R.id.businesspartnersinfo_re:
			
			if(str.equals("1")){
				//修改商友
				fenlei.setText("请选择分类");
				beizhu.setText("");
				MyBusinessperntActivity.isrezoom=true;
			}
			else if(str.equals("2")){
				//添加商友
				fenlei.setText("请选择分类");
				beizhu.setText("");

			}
			
			break;
		default:
			break;
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart( "BusinesspartnersInfoActivity" );
		MobclickAgent.onResume(this);
	}
	
	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd( "BusinesspartnersInfoActivity" );
		MobclickAgent.onPause(this);
	}
}
