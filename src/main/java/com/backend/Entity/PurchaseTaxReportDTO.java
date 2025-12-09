package com.backend.Entity;

import java.math.BigDecimal;
import java.sql.Date;

public class PurchaseTaxReportDTO {

	private Long orderId;
	private String orderType;
	private String taxName;
	private BigDecimal taxAmount;
	private Date orderDate;
	private String vendor;

	public PurchaseTaxReportDTO(Long orderId, String orderType, String taxName, BigDecimal taxAmount, Date orderDate,
			String vendor) {

		this.orderId = orderId;
		this.orderType = orderType;
		this.taxName = taxName;
		this.taxAmount = taxAmount;
		this.orderDate = orderDate;
		this.vendor = vendor;
	}
}
