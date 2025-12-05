package com.backend.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Tax {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String taxName;
	private Double taxValue;

	@ManyToMany
	@JoinTable(name = "group_tax_mapping", joinColumns = @JoinColumn(name = "group_tax_id"), inverseJoinColumns = @JoinColumn(name = "single_tax_id"))
	private List<Tax> includedTaxes;

	public Double calculateTotalTax() {
		if (includedTaxes == null || includedTaxes.isEmpty()) {
			return taxValue != null ? taxValue : 0.0;
		}
		return includedTaxes.stream().mapToDouble(tax -> tax.getTaxValue() != null ? tax.getTaxValue() : 0.0).sum();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaxName() {
		return taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public Double getTaxValue() {
		return calculateTotalTax();
	}

	public void setTaxValue(Double taxValue) {
		this.taxValue = taxValue;
	}

	public List<Tax> getIncludedTaxes() {
		return includedTaxes;
	}

	public void setIncludedTaxes(List<Tax> includedTaxes) {
		this.includedTaxes = includedTaxes;
	}
}
