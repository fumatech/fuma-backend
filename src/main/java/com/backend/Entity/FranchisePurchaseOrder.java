package com.backend.Entity;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;

@Entity
public class FranchisePurchaseOrder {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String franchisePurchaseOrderId;

	private Long status;

	private String vendor;

	private String addedBy;

	private String orderedBy;

	private String referenceNumber;

	private Date orderDate;

	private Date deliveryDate;

	private String dispatchStatus;

	private String location;

	private String file;

	private String franchiseId;

	private Long customerId;

	private String franchiseName;

	private Long totalItems;

	private Long totalShippedItems;

	private String additionalNotes;

	@OneToMany(mappedBy = "franchisePurchaseOrder", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FranchiseOrderItems> franchiseOrderItems;

	@Transient
	private com.backend.Service.IdGenerator idGenerator;

	public void setIdGenerator(com.backend.Service.IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@PrePersist
	private void generateFranchisePurchaseOrderId() {
		if (this.franchisePurchaseOrderId == null && idGenerator != null) {
			this.franchisePurchaseOrderId = idGenerator.generateFranchisePurchaseOrderId();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFranchisePurchaseOrderId() {
		return franchisePurchaseOrderId;
	}

	public void setFranchisePurchaseOrderId(String franchisePurchaseOrderId) {
		this.franchisePurchaseOrderId = franchisePurchaseOrderId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}

	public String getOrderedBy() {
		return orderedBy;
	}

	public void setOrderedBy(String orderedBy) {
		this.orderedBy = orderedBy;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFranchiseId() {
		return franchiseId;
	}

	public void setFranchiseId(String franchiseId) {
		this.franchiseId = franchiseId;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public String getDispatchStatus() {
		return dispatchStatus;
	}

	public void setDispatchStatus(String dispatchStatus) {
		this.dispatchStatus = dispatchStatus;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getFranchiseName() {
		return franchiseName;
	}

	public void setFranchiseName(String franchiseName) {
		this.franchiseName = franchiseName;
	}

	public Long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(Long totalItems) {
		this.totalItems = totalItems;
	}

	public Long getTotalShippedItems() {
		return totalShippedItems;
	}

	public void setTotalShippedItems(Long totalShippedItems) {
		this.totalShippedItems = totalShippedItems;
	}

	public String getAdditionalNotes() {
		return additionalNotes;
	}

	public void setAdditionalNotes(String additionalNotes) {
		this.additionalNotes = additionalNotes;
	}

	public List<FranchiseOrderItems> getFranchiseOrderItems() {
		return franchiseOrderItems;
	}

	public void setFranchiseOrderItems(List<FranchiseOrderItems> franchiseOrderItems) {
		this.franchiseOrderItems = franchiseOrderItems;
	}

}
