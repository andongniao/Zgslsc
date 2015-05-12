package com.example.educonsult.beans;

public class QuerenOrderBean extends BaseBean{
	private ListShopBean list;
	private int total_amount;
	private int total;
	private AddressBean address;
	public ListShopBean getList() {
		return list;
	}
	public void setList(ListShopBean list) {
		this.list = list;
	}
	public int getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(int total_amount) {
		this.total_amount = total_amount;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public AddressBean getAddress() {
		return address;
	}
	public void setAddress(AddressBean address) {
		this.address = address;
	}

}
