package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.ClientDto;
import org.example.entity.Client;
import org.example.mapper.ClientMapper;
import org.example.repository.ClientRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientDto addClient(@Valid ClientDto dto) {
        if (dto == null) {
            throw new RuntimeException("Не переданы данные клиента");
        }

        Client client = clientMapper.toEntity(dto);

        return clientMapper.toDto(clientRepository.save(client));
    }

    public List<ClientDto> getAllClients() {
        return clientMapper.toDtoList(clientRepository.findAll());
    }

    public ClientDto getClientById(Long id) {
        Client client = clientRepository.findById(id).orElse(null);

        return clientMapper.toDto(client);
    }

    public ClientDto updateClient(@Valid ClientDto dto) {
        if (dto == null) {
            throw new RuntimeException("Не переданы данные клиента");
        }

        if (dto.getId() == null) {
            throw new RuntimeException("Не передан id клиента");
        }

        Client client = clientMapper.toEntity(dto);

        return clientMapper.toDto(clientRepository.save(client));
    }

    public void deleteClientById(Long clientId) {
        if (clientId == null) {
            throw new RuntimeException("Не указан id клиента");
        }

        clientRepository.deleteById(clientId);
    }
}
