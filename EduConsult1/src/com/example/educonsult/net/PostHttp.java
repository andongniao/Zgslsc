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

		/* 建立HTTPPost对象 */  
		HttpPost httpRequest = new HttpPost(url);  

		String strResult = "doPostError";  

		try {  
			/* 添加请求参数到请求对象 */  
			httpRequest.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));  
			/* 发送请求并等待响应 */  
			HttpResponse httpResponse = httpClient.execute(httpRequest);  
			/* 若状态码为200 ok */  
			JSONObject obj= null;
			if (httpResponse.getStatusLine().getStatusCode() == 200) {  
				/* 读返回数据 */  
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
			bean.setMsg("服务器异常，请稍后再试...");
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  
			strResult = e.getMessage().toString();  
			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg("服务器异常，请稍后再试...");
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  
			strResult = e.getMessage().toString();  
			e.printStackTrace();  
			bean.setMsg("服务器异常，请稍后再试...");
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
		/**NameValuePair是传送给服务器的请求参数    param.get("name") **/  
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
			/**设置编码 **/  
			entity = new UrlEncodedFormEntity(list,"UTF-8");  
		} catch (UnsupportedEncodingException e) {  
			e.printStackTrace();  
		}    
		/**新建一个post请求**/  
		HttpPost post = new HttpPost(url);    
		post.setEntity(entity);    
		HttpResponse response=null;  
		String strResult="";  
		try {  
			/**客服端向服务器发送请求**/  
			response = client.execute(post);  
		} catch (ClientProtocolException e) {  
			e.printStackTrace();  
		} catch (IOException e) {  
			e.printStackTrace();  
		}    
		/**请求发送成功，并得到响应**/  
		if(response.getStatusLine().getStatusCode()==200){    
			try {  
				/**读取服务器返回过来的json字符串数据**/  
				strResult = EntityUtils.toString(response.getEntity());     
			} catch (IllegalStateException e) {  
				e.printStackTrace();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  


			JSONObject jsonObject = null;  
			try {  
				/**把json字符串转换成json对象**/  
				jsonObject = new JSONObject(strResult);  
			} catch (JSONException e1) {  
				e1.printStackTrace();  
			}  
			String names="";  

			try {  
				//TODO 解析返回信息 并将ta传给后台

			} catch (Exception e) {  
				e.printStackTrace();  
			}    

		}    
	}











	//	public static String doHttpPost(String xmlInfo,String URL){         
	//        System.out.println("发起的数据:"+xmlInfo);       
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
	//                    System.out.println("返回空");
	//                   }
	//              System.out.println("返回数据为:" + ResponseString);
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

		// 创建 HttpParams 以用来设置 HTTP 参数（这一部分不是必需的）  

		this.httpParams = new BasicHttpParams();  

		// 设置连接超时和 Socket 超时，以及 Socket 缓存大小  

		HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);  

		HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);  

		HttpConnectionParams.setSocketBufferSize(httpParams, 8192);  

		// 设置重定向，缺省为 true  

		HttpClientParams.setRedirecting(httpParams, true);  

		// 设置 user agent  

		String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";  
		HttpProtocolParams.setUserAgent(httpParams, userAgent);  

		// 创建一个 HttpClient 实例  

		// 注意 HttpClient httpClient = new HttpClient(); 是Commons HttpClient  

		// 中的用法，在 Android 1.5 中我们需要使用 Apache 的缺省实现 DefaultHttpClient  

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
	//		/**NameValuePair是传送给服务器的请求参数    param.get("name") **/  
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
	//			/**设置编码 **/  
	//			entity = new UrlEncodedFormEntity(list,"UTF-8");  
	//		} catch (UnsupportedEncodingException e) {  
	//			e.printStackTrace();  
	//		}    
	//		/**新建一个post请求**/  
	//		HttpPost post = new HttpPost(url);    
	//		post.setEntity(entity);    
	//		HttpResponse response=null;  
	//		String strResult="";  
	//		try {  
	//			/**客服端向服务器发送请求**/  
	//			response = client.execute(post);  
	//		} catch (ClientProtocolException e) {  
	//			e.printStackTrace();  
	//		} catch (IOException e) {  
	//			e.printStackTrace();  
	//		}    
	//		/**请求发送成功，并得到响应**/  
	//		if(response.getStatusLine().getStatusCode()==200){    
	//			try {  
	//				/**读取服务器返回过来的json字符串数据**/  
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
	//				/**把json字符串转换成json对象**/  
	//				jsonObject = new JSONObject(strResult);  
	//			} catch (JSONException e1) {  
	//				e1.printStackTrace();  
	//			}  
	//			String names="";  
	//
	//			try {  
	//				//TODO 解析返回信息 并将ta传给后台
	////				/** 
	////				 * jsonObject.getString("code") 取出code 
	////				 * 比如这里返回的json 字符串为 [code:0,msg:"ok",data:[list:{"name":1},{"name":2}]] 
	////				 * **/  
	////
	////				/**得到data这个key**/  
	////				String data=jsonObject.getString("data");  
	////				/**把data下的数据转换成json对象**/  
	////				JSONObject jDat = new JSONObject(data);  
	////				/**判断data对象下的list是否存在**/  
	////				if(jDat.get("list")!=null){  
	////					/**把list转换成jsonArray对象**/  
	////					JSONArray jarr = jDat.getJSONArray("list");  
	////					/**循环list对象**/  
	////					for (int i = 0; i < jarr.length(); i++) {  
	////
	////						/** **/  
	////						JSONObject jsono = (JSONObject) jarr.get(i);   
	////
	////						/**取出list下的name的值 **/  
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
