package com.xunbo.store.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.adapters.MyCenterTuijianAdapter;
import com.xunbo.store.beans.ListProductBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class MyCenterTuijianActivity extends BaseActivity implements OnClickListener{
	
	private Context context;
	private GridView gridView;
	
	private Intent intent;
	private RelativeLayout rl_l,rl_r;
	public static boolean isread;
	public View ll_gqtwo_popu;
	private LinearLayout ll_not;
	private MyCenterTuijianAdapter adapter;
	private ThreadWithProgressDialog myPDT;
	private ListProductBean listProductBean;
	private ArrayList<ProductBean> list;
	private MyCenterTuijian myCenterTuijian;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		
		topRightTGone();
		
		setTitleTxt(R.string.mycenter_tuijian_title);
		setContentXml(R.layout.mycenter_tuijian);
		
		init();
		addlistener();
		//		/**********set***********/
//		UserBean b = new UserBean();
//		b.setName("121");
//		String l = b.toString();
//		MyApplication.bean = b;
//		/**********get***********/
//		UserBean a = MyApplication.bean;
//		String s = a.getName();





	}

	private void addlistener() {
		
		

	
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toproduct(list.get(arg2));
			}
		});

	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		isread = false;
		myPDT=new ThreadWithProgressDialog();
		
        ll_not=(LinearLayout)findViewById(R.id.mycenter_tuijian_ll_isnull);
        ll_not.setVisibility(View.GONE);
		gridView = (GridView) findViewById(R.id.mycenter_tuijian_gv);
		/*list = new ArrayList<Integer>();
		for(int i=0;i<10;i++){
			list.add(i);
		}*/
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		}
		
		//Util.SetRedNum(context, rl_l, 0);
		myCenterTuijian=new MyCenterTuijian() {
			
			@Override
			public void finish() {
				// TODO Auto-generated method stub
				MyApplication.mp.setlogin(false);
				Util.ShowToast(context, R.string.login_out_time);
				intent = new Intent(context,LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish(); 
			}
		};

	}
	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			//			Toast.makeText(context, "cancle", 1000).show();
			finish();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(listProductBean!=null){
				if("200".equals(listProductBean.getCode())){
					//TODO	
					//					private MallInfoBean mallinfo;
					//					private ArrayList<ProductBean> recommend;
					//					private ArrayList<ProductBean> buyedlist;
					initDate();
				}else{
					Util.ShowToast(context, listProductBean.getMsg());
				}
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}



			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			Send s = new Send(context);
			//productdetailbean = s.GetProductDetaile();
			 listProductBean=s.getCenterRecommend();
			return true;
		}
	}
	private void initDate(){
		list=listProductBean.getList();
		adapter = new MyCenterTuijianAdapter(context, list,myCenterTuijian);
		gridView.setAdapter(adapter);
		//gridView.setFocusable(false);
		if(list==null){
			gridView.setVisibility(View.GONE);
		}{
			ll_not.setVisibility(View.GONE);
		}
	}

	@Override
	public void onClick(View v) {
		
	}
	
	private void Toproduct(ProductBean bean){
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Bundle b=new Bundle();
		b.putSerializable("product", bean);
		intent.putExtra("productbundle", b);
		startActivity(intent);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		if(isread){
			Util.SetRedGone(context, rl_l);
			isread = false;
		}
	}
	public interface MyCenterTuijian{
		void finish();
		
	}
	
	
}
