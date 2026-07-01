package com.mariegourmet.backend.service;

import org.springframework.stereotype.Service;
import java.util.Map;

@Service
public class CotizacionService {
    private final MinutaService minutaService;

    public CotizacionService(MinutaService minutaService) {
        this.minutaService = minutaService;
    }

    public Map<String, Object> calcular(Map<String, Object> datos) {
        return minutaService.generar(datos);
    }
}
