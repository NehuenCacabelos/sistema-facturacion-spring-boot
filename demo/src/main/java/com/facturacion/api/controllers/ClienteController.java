package com.facturacion.api.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.facturacion.api.entities.Cliente;
import com.facturacion.api.entities.Factura;
import com.facturacion.api.services.IClienteService;
import com.facturacion.api.services.IFacturaService;

import jakarta.validation.Valid;

@Controller
public class ClienteController {

    private final IClienteService clienteService;
    private final IFacturaService facturaService;

    public ClienteController(IClienteService clienteService, IFacturaService facturaService) {
        this.clienteService = clienteService;
        this.facturaService = facturaService;
    }

    @GetMapping({"", "/", "/listar"})
    public String listar(Model model) {
        model.addAttribute("titulo", "Clientes");
        model.addAttribute("lista", clienteService.findAll());
        return "listar";
    }

    @GetMapping("/form")
    public String form(Model model) {
        model.addAttribute("titulo", "Cargar cliente");
        model.addAttribute("cliente", new Cliente());
        return "form";
    }

    @GetMapping("/ver/{id}")
    public String ver(@PathVariable(value = "id") long id, Map<String, Object> model) {
        Cliente cliente = clienteService.findById(id);
        List<Factura> facturas = facturaService.findAllByCliente(id);
        model.put("cliente", cliente);
        model.put("facturas", facturas);
        model.put("titulo", "Vista de cliente");
        return "ver";
    }

    @PostMapping("/form")
    public String guardar(@Valid @ModelAttribute Cliente cliente, BindingResult resultado, RedirectAttributes flash) {
        if (resultado.hasErrors()) {
            flash.addFlashAttribute("error", "Revisa los campos del formulario");
            return "form";
        }
        clienteService.save(cliente);
        flash.addFlashAttribute("success", "Cliente guardado con exito");
        return "redirect:/listar";
    }

    @GetMapping("/form/{id}")
    public String editar(@PathVariable(value = "id") long id, Model model, RedirectAttributes flash) {
        Cliente cliente = clienteService.findById(id);
        if (cliente == null) {
            flash.addFlashAttribute("error", "El cliente no existe!");
            return "redirect:/listar";
        }
        model.addAttribute("titulo", "Cargar cliente");
        model.addAttribute("cliente", cliente);
        return "form";
    }
}
