package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.xunbo.store.adapters.HomeSlidAdapter;
import com.xunbo.store.adapters.MoneyQueryAdapter;
import com.xunbo.store.beans.ListMoneyBean;
import com.xunbo.store.beans.MoneyDetaileBean;
import com.xunbo.store.myviews.MyListview;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class MoneyQueryInfoActivity extends BaseActivity {
	private RelativeLayout reaLayout;
	private TextView allquery,tvid,tvway,tvwaymoney,tvmoneyway,tvtime
	,tvmoney,tvbeizhu;
	private MyListview list_money,list_way;
	private ListView list_2,lv_l;
	private MoneyQueryAdapter moneyQueryAdapter;
	private Context context;
	private ArrayList<String> list;
	private PopupWindow popu;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private Intent intent;
	private HomeSlidAdapter adapter_r;
	private LinearLayout lin,linbeizhu;
	private int isbeizhu=1;
	private String itemid;
	private ThreadWithProgressDialog myPDT;
	private ListMoneyBean listMoneyBean;
	private ArrayList<MoneyDetaileBean> moneyDetaileBeans;
	private MoneyDetaileBean moneyDetaileBean;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		context=this;
		topRightTGone();
		rl_l = (RelativeLayout) getTopLightRl();
		rl_r = (RelativeLayout) getTopRightRl();
		iv_top_l = (ImageView) getTopLightView();
		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
		iv_top_t = (ImageView) getTopRightView();
		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		setTitleTxt(R.string.moneyquery_info_title);
		setContentXml(R.layout.moneyquery_info);
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
		TestinAgent.init(this);
		//tvid,tvway,tvwaymoney,tvmoneyway,tvtime
		//,tvmoney;
		context=this;
		intent=getIntent();
		itemid=intent.getStringExtra("itmeid");
		myPDT=new ThreadWithProgressDialog();
		tvid=(TextView)findViewById(R.id.moneyquery_info_id);
		tvway=(TextView)findViewById(R.id.moneyquery_info_way);
		tvwaymoney=(TextView)findViewById(R.id.moneyquery_info_waymoney);
		tvmoneyway=(TextView)findViewById(R.id.moneyquery_info_moneyway);
		tvtime=(TextView)findViewById(R.id.moneyquery_info_time);
		tvmoney=(TextView)findViewById(R.id.moneyquery_info_money);
		tvbeizhu=(TextView)findViewById(R.id.moneyquery_info_beizhu1);
		
		linbeizhu=(LinearLayout)findViewById(R.id.moneyqueryinfo_beizhu);
		
		
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		}else {
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}
	public class RefeshData implements ThreadWithProgressDialogTask {
		public RefeshData(){
		}

		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub
			Send s=new Send(context);
			listMoneyBean=s.getMoneyItemInfo(itemid, MyApplication.mp.getUser().getAuthstr());
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
			if(listMoneyBean!=null){
				String code = listMoneyBean.getCode();
				String m = listMoneyBean.getMsg();
				if("200".equals(code)){
					moneyDetaileBeans=listMoneyBean.getList();
					moneyDetaileBean=moneyDetaileBeans.get(0);
					//tvid,tvway,tvwaymoney,tvmoneyway,tvtime
					//,tvmoney;
					tvid.setText(moneyDetaileBean.getItemid());
					tvwaymoney.setText(moneyDetaileBean.getAmount());
					String s=moneyDetaileBean.getAmount().substring(0);
					if("-".equals(s)){
						//如果传过来的值1,备注是隐藏的   是收入方式，不是一则是支付方式
						linbeizhu.setVisibility(View.INVISIBLE);
						tvbeizhu.setText(moneyDetaileBean.getNote());
					}
					tvway.setText(moneyDetaileBean.getReason());
					tvmoneyway.setText(moneyDetaileBean.getBank());
					tvtime.setText(moneyDetaileBean.getAddtime());
					tvmoney.setText(moneyDetaileBean.getBalance());
					
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
	
	

}
