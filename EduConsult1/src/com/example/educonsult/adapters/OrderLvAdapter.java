package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.example.educonsult.R;
import com.example.educonsult.beans.CouponBean;
import com.example.educonsult.beans.ShopBean;
import com.example.educonsult.beans.ShopItemBean;
import com.example.educonsult.tools.Util;

public class OrderLvAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ShopBean>list;
	private LayoutInflater inflater;
	private Item item;
    private int index,num;
	private PopupWindow popu;
	private View v_fenlei;
	private ListView list_2,lv_l;
	private TextItemListAdapter adapter_r;
	private ArrayList<String> Strlist;
	private ArrayList<CouponBean> coupons;
	public OrderLvAdapter(Context context,ArrayList<ShopBean>list, int index){
		this.context = context;
		this.list = list;
		this.index=index;
		inflater = LayoutInflater.from(context);
	}
	public void SetData(ArrayList<ShopBean>list){
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
			convertView = inflater.inflate(R.layout.order_two_lv_item, null);
			item = new Item();
			//TODO 
			item.iv = (ImageView) convertView.findViewById(R.id.order_two_lv_item_iv);
			item.iv_uhui = (ImageView) convertView.findViewById(R.id.order_two_lv_item_iv_uhui);
			item.tv_title = (TextView) convertView.findViewById(R.id.order_two_lv_item_tv_title);
			item.tv_price = (TextView) convertView.findViewById(R.id.order_two_lv_item_tv_price);
			item.tv_num = (TextView) convertView.findViewById(R.id.order_two_lv_item_tv_num);
			item.tv_zongjia = (TextView) convertView.findViewById(R.id.order_two_lv_item_tv_zongjia);
			item.lin=(LinearLayout)convertView.findViewById(R.id.order_two_lv_item_lin_youhui);
			
			item.tv_uhui = (TextView) convertView.findViewById(R.id.order_two_lv_item_tv_uhui);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		ShopBean sb = (ShopBean) list.get(index);
		ArrayList<ShopItemBean> l = sb.getMall();
        
		final ShopItemBean b = (ShopItemBean) l.get(position);
		
		coupons=b.getCoupon();
		item.tv_num.setText(b.getNum());
		item.tv_price.setText("гд"+b.getPrice());
		item.tv_title.setText(b.getTitle());
		int i_num=b.getNum();
		float i_price=Float.parseFloat(b.getPrice());
		float i_allmoney=i_num*i_price;
		item.tv_zongjia.setText("гд"+i_allmoney);
		try {
			item.iv.setImageBitmap(Util.getBitmapForNet(b.getThumb()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		item.lin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Strlist=new ArrayList<String>();
				for(int i=0;i<coupons.size();i++){
					Strlist.add(coupons.get(i).getValue());
				}
				setpopuwindow(Strlist, item.lin);
				item.tv_uhui.setText(coupons.get(num).getValue());
				popu.dismiss();
			}
		});
		
		return convertView;
	}
	private void setpopuwindow(final ArrayList<String> list,LinearLayout lin){
		adapter_r = new TextItemListAdapter(context, list);
		lv_l.setAdapter(adapter_r);
		lv_l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
					num=arg2;
					popu.dismiss();
				
			}
		});
		popu = new PopupWindow(v_fenlei, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setOutsideTouchable(true);
		popu.showAsDropDown(lin);
		
		
	}
	class Item{
		TextView tv_title,tv_price,tv_zongjia,tv_num,tv_uhui;
		ImageView iv,iv_uhui;
		LinearLayout lin;
	}

}
