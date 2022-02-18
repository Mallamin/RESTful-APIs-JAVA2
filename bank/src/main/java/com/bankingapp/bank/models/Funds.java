package com.bankingapp.bank.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Funds {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int fundsID;
	@Column
	private int money;
	// Association(OneToOne funds and client)
	@OneToOne(mappedBy = "funds")
	private Client client;

	public Client getClient() {
		return client;
	}
	
	
	//ADDED
	
	// OneToMany Association(funds & operations)
		@OneToMany(mappedBy = "funds", cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY)
		private List<Operations> operation = new ArrayList<Operations>();

		public List<Operations> getoperation() {
			return operation;
		}
		
		
		

	public Funds() {
		super();
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Funds(int fundsID, int money) {
		super();
		this.fundsID = fundsID;
		this.money = money;
	}

	public int getUserID() {
		return fundsID;
	}

	public void setUserID(int userID) {
		this.fundsID = userID;
	}

	public int getmoney() {
		return money;
	}

	public void setmoney(int money) {
		this.money = money;
	}

}
