package com.mariegourmet.backend.service;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class CotizacionServiceTest {
    @Test
    public void cotizacionUsaMinutaService() {
        MinutaService minutaService = new MinutaService();
        CotizacionService cotizacionService = new CotizacionService(minutaService);

        Map<String, Object> datos = new HashMap<>();
        datos.put("asistentes", 30);
        datos.put("menuCarne", 10);
        datos.put("menuPollo", 10);
        datos.put("canapes", 30);

        Map<String, Object> respuesta = cotizacionService.calcular(datos);

        assertEquals("Minuta operativa generada correctamente", respuesta.get("mensaje"));
        assertNotNull(respuesta.get("totalEstimado"));
    }
}
