package com.xunbo.store;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleActivator;

import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.util.Log;
import android.widget.Toast;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMChatOptions;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.easemob.chat.OnNotificationClickListener;
import com.xunbo.store.activitys.HomePagerActivity;
import com.xunbo.store.beans.CenterUserBean;
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
	private static String Username ="";
	public static MyApplication mp;
	public static int money_home = 1;
	public static int money_detaile = 2;
	public static int money_income = 3;
	public static int money_pay = 4;
	public boolean islogin;
	private String authstr;



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
