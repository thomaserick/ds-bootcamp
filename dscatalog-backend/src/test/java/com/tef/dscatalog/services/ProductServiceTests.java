package com.tef.dscatalog.services;


import com.tef.dscatalog.Factory;
import com.tef.dscatalog.dto.ProductDTO;
import com.tef.dscatalog.entities.Category;
import com.tef.dscatalog.entities.Product;
import com.tef.dscatalog.repositories.CategoryRepository;
import com.tef.dscatalog.repositories.ProductRepository;
import com.tef.dscatalog.services.exception.DatabaseException;
import com.tef.dscatalog.services.exception.ResourceNotFoundException;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class ProductServiceTests
{
	@InjectMocks
	private ProductServices productServices;

	@Mock
	private ProductRepository productRepository;

	@Mock
	private CategoryRepository categoryRepository;

	private long existingId;
	private long nonExistinId;
	private long dependentId;
	private PageImpl<Product> page;
	private Product product;
	private Category category;
	private ProductDTO productDTO;

	@BeforeEach
	void setup()
	{
		existingId = 1L;
		nonExistinId = 1000L;
		dependentId = 4L;
		product = Factory.createProduct();
		page = new PageImpl<>(List.of(product));
		category = Factory.createCategory();
		productDTO = Factory.createProductDTO();

		Mockito.when(categoryRepository.getById(existingId)).thenReturn(category);
		Mockito.when(categoryRepository.getById(nonExistinId)).thenThrow(EntityNotFoundException.class);
		Mockito.when(productRepository.getById(existingId)).thenReturn(product);
		Mockito.when(productRepository.getById(nonExistinId)).thenThrow(EntityNotFoundException.class);
		Mockito.when(productRepository.save(ArgumentMatchers.any())).thenReturn(product);
		Mockito.when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);
		Mockito.when(productRepository.findById(existingId)).thenReturn(Optional.of(product));
		Mockito.when(productRepository.findById(nonExistinId)).thenReturn(Optional.empty());

		Mockito.doNothing().when(productRepository).deleteById(existingId);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(productRepository)
			.deleteById(nonExistinId);
		Mockito.doThrow(DataIntegrityViolationException.class).when(productRepository)
			.deleteById(dependentId);

	}

	@Test
	public void updateShouldReturnProductDTO()
	{
		ProductDTO result = productServices.update(existingId, productDTO);
		Assertions.assertNotNull(result);
	}

	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist()
	{
		Assertions.assertThrows(ResourceNotFoundException.class,
			() -> productServices.update(nonExistinId, productDTO));
	}

	@Test
	public void findAllPagedShouldReturnPage()
	{
		Pageable pageable = PageRequest.of(0, 10);
		Page<ProductDTO> result = productServices.findAllPaged(pageable);
		Assertions.assertNotNull(result);
		Mockito.verify(productRepository, Mockito.times(1)).findAll(pageable);
	}

	@Test
	public void findByIdShouldReturnProductDTOWhenIdExists()
	{
		ProductDTO productDTO = productServices.findById(existingId);
		Assertions.assertNotNull(productDTO);
	}

	@Test
	void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist()
	{
		Assertions.assertThrows(ResourceNotFoundException.class,
			() -> productServices.findById(nonExistinId));
	}

	@Test
	public void deleteShouldDoNothingWhenIdExists()
	{
		Assertions.assertDoesNotThrow(() -> productServices.delete(existingId));
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(existingId);
	}

	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist()
	{
		Assertions.assertThrows(ResourceNotFoundException.class,
			() -> productServices.delete(nonExistinId));
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(nonExistinId);
	}

	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId()
	{
		Assertions.assertThrows(DatabaseException.class, () -> productServices.delete(dependentId));
		Mockito.verify(productRepository, Mockito.times(1)).deleteById(dependentId);
	}


}
