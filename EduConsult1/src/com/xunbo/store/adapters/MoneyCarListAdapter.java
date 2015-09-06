package com.xunbo.store.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.example.educonsult.activitys.LoginActivity;
import com.example.educonsult.activitys.MoneyWithdrawalActivity.RefeshData;
import com.xunbo.store.MyApplication;
import com.xunbo.store.beans.MoneyTxBean;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;

public class MoneyCarListAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<MoneyTxBean>list;
	private LayoutInflater inflater;
	private Item item;
	private int index;


	public MoneyCarListAdapter(Context context,ArrayList<MoneyTxBean>list,int index){
		this.context = context;
		this.list = list;
		this.index = index;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(int index){
		this.index = index;
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
			convertView = inflater.inflate(R.layout.moneycar_list_item, null);
			item = new Item();
			item.ic=(ImageView)convertView.findViewById(R.id.moneycar_list_item_ic);
			item.tv_id = (TextView) convertView.findViewById(R.id.moneycar_list_item_carid);
			convertView.setTag(item);
			
		}else{
			item = (Item) convertView.getTag();
		}
		
		item.tv_id.setText(list.get(0).getBindAccNum());
		if("华夏银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.huaxia);
		}else if("河北银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.hebei);
		}else if("中国银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.zhongguo);
		}else if("中国工商银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.gongshang);
		}else if("中国农业银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.nongye);
		}else if("中国建设银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.jianshe);
		}else if("交通银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.jiaotong);
		}else if("招商银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.zhaoshang);
		}else if("渤海银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.bohai);
		}else if("广发银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.guangfa);
		}else if("平安银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.pingan);
		}else if("中信实业银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.zhongxin);
		}else if("中国广大银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.guangda);
		}else if("中国民生银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.minsheng);
		}else if("兴业银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.xingye);
		}else if("上海浦东发展银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.pufa);
		}else if("中国邮政储蓄银行".equals(list.get(0).getBindOpenBank())){
			item.ic.setBackgroundResource(R.drawable.youchu);
		}
		
		
		
		return convertView;
	}
	class Item{
		TextView tv_id;
		ImageView ic;
	}
	
	

}
