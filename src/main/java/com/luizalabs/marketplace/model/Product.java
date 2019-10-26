package com.luizalabs.marketplace.model;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Comparator;

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
			return this.getPrice().compareTo(new BigDecimal(fieldValue[1])) == 0;

		if (fieldValue[0].equals("stock"))
			return this.getStock().compareTo(Long.valueOf(fieldValue[1])) == 0;

		return true;

	}

	public Object returnGroupBy(String groupByField) {

		try {

			for (Field f : this.getClass().getDeclaredFields()) {
				if (groupByField.equals(f.getName()))
					return this.getClass().getMethod("get" + ucFirst(f.getName())).invoke(this);
			}

			return this.getEan();

		} catch (Exception e) {
			return this.getEan();
		}
	}

	public static Comparator<Product> returnComparator(String orderByValue) {

		String[] fieldValue = getFieldAndValue(orderByValue);
		Comparator<Product> comparatorProd = Comparator.comparing(Product::getStock);

		if (fieldValue[0].equals("id"))
			comparatorProd = Comparator.comparing(Product::getId);

		if (fieldValue[0].equals("ean"))
			comparatorProd = Comparator.comparing(Product::getEan);

		if (fieldValue[0].equals("title"))
			comparatorProd = Comparator.comparing(Product::getTitle);

		if (fieldValue[0].equals("brand"))
			comparatorProd = Comparator.comparing(Product::getBrand);

		if (fieldValue[0].equals("price"))
			comparatorProd = Comparator.comparing(Product::getPrice);

		if (fieldValue[0].equals("stock"))
			comparatorProd = Comparator.comparing(Product::getStock);

		if (orderByValue.isEmpty() || (fieldValue.length > 1 && fieldValue[1].equals("desc")))
			comparatorProd = comparatorProd.reversed();

		return comparatorProd;

	}

	private static String[] getFieldAndValue(String fieldValue) {
		return fieldValue.toLowerCase().split(":");
	}

	private String ucFirst(String input) {

		if (input.length() <= 1) {
			return input.toUpperCase();
		}

		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}
}