package org.example;

import org.example.dto.BookDto;
import org.example.dto.BookOrderDto;
import org.example.dto.ClientDto;
import org.example.service.BookOrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@Transactional
public class BookOrderServiceTest {
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
    public void addBookOrder() {
        BookDto bookDto = new BookDto();
        bookDto.setId(2L);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(1L);

        BookOrderDto dto = new BookOrderDto();
        dto.setBook(bookDto);
        dto.setClient(clientDto);
        dto.setDateBegin(LocalDate.now());

        BookOrderDto result = bookOrderService.addBookOrder(dto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getBook());
        assertNotNull(result.getClient());
        assertEquals(dto.getBook().getId(), result.getBook().getId());
        assertEquals(dto.getClient().getId(), result.getClient().getId());
        assertEquals(dto.getDateBegin(), result.getDateBegin());
    }

    @Test
    public void getAllBookOrders() {
        List<BookOrderDto> dtos = bookOrderService.getAllBookOrders();

        assertNotNull(dtos);
        assertFalse(dtos.isEmpty());
        assertEquals(2, dtos.size());
    }

    @Test
    public void addBookOrderWithNullParams() {
        BookDto bookDto = new BookDto();

        ClientDto clientDto = new ClientDto();
        clientDto.setId(1L);

        BookOrderDto dto = new BookOrderDto();
        dto.setBook(bookDto);
        dto.setClient(clientDto);
        dto.setDateBegin(LocalDate.now());

        RuntimeException nullBookIdException = assertThrows(RuntimeException.class, () -> bookOrderService.addBookOrder(dto));
        assertNotNull(nullBookIdException.getMessage());
        assertEquals("Не указан id книги, которую заказывают", nullBookIdException.getMessage());

        dto.setBook(null);

        RuntimeException nullBookException = assertThrows(RuntimeException.class, () -> bookOrderService.addBookOrder(dto));
        assertNotNull(nullBookException.getMessage());
        assertEquals("Не указана книга, которую заказывают", nullBookException.getMessage());

        bookDto.setId(2L);
        dto.setBook(bookDto);

        clientDto.setId(null);
        dto.setClient(clientDto);

        RuntimeException nullClientIdException = assertThrows(RuntimeException.class, () -> bookOrderService.addBookOrder(dto));
        assertNotNull(nullClientIdException.getMessage());
        assertEquals("Не указан id клиента, который заказывает", nullClientIdException.getMessage());

        dto.setClient(null);

        RuntimeException nullClientException = assertThrows(RuntimeException.class, () -> bookOrderService.addBookOrder(dto));
        assertNotNull(nullClientException.getMessage());
        assertEquals("Не указан клиент, который заказывает", nullClientException.getMessage());

        clientDto.setId(1L);
        dto.setClient(clientDto);
        dto.setDateBegin(null);

        RuntimeException nullDateBeginException = assertThrows(RuntimeException.class, () -> bookOrderService.addBookOrder(dto));
        assertNotNull(nullDateBeginException.getMessage());
        assertEquals("Не указана дата взятия книги", nullDateBeginException.getMessage());

        dto.setDateBegin(LocalDate.now());
        dto.setDateEnd(LocalDate.MIN);

        RuntimeException dateBeginAfterDateEndException = assertThrows(RuntimeException.class, () -> bookOrderService.addBookOrder(dto));
        assertNotNull(dateBeginAfterDateEndException.getMessage());
        assertEquals("Дата возврата не может быть раньше даты взятия книги", dateBeginAfterDateEndException.getMessage());
    }

    @Test
    public void addBookOrderWithNotExist() {
        BookDto bookDto = new BookDto();
        bookDto.setId(2L);

        ClientDto clientDto = new ClientDto();
        clientDto.setId(0L);
        BookOrderDto dto = new BookOrderDto();
        dto.setBook(bookDto);
        dto.setClient(clientDto);
        dto.setDateBegin(LocalDate.now());

        RuntimeException notExistsClientException = assertThrows(RuntimeException.class, () -> bookOrderService.addBookOrder(dto));
        assertNotNull(notExistsClientException.getMessage());
        assertEquals("Клинета с id = " + clientDto.getId() + " не существует", notExistsClientException.getMessage());

        bookDto.setId(0L);
        dto.setBook(bookDto);

        RuntimeException notExistsBookException = assertThrows(RuntimeException.class, () -> bookOrderService.addBookOrder(dto));
        assertNotNull(notExistsBookException.getMessage());
        assertEquals("Книги с id = " + bookDto.getId() + " не существует", notExistsBookException.getMessage());
    }

    @Test
    public void updateBookOrder() {
        BookDto bookDto = new BookDto();
        bookDto.setId(2L);

        BookOrderDto bookOrderDto = bookOrderService.getBookOrderById(1L);
        bookOrderDto.setBook(bookDto);

        BookOrderDto result = bookOrderService.updateBookOrder(bookOrderDto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(bookOrderDto.getBook().getId(), result.getBook().getId());
        assertEquals(bookOrderDto.getClient().getId(), result.getClient().getId());
        assertEquals(bookOrderDto.getDateBegin(), result.getDateBegin());
    }

    @Test
    public void deleteBookOrderById() {
        List<BookOrderDto> beforeDelete = bookOrderService.getAllBookOrders();
        assertNotNull(beforeDelete);
        assertEquals(2, beforeDelete.size());

        bookOrderService.deleteBookOrderById(1L);

        List<BookOrderDto> afterDelete = bookOrderService.getAllBookOrders();
        assertNotNull(afterDelete);
        assertEquals(1, afterDelete.size());
    }
}
