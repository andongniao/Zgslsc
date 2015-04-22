package com.example.educonsult.fragments;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apkplug.Bundle.BundleControl;
import org.apkplug.Bundle.OSGIServiceAgent;
import org.apkplug.Bundle.installCallback;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleContext;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.activitys.GqHomeActivity;
import com.example.educonsult.activitys.HomePagerActivity;
import com.example.educonsult.activitys.KnowHomeActivity;
import com.example.educonsult.activitys.ProductDetaileActivity;
import com.example.educonsult.activitys.ZhanhuiHomeActivity;
import com.example.educonsult.adapters.HomeLikeAdapter;
import com.example.educonsult.adapters.HomeRuzhuAdapter;
import com.example.educonsult.myviews.MyGridView;
import com.example.educonsult.tools.Util;
import com.example.educonsult.tools.FileUtil.filter.apkFilter;
import com.example.educonsult.tools.FileUtil.filter.isFilesFilter;

public class HomeFragment extends Fragment implements OnClickListener{
	private MyGridView gv_like,gv_ruzhu,gv;
	private View view;
	private HomeLikeAdapter likeadapter;
	private HomeRuzhuAdapter ruzhuadapter;
	private ArrayList<String> list;
	private Context context;
	private LinearLayout ll_gq,ll_cx,ll_pp,ll_zh,ll_zx,ll_zd,ll_free,ll_lm,ll_sl,ll_sy,ll_shebei,
	ll_chuqin,ll_tianjia,ll_yuanliao,ll_tuijian_l,ll_tuijian_one,ll_tuijian_two,ll_tuijian_three,
	ll_tuijian_four,ll_tuijian_b_l,ll_tuijian_b_t,ll_tuijian_b_r,
	ll_hot_l,ll_hot_t,ll_hot_r
	,ll_hot_b_l,ll_hot_b_t,ll_hot_b_r;
	private TextView tv_m_jingpin,tv_m_hot,tv_m_ruzhu;
	private ImageView iv_hot_l;
	private Intent intent;
	private FrameworkInstance frame=null;
	private String name = "ChatUIDemo.apk";
	private List<org.osgi.framework.Bundle> bundles=null;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.homefragment, container, false);
		init();
		addlistener();
		return view;
	}


	private void init() {
		context = getActivity();
		frame=MyApplication.frame;
		
		apkFilter apkFilter=new apkFilter(new isFilesFilter(null));
//		  if(copyApkFromAssets(context, "chatdemo-ui.apk", Environment.getExternalStorageDirectory().getAbsolutePath()+"/chatdemo-ui.apk")){
//			  Uri u = Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsolutePath()+"/chatdemo-ui.apk");
		String path = Environment.getExternalStorageDirectory().getPath()+"/"+name;
		try {
			boolean b = Tosd(name,  path);
			//调用osgi插件安装服务安装插件
			
			boolean a = MyApplication.sp.getBoolean("isinstaed", false); 
			if(b && !a){
			install(path,new myinstallCallback());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		list = new ArrayList<String>();
		ll_gq = (LinearLayout) view.findViewById(R.id.home_gongqiu_ll);
		ll_gq.setOnClickListener(this);
		ll_cx = (LinearLayout) view.findViewById(R.id.home_cuxiao_ll);
		ll_cx.setOnClickListener(this);
		ll_pp = (LinearLayout) view.findViewById(R.id.home_pinpai_ll);
		ll_pp.setOnClickListener(this);
		ll_zh = (LinearLayout) view.findViewById(R.id.home_zhanhui_ll);
		ll_zh.setOnClickListener(this);
		ll_zx = (LinearLayout) view.findViewById(R.id.home_zixun_ll);
		ll_zx.setOnClickListener(this);
		ll_zd = (LinearLayout) view.findViewById(R.id.home_zhidao_ll);
		ll_zd.setOnClickListener(this);
		ll_free = (LinearLayout) view.findViewById(R.id.home_mianfei_ll);
		ll_free.setOnClickListener(this);
		ll_lm = (LinearLayout) view.findViewById(R.id.home_leimu_ll);
		ll_lm.setOnClickListener(this);
		ll_sl = (LinearLayout) view.findViewById(R.id.home_siliao_ll);
		ll_sl.setOnClickListener(this);
		ll_sy = (LinearLayout) view.findViewById(R.id.home_shouyao_ll);
		ll_sy.setOnClickListener(this);
		ll_shebei = (LinearLayout) view.findViewById(R.id.home_yangzhi_ll);
		ll_shebei.setOnClickListener(this);
		ll_chuqin = (LinearLayout) view.findViewById(R.id.home_chuqin_ll);
		ll_chuqin.setOnClickListener(this);
		ll_tianjia = (LinearLayout) view.findViewById(R.id.home_tianjiaji_ll);
		ll_tianjia.setOnClickListener(this);
		ll_yuanliao = (LinearLayout) view.findViewById(R.id.home_yuanliao_ll);
		ll_yuanliao.setOnClickListener(this);
		tv_m_jingpin = (TextView) view.findViewById(R.id.home_tv_jingpin);
		tv_m_jingpin.setOnClickListener(this);
		tv_m_hot = (TextView) view.findViewById(R.id.home_tv_hot);
		tv_m_hot.setOnClickListener(this);
		tv_m_ruzhu = (TextView) view.findViewById(R.id.home_tv_ruzhu);
		tv_m_ruzhu.setOnClickListener(this);


		ll_tuijian_l = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_l);
		ll_tuijian_l.setOnClickListener(this);
		ll_tuijian_one = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_l_one);
		ll_tuijian_one.setOnClickListener(this);
		ll_tuijian_two = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_l_two);
		ll_tuijian_two.setOnClickListener(this);
		ll_tuijian_three = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_l_three);
		ll_tuijian_three.setOnClickListener(this);
		ll_tuijian_four = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_l_four);
		ll_tuijian_four.setOnClickListener(this);
		ll_tuijian_b_l = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_b_l);
		ll_tuijian_b_l.setOnClickListener(this);
		ll_tuijian_b_t = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_b_t);
		ll_tuijian_b_t.setOnClickListener(this);
		ll_tuijian_b_r = (LinearLayout) view.findViewById(R.id.home_ll_tuijian_b_r);
		ll_tuijian_b_r.setOnClickListener(this);

		ll_hot_l = (LinearLayout) view.findViewById(R.id.home_ll_hot_l);
		ll_hot_l.setOnClickListener(this);
		ll_hot_t = (LinearLayout) view.findViewById(R.id.home_ll_hot_t);
		ll_hot_t.setOnClickListener(this);
		ll_hot_r = (LinearLayout) view.findViewById(R.id.home_ll_hot_r);
		ll_hot_r.setOnClickListener(this);
		ll_hot_b_l = (LinearLayout) view.findViewById(R.id.home_ll_hot_b_l);
		ll_hot_b_l.setOnClickListener(this);
		ll_hot_b_t = (LinearLayout) view.findViewById(R.id.home_ll_hot_b_t);
		ll_hot_b_t.setOnClickListener(this);
		ll_hot_b_r = (LinearLayout) view.findViewById(R.id.home_ll_hot_b_r);
		ll_hot_b_r.setOnClickListener(this);
		iv_hot_l = (ImageView) view.findViewById(R.id.home_iv_hot_l);
		String filename = "test";
		Util util = new Util(context);
		if(util.isExistDataCache(filename) && util.isReadDataCache(filename)){
		Bitmap b = util.getBitmaoForCahe(MyApplication.bean.getBmp());
		iv_hot_l.setImageBitmap(b);
		}
		
		
		


		gv_like = (MyGridView) view.findViewById(R.id.home_ulike_gv);
		likeadapter = new HomeLikeAdapter(context, list);
		gv_ruzhu = (MyGridView) view.findViewById(R.id.home_ruzhu_gv);
		ruzhuadapter = new HomeRuzhuAdapter(context, list);
		gv_like.setAdapter(likeadapter);
		gv_ruzhu.setAdapter(ruzhuadapter);



	}

	private void addlistener() {
		gv_like.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toproduct();
			}
		});
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.home_gongqiu_ll:
			intent = new Intent(context,GqHomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.home_cuxiao_ll:

			break;
		case R.id.home_pinpai_ll:

			break;
		case R.id.home_zhanhui_ll:
			intent = new Intent(context,ZhanhuiHomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.home_zixun_ll:

			break;
		case R.id.home_zhidao_ll:
			intent = new Intent(context,KnowHomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.home_mianfei_ll:
//			intent = new Intent(context,apkplugActivity.class);
//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//			startActivity(intent);
			
			 //已安装插件列表
			bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
			BundleContext context =frame.getSystemBundleContext();
			for(int i=0;i<context.getBundles().length;i++)
			{
				//获取已安装插件
				bundles.add(context.getBundles()[i]);        	        
			}
			
//			BundleContext context =frame.getSystemBundleContext();
			startor(bundles);
			
			break;
		case R.id.home_leimu_ll:
			Message msg = HomePagerActivity.handler.obtainMessage();
			msg.obj = HomePagerActivity.SlidTag;
			HomePagerActivity.handler.sendMessage(msg);

			break;
		case R.id.home_siliao_ll:

			break;
		case R.id.home_shouyao_ll:

			break;
		case R.id.home_yangzhi_ll:

			break;
		case R.id.home_chuqin_ll:

			break;
		case R.id.home_tianjiaji_ll:

			break;
		case R.id.home_yuanliao_ll:

			break;
		case R.id.home_tv_jingpin:

			break;
		case R.id.home_tv_hot:

			break;
		case R.id.home_tv_ruzhu:
			
			break;


		case R.id.home_ll_tuijian_l:
			Toproduct();
			break;
		case R.id.home_ll_tuijian_l_one:
			Toproduct();
			break;
		case R.id.home_ll_tuijian_l_two:
			Toproduct();
			break;
		case R.id.home_ll_tuijian_l_three:
			Toproduct();
			break;
		case R.id.home_ll_tuijian_l_four:
			Toproduct();
			break;
		case R.id.home_ll_tuijian_b_l:
			Toproduct();
			break;
		case R.id.home_ll_tuijian_b_t:
			Toproduct();
			break;
		case R.id.home_ll_tuijian_b_r:
			Toproduct();
			break;
			
			
			
		case R.id.home_ll_hot_l:
			Toproduct();
			break;

		case R.id.home_ll_hot_t:
			Toproduct();
			break;
		case R.id.home_ll_hot_r:
			Toproduct();
			break;
		case R.id.home_ll_hot_b_l:
			Toproduct();
			break;
		case R.id.home_ll_hot_b_t:
			Toproduct();
			break;
		case R.id.home_ll_hot_b_r:
			Toproduct();
			break;






		}
	}
	

	private void Toproduct(){
		intent = new Intent(context,ProductDetaileActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
	}
	
	/**
	 * 安装插件回调函数
	 */
	class myinstallCallback implements installCallback{

		@Override
		public void callback(int arg0, org.osgi.framework.Bundle arg1) {
			if(arg0==installCallback.stutas5||arg0==installCallback.stutas7){
				Editor et = MyApplication.sp.edit();
				et.putBoolean("isinstaed", true);
				et.commit();
				String s =String.format("插件安装 %s ：\n %s",stutasToStr(arg0),showBundle(arg1));
				Toast.makeText(context, s, 200).show();
				System.out.println(s);
				return;
			}
			else{
				String info1="安装状态:"+arg0;
				String s ="插件安装失败 ："+this.stutasToStr(arg0);
				Toast.makeText(context, s, 3000).show();
				System.out.println(s);
			
			}
			
		}
		/**
		 * 信息由 http://www.apkplug.com/javadoc/bundledoc1.5.3/
		 * org.apkplug.Bundle 
		 *      接口 installCallback 提供
		 * @param stutas
		 * @return
		 */
		private String stutasToStr(int stutas){
			if(stutas==installCallback.stutas){
				return "缺少SymbolicName";
			}else if(stutas==installCallback.stutas1){
				return "已是最新版本";
			}else if(stutas==installCallback.stutas2){
				return "版本号不正确";
			}else if(stutas==installCallback.stutas3){
				return " 版本相等";
			}else if(stutas==installCallback.stutas4){
				return "无法获取正确的证书";
			}else if(stutas==installCallback.stutas5){
				return "更新成功";
			}else if(stutas==installCallback.stutas6){
				return "证书不一致";
			}else if(stutas==installCallback.stutas7){
				return "安装成功";
			}
			return "状态信息不正确";
		}
	}
	
	public String showBundle(org.osgi.framework.Bundle b){
		StringBuffer sb=new StringBuffer();
		sb.append("\n插件名称:"+b.getName());
		sb.append("\n插件应用名称:"+b.getSymbolicName());
		sb.append("\n插件版本:"+b.getVersion());
		sb.append("\n插件ID:"+b.getBundleId());
		sb.append("\n插件当前状态:"+b.getState());
		sb.append("\n插件启动Activity:"+b.getBundleActivity());
		return sb.toString();
	}
	public void startor(List<org.osgi.framework.Bundle> list){
		org.osgi.framework.Bundle bundle=list.get(1);
		if(bundle.getState()!=bundle.ACTIVE){
			//判断插件是否已启动
			try {
				bundle.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(bundle.getBundleActivity()!=null){
//			Toast.makeText(context, "启动"+bundle.getBundleActivity().split(",")[0],
//				     Toast.LENGTH_SHORT).show();
			Intent i=new Intent();
			i.setClassName(context, bundle.getBundleActivity().split(",")[0]);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}else{
			
			Toast.makeText(context, "该插件没有配置BundleActivity",
				     Toast.LENGTH_SHORT).show();
		}
	}
	
	
	public boolean copyApkFromAssets(Context context, String fileName, String path) {  
        boolean copyIsFinish = false;  
        try {  
            InputStream is = context.getAssets().open(fileName);  
            File file = new File(path);  
            file.createNewFile();  
            FileOutputStream fos = new FileOutputStream(file);  
            byte[] temp = new byte[1024];  
            int i = 0;  
            while ((i = is.read(temp)) > 0) {  
                fos.write(temp, 0, i);  
            }  
            fos.close();  
            is.close();  
            copyIsFinish = true;  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return copyIsFinish;  
    }  
	public String getRealPathFromURI(Uri contentUri) {
	    String res = null;
	    String[] proj = { MediaStore.Images.Media.DATA };
	    Cursor cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
	    if(cursor.moveToFirst()){;
	       int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	       res = cursor.getString(column_index);
	    }
	    cursor.close();
	    return res;
	}
	/**
	  * 安装本地插件服务调用
	  * @param path
	  * @param callback   安装插件的回掉函数
	  * @throws Exception
	  */
	 public void install(String path,installCallback callback) throws Exception{
		 System.out.println("安装 :"+path);
		 BundleContext mcontext=frame.getSystemBundleContext();
		 OSGIServiceAgent<BundleControl> agent=new OSGIServiceAgent<BundleControl>(mcontext,BundleControl.class);
		//插件启动级别为1(会自启) 并且不检查插件版本是否相同都安装
		 agent.getService().install(mcontext, path,callback, 1,false,false,false);
	}
	public boolean Tosd(String fileName,String path) {
		InputStream is;
		try {
			is = context.getAssets().open(fileName);
		File file = new File(path);
		file.createNewFile();
		FileOutputStream fos = new FileOutputStream(file);
		byte[] temp = new byte[1024];
		int i = 0;
		while ((i = is.read(temp)) > 0) {
		fos.write(temp, 0, i);
		}
		fos.close();
		is.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	} 
	@Override
	public void onResume() {
		if(MyApplication.isopen){
			 //已安装插件列表
			bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
			BundleContext context =frame.getSystemBundleContext();
			for(int i=0;i<context.getBundles().length;i++)
			{
				//获取已安装插件
				bundles.add(context.getBundles()[i]);        	        
			}
			
//			BundleContext context =frame.getSystemBundleContext();
			startor(bundles);
//			MyApplication.isopen = f
		}
		super.onResume();
	}
	
	
}
