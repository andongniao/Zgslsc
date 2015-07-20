package com.example.educonsult.activitys;

import java.util.ArrayList;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.adapters.TextItemListAdapter;
import com.xunbo.store.beans.AddressBean;
import com.xunbo.store.beans.AreaBean;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.beans.ListAreaBean;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.tools.UITools;
import com.xunbo.store.tools.Util;

/**
 * 新建地址&修改地址
 * @author Qzr
 *
 */
public class AddressNewSaveActivity extends BaseActivity implements OnClickListener{
	private AddressBean bean;
	private Intent intent;
	private Context context;
	private int type;
	private EditText et_pp,et_num,et_code,et_diqu,et_address;
	private PopupWindow popu;
	private int areaid;
	private TextItemListAdapter adapter_r;
	private ArrayList<String> list;
	private ListView list_2,lv_l;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ArrayList<AreaBean>listsheng,listshi,listxian;
	private ThreadWithProgressDialog myPDT;
	private String filename;
	private Util u;
	private ListAreaBean lare;
	private boolean ischanged;
	private BaseBean basebean;

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		setTitleTxt(R.string.address_title);
		setContentXml(R.layout.address_new_save);
		init();
	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		u = new Util(context);
		myPDT = new ThreadWithProgressDialog();
		lare = MyApplication.mp.lare;
		filename = MyApplication.AreaName;
		type = getIntent().getIntExtra("type", -1);
		if(type==1){
			bean = (AddressBean) getIntent().getExtras().get("newsave");
			areaid = Integer.parseInt(bean.getAreaid());
		}else{
			bean = new AddressBean();
		}
		et_pp = (EditText) findViewById(R.id.address_new_save_et_shouhuoren);
		et_num = (EditText) findViewById(R.id.address_new_save_et_phone);
		et_code = (EditText) findViewById(R.id.address_new_save_et_uzheng);
		et_diqu = (EditText) findViewById(R.id.address_new_save_et_diqu);
		et_diqu.setOnClickListener(this);
		et_address = (EditText) findViewById(R.id.address_new_save_et_address);
		findViewById(R.id.address_new_save_tv_save).setOnClickListener(this);
		if(type==1){
			et_pp.setText(""+bean.getTruename());
			et_num.setText(""+bean.getMobile());
			et_code.setText(""+bean.getPostcode());
			et_diqu.setText(""+bean.getArea());
			String dq = bean.getAddress().replace(bean.getArea(), "");
			et_address.setText(""+dq);
		}


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.address_new_save_tv_save:
			if(Util.IsNull(et_pp.getText().toString())){
				if(Util.IsNull(et_num.getText().toString())){
					if(Util.IsNull(et_code.getText().toString())){
						if(Util.IsNull(et_diqu.getText().toString())){
							if(Util.IsNull(et_address.getText().toString())){
								if(Util.detect(context)){
									myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
								}else{
									Util.ShowToast(context, R.string.net_is_eor);
								}
							}else{
								Util.ShowToast(context, "详细地址不能为空");
							}
						}else{
							Util.ShowToast(context, "地区不能为空");
						}
					}else{
						Util.ShowToast(context, "邮政编码不能为空");
					}
				}else{
					Util.ShowToast(context, "手机号不能为空");
				}
			}else{
				Util.ShowToast(context, "收货人不能为空");
			}
			break;
		case R.id.address_new_save_et_diqu:
			setpopuwindow(context, lare, et_diqu);
			break;

		}
	}


	private void setpopuwindow(Context contexts,ListAreaBean larea,EditText e){
		//		listsheng = lare.getList();
		final ArrayList<String> list = new ArrayList<String>(); 
		listsheng = larea.getList();
		for(int i=0;i<listsheng.size();i++){
			list.add(listsheng.get(i).getArename());
		}
		inflater=LayoutInflater.from(contexts);
		v_fenlei = inflater.inflate(R.layout.moneycar_list, null);
		lv_l = (ListView) v_fenlei.findViewById(R.id.moneycar_list_list);
		adapter_r = new TextItemListAdapter(context, list);
		lv_l.setAdapter(adapter_r);
		lv_l.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				listshi = listsheng.get(arg2).getChild(); 
				areaid = listsheng.get(arg2).getAreaid();
				et_diqu.setText(listsheng.get(arg2).getArename());
				list.clear();
				for(int i=0;i<listshi.size();i++){
					list.add(listshi.get(i).getArename());
				}
				adapter_r = new TextItemListAdapter(context, list);
				lv_l.setAdapter(adapter_r);
				adapter_r.notifyDataSetChanged();
				lv_l.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int ss, long arg3) {
						listxian = listshi.get(ss).getChild();
						areaid = listshi.get(ss).getAreaid();
						String a2 = et_diqu.getText().toString();
						et_diqu.setText(a2+listshi.get(ss).getArename());
						if(listxian!=null && listxian.size()>0){
							list.clear();
							for(int i=0;i<listxian.size();i++){
								list.add(listxian.get(i).getArename());
							}
							adapter_r = new TextItemListAdapter(context, list);
							lv_l.setAdapter(adapter_r);
							adapter_r.notifyDataSetChanged();
							lv_l.setOnItemClickListener(new OnItemClickListener() {

								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int s3, long arg3) {
									areaid = listxian.get(s3).getAreaid();
									String a3 = et_diqu.getText().toString();
									et_diqu.setText(a3+listxian.get(s3).getArename());
									popu.dismiss();
								}
							});
						}else{
							popu.dismiss();
						}

					}
				});
			}
		});

		popu = new PopupWindow(v_fenlei,LayoutParams.FILL_PARENT , UITools.dip2px(context, 300f));
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setOutsideTouchable(true);
		popu.setFocusable(true);
		popu.showAsDropDown(e);
	}
	@Override
	protected void onResume() {
		super.onResume();
		//		if(u.isExistDataCache(filename) && u.isReadDataCache(filename)){
		//			lare = (ListAreaBean) u.readObject(filename);
		//			listsheng = lare.getList();
		//		}else{
		//			if(Util.detect(context)){
		//				init = true;
		//				myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		//			}else{
		//				Util.ShowToast(context, R.string.net_is_eor);
		//			}
		//		}
	}




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
			if(bean!=null){
				if("200".equals(basebean.getCode())){
					ischanged = true;
					if(type==1 && ischanged){
						  Intent intent=new Intent();  
			              intent.putExtra("result", bean);  
			              setResult(RESULT_OK, intent);  
			              finish();
					}else if(type==2 && ischanged){
						OrderActivity.isinit=true;
						AddressActivity.isinit = true;
						finish();
					}
				}else if("300".equals(basebean.getCode())){
					intent = new Intent(context,LoginActivity.class);
					startActivity(intent);
					finish();
				}else{
					Util.ShowToast(context, basebean.getMsg());
				}
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}



			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			PostHttp p = new PostHttp(context);
			bean.setAddress(et_address.getText().toString());
			bean.setArea(et_diqu.getText().toString());
			bean.setAreaid(""+areaid);
			bean.setMobile(et_num.getText().toString());
			bean.setPostcode(et_code.getText().toString());
			bean.setTruename(et_pp.getText().toString());
			if(type!=1){
				bean.setIsdefault("0");
			}
			if(type==1){
				basebean = p.editOneAddress(bean, MyApplication.mp.getUser().getAuthstr());
			}else{
				basebean = p.addOneAddress(bean, MyApplication.mp.getUser().getAuthstr());
			}

			return true;
		}
	}
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
