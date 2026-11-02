package com.facturacion.api.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
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
    public String guardar(@Valid @ModelAttribute Cliente cliente, BindingResult resultado, RedirectAttributes flash,
                          @RequestParam("file") MultipartFile foto) {
        if (resultado.hasErrors()) {
            System.out.println(resultado.getErrorCount());
            flash.addFlashAttribute("error", "Revisa los campos del formulario");
            return "form";
        }

        if (!foto.isEmpty()) {
            try {
                String uniqueFileName = UUID.randomUUID().toString() + "_" + foto.getOriginalFilename();
                Path rootPath = Paths.get("uploads").resolve(uniqueFileName);
                Path rootAbsolutePath = rootPath.toAbsolutePath();
                Files.copy(foto.getInputStream(), rootAbsolutePath);
                cliente.setFoto(uniqueFileName);
                flash.addFlashAttribute("info", "Foto subida con éxito.");
            } catch (IOException e) {
                flash.addFlashAttribute("error", "No se pudo subir la foto. Intentá de nuevo.");
            }
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
