package com.example.educonsult.net;
/**
 * 
 * @author Qzr
 *
 */
public class ServiceUrl {
	//public static String Base="http://www.shop.com/app/";
	public static String Base="http://192.168.1.252/app/";
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
	public static String mycenter_hander= "member.php?action=";
	public static String mycenter_footer = "&authstr=";
	public static String Mycenter_home_member= "member_detail&authstr=";
	public static String Mycenter_home_company= "company_detail&authstr=";
	//Ǯ�� ��ѯ��ϸ
	public static String money_hand = "member.php?action=money_";
	public static String money_home ="bag";  
	public static String money_detaile = "bag_detail";
	public static String money_income= "income";
	public static String money_pay= "pay";
	//�ż���ҳ�б�
	public static String sms_list = "station_sms";
	public static String sms_deteaile = "sms_detail&itemid=";
	//�����п�
	public static String bank  = "bankcard.php?action=";
	//��ȡ�������б�
	public static String bank_list  = "bank";
	//��ȡ�����г���
	public static String bank_city  = "city";
	//��ȡ��������
	public static String bank_county  = "county";
	//��ȡ������Ӫҵ����
	public static String bank_branch  = "branch";
	//��ȡ�����г���
	public static String bank_bind  = "bind";
	
	

	//��ѡ�Ƽ����������ģ�
	public static String Mycenter_recommend = "recommendproduct.php";
	
	
	//���ﳵ�б�
	public static String cart_list = "cart.php?action=list&authstr=";
	//��ӹ���
	public static String cart_add = "cart.php?action=add&itemid=";
	//ɾ������
	public static String cart_del = "cart.php?action=del&itemid=";
	//��չ���
	public static String cart_clear = "cart.php?action=clear";
	
	
	
	
	
	//������ַ
	public static String address = "address.php";
	//��ַ�б�
	public static String address_list = "?action=list&authstr=";
	//��ӵ�ַ
	public static String address_add = "?action=add&authstr=";
	
	//��������Ʒ
	private static String fields = "fields.php?action=";
	
	private static String fields_search = "search";
	
	
	
	//�����б�
	public static String order = "order.php";
	
	
	
	
}
