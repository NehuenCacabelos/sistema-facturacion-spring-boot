package com.facturacion.api.services;

import java.util.List;

import com.facturacion.api.entities.Factura;

public interface IFacturaService extends IService<Factura> {

    List<Factura> findAllByCliente(Long id);
}

