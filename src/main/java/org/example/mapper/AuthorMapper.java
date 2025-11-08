package org.example.mapper;

import org.example.dto.AuthorDto;
import org.example.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    @Mapping(target = "fullName", expression = "java(entity.toString())")
    AuthorDto toDto(Author entity);

    Author toEntity(AuthorDto dto);

    List<AuthorDto> toDtoList(List<Author> entities);
}
