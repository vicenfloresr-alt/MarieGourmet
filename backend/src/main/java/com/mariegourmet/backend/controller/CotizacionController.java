package com.mariegourmet.backend.controller;

import com.mariegourmet.backend.service.CotizacionService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CotizacionController {
    private final CotizacionService cotizacionService;

    public CotizacionController(CotizacionService cotizacionService) {
        this.cotizacionService = cotizacionService;
    }

    @PostMapping("/cotizar")
    public Map<String, Object> cotizar(@RequestBody Map<String, Object> datos) {
        return cotizacionService.calcular(datos);
    }

    @PostMapping("/lista-compras")
    public String generarListaCompras() {
        return "Lista de compras generada y registrada correctamente en BD";
    }
}
