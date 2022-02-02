package com.devsuperior.bds01.repositories;

import com.devsuperior.bds01.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventRepository extends JpaRepository<Event,Long>
{
}
