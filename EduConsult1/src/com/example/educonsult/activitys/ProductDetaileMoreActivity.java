package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;

import com.example.educonsult.R;
import com.example.educonsult.adapters.ProductPingjiaAdapter;
import com.example.educonsult.myviews.MyListview;



public class ProductDetaileMoreActivity extends BaseActivity {
	private MyListview listview;
	private ProductPingjiaAdapter pingjiaAdapter;
	private Context context;
	private ArrayList<String > list;
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
		list=new ArrayList<String>();
		listview=(MyListview)findViewById(R.id.product_detail_more_list);
		pingjiaAdapter=new ProductPingjiaAdapter(context, list);
		listview.setAdapter(pingjiaAdapter);
	}
	


}
