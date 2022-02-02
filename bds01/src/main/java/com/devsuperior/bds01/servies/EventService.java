package com.devsuperior.bds01.servies;

import com.devsuperior.bds01.dto.EventDTO;
import com.devsuperior.bds01.entities.City;
import com.devsuperior.bds01.entities.Event;
import com.devsuperior.bds01.repositories.EventRepository;
import com.devsuperior.bds01.servies.exception.ResourceNotFoundException;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EventService
{

	@Autowired
	private EventRepository eventRepository;

	@Transactional()
	public EventDTO update(Long id, EventDTO eventDTO)
	{
		try
		{
			Event event = eventRepository.getOne(id);
			copyDtoToEntity(eventDTO, event);
			event = eventRepository.save(event);
			return new EventDTO(event);
		}
		catch (EntityNotFoundException e)
		{
			throw new ResourceNotFoundException("Id not found" + id);
		}
	}

	private void copyDtoToEntity(EventDTO eventDTO, Event event)
	{
		event.setName(eventDTO.getName());
		event.setDate(eventDTO.getDate());
		event.setUrl(eventDTO.getUrl());
		event.setCity(new City(eventDTO.getCityId(), null));
	}
}
