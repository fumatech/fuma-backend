package com.backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class BusinessDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private String address;
	private String email;
	private Long phoneNumber;
	private String website;
	private String logo;
	private String taxOrGstNumber;
	private String shopActNumber;
	private String cinNumber;
	private String panNumber;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}    
	public Long getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getTaxOrGstNumber() {
		return taxOrGstNumber;
	}
	public void setTaxOrGstNumber(String taxOrGstNumber) {
		this.taxOrGstNumber = taxOrGstNumber;
	}
	public String getShopActNumber() {
		return shopActNumber;
	}
	public void setShopActNumber(String shopActNumber) {
		this.shopActNumber = shopActNumber;
	}
	public String getCinNumber() {
		return cinNumber;
	}
	public void setCinNumber(String cinNumber) {
		this.cinNumber = cinNumber;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	
	
}
