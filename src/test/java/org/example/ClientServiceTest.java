package org.example;

import org.example.dto.BookOrderDto;
import org.example.dto.ClientDto;
import org.example.service.BookOrderService;
import org.example.service.ClientService;
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
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Testcontainers
public class ClientServiceTest {
    @Autowired
    private ClientService clientService;

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
    public void addClient() {
        ClientDto clientDto = new ClientDto();
        clientDto.setFirstName("John");
        clientDto.setSurname("Doe");
        clientDto.setPatronymic("Patronymic");
        clientDto.setBirthday(LocalDate.now());

        ClientDto result = clientService.addClient(clientDto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(clientDto.getFirstName(), result.getFirstName());
        assertEquals(clientDto.getSurname(), result.getSurname());
        assertEquals(clientDto.getPatronymic(), result.getPatronymic());
        assertEquals(clientDto.getBirthday(), result.getBirthday());
    }

    @Test
    public void addClientWithNullParams() {
        ClientDto nullFirstName = new ClientDto();
        nullFirstName.setSurname("Doe");
        nullFirstName.setPatronymic("Patronymic");
        nullFirstName.setBirthday(LocalDate.now());

        ConstraintViolationException exceptionNullFirstName = assertThrows(ConstraintViolationException.class, () -> clientService.addClient(nullFirstName));
        assertNotNull(exceptionNullFirstName.getMessage());

        ClientDto nullSurname = new ClientDto();
        nullSurname.setFirstName("John");
        nullSurname.setPatronymic("Patronymic");
        nullSurname.setBirthday(LocalDate.now());

        ConstraintViolationException exceptionNullSurname = assertThrows(ConstraintViolationException.class, () -> clientService.addClient(nullSurname));
        assertNotNull(exceptionNullSurname.getMessage());

        ClientDto nullPatronymic = new ClientDto();
        nullPatronymic.setFirstName("John");
        nullPatronymic.setSurname("Doe");
        nullPatronymic.setBirthday(LocalDate.now());

        ConstraintViolationException exceptionNullPatronymic = assertThrows(ConstraintViolationException.class, () -> clientService.addClient(nullPatronymic));
        assertNotNull(exceptionNullPatronymic.getMessage());

        ClientDto nullBirthday = new ClientDto();
        nullBirthday.setFirstName("John");
        nullBirthday.setSurname("Doe");
        nullBirthday.setPatronymic("Patronymic");

        ConstraintViolationException exceptionNullBirthday = assertThrows(ConstraintViolationException.class, () -> clientService.addClient(nullBirthday));
        assertNotNull(exceptionNullBirthday.getMessage());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> clientService.addClient(null));
        assertNotNull(exception.getMessage());
        assertEquals("Не переданы данные клиента", exception.getMessage());
    }

    @Test
    public void getAllClients() {
        List<ClientDto> clientDtos = clientService.getAllClients();

        assertNotNull(clientDtos);
        assertFalse(clientDtos.isEmpty());
        assertEquals(4, clientDtos.size());
    }

    @Test
    public void updateClient() {
        ClientDto clientDto = clientService.getClientById(1L);
        clientDto.setFirstName("John");

        ClientDto result = clientService.updateClient(clientDto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(clientDto.getFirstName(), result.getFirstName());
        assertEquals(clientDto.getSurname(), result.getSurname());
        assertEquals(clientDto.getPatronymic(), result.getPatronymic());
        assertEquals(clientDto.getBirthday(), result.getBirthday());
    }

    @Test
    public void deleteClient() {
        List<ClientDto> clientDtosBeforeDelete = clientService.getAllClients();
        assertNotNull(clientDtosBeforeDelete);
        assertFalse(clientDtosBeforeDelete.isEmpty());
        assertEquals(4, clientDtosBeforeDelete.size());

        List<BookOrderDto> bookOrderDtosBeforeDelete = bookOrderService.getAllBookOrders();
        assertNotNull(bookOrderDtosBeforeDelete);
        assertFalse(bookOrderDtosBeforeDelete.isEmpty());
        assertEquals(2, bookOrderDtosBeforeDelete.size());

        clientService.deleteClientById(1L);

        List<ClientDto> clientDtosAfterDelete = clientService.getAllClients();

        assertNotNull(clientDtosAfterDelete);
        assertFalse(clientDtosAfterDelete.isEmpty());
        assertEquals(3, clientDtosAfterDelete.size());

        List<BookOrderDto> orderDtosAfterDelete = bookOrderService.getAllBookOrders();
        assertNotNull(orderDtosAfterDelete);
        assertFalse(orderDtosAfterDelete.isEmpty());
        assertEquals(1, orderDtosAfterDelete.size());
    }
}
