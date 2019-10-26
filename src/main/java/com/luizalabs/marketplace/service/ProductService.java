package com.luizalabs.marketplace.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizalabs.marketplace.dto.ProductGroupDto;
import com.luizalabs.marketplace.model.Product;
import com.luizalabs.marketplace.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository prodRep;

	public List<Product> save(final List<Product> prod) {
		return prodRep.save(prod);
	}

	public List<ProductGroupDto> getProductGroups(List<Product> lstProd, String filter) throws Exception {

		try {

			List<ProductGroupDto> lstProdGroup = new ArrayList<ProductGroupDto>();

			if (lstProd.size() > 0) {

				lstProd = filterProducts(lstProd, filter);

				lstProdGroup.add(ProductGroupDto.builder().description("Filter").items(lstProd).build());

			}

			return lstProdGroup;
		} catch (Exception e) {
			System.out.println("Erro ProductService - getProductGroups - " + e.getMessage());
			throw new Exception();
		}

	}

	public List<Product> filterProducts(List<Product> lstProd, String filter) {

		if (filter != null && filter != "")
			return lstProd.stream().filter(p -> p.returnFilter(filter)).collect(Collectors.toList());
		return lstProd;
	}

}
