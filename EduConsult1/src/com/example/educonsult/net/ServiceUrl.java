package com.example.educonsult.net;
/**
 * 
 * @author Qzr
 *
 */
public class ServiceUrl {
	public static String Base="http://www.shop.com/app/";//"http://192.168.1.190/app/";
	//��ҳ
	public static String Home_url=Base;
	//����
	public static String City = "area.php";
	//����
	public static String Fenlei = "category.php";
	//��¼
	public static String Login_url_name = "login.php?username=";
	public static String Login_url_password = "&password=";

	//��Ʒ����
	public static String Product_url = "mall.php?action=show&itemid=";

	//��Ʒ����
	public static String Product_comment_head = "mall.php?action=comment&itemid=";
	public static String Product_comment_foot = "&page=";
	public static String Product_comment_star = "&star=";

	//����������ҳ 
	public static String Mycenter_home= "member.php?action=";
	public static String Mycenter_home_member= "member_detail&authstr=";
	public static String Mycenter_home_company= "company_detail&authstr=";
	//Ǯ�� ��ѯ��ϸ
	public static String money_hand = "member.php?action=money_";
	public static String money_home ="bag";  
	public static String money_detaile = "bag_detail";
	public static String money_income= "income";
	public static String money_pay= "pay";
	public static String money_foot = "&authstr=";
	
	

	//��ѡ�Ƽ����������ģ�
	public static String Mycenter_recommend = "recommendproduct.php";
}
