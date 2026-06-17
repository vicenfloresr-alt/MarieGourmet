package com.mariegourmet.backend.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "evento")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evento")
    public Long idEvento;

    @Column(name = "nombre_evento")
    public String nombreEvento;

    @Column(name = "tipo_evento")
    public String tipoEvento;

    public LocalDate fecha;
    public Integer asistentes;
    public String estado;

    @Column(columnDefinition = "TEXT")
    public String observaciones;
}
