package com.example.educonsult.net;
/**
 * 
 * @author Qzr
 *
 */
public class ServiceUrl {
	public static String Base="http://192.168.1.190/app/";
	//首页
	public static String Home_url=Base;
	//城市
	public static String City = "area.php";
	//分类
	public static String Fenlei = "category.php";
	//登录
	public static String Login_url_name = "login.php?username=";
	public static String Login_url_password = "&possword=";
	
	//产品详情
	public static String Product_url = "mall.php?action=show&itemid=";
	
	//产品评论
	public static String Product_comment_head = "mall.php?action=comment&itemid=";
	public static String Product_comment_foot = "&page=";
	public static String Product_comment_star = "&star=";
	
}
