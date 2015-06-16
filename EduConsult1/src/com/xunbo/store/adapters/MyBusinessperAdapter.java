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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.activitys.BusinesspartnersInfoActivity;
import com.xunbo.store.activitys.MyBusinessperntActivity.Myorder;

public class MyBusinessperAdapter extends BaseAdapter implements OnClickListener{
	private Context contexts;
	private ArrayList<String> list;
	private LayoutInflater inflater;
	private Myitem myitem;
	private List<org.osgi.framework.Bundle> bundles=null;
	private FrameworkInstance frame=null;
	private Intent intent;
	private Myorder myorder;

	public MyBusinessperAdapter(Context context,ArrayList<String> list,Myorder myorder){
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
			convertView = inflater.inflate(R.layout.mybusinesspartners_item, null);
			myitem = new Myitem();
			myitem.store=(ImageView)convertView.findViewById(R.id.mybusinesspartners_itme_store);
			myitem.store.setOnClickListener(this);
			myitem.talk=(ImageView)convertView.findViewById(R.id.mybusinesspartners_itme_talk);
			myitem.talk.setOnClickListener(this);
			myitem.delect=(Button)convertView.findViewById(R.id.mybusinesspartners_itme_dele);
			myitem.delect.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					myorder.delte(position);
				}
			});
			myitem.xiugai=(Button)convertView.findViewById(R.id.mybusinesspartners_itme_xiugai);
			myitem.xiugai.setOnClickListener(this);
			myitem.computername = (TextView) convertView.findViewById(R.id.mybusinesspartners_item_computername);
			myitem.username = (TextView) convertView.findViewById(R.id.mybusinesspartners_item_username);
			myitem.userbeizhu = (TextView) convertView.findViewById(R.id.mybusinesspartners_item_usernamebz);
			myitem.tel = (TextView) convertView.findViewById(R.id.mybusinesspartners_item_tel);
			myitem.qq=(TextView)convertView.findViewById(R.id.mybusinesspartners_item_qq1);
			myitem.phone=(TextView)convertView.findViewById(R.id.mybusinesspartners_item_phone);
			convertView.setTag(myitem);
		}else{
			myitem = (Myitem) convertView.getTag();
		}

		return convertView;
	}

	class Myitem{
		TextView computername,username,userbeizhu,tel,qq,phone;
		Button delect,xiugai;
		ImageView store,talk;
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
		case R.id.mybusinesspartners_itme_store:
//			intent = new Intent(context,MoneyQueryInfoActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);

			break;
		case R.id.mybusinesspartners_itme_talk:
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
			break;
		case R.id.mybusinesspartners_itme_xiugai:
			intent=new Intent(contexts, BusinesspartnersInfoActivity.class);
			intent.putExtra("bus","1");
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			contexts.startActivity(intent);

			break;
		/*case R.id.mybusinesspartners_itme_dele:
			

			break;*/

		default:
			break;
		}
	}
	
}
