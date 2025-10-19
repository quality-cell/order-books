package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
public class BookOrderDto {
    private Long id;
    private BookDto book;
    private ClientDto client;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateBegin;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateEnd;
}
