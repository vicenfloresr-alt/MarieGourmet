package com.mariegourmet.backend.controller;

import com.mariegourmet.backend.service.CotizacionService;
import com.mariegourmet.backend.service.MinutaService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class CotizacionController {
    private final CotizacionService cotizacionService;
    private final MinutaService minutaService;

    public CotizacionController(CotizacionService cotizacionService, MinutaService minutaService) {
        this.cotizacionService = cotizacionService;
        this.minutaService = minutaService;
    }

    @PostMapping("/cotizar")
    public Map<String, Object> cotizar(@RequestBody Map<String, Object> datos) {
        return cotizacionService.calcular(datos);
    }

    @PostMapping("/minuta")
    public Map<String, Object> minuta(@RequestBody Map<String, Object> datos) {
        return minutaService.generar(datos);
    }

    @PostMapping("/lista-compras")
    public String listaCompras() {
        return "Lista de compras generada desde la minuta operativa";
    }
}
