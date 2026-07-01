package com.mariegourmet.backend.service;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class MinutaServiceTest {
    @Test
    public void generaMinutaConProductosSeleccionados() {
        MinutaService service = new MinutaService();

        Map<String, Object> datos = new HashMap<>();
        datos.put("asistentes", 80);
        datos.put("menuCarne", 40);
        datos.put("menuPollo", 20);
        datos.put("menuVegetariano", 10);
        datos.put("menuNinos", 10);
        datos.put("canapes", 50);
        datos.put("crostinis", 30);
        datos.put("empanaditas", 40);
        datos.put("shotsPostre", 80);
        datos.put("jugoLitros", 20);
        datos.put("piscoSour", 3);

        Map<String, Object> respuesta = service.generar(datos);

        assertEquals("Minuta operativa generada correctamente", respuesta.get("mensaje"));
        assertEquals(80, respuesta.get("asistentes"));
        assertNotNull(respuesta.get("productosSeleccionados"));
        assertNotNull(respuesta.get("insumos"));
        assertNotNull(respuesta.get("personal"));
        assertTrue((Integer) respuesta.get("totalEstimado") > 0);
    }

    @Test
    public void generaMinutaAunqueNoLleguenDatos() {
        MinutaService service = new MinutaService();
        Map<String, Object> respuesta = service.generar(new HashMap<>());

        assertEquals("Minuta operativa generada correctamente", respuesta.get("mensaje"));
        assertEquals(1, respuesta.get("asistentes"));
    }
}
