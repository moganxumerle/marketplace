package com.luizalabs.marketplace.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

	@Id
	@EqualsAndHashCode.Include
	private String id;

	private String ean;
	private String title;
	private String brand;
	private BigDecimal price;
	private Long stock;
	
	public boolean returnFilter(String filterValueField) {
		
		String[] filterValue = filterValueField.toLowerCase().split(":");
		
		if (filterValue.length <= 1)
			return true;
		
		if (filterValue[0].equals("id"))
			return this.getId().equals(filterValue[1]);
		
		if (filterValue[0].equals("ean"))
			return this.getEan().equals(filterValue[1]);
		
		if (filterValue[0].equals("title"))
			return this.getTitle().equals(filterValue[1]);
		
		if (filterValue[0].equals("brand"))
			return this.getBrand().equals(filterValue[1]);
		
		if (filterValue[0].equals("price"))
			return this.getPrice().compareTo(new BigDecimal(filterValue[1])) == 0;
		
		if (filterValue[0].equals("stock"))
			return this.getStock().compareTo(Long.valueOf(filterValue[1])) == 0;

		return true;
		
	}
}