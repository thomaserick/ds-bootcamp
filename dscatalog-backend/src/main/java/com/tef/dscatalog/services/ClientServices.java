package com.tef.dscatalog.services;

import com.tef.dscatalog.dto.ClientDTO;
import com.tef.dscatalog.entities.Client;
import com.tef.dscatalog.repositories.ClientRepository;
import com.tef.dscatalog.services.exception.DatabaseException;
import com.tef.dscatalog.services.exception.ResourceNotFoundException;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClientServices
{
	@Autowired
	private ClientRepository clientRepository;

	//Não da lock no banco de dados
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest)
	{
		Page<Client> list = clientRepository.findAll(pageRequest);
		return list.map(ClientDTO::new);
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id)
	{
		Optional<Client> obj = clientRepository.findById(id);
		Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not Found"));
		return new ClientDTO(entity);
	}


	@Transactional()
	public ClientDTO insert(ClientDTO dto)
	{
		Client Client = fromDto(dto);
		Client = clientRepository.save(Client);
		return new ClientDTO(Client);

	}

	@Transactional()
	public ClientDTO update(Long id, ClientDTO dto)
	{
		try
		{
			Client entity = clientRepository.getById(id);
			Client client = fromDto(dto);
			client.setId(entity.getId());
			entity = clientRepository.save(client);
			return new ClientDTO(entity);
		}
		catch (EntityNotFoundException e)
		{
			throw new ResourceNotFoundException("Id not found" + id);
		}
	}

	/**
	 * Sem transaction para capturar do banco de dados
	 */
	public void delete(Long id)
	{
		try
		{

			clientRepository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e)
		{
			throw new ResourceNotFoundException("Id not found" + id);
		}
		catch (DataIntegrityViolationException e)
		{
			throw new DatabaseException("Integrity violation" + id);
		}
	}

	private Client fromDto(ClientDTO dto)
	{
		Client client = new Client();
		client.setName(dto.getName());
		client.setCpf(dto.getCpf());
		client.setIncome(dto.getIncome());
		client.setChildren(dto.getChildren());
		client.setBirthDate(dto.getBirthDate());

		return client;
	}


}
