package com.mariegourmet.backend.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CotizacionService {
    public Map<String, Object> calcular(Map<String, Object> datos) {
        int asistentes = numero(datos.get("asistentes"));
        if (asistentes <= 0) asistentes = 1;

        String tipoEvento = String.valueOf(datos.getOrDefault("tipoEvento", "Evento corporativo"));
        int precioPorPersona = precioBase(tipoEvento);
        int costoBase = asistentes * precioPorPersona;

        List<Map<String, Object>> extras = new ArrayList<>();
        int costoExtras = 0;

        costoExtras += extra(extras, "Menús vegetarianos especiales", numero(datos.get("vegetarianos")), 1500);
        costoExtras += extra(extras, "Menús veganos especiales", numero(datos.get("veganos")), 2000);
        costoExtras += extra(extras, "Preparaciones sin gluten", numero(datos.get("sinGluten")), 1800);
        costoExtras += extra(extras, "Preparaciones sin frutos secos", numero(datos.get("sinFrutosSecos")), 1000);
        costoExtras += extra(extras, "Opciones keto", numero(datos.get("keto")), 2200);
        costoExtras += extra(extras, "Menú niños", numero(datos.get("ninos")), 6500);
        costoExtras += extra(extras, "Pisco sour artesanal 1 litro", numero(datos.get("botellasPisco")), 19900);
        costoExtras += extra(extras, "Espumante brut 750 cc", numero(datos.get("botellasEspumante")), 14900);
        costoExtras += extra(extras, "Garzones adicionales", numero(datos.get("garzonesExtra")), 45000);

        if (Boolean.TRUE.equals(datos.get("montajePremium"))) {
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("nombre", "Montaje premium de mesa buffet");
            item.put("cantidad", 1);
            item.put("precioUnitario", 65000);
            item.put("subtotal", 65000);
            extras.add(item);
            costoExtras += 65000;
        }

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("mensaje", "Cotización calculada correctamente");
        respuesta.put("tipoEvento", tipoEvento);
        respuesta.put("asistentes", asistentes);
        respuesta.put("precioPorPersona", precioPorPersona);
        respuesta.put("costoBase", costoBase);
        respuesta.put("extras", extras);
        respuesta.put("costoExtras", costoExtras);
        respuesta.put("totalEstimado", costoBase + costoExtras);
        respuesta.put("insumosEstimados", insumos(asistentes, tipoEvento));
        respuesta.put("recomendacion", "Cotización referencial sujeta a revisión del administrador.");
        return respuesta;
    }

    private int precioBase(String tipoEvento) {
        String tipo = tipoEvento.toLowerCase();
        if (tipo.contains("coffee")) return 11500;
        if (tipo.contains("brunch")) return 14500;
        if (tipo.contains("cóctel") || tipo.contains("coctel")) return 17500;
        if (tipo.contains("almuerzo")) return 19500;
        return 16000;
    }

    private Map<String, Object> insumos(int asistentes, String tipoEvento) {
        Map<String, Object> insumos = new LinkedHashMap<>();
        String tipo = tipoEvento.toLowerCase();

        if (tipo.contains("coffee")) {
            insumos.put("Tapaditos / sándwiches", asistentes * 2 + " unidades");
            insumos.put("Dulces pequeños", asistentes * 2 + " unidades");
            insumos.put("Café", redondear(asistentes * 0.06) + " kg");
            insumos.put("Jugo natural", redondear(asistentes * 0.25) + " litros");
        } else if (tipo.contains("brunch")) {
            insumos.put("Tapaditos", asistentes * 2 + " unidades");
            insumos.put("Brochetas de fruta", asistentes + " unidades");
            insumos.put("Mini dulces", asistentes * 2 + " unidades");
            insumos.put("Jugos / bebidas", redondear(asistentes * 0.35) + " litros");
        } else if (tipo.contains("cóctel") || tipo.contains("coctel")) {
            insumos.put("Canapés / crostinis", asistentes * 4 + " unidades");
            insumos.put("Bocados calientes", asistentes * 2 + " unidades");
            insumos.put("Shots de postre", asistentes + " unidades");
            insumos.put("Bebidas", redondear(asistentes * 0.4) + " litros");
        } else {
            insumos.put("Plato principal", asistentes + " porciones");
            insumos.put("Ensaladas / acompañamientos", asistentes + " porciones");
            insumos.put("Postres", asistentes + " unidades");
            insumos.put("Bebidas", redondear(asistentes * 0.35) + " litros");
        }

        return insumos;
    }

    private int extra(List<Map<String, Object>> extras, String nombre, int cantidad, int precioUnitario) {
        if (cantidad <= 0) return 0;

        int subtotal = cantidad * precioUnitario;
        Map<String, Object> item = new LinkedHashMap<>();
        item.put("nombre", nombre);
        item.put("cantidad", cantidad);
        item.put("precioUnitario", precioUnitario);
        item.put("subtotal", subtotal);
        extras.add(item);
        return subtotal;
    }

    private int numero(Object valor) {
        if (valor == null) return 0;
        if (valor instanceof Number) return ((Number) valor).intValue();

        try {
            return Integer.parseInt(valor.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    private double redondear(double valor) {
        return Math.round(valor * 10.0) / 10.0;
    }
}
