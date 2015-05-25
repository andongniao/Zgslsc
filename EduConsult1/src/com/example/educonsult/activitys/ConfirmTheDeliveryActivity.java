package com.example.educonsult.activitys;

import java.util.ArrayList;

import net.simonvt.datepicker.DatePickDialog;
import net.simonvt.datepicker.DatePickDialog.IgetDate;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
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

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.adapters.TextItemListAdapter;
import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.ListOrderWuliu;
import com.example.educonsult.net.PostHttp;
import com.example.educonsult.tools.Util;
import com.testin.agent.TestinAgent;

public class ConfirmTheDeliveryActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_way,ll_time;
	private EditText ed_way,ed_phone,ed_carid,ed_wuliuid;
	private TextView tv_time,tv_tp;
	private Button button;
	private Context context;
	private ArrayList<String> list;
	private PopupWindow popu;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ListView list_2,lv_l;
	private TextItemListAdapter adapter_r;
	private DatePickDialog datePickDialog;
	private ThreadWithProgressDialog myPDT;
	private int addtype;
	private String authstr,itemid;
	private ListOrderWuliu listOrderWuliu;
	private ArrayList<String> ll;
	private int time_y,time_cho;
	private BaseBean bean;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		setTopLeftTv(R.string.confirm_the_delivery_title);
		setContentXml(R.layout.confirm_the_delivery);
		init();
		myPDT = new ThreadWithProgressDialog();
		String  msg = getResources().getString(R.string.loding);
		addtype=1;
		myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
	}

	private void init() {
		TestinAgent.init(this);
		context=this;
		ll = new ArrayList<String>();
		authstr = MyApplication.mp.getUser().getAuthstr();
		itemid = getIntent().getStringExtra("itemid");
		ll_way=(LinearLayout)findViewById(R.id.confirm_the_delivery_allway_lin);
		ll_way.setOnClickListener(this);
		tv_tp = (TextView) findViewById(R.id.confirm_the_delivery_allway);
		ll_time=(LinearLayout)findViewById(R.id.confirm_the_delivery_time_lin);
		ll_time.setOnClickListener(this);
		ed_way=(EditText)findViewById(R.id.confirm_the_delivery_way);
		ed_phone=(EditText)findViewById(R.id.confirm_the_delivery_carphone);
		ed_carid=(EditText)findViewById(R.id.confirm_the_delivery_carid);
		ed_wuliuid = (EditText)findViewById(R.id.confirm_the_delivery_wuliuid);
		ed_wuliuid.setVisibility(View.GONE);
		tv_time=(TextView)findViewById(R.id.confirm_the_delivery_time);
		button=(Button)findViewById(R.id.confirm_the_delivery_ok);
		button.setOnClickListener(this);
		inflater=LayoutInflater.from(context);
		v_fenlei = inflater.inflate(R.layout.moneycar_list, null);
		lv_l = (ListView) v_fenlei.findViewById(R.id.moneycar_list_list);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.confirm_the_delivery_allway_lin:
			popu.showAsDropDown(ll_way);

			break;
		case R.id.confirm_the_delivery_ok:
			if(time_cho>=time_y && time_cho<(time_y+7)){
				if(Util.detect(context)){
					addtype=2;
					myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				Util.ShowToast(context, "请保证在今天起七天内发货");
			}

			break;
		case R.id.confirm_the_delivery_time_lin:
			datePickDialog=new DatePickDialog(this, new IgetDate() {

				@Override
				public void getDate(int year, int month, int day) {
					// TODO Auto-generated method stub
					int showmon=month+1;
					String time = year+"-"+showmon+"-"+day;
					tv_time.setText(time);
					time_cho = Integer.parseInt(time.replace("-", ""));
				}
			}, "日期选择", "确定", "取消");
			datePickDialog.show();
			break;



		}
	}

	// 任务
	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			//				Toast.makeText(context, "cancle", 1000).show();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(addtype==1){
				if(listOrderWuliu!=null){
					if("200".equals(listOrderWuliu.getCode())){
						String s = listOrderWuliu.getSend_time();
						time_y = Integer.parseInt(s.replace("-", ""));
						ll = listOrderWuliu.getSend_types();
						adapter_r = new TextItemListAdapter(context, ll);
						lv_l.setAdapter(adapter_r);
						lv_l.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
									long arg3) {
								// TODO Auto-generated method stub
								tv_tp.setText(ll.get(arg2));
								popu.dismiss();
							}
						});
						popu = new PopupWindow(v_fenlei, LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
						popu.setFocusable(true);
						popu.setBackgroundDrawable(new BitmapDrawable());
						popu.setOutsideTouchable(true);
						
						
					}else if("300".equals(listOrderWuliu.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent i = new Intent(context,LoginActivity.class);
						startActivity(i);
						finish();
					}else{
						Util.ShowToast(context, listOrderWuliu.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
				
				
			}else{
				if(bean!=null){
					if("200".equals(bean.getCode())){
						Util.ShowToast(context, "操作成功");
						MyOrderActivity.isinit = true;
						finish();
					}else if("300".equals(bean.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent i = new Intent(context,LoginActivity.class);
						startActivity(i);
						finish();
					}else{
						Util.ShowToast(context, bean.getMsg());
					}
					
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}
			
			
			
			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			PostHttp p = new PostHttp(context);
			if(addtype==1){
				listOrderWuliu = p.Order_getwuliulist(itemid, authstr);
			}else{
				String no = ed_wuliuid.getText().toString();
				String t = tv_time.getText().toString();
				bean  = p.Order_sendgoods(itemid, 1, tv_tp.getText().toString(), no,t , authstr);
			}
			return true;
		}
	}



}
