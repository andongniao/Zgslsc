package com.example.educonsult.net;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.example.educonsult.MyApplication;
import com.example.educonsult.R;
import com.example.educonsult.beans.AddressBean;
import com.example.educonsult.beans.AreaBean;
import com.example.educonsult.beans.BanksBean;
import com.example.educonsult.beans.BaseBean;
import com.example.educonsult.beans.CenterUserBean;
import com.example.educonsult.beans.CommentBean;
import com.example.educonsult.beans.CommentStar;
import com.example.educonsult.beans.CompanyBean;
import com.example.educonsult.beans.FenleiBean;
import com.example.educonsult.beans.HomeBean;
import com.example.educonsult.beans.ListAddressBean;
import com.example.educonsult.beans.ListAreaBean;
import com.example.educonsult.beans.ListBanksBean;
import com.example.educonsult.beans.ListComment;
import com.example.educonsult.beans.ListFenleiBean;
import com.example.educonsult.beans.ListMoneyBean;
import com.example.educonsult.beans.ListOrderBean;
import com.example.educonsult.beans.ListProductBean;
import com.example.educonsult.beans.ListShopBean;
import com.example.educonsult.beans.ListXinjianBean;
import com.example.educonsult.beans.MallInfoBean;
import com.example.educonsult.beans.MoneyDetaileBean;
import com.example.educonsult.beans.OrderBean;
import com.example.educonsult.beans.OrderFields;
import com.example.educonsult.beans.ProdectDetaileBean;
import com.example.educonsult.beans.ProductBean;
import com.example.educonsult.beans.ShopBean;
import com.example.educonsult.beans.UserBean;
import com.example.educonsult.beans.XinJianBean;
import com.example.educonsult.beans.XinJianDetaileBean;
import com.example.educonsult.tools.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class Send {
	private Context context;
	private Gson gson;
	//
	public Send(Context context) {
		this.context = context;
		gson = new Gson();

	}

	/**
	 * 获取首页信息
	 */
	public HomeBean RequestHome() {
		HomeBean home = new HomeBean();
		String url = ServiceUrl.Home_url;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);

		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					home.setCode(code);
					home.setMsg(msg);
					JSONObject data = object.getJSONObject("data");
					JSONArray recommend = data.getJSONArray("recommend");
					Type type_re = new TypeToken<ArrayList<ProductBean>>() {
					}.getType();
					ArrayList<ProductBean> list_recommend = gson.fromJson(recommend.toString(), type_re);
					JSONArray hot = data.getJSONArray("hot");
					Type type_hot = new TypeToken<ArrayList<ProductBean>>() {
					}.getType();
					ArrayList<ProductBean> list_hot = gson.fromJson(hot.toString(), type_hot);
					JSONArray company = data.getJSONArray("company");
					Type type_com = new TypeToken<ArrayList<CompanyBean>>() {
					}.getType();
					ArrayList<CompanyBean> list_com = gson.fromJson(company.toString(), type_com);
					JSONArray ad = data.getJSONArray("ad");
					Type type_ad = new TypeToken<ArrayList<String>>() {
					}.getType();
					ArrayList<String> list_ad = gson.fromJson(ad.toString(), type_ad);
					//new ArrayList<ProductBean>();
					//					for (int i = 1; i < 9; i++) {
					//						String json = data.getString("ad_" + i).toString();
					//						ArrayList<GoodsBean> s = gson.fromJson(json, type);
					//						l.add(s);
					//					}
					//					List.setList(l);
					home.setRecommend(list_recommend);
					home.setHot(list_hot);
					home.setCompany(list_com);
					home.setAd(list_ad);
					//					home.setCode(code);
					//					home.setMsg(msg);
					return home;
				} else {
					home.setMsg(msg);
					home.setCode(code);
					return home;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				home.setCode("500");
				home.setMsg("服务器异常，请稍候再试...");
				return home;
			}
		} else {
			home.setCode("500");
			home.setMsg(context.getResources().getString(R.string.net_is_eor));
			return home;
		}

	}
	/**
	 * 登录
	 */
	public UserBean Login(String username,String password) {
		UserBean bean = new UserBean();
		String url = ServiceUrl.Base+ServiceUrl.Login_url_name+username+
				ServiceUrl.Login_url_password+password;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);
		if (jsonStr != null && !jsonStr.equals("")) { 
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					bean.setCode(code);
					bean.setMsg(msg);
					JSONObject data = object.getJSONObject("data");
					String authstr = data.getString("authstr");
//					int type = data.getInt("type");
//					bean.setType(type);
					bean.setAuthstr(authstr);
					return bean;
				} else {
					if(Util.IsNull(msg)){
						bean.setMsg(msg);
					}else{
						bean.setMsg("");
					}
					bean.setCode(code);
					return bean;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("服务器异常，请稍候再试...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}


	/**
	 * 获取城市
	 */
	public ListAreaBean GetArea() {
		ListAreaBean list = new ListAreaBean();
		ArrayList<AreaBean> list1 = new ArrayList<AreaBean>();
		ArrayList<AreaBean> list2 = new ArrayList<AreaBean>();
		ArrayList<AreaBean> list3 = new ArrayList<AreaBean>();

		AreaBean bean = new AreaBean();
		String url = ServiceUrl.Base+ServiceUrl.City;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);
		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					list.setCode(code);
					list.setMsg(msg);
					JSONArray data = object.getJSONArray("data");
					for(int i=0;i<34;i++){
						if(data.getJSONObject(i)!=null){
							JSONObject s1 = data.getJSONObject(i);
							AreaBean b1 = new AreaBean();
							b1.setAreaid(s1.getInt("areaid"));
							b1.setArename(s1.getString("areaname"));
							int index = s1.getInt("childnum");
							if(index!=0){
								list2 = new ArrayList<AreaBean>();
								JSONArray c1 = s1.getJSONArray("child");
								for(int j=0;j<index;j++){
									if(c1!=null && c1.getJSONObject(j)!=null){
										JSONObject s2 = c1.getJSONObject(j);
										AreaBean b2 = new AreaBean();
										b2.setAreaid(s2.getInt("areaid"));
										b2.setArename(s2.getString("areaname"));
										int pos = s2.getInt("childnum");
										list3 = new ArrayList<AreaBean>();
										if(s2!=null && pos!=0){
											JSONArray c2 = s2.getJSONArray("child");
											//									list3 = new ArrayList<AreaBean>();
											for(int k=0;k<pos;k++){
												if(c2!=null && c2.getJSONObject(k)!=null){
													JSONObject s3 = c2.getJSONObject(k);
													AreaBean b3 = new AreaBean();
													b3.setAreaid(s3.getInt("areaid"));
													b3.setArename(s3.getString("areaname"));
													//										int po3 = s3.getInt("childnum");
													b3.setChild(new ArrayList<AreaBean>());
													list3.add(b3);
												}
											}
											b2.setChild(list3);
										}
										list2.add(b2);
									}
								}
								b1.setChild(list2);
							}else{
								b1.setChild(new ArrayList<AreaBean>());
							}
							list1.add(b1);
						}
					}
					list.setList(list1);
					return list;
				} else {
					if(Util.IsNull(msg)){
						list.setMsg(msg);
					}else{
						list.setMsg("");
					}
					list.setCode(code);
					return list;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				System.out.println("***********************************"+e);
				list.setCode("500");
				list.setMsg("服务器异常，请稍候再试...");
				return list;
			}
		} else {
			list.setCode("500");
			list.setMsg(context.getResources().getString(R.string.net_is_eor));
			return list;
		}

	}

	/**
	 * 获取分类
	 */
	public ListFenleiBean GetFenlei() {
		ListFenleiBean list = new ListFenleiBean();
		ArrayList<FenleiBean> list1 = new ArrayList<FenleiBean>();
		ArrayList<FenleiBean> list2 = new ArrayList<FenleiBean>();
		ArrayList<FenleiBean> list3 = new ArrayList<FenleiBean>();

		FenleiBean bean = new FenleiBean();
		String url = ServiceUrl.Base+ServiceUrl.Fenlei;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);
		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					list.setCode(code);
					list.setMsg(msg);
					JSONArray data = object.getJSONArray("data");
					for(int i=0;i<7;i++){
						if(data.getJSONObject(i)!=null){
							JSONObject s1 = data.getJSONObject(i);
							FenleiBean b1 = new FenleiBean();
							b1.setCatid(s1.getInt("catid"));
							b1.setCatname(s1.getString("catname"));
							int index = s1.getInt("childnum");
							if(index!=0){
								list2 = new ArrayList<FenleiBean>();
								JSONArray c1 = s1.getJSONArray("child");
								for(int j=0;j<index;j++){
									if(c1!=null && c1.getJSONObject(j)!=null){
										JSONObject s2 = c1.getJSONObject(j);
										FenleiBean b2 = new FenleiBean();
										b2.setCatid(s2.getInt("catid"));
										b2.setCatname(s2.getString("catname"));
										int pos = s2.getInt("childnum");
										list3 = new ArrayList<FenleiBean>();
										if(s2!=null && pos!=0){
											JSONArray c2 = s2.getJSONArray("child");
											//									list3 = new ArrayList<FenleiBean>();
											for(int k=0;k<pos;k++){
												if(c2!=null && c2.getJSONObject(k)!=null){
													JSONObject s3 = c2.getJSONObject(k);
													FenleiBean b3 = new FenleiBean();
													b3.setCatid(s3.getInt("catid"));
													b3.setCatname(s3.getString("catname"));
													//										int po3 = s3.getInt("childnum");
													b3.setChild(new ArrayList<FenleiBean>());
													list3.add(b3);
												}
											}
											b2.setChild(list3);
										}
										list2.add(b2);
									}
								}
								b1.setChild(list2);
							}else{
								b1.setChild(new ArrayList<FenleiBean>());
							}
							list1.add(b1);
						}
					}
					list.setList(list1);
					return list;
				} else {
					if(Util.IsNull(msg)){
						list.setMsg(msg);
					}else{
						list.setMsg("");
					}
					list.setCode(code);
					return list;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				System.out.println("***********************************"+e);
				list.setCode("500");
				list.setMsg("服务器异常，请稍候再试...");
				return list;
			}
		} else {
			list.setCode("500");
			list.setMsg(context.getResources().getString(R.string.net_is_eor));
			return list;
		}

	}

	/**
	 * 获取产品详情
	 */
	public ProdectDetaileBean GetProductDetaile(String id) {
		ProdectDetaileBean bean = new ProdectDetaileBean();
		String url = ServiceUrl.Base+ServiceUrl.Product_url;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);
		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					bean.setCode(code);
					bean.setMsg(msg);
					JSONObject data = object.getJSONObject("data");
					JSONObject mall = data.getJSONObject("mallinfo");
					Type type = new TypeToken<MallInfoBean>() {
					}.getType();
					MallInfoBean mallbean = gson.fromJson(mall.toString(), type);

					JSONArray recommend = data.getJSONArray("recommend");
					Type type_re = new TypeToken<ArrayList<ProductBean>>() {
					}.getType();
					ArrayList<ProductBean> list_recommend = gson.fromJson(recommend.toString(), type_re);

					JSONArray buy = data.getJSONArray("buyedlist");
					Type type_buy = new TypeToken<ArrayList<ProductBean>>() {
					}.getType();
					ArrayList<ProductBean> list_buy = gson.fromJson(buy.toString(), type_buy);


					bean.setMallinfo(mallbean);
					bean.setRecommend(list_recommend);
					bean.setBuyedlist(list_buy);
					return bean;
				} else {
					if(Util.IsNull(msg)){
						bean.setMsg(msg);
					}else{
						bean.setMsg("");
					}
					bean.setCode(code);
					return bean;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("服务器异常，请稍候再试...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}


	/**
	 * 获取单品全部评论
	 */
	public ListComment GetComment(String id,int page,String s) {
		ListComment bean = new ListComment();
		ArrayList<CommentBean> listcomm = new ArrayList<CommentBean>();
		ArrayList<CommentStar> liststar = new ArrayList<CommentStar>();
		String url;
		if(Util.IsNull(s)){
			url = ServiceUrl.Base+ServiceUrl.Product_comment_head+
					id+ServiceUrl.Product_comment_foot+page+
					ServiceUrl.Product_comment_star+s;
		}else{
			url = ServiceUrl.Base+ServiceUrl.Product_comment_head+
					id+ServiceUrl.Product_comment_foot+page;
		}
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);

		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					bean.setCode(code);
					bean.setMsg(msg);
					JSONArray data = object.getJSONArray("data");
					JSONArray star = object.getJSONArray("star");
					JSONObject j0 = star.getJSONObject(0);
					Type type0 = new TypeToken<CommentStar>() {
					}.getType();
					CommentStar c0 = gson.fromJson(j0.toString(), type0);
					if(c0!=null){
						liststar.add(c0);
					}
					JSONObject j1 = star.getJSONObject(1);
					Type type1 = new TypeToken<CommentStar>() {
					}.getType();
					CommentStar c1 = gson.fromJson(j1.toString(), type1);
					if(c1!=null){
						liststar.add(c1);
					}
					JSONObject j2 = star.getJSONObject(2);
					Type type2 = new TypeToken<CommentStar>() {
					}.getType();
					CommentStar c2 = gson.fromJson(j2.toString(), type2);
					if(c2!=null){
						liststar.add(c2);
					}
					int index = Integer.parseInt(c0.getNumn())+Integer.parseInt(c1.getNumn())+
							Integer.parseInt(c2.getNumn());
					for(int i=0;i<index;i++){
						if(data!=null&&!"[]".equals(data.toString())){
						JSONObject j = data.getJSONObject(i);
						Type t = new TypeToken<CommentBean>() {
						}.getType();
						CommentBean cb = gson.fromJson(j.toString(), t);
						if(cb!=null){
							listcomm.add(cb);
						}
						}

					}
					bean.setComlist(listcomm);
					bean.setComstar(liststar);
					return bean;
				} else {
					bean.setMsg(msg);
					bean.setCode(code);
					return bean;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("服务器异常，请稍候再试...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}

	
	
	
	/**
	 * 个人中心推荐单品列表
	 */
	public ListProductBean getCenterRecommend() {
		ListProductBean lb = new ListProductBean();
		String url =  ServiceUrl.Base+ServiceUrl.Mycenter_recommend;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);

		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					lb.setCode(code);
					lb.setMsg(msg);
					JSONArray data = object.getJSONArray("data");
					JSONArray j = data.getJSONArray(0);
					Type type_re = new TypeToken<ArrayList<ProductBean>>() {
					}.getType();
					ArrayList<ProductBean> list_recommend = gson.fromJson(j.toString(), type_re);
					lb.setList(list_recommend);
					return lb;
				} else {
					lb.setMsg(msg);
					lb.setCode(code);
					return lb;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				lb.setCode("500");
				lb.setMsg("服务器异常，请稍候再试...");
				return lb;
			}
		} else {
			lb.setCode("500");
			lb.setMsg(context.getResources().getString(R.string.net_is_eor));
			return lb;
		}

	}
	
	
	/**
	 * 获取我的信息
	 */
	public CenterUserBean getMyinfo(int type,String authstr) {
		CenterUserBean bean = new CenterUserBean();
		String baseurl = ServiceUrl.Base;
		String url="";
		if(type==1){
			url = baseurl+ServiceUrl.Mycenter_home_member+authstr;
		}else{
			url = baseurl+ServiceUrl.Mycenter_home_company+authstr;
		}
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);

		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					JSONArray data = object.getJSONArray("data");
					JSONArray j = data.getJSONArray(0);
					Type t = new TypeToken<CenterUserBean>() {
					}.getType();
					bean = gson.fromJson(j.toString(), t);
					bean.setCode(code);
					bean.setMsg(msg);
					return bean;
				} else {
					bean.setMsg(msg);
					bean.setCode(code);
					return bean;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("服务器异常，请稍候再试...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}
	
	/**
	 * 获取钱包详情
	 */
	public ListMoneyBean getMoney(int type,String authstr) {
 		ListMoneyBean list = new ListMoneyBean();
		String baseurl = ServiceUrl.Base;
		String url=baseurl+ServiceUrl.money_hand;
		if(type==MyApplication.money_home){
			url +=ServiceUrl.money_home;
		}else if(type==MyApplication.money_detaile){
			url +=ServiceUrl.money_detaile;
		}else if(type==MyApplication.money_income){
			url +=ServiceUrl.money_income;
		}else if(type==MyApplication.money_pay){
			url +=ServiceUrl.money_pay;
		}
		url=url+ServiceUrl.mycenter_footer+authstr;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);

		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					list.setCode(code);
					list.setMsg(msg);
					JSONArray data = object.getJSONArray("data");
					Type t = new TypeToken<ArrayList<MoneyDetaileBean>>() {
					}.getType();
					ArrayList<MoneyDetaileBean>l = gson.fromJson(data.toString(), t);
					list.setList(l);
					return list;
				} else {
					list.setMsg(msg);
					list.setCode(code);
					return list;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				list.setCode("500");
				list.setMsg("服务器异常，请稍候再试...");
				return list;
			}
		} else {
			list.setCode("500");
			list.setMsg(context.getResources().getString(R.string.net_is_eor));
			return list;
		}

	}
	
	
	/**
	 * 获取购物车列表
	 */
	public ListShopBean getCartlist(String authstr) {
		ListShopBean bean = new ListShopBean();
		ArrayList<ShopBean>list = new ArrayList<ShopBean>();
		String baseurl = ServiceUrl.Base;
		String url="";
			url = baseurl+ServiceUrl.cart_list+authstr;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);

		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					if(Util.IsNull(object.getString("data"))){
					JSONObject data = object.getJSONObject("data");
					@SuppressWarnings("unchecked")
					Iterator<String> keys=data.keys();
					while (keys.hasNext ())
		            {
						String k = (String) keys.next() + "";
						JSONObject js = (JSONObject) data.get(k);
						Type t = new TypeToken<ShopBean>() {
							}.getType();
						ShopBean sb = gson.fromJson(js.toString(), t);
						sb.setCompanyid(k);
						if(sb!=null){
							list.add(0,sb);
						}
						
		            }
				}
					bean.setList(list);
					bean.setCode(code);
					bean.setMsg(msg);
					return bean;
				} else {
					bean.setMsg(msg);
					bean.setCode(code);
					return bean;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("服务器异常，请稍候再试...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}
	
	/**
	 * 添加购物车
	 */
	public BaseBean CartAdd(String itemid,int number,String authstr) {
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+ServiceUrl.cart_add+itemid+"&number"+number+
				ServiceUrl.mycenter_footer+authstr;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);
		if (jsonStr != null && !jsonStr.equals("")) { 
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					bean.setCode(code);
					bean.setMsg(msg);
//					JSONObject data = object.getJSONObject("data");
//					bean.setAuthstr(authstr);
					return bean;
				} else {
					if(Util.IsNull(msg)){
						bean.setMsg(msg);
					}else{
						bean.setMsg("");
					}
					bean.setCode(code);
					return bean;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("服务器异常，请稍候再试...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}

	/**
	 *	删除购物车
	 */
	public BaseBean CartDel(String itemid,String authstr) {
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+ServiceUrl.cart_del+itemid+
				ServiceUrl.mycenter_footer+authstr;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);
		if (jsonStr != null && !jsonStr.equals("")) { 
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					bean.setCode(code);
					bean.setMsg(msg);
//					JSONObject data = object.getJSONObject("data");
//					bean.setAuthstr(authstr);
					return bean;
				} else {
					if(Util.IsNull(msg)){
						bean.setMsg(msg);
					}else{
						bean.setMsg("");
					}
					bean.setCode(code);
					return bean;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("服务器异常，请稍候再试...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}
	
	/**
	 *	清空购物车
	 */
	public BaseBean CartClear(String authstr) {
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+ServiceUrl.cart_clear+
				ServiceUrl.mycenter_footer+authstr;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);
		if (jsonStr != null && !jsonStr.equals("")) { 
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					bean.setCode(code);
					bean.setMsg(msg);
					return bean;
				} else {
					if(Util.IsNull(msg)){
						bean.setMsg(msg);
					}else{
						bean.setMsg("");
					}
					bean.setCode(code);
					return bean;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("服务器异常，请稍候再试...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}
	
	
	
	
	/**
	 * 获取站内信件列表
	 */
	public ListXinjianBean getXinjianlist(String authstr) {
		ListXinjianBean bean = new ListXinjianBean();
		String baseurl = ServiceUrl.Base;
		String url="";
			url = baseurl+ServiceUrl.mycenter_hander+ServiceUrl.sms_list+
					ServiceUrl.mycenter_footer+authstr;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);

		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					JSONArray data = object.getJSONArray("data");
					Type t = new TypeToken<ArrayList<XinJianBean>>() {
					}.getType();
					ArrayList<XinJianBean> list = gson.fromJson(data.toString(), t);
					bean.setList(list);
					bean.setCode(code);
					bean.setMsg(msg);
					return bean;
				} else {
					bean.setMsg(msg);
					bean.setCode(code);
					return bean;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("服务器异常，请稍候再试...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}
	
	/**
	 * 获取站内信件列表
	 */
	public XinJianDetaileBean getXinjianDetaile(String itemid,String authstr) {
		XinJianDetaileBean bean = new XinJianDetaileBean();
		String baseurl = ServiceUrl.Base;
		String url="";
			url = baseurl+ServiceUrl.mycenter_hander+ServiceUrl.sms_deteaile+itemid+
					ServiceUrl.mycenter_footer+authstr;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);

		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					JSONArray data = object.getJSONArray("data");
					JSONObject js = data.getJSONObject(0);
					Type t = new TypeToken<XinJianDetaileBean>() {
					}.getType();
					bean = gson.fromJson(js.toString(), t);
					bean.setCode(code);
					bean.setMsg(msg);
					return bean;
				} else {
					bean.setMsg(msg);
					bean.setCode(code);
					return bean;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("服务器异常，请稍候再试...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}
	
	
	
	/**
	 * 获取地址列表
	 */
	public ListAddressBean getAddressList(String authstr) {
		ListAddressBean lb = new ListAddressBean();
		ArrayList<AddressBean> list = new ArrayList<AddressBean>();
		String url =  ServiceUrl.Base+ServiceUrl.address+ServiceUrl.address_list+authstr;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);

		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					lb.setCode(code);
					lb.setMsg(msg);
					JSONArray data = object.getJSONArray("data");
					Type type_re = new TypeToken<ArrayList<AddressBean>>() {
					}.getType();
					list = gson.fromJson(data.toString(), type_re);
					lb.setList(list);
					return lb;
				} else {
					lb.setMsg(msg);
					lb.setCode(code);
					return lb;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				lb.setCode("500");
				lb.setMsg("服务器异常，请稍候再试...");
				return lb;
			}
		} else {
			lb.setCode("500");
			lb.setMsg(context.getResources().getString(R.string.net_is_eor));
			return lb;
		}

	}
	
	
	
	
	
	
	
	
	/**
	 * 获取订单列表
	 */
	public ListOrderBean getOrderList(String authstr) {
		ListOrderBean lb = new ListOrderBean();
		ArrayList<OrderBean> list_order = new ArrayList<OrderBean>();
		ArrayList<OrderFields> list_fields = new ArrayList<OrderFields>();
		String url =  ServiceUrl.Base+ServiceUrl.order
				+ServiceUrl.mycenter_footer+authstr
				;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);

		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					lb.setCode(code);
					lb.setMsg(msg);
					JSONArray data = object.getJSONArray("data");
					Type type_o = new TypeToken<ArrayList<OrderBean>>() {
					}.getType();
					list_order = gson.fromJson(data.toString(), type_o);
					JSONObject fields = object.getJSONObject("fields");
					@SuppressWarnings("unchecked")
					Iterator<String> keys=fields.keys();
					while (keys.hasNext ())
		            {
						String k = (String) keys.next() + "";
						JSONObject js = (JSONObject) fields.get(k);
						Type t = new TypeToken<OrderFields>() {
							}.getType();
							OrderFields sb = gson.fromJson(js.toString(), t);
						if(sb!=null){
							list_fields.add(0,sb);
						}
						
		            }
					lb.setList_order(list_order);
					lb.setList_key(list_fields);
					return lb;
				} else {
					lb.setMsg(msg);
					lb.setCode(code);
					return lb;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				lb.setCode("500");
				lb.setMsg("服务器异常，请稍候再试...");
				return lb;
			}
		} else {
			lb.setCode("500");
			lb.setMsg(context.getResources().getString(R.string.net_is_eor));
			return lb;
		}

	}
	
	
	
	
	

	//
	//	/**
	//	 * 获取单品信息
	//	 */
	//	@SuppressWarnings("null")
	//	public GoodsBean GetProductDetails(int id) {
	//		GoodsBean bean = null;
	//		String url = ServiceUrl.Product_Details_Url + id;
	//		String jsonStr = null;
	//		jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					JSONObject data = object.getJSONObject("data");
	//					Type type = new TypeToken<GoodsBean>() {
	//					}.getType();
	//					String json = data.toString();
	//					bean = gson.fromJson(json, type);
	//					JSONArray array = data.getJSONArray("picurls");
	//					ArrayList<String> picurls = new ArrayList<String>();
	//					for (int i = 0; i < array.length(); i++) {
	//						picurls.add(array.getString(i));
	//					}
	//					bean.setPicurls(picurls);
	//					bean.setCode(200);
	//					bean.setMsg(object.getString("message"));
	//					return bean;
	//				} else {
	//					bean.setMsg(object.getString("message"));
	//					bean.setCode(object.getInt("code"));
	//					return bean;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			bean.setCode(500);
	//			bean.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return bean;
	//		}
	//
	//		return null;
	//
	//	}
	//
	//	/**
	//	 * 获取单品评论
	//	 */
	//	@SuppressWarnings("unused")
	//	public ListCommentsBean GetProductComments(int id, int page) {
	//		ListCommentsBean list = new ListCommentsBean();
	//		CommentsBean bean = null;
	//		String url = ServiceUrl.Product_comments_Url_head + id
	//				+ ServiceUrl.Product_comments_Url_foot + page;
	//		System.out.println("pinglunurl=================" + url);
	//		String jsonStr = null;
	//		jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					JSONArray data = object.getJSONArray("data");
	//					String json = data.toString();
	//					Type type = new TypeToken<ArrayList<CommentsBean>>() {
	//					}.getType();
	//					ArrayList<CommentsBean> l = gson.fromJson(json, type);
	//
	//					list.setList(l);
	//					list.setCode(200);
	//					list.setMsg(object.getString("message"));
	//					return list;
	//				} else {
	//					list.setMsg(object.getString("message"));
	//					list.setCode(object.getInt("code"));
	//					return list;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			list.setCode(500);
	//			list.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return list;
	//		}
	//
	//		return null;
	//
	//	}
	//
	//	/**
	//	 * 获取一级分类
	//	 */
	//	public ListGoodsBean GetFclassFrist(int id) {
	//		ListGoodsBean list = new ListGoodsBean();
	//		ArrayList<ArrayList<GoodsBean>> listb = new ArrayList<ArrayList<GoodsBean>>();
	//		String url = ServiceUrl.Product_fclass_Url_frist + id;
	//		String jsonStr = null;
	//		jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					JSONObject data = object.getJSONObject("data");
	//					JSONArray titlearray = data.getJSONArray("sub_categories");
	//					for (int i = 0; i < titlearray.length(); i++) {
	//						JSONObject j = titlearray.getJSONObject(i);
	//						String sub_id = j.getString("sub_cate_id");
	//						JSONObject soj = data.getJSONObject("category_2");
	//						JSONArray t = soj.getJSONArray(sub_id);
	//						String json = t.toString();
	//						Type type = new TypeToken<ArrayList<GoodsBean>>() {
	//						}.getType();
	//						ArrayList<GoodsBean> l = gson.fromJson(json, type);
	//						if (l != null) {
	//							listb.add(l);
	//						}
	//					}
	//					list.setList(listb);
	//					list.setCode(200);
	//					list.setMsg(object.getString("message"));
	//					return list;
	//				} else {
	//					list.setMsg(object.getString("message"));
	//					list.setCode(object.getInt("code"));
	//					return list;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			list.setCode(500);
	//			list.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return list;
	//		}
	//
	//		return null;
	//
	//	}
	//
	//	/**
	//	 * 获取二级分类
	//	 */
	//	public ListFclassTwo GetFclassTwo(int id, String asctype, int page) {
	//		ListFclassTwo list = new ListFclassTwo();
	//		String url = ServiceUrl.Product_fclass_Url_two_head + id
	//				+ ServiceUrl.Product_fclass_Url_two_center + asctype
	//				+ ServiceUrl.Product_fclass_Url_two_foot + page;
	//		String jsonStr = null;
	//		jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					JSONObject data = object.getJSONObject("data");
	//					JSONArray array = data.getJSONArray("sub_cate_content");
	//					String json = array.toString();
	//					Type type = new TypeToken<ArrayList<GoodsBean>>() {
	//					}.getType();
	//					ArrayList<GoodsBean> l = gson.fromJson(json, type);
	//					list.setList(l);
	//					list.setCode(200);
	//					list.setMsg(object.getString("message"));
	//					return list;
	//				} else {
	//					list.setMsg(object.getString("message"));
	//					list.setCode(object.getInt("code"));
	//					return list;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			list.setCode(500);
	//			list.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return list;
	//		}
	//
	//		return null;
	//
	//	}
	//
	//	/**
	//	 * 加入购物车
	//	 */
	//	public Base AddShopCart(int good_id, int number, String session_id,
	//			String userid) {
	//		Base bean = new Base();
	//		String url = ServiceUrl.Product_AddShopCart_Url_head + good_id
	//				+ ServiceUrl.Product_AddShopCart_Url_center + number
	//				+ ServiceUrl.Product_AddShopCart_Url_foot_sessid + session_id
	//				+ ServiceUrl.Product_AddShopCart_Url_foot_userid + userid;
	//		String jsonStr = null;
	//		jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					bean.setCode(200);
	//					bean.setMsg(object.getString("message"));
	//					System.out.println("result===============" + jsonStr);
	//					return bean;
	//				} else {
	//					bean.setMsg(object.getString("message"));
	//					bean.setCode(object.getInt("code"));
	//					return bean;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			bean.setCode(500);
	//			bean.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return bean;
	//		}
	//
	//		return null;
	//
	//	}
	//
	//	/**
	//	 * 购物车列表
	//	 */
	//	public ListShopCartBean getShopCartlist(String sessionid, String userid) {
	//		ListShopCartBean list = new ListShopCartBean();
	//		String url = ServiceUrl.GetShopCartList_Url_head + sessionid
	//				+ ServiceUrl.GetShopCartList_Url_foot + userid;
	//		String jsonStr = null;
	//		jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					ArrayList<ShopCartBean> l = new ArrayList<ShopCartBean>();
	//					JSONObject data = object.getJSONObject("data");
	//					JSONArray datas = data.getJSONArray("datas");
	//					for (int i = 0; i < datas.length(); i++) {
	//						JSONObject j = datas.getJSONObject(i);
	//						String json = j.toString();
	//						Type type = new TypeToken<ShopCartBean>() {
	//						}.getType();
	//						ShopCartBean b = gson.fromJson(json, type);
	//						l.add(b);
	//					}
	//					String count_price = String.valueOf(data
	//							.getInt("count_price"));
	//					list.setList(l);
	//					list.setCode(200);
	//					list.setCount_price(count_price);
	//					list.setMsg(object.getString("message"));
	//					return list;
	//				} else {
	//					list.setMsg(object.getString("message"));
	//					list.setCode(object.getInt("code"));
	//					return list;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			list.setCode(500);
	//			list.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return list;
	//		}
	//
	//		return null;
	//
	//	}
	//
	//	/**
	//	 * 从购物车删除
	//	 */
	//	public Base DeletefromShopCart(String res_id, String session_id,
	//			String userid) {
	//		Base bean = new Base();
	//		String url = ServiceUrl.Product_deletefromShopCart_Url_head + res_id
	//				+ ServiceUrl.Product_deletefromShopCart_Url_foot_sessid
	//				+ session_id
	//				+ ServiceUrl.Product_deletefromShopCart_Url_foot_userid
	//				+ userid;
	//		String jsonStr = null;
	//		jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					bean.setCode(200);
	//					bean.setMsg(object.getString("message"));
	//					return bean;
	//				} else {
	//					bean.setMsg(object.getString("message"));
	//					bean.setCode(object.getInt("code"));
	//					return bean;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			bean.setCode(500);
	//			bean.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return bean;
	//		}
	//
	//		return null;
	//
	//	}
	//
	//	/**
	//	 * 登录
	//	 * 
	//	 * @param id
	//	 * @param password
	//	 * @return
	//	 */
	//	public UserBean getLogin(String username, String password) {
	//		UserBean bean = new UserBean();
	//		String url = ServiceUrl.Login_Url_username + username
	//				+ ServiceUrl.Login_Url_password + password;
	//		String jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					JSONObject data = object.getJSONObject("data");
	//					String json = data.toString();
	//					Type type = new TypeToken<UserBean>() {
	//					}.getType();
	//					bean = gson.fromJson(json, type);
	//					bean.setCode(200);
	//					bean.setMsg(object.getString("message"));
	//					System.out.println(object.get("code") + "登陆成功");// 测试是不是200
	//					return bean;
	//				} else {
	//					bean.setMsg(object.getString("message"));
	//					bean.setCode(object.getInt("code"));
	//					return bean;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			bean.setCode(500);
	//			bean.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return bean;
	//		}
	//		return null;
	//	}
	//
	//	/**
	//	 * 注册
	//	 * 
	//	 * @param id
	//	 * @param password
	//	 * @return
	//	 */
	//	public UserBean regist(String username, String password) {
	//		UserBean bean = new UserBean();
	//		String url = ServiceUrl.Regist_Url_username + username
	//				+ ServiceUrl.Regist_Url_password + password;
	//		String jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					JSONObject data = object.getJSONObject("data");
	//					String json = data.toString();
	//					Type type = new TypeToken<UserBean>() {
	//					}.getType();
	//					bean = gson.fromJson(json, type);
	//					bean.setCode(200);
	//					bean.setMsg(object.getString("message"));
	//					System.out.println(object.get("code") + "注册成功");// 测试是不是200
	//					return bean;
	//				} else {
	//					bean.setMsg(object.getString("message"));
	//					bean.setCode(object.getInt("code"));
	//					return bean;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			bean.setCode(500);
	//			bean.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return bean;
	//		}
	//		return null;
	//	}
	//
	//	/**
	//	 * 获取收货人信息
	//	 * 
	//	 * @param id
	//	 * @param password
	//	 * @return
	//	 */
	//	public ConsigneeBean getConsigneeInfo(String userid) {
	//		ConsigneeBean bean = new ConsigneeBean();
	//		String url = ServiceUrl.get_consignee_info + userid;
	//		String jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			System.out.println("收件人信息"+jsonStr);
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					JSONObject data = object.getJSONObject("data");
	//					String json = data.toString();
	//					Type type = new TypeToken<ConsigneeBean>() {
	//					}.getType();
	//					bean = gson.fromJson(json, type);
	//					bean.setCode(200);
	//					bean.setMsg(object.getString("message"));
	//					return bean;
	//				} else {
	//					bean.setMsg(object.getString("message"));
	//					bean.setCode(object.getInt("code"));
	//					return bean;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			bean.setCode(500);
	//			bean.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return bean;
	//		}
	//		return null;
	//	}
	//
	//	/**
	//	 * save收货人信息
	//	 * 
	//	 * @param id
	//	 * @param password
	//	 * @return
	//	 */
	//	@SuppressWarnings("deprecation")
	//	public Base saveConsigneeInfo(String userid, String consignee, String tel,
	//			String address) {
	//		Base bean = new Base();
	//		String url = ServiceUrl.save_consignee_info + userid
	//				+ ServiceUrl.save_consignee_info_consignee +java.net.URLEncoder.encode(consignee) 
	//				+ ServiceUrl.save_consignee_info_tel  +java.net.URLEncoder.encode(tel) 
	//				+ ServiceUrl.save_consignee_info_address +java.net.URLEncoder.encode(address) ;
	//		String jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					bean.setCode(200);
	//					bean.setMsg(object.getString("message"));
	//					return bean;
	//				} else {
	//					bean.setMsg(object.getString("message"));
	//					bean.setCode(object.getInt("code"));
	//					return bean;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			bean.setCode(500);
	//			bean.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return bean;
	//		}
	//		return null;
	//	}
	//
	//	/**
	//	 * 提交订单
	//	 * 
	//	 * @param id
	//	 * @param password
	//	 * @return
	//	 */
	//	public CommitOrderBean CommitOrder(String userid, String time, String type, String fee) {
	//		CommitOrderBean bean = new CommitOrderBean();
	//		String url = ServiceUrl.save_order_info + userid
	//				+ ServiceUrl.save_order_info_time + time
	//				+ ServiceUrl.save_order_info_type + type
	//				+ ServiceUrl.save_order_info_time_fee + fee;
	//		String jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					JSONObject data = object.getJSONObject("data");
	//					bean.setOrder_sn(data.getString("order_sn"));
	//					
	//					
	//					bean.setCode(200);
	//					bean.setMsg(object.getString("message"));
	//					return bean;
	//				} else {
	//					bean.setMsg(object.getString("message"));
	//					bean.setCode(object.getInt("code"));
	//					return bean;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			bean.setCode(500);
	//			bean.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return bean;
	//		}
	//		return null;
	//	}
	//
	//	/**
	//	 * 获取全部订单
	//	 * 
	//	 * @param id
	//	 * @param password
	//	 * @return
	//	 */
	//	public ListOrderBean getAllOrderlist(String userid, int page) {
	//		ListOrderBean bean = new ListOrderBean();
	//		String url = ServiceUrl.get_order_list_userid + userid
	//				+ ServiceUrl.get_order_list_page + page
	//				+ ServiceUrl.get_order_list_action;
	//		String jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					JSONArray data = object.getJSONArray("data");
	//					String json = data.toString();
	//					Type t = new TypeToken<ArrayList<OrderBean>>() {
	//					}.getType();
	//					ArrayList<OrderBean> b = gson.fromJson(json, t);
	//					bean.setList(b);
	//					bean.setCode(200);
	//					bean.setMsg(object.getString("message"));
	//					return bean;
	//				} else {
	//					bean.setMsg(object.getString("message"));
	//					bean.setCode(object.getInt("code"));
	//					return bean;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			bean.setCode(500);
	//			bean.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return bean;
	//		}
	//		return null;
	//	}
	//
	//	/**
	//	 * 获取订单详情
	//	 * 
	//	 * @param id
	//	 * @param password
	//	 * @return
	//	 */
	//	public OrderDetailsBean getOrderDetalis(String orderid, String userid) {
	//		OrderDetailsBean bean = new OrderDetailsBean();
	//		String url = ServiceUrl.get_order_details_orderid + orderid
	//				+ ServiceUrl.get_order_details_userid + userid;
	//		String jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					JSONObject data = object.getJSONObject("data");
	//					String json = data.toString();
	//					Type t = new TypeToken<OrderDetailsBean>() {
	//					}.getType();
	//					bean = gson.fromJson(json, t);
	//					JSONArray j = data.getJSONArray("goods");
	//					Type tg = new TypeToken<ArrayList<Goods>>() {
	//					}.getType();
	//					ArrayList<Goods> gs = gson.fromJson(j.toString(), tg);
	//					bean.setGoods(gs);
	//					bean.setCode(200);
	//					bean.setMsg(object.getString("message"));
	//					return bean;
	//				} else {
	//					bean.setMsg(object.getString("message"));
	//					bean.setCode(object.getInt("code"));
	//					return bean;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			bean.setCode(500);
	//			bean.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return bean;
	//		}
	//		return null;
	//	}
	//	
	//	
	//	/**
	//	 * 同步购物车信息
	//	 * 
	//	 * @param id
	//	 * @param password
	//	 * @return
	//	 */
	//	public Base updataShopcartInfo(String sessionid, String userid) {
	//		Base bean = new Base();
	//		String url = ServiceUrl.updata_shopcart_session_id + sessionid
	//				+ ServiceUrl.updata_shopcart_user_id + userid;
	//		String jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					bean.setCode(200);
	//					bean.setMsg(object.getString("message"));
	//					return bean;
	//				} else {
	//					bean.setMsg(object.getString("message"));
	//					bean.setCode(object.getInt("code"));
	//					return bean;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			bean.setCode(500);
	//			bean.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return bean;
	//		}
	//		return null;
	//	}
	//	
	//	
	//	/**
	//	 * 修改订单状态
	//	 * 
	//	 * @param id
	//	 * @param password
	//	 * @return
	//	 */
	//	public Base UpdataOrderStatu(String order_sn) {
	//		Base bean = new Base();
	//		String url = ServiceUrl.updata_order_statu_order + order_sn;
	//				
	//		String jsonStr = GetHttp.sendGet(url);
	//
	//		if (jsonStr != null && !jsonStr.equals("")) {
	//			JSONObject object = null;
	//			try {
	//				object = new JSONObject(jsonStr);
	//				if (object.get("code") != null && object.getInt("code") == 200) {
	//					bean.setCode(200);
	//					bean.setMsg(object.getString("message"));
	//					return bean;
	//				} else {
	//					bean.setMsg(object.getString("message"));
	//					bean.setCode(object.getInt("code"));
	//					return bean;
	//
	//				}
	//
	//			} catch (JSONException e) {
	//				e.printStackTrace();
	//			}
	//		} else {
	//			bean.setCode(500);
	//			bean.setMsg(context.getResources().getString(
	//					R.string.http_status_code_error));
	//			return bean;
	//		}
	//		return null;
	//	}

}
