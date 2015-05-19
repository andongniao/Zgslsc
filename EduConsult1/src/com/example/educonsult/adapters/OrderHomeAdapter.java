package com.example.educonsult.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.activitys.ShopcartActivity;
import com.example.educonsult.activitys.ShopcartActivity.shop;
import com.example.educonsult.beans.ExpressBean;
import com.example.educonsult.beans.ShopBean;
import com.example.educonsult.myviews.MyListview;
import com.example.educonsult.myviews.SwipeMenuListView.SwipeMenu;
import com.example.educonsult.myviews.SwipeMenuListView.SwipeMenuCreator;
import com.example.educonsult.myviews.SwipeMenuListView.SwipeMenuItem;
import com.example.educonsult.myviews.SwipeMenuListView.SwipeMenuListView;
import com.example.educonsult.myviews.SwipeMenuListView.SwipeMenuListView.OnMenuItemClickListener;
import com.example.educonsult.tools.UITools;
import com.example.educonsult.tools.Util;

public class OrderHomeAdapter extends BaseAdapter{
	private Context context;
	private ArrayList<ShopBean>list;
	private LayoutInflater inflater;
	private Item item;
	private OrderLvAdapter adapter;
	private int n,num;
	private ArrayList<ExpressBean> express;
	private ArrayList<String> Strlist;
	private PopupWindow popu;
	private View v_fenlei;
	private ListView list_2,lv_l;
	private TextItemListAdapter adapter_r;

	public OrderHomeAdapter(Context context,ArrayList<ShopBean>list){
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		express=new ArrayList<ExpressBean>();
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

	@Override
	public View getView(final int index, View convertView, ViewGroup parent) {
		if(convertView==null){
			convertView = inflater.inflate(R.layout.order_home_lv_item, null);
			item = new Item();
			item.lv = (MyListview) convertView.findViewById(R.id.order_home_lv_item_lv);
			item.et = (EditText) convertView.findViewById(R.id.order_home_lv_item_et_liuyan);
			item.tv_title = (TextView) convertView.findViewById(R.id.order_home_lv_item_tv_title);
			item.tv_num = (TextView) convertView.findViewById(R.id.order_home_lv_item_tv_num);
			item.tv_heji = (TextView) convertView.findViewById(R.id.order_home_lv_item_tv_heji);
			item.tv_peisong = (TextView) convertView.findViewById(R.id.order_home_lv_item_tv_peisong);
			item.tv_online = (TextView) convertView.findViewById(R.id.order_home_lv_item_tv_on_line);
			item.tv_qq = (TextView) convertView.findViewById(R.id.order_home_lv_item_tv_qq);
			item.lin=(LinearLayout)convertView.findViewById(R.id.order_home_lv_item_lin_peisong);
			convertView.setTag(item);
		}else{
			item = (Item) convertView.getTag();
		}
		item.tv_online.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
		item.tv_qq.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线
//		ArrayList<Integer> l1 = new ArrayList<Integer>();
//		l1.add(1);
//		l1.add(2);
//		ArrayList<Integer> l2 = new ArrayList<Integer>();
//		l2.add(1);
		ShopBean s=list.get(index);
		item.tv_title.setText(s.getCompany());
		item.et.setText(s.getNote());
		
		express=s.getExpress();//运费模板
		if(express!=null && express.size()>=0){

			item.lin.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Strlist=new ArrayList<String>();
					for(int i=0;i<express.size();i++){
						Strlist.add(express.get(i).getTitle());
					}
					setpopuwindow(Strlist, item.lin);
					item.tv_peisong.setText(express.get(num).getTitle());
					popu.dismiss();
				}
			});
		
		}else{
			item.tv_peisong.setText("免运费");
		}
		float sum=0,i_price;
		int i_num,n=0;
		
		
		for(int i=0;i<s.getMall().size();i++){
			i_num=s.getMall().get(i).getNum();
			i_price=Float.parseFloat(s.getMall().get(i).getPrice());
			sum=sum+i_num*i_price;
			n++;
		}
		item.tv_heji.setText("￥"+sum);
		//item.tv_peisong.setText(s.get)
		//item.
		adapter=new OrderLvAdapter(context, list,index);
		item.lv.setAdapter(adapter);
		item.tv_num.setText("共"+n+"件商品");
		return convertView;
	}
	class Item{
		TextView tv_title,tv_peisong,tv_heji,tv_num,tv_online,tv_qq;
		MyListview lv;
		EditText et;
		LinearLayout lin;
	}

}
