package com.facturacion.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.facturacion.api.entities.ItemFactura;
import com.facturacion.api.entities.Producto;
import com.facturacion.api.services.IClienteService;
import com.facturacion.api.services.IFacturaService;
import com.facturacion.api.services.IProductoService;

import jakarta.validation.Valid;

@Controller
public class FacturaController {

    @Autowired
    private IFacturaService facturaService;

    @Autowired
    private IClienteService clienteService;

    @Autowired
    private IProductoService productoService;

    @GetMapping({"/nuevafactura/{id}", "/factura/form/{id}"})
    public String nuevaFactura(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.findById(id);
        if (cliente == null) {
            return "redirect:/listar";
        }
        List<Producto> productos = productoService.findAll();

        Factura factura = new Factura();
        factura.setCliente(cliente);

        model.addAttribute("cliente", cliente);
        model.addAttribute("productos", productos);
        model.addAttribute("factura", factura);
        model.addAttribute("titulo", "Nueva Factura");
        return "formfactura";
    }

    @PostMapping({"/nuevafactura", "/factura/form"})
    public String guardarFactura(@Valid @ModelAttribute("factura") Factura form, BindingResult result,
            Model model, RedirectAttributes flash) {

        if (form.getLista() == null || form.getLista().size() == 0 || form.getLista().isEmpty()) {
            result.reject("items", "Debe agregar al menos un producto");
        }
        Cliente cliente = (form.getCliente() != null && form.getCliente().getId() != null)
                ? clienteService.findById(form.getCliente().getId()) : null;
        form.setCliente(cliente);
        if (result.hasErrors()) {
            model.addAttribute("cliente", cliente);
            model.addAttribute("productos", productoService.findAll());
            model.addAttribute("titulo", "Nueva Factura");
            return "formfactura";
        }
        if (form.getLista() != null) {
            for (ItemFactura it : form.getLista()) {
                Long prodId = it.getProducto() != null ? it.getProducto().getId() : null;
                if (prodId == null) {
                    continue; // para saltar las lineas que puedan estar vacias
                }
                Producto p = productoService.findById(prodId);
                if (p == null) {
                    continue;
                }
                it.setProducto(p);
                it.setFactura(form);
                if (it.getCantidad() <= 0) {
                    it.setCantidad(1);
                }
            }
        }

        facturaService.save(form);
        flash.addFlashAttribute("success", "Factura creada con éxito");
        // return "redirect:/ver/" + form.getCliente().getId();
        return "redirect:/listar";
    }

    @GetMapping({"/facturas/{id}", "/factura/ver/{id}"})
    public String verFactura(@PathVariable Long id, Model model) {
        Factura factura = facturaService.findById(id);
        model.addAttribute("titulo", "Detalle Factura");
        model.addAttribute("factura", factura);
        return "verfactura";
    }
}

