package org.example.mapper;

import org.example.dto.BookOrderDto;
import org.example.entity.BookOrder;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BookMapper.class, ClientMapper.class})
public interface BookOrderMapper {
    BookOrderDto toDto(BookOrder entity);
    BookOrder toEntity(BookOrderDto dto);

    List<BookOrderDto> toDtoList(List<BookOrder> entities);

    @Named("toDtoWithIgnoreClient")
    @Mapping(target = "client", ignore = true)
    BookOrderDto toDtoWithIgnoreClient(BookOrder entity);

    @IterableMapping(qualifiedByName = "toDtoWithIgnoreClient")
    List<BookOrderDto> toDtoListWithIgnoreClient(List<BookOrder> entities);
}
