package com.xunbo.store.net;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xunbo.store.beans.AddressBean;
import com.xunbo.store.beans.BanksBean;
import com.xunbo.store.beans.BanksBranchBean;
import com.xunbo.store.beans.BanksCityBean;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.beans.CenterCountBean;
import com.xunbo.store.beans.CenterShopBean;
import com.xunbo.store.beans.ChargeBankBean;
import com.xunbo.store.beans.CouponBean;
import com.xunbo.store.beans.ExpressBean;
import com.xunbo.store.beans.ListBanksBean;
import com.xunbo.store.beans.ListBanksBranch;
import com.xunbo.store.beans.ListBanksCity;
import com.xunbo.store.beans.ListCenterShopBean;
import com.xunbo.store.beans.ListChargeBankBean;
import com.xunbo.store.beans.ListOrderCommit;
import com.xunbo.store.beans.ListOrderWuliu;
import com.xunbo.store.beans.ListProductBean;
import com.xunbo.store.beans.ListSCProductBean;
import com.xunbo.store.beans.ListShopBean;
import com.xunbo.store.beans.ListShopHomeBean;
import com.xunbo.store.beans.ListStoreCatBean;
import com.xunbo.store.beans.OrderCommitBean;
import com.xunbo.store.beans.OrderDetaileBean;
import com.xunbo.store.beans.PayBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.beans.QuerenOrderBean;
import com.xunbo.store.beans.RefundInfoBean;
import com.xunbo.store.beans.RefundInfoDetaileBean;
import com.xunbo.store.beans.SCProductBean;
import com.xunbo.store.beans.ShopBean;
import com.xunbo.store.beans.ShopInfoBean;
import com.xunbo.store.beans.ShopItemBean;
import com.xunbo.store.beans.StorecatBean;
import com.xunbo.store.beans.TnResultBean;
import com.xunbo.store.tools.Util;

public class PostHttp {
	private HttpParams httpParams;  
	private HttpClient httpClient;  
	@SuppressWarnings("unused")
	private Context context;
	private Gson gson;
	private static String error="服务器异常，请稍候再试...";
	public PostHttp(Context context){
		this.context = context;
		getHttpClient();
		gson = new Gson();
	}  

	/**
	 * 添加一条地址
	 * @return
	 */
	@SuppressWarnings("unused")
	public BaseBean addOneAddress(AddressBean ab,String authstr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+ServiceUrl.address;
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","add");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("areaid",ab.getAreaid());
		list.add(p1);
		NameValuePair p2 = new BasicNameValuePair("address",ab.getAddress());
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("truename",ab.getTruename());
		list.add(p3);
		NameValuePair p4 = new BasicNameValuePair("mobile",ab.getMobile());
		list.add(p4);
		NameValuePair p5 = new BasicNameValuePair("postcode",ab.getPostcode());
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
					bean.setMsg(error);
					bean.setCode("500");
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");
			}  

			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	}  




	/**
	 * 修改单条地址
	 * @return
	 */
	@SuppressWarnings("unused")
	public BaseBean editOneAddress(AddressBean ab,String authstr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+ServiceUrl.address;
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","edit");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("areaid",ab.getAreaid());
		list.add(p1);
		NameValuePair p2 = new BasicNameValuePair("address",ab.getAddress());
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("truename",ab.getTruename());
		list.add(p3);
		NameValuePair p4 = new BasicNameValuePair("mobile",ab.getMobile());
		list.add(p4);
		NameValuePair p5 = new BasicNameValuePair("itemid",ab.getItemid());
		list.add(p5);
		NameValuePair p6 = new BasicNameValuePair("postcode",ab.getPostcode());
		list.add(p6);
		NameValuePair p7 = new BasicNameValuePair("isdefault",ab.getIsdefault());
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
					bean.setMsg(error);
					bean.setCode("500");
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");
			}  

			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	}  



	/**
	 * 设置默认地址
	 * @return
	 */
	@SuppressWarnings("unused")
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
					bean.setMsg(error);
					bean.setCode("500");
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");
			}  

			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	}  

	/**
	 * 删除单条地址
	 * @return
	 */
	@SuppressWarnings("unused")
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
					bean.setMsg(error);
					bean.setCode("500");
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");
			}  

			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
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
		String url = ServiceUrl.Base+"search.php";
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
						bean.setList(lp);
						bean.setMsg("暂无搜索结果");
						bean.setCode("200");	
					}
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");	
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	}  




	/**
	 * 绑定银行卡界面（获取银行信息）
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
				bean.setMsg(error);
				bean.setCode("500");
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
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
			list.add(p);
			NameValuePair p1 = new BasicNameValuePair("province",province);
			list.add(p1);
		}else{
			p = new BasicNameValuePair("action","county");	
			list.add(p);
			NameValuePair p1 = new BasicNameValuePair("city",province);
			list.add(p1);
		}
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
					JSONArray data = obj.getJSONArray("data");
					Type t = new TypeToken<ArrayList<BanksCityBean>>() {
					}.getType();
					listcity = gson.fromJson(data.toString(), t);
					bean.setList(listcity);
				}
				bean.setMsg(obj.getString("message"));
				bean.setCode(obj.getString("code"));
			} else {  
				bean.setMsg(error);
				bean.setCode("500");
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  
			//			 
			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
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
				bean.setMsg(error);
				bean.setCode("500");
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
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
					bean.setMsg(error);
					bean.setCode("500");
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	} 









	/**
	 * 结算获取运费模板
	 */
	@SuppressWarnings("unused")
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
					JSONObject li = data.getJSONObject("list");
					@SuppressWarnings("unchecked")
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
						int len = mall.size();
						for(int i=0;i<len;i++){
							ArrayList<CouponBean> l = new ArrayList<CouponBean>();
							JSONObject ja = (JSONObject) m.get(i);
							if(Util.IsNull(ja.getString("coupon"))){
								Type tt = new TypeToken<ArrayList<CouponBean>>() {
								}.getType();
								l = gson.fromJson(m.toString(), tt);
							}
							mall.get(i).setCoupons(l);
						}

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
					bean.setMsg(error);
					bean.setCode("500");
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");
			}  

			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	} 


	/**
	 *  订单付款(未付给卖家，需要确认收货后再确认支付)订单预付款 宋 个人中心
	 *  不使用
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
					bean.setMsg(error);
					bean.setCode("500");
				}  
			}else{
				bean.setMsg(error);
				bean.setCode("500");
			}
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
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
					bean.setMsg(error);
					bean.setCode("500");
				}  

			}else{
				bean.setMsg(error);
				bean.setCode("500");
			}
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	} 

	/**
	 *  确认付款/确认收货（买家操作） 宋
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
					bean.setMsg(error);
					bean.setCode("500");
				}  
			}else{
				bean.setMsg(error);
				bean.setCode("500");
			}
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	} 



	/**
	 *  确认收款（卖家订单直接收取付款（卖家收钱操作））
	 * @param itemid		订单ID
	 * @param authstr		唯一标示
	 * @return
	 */
	public BaseBean Order_getpay(String itemid,String authstr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+"order.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","getpay");
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
					bean.setMsg(error);
					bean.setCode("500");
				}  

			}else{
				bean.setMsg(error);
				bean.setCode("500");
			}
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
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
		NameValuePair p = new BasicNameValuePair("action","comment");
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
					bean.setMsg(error);
					bean.setCode("500");
				}  
			}else{
				bean.setMsg(error);
				bean.setCode("500");
			}
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
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
		String t;
		if(submit==1){
			t="true";
		}else{
			t = "false";
		}
		NameValuePair p2 = new BasicNameValuePair("submit",t);
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
					bean.setMsg(error);
					bean.setCode("500");
				}  

			}else{
				bean.setMsg(error);
				bean.setCode("500");
			}
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	} 


	/**
	 *  获取物流列表&当前后台时间
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
					if("200".equals(obj.getString("code"))){
						JSONObject data = obj.getJSONObject("data");
						JSONArray send_types = data.getJSONArray("send_types");
						Type t = new TypeToken<ArrayList<String>>() {
						}.getType();
						listtype  = gson.fromJson(send_types.toString(), t);
						bean.setSend_time(data.getString("send_time"));
					}
					bean.setSend_types(listtype);
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				} else {  
					bean.setMsg(error);
					bean.setCode("500");
				}  

			}else{
				bean.setMsg(error);
				bean.setCode("500");
			}
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	} 


	/**
	 *  申请退货(退款)
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
					bean.setMsg(error);
					bean.setCode("500");
				}  

			}else{
				bean.setMsg(error);
				bean.setCode("500");
			}
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
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
	public RefundInfoBean Order_refunddata(String itemid,String authstr) {  
		RefundInfoBean bean = new RefundInfoBean();
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
					if("200".equals(obj.getString("code"))){
						JSONObject data = obj.getJSONObject("data");
						Type type = new TypeToken<RefundInfoBean>() {
						}.getType();
						bean = gson.fromJson(data.toString(), type);
					}
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				} else {  
					bean.setMsg(error);
					bean.setCode("500");
				}  

			}else{
				bean.setMsg(error);
				bean.setCode("500");
			}
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
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
		String url = ServiceUrl.Base+"trade.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","buy");
		list.add(p);
		for(int i=0;i<listshop.size();i++){
			ShopBean sb = listshop.get(i);
			for(int j=0;j<sb.getMall().size();j++){
				ShopItemBean sib = sb.getMall().get(j);
				NameValuePair p1 = new BasicNameValuePair("post["+sib.getItemid()+"][num]",""+sib.getNum());
				list.add(p1);
				String c = "";
				if(sib.getCoupons()!=null){
					for(int k =0;k<sib.getCoupons().size();k++){
						if(sib.getCoupons().get(k).isIsck()){
							c = sib.getCoupons().get(k).getId();
						}
					}
				}
				NameValuePair p2 = new BasicNameValuePair("post["+sib.getItemid()+"][coupon]",c);
				list.add(p2);
				String not = "";
				if(Util.IsNull(sb.getNote())){
					not = sb.getNote();
				}
				NameValuePair p3 = new BasicNameValuePair("post["+sb.getCompanyid()+"][note]",not);
				list.add(p3);
				String e = "";
				if(sb.getExpress()!=null){
					for(int k =0;k<sb.getExpress().size();k++){
						if(sb.getExpress().get(k).isIsck()){
							e = sb.getExpress().get(k).getItemid();
						}
					}
				}
				NameValuePair p4 = new BasicNameValuePair("post["+sb.getCompanyid()+"][express]",e);
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
					bean.setMsg(error);
					bean.setCode("500");
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	} 

	/**
	 * 结算订单，支付（购物车生成订单后直接支付）高川
	 * @param lsb		订单实体
	 * @param authstr	唯一标示
	 * @param password	password
	 * @return
	 */
	public PayBean PayOrder(ListOrderCommit lsb,String authstr,String password) {  
		PayBean bean = new PayBean();
		ArrayList<OrderCommitBean> listshop = lsb.getList();
		String url = ServiceUrl.Base+"trade.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","confirm");
		list.add(p);
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
							bean.setType(obj.getString("type"));
						}
						bean.setMsg(obj.getString("message"));
						bean.setCode(obj.getString("code"));
					}
				}else{
					bean.setMsg(error);
					bean.setCode("500");
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	} 



	/**
	 *  确认支付支付（个人中心订单列表跳转支付）预付款高川
	 * @param trade			订单ID
	 * @param coupon		优惠券
	 * @param authstr		唯一标示
	 * @param password		password
	 * @return
	 */
	public PayBean PayOrder(String trade,String coupon,String authstr,String password) {  
		PayBean bean = new PayBean();
		String url = ServiceUrl.Base+"trade.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","confirm");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("coupon["+trade+"]",coupon);
		list.add(p1);
		NameValuePair pi = new BasicNameValuePair("item[]",trade);
		list.add(pi);
		NameValuePair po = new BasicNameValuePair("password",password);
		list.add(po);
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
							if(Util.IsNull(obj.getString("type"))){
								bean.setType(obj.getString("type"));
							}
						}
						bean.setMsg(obj.getString("message"));
						bean.setCode(obj.getString("code"));
					}
				}else{
					bean.setMsg(error);
					bean.setCode("500");
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	} 




	/**
	 * 订单详情
	 * @param itemid	订单ID
	 * @param authstr	唯一标示
	 * @return
	 */
	public OrderDetaileBean getOrderDetaile(String itemid,String authstr) {  
		OrderDetaileBean bean = new OrderDetaileBean();
		String url = ServiceUrl.Base+"order.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","orderinfo");
		list.add(p);
		NameValuePair po = new BasicNameValuePair("itemid",itemid);
		list.add(po);
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
							String d = obj.getString("data");
							if(Util.IsNull(d)){
								JSONObject data = new JSONObject(d);
								Type t = new TypeToken<OrderDetaileBean>() {
								}.getType();
								bean = gson.fromJson(data.toString(), t);
							}
						}
						bean.setMsg(obj.getString("message"));
						bean.setCode(obj.getString("code"));
					}
				}else{
					bean.setMsg(error);
					bean.setCode("500");
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	} 


	/**
	 *  退款详情
	 * @param itemid		订单ID
	 * @param authstr		唯一标示
	 * @return
	 */
	public RefundInfoDetaileBean Order_refundinfo(String itemid,String authstr) {  
		RefundInfoDetaileBean bean = new RefundInfoDetaileBean();
		String url = ServiceUrl.Base+"order.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","refundinfo");
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
					if("200".equals(obj.getString("code"))){
						JSONObject data = obj.getJSONObject("data");
						Type type_ad = new TypeToken<RefundInfoDetaileBean>() {
						}.getType();
						bean = gson.fromJson(data.toString(), type_ad);
					}
					bean.setMsg(obj.getString("message"));
					bean.setCode(obj.getString("code"));
				} else {  
					bean.setMsg(error);
					bean.setCode("500");
				}  
			}else{
				bean.setMsg(error);
				bean.setCode("500");
			}
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	} 






	/**
	 * 企业注册
	 * @param regid			会员分组，企业会员6，养殖户5
	 * @param username		会员用户名（小写字母(a-z)、数字(0-9)、下划线(_)、中划线(-)，且以字母或数字开头和结尾）
	 * @param password		密码
	 * @param truename		真实姓名
	 * @param gender		性别 1是男，2是女
	 * @param areaid		所在地
	 * @param address		详细地址
	 * 企业会员必填参数
	 * @param mobile		手机号码
	 * @param company		公司名称
	 * @param type			公司类型	中文即可
	 * @param telephone		公司电话
	 * @return
	 */
	public BaseBean Regist(int regid,String username,String password,String truename,int gender
			,String areaid,String address,
			String mobile,String company,String type,String telephone) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+"register.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("post[regid]",""+regid);
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("post[username]",username);
		list.add(p1);
		NameValuePair p2 = new BasicNameValuePair("post[password]",password);
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("post[cpassword]",password);
		list.add(p3);
		NameValuePair p4 = new BasicNameValuePair("post[truename]",truename);
		list.add(p4);
		NameValuePair p5 = new BasicNameValuePair("post[gender]",""+gender);
		list.add(p5);
		NameValuePair p6 = new BasicNameValuePair("post[areaid]",areaid);
		list.add(p6);
		NameValuePair p7 = new BasicNameValuePair("post_fields[xxdz]",address);
		list.add(p7);
		//企业会员必填参数
		NameValuePair p8 = new BasicNameValuePair("post[mobile]",mobile);
		list.add(p8);
		NameValuePair p9 = new BasicNameValuePair("post[company]",company);
		list.add(p9);
		NameValuePair p10 = new BasicNameValuePair("post[type]",type);
		list.add(p10);
		NameValuePair p11 = new BasicNameValuePair("post[telephone]",telephone);
		list.add(p11);



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
					bean.setMsg(error);
					bean.setCode("500");
				}  

			}else{
				bean.setMsg(error);
				bean.setCode("500");
			}
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	} 



	/**
	 * 
	 * 养殖户注册
	 * @param regid			会员分组，企业会员6，养殖户5
	 * @param username		会员用户名（小写字母(a-z)、数字(0-9)、下划线(_)、中划线(-)，且以字母或数字开头和结尾）
	 * @param password		密码
	 * @param truename		真实姓名
	 * @param gender		性别 1是男，2是女
	 * @param areaid		所在地
	 * @param address		详细地址
	 * @param mobile		手机号
	 * 养殖户必填参数
	 * @param yzpz			养殖品种
	 * @param syzl			使用饲料
	 * @param xsycj			现在使用哪个厂家饲料
	 * @param kjsjw			可以接受的价格
	 * @param catid			急需产品分类
	 * 养殖户选填参数
	 * @param bankcard		所用银行卡
	 * @param yzsl			养殖数量
	 * @param tjr			推荐人
	 * @return
	 */
	public BaseBean Regist(int regid,String username,String password,String truename,int gender
			,String areaid,String address,String mobile,
			String yzpz,String syzl,String xsycj,String kjsjw,String catid,
			String bankcard,String yzsl,String tjr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+"register.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("post[regid]",""+regid);
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("post[username]",username);
		list.add(p1);
		NameValuePair p2 = new BasicNameValuePair("post[password]",password);
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("post[cpassword]",password);
		list.add(p3);
		NameValuePair p4 = new BasicNameValuePair("post[truename]",truename);
		list.add(p4);
		NameValuePair p5 = new BasicNameValuePair("post[gender]",""+gender);
		list.add(p5);
		NameValuePair p6 = new BasicNameValuePair("post[areaid]",areaid);
		list.add(p6);
		NameValuePair p7 = new BasicNameValuePair("post_fields[xxdz]",address);
		list.add(p7);
		NameValuePair p8 = new BasicNameValuePair("post[mobile]",mobile);
		list.add(p8);
		//养殖户必填参数
		NameValuePair pl1 = new BasicNameValuePair("post_fields[yzpz]",yzpz);
		list.add(pl1);
		NameValuePair pl2 = new BasicNameValuePair("post_fields[syzl]",syzl);
		list.add(pl2);
		NameValuePair pl3 = new BasicNameValuePair("post_fields[xsycj]",xsycj);
		list.add(pl3);
		NameValuePair pl4 = new BasicNameValuePair("post_fields[kjsjw]",kjsjw);
		list.add(pl4);
		NameValuePair pl5 = new BasicNameValuePair("post[catid]",catid);
		list.add(pl5);
		//养殖户选填参数
		NameValuePair ps1 = new BasicNameValuePair("post[bankcard]",bankcard);
		list.add(ps1);
		NameValuePair ps2 = new BasicNameValuePair("post_fields[yzsl]",yzsl);
		list.add(ps2);
		NameValuePair ps3 = new BasicNameValuePair("post_fields[tjr]",tjr);
		list.add(ps3);


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
					bean.setMsg(error);
					bean.setCode("500");
				}  

			}else{
				bean.setMsg(error);
				bean.setCode("500");
			}
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	} 




	/**
	 * 获取充值界面银行列表
	 * @param authstr
	 * @return
	 */
	public ListChargeBankBean getBanks(String authstr) {  
		ListChargeBankBean bean = new ListChargeBankBean();
		ArrayList<ChargeBankBean> li = new ArrayList<ChargeBankBean>();
		String url = ServiceUrl.Base+"charge.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","pay");
		list.add(p);
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
							if(Util.IsNull(obj.getString("data"))){
								JSONObject data = obj.getJSONObject("data");
								@SuppressWarnings("unchecked")
								Iterator<String> keys=data.keys();
								while (keys.hasNext ())
								{
									ChargeBankBean b = new ChargeBankBean(); 
									String k = (String) keys.next() + "";
									b.setId(k);
									b.setName(data.getString(k));
									li.add(b);
								}
							}
						}
						bean.setList(li);
						bean.setMsg(obj.getString("message"));
						bean.setCode(obj.getString("code"));
					}
				}else{
					bean.setMsg(error);
					bean.setCode("500");
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	} 

	/**
	 * 获取Tn
	 * @param authstr
	 * @return
	 */
	public TnResultBean getTn(String bank,String amount,String authstr) {  
		TnResultBean bean = new TnResultBean();
		String url = ServiceUrl.Base+"charge.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p = new BasicNameValuePair("action","confirm");
		list.add(p);
		NameValuePair p1 = new BasicNameValuePair("bank",bank);
		list.add(p1);
		NameValuePair p2 = new BasicNameValuePair("amount",amount);
		list.add(p2);
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
				//				StringUtils.removeBOM(strResult);
				//				if(strResult != null && strResult.startsWith("\ufeff"))
				//				{
				//					strResult =  strResult.substring(1);
				//					strResult.replace("\\\\u", "");
				//				}
				if(Util.IsNull(strResult)){
					obj = new JSONObject(strResult);
					if(obj!=null){
						if(obj.getInt("code")==200){
							bean.setTn(obj.getString("tn"));
						}else{
							bean.setTn("");
						}
						bean.setMsg(obj.getString("message"));
						bean.setCode(""+obj.getInt("code"));
					}
				}else{
					bean.setMsg(error);
					bean.setCode("500");
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	} 


	/**
	 * 供求首页
	 * @param type  	1分类 2地区 3综合排序 4 筛选
	 * @param order		排序 0综合 1销量降序 2销量升序 3、4价格 5、6VIP
	 * @param page 		页数
	 * @param catid		产品id
	 * @param areaid	地区id
	 * @param minprice	最小价格
	 * @param maxprice	最大价格
	 * @return
	 */
	public ListProductBean SeanchForGQ(int type,int order,int page,int catid,int areaid,
			float minprice,float maxprice) {  
		ListProductBean bean = new ListProductBean();
		ArrayList<ProductBean> lp = new ArrayList<ProductBean>();
		String url = ServiceUrl.Base+"search.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		if(type==1){
			NameValuePair p1 = new BasicNameValuePair("catid",""+catid);
			list.add(p1);
		}else if(type==2){
			NameValuePair p1 = new BasicNameValuePair("areaid",""+areaid);
			list.add(p1);
		}else if(type==3){
			NameValuePair p1 = new BasicNameValuePair("order",""+order);
			list.add(p1);
		}else if(type==4){
			NameValuePair pp1 = new BasicNameValuePair("minprice",""+minprice);
			list.add(pp1);
			NameValuePair pp2 = new BasicNameValuePair("maxprice",""+maxprice);
			list.add(pp2);
		}
		NameValuePair p2 = new BasicNameValuePair("page",""+page);
		list.add(p2);

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
						bean.setList(lp);
						bean.setMsg("暂无搜索结果");
						bean.setCode("200");	
					}
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");	
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	}


	/**
	 * 收藏/取消
	 * @param t		1收藏 2取消收藏
	 * @param type	1(产品)，2商铺
	 * @param id	产品或者商铺的id
	 * @return
	 */
	@SuppressWarnings("unused")
	public BaseBean Shoucang(int t,int type,int id,String authstr) {  
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+"personal_center.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		if(t==1){
			NameValuePair p1 = new BasicNameValuePair("action","collect");
			list.add(p1);
		}else if(t==2){
			NameValuePair p1 = new BasicNameValuePair("action","cancle");
			list.add(p1);
		}
		NameValuePair p = new BasicNameValuePair("type",""+type);
		list.add(p);
		NameValuePair p2 = new BasicNameValuePair("id",""+id);
		list.add(p2);
		NameValuePair p3 = new BasicNameValuePair("authstr",""+authstr);
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
					/* 读返回数据 */  
					obj = new JSONObject(strResult);
					if(obj!=null){
						bean.setMsg(obj.getString("message"));
						bean.setCode(obj.getString("code"));
					}else{
						bean.setMsg(error);
						bean.setCode("500");
					}
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");	
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	}



	/**
	 *  获取收藏产品，收藏店铺，待付款，待收货，待评价，待退款数量
	 * @return
	 */
	@SuppressWarnings("unused")
	public CenterCountBean getCenterCount(String authstr) {  
		CenterCountBean bean = new CenterCountBean();
		String url = ServiceUrl.Base+"personal_center.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p1 = new BasicNameValuePair("action","count");
		list.add(p1);
		NameValuePair p = new BasicNameValuePair("authstr",authstr);
		list.add(p);

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
					/* 读返回数据 */  
					obj = new JSONObject(strResult);
					if(obj!=null){
						if("200".equals(obj.getString("code"))){
							JSONObject data = obj.getJSONObject("data");
							Type type_re = new TypeToken<CenterCountBean>() {
							}.getType();
							bean = gson.fromJson(data.toString(), type_re);
						}
						bean.setMsg(obj.getString("message"));
						bean.setCode(obj.getString("code"));
					}else{
						bean.setMsg(error);
						bean.setCode("500");
					}
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");	
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	}


	/**
	 * 获取收藏产品列表
	 * @param page	页码
	 * @return
	 */
	@SuppressWarnings("unused")
	public ListSCProductBean getCenterProduct(int page,String authstr) {  
		ListSCProductBean bean = new ListSCProductBean();
		ArrayList<SCProductBean> lp = new ArrayList<SCProductBean>();
		String url = ServiceUrl.Base+"personal_center.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p1 = new BasicNameValuePair("action","product");
		list.add(p1);
		NameValuePair p = new BasicNameValuePair("page",""+page);
		list.add(p);

		NameValuePair pp = new BasicNameValuePair("authstr",authstr);
		list.add(pp);

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
					/* 读返回数据 */  
					obj = new JSONObject(strResult);
					if(obj!=null){
						if("200".equals(obj.getString("code"))
								&&!obj.getString("data").equals("[]")){
							//							JSONObject data = obj.("data");
							JSONArray data = obj.getJSONArray("data");
							Type type_re = new TypeToken<ArrayList<SCProductBean>>() {
							}.getType();
							lp = gson.fromJson(data.toString(), type_re);
						}
						bean.setList(lp);
						bean.setMsg(obj.getString("message"));
						bean.setCode(obj.getString("code"));
					}else{
						bean.setMsg(error);
						bean.setCode("500");
					}
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");	
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	}

	/**
	 * 获取收藏店铺列表
	 * @param page	页码
	 * @return
	 */
	@SuppressWarnings("unused")
	public ListCenterShopBean getCenterShop(int page,String authstr) {  
		ListCenterShopBean bean = new ListCenterShopBean();
		ArrayList<CenterShopBean> lp = new ArrayList<CenterShopBean>();
		String url = ServiceUrl.Base+"personal_center.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p1 = new BasicNameValuePair("action","shop");
		list.add(p1);
		NameValuePair p = new BasicNameValuePair("Page ",""+page);
		list.add(p);

		NameValuePair pp = new BasicNameValuePair("authstr",authstr);
		list.add(pp);


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
					/* 读返回数据 */  
					obj = new JSONObject(strResult);
					if(obj!=null){
						if("200".equals(obj.getString("code"))
								&&!obj.getString("data").equals("[]")&&Util.IsNull(obj.getString("data"))){
							JSONArray data = obj.getJSONArray("data");
							Type type_re = new TypeToken<ArrayList<CenterShopBean>>() {
							}.getType();
							lp = gson.fromJson(data.toString(), type_re);
						}
						bean.setList(lp);
						bean.setMsg(obj.getString("message"));
						bean.setCode(obj.getString("code"));
					}else{
						bean.setMsg(error);
						bean.setCode("500");
					}
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");	
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	}


	/**
	 * 获取店铺首页
	 * @param page	页码
	 * @return
	 */
	@SuppressWarnings("unused")
	public ListShopHomeBean getShopHomeData(String id,int type,int page) {  
		ListShopHomeBean bean = new ListShopHomeBean();
		ArrayList<ProductBean> list_re = new ArrayList<ProductBean>();
		ArrayList<ProductBean> list_data = new ArrayList<ProductBean>();
		ShopInfoBean infobean = new ShopInfoBean();
		String url = ServiceUrl.Base+"shop.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p1 = new BasicNameValuePair("action","shop");
		list.add(p1);
		if(type==1){
			NameValuePair p2 = new BasicNameValuePair("id",""+id);
			list.add(p2);
		}else{
			NameValuePair p2 = new BasicNameValuePair("shopname",""+id);
			list.add(p2);
		}
		NameValuePair p = new BasicNameValuePair("page",""+page);
		list.add(p);
		//		NameValuePair pp = new BasicNameValuePair("authstr",authstr);
		//		list.add(pp);


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
					/* 读返回数据 */  
					obj = new JSONObject(strResult);
					if(obj!=null){

						if("200".equals(obj.getString("code"))
								&&Util.IsNull(obj.getString("data"))){
							JSONObject data = obj.getJSONObject("data");
							JSONObject shop = data.getJSONObject("shopinfo");
							if(Util.IsNull(shop.getString("userid"))){
								infobean.setUserid(shop.getString("userid"));
							}
							if(Util.IsNull(shop.getString("username"))){
								infobean.setUsername(shop.getString("username"));
							}
							if(Util.IsNull(shop.getString("company"))){
								infobean.setCompany(shop.getString("company"));
							}
							if(Util.IsNull(shop.getString("totalgoods"))){
								infobean.setTotalgoods(shop.getString("totalgoods"));
							}
							if(Util.IsNull(shop.getString("thumb"))){
								infobean.setThumb(shop.getString("thumb"));
							}
							if(Util.IsNull(shop.getString("collect"))){
								infobean.setCollect(shop.getInt("collect"));
							}
							if(Util.IsNull(shop.getString("grade"))){
								infobean.setGrade(shop.getString("grade"));
							}
							if(Util.IsNull(shop.getString("describe"))){
								infobean.setDescribe(shop.getInt("describe"));
							}
							if(Util.IsNull(shop.getString("service"))){
								infobean.setService(shop.getInt("service"));
							}
							if(Util.IsNull(shop.getString("logistics"))){
								infobean.setLogistics(shop.getInt("logistics"));
							}
						}
						if("200".equals(obj.getString("code"))
								&& Util.IsNull(obj.getString("data"))){
							JSONObject data = obj.getJSONObject("data");
							if(!data.getString("recommend").equals("[]")){
								JSONArray recommend = data.getJSONArray("recommend");
								Type type_re = new TypeToken<ArrayList<ProductBean>>() {
								}.getType();
								list_re = gson.fromJson(recommend.toString(), type_re);
							}
						}
						if("200".equals(obj.getString("code"))
								&& Util.IsNull(obj.getString("data"))){
							//							JSONArray data = obj.getJSONArray("list");
							JSONObject data = obj.getJSONObject("data");
							if(!data.getString("list").equals("[]")){
								JSONArray l = data.getJSONArray("list");
								Type type_re = new TypeToken<ArrayList<ProductBean>>() {
								}.getType();
								list_data = gson.fromJson(l.toString(), type_re);
							}
						}
						bean.setShopInfoBean(infobean);
						bean.setRecommend(list_re);
						bean.setList(list_data);
						bean.setMsg(obj.getString("message"));
						bean.setCode(obj.getString("code"));
					}else{
						bean.setMsg(error);
						bean.setCode("500");
					}
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");	
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	}




	/**
	 * 获取店铺自定义分类
	 * @param id
	 * @param shopname
	 * @return
	 */
	@SuppressWarnings("unused")
	public ListStoreCatBean getShopCat(String id,String shopname) {  
		ListStoreCatBean bean = new ListStoreCatBean();
		ArrayList<StorecatBean> list_data = new ArrayList<StorecatBean>();
		ShopInfoBean infobean = new ShopInfoBean();
		String url = ServiceUrl.Base+"shop.php";
		List<NameValuePair> list = new ArrayList<NameValuePair>(); 
		NameValuePair p1 = new BasicNameValuePair("action","shopcategory");
		list.add(p1);
		if(Util.IsNull(id)){
			NameValuePair p = new BasicNameValuePair("id",""+id);
			list.add(p);
		}else{
			NameValuePair p = new BasicNameValuePair("shopname",""+shopname);
			list.add(p);
		}


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
					/* 读返回数据 */  
					obj = new JSONObject(strResult);
					if(obj!=null){

						if("200".equals(obj.getString("code"))
								&&Util.IsNull(obj.getString("data"))){
							JSONObject data = obj.getJSONObject("data");
							JSONArray l = data.getJSONArray("list");
							Type type_re = new TypeToken<ArrayList<StorecatBean>>() {
							}.getType();
							list_data = gson.fromJson(l.toString(), type_re);
						}
						bean.setList(list_data);
						bean.setMsg(obj.getString("message"));
						bean.setCode(obj.getString("code"));
					}else{
						bean.setMsg(error);
						bean.setCode("500");
					}
				}
			} else {  
				bean.setMsg(error);
				bean.setCode("500");	
			}  
			return bean;
		} catch (ClientProtocolException e) {  
			e.printStackTrace(); 

			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (IOException e) {  

			e.printStackTrace();  
			System.out.println("IO异常");
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		} catch (Exception e) {  

			e.printStackTrace();  
			bean.setMsg(error);
			bean.setCode("500");
			return bean;
		}  
	}










	/******************************************************************************************************************/

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
