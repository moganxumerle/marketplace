package com.luizalabs.marketplace.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luizalabs.marketplace.model.Product;
import com.luizalabs.marketplace.service.ProductService;

@RestController
public class ProductResource {
	
	@Autowired
	private ProductService prodServ;
	
	@PostMapping("/products")
	public ResponseEntity<List<Product>> create(@RequestBody List<Product> lstProduct) {
		
		return ResponseEntity.ok(prodServ.save(lstProduct));
		
	}
	
}
