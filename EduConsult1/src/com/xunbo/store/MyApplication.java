package com.xunbo.store;


import java.util.ArrayList;

import org.apkplug.app.FrameworkInstance;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.example.educonsult.R;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.xunbo.store.beans.CenterUserBean;
import com.xunbo.store.beans.ListAreaBean;
import com.xunbo.store.beans.ListProductBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.beans.UserBean;
import com.xunbo.store.tools.Util;

public class MyApplication extends Application{
	private Context context;
	public static SharedPreferences sp;
	public UserBean userbean;
	public CenterUserBean centerUserBean;
	private static Util util;
	public static FrameworkInstance frame=null;
	public static boolean isopen;
	public static String AreaName = "Area";
	public static String FenleiName = "Fenlei";
	public static String Seejilu = "Seejilu";
	@SuppressWarnings("unused")
	private static String Username ="";
	public static MyApplication mp;
	public static int money_home = 1;
	public static int money_detaile = 2;
	public static int money_income = 3;
	public static int money_pay = 4;
	public boolean islogin;
	private String authstr;
	public static ListAreaBean lare;



	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		mp = this;
		sp = getSharedPreferences("mysp", Context.MODE_PRIVATE);
		islogin = sp.getBoolean("islogin", false);
		authstr = sp.getString("authstr", "");
		userbean = new UserBean();
		userbean.setAuthstr(authstr);
		util = new Util(context);
		if(util.isExistDataCache(AreaName)&& util.isReadDataCache(AreaName)){
			lare = (ListAreaBean) util.readObject(AreaName);
		}

		DisplayImageOptions options = new DisplayImageOptions.Builder()
//		.showStubImage(R.drawable.default_bg) // ����ͼƬ�����ڼ���ʾ��ͼƬ
		.showImageForEmptyUri(R.drawable.default_bg) // ����ͼƬUriΪ�ջ��Ǵ����ʱ����ʾ��ͼƬ
		.showImageOnFail(R.drawable.default_bg) // ����ͼƬ���ػ��������з���������ʾ��ͼƬ
		.cacheInMemory(true) // �������ص�ͼƬ�Ƿ񻺴����ڴ���
		.cacheOnDisc(true) // �������ص�ͼƬ�Ƿ񻺴���SD����
		// .displayer(new RoundedBitmapDisplayer(20)) // ���ó�Բ��ͼƬ
		.build(); // �������ù���DisplayImageOption����

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(options)
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);

	}
	public CenterUserBean getCenterUserBean() {
		return centerUserBean;
	}
	public void setCenterUserBean(CenterUserBean centerUserBean) {
		this.centerUserBean = centerUserBean;
	}
	public void setUser(UserBean bean){
		this.userbean = bean;
		Editor er = sp.edit();
		er.putString("authstr", bean.getAuthstr());
		er.commit();
		Seejilu = bean.getUsername();
	}
	public UserBean getUser(){
		return this.userbean;
	}
	public void setlogin(boolean islogin){
		if(islogin){
			this.islogin = islogin;
			Editor er = sp.edit();
			er.putBoolean("islogin", islogin);
			er.commit();
		}else{
			this.islogin = islogin;
			this.userbean.setAuthstr("");
			Editor er = sp.edit();
			er.putBoolean("islogin", islogin);
			er.putString("authstr", "");
			er.commit();
		}
	}
	/**
	 * �����Ʒ�������¼
	 * @param bean
	 */
	public static void SaveSee(ProductBean bean){
		ListProductBean lb = new ListProductBean();
		ArrayList<ProductBean> list = GetSee();
		if(list!=null){
			if(list.size()>=0 && list.size()<30){
				for(int i=0;i<list.size();i++){
					String id = list.get(i).getItemid();
					if(Util.IsNull(bean.getItemid())){
						if(bean.getItemid().equals(id)){
							list.remove(list.get(i));
							break;
						}
					}
				}
				list.add(0, bean);
			}else if(list.size()==30){
				boolean ishave = false;
				for(int i=0;i<list.size();i++){
					String id = list.get(i).getItemid();
					if(Util.IsNull(bean.getItemid())){
						if(!bean.getItemid().equals(id)){
							ishave = false;
						}else{
							ishave = true;
							list.remove(list.get(i));
							break;
						}
					}
				}
				if(!ishave){
					list.remove(list.get(29));
				}
				list.add(0, bean);
			}
		}
		lb.setList(list);
		util.saveObject(lb,Seejilu);
	}
	/**
	 * ��ȡ�����¼�б�
	 * @return
	 */
	public static ArrayList<ProductBean> GetSee(){
		ListProductBean bean;
		ArrayList<ProductBean> list = new ArrayList<ProductBean>();
		if(util.isExistDataCache(Seejilu) && util.isReadDataCache(Seejilu)){
			bean = (ListProductBean) util.readObject(Seejilu);
			if(bean!=null){
				list = bean.getList();
			}
		}
		return list;
	}
	/**
	 * ɾ�������¼
	 * @param l
	 * @return
	 */
	public static ArrayList<ProductBean> deleteSee(ArrayList<String> l){
		ListProductBean lb = new ListProductBean();
		ProductBean bean;
		ArrayList<ProductBean> list =GetSee();
		for(int j=0;j<l.size();j++){
			String sl = l.get(j);
			for(int i=0;i<list.size();i++){
				bean = list.get(i);
				String s = bean.getItemid();
				if(sl.equals(s)){
					list.remove(bean);
					continue;
				}
			}
		}
		lb.setList(list);
		util.saveObject(lb,Seejilu);
		return list;
	}

}
