package com.example.educonsult.activitys;

import java.util.ArrayList;

import net.simonvt.datepicker.DatePickDialog;
import net.simonvt.datepicker.DatePickDialog.IgetDate;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.educonsult.R;
import com.example.educonsult.adapters.TextItemListAdapter;

public class ConfirmTheDeliveryActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_way,ll_time;
	private EditText ed_way,ed_phone,ed_carid,ed_wuliuid;
	private TextView tv_time;
	private Button button;
	private Context context;
	private ArrayList<String> list;
	private PopupWindow popu;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ListView list_2,lv_l;
	private TextItemListAdapter adapter_r;
	private DatePickDialog datePickDialog;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		setTopLeftTv(R.string.confirm_the_delivery_title);
		setContentXml(R.layout.confirm_the_delivery);
		init();

	}

	private void init() {
		//		private LinearLayout ll_way;
		//		private EditText ed_way,ed_phone,ed_carid,ed_wuliuid,ed_time;
		//		private Button button;
		context=this;
		ll_way=(LinearLayout)findViewById(R.id.confirm_the_delivery_allway_lin);
		ll_way.setOnClickListener(this);
		ll_time=(LinearLayout)findViewById(R.id.confirm_the_delivery_time_lin);
		ll_time.setOnClickListener(this);
		ed_way=(EditText)findViewById(R.id.confirm_the_delivery_way);
		ed_phone=(EditText)findViewById(R.id.confirm_the_delivery_carphone);
		ed_carid=(EditText)findViewById(R.id.confirm_the_delivery_carid);
		tv_time=(TextView)findViewById(R.id.confirm_the_delivery_time);
		button=(Button)findViewById(R.id.confirm_the_delivery_ok);
		button.setOnClickListener(this);


		inflater=LayoutInflater.from(context);
		v_fenlei = inflater.inflate(R.layout.moneycar_list, null);

		lv_l = (ListView) v_fenlei.findViewById(R.id.moneycar_list_list);
		ArrayList<String> ll = new ArrayList<String>();
		ll.add("全部收支");
		ll.add("收入");
		ll.add("支出");
		adapter_r = new TextItemListAdapter(context, ll);
		lv_l.setAdapter(adapter_r);
		lv_l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub

				popu.dismiss();
			}
		});
		popu = new PopupWindow(v_fenlei, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setOutsideTouchable(true);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.confirm_the_delivery_allway_lin:
			popu.showAsDropDown(ll_way);

			break;
		case R.id.confirm_the_delivery_ok:


			break;
		case R.id.confirm_the_delivery_time_lin:
			datePickDialog=new DatePickDialog(this, new IgetDate() {

				@Override
				public void getDate(int year, int month, int day) {
					// TODO Auto-generated method stub
					int showmon=month+1;
					tv_time.setText(year+"-"+showmon+"-"+day);
					Log.i("time", year+"-"+showmon+"-"+day);
				}
			}, "日期选择", "确定", "取消");
			datePickDialog.show();
			break;



		}
	}



}
