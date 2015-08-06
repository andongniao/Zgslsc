package com.example.educonsult.activitys;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.testin.agent.TestinAgent;
import com.xunbo.store.MyApplication;
import com.xunbo.store.adapters.ProductPingjiaAdapter;
import com.xunbo.store.beans.CommentBean;
import com.xunbo.store.beans.CommentStar;
import com.xunbo.store.beans.ListComment;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.myviews.xlistview.XListView;
import com.xunbo.store.myviews.xlistview.XListView.IXListViewListener;
import com.xunbo.store.net.Send;
import com.xunbo.store.tools.Util;



public class ProductDetaileMoreActivity extends BaseActivity implements OnClickListener,IXListViewListener{
	private XListView listview;
	private ProductPingjiaAdapter pingjiaAdapter;
	private Context context;
	private ArrayList<ProductBean> list;
	private ArrayList<CommentBean>commentBeans;
	private int type,page;
	private TextView tv_all,tv_good,tv_ok,tv_no,tv_isnull;
	private LinearLayout li_all,li_good,li_ok,li_no;
	private ThreadWithProgressDialog myPDT;
	private ListComment listComment;
	private ArrayList<CommentStar> comstar;
	private String strstar="";
	private Intent intent;
	private String itemid;
	private boolean isall,isgood,isok,isno,isfinish;
	private Handler handler;
	

	@Override
	protected void onCreate(Bundle arg0) {
		super.onCreate(arg0);
		//		topRightLVisible();
		//		topRightRVisible();
		topRightTGone();
		setTitleTxt(R.string.product_detaile_more_title);
		setContentXml(R.layout.product_detail_more);
		context=this;
		init();
	}
	void init(){
		TestinAgent.init(this);
		isfinish=true;
		type=1;
		page = 1;
		strstar="";
		intent=getIntent();
		itemid=intent.getStringExtra("qingjiamore");
		myPDT=new ThreadWithProgressDialog();
		list=new ArrayList<ProductBean>();
		tv_isnull = (TextView) findViewById(R.id.product_detail_more_tv_isnull);
		listview=(XListView)findViewById(R.id.product_detail_more_list);
		listview.setPullRefreshEnable(true);
		listview.setPullLoadEnable(true);
		listview.setXListViewListener(this);
		listview.setEmptyView(tv_isnull);
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
		if(Util.detect(context)){
			myPDT.Run(context, new RefeshData1(strstar),R.string.loding);//可取消
		}else{
			Util.ShowToast(context, R.string.net_is_eor);
		}
		isall=true;
		isgood=false;
		isok=false;
		isno=false;
		
		
		
		handler = new Handler(){
			@SuppressWarnings("unchecked")
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				if(msg.what==1){
					if(page==1){
						commentBeans = (ArrayList<CommentBean>) msg.obj;
					}else{
						ArrayList<CommentBean> ll = (ArrayList<CommentBean>) msg.obj;
						commentBeans.addAll(ll);
						if(ll.size()==0){
							Util.ShowToast(context, R.string.page_is_final);
							page-=1;
						}
					}
					setpingjiaDate();
				}else if(msg.what==2){
					intent = new Intent(context,LoginActivity.class);
					startActivity(intent);
					finish();
				}else{
					String s = (String) msg.obj;
					Util.ShowToast(context, s);
				}
				onLoad();
			}
		};
		
	}
	private void setRedText(LinearLayout li,TextView tv){
		li.setBackgroundResource(R.drawable.search_lv_notnull_btn_bg);
		tv.setTextColor(getResources().getColor(R.color.white));
	}
	private void setWhiteText(LinearLayout li,TextView tv){
		li.setBackgroundResource(R.drawable.products_et_bg_line);
		tv.setTextColor(getResources().getColor(R.color.red));
	}
	private void setpingjiaDate(){
		if(pingjiaAdapter==null){
			pingjiaAdapter=new ProductPingjiaAdapter(context, commentBeans,-1);
			listview.setAdapter(pingjiaAdapter);
		}else{
			pingjiaAdapter.setData(commentBeans, -1);
			pingjiaAdapter.notifyDataSetChanged();
		}
		if(type==1){
			setRedText(li_all,tv_all);
			setWhiteText(li_good,tv_good);
			setWhiteText(li_ok,tv_ok);
			setWhiteText(li_no,tv_no);
		}else if(type==2){
			setRedText(li_good,tv_good);
			setWhiteText(li_all,tv_all);
			setWhiteText(li_ok,tv_ok);
			setWhiteText(li_no,tv_no);
		}else if(type==3){
			setRedText(li_ok,tv_ok);
			setWhiteText(li_good,tv_good);
			setWhiteText(li_all,tv_all);
			setWhiteText(li_no,tv_no);
		}else if(type==4){
			setRedText(li_no,tv_no);
			setWhiteText(li_good,tv_good);
			setWhiteText(li_ok,tv_ok);
			setWhiteText(li_all,tv_all); 
		}

	}
	private void setStarDate(){
		int starnum=0;
		for(int i=0;i<comstar.size();i++){


			if(comstar.get(i).getStar().equals("1")){
				tv_no.setText("差评("+comstar.get(i).getNumn()+")");
				starnum=starnum+Integer.parseInt(comstar.get(i).getNumn());
			}else if(comstar.get(i).getStar().equals("2")){
				tv_ok.setText("中评("+comstar.get(i).getNumn()+")");
				starnum=starnum+Integer.parseInt(comstar.get(i).getNumn());
			}else if(comstar.get(i).getStar().equals("3")){
				tv_good.setText("好评("+comstar.get(i).getNumn()+")");
				starnum=starnum+Integer.parseInt(comstar.get(i).getNumn());
			}
		}
		tv_all.setText("总评("+starnum+")");
		

	}

	public class RefeshData1 implements ThreadWithProgressDialogTask {
		String star;

		public RefeshData1(String star) {
			this.star=star;
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			//			Toast.makeText(context, "cancle", 1000).show();
			//finish();
			if(isfinish){
				finish();
			}
			return true;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(listComment!=null){
				if("200".equals(listComment.getCode())){
					if(commentBeans==null){
						
						commentBeans=listComment.getComlist();
					}else{
						commentBeans.clear();
						commentBeans.addAll(listComment.getComlist());
					}
					comstar=listComment.getComstar();
					if(type==1){
						if(comstar.size()!=0||comstar!=null){
							setStarDate();
						}
					}
					setpingjiaDate();

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


			isfinish=false;
			return true;
		}

		@Override
		public boolean TaskMain() {
			// 访问
			Send s = new Send(context);
			//			listComment=s.GetComment("53", 1, star);
			listComment=s.GetComment(itemid, page, star);
			return true;
		}
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.product_detaile_ll_add_view_pingjia_costperformace:
			type=1;
			
			strstar="";
			//			if(!isall){
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData1(strstar),R.string.loding);//可取消
			}
			//				isall=true;
			//			}

			break;
		case R.id.product_detaile_ll_add_view_pingjia_zhiliang:
			type=2;
			
			strstar="3";
			//			if(!isgood){
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData1(strstar),R.string.loding);//可取消
			}
			//				isgood=true;
			//			}

			break;
		case R.id.product_detaile_ll_add_view_pingjia_fuwu:
			type=3;
			
			strstar="2";
			//			if(!isok){
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData1(strstar),R.string.loding);//可取消
			}
			//				isok=true;
			//			}

			break;
		case R.id.product_detaile_ll_add_view_pingjia_xiaoguo:
			type=4;
			
			strstar="1";
			//			if(!isno){
			if(Util.detect(context)){
				myPDT.Run(context, new RefeshData1(strstar),R.string.loding);//可取消
			}
			//				isno=true;
			//			}

			break;

		default:
			break;
		}
	}
	@Override
	public void onRefresh() {
		page=1;
		getData();

	}
	@Override
	public void onLoadMore() {
		page+=1;
		getData();
	}

	private void getData(){
		new Thread(){public void run() {
			Send s = new Send(context);
			//			listComment=s.GetComment("53", 1, star);
			listComment=s.GetComment(itemid, page, strstar);
			Message msg = handler.obtainMessage();
			if(listComment!=null){
				if("200".equals(listComment.getCode())){
					ArrayList<CommentBean>lt = listComment.getComlist();
					msg.what=1;
					msg.obj = lt;
				}else{
					msg.what=2;
					msg.obj = listComment.getMsg();
				}
			}else{
				String ss = context.getResources().getString(R.string.net_is_eor);
				msg.what=2;
				msg.obj = ss;
			}
			handler.sendMessage(msg);
		};}.start();
	}
	
	private void onLoad() {
		listview.stopRefresh();
		listview.stopLoadMore();
		SimpleDateFormat sDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd   hh:mm:ss");
		String date = sDateFormat.format(new java.util.Date());
		listview.setRefreshTime(date);
	}



}
