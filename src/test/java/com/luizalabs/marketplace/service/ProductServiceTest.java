package com.luizalabs.marketplace.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.luizalabs.marketplace.model.Product;
import com.luizalabs.marketplace.repository.ProductRepository;

public class ProductServiceTest {

	@InjectMocks
	private ProductService prodServ;

	@Mock
	private ProductRepository prodRep;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void should_create_new_product() {

		Product newProductInBase = new Product();
		newProductInBase.setId("u7042");
		newProductInBase.setEan("7898054800492");
		newProductInBase.setTitle("Espada de fótons Nikana Azul");
		newProductInBase.setBrand("nikana");
		newProductInBase.setPrice(new BigDecimal("2199.90"));
		newProductInBase.setStock(82L);

		Product newProduct = new Product();
		newProduct.setId("u7042");
		newProduct.setEan("7898054800492");
		newProduct.setTitle("Espada de fótons Nikana Azul");
		newProduct.setBrand("nikana");
		newProduct.setPrice(new BigDecimal("2199.90"));
		newProduct.setStock(82L);

		when(prodRep.save(Arrays.asList(newProduct))).thenReturn(Arrays.asList(newProductInBase));
		when(prodRep.findAll()).thenReturn(Arrays.asList(newProductInBase));

		List<Product> lstProdSaved = prodServ.save( Arrays.asList(newProduct));

		assertThat(lstProdSaved.size(), equalTo(1));
		assertThat(lstProdSaved.get(0).getId(), equalTo("u7042"));
		assertThat(lstProdSaved.get(0).getId(), equalTo("u7042"));
		assertThat(lstProdSaved.get(0).getEan(), equalTo("7898054800492"));
		assertThat(lstProdSaved.get(0).getTitle(), equalTo("Espada de fótons Nikana Azul"));
		assertThat(lstProdSaved.get(0).getBrand(), equalTo("nikana"));
		assertThat(lstProdSaved.get(0).getPrice(), equalTo(new BigDecimal("2199.90")));
		assertThat(lstProdSaved.get(0).getStock(), equalTo(82L));

	}

}
