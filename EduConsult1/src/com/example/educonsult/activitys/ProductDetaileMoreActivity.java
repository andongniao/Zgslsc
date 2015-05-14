package com.example.educonsult.activitys;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialog;
import com.LibLoading.LibThreadWithProgressDialog.ThreadWithProgressDialogTask;
import com.example.educonsult.R;
import com.example.educonsult.activitys.ProductDetaileActivity.RefeshData1;
import com.example.educonsult.adapters.ProductPingjiaAdapter;
import com.example.educonsult.beans.CommentBean;
import com.example.educonsult.beans.CommentStar;
import com.example.educonsult.beans.ListComment;
import com.example.educonsult.beans.ProductBean;
import com.example.educonsult.net.Send;
import com.example.educonsult.tools.Util;



public class ProductDetaileMoreActivity extends BaseActivity implements OnClickListener{
	private ListView listview;
	private ProductPingjiaAdapter pingjiaAdapter;
	private Context context;
	private ArrayList<ProductBean> list;
	private ArrayList<CommentBean>commentBeans;
	private int type=1;
	private TextView tv_all,tv_good,tv_ok,tv_no;
	private LinearLayout li_all,li_good,li_ok,li_no;
	private ThreadWithProgressDialog myPDT;
	private ListComment listComment;
	private ArrayList<CommentStar> comstar;
	private String strstar="";
	private Intent intent;
	private String itemid;
	private boolean isall,isgood,isok,isno;
	
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
		intent=getIntent();
		itemid=intent.getStringExtra("qingjiamore");
		list=new ArrayList<ProductBean>();
		listview=(ListView)findViewById(R.id.product_detail_more_list);
		myPDT=new ThreadWithProgressDialog();
		
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
			myPDT.Run(context, new RefeshData(),R.string.loding);//可取消
		}
		isall=true;
		isgood=false;
		isok=false;
		isno=false;
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
		
		pingjiaAdapter=new ProductPingjiaAdapter(context, commentBeans,-1);
		listview.setAdapter(pingjiaAdapter);
		
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
	public class RefeshData implements ThreadWithProgressDialogTask {
	

		public RefeshData() {
			
		}

		@Override
		public boolean OnTaskDismissed() {
			//任务取消
			//			Toast.makeText(context, "cancle", 1000).show();
			//finish();
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(listComment!=null){
				if("200".equals(listComment.getCode())){
					commentBeans=listComment.getComlist();
					comstar=listComment.getComstar();
					if(comstar.size()!=0||comstar!=null){
						setStarDate();
						setpingjiaDate();
					}
					
				}else{
					Util.ShowToast(context, listComment.getMsg());
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
			listComment=s.GetComment(itemid, 1, "");
			return true;
		}
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
			return false;
		}

		@Override
		public boolean OnTaskDone() {
			//任务完成后
			if(listComment!=null){
				if("200".equals(listComment.getCode())){
					commentBeans=listComment.getComlist();
					comstar=listComment.getComstar();
					setpingjiaDate();
					
				}else{
					Util.ShowToast(context, listComment.getMsg());
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
			listComment=s.GetComment("53", 1, star);
			//listComment=s.GetComment(itemid, 1, star);
			return true;
		}
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
			strstar="";
			if(!isall){
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData1(strstar),R.string.loding);//可取消
				}
				isall=true;
			}
			
			break;
		case R.id.product_detaile_ll_add_view_pingjia_zhiliang:
			type=2;
			setRedText(li_good,tv_good);
			setWhiteText(li_all,tv_all);
			setWhiteText(li_ok,tv_ok);
			setWhiteText(li_no,tv_no);
			strstar="3";
			if(!isgood){
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData1(strstar),R.string.loding);//可取消
				}
				isgood=true;
			}
			
			break;
		case R.id.product_detaile_ll_add_view_pingjia_fuwu:
			type=3;
			setRedText(li_ok,tv_ok);
			setWhiteText(li_good,tv_good);
			setWhiteText(li_all,tv_all);
			setWhiteText(li_no,tv_no);
			strstar="2";
			if(!isok){
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData1(strstar),R.string.loding);//可取消
				}
				isok=true;
			}
			
			break;
		case R.id.product_detaile_ll_add_view_pingjia_xiaoguo:
			type=4;
			setRedText(li_no,tv_no);
			setWhiteText(li_good,tv_good);
			setWhiteText(li_ok,tv_ok);
			setWhiteText(li_all,tv_all);
			strstar="1";
			if(!isno){
				if(Util.detect(context)){
					myPDT.Run(context, new RefeshData1(strstar),R.string.loding);//可取消
				}
				isno=true;
			}
			
			break;

		default:
			break;
		}
	}
	


}
