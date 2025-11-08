package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.ClientDto;
import org.example.service.ClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public String addClient(@ModelAttribute ClientDto clientDto, RedirectAttributes redirectAttributes) {
        try {
            clientService.addClient(clientDto);

            redirectAttributes.addFlashAttribute("successMessage", "Клиент успешно добавлен!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось добавить клиента: " + e.getMessage());
        }

        return "redirect:/client";
    }

    @GetMapping()
    public String getAllClients(Model model) {
        model.addAttribute("clients", clientService.getAllClients());

        return "client/clients";
    }

    @GetMapping("/new")
    public String getAddClientForm(Model model) {
        model.addAttribute("client", new ClientDto());

        return "client/add-client";
    }

    @GetMapping("/edit/{clientId}")
    public String getEditClientForm(@PathVariable Long clientId, Model model) {
        ClientDto dto = clientService.getClientById(clientId);

        model.addAttribute("client", dto);

        return "client/edit-client";
    }

    @PutMapping
    public String updateClient(@ModelAttribute ClientDto clientDto, RedirectAttributes redirectAttributes) {
        try {
            clientService.updateClient(clientDto);

            redirectAttributes.addFlashAttribute("successMessage", "Данные клиента успешно обновлены!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось обновить клиента: " + e.getMessage());
        }

        return "redirect:/client";
    }

    @DeleteMapping("/{clientId}")
    public String deleteClientById(@PathVariable Long clientId, RedirectAttributes redirectAttributes) {
        try {
            clientService.deleteClientById(clientId);

            redirectAttributes.addFlashAttribute("successMessage", "Клиент успешно удален!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Не удалось удалить клиента: " + e.getMessage());
        }

        return "redirect:/client";
    }
}
