package com.facturacion.api.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.facturacion.api.entities.Cliente;

public interface IClienteService extends IService<Cliente> {

    Page<Cliente> findAll(Pageable page);
}

