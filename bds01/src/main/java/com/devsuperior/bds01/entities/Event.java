package com.devsuperior.bds01.entities;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
@Table(name = "tb_event")
public class Event
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private LocalDate date;
	private String url;

	@ManyToOne
	@JoinColumn(name = "city_id")
	public City city;

	public Event()
	{
	}

	public Event(Long id, String name, LocalDate date, String url)
	{
		this.id = id;
		this.name = name;
		this.date = date;
		this.url = url;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public LocalDate getDate()
	{
		return date;
	}

	public void setDate(LocalDate date)
	{
		this.date = date;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public City getCity()
	{
		return city;
	}

	public void setCity(City city)
	{
		this.city = city;
	}
}
