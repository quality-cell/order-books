package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.BookDto;
import org.example.dto.BookOrderDto;
import org.example.dto.ClientDto;
import org.example.dto.ClientWithBookOrderDto;
import org.example.entity.BookOrder;
import org.example.entity.Client;
import org.example.mapper.BookOrderMapper;
import org.example.mapper.ClientMapper;
import org.example.repository.BookOrderRepository;
import org.example.repository.BookRepository;
import org.example.repository.ClientRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class BookOrderService {
    private final BookOrderRepository bookOrderRepository;
    private final BookRepository bookRepository;
    private final ClientRepository clientRepository;

    private final BookOrderMapper bookOrderMapper;
    private final ClientMapper clientMapper;

    public BookOrderDto addBookOrder(BookOrderDto dto) {
        validateBookOrder(dto);

        BookOrder bookOrder = bookOrderMapper.toEntity(dto);

        return bookOrderMapper.toDto(bookOrderRepository.save(bookOrder));
    }

    public List<BookOrderDto> getAllBookOrders() {
        List<BookOrder> bookOrders = bookOrderRepository.findAll();

        return bookOrderMapper.toDtoList(bookOrders);
    }

    public List<ClientWithBookOrderDto> getBookOrdersByClient() {
        List<BookOrder> bookOrders = bookOrderRepository.findAll();
        if (bookOrders.isEmpty()) {
            return null;
        }

        Map<Client, List<BookOrder>> bookOrderByClient = bookOrders.stream()
                .collect(Collectors.groupingBy(BookOrder::getClient));

        return bookOrderByClient.entrySet().stream()
                .map(entry -> {
                    ClientWithBookOrderDto clientWithBookOrderDto = new ClientWithBookOrderDto();
                    ClientDto clientDto = clientMapper.toDto(entry.getKey());
                    clientWithBookOrderDto.setClient(clientDto);

                    List<BookOrderDto> dtoListWithIgnoreClient = bookOrderMapper.toDtoListWithIgnoreClient(entry.getValue());
                    clientWithBookOrderDto.setBookOrders(dtoListWithIgnoreClient);

                    return clientWithBookOrderDto;
                })
                .collect(toList());
    }

    public BookOrderDto getBookOrderById(Long bookOrderId) {
        BookOrder bookOrder = bookOrderRepository.findById(bookOrderId).orElse(null);

        return bookOrderMapper.toDto(bookOrder);
    }

    public BookOrderDto updateBookOrder(BookOrderDto dto) {
        validateBookOrder(dto);

        if (dto.getId() == null) {
            throw new RuntimeException("Не указан id заказа");
        }

        BookOrder bookOrder = bookOrderMapper.toEntity(dto);

        return bookOrderMapper.toDto(bookOrderRepository.save(bookOrder));
    }

    public void deleteBookOrderById(Long bookOrderId) {
        if (bookOrderId == null) {
            throw new RuntimeException("Не указан id заказа");
        }

        bookOrderRepository.deleteById(bookOrderId);
    }

    private void validateBookOrder(BookOrderDto dto) {
        if (dto == null) {
            throw new RuntimeException("Не переданы данные о заказе книги");
        }

        BookDto book = dto.getBook();
        if (book == null) {
            throw new RuntimeException("Не указана книга, которую заказывают");
        }

        Long bookId = book.getId();
        if (bookId == null) {
            throw new RuntimeException("Не указан id книги, которую заказывают");
        }

        ClientDto client = dto.getClient();
        if (client == null) {
            throw new RuntimeException("Не указан клиент, который заказывает");
        }

        Long clientId = client.getId();
        if (clientId == null) {
            throw new RuntimeException("Не указан id клиента, который заказывает");
        }

        if (dto.getDateBegin() == null) {
            throw new RuntimeException("Не указана дата взятия книги");
        }

        if (dto.getDateEnd() != null && dto.getDateEnd().isBefore(dto.getDateBegin())) {
            throw new RuntimeException("Дата возврата не может быть раньше даты взятия книги");
        }

        if (!bookRepository.exitsBookById(bookId)) {
            throw new RuntimeException("Книги с id = " + bookId + " не существует");
        }

        if (!clientRepository.existsById(clientId)) {
            throw new RuntimeException("Клинета с id = " + clientId + " не существует");
        }
    }
}
