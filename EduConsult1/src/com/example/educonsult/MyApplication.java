package com.example.educonsult;


import java.util.ArrayList;
import java.util.List;

import org.apkplug.app.FrameworkFactory;
import org.apkplug.app.FrameworkInstance;
import org.osgi.framework.BundleActivator;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.util.Log;

import com.easemob.chat.EMChat;
import com.easemob.chat.EMChatManager;
import com.easemob.chat.EMConversation;
import com.easemob.chat.EMMessage;
import com.easemob.chat.EMMessage.ChatType;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.tools.Util;

public class MyApplication extends Application{
	private Context context;
	public static SharedPreferences sp;
	public static UserBean bean;
	private static Util util;
	public static FrameworkInstance frame=null;
	@Override
	public void onCreate() {
		super.onCreate();
		context = this;
		sp = getSharedPreferences("mysp", Context.MODE_PRIVATE);
		bean = new UserBean();
		util = new Util(context);
		List<BundleActivator> list = new ArrayList<BundleActivator>();
		SimpleBundle s= new SimpleBundle();
		list.add(s);
		EMChat.getInstance().init(context);
//		//只有注册了广播才能接收到新消息，目前离线消息，在线消息都是走接收消息的广播（离线消息目前无法监听，在登录以后，接收消息广播会执行一次拿到所有的离线消息）
//		NewMessageBroadcastReceiver msgReceiver = new NewMessageBroadcastReceiver();
//		IntentFilter intentFilter = new IntentFilter(EMChatManager.getInstance().getNewMessageBroadcastAction());
//		intentFilter.setPriority(3);
//		registerReceiver(msgReceiver, intentFilter);

		
//		EMChatManager.getInstance().getChatOptions().setRequireAck(flag);
//		//如果用到已读的回执需要把这个flag设置成true
//
//		IntentFilter ackMessageIntentFilter = new IntentFilter(EMChatManager.getInstance().getAckMessageBroadcastAction());
//		ackMessageIntentFilter.setPriority(3);
//		registerReceiver(ackMessageReceiver, ackMessageIntentFilter);
//		EMChat.getInstance().setAppInited();
	

		 try
	        {
	        	//启动框架
				//文档见 http://www.apkplug.com/javadoc/Maindoc1.4.6/
				//org.apkplug.app 
			 	//     接口 FrameworkInstance
			 	Log.e("start", "fsfsdafasfsa");
//				frame=FrameworkFactory.getInstance().start(list, context);
				frame=FrameworkFactory.getInstance().start(null,this);
//				System.out.println("ProxyApplication1");
//				BundleContext context =frame.getSystemBundleContext();
//				// InstallBundler 是2.7.0版本内置与框架中的
//				InstallBundler ib=new InstallBundler(context);
//				ib.installForAssets("chatdemo-ui.apk", "1.0.0", null,
//						new installCallback(){
//						@Override
//						public void callback(int arg0, Bundle arg1) {
//							if(arg0==installCallback.stutas5||arg0==installCallback.stutas7){
//								Log.d("",String.format("插件安装 %s ： %d",arg1.getName(),arg0));
//								return;
//							}
//							else{
//								Log.d("","插件安装失败 ：%d"+arg0);
//							}
//						}
//						});
//				ib.installForAssets("BundleDemoShow.apk", "1.0.0", null,null);
//				ib.installForAssets("BundleDemoStartActivity1.apk", "1.0.0", null,null);
	        }
	        catch (Exception ex)
	        {
	            System.err.println("Could not create : " + ex);
//	            ex.printStackTrace();
//	            int nPid = android.os.Process.myPid();
//				android.os.Process.killProcess(nPid);
	        }
		
	}
	public FrameworkInstance getFrame() {
		return frame;
	}

	private class NewMessageBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
		    // 注销广播
			abortBroadcast();

			// 消息id（每条消息都会生成唯一的一个id，目前是SDK生成）
			String msgId = intent.getStringExtra("msgid");
			//发送方
			String username = intent.getStringExtra("from");
			// 收到这个广播的时候，message已经在db和内存里了，可以通过id获取mesage对象
			EMMessage message = EMChatManager.getInstance().getMessage(msgId);
			EMConversation	conversation = EMChatManager.getInstance().getConversation(username);
			// 如果是群聊消息，获取到group id
			if (message.getChatType() == ChatType.GroupChat) {
				username = message.getTo();
			}
			if (!username.equals(username)) {
				// 消息不是发给当前会话，return
				return;
			}
		}
	}
	private BroadcastReceiver ackMessageReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			abortBroadcast();
			String msgid = intent.getStringExtra("msgid");
			String from = intent.getStringExtra("from");
			EMConversation conversation = EMChatManager.getInstance().getConversation(from);
			if (conversation != null) {
				// 把message设为已读
				EMMessage msg = conversation.getMessage(msgid);
				if (msg != null) {
					msg.isAcked = true;
				}
			}
					
		}
	};
	
}
