package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.annotation.SuppressLint;
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

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.xunbo.store.ExampleActivity;
import com.xunbo.store.MyApplication;
import com.xunbo.store.adapters.MoneyQueryAdapter;
import com.xunbo.store.adapters.TextItemListAdapter;
import com.xunbo.store.beans.ListMoneyBean;
import com.xunbo.store.beans.MoneyDetaileBean;
import com.xunbo.store.beans.UserBean;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

@SuppressLint("InflateParams") public class MoneyQueryActivity extends BaseActivity implements OnClickListener{
	private LinearLayout reaLayout;
	private TextView allquery;
	private ListView list_money;
	private ListView lv_l;
	private MoneyQueryAdapter moneyQueryAdapter;
	private Context context;
	private ArrayList<String> ll;
	private PopupWindow popu;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private Intent intent;
	private TextItemListAdapter adapter_r;
	private ListMoneyBean listmoneybean;
	private UserBean bean;
	private ArrayList<MoneyDetaileBean> moneylist;
	private ThreadWithProgressDialog myPDT;
	private int type;
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		topRightTGone();
		setTitleTxt(R.string.qianbao_query_title);
		rl_l = (RelativeLayout) getTopLightRl();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_l = (ImageView) getTopLightView();
		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
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
	@SuppressWarnings("deprecation")
	void init(){
		TestinAgent.init(this);
		bean=MyApplication.mp.getUser();
		myPDT=new ThreadWithProgressDialog();
		reaLayout=(LinearLayout)findViewById(R.id.qianbao_query_rela);
		reaLayout.setOnClickListener(this);
		list_money=(ListView)findViewById(R.id.qianbao_query_list);
		allquery=(TextView)findViewById(R.id.qianbao_query_allquery);
		
		inflater=LayoutInflater.from(context);
		v_fenlei = inflater.inflate(R.layout.moneycar_list, null);
		
		lv_l = (ListView) v_fenlei.findViewById(R.id.moneycar_list_list);
		ll = new ArrayList<String>();
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
				allquery.setText(ll.get(arg2));
				if("收入".equals(ll.get(arg2))){
					type=MyApplication.money_income;
				}else if("支出".equals(ll.get(arg2))){
					type=MyApplication.money_pay;
				}else{
					type=MyApplication.money_detaile;
				}
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(type),R.string.loding);//可取消
				}else {
					Util.ShowToast(context, R.string.net_is_eor);
				}
				popu.dismiss();
			}
		});
		popu = new PopupWindow(v_fenlei, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setOutsideTouchable(true);
		//popu.update();
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(MyApplication.money_detaile),R.string.loding);//可取消
		}else {
			Util.ShowToast(context, R.string.net_is_eor);
		}
		
		list_money.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				//
				intent = new Intent(context,MoneyQueryInfoActivity.class);
				intent.putExtra("itemid", moneylist.get(arg2).getItemid());
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				
			}
		});
		
		
	}
	public class RefeshData implements ThreadWithProgressDialogTask {
		private int type;
		public RefeshData(int type){
			this.type=type;
		}

		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub
			Send s=new Send(context);
			listmoneybean=s.getMoneyInfo(type, bean.getAuthstr());
			return false;
		}

		@Override
		public boolean OnTaskDismissed() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(listmoneybean!=null){
				String code = listmoneybean.getCode();
				String m = listmoneybean.getMsg();
				if("200".equals(code)){
					
					initData();
				}else{
					if(Util.IsNull(m)){
						Util.ShowToast(context, m);
					}
				}
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			return true;
		
		}
		
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
	public void initData(){
		moneylist=listmoneybean.getList();
		if(moneylist!=null){
			if(moneyQueryAdapter!=null){
				moneyQueryAdapter.setData(moneylist);
				moneyQueryAdapter.notifyDataSetChanged();
			}else{
				moneyQueryAdapter=new MoneyQueryAdapter(context, moneylist);
				list_money.setAdapter(moneyQueryAdapter);
			}
			
		}
	}

}
