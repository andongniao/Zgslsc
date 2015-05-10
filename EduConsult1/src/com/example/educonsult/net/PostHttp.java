package com.example.educonsult.net;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.educonsult.beans.ShopBean;
import com.example.educonsult.beans.ShopItemBean;

public class PostHttp {
	private void postorder(String url,ArrayList<ShopBean> lsb){
		//		HttpPost httpPost = new HttpPost(url);
		//		ArrayList<NameValuePair>params = new ArrayList<NameValuePair>();
		//		params.add(base)
		/***************************************/
		DefaultHttpClient client = new DefaultHttpClient();   
		/**NameValuePair是传送给服务器的请求参数    param.get("name") **/  
		List<NameValuePair> list = new ArrayList<NameValuePair>();    
		for(int i=0;i<lsb.size();i++){
			ShopBean sb = lsb.get(i);
//			ShopItemBean s = listshop.get(i);
		}
		NameValuePair pair1 = new BasicNameValuePair("post["+50+"][num]", "name0001");    
		NameValuePair pair2 = new BasicNameValuePair("age", "age0001");    
		list.add(pair1);    
		list.add(pair2);    
//		NameValuePair post = new BasicNameValuePair("post", ); 
		UrlEncodedFormEntity entity=null;  
		try {  
			/**设置编码 **/  
			entity = new UrlEncodedFormEntity(list,"UTF-8");  
		} catch (UnsupportedEncodingException e) {  
			// TODO Auto-generated catch block  
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
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		} catch (IOException e) {  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		}    
		/**请求发送成功，并得到响应**/  
		if(response.getStatusLine().getStatusCode()==200){    
			try {  
				/**读取服务器返回过来的json字符串数据**/  
				strResult = EntityUtils.toString(response.getEntity());     
			} catch (IllegalStateException e) {  
				// TODO Auto-generated catch block  
				e.printStackTrace();  
			} catch (IOException e) {  
				// TODO Auto-generated catch block  
				e.printStackTrace();  
			}  


			JSONObject jsonObject = null;  
			try {  
				/**把json字符串转换成json对象**/  
				jsonObject = new JSONObject(strResult);  
			} catch (JSONException e1) {  
				// TODO Auto-generated catch block  
				e1.printStackTrace();  
			}  
			String names="";  

			try {  
				/** 
				 * jsonObject.getString("code") 取出code 
				 * 比如这里返回的json 字符串为 [code:0,msg:"ok",data:[list:{"name":1},{"name":2}]] 
				 * **/  

				/**得到data这个key**/  
				String data=jsonObject.getString("data");  
				/**把data下的数据转换成json对象**/  
				JSONObject jDat = new JSONObject(data);  
				/**判断data对象下的list是否存在**/  
				if(jDat.get("list")!=null){  
					/**把list转换成jsonArray对象**/  
					JSONArray jarr = jDat.getJSONArray("list");  
					/**循环list对象**/  
					for (int i = 0; i < jarr.length(); i++) {  

						/** **/  
						JSONObject jsono = (JSONObject) jarr.get(i);   

						/**取出list下的name的值 **/  
						names=names+jsono.getString("name");  

					}  
				} 
				
				
				//TODO
			} catch (JSONException e) {  
				// TODO Auto-generated catch block  
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
    

}
