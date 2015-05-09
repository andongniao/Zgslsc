package com.example.educonsult.adapters;

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

import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.activitys.SCStoreActivity.Myorder;

public class SCStoreAdapter extends BaseAdapter implements OnClickListener{
	private Context contexts;
	private ArrayList<String> list;
	private LayoutInflater inflater;
	private Myitem myitem;
	private List<org.osgi.framework.Bundle> bundles=null;
	private FrameworkInstance frame=null;
	private Intent intent;
	private Myorder myorder;

	public SCStoreAdapter(Context context,ArrayList<String> list,Myorder myorder2){
		this.contexts = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		frame = MyApplication.frame;
		this.myorder = myorder2;
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
			convertView = inflater.inflate(R.layout.scstore_item, null);
			myitem = new Myitem();
			myitem.ic=(ImageView)convertView.findViewById(R.id.scstore_item_ic);
			myitem.instore=(TextView)convertView.findViewById(R.id.scstore_itme_instores);
			myitem.instore.setOnClickListener(this);

			myitem.qxsc=(TextView)convertView.findViewById(R.id.scstore_itme_qxsc);
			myitem.qxsc.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					myorder.delte(position);
				}
			});


			myitem.computername = (TextView) convertView.findViewById(R.id.scstore_itme_computername);
			myitem.talk=(TextView)convertView.findViewById(R.id.scstore_itme_talk);
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
			convertView.setTag(myitem);
		}else{
			myitem = (Myitem) convertView.getTag();
		}

		return convertView;
	}

	class Myitem{
		ImageView ic;
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
