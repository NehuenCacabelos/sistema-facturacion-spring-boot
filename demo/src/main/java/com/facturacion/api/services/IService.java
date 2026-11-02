package com.facturacion.api.services;

import java.util.List;

public interface IService<T> {

    T save(T t);

    List<T> findAll();

    T findById(Long id);
}

