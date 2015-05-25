package com.example.educonsult.activitys;

import java.util.List;

import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.beans.RefundInfoDetaileBean;
import com.example.educonsult.net.PostHttp;
import com.example.educonsult.tools.Util;
import com.testin.agent.TestinAgent;

public class ApplyInfoActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private Intent intent;
	private TextView tv_name,tv_cname,tv_money,tv_time,tv_way,tv_applying,tv_why,tv_text;
	private LinearLayout ll_talk;
	private int time=5;
	private List<org.osgi.framework.Bundle> bundles=null;
	private FrameworkInstance frame=null;
	private ThreadWithProgressDialog myPDT;
	private RefundInfoDetaileBean bean;
	private String itemid,authstr;


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		setTopLeftTv(R.string.applyinfo_title);
		setContentXml(R.layout.applyinfo);
		init();
		if(Util.detect(context)){
		myPDT.Run(context, new RefeshData(),R.string.loding);//��ȡ��
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}



	private void init() {
		TestinAgent.init(this);
		context = this;
		itemid = getIntent().getStringExtra("itemid");
		authstr = MyApplication.mp.getUser().getAuthstr();
		//		private TextView tv_name,tv_cname,tv_money,
		//		tv_time,tv_way
		//		,tv_talk,tv_applying,tv_why;
		//		private LinearLayout ll_talk;
		frame = MyApplication.frame;

		tv_name=(TextView)findViewById(R.id.applyinfo_shopkeeper);
		tv_text=(TextView)findViewById(R.id.applyinfo_apptexttime);
		tv_cname=(TextView)findViewById(R.id.applyinfo_computername);
		tv_money=(TextView)findViewById(R.id.applyinfo_applymoney);
		tv_time=(TextView)findViewById(R.id.applyinfo_applytime);
		tv_way=(TextView)findViewById(R.id.applyinfo_applyway);
		tv_applying=(TextView)findViewById(R.id.applyinfo_applying);
		ll_talk=(LinearLayout)findViewById(R.id.applyinfo_talk_lin);
		ll_talk.setOnClickListener(this);
		tv_why=(TextView)findViewById(R.id.applyinfo_applywhy);
		//		tv_text.setText("����"+time+"�����̼�δ�����˿����뽫��ɲ��˿����������п��С���");

		myPDT = new ThreadWithProgressDialog();
		String  msg = getResources().getString(R.string.loding);
	}
	public void startor(List<org.osgi.framework.Bundle> list){
		org.osgi.framework.Bundle bundle=list.get(1);
		if(bundle.getState()!=bundle.ACTIVE){
			//�жϲ���Ƿ�������
			try {
				bundle.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(bundle.getBundleActivity()!=null){
			Intent i=new Intent();
			i.setClassName(context, bundle.getBundleActivity().split(",")[0]);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}else{

			Toast.makeText(context, "�ò��û������BundleActivity",
					Toast.LENGTH_SHORT).show();
		}
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {



		case R.id.applyinfo_talk_lin:
			//�Ѱ�װ����б�
//			bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
//			BundleContext context = frame.getSystemBundleContext();
//			for(int i=0;i<context.getBundles().length;i++)
//			{
//				//��ȡ�Ѱ�װ���
//				bundles.add(context.getBundles()[i]);        	        
//			}
//
//			//BundleContext context =frame.getSystemBundleContext();
//			startor(bundles);
			ShowDialog();
			
			
			break;


		}
	}


	// ����
	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//����ȡ��
			//				Toast.makeText(context, "cancle", 1000).show();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//������ɺ�
			if(bean!=null){
				if("200".equals(bean.getCode())){
					tv_cname.setText(bean.getCompany());
					tv_name.setText(bean.getTruename());
					tv_time.setText(bean.getUpdatetime());
					tv_applying.setText(bean.getStatus());
					tv_money.setText(bean.getMoney());
					tv_why.setText(bean.getContent());
				}else if("300".equals(bean.getCode())){
					MyApplication.mp.setlogin(false);
					Util.ShowToast(context, R.string.login_out_time);
					intent = new Intent(context,LoginActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(intent);
					finish(); 
				}else{
					Util.ShowToast(context, bean.getMsg());
				}
				
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			
			
			
			return true;
		}

		@Override
		public boolean TaskMain() {
			// ����
			PostHttp p = new PostHttp(context);
			bean = p.Order_refundinfo(itemid, authstr);
			return true;
		}
	}


	private void ShowDialog() {
		AlertDialog alertDialog;
		String title = "";
			title = "�Ƿ񲦴�绰?";
		alertDialog = new AlertDialog.Builder(this)
		.setTitle(title)
		.setIcon(null)
		.setPositiveButton("ȡ��",
				new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog,
					int which) {
			}
		})
		.setNegativeButton("ȷ��",
				new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog,
					int which) {
					Intent phoneIntent = new Intent(
							"android.intent.action.CALL",
							Uri.parse("tel:"
									+ 86666666));
					startActivity(phoneIntent);
//					Uri uri = Uri.parse("smsto:"
//							+ MyApplication.smsnumber);
//					Intent ii = new Intent(
//							Intent.ACTION_SENDTO, uri);
//					ii.putExtra("sms_body", "");
//					startActivity(ii);
			}
		}).create();
		alertDialog.show();
	}

}
