package com.luizalabs.marketplace.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.luizalabs.marketplace.model.Product;
import com.luizalabs.marketplace.model.RetornoModel;
import com.luizalabs.marketplace.service.ProductService;

@RestController
public class ProductResource {
	
	@Autowired
	private ProductService prodServ;
	
	@PostMapping("/products")
	public ResponseEntity<?> create(@RequestBody List<Product> lstProduct) {
		
		RetornoModel ret = new RetornoModel();
		ret.setData(prodServ.save(lstProduct));
		
		return ResponseEntity.ok(ret);
		
	}
	
}
