package com.facturacion.api.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.facturacion.api.entities.Cliente;
import com.facturacion.api.services.IClienteService;
import com.facturacion.api.services.IFacturaService;
import com.facturacion.api.util.paginator.PageRender;

import jakarta.validation.Valid;

@Controller
public class ClienteWebController {

    private final IClienteService clienteService;
    private final IFacturaService facturaService;

    public ClienteWebController(IClienteService clienteService, IFacturaService facturaService) {
        this.clienteService = clienteService;
        this.facturaService = facturaService;
    }

    @GetMapping({"", "/", "/listar"})
    public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
        Pageable pageRequest = PageRequest.of(page, 4);
        Page<Cliente> clientes = clienteService.findAll(pageRequest);

        model.addAttribute("titulo", "Listado de Clientes");
        model.addAttribute("clientes", clientes);
        model.addAttribute("page", new PageRender<>("/listar", clientes));
        return "listar";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Cliente cliente = clienteService.findById(id);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
            return "redirect:/listar";
        }

        model.addAttribute("cliente", cliente);
        model.addAttribute("facturas", facturaService.findAllByCliente(id));
        model.addAttribute("titulo", "Detalle del cliente: " + cliente.getNombre() + " " + cliente.getApellido());
        return "ver";
    }

    @GetMapping("/form")
    public String crear(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Formulario de Cliente");
        return "form";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        Cliente cliente = clienteService.findById(id);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe!");
            return "redirect:/listar";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");
        return "form";
    }

    @PostMapping("/form")
    public String guardar(@Valid Cliente cliente, BindingResult result, Model model, RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Cliente");
            return "form";
        }
        clienteService.save(cliente);
        flash.addFlashAttribute("success", "Cliente guardado con éxito!");
        return "redirect:/listar";
    }
}
