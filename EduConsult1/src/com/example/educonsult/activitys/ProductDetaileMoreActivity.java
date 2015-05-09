package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.adapters.ProductPingjiaAdapter;
import com.example.educonsult.beans.ProductBean;



public class ProductDetaileMoreActivity extends BaseActivity implements OnClickListener{
	private ListView listview;
	private ProductPingjiaAdapter pingjiaAdapter;
	private Context context;
	private ArrayList<ProductBean> list;
	private int type=1;
	private TextView tv_all,tv_good,tv_ok,tv_no;
	private LinearLayout li_all,li_good,li_ok,li_no;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
//		topRightLVisible();
//		topRightRVisible();
		topRightTGone();
		setTopLeftTv(R.string.product_detaile_more_title);
		setContentXml(R.layout.product_detail_more);
		context=this;
		init();
	}
	void init(){
		list=new ArrayList<ProductBean>();
		listview=(ListView)findViewById(R.id.product_detail_more_list);
		pingjiaAdapter=new ProductPingjiaAdapter(context, list);
		listview.setAdapter(pingjiaAdapter);
		li_all=(LinearLayout)findViewById(R.id.product_detaile_ll_add_view_pingjia_costperformace);
		li_good=(LinearLayout)findViewById(R.id.product_detaile_ll_add_view_pingjia_zhiliang);
		li_ok=(LinearLayout)findViewById(R.id.product_detaile_ll_add_view_pingjia_fuwu);
		li_no=(LinearLayout)findViewById(R.id.product_detaile_ll_add_view_pingjia_xiaoguo);
		tv_all=(TextView)findViewById(R.id.product_detaile_add_view_pingjia_costperformace);
		tv_good=(TextView)findViewById(R.id.product_detaile_add_view_pingjia_zhiliang);
		tv_ok=(TextView)findViewById(R.id.product_detaile_add_view_pingjia_fuwu);
		tv_no=(TextView)findViewById(R.id.product_detaile_add_view_pingjia_xiaoguo);
		li_all.setOnClickListener(this);
		li_good.setOnClickListener(this);
		li_no.setOnClickListener(this);
		li_ok.setOnClickListener(this);
		setRedText(li_all,tv_all);
	}
	private void setRedText(LinearLayout li,TextView tv){
		li.setBackgroundResource(R.drawable.search_lv_notnull_btn_bg);
		tv.setTextColor(getResources().getColor(R.color.white));
	}
	private void setWhiteText(LinearLayout li,TextView tv){
		li.setBackgroundResource(R.drawable.products_et_bg_line);
		tv.setTextColor(getResources().getColor(R.color.red));
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.product_detaile_ll_add_view_pingjia_costperformace:
			type=1;
			setRedText(li_all,tv_all);
			setWhiteText(li_good,tv_good);
			setWhiteText(li_ok,tv_ok);
			setWhiteText(li_no,tv_no);
			pingjiaAdapter=new ProductPingjiaAdapter(context, list);
			listview.setAdapter(pingjiaAdapter);
			break;
		case R.id.product_detaile_ll_add_view_pingjia_zhiliang:
			type=2;
			setRedText(li_good,tv_good);
			setWhiteText(li_all,tv_all);
			setWhiteText(li_ok,tv_ok);
			setWhiteText(li_no,tv_no);
			pingjiaAdapter=new ProductPingjiaAdapter(context, list);
			listview.setAdapter(pingjiaAdapter);
			break;
		case R.id.product_detaile_ll_add_view_pingjia_fuwu:
			type=3;
			setRedText(li_ok,tv_ok);
			setWhiteText(li_good,tv_good);
			setWhiteText(li_all,tv_all);
			setWhiteText(li_no,tv_no);
			pingjiaAdapter=new ProductPingjiaAdapter(context, list);
			listview.setAdapter(pingjiaAdapter);
			break;
		case R.id.product_detaile_ll_add_view_pingjia_xiaoguo:
			type=4;
			setRedText(li_no,tv_no);
			setWhiteText(li_good,tv_good);
			setWhiteText(li_ok,tv_ok);
			setWhiteText(li_all,tv_all);
			pingjiaAdapter=new ProductPingjiaAdapter(context, list);
			listview.setAdapter(pingjiaAdapter);
			break;

		default:
			break;
		}
	}
	


}
