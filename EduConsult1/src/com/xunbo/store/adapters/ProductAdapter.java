package com.xunbo.store.adapters;

import java.util.ArrayList;
import java.util.List;

import org.apkplug.app.FrameworkInstance;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.activitys.SCProductActivity.Myorder;
import com.xunbo.store.activitys.StoreActivity;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.beans.SCProductBean;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.tools.Util;

public class ProductAdapter extends BaseAdapter{
	private Context contexts;
	private ArrayList<SCProductBean> list;
	private LayoutInflater inflater;
	private Myitem myitem;
	private List<org.osgi.framework.Bundle> bundles=null;
	private FrameworkInstance frame=null;
	private Intent intent;
	private Myorder myorder;
	private ThreadWithProgressDialog myPDT;
	private BaseBean baseBean;
	private boolean isshow;
	public ProductAdapter(Context context,ArrayList<SCProductBean> list,Myorder myorder,boolean isshow){
		this.contexts = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		frame = MyApplication.frame;
		this.myorder = myorder;
		myPDT=new ThreadWithProgressDialog();
		this.isshow=isshow;
	}
	public void SetData(ArrayList<SCProductBean> list){
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
			convertView = inflater.inflate(R.layout.scproduct_item, null);
			myitem = new Myitem();
			myitem.ic=(ImageView)convertView.findViewById(R.id.scproduct_item_ic);
			myitem.instore=(TextView)convertView.findViewById(R.id.scproduct_instores);
			myitem.qxsc=(TextView)convertView.findViewById(R.id.scproduct_qxsc);
			myitem.computername = (TextView) convertView.findViewById(R.id.scproduct_item_computername);
			myitem.productname = (TextView) convertView.findViewById(R.id.scproduct_item_productname);
			myitem.money = (TextView) convertView.findViewById(R.id.scproduct_item_money);
			myitem.time = (TextView) convertView.findViewById(R.id.scproduct_time);
			convertView.setTag(myitem);
		}else{
			myitem = (Myitem) convertView.getTag();
		}
		Util.Getbitmap(myitem.ic, list.get(position).getThumb());
		myitem.computername.setText(list.get(position).getCompany());
		myitem.productname.setText(list.get(position).getTitle());
		myitem.money.setText(list.get(position).getPrice());
		myitem.time.setText(list.get(position).getTime());
		myitem.instore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isshow){
					
					intent = new Intent(contexts,StoreActivity.class);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					intent.putExtra("storeid", "");
					intent.putExtra("storename", list.get(position).getShopname());
					contexts.startActivity(intent);
				}
			}
		});
		myitem.qxsc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isshow){

					if(Util.detect(contexts)){
						myPDT.Run(contexts, new RefeshData(position),R.string.loding);//可取消
					}else{
						Util.ShowToast(contexts, R.string.net_is_eor);
					}
				}
				
			}
		});

		return convertView;
	}

	class Myitem{
		ImageView ic;
		TextView computername,productname,money,time,qxsc,instore;
		
	}
	public class RefeshData implements ThreadWithProgressDialogTask {
		private int position;
		public RefeshData(int position) {
			this.position=position;
		}

		@Override
		public boolean OnTaskDismissed() {
			
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			if(baseBean!=null){
				if("200".equals(baseBean.getCode())){
					myorder.delte(position);
				}else if("300".equals(baseBean.getCode())){
					myorder.finish();
				}else{
					Util.ShowToast(contexts, baseBean.getMsg());
				}
			
			}else{
				Util.ShowToast(contexts, R.string.net_is_eor);
			}
			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			PostHttp p=new PostHttp(contexts);
			baseBean=p.Shoucang(2, 1, Integer.parseInt(list.get(position).getCollected()), MyApplication.mp.getUser().getAuthstr());
			return true;
		}
	}
	

}
