package com.devsuperior.bds01.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devsuperior.bds01.dto.CityDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CityControllerIT
{

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void findAllShouldReturnAllResourcesSortedByName() throws Exception
	{

		ResultActions result = mockMvc.perform(get("/cities").contentType(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$[0].name").value("Barra Velha"));
		result.andExpect(jsonPath("$[1].name").value("Bauru"));
		result.andExpect(jsonPath("$[2].name").value("Bofete"));
	}

	@Test
	void insertShouldInsertResource() throws Exception
	{

		CityDTO dto = new CityDTO(null,"Recife");
		String jsonBody = objectMapper.writeValueAsString(dto);

		ResultActions result = mockMvc.perform(
			post("/cities").content(jsonBody).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.name").value("Recife"));
	}

	@Test
	void deleteShouldNoContentWhenIndependentId() throws Exception
	{
		Long independentId = 7L;
		ResultActions resultActions = mockMvc.perform(delete("/cities/{id}", independentId));
		resultActions.andExpect(status().isNoContent());
	}

	@Test
	void deleteShouldReturnNotFoundWhenNonExistingId() throws Exception
	{
		Long nonExistingId = 50L;
		ResultActions resultActions = mockMvc.perform(delete("/cities/{id}", nonExistingId));
		resultActions.andExpect(status().isNotFound());
	}

	@Test
	@Transactional(propagation = Propagation.NEVER)
	//Funciona pois o teste da data integration exception
	void deleteShouldReturnBadRequestWhenDependentId() throws Exception
	{
		Long dependentId = 1L;

		ResultActions resultActions = mockMvc.perform(delete("/cities/{id}", dependentId));
		resultActions.andExpect(status().isBadRequest());
	}


}
