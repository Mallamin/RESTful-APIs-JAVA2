package com.bankingapp.bank.dto;

public class SecondDto {
	
	private int userID;
	private String date;
	public SecondDto(int userID, String date) {
		super();
		this.userID = userID;
		this.date = date;
	}
	public SecondDto() {
		// TODO Auto-generated constructor stub
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

}
