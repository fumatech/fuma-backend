package com.backend.Entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Target {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private long employee;
    
	@ElementCollection
    private List<BigDecimal> totalAmountFrom;

	@ElementCollection
    private List<BigDecimal> totalAmountTo;
    
	@ElementCollection
    private List<BigDecimal> commisionPercent;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getEmployee() {
		return employee;
	}

	public void setEmployee(long employee) {
		this.employee = employee;
	}

	public List<BigDecimal> getTotalAmountFrom() {
		return totalAmountFrom;
	}

	public void setTotalAmountFrom(List<BigDecimal> totalAmountFrom) {
		this.totalAmountFrom = totalAmountFrom;
	}

	public List<BigDecimal> getTotalAmountTo() {
		return totalAmountTo;
	}

	public void setTotalAmountTo(List<BigDecimal> totalAmountTo) {
		this.totalAmountTo = totalAmountTo;
	}

	public List<BigDecimal> getCommisionPercent() {
		return commisionPercent;
	}

	public void setCommisionPercent(List<BigDecimal> commisionPercent) {
		this.commisionPercent = commisionPercent;
	}

	
    

    
    

}
