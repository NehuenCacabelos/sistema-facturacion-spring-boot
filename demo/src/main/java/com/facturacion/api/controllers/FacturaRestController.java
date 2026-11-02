package com.facturacion.api.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.facturacion.api.entities.Factura;
import com.facturacion.api.services.IFacturaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/facturas")
public class FacturaRestController {

    private final IFacturaService facturaService;

    public FacturaRestController(IFacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    public List<Factura> listar() {
        return facturaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Factura> buscarPorId(@PathVariable Long id) {
        Factura factura = facturaService.findById(id);
        if (factura == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(factura);
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Factura> listarPorCliente(@PathVariable Long clienteId) {
        return facturaService.findAllByCliente(clienteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Factura crear(@Valid @RequestBody Factura factura) {
        return facturaService.save(factura);
    }
}

