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

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.educonsult.ExampleActivity;
import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.activitys.GqHomeActivity;
import com.example.educonsult.activitys.GqTwoActivity;
import com.example.educonsult.activitys.HomePagerActivity;
import com.example.educonsult.activitys.KnowHomeActivity;
import com.example.educonsult.activitys.ProductDetaileActivity;
import com.example.educonsult.activitys.XinjianActivity;
import com.example.educonsult.activitys.ZhanhuiHomeActivity;
import com.example.educonsult.adapters.HomeLikeAdapter;
import com.example.educonsult.adapters.HomeRuzhuAdapter;
import com.example.educonsult.beans.ListUserBean;
import com.example.educonsult.myviews.MyGridView;
import com.example.educonsult.tools.FileUtils;
import com.example.educonsult.tools.ImageUtils;
import com.example.educonsult.tools.StringUtils;
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
	private LinearLayout ll_gq,ll_news,ll_know,ll_zhanhui,ll_pinpai,ll_zhaobiao,ll_team,ll_leimu,ll_sl,ll_sy,ll_shebei,
	ll_chuqin,ll_tianjia,ll_yuanliao,ll_tuijian_l,ll_tuijian_one,ll_tuijian_two,ll_tuijian_three,
	ll_tuijian_four,ll_tuijian_b_l,ll_tuijian_b_t,ll_tuijian_b_r,ll_search,
	ll_hot_l,ll_hot_t,ll_hot_r
	,ll_hot_b_l,ll_hot_b_t,ll_hot_b_r;
	private RelativeLayout top_rl;
	private TextView tv_m_jingpin,tv_m_hot,tv_m_ruzhu;
	private ImageView iv_hot_l,iv_fenlei;
	private EditText et_search;
	private Intent intent;
	private FrameworkInstance frame=null;
	private String name = "ChatUIDemo.apk";
	private List<org.osgi.framework.Bundle> bundles=null;
	private String title;
	private ListUserBean listUserBean;
	private Message msg;


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
		top_rl = (RelativeLayout) view.findViewById(R.id.home_top_rl_right);
		Util.SetRedNum(context, top_rl, 1);
		apkFilter apkFilter=new apkFilter(new isFilesFilter(null));
		//		  if(copyApkFromAssets(context, "chatdemo-ui.apk", Environment.getExternalStorageDirectory().getAbsolutePath()+"/chatdemo-ui.apk")){
		//			  Uri u = Uri.parse("file://" + Environment.getExternalStorageDirectory().getAbsolutePath()+"/chatdemo-ui.apk");
		String path = Environment.getExternalStorageDirectory().getPath()+"/"+name;
		try {
			boolean b = Tosd(name,  path);
			//����osgi�����װ����װ���

			boolean a = MyApplication.sp.getBoolean("isinstaed", false); 
			if(b && !a){
				install(path,new myinstallCallback());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		et_search = (EditText) view.findViewById(R.id.title_iv_search);
		et_search.setOnClickListener(this);
		ll_search = (LinearLayout) view.findViewById(R.id.home_ll_top_search);
		ll_search.setOnClickListener(this);
		list = new ArrayList<String>();
		iv_fenlei = (ImageView) view.findViewById(R.id.title_left_iv);
		iv_fenlei.setOnClickListener(this);
		ll_gq = (LinearLayout) view.findViewById(R.id.home_gongqiu_ll);
		ll_gq.setOnClickListener(this);
		ll_news = (LinearLayout) view.findViewById(R.id.home_news_ll);
		ll_news.setOnClickListener(this);
		ll_know = (LinearLayout) view.findViewById(R.id.home_zhidao_ll);
		ll_know.setOnClickListener(this);
		ll_zhanhui = (LinearLayout) view.findViewById(R.id.home_zhanhui_ll);
		ll_zhanhui.setOnClickListener(this);
		ll_pinpai = (LinearLayout) view.findViewById(R.id.home_pinpai_ll);
		ll_pinpai.setOnClickListener(this);
		ll_zhaobiao = (LinearLayout) view.findViewById(R.id.home_zhaobiao_ll);
		ll_zhaobiao.setOnClickListener(this);
		ll_team = (LinearLayout) view.findViewById(R.id.home_team_ll);
		ll_team.setOnClickListener(this);
		ll_leimu = (LinearLayout) view.findViewById(R.id.home_leimu_ll);
		ll_leimu.setOnClickListener(this);
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
		final Util util = new Util(context);
		if(util.isExistDataCache(filename) && util.isReadDataCache(filename)){
			listUserBean = (ListUserBean) util.readObject(filename);
			MyApplication.bean = listUserBean.getList().get(0);
//			Bitmap b = util.getBitmaoForCahe(MyApplication.bean.getBmp());
//			iv_hot_l.setImageBitmap(b);
		}

		
		
		final String url = MyApplication.bean.getBmp();
		
		
		Util.Getbitmap(iv_hot_l, url);



		gv_like = (MyGridView) view.findViewById(R.id.home_ulike_gv);
		likeadapter = new HomeLikeAdapter(context, list);
		gv_ruzhu = (MyGridView) view.findViewById(R.id.home_ruzhu_gv);
		ruzhuadapter = new HomeRuzhuAdapter(context, list);
		gv_like.setAdapter(likeadapter);
		gv_ruzhu.setAdapter(ruzhuadapter);



	}

	private void addlistener() {
		top_rl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intent = new Intent(context,XinjianActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				context.startActivity(intent);

			}
		});
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
		case R.id.title_left_iv:
			msg = HomePagerActivity.handler.obtainMessage();
			msg.obj = HomePagerActivity.SlidTag;
			HomePagerActivity.handler.sendMessage(msg);
			break;
		case R.id.home_gongqiu_ll:
			intent = new Intent(context,GqHomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.home_news_ll:

			break;
		case R.id.home_zhidao_ll:
			intent = new Intent(context,KnowHomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;

		case R.id.home_zhanhui_ll:
			intent = new Intent(context,ZhanhuiHomeActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(intent);
			break;
		case R.id.home_pinpai_ll:

			break;
		case R.id.home_zhaobiao_ll:

			break;

		case R.id.home_team_ll:

			//�Ѱ�װ����б�
			bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
			BundleContext context =frame.getSystemBundleContext();
			for(int i=0;i<context.getBundles().length;i++)
			{
				//��ȡ�Ѱ�װ���
				bundles.add(context.getBundles()[i]);        	        
			}

			//			BundleContext context =frame.getSystemBundleContext();
			startor(bundles);

			break;
		case R.id.home_leimu_ll:
			msg = HomePagerActivity.handler.obtainMessage();
			msg.obj = HomePagerActivity.SlidTag;
			HomePagerActivity.handler.sendMessage(msg);

			break;
		case R.id.home_siliao_ll:
			intent = new Intent(this.context,GqTwoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			title = getResources().getString(R.string.home_siliao);
			intent.putExtra("title", title);
			startActivity(intent);
			break;
		case R.id.home_shouyao_ll:
			intent = new Intent(this.context,GqTwoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			title = getResources().getString(R.string.home_shouyao);
			intent.putExtra("title", title);
			startActivity(intent);
			break;
		case R.id.home_yangzhi_ll:
			intent = new Intent(this.context,GqTwoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			title = getResources().getString(R.string.home_yangzhishebei);
			intent.putExtra("title", title);
			startActivity(intent);
			break;
		case R.id.home_chuqin_ll:
			intent = new Intent(this.context,GqTwoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			title = getResources().getString(R.string.home_chuqinyangzhi);
			intent.putExtra("title", title);
			startActivity(intent);
			break;
		case R.id.home_tianjiaji_ll:
			intent = new Intent(this.context,GqTwoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			title = getResources().getString(R.string.home_tianjiaji);
			intent.putExtra("title", title);
			startActivity(intent);
			break;
		case R.id.home_yuanliao_ll:
			intent = new Intent(this.context,GqTwoActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			title = getResources().getString(R.string.home_yuanliao);
			intent.putExtra("title", title);
			startActivity(intent);
			break;
		case R.id.home_tv_jingpin:
			//			intent = new Intent(this.context,GqTwoActivity.class);
			//			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//			title = getResources().getString(R.string.home_tuijian);
			//			intent.putExtra("title", title);
			//			startActivity(intent);
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
		case R.id.home_ll_top_search:
			ExampleActivity.setCurrentTab(1);
			break;
		case R.id.title_iv_search:
			ExampleActivity.setCurrentTab(1);
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
	 * ��װ����ص�����
	 */
	class myinstallCallback implements installCallback{

		@Override
		public void callback(int arg0, org.osgi.framework.Bundle arg1) {
			if(arg0==installCallback.stutas5||arg0==installCallback.stutas7){
				Editor et = MyApplication.sp.edit();
				et.putBoolean("isinstaed", true);
				et.commit();
				String s =String.format("�����װ %s ��\n %s",stutasToStr(arg0),showBundle(arg1));
				Toast.makeText(context, s, 200).show();
				System.out.println(s);
				return;
			}
			else{
				String info1="��װ״̬:"+arg0;
				String s ="�����װʧ�� ��"+this.stutasToStr(arg0);
				Toast.makeText(context, s, 3000).show();
				System.out.println(s);

			}

		}
		/**
		 * ��Ϣ�� http://www.apkplug.com/javadoc/bundledoc1.5.3/
		 * org.apkplug.Bundle 
		 *      �ӿ� installCallback �ṩ
		 * @param stutas
		 * @return
		 */
		private String stutasToStr(int stutas){
			if(stutas==installCallback.stutas){
				return "ȱ��SymbolicName";
			}else if(stutas==installCallback.stutas1){
				return "�������°汾";
			}else if(stutas==installCallback.stutas2){
				return "�汾�Ų���ȷ";
			}else if(stutas==installCallback.stutas3){
				return " �汾���";
			}else if(stutas==installCallback.stutas4){
				return "�޷���ȡ��ȷ��֤��";
			}else if(stutas==installCallback.stutas5){
				return "���³ɹ�";
			}else if(stutas==installCallback.stutas6){
				return "֤�鲻һ��";
			}else if(stutas==installCallback.stutas7){
				return "��װ�ɹ�";
			}
			return "״̬��Ϣ����ȷ";
		}
	}

	public String showBundle(org.osgi.framework.Bundle b){
		StringBuffer sb=new StringBuffer();
		sb.append("\n�������:"+b.getName());
		sb.append("\n���Ӧ������:"+b.getSymbolicName());
		sb.append("\n����汾:"+b.getVersion());
		sb.append("\n���ID:"+b.getBundleId());
		sb.append("\n�����ǰ״̬:"+b.getState());
		sb.append("\n�������Activity:"+b.getBundleActivity());
		return sb.toString();
	}
	public void startor(List<org.osgi.framework.Bundle> list){
		org.osgi.framework.Bundle bundle=list.get(1);
		if(bundle.getState()!=bundle.ACTIVE){
			//�жϲ���Ƿ�������
			try {
				bundle.start();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(bundle.getBundleActivity()!=null){
			//			Toast.makeText(context, "����"+bundle.getBundleActivity().split(",")[0],
			//				     Toast.LENGTH_SHORT).show();
			Intent i=new Intent();
			i.setClassName(context, bundle.getBundleActivity().split(",")[0]);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}else{

			Toast.makeText(context, "�ò��û������BundleActivity",
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
	 * ��װ���ز���������
	 * @param path
	 * @param callback   ��װ����Ļص�����
	 * @throws Exception
	 */
	public void install(String path,installCallback callback) throws Exception{
		System.out.println("��װ :"+path);
		BundleContext mcontext=frame.getSystemBundleContext();
		OSGIServiceAgent<BundleControl> agent=new OSGIServiceAgent<BundleControl>(mcontext,BundleControl.class);
		//�����������Ϊ1(������) ���Ҳ�������汾�Ƿ���ͬ����װ
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
			//�Ѱ�װ����б�
			bundles=new java.util.ArrayList<org.osgi.framework.Bundle>();
			BundleContext context =frame.getSystemBundleContext();
			for(int i=0;i<context.getBundles().length;i++)
			{
				//��ȡ�Ѱ�װ���
				bundles.add(context.getBundles()[i]);        	        
			}

			//			BundleContext context =frame.getSystemBundleContext();
			startor(bundles);
			//			MyApplication.isopen = f
		}
		super.onResume();
	}

}
