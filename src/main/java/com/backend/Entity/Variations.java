package com.backend.Entity;

import java.util.List;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Variations {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String variationName;
	
	 @ElementCollection
	    @CollectionTable(name = "variation_values", joinColumns = @JoinColumn(name = "variation_id"))
	    @Column(name = "value")
	private List<String> values;

	public Long getId() {
		return id;
	}
    
	public void setId(Long id) {
		this.id = id;
	}

	public String getVariationName() {
		return variationName;
	}

	public void setVariationName(String variationName) {
		this.variationName = variationName;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}
	
	
	  
	

}
