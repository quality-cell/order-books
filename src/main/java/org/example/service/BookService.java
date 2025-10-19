package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.BookDto;
import org.example.entity.Book;
import org.example.mapper.BookMapper;
import org.example.repository.BookRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    public BookDto addBook(@Valid BookDto dto) {
        if (dto == null) {
            throw new RuntimeException("Не переданы данные для добавления книги");
        }

        Book book = bookMapper.toEntity(dto);

        return bookMapper.toDto(bookRepository.addBook(book));
    }

    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.getAllBooks();

        return bookMapper.toDtoList(books);
    }

    public BookDto getBookById(Long bookId) {
        Book book = bookRepository.getBookById(bookId);

        return bookMapper.toDto(book);
    }

    public BookDto updateBook(@Valid BookDto dto) {
        if (dto == null) {
            throw new RuntimeException("Не переданы данные книги");
        }

        if (dto.getId() == null) {
            throw new RuntimeException("Не передан id книги");
        }

        Book book = bookMapper.toEntity(dto);

        return bookMapper.toDto(bookRepository.updateBook(book));
    }

    public void deleteBookById(Long bookId) {
        if (bookId == null) {
            throw new RuntimeException("Не указа id книги");
        }

        bookRepository.deleteBookById(bookId);
    }
}
