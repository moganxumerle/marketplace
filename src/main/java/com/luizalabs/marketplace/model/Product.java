package com.luizalabs.marketplace.model;

import java.math.BigDecimal;
import java.lang.reflect.Field;

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

	public boolean returnFilter(String filterFieldValue) {

		String[] fieldValue = getFieldAndValue(filterFieldValue);

		if (fieldValue.length <= 1)
			return true;

		if (fieldValue[0].equals("id"))
			return this.getId().equals(fieldValue[1]);

		if (fieldValue[0].equals("ean"))
			return this.getEan().equals(fieldValue[1]);

		if (fieldValue[0].equals("title"))
			return this.getTitle().equals(fieldValue[1]);

		if (fieldValue[0].equals("brand"))
			return this.getBrand().equals(fieldValue[1]);

		if (fieldValue[0].equals("price"))
			return this.getPrice().compareTo(new BigDecimal( fieldValue[1])) == 0;

		if (fieldValue[0].equals("stock"))
			return this.getStock().compareTo(Long.valueOf(fieldValue[1])) == 0;

		return true;
		
	}

	public Object returnGroupBy(String groupByFieldValue) {

		try {

			for (Field f : this.getClass().getDeclaredFields()) {
				if (groupByFieldValue.equals(f.getName()))
					return this.getClass().getMethod("get" + ucFirst(f.getName())).invoke(this);
			}

			return this.getEan();

		} catch (Exception e) {
			return this.getEan();
		}
	}

	private String[] getFieldAndValue(String fieldValue) {
		return fieldValue.toLowerCase().split(":");
	}

	private String ucFirst(String input) {

		if (input.length() <= 1) {
			return input.toUpperCase();
		}

		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}
}