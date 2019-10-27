package com.luizalabs.marketplace.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.luizalabs.marketplace.dto.ProductGroupDto;
import com.luizalabs.marketplace.model.Product;
import com.luizalabs.marketplace.repository.ProductRepository;

public class ProductServiceTest {

	@InjectMocks
	private ProductService prodServ;

	@Mock
	private ProductRepository prodRep;

	private List<Product> lstProdPayload;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);

		lstProdPayload = new ArrayList<Product>();
		Product prod = new Product();
		Product prod2 = new Product();
		Product prod3 = new Product();

		prod.setId("u7042");
		prod.setEan("7898054800492");
		prod.setTitle("Espada de fótons Nikana Azul");
		prod.setBrand("nikana");
		prod.setPrice(new BigDecimal("2199.90"));
		prod.setStock(82L);

		lstProdPayload.add(prod);

		prod2.setId("80092");
		prod2.setEan("7898054800492");
		prod2.setTitle("Espada de Fótons REDAV Azul");
		prod2.setBrand("redav");
		prod2.setPrice(new BigDecimal("1799.90"));
		prod2.setStock(0L);

		lstProdPayload.add(prod2);

		prod3.setId("123456");
		prod3.setEan("7898054800666");
		prod3.setTitle("Violao Branco");
		prod3.setBrand("tonante");
		prod3.setPrice(new BigDecimal("1000.0"));
		prod3.setStock(10L);

		lstProdPayload.add(prod3);

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

		List<Product> lstProdSaved = prodServ.save(Arrays.asList(newProduct));

		assertThat(lstProdSaved.size(), equalTo(1));
		assertThat(lstProdSaved.get(0).getId(), equalTo("u7042"));
		assertThat(lstProdSaved.get(0).getId(), equalTo("u7042"));
		assertThat(lstProdSaved.get(0).getEan(), equalTo("7898054800492"));
		assertThat(lstProdSaved.get(0).getTitle(), equalTo("Espada de fótons Nikana Azul"));
		assertThat(lstProdSaved.get(0).getBrand(), equalTo("nikana"));
		assertThat(lstProdSaved.get(0).getPrice().compareTo(new BigDecimal("2199.90")), equalTo(0));
		assertThat(lstProdSaved.get(0).getStock(), equalTo(82L));

	}

	@Test
	public void should_filter_one_product_by_id() {

		List<Product> lstProductsFiltered = new ArrayList<Product>();

		lstProductsFiltered = prodServ.setFilterProducts(lstProdPayload, "id:80092");

		assertThat(lstProductsFiltered.size(), equalTo(1));
		assertThat(lstProductsFiltered.get(0).getId(), equalTo("80092"));
		assertThat(lstProductsFiltered.get(0).getBrand(), equalTo("redav"));

	}

	@Test
	public void should_filter_two_products_by_ean() {

		List<Product> lstProductsFiltered = new ArrayList<Product>();

		lstProductsFiltered = prodServ.setFilterProducts(lstProdPayload, "ean:7898054800492");

		assertThat(lstProductsFiltered.size(), equalTo(2));

	}

	@Test
	public void should_group_products_by_ean() {

		List<Product> lstProd = new ArrayList<Product>(lstProdPayload);
		List<ProductGroupDto> lstProductsGrouped = prodServ.setGroupProducts(lstProd, "group_by:ean", 0);

		assertThat(lstProductsGrouped.size(), equalTo(2));
		assertThat(lstProductsGrouped.get(0).getItems().get(0).getId(), equalTo("u7042"));
		assertThat(lstProductsGrouped.get(0).getItems().get(1).getId(), equalTo("80092"));
		assertThat(lstProductsGrouped.get(1).getItems().get(0).getId(), equalTo("123456"));

	}

	@Test
	public void should_group_products_by_default() {

		List<Product> lstProd = new ArrayList<Product>(lstProdPayload);
		List<ProductGroupDto> lstProductsGrouped = prodServ.setGroupProducts(lstProd, "", 0);

		assertThat(lstProductsGrouped.size(), equalTo(2));
		assertThat(lstProductsGrouped.get(0).getItems().get(0).getId(), equalTo("u7042"));
		assertThat(lstProductsGrouped.get(0).getItems().get(1).getId(), equalTo("80092"));
		assertThat(lstProductsGrouped.get(1).getItems().get(0).getId(), equalTo("123456"));

	}

	@Test
	public void should_order_products_by_default() {

		List<Product> lstProd = new ArrayList<Product>(lstProdPayload);
		List<ProductGroupDto> lstProductsGrouped = prodServ.setGroupProducts(lstProd, "", 0);
		List<ProductGroupDto> lstProductsOrdered = prodServ.setOrderGroupProducts(lstProductsGrouped, "");

		assertThat(lstProductsOrdered.size(), equalTo(2));
		assertThat(lstProductsOrdered.get(0).getItems().get(0).getId(), equalTo("80092"));
		assertThat(lstProductsOrdered.get(0).getItems().get(1).getId(), equalTo("u7042"));
		assertThat(lstProductsOrdered.get(1).getItems().get(0).getId(), equalTo("123456"));

	}

	@Test
	public void should_set_description_group_products_by_brand() {

		List<Product> lstProd = new ArrayList<Product>(lstProdPayload);

		Product prod4 = new Product();

		prod4.setId("u7044");
		prod4.setEan("7898054800777");
		prod4.setTitle("Espada de fótons Nikana Preto");
		prod4.setBrand("nikana");
		prod4.setPrice(new BigDecimal("2000.00"));
		prod4.setStock(10L);
		lstProd.add(prod4);

		List<ProductGroupDto> lstProductsGrouped = prodServ.setGroupProducts(lstProd, "brand", 0);
		List<ProductGroupDto> lstProductsOrdered = prodServ.setOrderGroupProducts(lstProductsGrouped, "stock:asc");
		List<ProductGroupDto> lstGroupProductsWithDescription = prodServ
				.setDescriptionGroupProducts(lstProductsOrdered);

		assertThat(lstGroupProductsWithDescription.size(), equalTo(3));

		System.out.println(lstGroupProductsWithDescription);

		assertThat(lstGroupProductsWithDescription.get(0).getDescription(), equalTo("nikana"));
		assertThat(lstGroupProductsWithDescription.get(0).getItems().size(), equalTo(2));
		assertThat(lstGroupProductsWithDescription.get(0).getItems().get(0).getId(), equalTo("u7044"));
		assertThat(lstGroupProductsWithDescription.get(0).getItems().get(1).getId(), equalTo("u7042"));

		assertThat(lstGroupProductsWithDescription.get(1).getDescription(), equalTo("redav"));
		assertThat(lstGroupProductsWithDescription.get(1).getItems().size(), equalTo(1));
		assertThat(lstGroupProductsWithDescription.get(1).getItems().get(0).getId(), equalTo("80092"));

		assertThat(lstGroupProductsWithDescription.get(2).getDescription(), equalTo("tonante"));
		assertThat(lstGroupProductsWithDescription.get(2).getItems().size(), equalTo(1));
		assertThat(lstGroupProductsWithDescription.get(2).getItems().get(0).getId(), equalTo("123456"));

	}

	@Test
	public void should_group_products_by_similarity_title() {

		List<Product> lstProd = new ArrayList<Product>(lstProdPayload);

		List<ProductGroupDto> lstProductsGrouped = prodServ.setGroupProducts(lstProd, "title", 0);
		List<ProductGroupDto> lstProductsOrdered = prodServ.setOrderGroupProducts(lstProductsGrouped, "id:asc");
		lstProductsOrdered = prodServ.setDescriptionGroupProducts(lstProductsOrdered);

		assertThat(lstProductsOrdered.size(), equalTo(2));
		
		System.out.println(lstProductsOrdered.get(0));
		assertThat(lstProductsOrdered.get(0).getDescription(), equalTo("Espada de Fótons REDAV Azul"));
		assertThat(lstProductsOrdered.get(0).getItems().size(), equalTo(2));
		assertThat(lstProductsOrdered.get(0).getItems().get(0).getId(), equalTo("80092"));
		assertThat(lstProductsOrdered.get(0).getItems().get(1).getId(), equalTo("u7042"));

		assertThat(lstProductsOrdered.get(1).getDescription(), equalTo("Violao Branco"));
		assertThat(lstProductsOrdered.get(1).getItems().size(), equalTo(1));
		assertThat(lstProductsOrdered.get(1).getItems().get(0).getId(), equalTo("123456"));

	}

}
