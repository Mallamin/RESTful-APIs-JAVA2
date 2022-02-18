package com.bankingapp.bank.dto;

import java.sql.Date;

public class ObjectDto {
	private int userID;
	private String password;
	private long fundsID;
	private Date date;
	public ObjectDto(int userID, Date date) {
		super();
		this.userID = userID;
		this.date = date;
	}
	public ObjectDto() {
		// TODO Auto-generated constructor stub
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public long getFundsID() {
		return fundsID;
	}
	public void setFundsID(long fundsID) {
		this.fundsID = fundsID;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(int userID) {
		this.userID = userID;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	

}
