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
//		.showStubImage(R.drawable.default_bg) // 设置图片下载期间显示的图片
		.showImageForEmptyUri(R.drawable.default_bg) // 设置图片Uri为空或是错误的时候显示的图片
		.showImageOnFail(R.drawable.default_bg) // 设置图片加载或解码过程中发生错误显示的图片
		.cacheInMemory(true) // 设置下载的图片是否缓存在内存中
		.cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
		// .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
		.build(); // 创建配置过得DisplayImageOption对象

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
	 * 保存产品到浏览记录
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
	 * 获取浏览记录列表
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
	 * 删除浏览记录
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
