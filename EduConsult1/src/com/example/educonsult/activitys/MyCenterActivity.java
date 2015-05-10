package com.example.educonsult.activitys;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.activitys.LoginActivity.RefeshData;
import com.example.educonsult.beans.CenterUserBean;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.myviews.BadgeView;
import com.example.educonsult.myviews.CircleImageView;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.Util;


public class MyCenterActivity extends BaseActivity implements OnClickListener{
	private long exitTime = 0;
	private Context context;
	private Intent intent;
	private LinearLayout ll_zhifu,ll_cp,ll_mima,ll_dp,ll_zj,ll_zf,ll_fh,ll_sh,ll_pj,ll_order,ll_address,
	ll_qianbao,ll_xinjian,ll_jifen,ll_youhuiquan,ll_fenxiang,ll_fuwu,ll_update,ll_tuijian;
	private ImageView iv_zhifu,iv_fahuo,iv_shouhuo,iv_pingjia;
	private CircleImageView icv_head;
	private TextView tv_version;
	private UserBean bean;
	private CenterUserBean cbean;
	private ThreadWithProgressDialog myPDT;
	private String msg;
	
	
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		goneTopLeft();
		topRightTGone();
		setTitleTxt(R.string.mycenger_title);
		setContentXml(R.layout.mycenter);
		init();
	}

	private void init() {
		
		
		context = this;
		tv_version = (TextView) findViewById(R.id.mycenter_home_tv_version);
		icv_head = (CircleImageView) findViewById(R.id.mycenter_home_civ_head);
		icv_head.setOnClickListener(this);
		iv_zhifu = (ImageView) findViewById(R.id.mycenter_home_btn_zhifu);
		ll_cp = (LinearLayout) findViewById(R.id.mycenter_home_ll_cp);
		ll_cp.setOnClickListener(this);
		ll_dp = (LinearLayout) findViewById(R.id.mycenter_home_ll_dp);
		ll_dp.setOnClickListener(this);
		ll_zj = (LinearLayout) findViewById(R.id.mycenter_home_ll_zj);
		ll_zj.setOnClickListener(this);
		ll_zf = (LinearLayout) findViewById(R.id.mycenter_home_ll_zhifu);
		ll_zf.setOnClickListener(this);
		ll_fh = (LinearLayout) findViewById(R.id.mycenter_home_ll_fahuuo);
		ll_fh.setOnClickListener(this);
		ll_sh = (LinearLayout) findViewById(R.id.mycenter_home_ll_shouhuo);
		ll_sh.setOnClickListener(this);
		ll_pj = (LinearLayout) findViewById(R.id.mycenter_home_ll_pingjia);
		ll_pj.setOnClickListener(this);
		ll_order = (LinearLayout) findViewById(R.id.mycenter_home_ll_order);
		ll_order.setOnClickListener(this);
		ll_address = (LinearLayout) findViewById(R.id.mycenter_home_ll_address);
		ll_address.setOnClickListener(this);
		ll_qianbao = (LinearLayout) findViewById(R.id.mycenter_home_ll_qianbao);
		ll_qianbao.setOnClickListener(this);
		ll_xinjian = (LinearLayout) findViewById(R.id.mycenter_home_ll_xinjian);
		ll_xinjian.setOnClickListener(this);
		ll_jifen = (LinearLayout) findViewById(R.id.mycenter_home_ll_jifen);
		ll_jifen.setVisibility(View.GONE);
		ll_jifen.setOnClickListener(this);
		ll_fenxiang = (LinearLayout) findViewById(R.id.mycenter_home_ll_fenxiang);
		ll_fenxiang.setOnClickListener(this);
		ll_fenxiang.setVisibility(View.GONE);
		ll_fuwu = (LinearLayout) findViewById(R.id.mycenter_home_ll_fuwu);
		ll_fuwu.setOnClickListener(this);
		ll_update = (LinearLayout) findViewById(R.id.mycenter_home_ll_update);
		ll_update.setOnClickListener(this);
		ll_tuijian = (LinearLayout) findViewById(R.id.mycenter_home_ll_tuijian);
		ll_tuijian.setOnClickListener(this);
		ll_youhuiquan = (LinearLayout) findViewById(R.id.myinfo_ll_youhuiquan);
		ll_youhuiquan.setOnClickListener(this);
		ll_mima = (LinearLayout) findViewById(R.id.mycenter_home_ll_mima);
		ll_mima.setOnClickListener(this);
		ll_zhifu=(LinearLayout)findViewById(R.id.mycenter_home_btn_zhifu_lin);
		BadgeView badge = new BadgeView(this, ll_zhifu);
		badge.setText(""+1);
		badge.show();
//		badge.toggle();
		PackageManager manager;
		PackageInfo info = null;
		manager = this.getPackageManager();
		try {
		info = manager.getPackageInfo(this.getPackageName(), 0);
		} catch (NameNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}

		String vserion = info.versionName;
		tv_version.setText(vserion);
		
		
		bean=MyApplication.mp.getUser();
		if(bean.getType()==1){
			ll_fh.setVisibility(View.GONE);
		}else if(bean.getType()==0){
			ll_zhifu.setVisibility(View.GONE);
			ll_sh.setVisibility(View.GONE);
			ll_youhuiquan.setVisibility(View.GONE);
		}
		myPDT = new ThreadWithProgressDialog();
		msg = "加载中...";
		if(Util.detect(context)){
			//myPDT.Run(context, new RefeshData(bean.getType(),bean.getAuthstr()),msg,false);//不可取消
		}
	}
	public class RefeshData implements ThreadWithProgressDialogTask {
		private int type;
		private String authstr;
		public RefeshData(int type,String authstr){
			this.type=type;
			this.authstr=authstr;
		}

		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub
			Send s=new Send(context);
			cbean=s.getMyinfo(type, authstr);
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
			if(cbean!=null){
				String code = cbean.getCode();
				String m = cbean.getMsg();
				if("200".equals(code)){
//					Util.ShowToast(context, bean.getAuthstr());
//					MyApplication.bean.setAuthstr(bean.getAuthstr());
					/*Intent in = new Intent(context, ExampleActivity.class);
					MyApplication.mp.setUser(bean);
					startActivity(in);*/
					
					finish();
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
		switch (v.getId()) {
		case R.id.mycenter_home_civ_head:
			intent = new Intent(context,MyInfoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_cp:
			intent = new Intent(context,SCProductActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_dp:
			intent = new Intent(context,SCStoreActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.myinfo_ll_youhuiquan:
			intent = new Intent(context,ConfirmTheDeliveryActivity.class);
			//intent = new Intent(context,CouponsActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_zj:
			intent = new Intent(context,MyZjActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_zhifu:
			intent = new Intent(context,MyOrderActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("index", 1);
			startActivity(intent);
			Toast.makeText(context, "Test", 500).show();
			break;
		case R.id.mycenter_home_ll_fahuuo:
			intent = new Intent(context,MyOrderActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("index", 2);
			startActivity(intent);
			Toast.makeText(context, "Test", 500).show();
			break;
		case R.id.mycenter_home_ll_shouhuo:
			intent = new Intent(context,MyOrderActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("index", 3);
			startActivity(intent);
			Toast.makeText(context, "Test", 500).show();
			break;
		case R.id.mycenter_home_ll_pingjia:
			intent = new Intent(context,MyOrderActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("index", 4);
			startActivity(intent);
			Toast.makeText(context, "Test", 500).show();
			break;
		case R.id.mycenter_home_ll_order:
			intent = new Intent(context,MyOrderActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_address:
			intent = new Intent(context,AddressActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_qianbao:
			intent = new Intent(context,QianBaoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_mima:
			intent = new Intent(context,PasswordActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_xinjian:
			intent = new Intent(context,XinjianActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_jifen:
			Toast.makeText(context, "Test", 500).show();
			break;
		case R.id.mycenter_home_ll_fenxiang:
			Toast.makeText(context, "Test", 500).show();
			break;
		case R.id.mycenter_home_ll_fuwu:
			intent = new Intent(context,ServiceCenterActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.mycenter_home_ll_update:
			Toast.makeText(context, "Test", 500).show();
			break;
		case R.id.mycenter_home_ll_tuijian:
			Toast.makeText(context, "Test", 500).show();
			intent = new Intent(context,GqHomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;

		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){   
	        if((System.currentTimeMillis()-exitTime) > 2000){  
	            Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
	            exitTime = System.currentTimeMillis();   
	        } else {
	            finish();
	            System.exit(0);
	        }
	        return true;   
	    }
	    return super.onKeyDown(keyCode, event);
	}

}
