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
import com.example.educonsult.R;
import com.example.educonsult.activitys.SCStoreActivity.Myorder;
import com.example.educonsult.activitys.StoreShopBaseActivity;
import com.xunbo.store.MyApplication;
import com.xunbo.store.beans.CenterShopBean;
import com.xunbo.store.myviews.CircleImageView;
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

	public SCStoreAdapter(Context context,ArrayList<CenterShopBean> list,Myorder myorder,boolean isshow){
		this.contexts = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		frame = MyApplication.frame;
		myPDT = new ThreadWithProgressDialog();
		this.myorder = myorder;
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
//			myitem.bond.setVisibility(View.GONE);
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
		if("1".equals(list.get(position).getValidated())){
			myitem.validated.setBackgroundResource(R.drawable.product_zizhi_t);
		}else{
			myitem.validated.setVisibility(View.GONE);
		}
		if("1".equals(list.get(position).getBond())){
			myitem.bond.setBackgroundResource(R.drawable.product_zizhi_r);
		}else{
			myitem.bond.setVisibility(View.GONE);
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
						intent = new Intent(contexts,StoreShopBaseActivity.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						intent.putExtra("storeid", "");
						intent.putExtra("storename", list.get(position).getShopname());
						contexts.startActivity(intent);
					}else {
						Util.ShowToast(contexts, R.string.please_wait);
					}
				}
			});
			

		return convertView;
	}

	class Myitem{
		CircleImageView ic;
		ImageView vip,validated,bond;
		TextView computername,qxsc,instore,talk;

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
