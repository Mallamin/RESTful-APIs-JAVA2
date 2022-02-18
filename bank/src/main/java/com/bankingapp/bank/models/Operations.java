package com.bankingapp.bank.models;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Operations {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int operationID ;
	private int debit;
	private int credit;
	private int transfers;
	private Date date;
	public Operations(int debit, int credit, Date date) {
		super();
		this.debit = debit;
		this.credit = credit;
		this.date = date;
	}
	public Operations(Date date) {
		super();
		this.date = date;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getTransfers() {
		return transfers;
	}
	public void setTransfers(int transfers) {
		this.transfers = transfers;
	}
	@Override
	public String toString() {
		return "Operations [debit=" + debit + ", credit=" + credit + "]";
	}
	public Operations(int debit, int credit) {
		super();
		this.debit = debit;
		this.credit = credit;
	}
		//Association ManyToOne operations and client
		@ManyToOne
		@JoinColumn(name="userid",insertable=false, updatable=false)
		private Client client;
		
		//ADDED
		//Association ManyToOne operations and fund
				@ManyToOne
				@JoinColumn(name="fundsid",insertable=false, updatable=false)
				private Funds funds;
		
		
	public Operations() {
		super();
	}
	public Operations(int operationID, int debit, int credit, boolean action) {
		super();
		this.operationID = operationID;
		this.debit = debit;
		this.credit = credit;
		this.action = action;
	}
	public int getOperationID() {
		return operationID;
	}
	public void setOperationID(int operationID) {
		this.operationID = operationID;
	}
	public int getDebit() {
		return debit;
	}
	public void setDebit(int debit) {
		this.debit = debit;
	}
	public int getCredit() {
		return credit;
	}
	public void setCredit(int credit) {
		this.credit = credit;
	}
	public boolean isAction() {
		return action;
	}
	public void setAction(boolean action) {
		this.action = action;
	}
	boolean action;

}
