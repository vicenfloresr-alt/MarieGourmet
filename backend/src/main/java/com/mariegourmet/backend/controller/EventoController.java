package com.mariegourmet.backend.controller;

import com.mariegourmet.backend.model.Evento;
import com.mariegourmet.backend.repository.EventoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = "*")
public class EventoController {
    private final EventoRepository eventoRepository;

    public EventoController(EventoRepository eventoRepository) {
        this.eventoRepository = eventoRepository;
    }

    @GetMapping
    public List<Evento> listar() {
        return eventoRepository.findAllByOrderByFechaAsc();
    }

    @PostMapping
    public Evento crear(@RequestBody Evento evento) {
        if (evento.estado == null || evento.estado.isEmpty()) {
            evento.estado = "pendiente";
        }
        return eventoRepository.save(evento);
    }

    @PutMapping("/{id}")
    public Evento editar(@PathVariable Long id, @RequestBody Evento datos) {
        Evento evento = eventoRepository.findById(id).orElse(null);
        if (evento == null) return null;

        evento.nombreEvento = datos.nombreEvento;
        evento.tipoEvento = datos.tipoEvento;
        evento.fecha = datos.fecha;
        evento.asistentes = datos.asistentes;
        evento.estado = datos.estado;
        evento.observaciones = datos.observaciones;
        return eventoRepository.save(evento);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        eventoRepository.deleteById(id);
        return "Evento eliminado correctamente";
    }
}
