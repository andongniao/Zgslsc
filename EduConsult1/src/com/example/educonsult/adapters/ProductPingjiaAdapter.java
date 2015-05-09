package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.beans.CompanyBean;
import com.example.educonsult.beans.ProductBean;

public class ProductPingjiaAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ProductBean> list;
	private LayoutInflater inflater;
	private Myitem myitem;
	private int star=3;


	public ProductPingjiaAdapter(Context context,ArrayList<ProductBean> list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;//list!=null?list.size():0;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = inflater.inflate(R.layout.product_datail_pingjia_item, null);
			myitem = new Myitem();
			myitem.iv=(ImageView)convertView.findViewById(R.id.product_datail_pingjia_time_head);
			myitem.starnum=(ImageView)convertView.findViewById(R.id.product_detail_pingjia_item_star);
			myitem.tv_name=(TextView)convertView.findViewById(R.id.product_datail_pingjia_time_name);
			myitem.tv_text=(TextView)convertView.findViewById(R.id.product_datail_pingjia_item_take);
			myitem.tv_time=(TextView)convertView.findViewById(R.id.product_detail_pingjia_item_time);
			myitem.tv_num=(TextView)convertView.findViewById(R.id.product_detail_pingjia_item_num);
			convertView.setTag(myitem);
		}else{
			myitem = (Myitem) convertView.getTag();
		}
		//		ImageView v = new ImageView(context);
		//myitem.iv.setBackgroundResource(R.drawable.ic_launcher);
		switch (star) {
		case 1:
			myitem.starnum.setBackgroundResource(R.drawable.onestar);
			break;
		case 3:
			myitem.starnum.setBackgroundResource(R.drawable.threestar);
			break;
		case 5:
			myitem.starnum.setBackgroundResource(R.drawable.fivestar);
			break;

		default:
			break;
		}
		return convertView;
	}

	class Myitem{
		TextView tv_name,tv_text,tv_time,tv_num;
		ImageView iv,starnum;
	}
}
