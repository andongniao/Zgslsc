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
		//		List<BundleActivator> list = new ArrayList<BundleActivator>();
		//		SimpleBundle s= new SimpleBundle();
		//		list.add(s);
		//		EMChat.getInstance().init(context);
		// ��ȡ��EMChatOptions����

		//		//ֻ��ע���˹㲥���ܽ��յ�����Ϣ��Ŀǰ������Ϣ��������Ϣ�����߽�����Ϣ�Ĺ㲥��������ϢĿǰ�޷��������ڵ�¼�Ժ󣬽�����Ϣ�㲥��ִ��һ���õ����е�������Ϣ��
		//		NewMessageBroadcastReceiver msgReceiver = new NewMessageBroadcastReceiver();
		//		IntentFilter intentFilter = new IntentFilter(EMChatManager.getInstance().getNewMessageBroadcastAction());
		//		intentFilter.setPriority(3);
		//		registerReceiver(msgReceiver, intentFilter);


		//		EMChatManager.getInstance().getChatOptions().setRequireAck(flag);
		//		//����õ��Ѷ��Ļ�ִ��Ҫ�����flag���ó�true
		//
		//		IntentFilter ackMessageIntentFilter = new IntentFilter(EMChatManager.getInstance().getAckMessageBroadcastAction());
		//		ackMessageIntentFilter.setPriority(3);
		//		registerReceiver(ackMessageReceiver, ackMessageIntentFilter);
		//		EMChat.getInstance().setAppInited();


		//		try
		//		{
		//			//�������
		//			//�ĵ��� http://www.apkplug.com/javadoc/Maindoc1.4.6/
		//			//org.apkplug.app 
		//			//     �ӿ� FrameworkInstance
		//			Log.e("start", "fsfsdafasfsa");
		//			//				frame=FrameworkFactory.getInstance().start(list, context);
		//			frame=FrameworkFactory.getInstance().start(null,this);
		//			//				System.out.println("ProxyApplication1");
		//			//				BundleContext context =frame.getSystemBundleContext();
		//			//				// InstallBundler ��2.7.0�汾���������е�
		//			//				InstallBundler ib=new InstallBundler(context);
		//			//				ib.installForAssets("chatdemo-ui.apk", "1.0.0", null,
		//			//						new installCallback(){
		//			//						@Override
		//			//						public void callback(int arg0, Bundle arg1) {
		//			//							if(arg0==installCallback.stutas5||arg0==installCallback.stutas7){
		//			//								Log.d("",String.format("�����װ %s �� %d",arg1.getName(),arg0));
		//			//								return;
		//			//							}
		//			//							else{
		//			//								Log.d("","�����װʧ�� ��%d"+arg0);
		//			//							}
		//			//						}
		//			//						});
		//			//				ib.installForAssets("BundleDemoShow.apk", "1.0.0", null,null);
		//			//				ib.installForAssets("BundleDemoStartActivity1.apk", "1.0.0", null,null);
		//		}
		//		catch (Exception ex)
		//		{
		//			System.err.println("Could not create : " + ex);
		//			//	            ex.printStackTrace();
		//			//	            int nPid = android.os.Process.myPid();
		//			//				android.os.Process.killProcess(nPid);
		//		}
		//
		//
		//		EMChatOptions options = EMChatManager.getInstance().getChatOptions();
		//		//����notification���listener
		//		options.setOnNotificationClickListener(new OnNotificationClickListener() {
		//
		//			@Override
		//			public Intent onNotificationClick(EMMessage message) {
		//				Intent i=new Intent();
		//				//					i.setClassName(MyApplication.this.context.getPackageName(), bundle.getBundleActivity().split(",")[0]);
		//				//					i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//				i = new Intent(MyApplication.this.context,HomePagerActivity.class);
		//				isopen = true;
		//
		//				//					ChatType chatType = message.getChatType();
		//				//					if(chatType == ChatType.Chat){ //������Ϣ
		//				//						intent.putExtra("userId", message.getFrom());
		//				////						intent.putExtra("chatType", ChatActivity.CHATTYPE_SINGLE);
		//				//					}else{ //Ⱥ����Ϣ
		//				//						//message.getTo()ΪȺ��id
		//				//						intent.putExtra("groupId", message.getTo());
		//				////						intent.putExtra("chatType", ChatActivity.CHATTYPE_GROUP);
		//				//					}
		//				return i;
		//			}
		//		});
		//
		//	}
		//	public FrameworkInstance getFrame() {
		//		return frame;
		//	}
		//
		//	private class NewMessageBroadcastReceiver extends BroadcastReceiver {
		//		@Override
		//		public void onReceive(Context context, Intent intent) {
		//			// ע���㲥
		//			abortBroadcast();
		//
		//			// ��Ϣid��ÿ����Ϣ��������Ψһ��һ��id��Ŀǰ��SDK���ɣ�
		//			String msgId = intent.getStringExtra("msgid");
		//			//���ͷ�
		//			String username = intent.getStringExtra("from");
		//			// �յ�����㲥��ʱ��message�Ѿ���db���ڴ����ˣ�����ͨ��id��ȡmesage����
		//			EMMessage message = EMChatManager.getInstance().getMessage(msgId);
		//			EMConversation	conversation = EMChatManager.getInstance().getConversation(username);
		//			// �����Ⱥ����Ϣ����ȡ��group id
		//			if (message.getChatType() == ChatType.GroupChat) {
		//				username = message.getTo();
		//			}
		//			if (!username.equals(username)) {
		//				// ��Ϣ���Ƿ�����ǰ�Ự��return
		//				return;
		//			}
		//		}
		//	}
		//	private BroadcastReceiver ackMessageReceiver = new BroadcastReceiver() {
		//
		//		@Override
		//		public void onReceive(Context context, Intent intent) {
		//			abortBroadcast();
		//			String msgid = intent.getStringExtra("msgid");
		//			String from = intent.getStringExtra("from");
		//			EMConversation conversation = EMChatManager.getInstance().getConversation(from);
		//			if (conversation != null) {
		//				// ��message��Ϊ�Ѷ�
		//				EMMessage msg = conversation.getMessage(msgid);
		//				if (msg != null) {
		//					msg.isAcked = true;
		//				}
		//			}
		//		}
		//	};
		//
		//	private String getAppName(int pID) {
		//		String processName = null;
		//		ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
		//		List l = am.getRunningAppProcesses();
		//		Iterator i = l.iterator();
		//		PackageManager pm = this.getPackageManager();
		//		while (i.hasNext()) {
		//			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
		//			try {
		//				if (info.pid == pID) {
		//					CharSequence c = pm.getApplicationLabel(pm.getApplicationInfo(info.processName, PackageManager.GET_META_DATA));
		//					// Log.d("Process", "Id: "+ info.pid +" ProcessName: "+
		//					// info.processName +"  Label: "+c.toString());
		//					// processName = c.toString();
		//					processName = info.processName;
		//					return processName;
		//				}
		//			} catch (Exception e) {
		//				// Log.d("Process", "Error>> :"+ e.toString());
		//			}
		//		}
		//		return processName;
		//	}
		//
		//	public void startor(List<org.osgi.framework.Bundle> list){
		//		org.osgi.framework.Bundle bundle=list.get(1);
		//		if(bundle.getState()!=bundle.ACTIVE){
		//			//�жϲ���Ƿ�������
		//			try {
		//				bundle.start();
		//			} catch (Exception e) {
		//				// TODO Auto-generated catch block
		//				e.printStackTrace();
		//			}
		//		}
		//		if(bundle.getBundleActivity()!=null){
		//			Toast.makeText(context, "����"+bundle.getBundleActivity().split(",")[0],
		//					Toast.LENGTH_SHORT).show();
		//			Intent i=new Intent();
		//			i.setClassName(context, bundle.getBundleActivity().split(",")[0]);
		//			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		//			context.startActivity(i);
		//		}else{
		//
		//			Toast.makeText(context, "�ò��û������BundleActivity",
		//					Toast.LENGTH_SHORT).show();
		//		}
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
