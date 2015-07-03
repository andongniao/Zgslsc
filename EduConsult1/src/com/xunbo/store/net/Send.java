package com.xunbo.store.net;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xunbo.store.MyApplication;
import com.xunbo.store.R;
import com.xunbo.store.beans.AddressBean;
import com.xunbo.store.beans.AreaBean;
import com.xunbo.store.beans.BaseBean;
import com.xunbo.store.beans.CenterUserBean;
import com.xunbo.store.beans.CommentBean;
import com.xunbo.store.beans.CommentStar;
import com.xunbo.store.beans.CompanyBean;
import com.xunbo.store.beans.FenleiBean;
import com.xunbo.store.beans.HomeBean;
import com.xunbo.store.beans.HomeCatBean;
import com.xunbo.store.beans.HomeInfoBean;
import com.xunbo.store.beans.ListAddressBean;
import com.xunbo.store.beans.ListAreaBean;
import com.xunbo.store.beans.ListComment;
import com.xunbo.store.beans.ListCompanyBean;
import com.xunbo.store.beans.ListFenleiBean;
import com.xunbo.store.beans.ListMoneyBean;
import com.xunbo.store.beans.ListMoneytxBean;
import com.xunbo.store.beans.ListOrderBean;
import com.xunbo.store.beans.ListProductBean;
import com.xunbo.store.beans.ListShopBean;
import com.xunbo.store.beans.ListXinjianBean;
import com.xunbo.store.beans.MallInfoBean;
import com.xunbo.store.beans.MoneyBagBean;
import com.xunbo.store.beans.MoneyDetaileBean;
import com.xunbo.store.beans.MoneyTxBean;
import com.xunbo.store.beans.OrderBean;
import com.xunbo.store.beans.OrderFields;
import com.xunbo.store.beans.ProdectDetaileBean;
import com.xunbo.store.beans.ProductBean;
import com.xunbo.store.beans.ShopBean;
import com.xunbo.store.beans.UserBean;
import com.xunbo.store.beans.XinJianBean;
import com.xunbo.store.beans.XinJianDetaileBean;
import com.xunbo.store.tools.Util;


public class Send {
	private Context context;
	private Gson gson;
	//
	public Send(Context context) {
		this.context = context;
		gson = new Gson();

	}

	/**
	 * ��ȡ��ҳ��Ϣ
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
					home.setRecommend(list_recommend);
					home.setHot(list_hot);
					home.setCompany(list_com);
					home.setAd(list_ad);
					return home;
				} else {
					home.setMsg(msg);
					home.setCode(code);
					return home;

				}

			} catch (JSONException e) {
				e.printStackTrace();
				home.setCode("500");
				home.setMsg("�������쳣�����Ժ�����...");
				return home;
			}
		} else {
			home.setCode("500");
			home.setMsg(context.getResources().getString(R.string.net_is_eor));
			return home;
		}

	}

	/**
	 * ��ȡ��ҳ��Ϣ
	 */
	public HomeInfoBean getHomeInfo() {
		HomeInfoBean home = new HomeInfoBean();
		ArrayList<ProductBean> recommend = new ArrayList<ProductBean>();
		ArrayList<HomeCatBean> cat = new ArrayList<HomeCatBean>();
		ArrayList<String> ad = new ArrayList<String>();
		String url = ServiceUrl.Home;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);

		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				home.setCode(code);
				home.setMsg(msg);
				if (code != null && "200".equals(code)) {
					JSONObject data = object.getJSONObject("data");
					JSONArray rem = data.getJSONArray("recommend");
					Type type_re = new TypeToken<ArrayList<ProductBean>>() {
					}.getType();
					recommend = gson.fromJson(rem.toString(), type_re);
					JSONArray catinfo = data.getJSONArray("cat");
					Type type_hot = new TypeToken<ArrayList<HomeCatBean>>() {
					}.getType();
					cat = gson.fromJson(catinfo.toString(), type_hot);
					JSONArray adinfo = data.getJSONArray("ad");
					Type type_ad = new TypeToken<ArrayList<String>>() {
					}.getType();
					ad = gson.fromJson(adinfo.toString(), type_ad);
				} 
				home.setRecommend(recommend);
				home.setCat(cat);
				home.setAd(ad);
				return home;

			} catch (JSONException e) {
				e.printStackTrace();
				home.setCode("500");
				home.setMsg("�������쳣�����Ժ�����...");
				return home;
			}
		} else {
			home.setCode("500");
			home.setMsg(context.getResources().getString(R.string.net_is_eor));
			return home;
		}

	}
	/**
	 *  ��¼
	 * @param username 	�û���
	 * @param password	����
	 * @return
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
					int type = data.getInt("type");
					bean.setType(type);
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
				bean.setMsg("�������쳣�����Ժ�����...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}


	/**
	 * ��ȡ����
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
					for(int i=0;i<data.length();i++){
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
				list.setMsg("�������쳣�����Ժ�����...");
				return list;
			}
		} else {
			list.setCode("500");
			list.setMsg(context.getResources().getString(R.string.net_is_eor));
			return list;
		}

	}

	/**
	 * ��ȡ����
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
					for(int i=0;i<data.length();i++){
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
				list.setMsg("�������쳣�����Ժ�����...");
				return list;
			}
		} else {
			list.setCode("500");
			list.setMsg(context.getResources().getString(R.string.net_is_eor));
			return list;
		}

	}

	/**
	 * ��ȡ��Ʒ����
	 * @param id	��Ʒid
	 * @return
	 */
	public ProdectDetaileBean GetProductDetaile(String id) {
		ProdectDetaileBean bean = new ProdectDetaileBean();
		String url = ServiceUrl.Base+ServiceUrl.Product_url+id;
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
				bean.setMsg("�������쳣�����Ժ�����...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}


	/**
	 * ��ȡ��Ʒȫ������
	 * @param id	��Ʒid
	 * @param page	ҳ��
	 * @param s		����������������
	 * @return
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
				bean.setMsg("�������쳣�����Ժ�����...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}




	/**
	 * ���������Ƽ���Ʒ�б�
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
				lb.setMsg("�������쳣�����Ժ�����...");
				return lb;
			}
		} else {
			lb.setCode("500");
			lb.setMsg(context.getResources().getString(R.string.net_is_eor));
			return lb;
		}

	}


	/**
	 * ��ȡ�ҵ���Ϣ
	 * @param type		��Ա����
	 * @param authstr	Ψһ��ʾ
	 * @return
	 */
	public CenterUserBean getMyinfo(int type,String authstr) {
		CenterUserBean bean = new CenterUserBean();
		ArrayList<CenterUserBean>list = new ArrayList<CenterUserBean>();
		String baseurl = ServiceUrl.Base;
		String url="";
		url = baseurl+"member.php?action=member_detail&authstr="+authstr;
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
					//					JSONArray j = data.getJSONArray(0);
					Type t = new TypeToken<ArrayList<CenterUserBean>>() {
					}.getType();
					list = gson.fromJson(data.toString(), t);
					bean = list.get(0);
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
				bean.setMsg("�������쳣�����Ժ�����...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}

	/**
	 * ��ȡǮ������
	 * @param type		ȫ�������룬֧��
	 * @param authstr	Ψһ��ʾ
	 * @return
	 */
	public MoneyBagBean getMoney(int type,String authstr) {
		MoneyBagBean bean = new MoneyBagBean();
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
					JSONArray data = object.getJSONArray("data");
					JSONObject js = data.getJSONObject(0);
					Type t = new TypeToken<MoneyBagBean>() {
					}.getType();
					bean = gson.fromJson(js.toString(), t);
					bean.setMsg(msg);
					bean.setCode(code);
					return bean;
				} else {
					bean.setMsg(msg);
					bean.setCode(code);
					return bean;
				}
			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("�������쳣�����Ժ�����...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}


	/**
	 * ��ȡǮ����ϸ
	 * @param type		ȫ�������룬֧��
	 * @param authstr	Ψһ��ʾ
	 * @return
	 */
	public ListMoneyBean getMoneyInfo(int type,String authstr) {
		ListMoneyBean bean = new ListMoneyBean();
		ArrayList<MoneyDetaileBean> list = new ArrayList<MoneyDetaileBean>();
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
					if(Util.IsNull(object.getString("data")) && !"[]".equals(object.getString("data"))){
						JSONArray data = object.getJSONArray("data");
						//					JSONObject js = data.getJSONObject(0);
						Type t = new TypeToken<ArrayList<MoneyDetaileBean>>() {
						}.getType();
						list = gson.fromJson(data.toString(), t);
					}
				}
				bean.setList(list);
				bean.setMsg(msg);
				bean.setCode(code);
				return bean;
			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("�������쳣�����Ժ�����...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}

	/**
	 * ��ȡ������֧����
	 * @param authstr	Ψһ��ʾ
	 * @param Itemid	�ʽ���ˮ��id
	 * @return
	 */
	public ListMoneyBean getMoneyItemInfo(String Itemid,String authstr) {
		ListMoneyBean bean = new ListMoneyBean();
		ArrayList<MoneyDetaileBean> list = new ArrayList<MoneyDetaileBean>();
		String baseurl = ServiceUrl.Base;
		String url=baseurl+ServiceUrl.money_hand;
		url=url+"detail"+ServiceUrl.mycenter_footer+authstr;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);

		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					if(Util.IsNull(object.getString("data")) && !"[]".equals(object.getString("data"))){
						JSONArray data = object.getJSONArray("data");
						//					JSONObject js = data.getJSONObject(0);
						Type t = new TypeToken<ArrayList<MoneyDetaileBean>>() {
						}.getType();
						list = gson.fromJson(data.toString(), t);
					}
				}
				bean.setList(list);
				bean.setMsg(msg);
				bean.setCode(code);
				return bean;
			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("�������쳣�����Ժ�����...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}
	/**
	 * ��ȡ���������п��б�
	 * @param authstr	Ψһ��ʾ
	 * @return
	 */
	public ListMoneytxBean getTxBankList(String authstr) {
		ListMoneytxBean bean = new ListMoneytxBean();
		ArrayList<MoneyTxBean> list = new ArrayList<MoneyTxBean>();
		String baseurl = ServiceUrl.Base;
		//TODO
		String url=baseurl+ServiceUrl.money_hand;
		url=url+"detail"+ServiceUrl.mycenter_footer+authstr;
		String jsonStr = null;
		jsonStr = GetHttp.sendGet(url);

		if (jsonStr != null && !jsonStr.equals("")) {
			JSONObject object = null;
			try {
				object = new JSONObject(jsonStr);
				String code = object.getString("code");
				String msg = object.getString("message");
				if (code != null && "200".equals(code)) {
					if(Util.IsNull(object.getString("data")) && !"[]".equals(object.getString("data"))){
						JSONArray data = object.getJSONArray("data");
						Type t = new TypeToken<ArrayList<MoneyTxBean>>() {
						}.getType();
						list = gson.fromJson(data.toString(), t);
					}
				}
				bean.setList(list);
				bean.setMsg(msg);
				bean.setCode(code);
				return bean;
			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("�������쳣�����Ժ�����...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}
	
	
	/**
	 * ��������
	 * @param authstr	Ψһ��ʾ
	 * @return
	 */
	public BaseBean getTx(String money,String authstr) {
		ListMoneytxBean bean = new ListMoneytxBean();
		ArrayList<MoneyTxBean> list = new ArrayList<MoneyTxBean>();
		String baseurl = ServiceUrl.Base+"member.php?action=sure";
		//TODO
		String url=baseurl+"";
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
					if(Util.IsNull(object.getString("data")) && !"[]".equals(object.getString("data"))){
						JSONArray data = object.getJSONArray("data");
						Type t = new TypeToken<ArrayList<MoneyTxBean>>() {
						}.getType();
						list = gson.fromJson(data.toString(), t);
					}
				}
				bean.setList(list);
				bean.setMsg(msg);
				bean.setCode(code);
				return bean;
			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("�������쳣�����Ժ�����...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}



	/**
	 * ��ȡ���ﳵ�б�
	 * @param authstr	Ψһ��ʾ
	 * @return
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
				bean.setMsg("�������쳣�����Ժ�����...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}

	/**
	 * ��ӹ��ﳵ
	 * @param itemid
	 * @param number
	 * @param authstr
	 * @return
	 */
	public BaseBean CartAdd(String itemid,int number,String authstr) {
		BaseBean bean = new BaseBean();
		String url = ServiceUrl.Base+ServiceUrl.cart_add+itemid+"&num="+number+
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
				bean.setMsg("�������쳣�����Ժ�����...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}

	/**
	 *	ɾ�����ﳵ
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
				bean.setMsg("�������쳣�����Ժ�����...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}

	/**
	 *	��չ��ﳵ
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
				bean.setMsg("�������쳣�����Ժ�����...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}




	/**
	 * ��ȡվ���ż��б�
	 */
	public ListXinjianBean getXinjianlist(String authstr) {
		ListXinjianBean bean = new ListXinjianBean();
		ArrayList<XinJianBean> list = new ArrayList<XinJianBean>();
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
					if(Util.IsNull(object.getString("data"))){
						JSONArray data = object.getJSONArray("data");
						Type t = new TypeToken<ArrayList<XinJianBean>>() {
						}.getType();
						list = gson.fromJson(data.toString(), t);
					}
				}
				bean.setList(list);
				bean.setMsg(msg);
				bean.setCode(code);
				return bean;

			} catch (JSONException e) {
				e.printStackTrace();
				bean.setCode("500");
				bean.setMsg("�������쳣�����Ժ�����...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}

	/**
	 * ��ȡվ���ż��б�
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
				bean.setMsg("�������쳣�����Ժ�����...");
				return bean;
			}
		} else {
			bean.setCode("500");
			bean.setMsg(context.getResources().getString(R.string.net_is_eor));
			return bean;
		}

	}



	/**
	 * ��ȡ��ַ�б�
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
				lb.setMsg("�������쳣�����Ժ�����...");
				return lb;
			}
		} else {
			lb.setCode("500");
			lb.setMsg(context.getResources().getString(R.string.net_is_eor));
			return lb;
		}

	}








	/**
	 * ��ȡ�����б� 
	 * @param step		��������1�����2��������3���ջ���4�����ۣ�5�����˿��ѡ��Ĭ��Ϊ0
	 * 							6���˿����ң�7���˿�����ң�8��ҹرս��ף�9���ҹرս���
	 * @param page		ҳ�� 
	 * @param authstr	Ψһ��ʾrefundlist
	 * @return
	 */
	public ListOrderBean getOrderList(int step,int page,String authstr) {
		ListOrderBean lb = new ListOrderBean();
		ArrayList<OrderBean> list_order = new ArrayList<OrderBean>();
		ArrayList<OrderFields> list_fields = new ArrayList<OrderFields>();
		String url =  ServiceUrl.Base+ServiceUrl.order
				+"?step="+step+"&page="+page
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
					if(Util.IsNull(object.getString("data"))){
						JSONArray data = object.getJSONArray("data");
						//					Type type_o = new TypeToken<ArrayList<OrderBean>>() {
						//					}.getType();
						//					list_order = gson.fromJson(data.toString(), type_o);
						for(int i=0;i<data.length();i++){
							OrderBean o = new OrderBean();
							String j = data.getString(i);
							if(Util.IsNull(j)){			
								JSONObject d = new JSONObject(j);
								o.setItemid(d.getString("itemid"));
								o.setTitle(d.getString("title"));
								o.setThumb(d.getString("thumb"));
								o.setAddtime(d.getString("addtime"));
								o.setPrice(d.getString("price"));
								o.setNumber(d.getString("number"));
								o.setStatus(d.getString("status"));
								o.setStatusid(d.getString("statusid"));
								o.setCompany(d.getString("company"));
								o.setCoupons(d.getString("coupons"));
								o.setUnit(d.getString("unit"));
							}
							list_order.add(o);
						}
					}
					if(Util.IsNull(object.getString("fields"))){
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
								list_fields.add(sb);
							}

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
				lb.setMsg("�������쳣�����Ժ�����...");
				return lb;
			}
		} else {
			lb.setCode("500");
			lb.setMsg(context.getResources().getString(R.string.net_is_eor));
			return lb;
		}

	}

	/**
	 * ��ȡ�˿���б� 
	 * @param page		ҳ�� 
	 * @param authstr	Ψһ��ʾ
	 * @return
	 */
	public ListOrderBean getOrderRefundList(int page,String authstr) {
		ListOrderBean lb = new ListOrderBean();
		ArrayList<OrderBean> list_order = new ArrayList<OrderBean>();
		ArrayList<OrderFields> list_fields = new ArrayList<OrderFields>();
		String url =  ServiceUrl.Base+ServiceUrl.order
				+"?action=refundlist&page="+page
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
					lb.setMsg(msg);
					lb.setCode(code);
					JSONArray data = object.getJSONArray("data");
					Type type_o = new TypeToken<ArrayList<OrderBean>>() {
					}.getType();
					list_order = gson.fromJson(data.toString(), type_o);
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
				lb.setMsg("�������쳣�����Ժ�����...");
				return lb;
			}
		} else {
			lb.setCode("500");
			lb.setMsg(context.getResources().getString(R.string.net_is_eor));
			return lb;
		}

	}


	/**
	 * ���������Ʒ�Ƽ�
	 * @return
	 */
	public ListProductBean getGQRecommend() {
		ListProductBean lb = new ListProductBean();
		String url =  ServiceUrl.Base+"?action=recommend";
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
					//					JSONArray j = data.getJSONArray(0);
					Type type_re = new TypeToken<ArrayList<ProductBean>>() {
					}.getType();
					ArrayList<ProductBean> list_recommend = gson.fromJson(data.toString(), type_re);
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
				lb.setMsg("�������쳣�����Ժ�����...");
				return lb;
			}
		} else {
			lb.setCode("500");
			lb.setMsg(context.getResources().getString(R.string.net_is_eor));
			return lb;
		}

	}
	/**
	 *  �������Ʒ��
	 * @return
	 */
	public ListCompanyBean getGQbrand() {
		ListCompanyBean lb = new ListCompanyBean();
		ArrayList<CompanyBean> list = new ArrayList<CompanyBean>();
		String url =  ServiceUrl.Base+"?action=brand";
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
					//					JSONArray j = data.getJSONArray(0);
					Type type_re = new TypeToken<ArrayList<CompanyBean>>() {
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
				lb.setMsg("�������쳣�����Ժ�����...");
				return lb;
			}
		} else {
			lb.setCode("500");
			lb.setMsg(context.getResources().getString(R.string.net_is_eor));
			return lb;
		}

	}





}
