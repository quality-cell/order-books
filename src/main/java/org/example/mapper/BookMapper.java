package org.example.mapper;

import org.example.dto.BookDto;
import org.example.entity.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = AuthorMapper.class)
public interface BookMapper {
    BookDto toDto(Book entity);
    Book toEntity(BookDto dto);

    List<BookDto> toDtoList(List<Book> books);
}
