package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.dto.AuthorDto;
import org.example.entity.Author;
import org.example.mapper.AuthorMapper;
import org.example.repository.AuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

@Service
@Validated
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public AuthorDto addAuthor(@Valid AuthorDto dto) {
        if (dto == null) {
            throw new RuntimeException("Не переданы данные автора");
        }

        Author author = authorMapper.toEntity(dto);

        return authorMapper.toDto(authorRepository.save(author));
    }

    public List<AuthorDto> getAllAuthors() {
        return authorMapper.toDtoList(authorRepository.findAll());
    }

    public AuthorDto getAuthorById(Long authorId) {
        Author author = authorRepository.findById(authorId).orElse(null);

        return authorMapper.toDto(author);
    }

    public AuthorDto updateAuthor(@Valid AuthorDto dto) {
        if (dto == null) {
            throw new RuntimeException("Не переданы данные автора");
        }

        if (dto.getId() == null) {
            throw new RuntimeException("Не передан id автора");
        }

        Author author = authorMapper.toEntity(dto);

        return authorMapper.toDto(authorRepository.save(author));
    }

    public void deleteAuthorById(Long authorId) {
        if (authorId == null) {
            throw new RuntimeException("Не указан id автора");
        }

        authorRepository.deleteById(authorId);
    }
}
