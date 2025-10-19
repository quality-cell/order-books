package org.example.controller;


import lombok.RequiredArgsConstructor;
import org.example.dto.BookDto;
import org.example.service.BookService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @PostMapping
    public String addBook(@ModelAttribute BookDto dto, RedirectAttributes redirectAttributes) {
        try {
            bookService.addBook(dto);

            redirectAttributes.addFlashAttribute("successMessage", "Книга успешно добавлена!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось добавить книгу: " + e.getMessage());
        }

        return "redirect:/book";
    }

    @GetMapping()
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());

        return "books";
    }

    @GetMapping("/new")
    public String getAddBookForm(Model model) {
        model.addAttribute("book", new BookDto());

        return "add-book";
    }

    @GetMapping("/edit/{bookId}")
    public String getEditBookForm(@PathVariable Long bookId, Model model) {
        BookDto dto = bookService.getBookById(bookId);

        model.addAttribute("book", dto);

        return "edit-book";
    }

    @PutMapping
    public String updateBook(@ModelAttribute BookDto dto, RedirectAttributes redirectAttributes) {
        try {
            bookService.updateBook(dto);

            redirectAttributes.addFlashAttribute("successMessage", "Данные о книге успешно обновлены!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось обновить инфорамцию о книге: " + e.getMessage());
        }

        return "redirect:/book";
    }

    @DeleteMapping("/{bookId}")
    public String deleteBookById(@PathVariable Long bookId, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBookById(bookId);

            redirectAttributes.addFlashAttribute("successMessage", "Книга успешно удалена!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось удалить книгу: " + e.getMessage());
        }

        return "redirect:/book";
    }
}
