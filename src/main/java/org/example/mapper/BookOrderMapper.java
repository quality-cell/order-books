package org.example.mapper;

import org.example.dto.BookOrderDto;
import org.example.entity.BookOrder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BookOrderMapper.class, ClientMapper.class})
public interface BookOrderMapper {
    BookOrderDto toDto(BookOrder entity);
    BookOrder toEntity(BookOrderDto dto);

    List<BookOrderDto> toDtoList(List<BookOrder> entities);
}
