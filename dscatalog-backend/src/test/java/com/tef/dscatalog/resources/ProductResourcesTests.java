package com.tef.dscatalog.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tef.dscatalog.Factory;
import com.tef.dscatalog.dto.ProductDTO;
import com.tef.dscatalog.services.ProductServices;
import com.tef.dscatalog.services.exception.DatabaseException;
import com.tef.dscatalog.services.exception.ResourceNotFoundException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@WebMvcTest(ProductResource.class)
class ProductResourcesTests
{

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductServices productServices;

	@Autowired
	private ObjectMapper objectMapper;


	//Para testes aceita New
	private PageImpl<ProductDTO> page;
	private ProductDTO productDTO;
	private long existingId;
	private long nonExistingId;
	private long dependentId;

	@BeforeEach
	private void setup()
	{
		existingId = 1L;
		nonExistingId = 2L;
		dependentId = 3L;

		productDTO = Factory.createProductDTO();
		page = new PageImpl<>(List.of(productDTO));

		when(productServices.findAllPaged(any())).thenReturn(page);
		when(productServices.findById(existingId)).thenReturn(productDTO);
		when(productServices.findById(nonExistingId)).thenThrow(ResourceNotFoundException.class);
		when(productServices.update(eq(existingId), any())).thenReturn(productDTO);
		when(productServices.update(eq(nonExistingId), any())).thenThrow(
			ResourceNotFoundException.class);
		doNothing().when(productServices).delete(existingId);
		doThrow(ResourceNotFoundException.class).when(productServices).delete(nonExistingId);
		doThrow(DatabaseException.class).when(productServices).delete(dependentId);

		when(productServices.insert(any())).thenReturn(productDTO);

	}

	@Test
	void insertShouldReturnProductsDTO() throws Exception
	{
		String jsonBody = objectMapper.writeValueAsString(productDTO);

		ResultActions resultActions = mockMvc.perform(
			post("/products").content(jsonBody).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isCreated());
	}

	@Test
	void deleteShouldReturnNoContentWhenIdExist() throws Exception
	{
		ResultActions resultActions = mockMvc.perform(
			delete("/products/{id}", existingId)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isNoContent());

	}

	@Test
	void deleteShouldReturnNotFoundWhenIdDoesNotExists() throws Exception
	{
		ResultActions resultActions = mockMvc.perform(
			delete("/products/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isNotFound());
	}


	@Test
	void findAllShouldReturnPage() throws Exception
	{
		//Faz uma requisição
		mockMvc.perform(get("/products")).andExpect(status().isOk());
	}

	@Test
	void findByIdShouldReturnProductWhenIdExists() throws Exception
	{
		ResultActions resultActions = mockMvc.perform(
			get("/products/{id}", existingId).accept(MediaType.APPLICATION_JSON));

		//Verifica os atributos do Json
		resultActions.andExpect(jsonPath("$.id").exists());
		resultActions.andExpect(jsonPath("$.name").exists());

	}

	@Test
	void findByIdShouldReturnNotFoundWhenIdDoesNotExists() throws Exception
	{
		ResultActions resultActions = mockMvc.perform(
			get("/products/{id}", nonExistingId).accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isNotFound());
	}

	@Test
	void updateShouldReturnProductDTOWhenIdExists() throws Exception
	{

		String jsonBody = objectMapper.writeValueAsString(productDTO);

		ResultActions resultActions = mockMvc.perform(
			put("/products/{id}", existingId).content(jsonBody).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isOk());
		//Verifica os atributos do Json
		resultActions.andExpect(jsonPath("$.id").exists());
		resultActions.andExpect(jsonPath("$.name").exists());

	}

	@Test
	void updateShouldReturnFoundWhenIdDoesNotExists() throws Exception
	{
		String jsonBody = objectMapper.writeValueAsString(productDTO);

		ResultActions resultActions = mockMvc.perform(
			put("/products/{id}", nonExistingId).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isNotFound());

	}


}
