package com.luizalabs.marketplace.dto;

import java.util.List;

import com.luizalabs.marketplace.model.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductGroupDto {

	private String description;
	private List<Product> items;
	
}
