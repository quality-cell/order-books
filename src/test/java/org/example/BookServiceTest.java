package org.example;

import org.example.dto.AuthorDto;
import org.example.dto.BookDto;
import org.example.dto.BookOrderDto;
import org.example.service.BookOrderService;
import org.example.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Testcontainers
public class BookServiceTest {
    @Autowired
    private BookService bookService;

    @Autowired
    private BookOrderService bookOrderService;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.9")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Test
    public void addBook() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);

        BookDto bookDto = new BookDto();
        bookDto.setTitle("Война и мир");
        bookDto.setAuthor(authorDto);
        bookDto.setIsbn("123456789");

        BookDto result = bookService.addBook(bookDto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(bookDto.getIsbn(), result.getIsbn());
        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getAuthor().getId(), result.getAuthor().getId());
    }

    @Test
    public void addBookWithNullParams() {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);

        BookDto nullTitle = new BookDto();
        nullTitle.setAuthor(authorDto);
        nullTitle.setIsbn("123456789");

        ConstraintViolationException exceptionNullTitle = assertThrows(ConstraintViolationException.class, () -> bookService.addBook(nullTitle));
        assertNotNull(exceptionNullTitle.getMessage());

        BookDto nullAuthor = new BookDto();
        nullAuthor.setTitle("Война и мир");
        nullAuthor.setIsbn("123456789");

        ConstraintViolationException exceptionNullAuthor = assertThrows(ConstraintViolationException.class, () -> bookService.addBook(nullAuthor));
        assertNotNull(exceptionNullAuthor.getMessage());

        BookDto nullIsbn = new BookDto();
        nullIsbn.setTitle("Война и мир");
        nullIsbn.setAuthor(authorDto);

        ConstraintViolationException exceptionNullIsbn = assertThrows(ConstraintViolationException.class, () -> bookService.addBook(nullIsbn));
        assertNotNull(exceptionNullIsbn.getMessage());

        BookDto dto = new BookDto();
        dto.setTitle("Война и мир");
        dto.setAuthor(authorDto);
        dto.setIsbn("1234567890000000000000");

        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> bookService.addBook(dto));
        assertNotNull(exception.getMessage());

        RuntimeException dtoNull = assertThrows(RuntimeException.class, () -> bookService.addBook(null));
        assertNotNull(dtoNull.getMessage());
        assertEquals("Не переданы данные для добавления книги", dtoNull.getMessage());
    }

    @Test
    public void getAllBooks() {
        List<BookDto> bookDtos = bookService.getAllBooks();

        assertNotNull(bookDtos);
        assertFalse(bookDtos.isEmpty());
        assertEquals(4, bookDtos.size());
    }

    @Test
    public void updateBook() {
        BookDto bookDto = bookService.getBookById(1L);
        bookDto.setTitle("Война и мир");

        BookDto result = bookService.updateBook(bookDto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(bookDto.getTitle(), result.getTitle());
        assertEquals(bookDto.getId(), result.getId());
        assertEquals(bookDto.getIsbn(), result.getIsbn());
    }

    @Test
    public void deleteBookById() {
        List<BookDto> bookDtosBeforeDelete = bookService.getAllBooks();
        assertNotNull(bookDtosBeforeDelete);
        assertFalse(bookDtosBeforeDelete.isEmpty());
        assertEquals(4, bookDtosBeforeDelete.size());

        List<BookOrderDto> bookOrderDtosBeforeDelete = bookOrderService.getAllBookOrders();
        assertNotNull(bookOrderDtosBeforeDelete);
        assertFalse(bookOrderDtosBeforeDelete.isEmpty());
        assertEquals(2, bookOrderDtosBeforeDelete.size());

        bookService.deleteBookById(1L);

        List<BookDto> bookDtosAfterDelete = bookService.getAllBooks();

        assertNotNull(bookDtosAfterDelete);
        assertFalse(bookDtosAfterDelete.isEmpty());
        assertEquals(3, bookDtosAfterDelete.size());

        List<BookOrderDto> orderDtosAfterDelete = bookOrderService.getAllBookOrders();
        assertNotNull(orderDtosAfterDelete);
        assertFalse(orderDtosAfterDelete.isEmpty());
        assertEquals(1, orderDtosAfterDelete.size());
    }
}
