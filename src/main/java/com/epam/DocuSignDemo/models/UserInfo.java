package com.epam.DocuSignDemo.models;

import java.util.List;

public class UserInfo {
	String sub;
	String name;
	String given_name;
	String family_name;
	String email;
	String created;
	List<Account> accounts;
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGiven_name() {
		return given_name;
	}
	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}
	public String getFamily_name() {
		return family_name;
	}
	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	@Override
	public String toString() {
		return "UserInfo [sub=" + sub + ", name=" + name + ", given_name=" + given_name + ", family_name=" + family_name
				+ ", email=" + email + ", created=" + created + ", accounts=" + accounts + "]";
	}
	
	
}
