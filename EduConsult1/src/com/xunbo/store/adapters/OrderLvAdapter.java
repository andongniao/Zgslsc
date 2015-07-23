package com.xunbo.store.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educonsult.R;
import com.xunbo.store.beans.CouponBean;
import com.xunbo.store.beans.ShopBean;
import com.xunbo.store.beans.ShopItemBean;
import com.xunbo.store.tools.Util;

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
	private ArrayList<String> couponlist;
	private HashMap<Integer, ArrayList<String>> couponmap;
	private double i_allmoney;
	public OrderLvAdapter(Context context,ArrayList<ShopBean>list, int index,HashMap<Integer, ArrayList<String>> couponmap){
		this.context = context;
		this.list = list;
		this.index=index;
		inflater = LayoutInflater.from(context);
		coupons=new ArrayList<CouponBean>();
		this.couponmap=couponmap;
		couponlist=couponmap.get(index);
	}
	public void SetData(ArrayList<ShopBean>list){
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list!=null?list.get(index).getMall().size():0;
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
			item.tv_unit = (TextView) convertView.findViewById(R.id.order_two_lv_item_tv_unit);
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
		
		
		item.tv_num.setText("共"+b.getNum()+"件");
		item.tv_price.setText("￥"+b.getPrice());
		item.tv_unit.setText(b.getUnit());
		item.tv_title.setText(b.getTitle());
		int i_num=b.getNum();
		double i_price=Float.parseFloat(b.getPrice());
		i_allmoney=i_num*i_price;
		item.tv_zongjia.setText("￥"+i_allmoney);
		Util.Getbitmap(item.iv, b.getThumb());
//		try {
////			item.iv.setImageBitmap(Util.getBitmapForNet(b.getThumb()));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		coupons=b.getCoupons();
		
			item.lin.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if(coupons!=null&&coupons.size()>=0){
						Strlist=new ArrayList<String>();
						for(int i=0;i<coupons.size();i++){
							Strlist.add("消费金额满"+coupons.get(i).getPayment()+"元减"+coupons.get(i).getValue()+"元");
						}
						setpopuwindow(Strlist, item.lin,position);
						item.tv_uhui.setText(coupons.get(num).getValue());
						popu.dismiss();
					}else{
						item.tv_uhui.setText("");
						Util.ShowToast(context, "您还没有符合条件的优惠券哦！");
					}
				}
			});
		
		
		
		return convertView;
	}
	private void setpopuwindow(final ArrayList<String> list,LinearLayout lin,final int position){
		adapter_r = new TextItemListAdapter(context, list);
		lv_l.setAdapter(adapter_r);
		
		lv_l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
					num=arg2;
					if(i_allmoney>=Double.parseDouble(coupons.get(arg2).getPayment())){
						
						for(int j=0;j<couponlist.size();j++){
							if(j==position){
								couponlist.add(j, coupons.get(arg2).getId());
							}else{
								couponlist.add(coupons.get(arg2).getId());
							}
						}
						for(int i=0;i<coupons.size();i++){
							coupons.get(i).setIsck(false);
						}
						coupons.get(arg2).setIsck(true);
						popu.dismiss();
					}else{
						Util.ShowToast(context, "亲，消费金额不够哦~");
					}
				
			}
		});
		popu = new PopupWindow(v_fenlei, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setOutsideTouchable(true);
		popu.showAsDropDown(lin);
		
		
	} 
	class Item{
		TextView tv_title,tv_price,tv_zongjia,tv_num,tv_uhui,tv_unit;
		ImageView iv,iv_uhui;
		LinearLayout lin;
	}

}
