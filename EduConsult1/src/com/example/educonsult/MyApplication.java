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
	

		 try
	        {
	        	//�������
				//�ĵ��� http://www.apkplug.com/javadoc/Maindoc1.4.6/
				//org.apkplug.app 
			 	//     �ӿ� FrameworkInstance
			 	Log.e("start", "fsfsdafasfsa");
//				frame=FrameworkFactory.getInstance().start(list, context);
				frame=FrameworkFactory.getInstance().start(null,this);
//				System.out.println("ProxyApplication1");
//				BundleContext context =frame.getSystemBundleContext();
//				// InstallBundler ��2.7.0�汾���������е�
//				InstallBundler ib=new InstallBundler(context);
//				ib.installForAssets("chatdemo-ui.apk", "1.0.0", null,
//						new installCallback(){
//						@Override
//						public void callback(int arg0, Bundle arg1) {
//							if(arg0==installCallback.stutas5||arg0==installCallback.stutas7){
//								Log.d("",String.format("�����װ %s �� %d",arg1.getName(),arg0));
//								return;
//							}
//							else{
//								Log.d("","�����װʧ�� ��%d"+arg0);
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
		    // ע���㲥
			abortBroadcast();

			// ��Ϣid��ÿ����Ϣ��������Ψһ��һ��id��Ŀǰ��SDK���ɣ�
			String msgId = intent.getStringExtra("msgid");
			//���ͷ�
			String username = intent.getStringExtra("from");
			// �յ�����㲥��ʱ��message�Ѿ���db���ڴ����ˣ�����ͨ��id��ȡmesage����
			EMMessage message = EMChatManager.getInstance().getMessage(msgId);
			EMConversation	conversation = EMChatManager.getInstance().getConversation(username);
			// �����Ⱥ����Ϣ����ȡ��group id
			if (message.getChatType() == ChatType.GroupChat) {
				username = message.getTo();
			}
			if (!username.equals(username)) {
				// ��Ϣ���Ƿ�����ǰ�Ự��return
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
				// ��message��Ϊ�Ѷ�
				EMMessage msg = conversation.getMessage(msgid);
				if (msg != null) {
					msg.isAcked = true;
				}
			}
					
		}
	};
	
}
