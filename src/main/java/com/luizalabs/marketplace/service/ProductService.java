package com.luizalabs.marketplace.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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

	public List<ProductGroupDto> getProductGroups(List<Product> lstProd, String filter, String groupBy)
			throws Exception {

		try {

			List<ProductGroupDto> lstProdGroup = new ArrayList<ProductGroupDto>();

			if (lstProd.size() > 0) {

				lstProd = setFilterProducts(lstProd, filter);
				lstProdGroup = setGroupProducts(lstProd, groupBy, 0);

			}

			return lstProdGroup;
		} catch (Exception e) {
			System.out.println("Erro ProductService - getProductGroups - " + e.getMessage());
			throw new Exception();
		}

	}

	public List<Product> setFilterProducts(List<Product> lstProd, String filter) {

		if (filter != null && filter != "")
			return lstProd.stream().filter(p -> p.returnFilter(filter)).collect(Collectors.toList());
		return lstProd;
	}

	public List<ProductGroupDto> setGroupProducts(List<Product> lstProd, String groupBy, int defaultGroupBy) {

		List<ProductGroupDto> lstProdGroup = new ArrayList<ProductGroupDto>();

		Map<Object, List<Product>> mapGroupProducts = lstProd.stream().collect(Collectors
				.groupingBy(p -> p.returnGroupBy(groupBy), Collectors.mapping((Product p) -> p, Collectors.toList())));

		for (Object o : mapGroupProducts.keySet()) {

			if (mapGroupProducts.get(o).size() > 0) {

				if (mapGroupProducts.get(o).size() > 1 || (!groupBy.isEmpty() && defaultGroupBy == 0)) {

					lstProd.removeAll(mapGroupProducts.get(o));

					lstProdGroup.add(ProductGroupDto.builder().description(mapGroupProducts.get(o).get(0).getTitle())
							.items(mapGroupProducts.get(o)).build());
				}
			}
		}

		if (groupBy == null || groupBy.isEmpty()) {

			lstProdGroup.addAll(setGroupProducts(lstProd, "title", 1));
			lstProdGroup.forEach(g -> lstProd.removeAll(g.getItems()));
			lstProdGroup.addAll(setGroupProducts(lstProd, "brand", 0));

		}

		return lstProdGroup;
	}

}
