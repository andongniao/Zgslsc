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

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.activitys.LoginActivity;
import com.example.educonsult.activitys.SCStoreActivity.Myorder;
import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.CenterShopBean;
import com.example.educonsult.net.PostHttp;
import com.example.educonsult.tools.Util;

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
	private BaseBean baseBean;

	public SCStoreAdapter(Context context,ArrayList<CenterShopBean> list,Myorder myorder2){
		this.contexts = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		frame = MyApplication.frame;
		myPDT = new ThreadWithProgressDialog();
		this.myorder = myorder2;
	}
	public void SetData(ArrayList<CenterShopBean> list){
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
			myitem.computername = (TextView) convertView.findViewById(R.id.scstore_itme_computername);
			myitem.talk=(TextView)convertView.findViewById(R.id.scstore_itme_talk);
			
			convertView.setTag(myitem);
		}else{
			myitem = (Myitem) convertView.getTag();
			Util.Getbitmap(myitem.ic, list.get(position).getThumb());
			myitem.computername.setText(list.get(position).getCompany());
			myitem.qxsc.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
						if(Util.detect(contexts)){
							//			myPDT.Run(context, new RefeshData(bean.getType(),bean.getAuthstr()),msg,false);//不可取消
							myPDT.Run(contexts, new RefeshData(position),R.string.loding);//不可取消
						}else{
							Util.ShowToast(contexts, R.string.net_is_eor);
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
	public class RefeshData implements ThreadWithProgressDialogTask {
		private int position;
		public RefeshData(int position){
			this.position=position;
		}

		@Override
		public boolean TaskMain() {
			// TODO Auto-generated method stub
			PostHttp p=new PostHttp(contexts);
			baseBean=p.Shoucang(2,2,Integer.parseInt(list.get(position).getCid()));
			return true;
		}

		@Override
		public boolean OnTaskDismissed() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			if(baseBean!=null){
				String code = baseBean.getCode();
				String m = baseBean.getMsg();
				if("200".equals(code)){
					myorder.delte(position);
				}else if("300".equals(code)){
					myorder.finish();
				}else{
					if(Util.IsNull(m)){
						Util.ShowToast(contexts, m);
					}
				}	
			}else{
				Util.ShowToast(contexts, R.string.net_is_eor);
			}
			return true;

		}

	}

}
