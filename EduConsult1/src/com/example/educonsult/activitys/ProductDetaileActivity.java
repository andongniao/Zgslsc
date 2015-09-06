package com.example.educonsult.activitys;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.xunbo.store.ExampleActivity;
import com.xunbo.store.MyApplication;
import com.xunbo.store.adapters.HomeGridAdapter;
import com.xunbo.store.adapters.HomeLikeAdapter;
import com.xunbo.store.adapters.ProductPingjiaAdapter;
import com.xunbo.store.adapters.TextItemCenterListAdapter;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.beans.CommentBean;
import com.xunbo.store.beans.CommentStar;
import com.xunbo.store.beans.HomeBean;
import com.xunbo.store.beans.ListComment;
import com.xunbo.store.beans.ListProductBean;
import com.xunbo.store.beans.MallInfoBean;
import com.xunbo.store.beans.ProdectDetaileBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.beans.UserBean;
import com.xunbo.store.myviews.ImageCycleView;
import com.xunbo.store.myviews.ImageCycleView.ImageCycleViewListener;
import com.xunbo.store.myviews.MyListview;
import com.xunbo.store.myviews.ScrollViewExtend;
import com.xunbo.store.net.PostHttp;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;
public class ProductDetaileActivity extends Activity implements OnClickListener{
	protected int activityCloseEnterAnimation;
	protected int activityCloseExitAnimation;
	private Context context;
	private ScrollViewExtend scrollView;
	private LinearLayout ll_addshopcart,ll_gopay,ll_as_l,ll_as_t,ll_as_r,
	ll_paied_l,ll_paied_t,ll_paied_r,ll_add_chanpin,ll_add_pingjia,ll_add_dianpu,
	ll_add_view_chanpin,ll_add_view_pingjia,ll_add_view_dianpu,ll_kefu,ll_shouchang
	,ll_buy,ll_nobuy,ll_chanpin,ll_pingjia,ll_tuijian,ll_topchanpin,ll_toppingjia,ll_toptuijian,ll_top;
	private boolean isshow;
	private PopupWindow popupWindow,popuyunfei;
	private int w,h,lh;
	private Intent intent;
	private TextView chanpin,pingjia,dianpu,pingjiamore,add,buymore,tv_title
	,tv_shangcheng,tv_danjia,tv_qidingliang,tv_xiaoliang,tv_kucun,tv_chandi
	,tv_computer,tv_miaoshu,tv_taidu,tv_fahuo,topchanpin,toppingjia,toptuijian,
	pingjiaisnot,tv_yunsong,tv_isyouhuo,tv_time,text_qiding;
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
	private ImageView b_l,b_t,b_r,t_l,t_t,t_r,shoucang;
	private TextView tb_l,tb_t,tb_r,tt_l,tt_t,tt_r,tv_content;
	private ListComment listComment;
	private ArrayList<CommentBean> comlist;
	private ArrayList<CommentStar> comstar;
	private ProductBean productBean;
	private String liulanfile,storename;
	private Util u;
	private boolean isSave,iserror;
	private UserBean userbean;
	private BaseBean bean,bean2;
	private HomeBean home;
	private TextView[] tvtuijian;
	private ImageView[] imtuijian;
	private ListProductBean listProductBean;
	private LinearLayout dianpulin;
	private View expresslin;
	private ImageView iv_top_t,ima_pingjia,ima_chanpin,ima_tuijian,ima_fanhui,ima_shop;
	private RelativeLayout rl_r;
	private int refeshDatatype,chanpingx,chanpingy,pingjiax,pingjiay,tuijianx,tuijiany,topx,topy;
	private HomeGridAdapter recommendAdapter;
	private ArrayList<String> listExpress;
	private LayoutInflater inflater;
	private ListView lv_express;
	private TextItemCenterListAdapter textItemCenterListAdapter;
	private boolean isCollect;
	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//		topRightLVisible();
		TestinAgent.init(this);
		TypedArray activityStyle = getTheme().obtainStyledAttributes(new int[] {android.R.attr.windowAnimationStyle});
		int windowAnimationStyleResId = activityStyle.getResourceId(0, 0);      
		activityStyle.recycle();
		activityStyle = getTheme().obtainStyledAttributes(windowAnimationStyleResId, new int[] {android.R.attr.activityCloseEnterAnimation, android.R.attr.activityCloseExitAnimation});
		activityCloseEnterAnimation = activityStyle.getResourceId(0, 0);
		activityCloseExitAnimation = activityStyle.getResourceId(1, 0);
		activityStyle.recycle();
		setContentView(R.layout.product_detail);
		init();
		addlistener();
	}

	private void init() {
		TestinAgent.init(this);
		context = this;
		intent=getIntent();
		Bundle b=intent.getBundleExtra("productbundle");
		productBean=(ProductBean)b.getSerializable("product");
		if(MyApplication.mp.islogin){
			
			MyApplication.mp.SaveSee(productBean);
		}
		isCollect=false;
		userbean=MyApplication.mp.getUser();
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
//		imageview.stopImageTimerTask();
//		imageview.settime(999999999);
		pingjiamore=(TextView)findViewById(R.id.product_detaile_ll_add_View_xiangqing_more);
		pingjiamore.setOnClickListener(this);
		dianpulin=(LinearLayout)findViewById(R.id.product_detaile_ll_into_dianpu);
		dianpulin.setVisibility(View.GONE);
        dianpulin.setOnClickListener(this);
		scrollView = (ScrollViewExtend) findViewById(R.id.product_detaile_sl);
		ll_kefu=(LinearLayout)findViewById(R.id.product_detail_ll_kefu);
		ll_kefu.setOnClickListener(this);
		ll_shouchang=(LinearLayout)findViewById(R.id.product_detail_ll_shoucang);
		ll_shouchang.setOnClickListener(this);
		shoucang=(ImageView)findViewById(R.id.product_detail_ima_shoucang);
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
		tv_content = (TextView) findViewById(R.id.product_detaile_product_main_ingredients_edit);
		chanpin=(TextView)findViewById(R.id.product_detaile_tv_chanpin);
		pingjia=(TextView)findViewById(R.id.product_detaile_tv_pingjia);
		list=new ArrayList<ProductBean>();
		listView=(MyListview)findViewById(R.id.product_detaile_ll_add_view_list);

		gridView=(GridView)findViewById(R.id.product_detaile_all_view_dianputuijian_gv);
		
		
		ll_nobuy=(LinearLayout)findViewById(R.id.product_detaile_ll_notbuy);
		ll_buy=(LinearLayout)findViewById(R.id.product_detaile_ll_buied);
		buymore=(TextView)findViewById(R.id.product_detaile_tv_more_shopped);
		buymore.setOnClickListener(this);



		tv_title=(TextView)findViewById(R.id.product_detail_tv_title);

		tv_shangcheng=(TextView)findViewById(R.id.product_detaile_tv_shangcheng);
		tv_danjia=(TextView)findViewById(R.id.product_detaile_tv_danjia);
		tv_qidingliang=(TextView)findViewById(R.id.product_detaile_tv_qiding);
		tv_xiaoliang=(TextView)findViewById(R.id.product_detail_tv_xiaoliang);
		tv_kucun=(TextView)findViewById(R.id.product_detail_tv_kucun);
		tv_computer=(TextView)findViewById(R.id.product_detaile_tv_computer);
		tv_miaoshu=(TextView)findViewById(R.id.product_detaile_tv_miaoshu);
		tv_taidu=(TextView)findViewById(R.id.product_detaile_tv_fuwu);
		tv_fahuo=(TextView)findViewById(R.id.product_detaile_tv_fahuo);
		tv_chandi=(TextView)findViewById(R.id.product_detaile_tv_chandi);
		tv_yunsong=(TextView)findViewById(R.id.product_detaile_tv_yunsong);
		tv_isyouhuo=(TextView)findViewById(R.id.product_detaile_tv_isyouhuo);
		tv_time=(TextView)findViewById(R.id.product_detaile_tv_time);
		text_qiding=(TextView)findViewById(R.id.product_detaile_test_qiding);
		
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
		refeshDatatype=1;
		 
		ll_chanpin=(LinearLayout)findViewById(R.id.product_detail_ll_chanpin);
		ll_pingjia=(LinearLayout)findViewById(R.id.product_detail_ll_pingjia);
		ll_tuijian=(LinearLayout)findViewById(R.id.product_detaile_ll_tongleituijian);
		ll_topchanpin=(LinearLayout)findViewById(R.id.product_detaile_top_ll_chanpin);
		ll_toppingjia=(LinearLayout)findViewById(R.id.product_detaile_top_ll_pingjia);
		ll_toptuijian=(LinearLayout)findViewById(R.id.product_detaile_top_ll_tuijian);
		ll_top=(LinearLayout)findViewById(R.id.product_detaile_top);
		ll_topchanpin.setOnClickListener(this);
		ll_toppingjia.setOnClickListener(this);
		ll_toptuijian.setOnClickListener(this);
		ima_chanpin=(ImageView)findViewById(R.id.product_detaile_top_ima_chanpin);
		ima_pingjia=(ImageView)findViewById(R.id.product_detaile_top_ima_pingjia);
		ima_tuijian=(ImageView)findViewById(R.id.product_detaile_top_ima_tuijian);
		topchanpin=(TextView)findViewById(R.id.product_detaile_top_tv_chanpin);
		toppingjia=(TextView)findViewById(R.id.product_detaile_top_tv_pingjia);
		toptuijian=(TextView)findViewById(R.id.product_detaile_top_tv_tuijian);
		pingjiaisnot=(TextView)findViewById(R.id.product_detaile_ll_add_View_xiangqing_isnot);
		ll_chanpin.setFocusable(false);
		ll_pingjia.setFocusable(false);
		ll_tuijian.setFocusable(false);
		gridView.setFocusable(false);
		
		ima_shop=(ImageView)findViewById(R.id.product_detaile_iv_shop);
		ima_fanhui=(ImageView)findViewById(R.id.product_detaile_iv_back);
		ima_shop.setOnClickListener(this);
		ima_fanhui.setOnClickListener(this);
		View v = new ImageView(context);
		v.setBackgroundResource(R.drawable.base_to_top);
		popupWindow = new PopupWindow(v, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		isshow = false;
		
		toppingjia=(TextView)findViewById(R.id.product_detaile_top_tv_pingjia);
		
		
		scrollView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_MOVE){
					if((scrollView.getScrollY()+100)>=(pingjiay+chanpingy)){
						toptuijian.setTextColor(getResources().getColor(R.color.fame_hui3));
						ima_tuijian.setBackgroundColor(getResources().getColor(R.color.fame_hui3));
						topchanpin.setTextColor(getResources().getColor(R.color.white));
						ima_chanpin.setBackgroundColor(getResources().getColor(R.color.transparent));
						toppingjia.setTextColor(getResources().getColor(R.color.white));
						ima_pingjia.setBackgroundColor(getResources().getColor(R.color.transparent));
						
					}else if((scrollView.getScrollY()+100)>=chanpingy){
						toppingjia.setTextColor(getResources().getColor(R.color.fame_hui3));
						ima_pingjia.setBackgroundColor(getResources().getColor(R.color.fame_hui3));
						toptuijian.setTextColor(getResources().getColor(R.color.white));
						ima_tuijian.setBackgroundColor(getResources().getColor(R.color.transparent));
						topchanpin.setTextColor(getResources().getColor(R.color.white));
						ima_chanpin.setBackgroundColor(getResources().getColor(R.color.transparent));
						
					}else{
						topchanpin.setTextColor(getResources().getColor(R.color.fame_hui3));
						ima_chanpin.setBackgroundColor(getResources().getColor(R.color.fame_hui3));
						toptuijian.setTextColor(getResources().getColor(R.color.white));
						ima_tuijian.setBackgroundColor(getResources().getColor(R.color.transparent));
						toppingjia.setTextColor(getResources().getColor(R.color.white));
						ima_pingjia.setBackgroundColor(getResources().getColor(R.color.transparent));
						
					}
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
					topchanpin.setTextColor(getResources().getColor(R.color.fame_hui3));
					ima_chanpin.setBackgroundColor(getResources().getColor(R.color.fame_hui3));
					toptuijian.setTextColor(getResources().getColor(R.color.white));
					ima_tuijian.setBackgroundColor(getResources().getColor(R.color.transparent));
					toppingjia.setTextColor(getResources().getColor(R.color.white));
					ima_pingjia.setBackgroundColor(getResources().getColor(R.color.transparent));
					
				}
			}
		});

		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		}else {
			Util.ShowToast(context, R.string.net_is_eor);
		}
	}
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		super.onWindowFocusChanged(hasFocus);
		chanpingy=ll_chanpin.getHeight();
		chanpingx=ll_chanpin.getWidth();
		pingjiax=ll_pingjia.getWidth();
		pingjiay=ll_pingjia.getHeight();
		tuijianx=ll_tuijian.getWidth();
		tuijiany=ll_tuijian.getHeight();
		topy=ll_top.getHeight();
	}
	private void setPopuwindowCenter(){
		inflater=LayoutInflater.from(context);
		expresslin = inflater.inflate(R.layout.moneycar_list, null);
		lv_express = (ListView) expresslin.findViewById(R.id.moneycar_list_list);
		textItemCenterListAdapter = new TextItemCenterListAdapter(context, listExpress);
		lv_express.setAdapter(textItemCenterListAdapter);
	
		lv_express.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				tv_yunsong.setText(listExpress.get(arg2));
				/*if(Util.detect(context)){
					myPDT.Run(context, new RefeshData(arg2,0,3,""),R.string.loding);//可取消
				}*/
				popuyunfei.dismiss();
			}
		});
		
		popuyunfei = new PopupWindow(expresslin, LayoutParams.WRAP_CONTENT ,LayoutParams.WRAP_CONTENT  );
		popuyunfei.setFocusable(true);
		popuyunfei.setBackgroundDrawable(getResources().getDrawable(R.drawable.search_kuang));
		popuyunfei.setOutsideTouchable(true);
		popuyunfei.showAsDropDown(tv_yunsong);
		
	}
	void initDate(){
		dianpulin.setVisibility(View.VISIBLE);
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
			public void displayImage(final String imageURL, final ImageView imageView) {
				Util.Getbitmap(imageView, imageURL);
//				setImage(imageURL,imageView);
				
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
			text_qiding.setVisibility(View.GONE);
			tv_qidingliang.setVisibility(View.GONE);
		}
		storename=mallinfo.getUsername();
		tv_xiaoliang.setText(mallinfo.getSales()+mallinfo.getUnit());
		tv_kucun.setText(mallinfo.getAmount()+mallinfo.getUnit());
		tv_chandi.setText(mallinfo.getAreaname());
		
//		tv_yunsong.setText(mallinfo.get);
		if("0".equals(mallinfo.getExpress_1())&&"0".equals(mallinfo.getExpress_2())
				&&"0".equals(mallinfo.getExpress_3())){
			tv_yunsong.setText("请联系客服");
		}else{
			listExpress=new ArrayList<String>();
			if(!"0".equals(mallinfo.getExpress_1())){
				listExpress.add(mallinfo.getExpress_name_1());
			}
			if(!"0".equals(mallinfo.getExpress_2())){
				listExpress.add(mallinfo.getExpress_name_2());
			}
			if(!"0".equals(mallinfo.getExpress_3())){
				listExpress.add(mallinfo.getExpress_name_3());
			}
			if(listExpress.size()==1){
				tv_yunsong.setText(listExpress.get(0));
			}else{
				tv_yunsong.setText(listExpress.get(0));
				tv_yunsong.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						setPopuwindowCenter();
					}
				});
			}
			
			
		}
		if(Util.IsNull(mallinfo.getAmount())){
			if(Integer.parseInt(mallinfo.getAmount())>0){
				tv_isyouhuo.setText("有货");
			}else{
				tv_isyouhuo.setText("无货");
			}
		}
		
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		int lastDay=c.getActualMaximum(Calendar.DAY_OF_MONTH);
		for(int i=1;i<4;i++){
			if((day+i)==lastDay){
				day=1;
				month+=1;
			}
			day++;
		}
		month+=1;
		
		

		tv_time.setText("18:00前完成下单，预计后天（"+month+"月"+day+"日到）");
		tv_computer.setText(mallinfo.getCompany());
		tv_miaoshu.setText("5.0");
		tv_taidu.setText("5.0");
		tv_fahuo.setText("5.0");
		if(mallinfo.getContent()!=null){
			if("".equals(mallinfo.getContent())){
				tv_content.setText("暂无产品介绍！");
			}else{
				tv_content.setText(Html.fromHtml(mallinfo.getContent()));
			}
		}else{
			tv_content.setText("暂无产品介绍！");
		}
		
		
		if("1".equals(mallinfo.getCollect())){
			isCollect=false;
			shoucang.setBackgroundResource(R.drawable.product_shoucang);
		}else{
			isCollect=true;
			shoucang.setBackgroundResource(R.drawable.product_shoucang2);
		}

		if(recommend.size()!=0){
			if(recommendAdapter!=null){
				recommendAdapter.setProductBean(recommend);
				recommendAdapter.notifyDataSetChanged();
			}else{
				recommendAdapter=new HomeGridAdapter(context, recommend);
				gridView.setAdapter(recommendAdapter);
			}
			

		}

		Handler h=new Handler();
		h.postDelayed(runna, 500);
	}
	private void setImage(final String imageURL, final ImageView imageView) {
		final Handler handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what==1){
					Bitmap bitmap = (Bitmap) msg.obj;
					imageView.setImageBitmap(bitmap);
				}else{
					imageView.setBackgroundResource(R.drawable.default_bg);
				}
			}
		};
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					Bitmap b = Util.getBitmapForNet(imageURL);
					Message msg = handler.obtainMessage();
					if(b!=null){
					msg.what=1;
					msg.obj = b;
					}else{
						msg.what=2;
					}
					handler.sendMessage(msg);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}).start();
	}

	private void addlistener() {
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				Toproduct(recommend.get(arg2));
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.product_detail_ll_shoucang:
			refeshDatatype=2;
			
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
			}else {
				Util.ShowToast(context, R.string.net_is_eor);
			}
			break;
		case R.id.product_detail_ll_kefu:
			intent = new Intent(context,ServiceCenterActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.product_detaile_ll_into_dianpu:
			intent = new Intent(context,StoreShopBaseActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("storeid", storename);
			intent.putExtra("storename", "");
			startActivity(intent);
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
				ExampleActivity.setCurrentTab(2);
				SearchResultActivity.isproductfinish=true;
				SearchHomeActivity.isfinish=true;
				MyZjActivity.isfinish=true;
				StoreShopBaseActivity.isfinish=true;
				SCProductActivity.isfinish=true;
				SCStoreActivity.isfinish=true;
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
//		case R.id.product_detaile_ll_pingjia:
//	 
////			if(!ispingjia){
////				if(Util.detect(context)){
////					myPDT.Run(context, new RefeshData1(),R.string.loding);//可取消
////				}
////			}
			
//
//			break;
		
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
				
				
				
		case R.id.product_detaile_top_ll_chanpin:
			topchanpin.setTextColor(getResources().getColor(R.color.fame_hui3));
			ima_chanpin.setBackgroundColor(getResources().getColor(R.color.fame_hui3));
			toptuijian.setTextColor(getResources().getColor(R.color.white));
			ima_tuijian.setBackgroundColor(getResources().getColor(R.color.transparent));
			toppingjia.setTextColor(getResources().getColor(R.color.white));
			ima_pingjia.setBackgroundColor(getResources().getColor(R.color.transparent));
			ll_chanpin.setFocusable(true);
			ll_pingjia.setFocusable(false);
			ll_tuijian.setFocusable(false);
			scrollView.scrollTo(10, 10);
			popupWindow.dismiss();
			isshow = false;
			break;
		case R.id.product_detaile_top_ll_pingjia:
			toppingjia.setTextColor(getResources().getColor(R.color.fame_hui3));
			ima_pingjia.setBackgroundColor(getResources().getColor(R.color.fame_hui3));
			toptuijian.setTextColor(getResources().getColor(R.color.white));
			ima_tuijian.setBackgroundColor(getResources().getColor(R.color.transparent));
			topchanpin.setTextColor(getResources().getColor(R.color.white));
			ima_chanpin.setBackgroundColor(getResources().getColor(R.color.transparent));
			
			scrollView.scrollTo(0,(chanpingy));
//			
			popupWindow.showAtLocation(ll_addshopcart, Gravity.BOTTOM,w/2-20, 120);
			isshow = true;
			break;
		case R.id.product_detaile_top_ll_tuijian:
			toptuijian.setTextColor(getResources().getColor(R.color.fame_hui3));
			ima_tuijian.setBackgroundColor(getResources().getColor(R.color.fame_hui3));
			topchanpin.setTextColor(getResources().getColor(R.color.white));
			ima_chanpin.setBackgroundColor(getResources().getColor(R.color.transparent));
			toppingjia.setTextColor(getResources().getColor(R.color.white));
			ima_pingjia.setBackgroundColor(getResources().getColor(R.color.transparent));
			
			scrollView.scrollTo(0, (chanpingy+pingjiay));
			popupWindow.showAtLocation(ll_addshopcart, Gravity.BOTTOM,w/2-20, 120);
			isshow = true;
			break;
		case R.id.product_detaile_iv_back:
			finish();
			break;
		case R.id.product_detaile_iv_shop:
			SearchResultActivity.isproductfinish=true;
			MyZjActivity.isfinish=true;
			StoreActivity.isfinish = true;
			ExampleActivity.setCurrentTab(2);
			finish();
			break;
		
		


		}
	}
	private void Toproduct(ProductBean productBean){
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
			if(refeshDatatype==1){

				//任务完成后
				if(listComment!=null){
					if("200".equals(listComment.getCode())){
						comlist=listComment.getComlist();
						comstar=listComment.getComstar();
						mallinfo=productdetailbean.getMallinfo();
						recommend=productdetailbean.getRecommend();
						buyedlist=productdetailbean.getBuyedlist();
						setpingjiaDate();
						initDate();
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


			}
			else if(refeshDatatype==2){
				if(bean2!=null){
					if("200".equals(bean2.getCode())){
						if(isCollect){
							isCollect=false;
							shoucang.setBackgroundResource(R.drawable.product_shoucang);
						}else{
							isCollect=true;
							shoucang.setBackgroundResource(R.drawable.product_shoucang2);
						}
						Util.ShowToast(context, bean2.getMsg());
					}else if("300".equals(bean2.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent i= new Intent(context,LoginActivity.class);
						startActivity(i);
						finish();
					}else{
						Util.ShowToast(context, bean2.getMsg());
					}
				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
				MyCenterActivity.ischanged = true;
			}
			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			if(refeshDatatype==1){
				Send s = new Send(context);
				productdetailbean = s.GetProductDetaile(productBean.getItemid(),MyApplication.mp.getUser().getAuthstr());
				if(productdetailbean!=null){
					if("200".equals(productdetailbean.getCode())){
						listComment=s.GetComment(productBean.getItemid(), 1, "");
					}else if("300".equals(productdetailbean.getCode())){
						MyApplication.mp.setlogin(false);
						Util.ShowToast(context, R.string.login_out_time);
						Intent i= new Intent(context,LoginActivity.class);
						startActivity(i);
						finish();
					}else{
						Util.ShowToast(context, productdetailbean.getMsg());
					}

				}else{
					Util.ShowToast(context, R.string.net_is_eor);
				}
			}else if(refeshDatatype==2){
				PostHttp p=new PostHttp(context);
				if(!isCollect){
					//收藏
					bean2=p.Shoucang(1, 1,Integer.parseInt(productBean.getItemid()),userbean.getAuthstr());
				}else{
					//取消收藏
					bean2=p.Shoucang(2, 1,Integer.parseInt(productBean.getItemid()),userbean.getAuthstr());
				}
			}
			return true;
		}
	}
	private void setpingjiaDate(){
		listView.setFocusable(false);
		if(comlist!=null&&comlist.size()>0){
			pingjiaAdapter=new ProductPingjiaAdapter(this, comlist,3);
			listView.setAdapter(pingjiaAdapter);
		}else {
			pingjiaisnot.setVisibility(View.VISIBLE);
			pingjiamore.setVisibility(View.GONE);
//			Util.ShowToast(context, "暂无评价");
		}
//		ll_add_view_chanpin.setVisibility(View.GONE);
//		ll_add_view_pingjia.setVisibility(View.VISIBLE);
//		ll_add_view_dianpu.setVisibility(View.GONE);
//		chanpin.setTextColor(getResources().getColor(R.color.black));
//		pingjia.setTextColor(getResources().getColor(R.color.orn));
//		dianpu.setTextColor(getResources().getColor(R.color.black));

	}
	private Runnable runna=new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			chanpingy=ll_chanpin.getHeight();
			chanpingx=ll_chanpin.getWidth();
			pingjiax=ll_pingjia.getWidth();
			pingjiay=ll_pingjia.getHeight();
			tuijianx=ll_tuijian.getWidth();
			tuijiany=ll_tuijian.getHeight();
			topy=ll_top.getHeight();
		}
	};

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(activityCloseEnterAnimation, activityCloseExitAnimation);
	}

}
