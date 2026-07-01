package com.mariegourmet.backend.repository;

import com.mariegourmet.backend.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findAllByOrderByFechaAsc();
}
