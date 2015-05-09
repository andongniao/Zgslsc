package com.example.educonsult.adapters;

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

import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.activitys.SCProductActivity.Myorder;

public class ProductAdapter extends BaseAdapter implements OnClickListener{
	private Context contexts;
	private ArrayList<String> list;
	private LayoutInflater inflater;
	private Myitem myitem;
	private List<org.osgi.framework.Bundle> bundles=null;
	private FrameworkInstance frame=null;
	private Intent intent;
	private Myorder myorder;

	public ProductAdapter(Context context,ArrayList<String> list,Myorder myorder){
		this.contexts = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		frame = MyApplication.frame;
		this.myorder = myorder;
	}
	public void SetData(ArrayList<String> list){
		this.list = list;
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
			myitem.instore.setOnClickListener(this);
			
			myitem.qxsc=(TextView)convertView.findViewById(R.id.scproduct_qxsc);
			myitem.qxsc.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					myorder.delte(position);
				}
			});
		
			myitem.computername = (TextView) convertView.findViewById(R.id.scproduct_item_computername);
			myitem.productname = (TextView) convertView.findViewById(R.id.scproduct_item_productname);
			myitem.money = (TextView) convertView.findViewById(R.id.scproduct_item_money);
			myitem.time = (TextView) convertView.findViewById(R.id.scproduct_time);
			convertView.setTag(myitem);
		}else{
			myitem = (Myitem) convertView.getTag();
		}

		return convertView;
	}

	class Myitem{
		ImageView ic;
		TextView computername,productname,money,time,qxsc,instore;
		
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
