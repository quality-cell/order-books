package org.example.mapper;

import org.example.dto.ClientDto;
import org.example.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    @Mapping(target = "fullName", expression = "java(entity.toString())")
    ClientDto toDto(Client entity);
    Client toEntity(ClientDto dto);

    List<ClientDto> toDtoList(List<Client> entities);
}
