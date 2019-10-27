package com.luizalabs.marketplace.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.luizalabs.marketplace.model.Product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductGroupDto {

	private String description;
	private List<Product> items;

	@JsonIgnore
	private String groupBy;

	public void setDescriptionGroup() {

		if (this.getItems().size() > 0) {
			this.setDescription(this.getGroupBy().equals("brand") ? this.getItems().get(0).getBrand()
					: this.getItems().get(0).getTitle());
		} else {
			this.setDescription("Not Found");
		}
	}

}
