package com.mariegourmet.backend.model;

import jakarta.persistence.*;

@Entity
@Table(name = "producto")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    public Long idProducto;

    public String nombre;
    public String categoria;
    public String descripcion;
    public Integer precio;
    public Boolean activo = true;
}
