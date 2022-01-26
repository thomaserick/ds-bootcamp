package com.tef.dscatalog.entities;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name = "tb_product")
public class Product implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	@Column(columnDefinition = "TEXT")
	private String description;
	private Double price;
	private String imgUrl;


	@ManyToMany
	@JoinTable(name = "tb_product_category",
		joinColumns = @JoinColumn(name = "product_id"),
		inverseJoinColumns = @JoinColumn(name = "category_id"))
	Set<Category> categories = new HashSet<>();

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant createdAt;

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant updatedAt;

	public Product()
	{
	}

	public Product(Long id, String name,  Double price, String description, String imgUrl)
	{
		this.id = id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.imgUrl = imgUrl;
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

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Double getPrice()
	{
		return price;
	}

	public void setPrice(Double price)
	{
		this.price = price;
	}

	public String getImgUrl()
	{
		return imgUrl;
	}

	public void setImgUrl(String imgUrl)
	{
		this.imgUrl = imgUrl;
	}

	public Instant getCreatedAt()
	{
		return createdAt;
	}

	public Instant getUpdatedAt()
	{
		return updatedAt;
	}

	public Set<Category> getCategories()
	{
		return categories;
	}

	@PrePersist
	public void prePersist()
	{
		createdAt = Instant.now();
	}

	@PreUpdate
	public void preUpdate()
	{
		updatedAt = Instant.now();
	}


	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}
		Product product = (Product) o;
		return id.equals(product.id);
	}

	@Override
	public int hashCode()
	{
		return Objects.hash(id);
	}
}
