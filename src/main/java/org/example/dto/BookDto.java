package org.example.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Setter
@Getter
public class BookDto {
    private Long id;

    @NotEmpty(message = "Не указано название книги")
    private String title;

    @NotEmpty(message = "Не указан автор книги")
    private String author;

    @NotEmpty(message = "Не указан ISBN книги")
    @Length(max = 17, message = "Слишком длинный ISBN")
    private String isbn;
}
