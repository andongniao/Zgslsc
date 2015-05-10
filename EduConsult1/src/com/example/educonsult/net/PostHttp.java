package com.example.educonsult.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.ListShopBean;
import com.example.educonsult.beans.ShopBean;
import com.example.educonsult.beans.ShopItemBean;
import com.example.educonsult.tools.Util;

public class PostHttp {
	private HttpParams httpParams;  
	private HttpClient httpClient;  
	private Context context;
	public PostHttp(Context context){
		this.context = context;
		getHttpClient();
	}
	public BaseBean addAddress() {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+ServiceUrl.add_address;
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","add");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("post[truename]","000");
		list.add(p1);
		NameValuePair p2 = new BasicNameValuePair("post[mobile]","1303030430");
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("post[postcode]","051530");
		list.add(p3);
		NameValuePair p4 = new BasicNameValuePair("post[areaid]","35");
		list.add(p4);
		NameValuePair p5 = new BasicNameValuePair("post[address]","ddddd");
		list.add(p5);
		NameValuePair p6 = new BasicNameValuePair("authstr","u0v3g87dqn34qvdj7nlosq8hk1");
		list.add(p6);

		/* ����HTTPPost���� */  
		HttpPost httpRequest = new HttpPost(url);  

		String strResult = "doPostError";  

		try {  
			/* ������������������� */  
			httpRequest.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));  
			/* �������󲢵ȴ���Ӧ */  
			HttpResponse httpResponse = httpClient.execute(httpRequest);  
			/* ��״̬��Ϊ200 ok */  
			JSONObject obj= null;
			if (httpResponse.getStatusLine().getStatusCode() == 200) {  
				/* ���������� */  
				strResult = EntityUtils.toString(httpResponse.getEntity());  
				obj = new JSONObject(strResult);
			} else {  
//				strResult = "Error Response: "  
//						+ httpResponse.getStatusLine().toString();  
			}  
			if(obj!=null){
				bean.setMsg(obj.getString("message"));
				bean.setCode(obj.getString("code"));
			}
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 
			strResult = e.getMessage().toString();  
			bean.setMsg("�������쳣�����Ժ�����...");
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  
			strResult = e.getMessage().toString();  
			e.printStackTrace();  
			System.out.println("IO�쳣");
			bean.setMsg("�������쳣�����Ժ�����...");
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  
			strResult = e.getMessage().toString();  
			e.printStackTrace();  
			bean.setMsg("�������쳣�����Ժ�����...");
			bean.setCode("500");
			return bean;
		}  
	}  




	public void AddAddress(){
		String url = "www.shop.com/app/address.php?action=add";
		//		HttpPost httpPost = new HttpPost(url);
		//		ArrayList<NameValuePair>params = new ArrayList<NameValuePair>();
		//		params.add(base)
		/***************************************/
		//		ArrayList<ShopBean>lb = lsb.getList();
		DefaultHttpClient client = new DefaultHttpClient();   
		/**NameValuePair�Ǵ��͸����������������    param.get("name") **/  
		//		for(int i=0;i<lb.size();i++){
		//			ShopBean sb = lb.get(i);
		//			for(int j = 0;j<sb.getMall().size();j++){
		//				ShopItemBean sib = sb.getMall().get(j);
		//				NameValuePair p1 = new BasicNameValuePair("post["+sib.getItemid()+"][num]",""+sib.getOunt());    
		//				list.add(p1);
		////				NameValuePair p1 = new BasicNameValuePair("post["+sib.getItemid()+"][coupon]","");
		//				if(Util.IsNull(sb.getNote())){
		//				NameValuePair p2 = new BasicNameValuePair("post["+sb.getCompanyid()+"][note]",""+sb.getNote());
		//				list.add(p2);
		//				}
		//				NameValuePair p3 = new BasicNameValuePair("post["+sb.getCompanyid()+"][express]",""+1);
		//				list.add(p3);
		//			}
		//		}
		List<NameValuePair> list = new ArrayList<NameValuePair>();    
		NameValuePair p1 = new BasicNameValuePair("post[truename]","000");
		list.add(p1);
		NameValuePair p2 = new BasicNameValuePair("post[mobile]","1303030430");
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("post[postcode]","051530");
		list.add(p3);
		NameValuePair p4 = new BasicNameValuePair("post[areaid]","35");
		list.add(p4);
		NameValuePair p5 = new BasicNameValuePair("post[address]","ddddd");
		list.add(p5);
		UrlEncodedFormEntity entity=null;  
		try {  
			/**���ñ��� **/  
			entity = new UrlEncodedFormEntity(list,"UTF-8");  
		} catch (UnsupportedEncodingException e) {  
			e.printStackTrace();  
		}    
		/**�½�һ��post����**/  
		HttpPost post = new HttpPost(url);    
		post.setEntity(entity);    
		HttpResponse response=null;  
		String strResult="";  
		try {  
			/**�ͷ������������������**/  
			response = client.execute(post);  
		} catch (ClientProtocolException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}    
		/**�����ͳɹ������õ���Ӧ**/  
		if(response.getStatusLine().getStatusCode()==200){    
			try {  
				/**��ȡ���������ع�����json�ַ�������**/  
				strResult = EntityUtils.toString(response.getEntity());     
			} catch (IllegalStateException e) {  
				e.printStackTrace();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  


			JSONObject jsonObject = null;  
			try {  
				/**��json�ַ���ת����json����**/  
				jsonObject = new JSONObject(strResult);  
			} catch (JSONException e1) {  
				e1.printStackTrace();  
			}  
			String names="";  

			try {  
				//TODO ����������Ϣ ����ta������̨

			} catch (Exception e) {  
				e.printStackTrace();  
			}    

		}    
	}











	//	public static String doHttpPost(String xmlInfo,String URL){         
	//        System.out.println("���������:"+xmlInfo);       
	//       byte[] xmlData = xmlInfo.getBytes();            
	//        InputStream instr  = null; 
	//        java.io.ByteArrayOutputStream out = null;              
	//         try{                          
	//                URL url = new URL(URL);                
	//                URLConnection urlCon = url.openConnection();               
	//                urlCon.setDoOutput(true);              
	//                urlCon.setDoInput(true);               
	//                urlCon.setUseCaches(false);                            
	//                urlCon.setRequestProperty("Content-Type", "text/xml");       
	//                urlCon.setRequestProperty("Content-length",String.valueOf(xmlData.length)); 
	//                System.out.println(String.valueOf(xmlData.length));
	//                DataOutputStream printout = new DataOutputStream(urlCon.getOutputStream());            
	//                printout.write(xmlData);               
	//                printout.flush();              
	//                printout.close();              
	//                instr = urlCon.getInputStream();   
	//                byte[] bis = IOUtils.toByteArray(instr);
	//                String ResponseString = new String(bis, "UTF-8");  
	//               if ((ResponseString == null) || ("".equals(ResponseString.trim()))) {
	//                    System.out.println("���ؿ�");
	//                   }
	//              System.out.println("��������Ϊ:" + ResponseString);
	//             return ResponseString;    
	//            
	//         }catch(Exception e){              
	//                e.printStackTrace();
	//               return "0";
	//         }             
	//         finally {             
	//                try {          
	//                       out.close();   
	//                       instr.close();  
	//                        
	//                }catch (Exception ex) {      
	//                       return "0";
	//                    }                  
	//                }                  
	//         }                     


	public HttpClient getHttpClient() {  

		// ���� HttpParams ���������� HTTP ��������һ���ֲ��Ǳ���ģ�  

		this.httpParams = new BasicHttpParams();  

		// �������ӳ�ʱ�� Socket ��ʱ���Լ� Socket �����С  

		HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);  

		HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);  

		HttpConnectionParams.setSocketBufferSize(httpParams, 8192);  

		// �����ض���ȱʡΪ true  

		HttpClientParams.setRedirecting(httpParams, true);  

		// ���� user agent  

		String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";  
		HttpProtocolParams.setUserAgent(httpParams, userAgent);  

		// ����һ�� HttpClient ʵ��  

		// ע�� HttpClient httpClient = new HttpClient(); ��Commons HttpClient  

		// �е��÷����� Android 1.5 ��������Ҫʹ�� Apache ��ȱʡʵ�� DefaultHttpClient  

		httpClient = new DefaultHttpClient(httpParams);  

		return httpClient;  
	}


	//		private void PostOrder(String url,ListShopBean lsb){
	//		//		HttpPost httpPost = new HttpPost(url);
	//		//		ArrayList<NameValuePair>params = new ArrayList<NameValuePair>();
	//		//		params.add(base)
	//		/***************************************/
	//		ArrayList<ShopBean>lb = lsb.getList();
	//		DefaultHttpClient client = new DefaultHttpClient();   
	//		/**NameValuePair�Ǵ��͸����������������    param.get("name") **/  
	//		List<NameValuePair> list = new ArrayList<NameValuePair>();    
	//		for(int i=0;i<lb.size();i++){
	//			ShopBean sb = lb.get(i);
	//			for(int j = 0;j<sb.getMall().size();j++){
	//				ShopItemBean sib = sb.getMall().get(j);
	//				NameValuePair p1 = new BasicNameValuePair("post["+sib.getItemid()+"][num]",""+sib.getOunt());    
	//				list.add(p1);
	////				NameValuePair p1 = new BasicNameValuePair("post["+sib.getItemid()+"][coupon]","");
	//				if(Util.IsNull(sb.getNote())){
	//				NameValuePair p2 = new BasicNameValuePair("post["+sb.getCompanyid()+"][note]",""+sb.getNote());
	//				list.add(p2);
	//				}
	//				NameValuePair p3 = new BasicNameValuePair("post["+sb.getCompanyid()+"][express]",""+1);
	//				list.add(p3);
	//			}
	//		}
	//		UrlEncodedFormEntity entity=null;  
	//		try {  
	//			/**���ñ��� **/  
	//			entity = new UrlEncodedFormEntity(list,"UTF-8");  
	//		} catch (UnsupportedEncodingException e) {  
	//			e.printStackTrace();  
	//		}    
	//		/**�½�һ��post����**/  
	//		HttpPost post = new HttpPost(url);    
	//		post.setEntity(entity);    
	//		HttpResponse response=null;  
	//		String strResult="";  
	//		try {  
	//			/**�ͷ������������������**/  
	//			response = client.execute(post);  
	//		} catch (ClientProtocolException e) {  
	//			e.printStackTrace();  
	//		} catch (IOException e) {  
	//			e.printStackTrace();  
	//		}    
	//		/**�����ͳɹ������õ���Ӧ**/  
	//		if(response.getStatusLine().getStatusCode()==200){    
	//			try {  
	//				/**��ȡ���������ع�����json�ַ�������**/  
	//				strResult = EntityUtils.toString(response.getEntity());     
	//			} catch (IllegalStateException e) {  
	//				e.printStackTrace();  
	//			} catch (IOException e) {  
	//				e.printStackTrace();  
	//			}  
	//
	//
	//			JSONObject jsonObject = null;  
	//			try {  
	//				/**��json�ַ���ת����json����**/  
	//				jsonObject = new JSONObject(strResult);  
	//			} catch (JSONException e1) {  
	//				e1.printStackTrace();  
	//			}  
	//			String names="";  
	//
	//			try {  
	//				//TODO ����������Ϣ ����ta������̨
	////				/** 
	////				 * jsonObject.getString("code") ȡ��code 
	////				 * �������ﷵ�ص�json �ַ���Ϊ [code:0,msg:"ok",data:[list:{"name":1},{"name":2}]] 
	////				 * **/  
	////
	////				/**�õ�data���key**/  
	////				String data=jsonObject.getString("data");  
	////				/**��data�µ�����ת����json����**/  
	////				JSONObject jDat = new JSONObject(data);  
	////				/**�ж�data�����µ�list�Ƿ����**/  
	////				if(jDat.get("list")!=null){  
	////					/**��listת����jsonArray����**/  
	////					JSONArray jarr = jDat.getJSONArray("list");  
	////					/**ѭ��list����**/  
	////					for (int i = 0; i < jarr.length(); i++) {  
	////
	////						/** **/  
	////						JSONObject jsono = (JSONObject) jarr.get(i);   
	////
	////						/**ȡ��list�µ�name��ֵ **/  
	////						names=names+jsono.getString("name");  
	////
	////					}  
	////				} 
	//				
	//			} catch (Exception e) {  
	//				e.printStackTrace();  
	//			}    
	//
	//		}    
	//	}


}
