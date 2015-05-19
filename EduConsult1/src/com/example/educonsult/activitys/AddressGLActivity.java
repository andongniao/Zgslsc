package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.adapters.TextItemListAdapter;
import com.example.educonsult.beans.AddressBean;
import com.example.educonsult.beans.AreaBean;
import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.ListAreaBean;
import com.example.educonsult.net.PostHttp;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.UITools;
import com.example.educonsult.tools.Util;

public class AddressGLActivity extends BaseActivity implements OnClickListener{
	private LinearLayout ll_diqu,ll_jiedao,ll_address,ll_shouhuoren,ll_number,ll_youbian;
	private TextView tv_diqu,tv_jiedao,tv_delete,tv_save;
	private EditText tv_address,tv_shouhuoren,tv_number,tv_youbian;
	private CheckBox cb_set;
	private boolean isok;
	private int areaid;
	//	private ImageView iv_top_l,iv_top_t;
	private Intent intent;
	String num;
	private int isdetault;
	private ArrayList<String> list;
	private TextItemListAdapter adapter_r;
	private PopupWindow popu;
	private ListView list_2,lv_l;
	private LayoutInflater inflater;
	private View v_fenlei;
	private ImageView iv_top_l,iv_top_t;
	private RelativeLayout rl_l,rl_r;
	private Context context;
	private ThreadWithProgressDialog myPDT;
	private AddressBean bean;
	private ListAreaBean lare;
	private ArrayList<AreaBean>listsheng,listshi,listxian;
	private Util u;
	private int type = 1,ttp;
	private String filename;
	private String diqu,dizhi,person,mob,postcode;
	private BaseBean beanresult;
	private boolean init;



	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		topRightTGone();
		//		topRightLVisible();
		//		topRightRVisible();
		//		iv_top_l = (ImageView) getTopLightView();
		//		iv_top_l.setBackgroundResource(R.drawable.top_xx_bg);
		//		iv_top_t = (ImageView) getTopRightView();
		//		iv_top_t.setBackgroundResource(R.drawable.top_home_bg);
		setTopLeftTv(R.string.address_title);
		setContentXml(R.layout.address_update);
		init();
		if(u.isExistDataCache(filename) && u.isReadDataCache(filename)){
			lare = (ListAreaBean) u.readObject(filename);
			listsheng = lare.getList();
		}else{
			if(Util.detect(context)){
				init = true;
				myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
		}
	}

	private void init() {
		context=this;
		filename = MyApplication.AreaName;
		u = new Util(context);
		bean = (AddressBean) getIntent().getExtras().get("address");
		myPDT = new ThreadWithProgressDialog();
		String  msg = getResources().getString(R.string.loding);
		num=getIntent().getStringExtra("addressnum");
//		isok = false;
		ll_diqu = (LinearLayout) findViewById(R.id.address_up_ll_diqu);
		ll_diqu.setOnClickListener(this);
		ll_jiedao = (LinearLayout) findViewById(R.id.address_up_ll_jiedao);
		ll_jiedao.setOnClickListener(this);
		ll_address = (LinearLayout) findViewById(R.id.address_up_ll_detaile);
		ll_address.setOnClickListener(this);
		ll_shouhuoren = (LinearLayout) findViewById(R.id.address_up_ll_shouhuoren);
		ll_shouhuoren.setOnClickListener(this);
		ll_number = (LinearLayout) findViewById(R.id.address_up_ll_phone_number);
		ll_number.setOnClickListener(this);
		ll_youbian = (LinearLayout) findViewById(R.id.address_up_ll_youbian);
		ll_youbian.setOnClickListener(this);
		tv_diqu = (TextView) findViewById(R.id.address_up_tv_diqu);
		//tv_diqu.setOnClickListener(this);
		//		tv_jiedao = (TextView) findViewById(R.id.address_up_tv_jiedao);
		//		tv_jiedao.setOnClickListener(this);
		tv_address = (EditText) findViewById(R.id.address_up_tv_detaile);
		tv_shouhuoren = (EditText) findViewById(R.id.address_up_tv_shouhuoren);

		tv_number = (EditText) findViewById(R.id.address_up_tv_phone_number);

		tv_youbian = (EditText) findViewById(R.id.address_up_tv_youbian);

		tv_delete = (TextView) findViewById(R.id.address_up_tv_delete);
		tv_delete.setOnClickListener(this);
		tv_save = (TextView) findViewById(R.id.address_up_tv_save);
		tv_save.setOnClickListener(this);
		cb_set = (CheckBox) findViewById(R.id.address_up_cb_set_default);
		cb_set.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				isok = isChecked;
				if(isok){
					isdetault = 1;
				}else{
					isdetault = 0;
				}
			}
		});
		if(num.equals("1")){//修改
			tv_delete.setVisibility(View.GONE);
		}else{
			tv_delete.setVisibility(View.GONE);
			if(bean!=null){
				tv_youbian.setText(bean.getPostcode());
				tv_number.setText(bean.getMobile());
				tv_shouhuoren.setText(bean.getTruename());
				tv_address.setText(bean.getAddress());
				tv_diqu.setText((Html.fromHtml(bean.getArea())));
				if("1".equals(bean.getIsdefault())){
					cb_set.setChecked(true);
				}else{
					cb_set.setChecked(false);
				}
			}
		}
		if(u.isExistDataCache(filename) && u.isReadDataCache(filename)){
			lare = (ListAreaBean) u.readObject(filename);
			listsheng = lare.getList();
		}

	}
	private void setpopuwindow(Context contexts,ListAreaBean larea,LinearLayout lin){

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
				tv_diqu.setText(listsheng.get(arg2).getArename());
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
						String a2 = tv_diqu.getText().toString();
						tv_diqu.setText(a2+listshi.get(ss).getArename());
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
									String a3 = tv_diqu.getText().toString();
									tv_diqu.setText(a3+listxian.get(s3).getArename());
									popu.dismiss();
								}
							});
						}else{
							popu.dismiss();
						}

					}
				});
				//				popu.dismiss();
			}
		});

		popu = new PopupWindow(v_fenlei,LayoutParams.FILL_PARENT , UITools.dip2px(context, 300f));
		popu.setFocusable(true);
		popu.setBackgroundDrawable(new BitmapDrawable());
		popu.setOutsideTouchable(true);
		popu.setFocusable(true);
		popu.showAsDropDown(lin);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.address_up_ll_diqu:
			setpopuwindow(context, lare, ll_diqu);
			break;
		case R.id.address_up_ll_jiedao:

			break;
		case R.id.address_up_ll_detaile:

			break;
		case R.id.address_up_ll_shouhuoren:

			break;
		case R.id.address_up_ll_phone_number:

			break;
		case R.id.address_up_ll_youbian:

			break;
		case R.id.address_up_tv_save:
			diqu = tv_diqu.getText().toString();
			dizhi = tv_address.getText().toString();
			person = tv_shouhuoren.getText().toString();
			postcode = tv_youbian.getText().toString();
			mob = tv_number.getText().toString();
			if(Util.IsNull(diqu) &&Util.IsNull(dizhi) &&Util.IsNull(person) &&Util.IsNull(postcode) &&
					Util.IsNull(mob)){
				if(Util.isMobileNO(mob)){
					if(Util.detect(context)){
						init = false;
						if(Util.detect(context)){
							init = false;
							myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
						}
					}else{
						Util.ShowToast(context, R.string.net_is_eor);
					}
				}else{
					Util.ShowToast(context, "请检查手机号格式");
				}
			}else{
				Util.ShowToast(context, "请保证信息完整");
			}
			break;
		case R.id.address_up_tv_delete:

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
			if(init){
				if(lare!=null){
					if("200".equals(lare.getCode())){
						init = false;
					}else if("300".equals(lare.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						intent = new Intent(context,LoginActivity.class);
						startActivity(intent);
						finish();
					}else{
						Util.ShowToast(context, beanresult.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				if(beanresult!=null){
					if("200".equals(beanresult.getCode())){
						if(num.equals("1")){
							Util.ShowToast(context, "添加成功！");
						}else if(num.equals("0")){
							Util.ShowToast(context, "修改成功！");
						}
						OrderActivity.isinit = true;
						AddressActivity.isinit = true;
						finish();
					}else if("300".equals(beanresult.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						intent = new Intent(context,LoginActivity.class);
						startActivity(intent);
						finish();
					}else{
						Util.ShowToast(context, beanresult.getMsg());
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
			if(!init){
				PostHttp p = new PostHttp(context);
				AddressBean ab = new AddressBean();
				String authstr = MyApplication.mp.getUser().getAuthstr();
				ab.setAddress(dizhi);
				ab.setArea(diqu);
				ab.setIsdefault(""+1);
				ab.setMobile(mob);
				ab.setTruename(person);
				ab.setPostcode(postcode);
				ab.setIsdefault(""+isdetault);
				if(num.equals("1")){
					ab.setAreaid(""+areaid);
					beanresult = p.addOneAddress(ab,authstr);
				}else if(num.equals("0")){
					ab.setAreaid(bean.getAreaid());
					ab.setItemid(bean.getItemid());
					ab.setIsdefault(""+isdetault);
					beanresult = p.editOneAddress(ab, authstr);
				}
			}else{
				Send s  = new Send(context);
				lare = s.GetArea();
			}

			return true;
		}
	}


	public void showpop(){
		adapter_r = new TextItemListAdapter(context, list);
		lv_l.setAdapter(adapter_r);
	}


}
