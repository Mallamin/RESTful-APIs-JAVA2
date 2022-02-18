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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Client {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private long userID;
	@Column
	private String name;
	@Column
	private String surname;
	@Column
	private String cf;
	@Column
	private long phone;
	@Column
	private String email;
	@Column
	private String city;
	@Column
	private String birthplace;
	@Column
	private String sex;
	@Column
	private String password;

	// Association OneToOne Client and Funds
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "Funds")
	private Funds funds;

	// Association OneToOne Client and transfers
		@OneToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "transfers")
		private Funds transfers;

	// OneToMany Association(Client & operations)
	@OneToMany(mappedBy = "client", cascade = javax.persistence.CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Operations> operation = new ArrayList<Operations>();

	public List<Operations> getoperation() {
		return operation;
	}

	public void setoperation(List<Operations> operation) {
		this.operation = operation;
	}

	public Funds getFunds() {
		return funds;
	}

	public void setFunds(Funds funds) {
		this.funds = funds;
	}

	public Client() {
		super();
	}

	public long getUserID() {
		return userID;
	}

	public void setUserID(long userID) {
		this.userID = userID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getBirthplace() {
		return birthplace;
	}

	public void setBirthplace(String birthplace) {
		this.birthplace = birthplace;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Client [userID=" + userID + ", name=" + name + ", surname=" + surname + ", cf=" + cf + ", phone="
				+ phone + ", email=" + email + ", city=" + city + ", birthplace=" + birthplace + ", sex=" + sex + "]";
	}

}
