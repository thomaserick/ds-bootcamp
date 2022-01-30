package com.tef.dscatalog;

import com.tef.dscatalog.dto.ProductDTO;
import com.tef.dscatalog.entities.Category;
import com.tef.dscatalog.entities.Product;
import org.checkerframework.checker.units.qual.C;

public class Factory
{
	public static Product createProduct()
	{
		Product product = new Product(1L, "Phone", 800.00, "Good Phone", "Https://img.com/img.png");
		product.getCategories().add(createCategory());
		return product;
	}

	public static ProductDTO createProductDTO()
	{
		Product product = createProduct();
		return new ProductDTO(product, product.getCategories());
	}

	public static Category createCategory()
	{
		Category category = new Category(1L, "Electronics");
		return category;
	}
}
