package com.mariegourmet.backend.service;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class CotizacionServiceTest {
    @Test
    public void calcularCoffeeBreakSinExtras() {
        CotizacionService service = new CotizacionService();
        Map<String, Object> datos = new HashMap<>();
        datos.put("tipoEvento", "Coffee break");
        datos.put("asistentes", 10);

        Map<String, Object> respuesta = service.calcular(datos);

        assertEquals(11500, respuesta.get("precioPorPersona"));
        assertEquals(115000, respuesta.get("costoBase"));
        assertEquals(115000, respuesta.get("totalEstimado"));
    }

    @Test
    public void calcularCoctelConExtras() {
        CotizacionService service = new CotizacionService();
        Map<String, Object> datos = new HashMap<>();
        datos.put("tipoEvento", "Cóctel");
        datos.put("asistentes", 10);
        datos.put("vegetarianos", 2);
        datos.put("botellasPisco", 1);
        datos.put("montajePremium", true);

        Map<String, Object> respuesta = service.calcular(datos);

        assertEquals(17500, respuesta.get("precioPorPersona"));
        assertEquals(175000, respuesta.get("costoBase"));
        assertEquals(87900, respuesta.get("costoExtras"));
        assertEquals(262900, respuesta.get("totalEstimado"));
    }

    @Test
    public void calcularDebeRetornarInsumos() {
        CotizacionService service = new CotizacionService();
        Map<String, Object> datos = new HashMap<>();
        datos.put("tipoEvento", "Brunch");
        datos.put("asistentes", 20);

        Map<String, Object> respuesta = service.calcular(datos);

        assertTrue(respuesta.containsKey("insumosEstimados"));
        assertEquals("Cotización calculada correctamente", respuesta.get("mensaje"));
    }
}
