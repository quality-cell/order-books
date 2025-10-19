package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ClientWithBookOrderDto;
import org.example.service.BookOrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book-order")
@RequiredArgsConstructor
public class BookOrderRestController {
    private final BookOrderService bookOrderService;

    @GetMapping("/by-client")
    public List<ClientWithBookOrderDto> getBookOrdersByClient() {
        return bookOrderService.getBookOrdersByClient();
    }
}
