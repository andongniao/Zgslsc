package com.xunbo.store.adapters;

import java.util.ArrayList;
import java.util.List;

import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.baidu.mobstat.GetReverse;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.activitys.SCStoreActivity.Myorder;
import com.xunbo.store.activitys.StoreActivity;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.beans.CenterShopBean;
import com.xunbo.store.myviews.CircleImageView;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.tools.Util;

public class SCStoreAdapter extends BaseAdapter implements OnClickListener{
	private Context contexts;
	private ArrayList<CenterShopBean> list;
	private LayoutInflater inflater;
	private Myitem myitem;
	private List<org.osgi.framework.Bundle> bundles=null;
	private FrameworkInstance frame=null;
	private Intent intent;
	private Myorder myorder;
	private ThreadWithProgressDialog myPDT;
	
	private boolean isshow;

	public SCStoreAdapter(Context context,ArrayList<CenterShopBean> list,Myorder myorder2,boolean isshow){
		this.contexts = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		frame = MyApplication.frame;
		myPDT = new ThreadWithProgressDialog();
		this.myorder = myorder2;
		this.isshow=isshow;
	}
	public void SetData(ArrayList<CenterShopBean> list){
		this.list = list;
	}
	public void SetIsShow(boolean isshow){
		this.isshow=isshow;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list!=null?list.size():0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = inflater.inflate(R.layout.scstore_item, null);
			myitem = new Myitem();
			myitem.ic=(CircleImageView)convertView.findViewById(R.id.scstore_item_ic);
			myitem.instore=(TextView)convertView.findViewById(R.id.scstore_itme_instores);
			
			myitem.vip=(ImageView)convertView.findViewById(R.id.scstore_itme_vip);
			myitem.validated=(ImageView)convertView.findViewById(R.id.scstore_itme_zizhi);
			myitem.bond=(ImageView)convertView.findViewById(R.id.scstore_itme_bond);
			myitem.qxsc=(TextView)convertView.findViewById(R.id.scstore_itme_qxsc);
			myitem.computername = (TextView) convertView.findViewById(R.id.scstore_itme_computername);
			myitem.talk=(TextView)convertView.findViewById(R.id.scstore_itme_talk);
			myitem.bond.setVisibility(View.GONE);
			convertView.setTag(myitem);
		}else{
			myitem = (Myitem) convertView.getTag();
		}
		Util.Getbitmap(myitem.ic, list.get(position).getThumb());
		if("1".equals(list.get(position).getVip())){
			myitem.vip.setBackgroundResource(R.drawable.scv1);
		}else if("2".equals(list.get(position).getVip())){
			myitem.vip.setBackgroundResource(R.drawable.scv2);
		}else{
			myitem.vip.setVisibility(View.GONE);
		}
		if("1".equals(list.get(position).getValidated())||"1".equals(list.get(position).getBond())){
			myitem.validated.setBackgroundResource(R.drawable.sczizhi);
		}else{
			myitem.validated.setVisibility(View.GONE);
		}
//		if("1".equals(list.get(position).getBond())){
//			myitem.bond.setBackgroundResource(R.drawable.sczizhi);
//		}else{
//			myitem.bond.setVisibility(View.GONE);
//		}
		myitem.computername.setText(list.get(position).getCompany());
			
			myitem.qxsc.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!isshow){
						myorder.delte(position);
					}else{
						Util.ShowToast(contexts, R.string.please_wait);
					}
				}
			});
			myitem.instore.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(!isshow){
						intent = new Intent(contexts,StoreActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("storeid", list.get(position).getCollected());
						intent.putExtra("storename", "");
						contexts.startActivity(intent);
					}else {
						Util.ShowToast(contexts, R.string.please_wait);
					}
				}
			});
			
		myitem.talk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//已安装插件列表
				bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
				BundleContext context = frame.getSystemBundleContext();
				for(int i=0;i<context.getBundles().length;i++)
				{
					//获取已安装插件
					bundles.add(context.getBundles()[i]);        	        
				}
				
				//BundleContext context =frame.getSystemBundleContext();
				startor(bundles);
			}
		});

		return convertView;
	}

	class Myitem{
		CircleImageView ic;
		ImageView vip,validated,bond;
		TextView computername,qxsc,instore,talk;

	}

	public void startor(List<org.osgi.framework.Bundle> list){
		org.osgi.framework.Bundle bundle=list.get(1);
		if(bundle.getState()!=bundle.ACTIVE){
			//判断插件是否已启动
			try {
				bundle.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(bundle.getBundleActivity()!=null){
			//			Toast.makeText(context, "启动"+bundle.getBundleActivity().split(",")[0],
			//				     Toast.LENGTH_SHORT).show();
			Intent i=new Intent();
			i.setClassName(contexts, bundle.getBundleActivity().split(",")[0]);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			contexts.startActivity(i);
		}else{

			Toast.makeText(contexts, "该插件没有配置BundleActivity",
					Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.scproduct_instores:
			//			intent = new Intent(context,MoneyQueryInfoActivity.class);
			//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//			startActivity(intent);

			break;


			/*case R.id.mybusinesspartners_itme_dele:


			break;*/

		default:
			break;
		}
	}

}
