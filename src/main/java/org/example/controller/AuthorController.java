package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.AuthorDto;
import org.example.dto.ClientDto;
import org.example.service.AuthorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    public String addAuthor(@ModelAttribute AuthorDto authorDto, RedirectAttributes redirectAttributes) {
        try {
            authorService.addAuthor(authorDto);

            redirectAttributes.addFlashAttribute("successMessage", "Автор успешно добавлен!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось добавить автора: " + e.getMessage());
        }

        return "redirect:/author";
    }

    @GetMapping()
    public String getAllAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());

        return "author/authors";
    }

    @GetMapping("/new")
    public String getAddAuthorForm(Model model) {
        model.addAttribute("author", new AuthorDto());

        return "author/add-author";
    }

    @GetMapping("/edit/{authorId}")
    public String getEditAuthorForm(@PathVariable Long authorId, Model model) {
        AuthorDto dto = authorService.getAuthorById(authorId);

        model.addAttribute("author", dto);

        return "author/edit-author";
    }

    @PutMapping
    public String updateAuthor(@ModelAttribute AuthorDto authorDto, RedirectAttributes redirectAttributes) {
        try {
            authorService.updateAuthor(authorDto);

            redirectAttributes.addFlashAttribute("successMessage", "Данные автора успешно обновлены!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось обновить автора: " + e.getMessage());
        }

        return "redirect:/author";
    }

    @DeleteMapping("/{authorId}")
    public String deleteAuthorById(@PathVariable Long authorId, RedirectAttributes redirectAttributes) {
        try {
            authorService.deleteAuthorById(authorId);

            redirectAttributes.addFlashAttribute("successMessage", "Автор успешно удален!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось удалить автора: " + e.getMessage());
        }

        return "redirect:/author";
    }
}
