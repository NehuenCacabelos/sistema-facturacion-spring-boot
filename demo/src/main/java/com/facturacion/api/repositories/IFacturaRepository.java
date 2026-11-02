package com.facturacion.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.facturacion.api.entities.Factura;

@Repository
public interface IFacturaRepository extends JpaRepository<Factura, Long> {

    List<Factura> findAllByClienteId(Long clienteId);
}

