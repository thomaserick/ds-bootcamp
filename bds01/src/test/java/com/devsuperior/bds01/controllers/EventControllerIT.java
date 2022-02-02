package com.devsuperior.bds01.controllers;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.devsuperior.bds01.dto.EventDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@SpringBootTest
@AutoConfigureMockMvc
class EventControllerIT
{

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	private EventDTO eventDTO;

	@BeforeEach
	void setup()
	{
		eventDTO = new EventDTO(null, "Expo XP", LocalDate.of(2022, 02, 01), "https://tef.com.br", 7L);
	}


	@Test
	void updateShouldUpdateResourceWhenIdExists() throws Exception
	{

		long existingId = 1L;
		String jsonBody = objectMapper.writeValueAsString(eventDTO);

		ResultActions result = mockMvc.perform(
			put("/events/{id}", existingId).content(jsonBody).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.id").value(1L));
		result.andExpect(jsonPath("$.name").value("Expo XP"));
		result.andExpect(jsonPath("$.date").value("2022-02-01"));
		result.andExpect(jsonPath("$.cityId").value(7L));
	}

	@Test
	void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception
	{

		long nonExistingId = 1000L;

		String jsonBody = objectMapper.writeValueAsString(eventDTO);

		ResultActions resultActions = mockMvc.perform(
			put("/events/{id}", nonExistingId).content(jsonBody).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));

		resultActions.andExpect(status().isNotFound());
	}
}
