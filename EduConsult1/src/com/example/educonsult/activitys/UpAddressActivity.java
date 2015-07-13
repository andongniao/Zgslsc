package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.adapters.UpaddressAdapter;
import com.xunbo.store.beans.AddressBean;
import com.xunbo.store.beans.AreaBean;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.beans.ListAddressBean;
import com.xunbo.store.beans.ListAreaBean;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.tools.Util;

public class UpAddressActivity extends BaseActivity{
	private ListView lv;
	private Context context;
	private ArrayList<Integer> list;
	private UpaddressAdapter adapter;
	private ImageView iv_top_right;
	private Intent intent; 
	private Util u;
	private String filename;
	private ListAreaBean lare;
	private ArrayList<AreaBean>listsheng,listshi,listxian;
	private ThreadWithProgressDialog myPDT;
	private ListAddressBean listAddressBean;
	private ArrayList<AddressBean> addressBeans;
	private UpAddress upAddress;
	private BaseBean bean;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		topRightRVisible();
//		iv_top_right = (ImageView) getTopRightView();
//		iv_top_right.setBackgroundResource(R.drawable.address_guanli_btn);
		topRightTGone();
		setTitleTxt(R.string.address_title);
		setContentXml(R.layout.up_address_layout);
		init();
		addlistener();

	}


	private void init() {
		TestinAgent.init(this);
		context = this;
		myPDT=new ThreadWithProgressDialog();
		upAddress = new UpAddress() {

			@Override
			public void setAddidde(int index) {
				
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(index),R.string.loding);//可取消
				}
			}
		};
		list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		//		le b=new Bundle();
		//		b.putSerializable("orderbundle", listAddressBean);
		//		id.putExtra("upaddress", b);
		intent=getIntent();
		Bundle b=intent.getBundleExtra("upaddress");

		listAddressBean=(ListAddressBean)b.getSerializable("orderbundle");
		if(listAddressBean==null){
			addressBeans=new ArrayList<AddressBean>();
		}else{
			addressBeans=listAddressBean.getList();
		}
		lv = (ListView) findViewById(R.id.up_address_lv);
		adapter = new UpaddressAdapter(context, addressBeans, 0,upAddress);
		lv.setAdapter(adapter);
	}
	private void addlistener() {
//		iv_top_right.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				intent = new Intent(context,AddressActivity.class);
//				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//				startActivity(intent);
//			}
//		});
//		lv.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				adapter.SetData(arg2);
//				adapter.notifyDataSetChanged();
//			}
//		});
	}

	public interface UpAddress{
		void setAddidde(int index);
	}
	
	
	// 任务
		public class RefeshData implements ThreadWithProgressDialogTask {
			private int index;
			
			public RefeshData(int index) {
				this.index = index;
			}

			@Override
			public boolean OnTaskDismissed() {
				//任务取消
				//				Toast.makeText(context, "cancle", 1000).show();
				return false;
			}

			@Override
			public boolean OnTaskDone() {
				//任务完成后
				if(bean!=null){
					if("200".equals(bean.getCode())){
						OrderActivity.isinit =true;
						for(int i=0;i<addressBeans.size();i++){
							addressBeans.get(i).setIsdefault("0");
						}
						addressBeans.get(index).setIsdefault("1");
						if(adapter!=null){
						adapter.SetData(addressBeans);
						adapter.notifyDataSetChanged();
						}else{
							adapter = new UpaddressAdapter(context, addressBeans, 0,upAddress);
							lv.setAdapter(adapter);
						}
						
					}else if("300".equals(bean.getCode())){
						intent = new Intent(context,LoginActivity.class);
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
				// 访问
				PostHttp p = new PostHttp(context);
				bean = p.SetDetaultAddress(addressBeans.get(index), MyApplication.mp.getUser().getAuthstr());

				return true;
			}
		}

}
