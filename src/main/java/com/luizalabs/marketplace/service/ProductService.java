package com.luizalabs.marketplace.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luizalabs.marketplace.model.Product;
import com.luizalabs.marketplace.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository prodRep;

	public List<Product> save(final List<Product> prod) {
		
		prodRep.save(prod);
		return prodRep.findAll();
		
	}

}
