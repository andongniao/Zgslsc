package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.ExampleActivity;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.adapters.HomeLikeAdapter;
import com.example.educonsult.adapters.ProductPingjiaAdapter;
import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.CommentBean;
import com.example.educonsult.beans.CommentStar;
import com.example.educonsult.beans.HomeBean;
import com.example.educonsult.beans.ListComment;
import com.example.educonsult.beans.ListProductBean;
import com.example.educonsult.beans.MallInfoBean;
import com.example.educonsult.beans.ProdectDetaileBean;
import com.example.educonsult.beans.ProductBean;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.myviews.ImageCycleView;
import com.example.educonsult.myviews.ImageCycleView.ImageCycleViewListener;
import com.example.educonsult.myviews.MyListview;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.Util;
import com.testin.agent.TestinAgent;
public class ProductDetaileActivity extends BaseActivity implements OnClickListener{
	private Context context;
	private ScrollView scrollView;
	private LinearLayout ll_addshopcart,ll_gopay,ll_as_l,ll_as_t,ll_as_r,
	ll_paied_l,ll_paied_t,ll_paied_r,ll_add_chanpin,ll_add_pingjia,ll_add_dianpu,
	ll_add_view_chanpin,ll_add_view_pingjia,ll_add_view_dianpu,ll_kefu,ll_shouchang
	,ll_buy,ll_nobuy;
	private boolean isshow;
	private PopupWindow popupWindow;
	private int w,h,lh;
	private Intent intent;
	private TextView chanpin,pingjia,dianpu,pingjiamore,add,buymore,tv_title
	,tv_shangcheng,tv_danjia,tv_qidingliang,tv_xiaoliang,tv_kucun,tv_chandi
	,tv_computer,tv_miaoshu,tv_taidu,tv_fahuo;
	private GridView gridView;
	private MyListview listView;
	private ProductPingjiaAdapter pingjiaAdapter;
	private ArrayList<ProductBean> list,liulanlist;
	private HomeLikeAdapter homeLikeAdapter;
	private ProdectDetaileBean productdetailbean;
	private ThreadWithProgressDialog myPDT;
	private MallInfoBean mallinfo;
	private ArrayList<ProductBean> recommend;
	private ArrayList<ProductBean> buyedlist;
	private ImageCycleView imageview;
	private ArrayList<String> images;
	private ImageView b_l,b_t,b_r,t_l,t_t,t_r;
	private TextView tb_l,tb_t,tb_r,tt_l,tt_t,tt_r,tv_content;
	private ListComment listComment;
	private ArrayList<CommentBean> comlist;
	private ArrayList<CommentStar> comstar;
	private ProductBean productBean;
	private String liulanfile;
	private Util u;
	private boolean isSave,ispingjia,iserror;
	private UserBean userbean;
	private BaseBean bean;
	private HomeBean home;
	private TextView[] tvtuijian;
	private ImageView[] imtuijian;
	private ListProductBean listProductBean;
	private LinearLayout dianpulin;
	


	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//		topRightLVisible();
		//		topRightRVisible();
		topRightTGone();
		setTopLeftTv(R.string.product_detaile_title);
		setContentXml(R.layout.product_detail);
		init();
		addlistener();
	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		intent=getIntent();
		//		productBean=(ProductBean)intent.getSerializableExtra("productdetaile");
		//		/*izable("product", productBean);
		//		intent.putE*/xtra("productbundle", b);
		Bundle b=intent.getBundleExtra("productbundle");
		productBean=(ProductBean)b.getSerializable("product");
		liulanfile=MyApplication.Seejilu;
		userbean=MyApplication.mp.getUser();
		u=new Util(context);
		//isSave=u.readObject(liulanfile);
		listProductBean=(ListProductBean)u.readObject(liulanfile);
		if(listProductBean==null){
			listProductBean=new ListProductBean();
			liulanlist=new ArrayList<ProductBean>();

		}else{

			liulanlist=listProductBean.getList();
		}
		liulanlist.add(productBean);
		listProductBean.setList(liulanlist);
		isSave=u.saveObject(listProductBean, liulanfile);
		/*if(isSave){
			Util.ShowToast(context, "单品保存成功");
		}else{
			Util.ShowToast(context, "单品保存失败");
		}*/
		mallinfo=new MallInfoBean();
		recommend=new ArrayList<ProductBean>();
		buyedlist=new ArrayList<ProductBean>();
		comlist=new ArrayList<CommentBean>();
		comstar=new ArrayList<CommentStar>();
		DisplayMetrics  dm = new DisplayMetrics();  
		getWindowManager().getDefaultDisplay().getMetrics(dm);  
		w = dm.widthPixels;  
		h = dm.heightPixels; 
		myPDT=new ThreadWithProgressDialog();
		add=(TextView)findViewById(R.id.product_detaile_adds);
		add.setOnClickListener(this);
		imageview=(ImageCycleView)findViewById(R.id.product_detail_icv);
		imageview.stopImageTimerTask();
		imageview.settime(999999999);
		ispingjia=false;
		pingjiamore=(TextView)findViewById(R.id.product_detaile_ll_add_View_xiangqing_more);
		pingjiamore.setOnClickListener(this);
		dianpulin=(LinearLayout)findViewById(R.id.product_detaile_ll_into_dianpu);
        dianpulin.setOnClickListener(this);
        dianpulin.setVisibility(View.GONE);
		scrollView = (ScrollView) findViewById(R.id.product_detaile_sl);
		ll_kefu=(LinearLayout)findViewById(R.id.product_detail_ll_kefu);
		ll_kefu.setOnClickListener(this);
		ll_shouchang=(LinearLayout)findViewById(R.id.product_detail_ll_shoucang);
		ll_shouchang.setOnClickListener(this);
		ll_as_l = (LinearLayout) findViewById(R.id.product_detaile_ll_tonglei_l);
		ll_as_l.setOnClickListener(this);
		ll_as_t = (LinearLayout) findViewById(R.id.product_detaile_ll_tonglei_t);
		ll_as_t.setOnClickListener(this);
		ll_as_r = (LinearLayout) findViewById(R.id.product_detaile_ll_tonglei_r);
		ll_as_r.setOnClickListener(this);
		ll_paied_l = (LinearLayout) findViewById(R.id.product_detaile_ll_shopped_l);
		ll_paied_l.setOnClickListener(this);
		ll_paied_t = (LinearLayout) findViewById(R.id.product_detaile_ll_shopped_t);
		ll_paied_t.setOnClickListener(this);
		ll_paied_r = (LinearLayout) findViewById(R.id.product_detaile_ll_shopped_r);
		ll_paied_r.setOnClickListener(this);
		ll_gopay = (LinearLayout) findViewById(R.id.product_detaile_ll_pay_now);
		ll_gopay.setOnClickListener(this);
		ll_addshopcart = (LinearLayout) findViewById(R.id.product_detaile_ll_add_shopcart);
		ll_addshopcart.setOnClickListener(this);
		ll_add_chanpin=(LinearLayout)findViewById(R.id.product_detaile_ll_chanpin);
		ll_add_chanpin.setOnClickListener(this);
		ll_add_pingjia=(LinearLayout)findViewById(R.id.product_detaile_ll_pingjia);
		ll_add_pingjia.setOnClickListener(this);
		ll_add_dianpu=(LinearLayout)findViewById(R.id.product_detaile_ll_dianputuijian);
		ll_add_dianpu.setOnClickListener(this);
		ll_add_view_chanpin=(LinearLayout)findViewById(R.id.product_detaile_ll_add_view_chanpin);
		ll_add_view_pingjia=(LinearLayout)findViewById(R.id.product_detaile_ll_add_view_pingjia);
		ll_add_view_dianpu=(LinearLayout)findViewById(R.id.product_detaile_ll_add_view_dianputuijian);
		tv_content = (TextView) findViewById(R.id.product_detaile_product_main_ingredients_edit);
		chanpin=(TextView)findViewById(R.id.product_detaile_tv_chanpin);
		pingjia=(TextView)findViewById(R.id.product_detaile_tv_pingjia);
		dianpu=(TextView)findViewById(R.id.product_detaile_tv_dianutuijian );
		list=new ArrayList<ProductBean>();
		listView=(MyListview)findViewById(R.id.product_detaile_ll_add_view_list);

		gridView=(GridView)findViewById(R.id.product_detaile_all_view_dianputuijian_gv);
		homeLikeAdapter = new HomeLikeAdapter(context, list);
		gridView.setAdapter(homeLikeAdapter);
		View v = new ImageView(context);
		v.setBackgroundResource(R.drawable.base_to_top);
		popupWindow = new PopupWindow(v, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		isshow = false;
		scrollView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_MOVE){
					if(!isshow){
						if(scrollView.getScrollY()>100){
							popupWindow.showAtLocation(ll_addshopcart, Gravity.BOTTOM,w/2-20, 120);
							isshow = true;
						}
					}else{
						if(scrollView.getScrollY()<=100){
							if(popupWindow!=null && popupWindow.isShowing()){
								popupWindow.dismiss();
							}
							isshow = false;
						}
					}
				}
				return false;
			}
		});

		v.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isshow){
					if(popupWindow!=null && popupWindow.isShowing()){
						popupWindow.dismiss();
					}
					scrollView.scrollTo(10, 10);
					isshow = false;
				}
			}
		});
		ll_nobuy=(LinearLayout)findViewById(R.id.product_detaile_ll_notbuy);
		ll_buy=(LinearLayout)findViewById(R.id.product_detaile_ll_buied);
		buymore=(TextView)findViewById(R.id.product_detaile_tv_more_shopped);
		buymore.setOnClickListener(this);



		tv_title=(TextView)findViewById(R.id.product_detail_tv_title);
		//		,tv_shangcheng,tv_danjia,tv_qidingliang,tv_xiaoliang,tv_kucun,tv_chandi
		//		,tv_computer,tv_miaoshu,tv_taidu,tv_fahuo;
		tv_shangcheng=(TextView)findViewById(R.id.product_detaile_tv_shangcheng);
		tv_danjia=(TextView)findViewById(R.id.product_detaile_tv_danjia);
		tv_qidingliang=(TextView)findViewById(R.id.product_detaile_tv_qiding);
		tv_xiaoliang=(TextView)findViewById(R.id.product_detail_tv_xiaoliang);
		tv_kucun=(TextView)findViewById(R.id.product_detail_tv_kucun);
		tv_chandi=(TextView)findViewById(R.id.product_detail_tv_chandi);
		tv_computer=(TextView)findViewById(R.id.product_detaile_tv_computer);
		tv_miaoshu=(TextView)findViewById(R.id.product_detaile_tv_miaoshu);
		tv_taidu=(TextView)findViewById(R.id.product_detaile_tv_fuwu);
		tv_fahuo=(TextView)findViewById(R.id.product_detaile_tv_fahuo);


		//		b_l,b_t,b_r,t_l,t_t,t_r;
		//		private TextView tb_l,tb_t,tb_r,tt_l,tt_t,tt_r;
		b_l=(ImageView)findViewById(R.id.product_detaile_ima_bui_l);
		tb_l=(TextView)findViewById(R.id.product_detaile_tv_bui_l);
		b_t=(ImageView)findViewById(R.id.product_detaile_ima_bui_t);
		tb_t=(TextView)findViewById(R.id.product_detaile_tv_bui_t);
		b_r=(ImageView)findViewById(R.id.product_detaile_ima_bui_4);
		tb_r=(TextView)findViewById(R.id.product_detaile_tv_bui_r);

		t_l=(ImageView)findViewById(R.id.product_detaile_ima_tonglei_l);
		tt_l=(TextView)findViewById(R.id.product_detaile_tv_tonglei_l);
		t_t=(ImageView)findViewById(R.id.product_detaile_ima_tonglei_t);
		tt_t=(TextView)findViewById(R.id.product_detaile_tv_tonglei_t);
		t_r=(ImageView)findViewById(R.id.product_detaile_ima_tonglei_r);
		tt_r=(TextView)findViewById(R.id.product_detaile_tv_tonglei_r);

		//b_l.setBackgroundDrawable(getResources().)




		//		

		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		}else {
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}
	void initDate(){
		//ll_nobuy.setVisibility(View.GONE);
		images=new ArrayList<String>();
		if(Util.IsNull(mallinfo.getThumb())){
			images.add(mallinfo.getThumb());
		}
		if(Util.IsNull(mallinfo.getThumb1())){
			images.add(mallinfo.getThumb1());
		}
		if(Util.IsNull(mallinfo.getThumb2())){
			images.add(mallinfo.getThumb2());
		}
		//		imageview.set
		imageview.setImageResources(images, new ImageCycleViewListener() {

			@Override
			public void onImageClick(int position, View imageView) {
				
				// TODO Auto-generated method stub

			}

			@Override
			public void displayImage(String imageURL, ImageView imageView) {
				Util.Getbitmap(imageView, imageURL);
			}
		});
		tv_title.setText(mallinfo.getKeyword());
		if(!"".equals(mallinfo.getPrice2())&& mallinfo.getPrice2()!=null){
			
			tv_shangcheng.setText(mallinfo.getPrice2());
		}else{
			tv_shangcheng.setText(mallinfo.getPrice());
		}
		tv_danjia.setText(mallinfo.getPrice());
		if(Util.IsNull(mallinfo.getQbnum())){
			tv_qidingliang.setText(mallinfo.getQbnum());
		}else{
			tv_qidingliang.setText("1");
		}
		
		tv_xiaoliang.setText(mallinfo.getSales());
		tv_kucun.setText(mallinfo.getAmount());
		tv_chandi.setText(mallinfo.getAreaname());
		tv_computer.setText(mallinfo.getCompany());
		tv_miaoshu.setText("4.5");
		tv_taidu.setText("4.5");
		tv_fahuo.setText("4.5");
		tv_content.setText(Html.fromHtml(mallinfo.getContent()));

		if(buyedlist.size()==0){
			//ll_buy.setVisibility(View.GONE);
			buymore.setVisibility(View.GONE);
		}else{
			ll_nobuy.setVisibility(View.GONE);
			ll_buy.setVisibility(View.VISIBLE);
			try {
//				b_l.setImageBitmap(Util.getBitmapForNet(buyedlist.get(0).getThumb()));
//				b_t.setImageBitmap(Util.getBitmapForNet(buyedlist.get(1).getThumb()));
//				b_r.setImageBitmap(Util.getBitmapForNet(buyedlist.get(2).getThumb()));
				Util.Getbitmap(b_l, buyedlist.get(0).getThumb());
				Util.Getbitmap(b_r, buyedlist.get(1).getThumb());
				Util.Getbitmap(b_r, buyedlist.get(2).getThumb());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Util.Getbitmap(b_l, buyedlist.get(0).getThumb());
			tb_l.setText(buyedlist.get(0).getPrice());
			//Util.Getbitmap(b_t, buyedlist.get(0).getThumb());
			tb_t.setText(buyedlist.get(1).getPrice());
			//Util.Getbitmap(b_r, buyedlist.get(0).getThumb());
			tb_r.setText(buyedlist.get(2).getPrice());
		}
		if(recommend.size()!=0){

			try {
//				t_l.setImageBitmap(Util.getBitmapForNet(recommend.get(0).getThumb()));
//				t_t.setImageBitmap(Util.getBitmapForNet(recommend.get(1).getThumb()));
//				t_r.setImageBitmap(Util.getBitmapForNet(recommend.get(2).getThumb()));
				Util.Getbitmap(t_l, recommend.get(0).getThumb());
				Util.Getbitmap(t_t, recommend.get(1).getThumb());
				Util.Getbitmap(t_r, recommend.get(2).getThumb());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Util.Getbitmap(b_l, buyedlist.get(0).getThumb());
			tt_l.setText("￥"+recommend.get(0).getPrice()+"");
			//Util.Getbitmap(b_t, buyedlist.get(0).getThumb());
			tt_t.setText("￥"+recommend.get(1).getPrice()+"");
			//Util.Getbitmap(b_r, buyedlist.get(0).getThumb());
			tt_r.setText("￥"+recommend.get(2).getPrice()+"");
		}


	}
	private void addlistener() {
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				//Toproduct();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.product_detail_ll_shoucang:
			break;
		case R.id.product_detail_ll_kefu:
			intent = new Intent(context,ServiceCenterActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.product_detaile_ll_into_dianpu:
			//			intent = new Intent(context,StoreActivity.class);
			//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//			startActivity(intent);
//			Util.ShowToast(context, R.string.maimeng);''
			
			break;
		case R.id.product_detaile_ll_tonglei_l:

			Toproduct(recommend.get(0));
			break;
		case R.id.product_detaile_ll_tonglei_t:
			Toproduct(recommend.get(1));
			break;
		case R.id.product_detaile_ll_tonglei_r:
			Toproduct(recommend.get(2));
			break;
		case R.id.product_detaile_ll_shopped_l:
			Toproduct(buyedlist.get(0));
			break;
		case R.id.product_detaile_ll_shopped_t:
			Toproduct(buyedlist.get(1));
			break;
		case R.id.product_detaile_ll_shopped_r:
			Toproduct(buyedlist.get(2));
			break;
		case R.id.product_detaile_ll_pay_now: 
			if(MyApplication.mp.islogin){
//				ShopcartActivity.ischange=true;
				SearchResultActivity.isproductfinish=true;
				ExampleActivity.setCurrentTab(3);
				finish();
			}else{
				intent = new Intent(context,LoginActivity.class);
				startActivity(intent);
				finish();
			}
			break;
		case R.id.product_detaile_ll_add_shopcart:
			if(MyApplication.mp.islogin){
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData2(),R.string.loding);//可取消
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else{
				intent = new Intent(context,LoginActivity.class);
				startActivity(intent);
				finish();
			}
			break;
		case R.id.product_detaile_ll_chanpin:
			ll_add_view_chanpin.setVisibility(View.VISIBLE);
			ll_add_view_pingjia.setVisibility(View.GONE);
			ll_add_view_dianpu.setVisibility(View.GONE);
			chanpin.setTextColor(getResources().getColor(R.color.orn));
			pingjia.setTextColor(getResources().getColor(R.color.black));
			dianpu.setTextColor(getResources().getColor(R.color.black));
			break;
		case R.id.product_detaile_ll_pingjia:
	 
//			if(!ispingjia){
//				if(Util.detect(context)){
//					myPDT.Run(context, new RefeshData1(),R.string.loding);//可取消
//				}
//			}
			ispingjia=true;
			setpingjiaDate();

			break;
		case R.id.product_detaile_ll_dianputuijian:
			ll_add_view_chanpin.setVisibility(View.GONE);
			ll_add_view_pingjia.setVisibility(View.GONE);
			ll_add_view_dianpu.setVisibility(View.VISIBLE);
			chanpin.setTextColor(getResources().getColor(R.color.black));
			pingjia.setTextColor(getResources().getColor(R.color.black));
			dianpu.setTextColor(getResources().getColor(R.color.orn));
			break;
		case R.id.product_detaile_ll_add_View_xiangqing_more:
			intent = new Intent(context,ProductDetaileMoreActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//intent.putExtra("qingjiamore", mallinfo.getItemid());
			intent.putExtra("qingjiamore",productBean.getItemid());
			startActivity(intent);

			break;
		case R.id.product_detaile_adds:
			intent=new Intent(context, BusinesspartnersInfoActivity.class);
			intent.putExtra("bus","2");
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;


		}
	}
	private void Toproduct(ProductBean productBean){
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		//intent.putExtra("productdetaile", value);
		Bundle b=new Bundle();
		b.putSerializable("product", productBean);
		intent.putExtra("productbundle", b);
		startActivity(intent);
	}
	public class RefeshData2 implements ThreadWithProgressDialogTask {
		int postion;
		public RefeshData2() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			//			Toast.makeText(context, "cancle", 1000).show();
			Util.ShowToast(context,"添加失败");
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(bean!=null){
				if("200".equals(bean.getCode())){
					//TODO	
					ShopcartActivity.ischange = true;
					Util.ShowToast(context,"添加成功");
				}else if("300".equals(bean.getCode())){
					MyApplication.mp.setlogin(false);
					Util.ShowToast(context, R.string.login_out_time);
					Intent i= new Intent(context,LoginActivity.class);
					startActivity(i);
					finish();
				}else{
					Util.ShowToast(context, bean.getMsg());
				}
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}

			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			Send s = new Send(context);
			//productdetailbean = s.GetProductDetaile();
			// listProductBean=s.getCenterRecommend();
			//bean=s.CartAdd(mallinfo.getItemid(), 1,userbean.getAuthstr());
			bean=s.CartAdd(productBean.getItemid(), 1,userbean.getAuthstr());
			return true;
		}
	}
	void initTuijian(){
		try {
			t_l.setImageBitmap(Util.getBitmapForNet(recommend.get(0).getThumb()));
			t_t.setImageBitmap(Util.getBitmapForNet(recommend.get(1).getThumb()));
			t_r.setImageBitmap(Util.getBitmapForNet(recommend.get(2).getThumb()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tt_l.setText(recommend.get(0).getTitle());
		tt_t.setText(recommend.get(1).getTitle());
		tt_r.setText(recommend.get(2).getTitle());

	}


	public class RefeshData implements ThreadWithProgressDialogTask {

		public RefeshData() {
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			//			Toast.makeText(context, "cancle", 1000).show();
			finish();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(productdetailbean!=null){
				if("200".equals(productdetailbean.getCode())){
					mallinfo=productdetailbean.getMallinfo();
					recommend=productdetailbean.getRecommend();
					buyedlist=productdetailbean.getBuyedlist();
					initDate();

				}else if("300".equals(productdetailbean.getCode())){
					MyApplication.mp.setlogin(false);
					Util.ShowToast(context, R.string.login_out_time);
					Intent i= new Intent(context,LoginActivity.class);
					startActivity(i);
					finish();
				}else{
					Util.ShowToast(context, productdetailbean.getMsg());
				}

			}
			if(listComment!=null){
				if("200".equals(listComment.getCode())){
					comlist=listComment.getComlist();
					comstar=listComment.getComstar();
					
				}else if("300".equals(listComment.getCode())){
					MyApplication.mp.setlogin(false);
					Util.ShowToast(context, R.string.login_out_time);
					Intent i= new Intent(context,LoginActivity.class);
					startActivity(i);
					finish();
				}else{
					Util.ShowToast(context, listComment.getMsg());
				}
			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}
			/*if(home!=null){
				if("200".equals(home.getCode())){
					initTuijian();
				}else{
					Util.ShowToast(context, home.getMsg());
				}

			}else{
				Util.ShowToast(context, R.string.net_is_eor);
			}*/



			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			Send s = new Send(context);
			productdetailbean = s.GetProductDetaile(productBean.getItemid());
			//home = s.RequestHome();
			//productdetailbean = s.GetProductDetaile();
			listComment=s.GetComment(productBean.getItemid(), 1, "");
			return true;
		}
	}
	private void setpingjiaDate(){
      
		if(comlist!=null&&comlist.size()>0){
			pingjiaAdapter=new ProductPingjiaAdapter(this, comlist,3);
			listView.setAdapter(pingjiaAdapter);
		}else {
			pingjiamore.setVisibility(View.GONE);
			Util.ShowToast(context, "暂无评价");
		}
		ll_add_view_chanpin.setVisibility(View.GONE);
		ll_add_view_pingjia.setVisibility(View.VISIBLE);
		ll_add_view_dianpu.setVisibility(View.GONE);
		chanpin.setTextColor(getResources().getColor(R.color.black));
		pingjia.setTextColor(getResources().getColor(R.color.orn));
		dianpu.setTextColor(getResources().getColor(R.color.black));

	}



}
