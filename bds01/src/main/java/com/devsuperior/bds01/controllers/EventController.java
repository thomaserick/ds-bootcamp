package com.devsuperior.bds01.controllers;


import com.devsuperior.bds01.dto.EventDTO;
import com.devsuperior.bds01.servies.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class EventController
{

	@Autowired
	private EventService eventService;


	@PutMapping(value = "/{id}")
	public ResponseEntity<EventDTO> update(@PathVariable Long id, @RequestBody EventDTO dto)
	{
		EventDTO eventDTO = eventService.update(id, dto);
		return ResponseEntity.ok().body(eventDTO);
	}


}
