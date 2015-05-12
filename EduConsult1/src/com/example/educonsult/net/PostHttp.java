package com.example.educonsult.net;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.example.educonsult.beans.AddressBean;
import com.example.educonsult.beans.BanksBean;
import com.example.educonsult.beans.BanksBranchBean;
import com.example.educonsult.beans.BanksCityBean;
import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.ExpressBean;
import com.example.educonsult.beans.ListBanksBean;
import com.example.educonsult.beans.ListBanksBranch;
import com.example.educonsult.beans.ListBanksCity;
import com.example.educonsult.beans.ListOrderCommit;
import com.example.educonsult.beans.ListOrderWuliu;
import com.example.educonsult.beans.ListProductBean;
import com.example.educonsult.beans.ListShopBean;
import com.example.educonsult.beans.OrderCommitBean;
import com.example.educonsult.beans.ProductBean;
import com.example.educonsult.beans.QuerenOrderBean;
import com.example.educonsult.beans.ShopBean;
import com.example.educonsult.beans.ShopItemBean;
import com.example.educonsult.tools.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PostHttp {
	private HttpParams httpParams;  
	private HttpClient httpClient;  
	private Context context;
	private Gson gson;
	public PostHttp(Context context){
		this.context = context;
		getHttpClient();
		gson = new Gson();
	}
	/**
	 * 添加一条地址
	 * @return
	 */
	public BaseBean addAddress() {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+ServiceUrl.address;
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
				if(obj!=null){
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				}else{
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}
			} else {  
				//				strResult = "Error Response: "  
				//						+ httpResponse.getStatusLine().toString();  
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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


	/**
	 * 添加一条地址
	 * @return
	 */
	public BaseBean addOneAddress(AddressBean ab,String authstr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+ServiceUrl.address;
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","add");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("areaid","35");
		list.add(p1);
		NameValuePair p2 = new BasicNameValuePair("address","s是是顺丰到付");
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("truename","王00");
		list.add(p3);
		NameValuePair p4 = new BasicNameValuePair("mobile","1303040480");
		list.add(p4);
		NameValuePair p5 = new BasicNameValuePair("postcode","051530");
		list.add(p5);
		NameValuePair p6 = new BasicNameValuePair("authstr",authstr);
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
				if(obj!=null){
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				}else{
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}
			} else {  
				//				strResult = "Error Response: "  
				//						+ httpResponse.getStatusLine().toString();  
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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




	/**
	 * 修改单条地址
	 * @return
	 */
	public BaseBean editOneAddress(AddressBean ab,String authstr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+ServiceUrl.address;
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","edit");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("areaid","35");
		list.add(p1);
		NameValuePair p2 = new BasicNameValuePair("address",ab.getAddress()+"000");
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("truename",ab.getTruename()+"000");
		list.add(p3);
		NameValuePair p4 = new BasicNameValuePair("mobile","1303030430");
		list.add(p4);
		NameValuePair p5 = new BasicNameValuePair("itemid",ab.getItemid());
		list.add(p5);
		NameValuePair p6 = new BasicNameValuePair("postcode","051530");
		list.add(p6);
		NameValuePair p7 = new BasicNameValuePair("isdefault","0");
		list.add(p7);
		NameValuePair p8 = new BasicNameValuePair("authstr",authstr);
		list.add(p8);

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
				if(obj!=null){
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				}else{
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}
			} else {  
				//				strResult = "Error Response: "  
				//						+ httpResponse.getStatusLine().toString();  
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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



	/**
	 * 设置默认地址
	 * @return
	 */
	public BaseBean SetDetaultAddress(AddressBean ab,String authstr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+ServiceUrl.address;
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","default");
		list.add(p);
		NameValuePair p5 = new BasicNameValuePair("itemid",ab.getItemid());
		list.add(p5);
		NameValuePair p8 = new BasicNameValuePair("authstr",authstr);
		list.add(p8);

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
				if(obj!=null){
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				}else{
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}
			} else {  
				//				strResult = "Error Response: "  
				//						+ httpResponse.getStatusLine().toString();  
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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

	/**
	 * 删除单条地址
	 * @return
	 */
	public BaseBean DelAddress(AddressBean ab,String authstr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+ServiceUrl.address;
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","del");
		list.add(p);
		NameValuePair p5 = new BasicNameValuePair("itemid",ab.getItemid());
		list.add(p5);
		NameValuePair p8 = new BasicNameValuePair("authstr",authstr);
		list.add(p8);

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
				if(obj!=null){
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				}else{
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}
			} else {  
				//				strResult = "Error Response: "  
				//						+ httpResponse.getStatusLine().toString();  
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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


	/**
	 * 搜索
	 * @param type 	产品1、地区2、分类3
	 * @param order 排序 0综合 1销量降序 2销量升序 3、4价格 5、6人气
	 * @param page 	页数
	 * @param text	关键字
	 * @return
	 */
	public ListProductBean SeanchText(int type,int order,int page,String text) {  
		ListProductBean bean = new ListProductBean();
		ArrayList<ProductBean> lp = new ArrayList<ProductBean>();
		String key = "";
		switch (type) {
		case 1:
			key = "product";
			break;
		case 2:
			key = "area";
			break;
		case 3:
			key = "category";
			break;
		}
		String url = ServiceUrl.Base+"fields.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action",key);
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("order",""+order);
		list.add(p1);
		NameValuePair p2 = new BasicNameValuePair("page",""+page);
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("key",text);
		list.add(p3);

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
				if(Util.IsNull(strResult)){
					if(!"[]".equals(strResult)){
						obj = new JSONObject(strResult);
						if(obj!=null){
							String code = obj.getString("code");
							String msg = obj.getString("message");
							if("200".equals(code)){
								JSONArray data = obj.getJSONArray("data");
								Type type_re = new TypeToken<ArrayList<ProductBean>>() {
								}.getType();
								lp = gson.fromJson(data.toString(), type_re);
							}
							bean.setList(lp);
							bean.setMsg(msg);
							bean.setCode(code);
						}
					}else{
						bean.setMsg("暂无搜索结果");
						bean.setCode("400");	
					}
				}
			} else {  
				//				strResult = "Error Response: "  
				//						+ httpResponse.getStatusLine().toString();  
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");	
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




	/**
	 * 绑定银行卡界面
	 */
	public ListBanksBean getBanksList(String authstr) {  
		ListBanksBean bean = new ListBanksBean();
		ArrayList<BanksBean> listbanks = new ArrayList<BanksBean>();
		ArrayList<String> listprovince = new ArrayList<String>();
		String url = ServiceUrl.Base+"bankcard.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","bank");
		list.add(p);
		NameValuePair pl = new BasicNameValuePair("authstr",authstr);
		list.add(pl);

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
				if(obj!=null){
					JSONObject data = obj.getJSONObject("data");
					JSONArray banks = data.getJSONArray("banks");
					Type t = new TypeToken<ArrayList<BanksBean>>() {
					}.getType();
					listbanks = gson.fromJson(banks.toString(), t);
					JSONArray province = data.getJSONArray("province");
					Type tp = new TypeToken<ArrayList<String>>() {
					}.getType();
					listprovince = gson.fromJson(province.toString(), tp);
					bean.setBanks(listbanks);
					bean.setProvince(listprovince);
				}
				bean.setMsg(obj.getString("message"));
				bean.setCode(obj.getString("code"));
			} else {  
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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









	/**
	 * 获取开户行城市/县
	 * @param type 		1城市2县
	 * @param province 	城市名称
	 * @param authstr	唯一标示
	 * @return
	 */
	public ListBanksCity getBanksCityList(int type,String province,String authstr) {  
		ListBanksCity bean = new ListBanksCity();
		ArrayList<BanksCityBean> listcity = new ArrayList<BanksCityBean>();
		String url = ServiceUrl.Base+"bankcard.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = null;
		if(type==1){
			p = new BasicNameValuePair("action","city");
		}else{
			p = new BasicNameValuePair("action","county");	
		}
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("province",province);
		list.add(p1);
		NameValuePair pl = new BasicNameValuePair("authstr",authstr);
		list.add(pl);

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
				if(obj!=null){
					JSONArray data = obj.getJSONArray("data");
					Type t = new TypeToken<ArrayList<BanksCityBean>>() {
					}.getType();
					listcity = gson.fromJson(data.toString(), t);
					bean.setList(listcity);
				}
				bean.setMsg(obj.getString("message"));
				bean.setCode(obj.getString("code"));
			} else {  
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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



	/**
	 * 获取开户网点
	 * @param id		开户行ID
	 * @param code		城市ID或者区县ID（可选）
	 * @param key		搜索关键字（可选）
	 * @param authstr	唯一标示
	 * @return
	 */
	public ListBanksBranch getBanksBeanchList(int id,String code,String key,String authstr) {  
		ListBanksBranch bean = new ListBanksBranch();
		ArrayList<BanksBranchBean> listbranch = new ArrayList<BanksBranchBean>();
		String url = ServiceUrl.Base+"bankcard.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","branch");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("type",""+id);
		list.add(p1);
		NameValuePair p2 = new BasicNameValuePair("code",""+code);
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("key",""+key);
		list.add(p3);
		NameValuePair pl = new BasicNameValuePair("authstr",authstr);
		list.add(pl);

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
				if(obj!=null){
					JSONArray data = obj.getJSONArray("data");
					Type t = new TypeToken<ArrayList<BanksBranchBean>>() {
					}.getType();
					listbranch = gson.fromJson(data.toString(), t);
					bean.setList(listbranch);
				}
				bean.setMsg(obj.getString("message"));
				bean.setCode(obj.getString("code"));
			} else {  
				//				strResult = "Error Response: "  
				//						+ httpResponse.getStatusLine().toString();  
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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



	/**
	 * 绑定银行卡
	 * @param branch		银行联行行号 格式为"001121013007|中国人民银行石家庄中心支行营业部"
	 * @param account		收款账号 		开户卡号
	 * @param accountname	收款账号户名		开户名
	 * @param authstr		唯一标示
	 * @param bank			开户行ID
	 * @return
	 */
	public BaseBean bindBankcart(String branch,String account,String accountname,String authstr
			,int bank) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+"bankcard.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","bind");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("bank",""+bank);
		list.add(p1);
		NameValuePair p2 = new BasicNameValuePair("branch",""+branch);
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("account",""+account);
		list.add(p3);
		NameValuePair p4 = new BasicNameValuePair("accountname",""+accountname);
		list.add(p4);
		NameValuePair pl = new BasicNameValuePair("authstr",authstr);
		list.add(pl);

		/* 建立HTTPPost对象 */  
		HttpPost httpRequest = new HttpPost(url);  

		String strResult = "";  

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
				if(Util.IsNull(strResult)){
					obj = new JSONObject(strResult);
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				}else{
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}
			} else {  
				//				strResult = "Error Response: "  
				//						+ httpResponse.getStatusLine().toString();  
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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









	/**
	 * 结算获取运费模板
	 */
	public QuerenOrderBean Jiesuan(ListShopBean lsb,String authstr) {  
		QuerenOrderBean bean = new QuerenOrderBean();
		ArrayList<ShopBean> listshop = lsb.getList();
		ArrayList<ShopBean>lsp = new ArrayList<ShopBean>();
		String url = ServiceUrl.Base+"trade.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","list");
		list.add(p);
		for(int i=0;i<listshop.size();i++){
			ShopBean sb = listshop.get(i);
			for(int j=0;j<sb.getMall().size();j++){
				ShopItemBean sib = sb.getMall().get(j);
				NameValuePair p1 = new BasicNameValuePair("item["+sib.getItemid()+"]","1");
				list.add(p1);
			}
		}
		NameValuePair pl = new BasicNameValuePair("authstr",authstr);
		list.add(pl);

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
				if(obj!=null){
					JSONObject data = obj.getJSONObject("data");
					@SuppressWarnings("unchecked")
					JSONObject li = data.getJSONObject("list");
					Iterator<String> keys=li.keys();
					while (keys.hasNext ())
					{
						ShopBean sb =new ShopBean();
						String k = (String) keys.next() + "";
						JSONObject js = (JSONObject) li.get(k);
						JSONArray m = js.getJSONArray("mall");
						ArrayList<ShopItemBean>  mall = new ArrayList<ShopItemBean>();
						Type t = new TypeToken<ArrayList<ShopItemBean>>() {
						}.getType();
						mall = gson.fromJson(m.toString(), t);
						
						ArrayList<ExpressBean>  expre = new ArrayList<ExpressBean>();
						if(Util.IsNull(js.get("express").toString())){
						JSONArray express = js.getJSONArray("express");
						Type tp = new TypeToken<ArrayList<ExpressBean>>() {
						}.getType();
						expre = gson.fromJson(express.toString(), tp);
						}
						sb.setCompany(js.getString("company"));
						sb.setExpress(expre);
						sb.setMall(mall);
						sb.setCompanyid(k);
						lsp.add(sb);
					}
					ListShopBean ll = new ListShopBean();
					ll.setList(lsp);
					bean.setList(ll);
					AddressBean ab = new AddressBean();
					if(Util.IsNull(data.getString("address"))){
					JSONObject address = data.getJSONObject("address");
					Type t = new TypeToken<AddressBean>() {
					}.getType();
					ab = gson.fromJson(address.toString(), t);
					}
					bean.setAddress(ab);
					bean.setTotal_amount(data.getInt("total_amount"));
					bean.setTotal(data.getInt("total"));
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				}else{
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}
			} else {  
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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


	/**
	 *  订单付款
	 * @param itemid		订单ID
	 * @param password		交易密码
	 * @param couponid		优惠券ID
	 * @param authstr		唯一标示
	 * @return
	 */
	public BaseBean Order_pay(String itemid,String password,String couponid,String authstr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+"order.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","prepay");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("itemid",itemid);
		list.add(p1);
		NameValuePair p2 = new BasicNameValuePair("password",password);
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("couponid",couponid);
		list.add(p3);
		NameValuePair pl = new BasicNameValuePair("authstr",authstr);
		list.add(pl);

		/* 建立HTTPPost对象 */  
		HttpPost httpRequest = new HttpPost(url);  

		String strResult = "";  

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
				if(Util.IsNull(strResult)){
					obj = new JSONObject(strResult);
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				} else {  
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}  
			}else{
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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

	
	/**
	 *  关闭订单
	 * @param itemid		订单ID
	 * @param authstr		唯一标示
	 * @return
	 */
	public BaseBean Order_close(String itemid,String authstr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+"order.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","close");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("itemid",itemid);
		list.add(p1);
		NameValuePair pl = new BasicNameValuePair("authstr",authstr);
		list.add(pl);

		/* 建立HTTPPost对象 */  
		HttpPost httpRequest = new HttpPost(url);  

		String strResult = "";  

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
				if(Util.IsNull(strResult)){
					obj = new JSONObject(strResult);
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				} else {  
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}  
				
			}else{
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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
	
	/**
	 *  确认付款（买家操作）
	 * @param itemid		订单ID
	 * @param authstr		唯一标示
	 * @return
	 */
	public BaseBean Order_confirmpay(String itemid,String authstr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+"order.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","confirmpay");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("itemid",itemid);
		list.add(p1);
		NameValuePair pl = new BasicNameValuePair("authstr",authstr);
		list.add(pl);

		/* 建立HTTPPost对象 */  
		HttpPost httpRequest = new HttpPost(url);  

		String strResult = "";  

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
				if(Util.IsNull(strResult)){
					obj = new JSONObject(strResult);
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				} else {  
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}  
			}else{
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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
	
	/**
	 *  评价订单
	 * @param itemid		订单ID
	 * @param star			评价星级1差评，2中评，3好评（默认值）
	 * @param content		评价内容
	 * @param authstr		唯一标示
	 * @return
	 */
	public BaseBean Order_comment(String itemid,int star,String content,String authstr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+"order.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","confirmpay");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("itemid",itemid);
		list.add(p1);
		NameValuePair p2 = new BasicNameValuePair("star",""+star);
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("content",content);
		list.add(p3);
		NameValuePair pl = new BasicNameValuePair("authstr",authstr);
		list.add(pl);

		/* 建立HTTPPost对象 */  
		HttpPost httpRequest = new HttpPost(url);  

		String strResult = "";  

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
				if(Util.IsNull(strResult)){
					obj = new JSONObject(strResult);
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				} else {  
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}  
			}else{
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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
	


	/**
	 *  卖家发货处理(确认发货)
	 * @param itemid			订单ID
	 * @param submit			true
	 * @param send_type			物流类型（可选）
	 * @param send_no			物流号码（可选）
	 * @param send_time			发货时间（可选），格式如：2015-05-11
	 * @param authstr			唯一标示
	 * @return
	 */
	public BaseBean Order_sendgoods(String itemid,int submit,String send_type
			,String send_no,String send_time,String authstr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+"order.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","sendgoods");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("itemid",itemid);
		list.add(p1);
		NameValuePair p2 = new BasicNameValuePair("submit","true");
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("send_type",send_type);
		list.add(p3);
		NameValuePair p4 = new BasicNameValuePair("send_no",send_no);
		list.add(p4);
		NameValuePair p5 = new BasicNameValuePair("send_time",send_time);
		list.add(p5);
		NameValuePair pl = new BasicNameValuePair("authstr",authstr);
		list.add(pl);

		/* 建立HTTPPost对象 */  
		HttpPost httpRequest = new HttpPost(url);  

		String strResult = "";  

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
				if(Util.IsNull(strResult)){
					obj = new JSONObject(strResult);
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				} else {  
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}  
				
			}else{
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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
	
	
	/**
	 *  确认发货 获取物流列表&当前后台时间
	 * @param itemid			订单ID
	 * @param authstr			唯一标示
	 * @return
	 */
	public ListOrderWuliu Order_getwuliulist(String itemid,String authstr) {  
		ListOrderWuliu bean = new ListOrderWuliu();
		ArrayList<String> listtype = new ArrayList<String>();
		String url = ServiceUrl.Base+"order.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","sendgoods");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("itemid",itemid);
		list.add(p1);
		NameValuePair pl = new BasicNameValuePair("authstr",authstr);
		list.add(pl);

		/* 建立HTTPPost对象 */  
		HttpPost httpRequest = new HttpPost(url);  

		String strResult = "";  

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
				if(Util.IsNull(strResult)){
					obj = new JSONObject(strResult);
					JSONObject data = obj.getJSONObject("data");
					JSONArray send_types = data.getJSONArray("send_types");
					Type t = new TypeToken<ArrayList<String>>() {
					}.getType();
					listtype  = gson.fromJson(send_types.toString(), t);
					bean.setSend_time(data.getString("send_time"));
					bean.setSend_types(listtype);
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				} else {  
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}  
				
			}else{
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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
	
	
	/**
	 *  申请退货
	 * @param itemid			订单ID
	 * @param submit			true
	 * @param content			发货时间（可选），格式如：2015-05-11
	 * @param authstr			唯一标示
	 * @return
	 */
	public BaseBean Order_refund(String itemid,int submit,String content,String authstr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+"order.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","refund");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("itemid",itemid);
		list.add(p1);
		String t = "";
		if(submit==1){
			t = "true";
		}else{
			t = "false";
		}
		NameValuePair p2 = new BasicNameValuePair("submit",t);
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("content",content);
		list.add(p3);
		NameValuePair pl = new BasicNameValuePair("authstr",authstr);
		list.add(pl);

		/* 建立HTTPPost对象 */  
		HttpPost httpRequest = new HttpPost(url);  

		String strResult = "";  

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
				if(Util.IsNull(strResult)){
					obj = new JSONObject(strResult);
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				} else {  
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}  
				
			}else{
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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
	
	
	/**
	 *  获取申请退货订单的订单数据
	 * @param itemid			订单ID
	 * @param authstr			唯一标示
	 * @return
	 */
	public BaseBean Order_refunddata(String itemid,String authstr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+"order.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","refund");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("itemid",itemid);
		list.add(p1);
		NameValuePair pl = new BasicNameValuePair("authstr",authstr);
		list.add(pl);

		/* 建立HTTPPost对象 */  
		HttpPost httpRequest = new HttpPost(url);  

		String strResult = "";  

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
				if(Util.IsNull(strResult)){
					obj = new JSONObject(strResult);
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				} else {  
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}  
				
			}else{
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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
	
	
	

	/**
	 * 提交（生成）订单
	 * @param lsb		购物车传过来的实体
	 * @param authstr	唯一标示
	 * @return
	 */
	public ListOrderCommit CommitOrder(ListShopBean lsb,String authstr) {  
		ListOrderCommit bean = new ListOrderCommit();
		ArrayList<OrderCommitBean> listorder = new ArrayList<OrderCommitBean>();
		ArrayList<ShopBean> listshop = lsb.getList();
		ArrayList<ShopBean>lsp = new ArrayList<ShopBean>();
		String url = ServiceUrl.Base+"trade.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","buy");
		list.add(p);
		for(int i=0;i<listshop.size();i++){
			ShopBean sb = listshop.get(i);
			for(int j=0;j<sb.getMall().size();j++){
				ShopItemBean sib = sb.getMall().get(j);
				NameValuePair p1 = new BasicNameValuePair("post["+sib.getItemid()+"][num]","1");
				list.add(p1);
				NameValuePair p2 = new BasicNameValuePair("post["+sib.getItemid()+"][coupon]","");
				list.add(p2);
				NameValuePair p3 = new BasicNameValuePair("post["+sb.getCompanyid()+"][note]","");
				list.add(p3);
				NameValuePair p4 = new BasicNameValuePair("post["+sb.getCompanyid()+"][express]","39");
				list.add(p4);
			}
		}
		NameValuePair pl = new BasicNameValuePair("authstr",authstr);
		list.add(pl);

		/* 建立HTTPPost对象 */  
		HttpPost httpRequest = new HttpPost(url);  

		String strResult = "";  

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
				if(Util.IsNull(strResult)){
					obj = new JSONObject(strResult);
					if(obj!=null){
						if("200".equals(obj.getString("code"))){
						JSONObject data = obj.getJSONObject("data");
						OrderCommitBean a = new OrderCommitBean();
						JSONArray trade = data.getJSONArray("trade");
						Type t = new TypeToken<ArrayList<Integer>>() {
						}.getType();
						ArrayList<Integer>	lin  = gson.fromJson(trade.toString(), t);
						System.out.println("提交订单后得到的list len="+lin.size());
						for(int i=0;i<lin.size();i++){
							String key = ""+lin.get(i);
							a.setTrade(lin.get(i));
						if(Util.IsNull(data.getString("coupon"))){
							JSONObject coupon = data.getJSONObject("coupon");
							a.setCoupon(coupon.getString(key));
						}else{
							a.setCoupon("");
						}
						listorder.add(a);
						}
						}
						bean.setList(listorder);
						bean.setMsg(obj.getString("message"));
						bean.setCode(obj.getString("code"));
					}
				}else{
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}
			} else {  
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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

	/**
	 * 结算订单，支付
	 * @param lsb		订单实体
	 * @param authstr	唯一标示
	 * @param password	password
	 * @return
	 */
	public BaseBean PayOrder(ListOrderCommit lsb,String authstr,String password) {  
		BaseBean bean = new BaseBean();
		ArrayList<OrderCommitBean> listshop = lsb.getList();
		String url = ServiceUrl.Base+"trade.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","confirm");
		list.add(p);
		System.out.println("支付时list len="+listshop.size());
		for(int i=0;i<listshop.size();i++){
			OrderCommitBean sb = listshop.get(i);
				NameValuePair p1 = new BasicNameValuePair("coupon["+sb.getTrade()+"]",sb.getCoupon());
				list.add(p1);
				NameValuePair pi = new BasicNameValuePair("item[]",""+sb.getTrade());
				list.add(pi);
		}
		NameValuePair po = new BasicNameValuePair("password",password);
		list.add(po);
		NameValuePair pl = new BasicNameValuePair("authstr",authstr);
		list.add(pl);
		System.out.println("支付时上传的数据" +
				""+list.toString());
		/* 建立HTTPPost对象 */  
		HttpPost httpRequest = new HttpPost(url);  

		String strResult = "";  

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
				if(Util.IsNull(strResult)){
					obj = new JSONObject(strResult);
					if(obj!=null){
//						if("200".equals(obj.getString("code"))){
//						}
						bean.setMsg(obj.getString("message"));
						bean.setCode(obj.getString("code"));
					}
				}else{
					bean.setMsg("服务器异常，请稍后再试...");
					bean.setCode("500");
				}
			} else {  
				bean.setMsg("服务器异常，请稍后再试...");
				bean.setCode("500");
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

}
