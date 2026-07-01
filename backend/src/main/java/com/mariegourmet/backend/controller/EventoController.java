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
        if (evento.getEstado() == null || evento.getEstado().isEmpty()) {
            evento.setEstado("pendiente");
        }
        return eventoRepository.save(evento);
    }

    @PutMapping("/{id}")
    public Evento editar(@PathVariable Long id, @RequestBody Evento datos) {
        Evento evento = eventoRepository.findById(id).orElse(null);
        if (evento == null) return null;

        evento.setNombreEvento(datos.getNombreEvento());
        evento.setTipoEvento(datos.getTipoEvento());
        evento.setFecha(datos.getFecha());
        evento.setAsistentes(datos.getAsistentes());
        evento.setEstado(datos.getEstado());
        evento.setObservaciones(datos.getObservaciones());

        return eventoRepository.save(evento);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        eventoRepository.deleteById(id);
        return "Evento eliminado correctamente";
    }
}
