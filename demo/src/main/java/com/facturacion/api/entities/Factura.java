package com.facturacion.api.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "facturas")
public class Factura implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String descripcion;

    private String observacion;

    @Column(name = "create_at")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate createAt;

    @NotNull
    @ManyToOne
    private Cliente cliente;

    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("factura")
    private List<ItemFactura> lista;

    public Factura() {
        this.lista = new ArrayList<ItemFactura>();
    }

    public Factura(Long id, String descripcion, String observacion, LocalDate createAt, Cliente cliente, List<ItemFactura> lista) {
        this.id = id;
        this.descripcion = descripcion;
        this.observacion = observacion;
        this.createAt = createAt;
        this.cliente = cliente;
        this.lista = lista != null ? lista : new ArrayList<ItemFactura>();
    }

    @PrePersist
    public void prePersist() {
        if (createAt == null) {
            createAt = LocalDate.now();
        }
    }

    public Double getTotal() {
        return lista.stream().mapToDouble(ItemFactura::getMonto).sum();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public LocalDate getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public List<ItemFactura> getLista() {
        return lista;
    }

    public void setLista(List<ItemFactura> lista) {
        this.lista = lista;
    }
}

