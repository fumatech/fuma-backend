package com.backend.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tax_groups")
public class TaxGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    // No need to store groupTaxRate as String, we can calculate it dynamically.
    @Transient
    private String groupTaxRate;  // Store calculated value as a transient field (not persisted)

    private String SubTaxes;

    @ManyToMany
    @JoinTable(
      name = "tax_group_tax_rate", 
      joinColumns = @JoinColumn(name = "tax_group_id"), 
      inverseJoinColumns = @JoinColumn(name = "tax_rate_id")
    )
    private List<TaxRate> taxRates = new ArrayList<>();

    @Transient
    private List<Long> taxRatesIds;

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

    public List<TaxRate> getTaxRates() {
        return taxRates;
    }

    public void setTaxRates(List<TaxRate> taxRates) {
        this.taxRates = taxRates;
    }

    public List<Long> getTaxRatesIds() {
        return taxRatesIds;
    }

    public void setTaxRatesIds(List<Long> taxRatesIds) {
        this.taxRatesIds = taxRatesIds;
    }

    // Calculate the total tax rate dynamically
    public String getGroupTaxRate() {
        double totalRate = 0;
        for (TaxRate taxRate : taxRates) {
            totalRate += taxRate.getTaxRate(); // Sum up all the individual tax rates
        }
        return String.format("%.2f", totalRate);  // Format the result to two decimal places
    }

    public void setGroupTaxRate(String groupTaxRate) {
        this.groupTaxRate = groupTaxRate;
    }

    public String getSubTaxes() {
        // Return a comma-separated list of the tax rate names
        StringBuilder subTaxesBuilder = new StringBuilder();
        for (TaxRate taxRate : taxRates) {
            if (subTaxesBuilder.length() > 0) {
                subTaxesBuilder.append(", ");
            }
            subTaxesBuilder.append(taxRate.getName());
        }
        return subTaxesBuilder.toString();
    }

    public void setSubTaxes(String subTaxes) {
        SubTaxes = subTaxes;
    }
}
