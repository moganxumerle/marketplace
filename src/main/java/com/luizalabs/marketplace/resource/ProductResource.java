package com.luizalabs.marketplace.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luizalabs.marketplace.dto.ProductGroupDto;
import com.luizalabs.marketplace.model.Product;
import com.luizalabs.marketplace.model.ReturnModel;
import com.luizalabs.marketplace.service.ProductService;

@RestController
public class ProductResource {

	@Autowired
	private ProductService prodServ;

	@PostMapping("/products")
	public ResponseEntity<?> create(@RequestBody List<Product> lstProduct,
			@RequestParam(value = "filter", defaultValue = "") String filter,
			@RequestParam(value = "group_by", defaultValue = "") String groupBy) {

		ReturnModel ret = new ReturnModel();

		try {
			
			List<Product> lstProductsSaved = prodServ.save(lstProduct);
			
			List<ProductGroupDto> lstProdGroup = prodServ.getProductGroups(lstProductsSaved, filter, groupBy);

			ret.setData(lstProdGroup);

			return ResponseEntity.ok(ret);
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a requisição!");
		}

	}

}
