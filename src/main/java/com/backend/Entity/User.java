package com.backend.Entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String prefix;
	private String firstname;
	private String lastname;
	@Column(unique = true)
	private String email;
	private Boolean isActive;
	private boolean enableServiceStaffPin;
	private String staffPin;
	private String username;
	private String password;
	private Boolean allowLogin;
	private String location;

	private BigDecimal salesCommissionPercentage;
	private BigDecimal commisionPercent;
	private BigDecimal allowContacts;
	@ElementCollection
	private List<Long> selectedContacts;

	private String language;
	private Date dateOfBirth;
	private String gender;
	private String maritalStatus;
	private String bloodGroup;
	private Long mobileNumber;
	private Long alternateContactNumber;
	private Long familyContactNumber;
	private String facebookLink;
	private String twitterLink;
	private String socialMedia1;
	private String socialMedia2;
	private String customField1;
	private String customField2;
	private String customField3;
	private String customField4;
	private String guardianName;
	private String idProofName;
	private String idProofNumber;
	private String permanentAddress;
	private String currentAddress;

	private Long departmentId;
	private Long designationId;
	private String primaryWorkLocation;
	private BigDecimal basicSalary;
	private String salaryIn;
	private Long payComponentId;

	private String accountHolderName;
	private Long accountNumber;
	private String bankName;
	private String ifsc;     
	private String branch;
	private String taxPayerId;      

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Long getAlternateContactNumber() {
		return alternateContactNumber;
	}

	public void setAlternateContactNumber(Long alternateContactNumber) {
		this.alternateContactNumber = alternateContactNumber;
	}

	public Long getFamilyContactNumber() {
		return familyContactNumber;
	}

	public void setFamilyContactNumber(Long familyContactNumber) {
		this.familyContactNumber = familyContactNumber;
	}

	public String getFacebookLink() {
		return facebookLink;
	}

	public void setFacebookLink(String facebookLink) {
		this.facebookLink = facebookLink;
	}

	public String getTwitterLink() {
		return twitterLink;
	}

	public void setTwitterLink(String twitterLink) {
		this.twitterLink = twitterLink;
	}

	public String getSocialMedia1() {
		return socialMedia1;
	}

	public void setSocialMedia1(String socialMedia1) {
		this.socialMedia1 = socialMedia1;
	}

	public String getSocialMedia2() {
		return socialMedia2;
	}

	public void setSocialMedia2(String socialMedia2) {
		this.socialMedia2 = socialMedia2;
	}

	public String getCustomField1() {
		return customField1;
	}

	public void setCustomField1(String customField1) {
		this.customField1 = customField1;
	}

	public String getCustomField2() {
		return customField2;
	}

	public void setCustomField2(String customField2) {
		this.customField2 = customField2;
	}

	public String getCustomField3() {
		return customField3;
	}

	public void setCustomField3(String customField3) {
		this.customField3 = customField3;
	}

	public String getCustomField4() {
		return customField4;
	}

	public void setCustomField4(String customField4) {
		this.customField4 = customField4;
	}

	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	public String getIdProofName() {
		return idProofName;
	}

	public void setIdProofName(String idProofName) {
		this.idProofName = idProofName;
	}

	public String getIdProofNumber() {
		return idProofNumber;
	}

	public void setIdProofNumber(String idProofNumber) {
		this.idProofNumber = idProofNumber;
	}

	public String getPermanentAddress() {
		return permanentAddress;
	}

	public void setPermanentAddress(String permanentAddress) {
		this.permanentAddress = permanentAddress;
	}

	public String getCurrentAddress() {
		return currentAddress;
	}

	public void setCurrentAddress(String currentAddress) {
		this.currentAddress = currentAddress;
	}

	public boolean isEnableServiceStaffPin() {
		return enableServiceStaffPin;
	}

	public void setEnableServiceStaffPin(boolean enableServiceStaffPin) {
		this.enableServiceStaffPin = enableServiceStaffPin;
	}

	public String getStaffPin() {
		return staffPin;
	}

	public void setStaffPin(String staffPin) {
		this.staffPin = staffPin;
	}

	public Boolean getAllowLogin() {
		return allowLogin;
	}

	public void setAllowLogin(Boolean allowLogin) {
		this.allowLogin = allowLogin;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public BigDecimal getSalesCommissionPercentage() {
		return salesCommissionPercentage;
	}

	public void setSalesCommissionPercentage(BigDecimal salesCommissionPercentage) {
		this.salesCommissionPercentage = salesCommissionPercentage;
	}

	public BigDecimal getCommisionPercent() {
		return commisionPercent;
	}

	public void setCommisionPercent(BigDecimal commisionPercent) {
		this.commisionPercent = commisionPercent;
	}

	public BigDecimal getAllowContacts() {
		return allowContacts;
	}

	public void setAllowContacts(BigDecimal allowContacts) {
		this.allowContacts = allowContacts;
	}

	public List<Long> getSelectedContacts() {
		return selectedContacts;
	}

	public void setSelectedContacts(List<Long> selectedContacts) {
		this.selectedContacts = selectedContacts;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getDesignationId() {
		return designationId;
	}

	public void setDesignationId(Long designationId) {
		this.designationId = designationId;
	}

	public String getPrimaryWorkLocation() {
		return primaryWorkLocation;
	}

	public void setPrimaryWorkLocation(String primaryWorkLocation) {
		this.primaryWorkLocation = primaryWorkLocation;
	}

	public BigDecimal getBasicSalary() {
		return basicSalary;
	}

	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}

	public String getSalaryIn() {
		return salaryIn;
	}

	public void setSalaryIn(String salaryIn) {
		this.salaryIn = salaryIn;
	}

	public Long getPayComponentId() {
		return payComponentId;
	}

	public void setPayComponentId(Long payComponentId) {
		this.payComponentId = payComponentId;
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public void setAccountHolderName(String accountHolderName) {
		this.accountHolderName = accountHolderName;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getTaxPayerId() {
		return taxPayerId;
	}

	public void setTaxPayerId(String taxPayerId) {
		this.taxPayerId = taxPayerId;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

}
