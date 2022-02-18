package com.bankingapp.bank.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
@Entity
public class Transfers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column
	private long transferID;
	@Column
	private int beneficiaryID;
	@Column
	private int iban;
	@Column
	private int currency;
	@Column
	private Date dateOfTransfer;

	// Association(OneToOne transfers and client)
	@OneToOne(mappedBy = "transfers")
	private Client client;
	
	
	
	public int getCurrency() {
		return currency;
	}

	public void setCurrency(int currency) {
		this.currency = currency;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Transfers() {
		super();
	}

	public Transfers(long transferID, int beneficiaryID, int iban, int currency, Date dateOfTransfer) {
		super();
	this.transferID = transferID;
		this.beneficiaryID = beneficiaryID;
		this.iban = iban;
		this.currency = currency;
		this.dateOfTransfer = dateOfTransfer;
	}

	public long getTransferID() {
		return transferID;
	}
	public void setTransferID(long transferID) {
		this.transferID = transferID;
	}
	public int getbeneficiaryID() {
		return beneficiaryID;
	 }
	public void setbeneficiaryID(int beneficiaryID) {
		this.beneficiaryID = beneficiaryID;
	}
	public int getIban() {
		return iban;
	}
	public void setIban(int iban) {
		this.iban = iban;
	}
	public int getcurrency() {
		return currency;
	}

	public void setcurrency(int currency) {
		this.currency = currency;
	}

	public Date getDateOfTransfer() {
		return dateOfTransfer;
	}

	public void setDateOfTransfer(Date dateOfTransfer) {
		this.dateOfTransfer = dateOfTransfer;
	}

}
