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
		/**NameValuePair�Ǵ��͸����������������    param.get("name") **/  
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
			/**���ñ��� **/  
			entity = new UrlEncodedFormEntity(list,"UTF-8");  
		} catch (UnsupportedEncodingException e) {  
			// TODO Auto-generated catch block  
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
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		} catch (IOException e) {  
			// TODO Auto-generated catch block  
			e.printStackTrace();  
		}    
		/**�����ͳɹ������õ���Ӧ**/  
		if(response.getStatusLine().getStatusCode()==200){    
			try {  
				/**��ȡ���������ع�����json�ַ�������**/  
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
				/**��json�ַ���ת����json����**/  
				jsonObject = new JSONObject(strResult);  
			} catch (JSONException e1) {  
				// TODO Auto-generated catch block  
				e1.printStackTrace();  
			}  
			String names="";  

			try {  
				/** 
				 * jsonObject.getString("code") ȡ��code 
				 * �������ﷵ�ص�json �ַ���Ϊ [code:0,msg:"ok",data:[list:{"name":1},{"name":2}]] 
				 * **/  

				/**�õ�data���key**/  
				String data=jsonObject.getString("data");  
				/**��data�µ�����ת����json����**/  
				JSONObject jDat = new JSONObject(data);  
				/**�ж�data�����µ�list�Ƿ����**/  
				if(jDat.get("list")!=null){  
					/**��listת����jsonArray����**/  
					JSONArray jarr = jDat.getJSONArray("list");  
					/**ѭ��list����**/  
					for (int i = 0; i < jarr.length(); i++) {  

						/** **/  
						JSONObject jsono = (JSONObject) jarr.get(i);   

						/**ȡ��list�µ�name��ֵ **/  
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
    

}
