package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.example.educonsult.activitys.ShopcartActivity;
import com.xunbo.store.MyApplication;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.beans.UserBean;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class GqAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ProductBean>list;
	private LayoutInflater inflater;
	private Item item;
	private ThreadWithProgressDialog myPDT;
	private BaseBean bean;
	private UserBean userbean;
	
	public GqAdapter(Context context,ArrayList<ProductBean>list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		userbean=MyApplication.mp.getUser();
		myPDT=new ThreadWithProgressDialog();
	}
	public void SetData(ArrayList<ProductBean>list){
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
			convertView = inflater.inflate(R.layout.base_list_item, null);
			item = new Item();
			item.iv = (ImageView) convertView.findViewById(R.id.base_list_item_iv);
			item.tv_title = (TextView) 
					convertView.findViewById(R.id.base_list_item_tv_title);
			item.tv_address = (TextView) 
					convertView.findViewById(R.id.base_list_item_tv_address);
			item.tv_price = (TextView) 
					convertView.findViewById(R.id.base_list_item_tv_price);
			item.tv_add = (TextView) 
					convertView.findViewById(R.id.base_list_item_tv_add);
			item.tv_util=(TextView)convertView.findViewById(R.id.base_list_item_tv_uilt);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		Util.Getbitmap(item.iv, list.get(position).getThumb());
		item.tv_title.setText(list.get(position).getTitle());
		item.tv_address.setText(list.get(position).getAreaname());
		item.tv_price.setText(list.get(position).getPrice());
		item.tv_util.setText(list.get(position).getUnit());
		item.tv_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(position),R.string.loding);//可取消
				}
			}
		});
		return convertView;
	}
	class Item{
		TextView tv_title,tv_price,tv_address,tv_add,tv_util;
		ImageView iv;
	}
	public class RefeshData implements ThreadWithProgressDialogTask {
		int postion;
		public RefeshData(int postion) {
			this.postion=postion;
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			//			Toast.makeText(context, "cancle", 1000).show();
			Util.ShowToast(context,"添加失败");
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(bean!=null){
				if("200".equals(bean.getCode())){
					//TODO	
					ShopcartActivity.ischange=true;
					Util.ShowToast(context,"添加成功");
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
			Send s = new Send(context);
			//productdetailbean = s.GetProductDetaile();
			// listProductBean=s.getCenterRecommend();
			bean=s.CartAdd(list.get(postion).getItemid(), 1,userbean.getAuthstr());
			return true;
		}
	}

}
