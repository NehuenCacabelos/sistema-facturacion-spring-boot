package com.facturacion.api.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.facturacion.api.entities.Factura;
import com.facturacion.api.repositories.IFacturaRepository;

@Service
public class FacturaService implements IFacturaService {

    private final IFacturaRepository facturaRepository;

    public FacturaService(IFacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    @Override
    @Transactional
    public Factura save(Factura t) {
        if (t.getLista() != null) {
            t.getLista().forEach(item -> item.setFactura(t));
        }
        return facturaRepository.save(t);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Factura> findAll() {
        return facturaRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Factura findById(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Factura> findAllByCliente(Long id) {
        return facturaRepository.findAllByClienteId(id);
    }
}

