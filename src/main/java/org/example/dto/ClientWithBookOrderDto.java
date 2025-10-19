package org.example.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientWithBookOrderDto {
    private ClientDto client;
    private List<BookOrderDto> bookOrders;
}
