package com.example.educonsult.net;
/**
 * 
 * @author Qzr
 *
 */
public class ServiceUrl {
	//public static String Base="http://www.shop.com/app/";
	public static String Base="http://192.168.1.252/app/";
	//首页
	public static String Home_url=Base;
	//城市
	public static String City = "area.php";
	//分类
	public static String Fenlei = "category.php";
	//登录
	public static String Login_url_name = "login.php?username=";
	public static String Login_url_password = "&password=";

	//产品详情
	public static String Product_url = "mall.php?action=show&itemid=";

	//产品评论
	public static String Product_comment_head = "mall.php?action=comment&itemid=";
	public static String Product_comment_foot = "&page=";
	public static String Product_comment_star = "&star=";

	//个人中心首页 
	public static String mycenter_hander= "member.php?action=";
	public static String mycenter_footer = "&authstr=";
	public static String Mycenter_home_member= "member_detail&authstr=";
	public static String Mycenter_home_company= "company_detail&authstr=";
	//钱包 查询明细
	public static String money_hand = "member.php?action=money_";
	public static String money_home ="bag";  
	public static String money_detaile = "bag_detail";
	public static String money_income= "income";
	public static String money_pay= "pay";
	//信件首页列表
	public static String sms_list = "station_sms";
	public static String sms_deteaile = "sms_detail&itemid=";
	//绑定银行卡
	public static String bank  = "bankcard.php?action=";
	//获取开户行列表
	public static String bank_list  = "bank";
	//获取开户行城市
	public static String bank_city  = "city";
	//获取开户行县
	public static String bank_county  = "county";
	//获取开户行营业网点
	public static String bank_branch  = "branch";
	//获取开户行城市
	public static String bank_bind  = "bind";
	
	

	//精选推荐（个人中心）
	public static String Mycenter_recommend = "recommendproduct.php";
	
	
	//购物车列表
	public static String cart_list = "cart.php?action=list&authstr=";
	//添加购车
	public static String cart_add = "cart.php?action=add&itemid=";
	//删除购车
	public static String cart_del = "cart.php?action=del&itemid=";
	//清空购车
	public static String cart_clear = "cart.php?action=clear";
	
	
	
	
	
	//单条地址
	public static String address = "address.php";
	//地址列表
	public static String address_list = "?action=list&authstr=";
	//添加地址
	public static String address_add = "?action=add&authstr=";
	
	//搜索、产品
	private static String fields = "fields.php?action=";
	
	private static String fields_search = "search";
	
	
	
	//订单列表
	public static String order = "order.php";
	
	
	
	
}
