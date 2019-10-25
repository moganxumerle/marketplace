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

}