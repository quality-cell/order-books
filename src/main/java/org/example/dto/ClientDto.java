package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class ClientDto {
    private Long id;

    @NotEmpty(message = "Не указана фамилия клиента")
    private String surname;

    @NotEmpty(message = "Не указано имя клиента")
    private String firstName;

    @NotEmpty(message = "Не указано отчество клиента")
    private String patronymic;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Не указана дата рождения")
    private LocalDate birthday;

    private String fullName;
}
