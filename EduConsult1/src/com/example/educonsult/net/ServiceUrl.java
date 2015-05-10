package com.example.educonsult.net;
/**
 * 
 * @author Qzr
 *
 */
public class ServiceUrl {
	public static String Base="http://www.shop.com/app/";//"http://192.168.1.190/app/";
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
	public static String Mycenter_home= "member.php?action=";
	public static String Mycenter_home_member= "member_detail&authstr=";
	public static String Mycenter_home_company= "company_detail&authstr=";
	//钱包 查询明细
	public static String money_hand = "member.php?action=money_";
	public static String money_home ="bag";  
	public static String money_detaile = "bag_detail";
	public static String money_income= "income";
	public static String money_pay= "pay";
	public static String money_foot = "&authstr=";
	
	

	//精选推荐（个人中心）
	public static String Mycenter_recommend = "recommendproduct.php";
}
