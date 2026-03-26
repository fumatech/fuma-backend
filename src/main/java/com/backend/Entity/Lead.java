package com.backend.Entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "leads")
public class Lead {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String name;
	
	private String email;
	
	private String source;
	
	private String lifeStage;
	
	private Long employeeid;
	
	private Long mobileNumber;
	
	private String taxNumber;
	
	private LocalDateTime addedOn;
	
	private String customField1;
	
	private String customField2;

	private String customField3;

	// ---- New CRM fields ----
	
	private Double dealValue;
	
	private String nextAction;
	
	private LocalDateTime followUpDate;

	private String followUpNote;

	private String followUpStatus; // PENDING, COMPLETED, MISSED

	// ---- New pipeline fields ----

	private String company;

	private String phone;

	@Column(columnDefinition = "varchar(255) default 'NEW'")
	private String stage;

	@Column(columnDefinition = "varchar(255) default 'MEDIUM'")
	private String priority;

	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		if (this.stage == null || this.stage.isEmpty()) {
			this.stage = "NEW";
		}
		if (this.priority == null || this.priority.isEmpty()) {
			this.priority = "MEDIUM";
		}
		if (this.followUpStatus == null || this.followUpStatus.isEmpty()) {
			this.followUpStatus = "PENDING";
		}
		if (this.updatedAt == null) {
			this.updatedAt = LocalDateTime.now();
		}
		if (this.addedOn == null) {
			this.addedOn = LocalDateTime.now();
		}
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}

	// ---- Getters and Setters ----

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getLifeStage() {
		return lifeStage;
	}

	public void setLifeStage(String lifeStage) {
		this.lifeStage = lifeStage;
	}

	public Long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
	}

	public Long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(Long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getTaxNumber() {
		return taxNumber;
	}

	public void setTaxNumber(String taxNumber) {
		this.taxNumber = taxNumber;
	}

	public LocalDateTime getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(LocalDateTime addedOn) {
		this.addedOn = addedOn;
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

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Double getDealValue() {
		return dealValue;
	}

	public void setDealValue(Double dealValue) {
		this.dealValue = dealValue;
	}

	public String getNextAction() {
		return nextAction;
	}

	public void setNextAction(String nextAction) {
		this.nextAction = nextAction;
	}

	public LocalDateTime getFollowUpDate() {
		return followUpDate;
	}

	public void setFollowUpDate(LocalDateTime followUpDate) {
		this.followUpDate = followUpDate;
	}

	public String getFollowUpNote() {
		return followUpNote;
	}

	public void setFollowUpNote(String followUpNote) {
		this.followUpNote = followUpNote;
	}

	public String getFollowUpStatus() {
		return followUpStatus;
	}

	public void setFollowUpStatus(String followUpStatus) {
		this.followUpStatus = followUpStatus;
	}

}
