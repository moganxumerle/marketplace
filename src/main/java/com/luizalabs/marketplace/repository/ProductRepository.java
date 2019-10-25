package com.luizalabs.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.luizalabs.marketplace.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
	
}
