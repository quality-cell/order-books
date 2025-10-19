package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.BookOrderDto;
import org.example.service.BookOrderService;
import org.example.service.BookService;
import org.example.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/book-order")
@RequiredArgsConstructor
public class BookOrderController {
    private final BookOrderService bookOrderService;
    private final ClientService clientService;
    private final BookService bookService;

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("order", new BookOrderDto());
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("books", bookService.getAllBooks());

        return "add-book-order";
    }

    @PostMapping
    public String addBookOrder(@ModelAttribute BookOrderDto bookOrderDto, RedirectAttributes redirectAttributes) {
        try {
            bookOrderService.addBookOrder(bookOrderDto);

            redirectAttributes.addFlashAttribute("successMessage", "Данные о заказе успешно добавлены!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось добавить заказ: " + e.getMessage());
        }

        return "redirect:/book-order";
    }

    @GetMapping()
    public String getAllBookOrders(Model model) {
        model.addAttribute("orders", bookOrderService.getAllBookOrders());

        return "book-orders";
    }

    @GetMapping("/edit/{bookOrderId}")
    public String getEditBookForm(@PathVariable Long bookOrderId, Model model) {
        BookOrderDto dto = bookOrderService.getBookOrderById(bookOrderId);

        model.addAttribute("order", dto);
        model.addAttribute("clients", clientService.getAllClients());
        model.addAttribute("books", bookService.getAllBooks());

        return "edit-book-order";
    }

    @PutMapping
    public String updateBookOrder(@ModelAttribute BookOrderDto bookOrderDto, RedirectAttributes redirectAttributes) {
        try {
            bookOrderService.updateBookOrder(bookOrderDto);

            redirectAttributes.addFlashAttribute("successMessage", "Данные о заказе успешно обнволена!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось обновить данные о заказе: " + e.getMessage());
        }

        return "redirect:/book-order";
    }

    @DeleteMapping("/{bookOrderId}")
    public String deleteBookOrderById(@PathVariable Long bookOrderId, RedirectAttributes redirectAttributes) {
        try {
            bookOrderService.deleteBookOrderById(bookOrderId);

            redirectAttributes.addFlashAttribute("successMessage", "Данные о заказе успешно удалены!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось удалить заказ: " + e.getMessage());
        }

        return "redirect:/book-order";
    }
}
