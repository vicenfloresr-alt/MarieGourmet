package com.mariegourmet.backend.service;

import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MinutaService {

    public Map<String, Object> generar(Map<String, Object> datos) {
        int asistentes = numero(datos, "asistentes");
        if (asistentes <= 0) asistentes = 1;

        int menuCarne = numero(datos, "menuCarne");
        int menuPollo = numero(datos, "menuPollo");
        int menuVegetariano = numero(datos, "menuVegetariano");
        int menuVegano = numero(datos, "menuVegano");
        int menuNinos = numero(datos, "menuNinos");

        int canapes = numero(datos, "canapes");
        int crostinis = numero(datos, "crostinis");
        int tapaditos = numero(datos, "tapaditos");
        int rollitosSalmon = numero(datos, "rollitosSalmon");
        int rollitosJamon = numero(datos, "rollitosJamon");
        int brochetasGourmet = numero(datos, "brochetasGourmet");

        int empanaditas = numero(datos, "empanaditas");
        int pizzetas = numero(datos, "pizzetas");
        int miniQuiche = numero(datos, "miniQuiche");
        int brochetasPollo = numero(datos, "brochetasPollo");

        int shotsPostre = numero(datos, "shotsPostre");
        int brownies = numero(datos, "brownies");
        int alfajores = numero(datos, "alfajores");
        int brochetasFruta = numero(datos, "brochetasFruta");

        int jugoLitros = numero(datos, "jugoLitros");
        int aguas = numero(datos, "aguas");
        int piscoSour = numero(datos, "piscoSour");
        int espumante = numero(datos, "espumante");

        int adultos = Math.max(0, asistentes - menuNinos);
        int totalBocados = canapes + crostinis + tapaditos + rollitosSalmon + rollitosJamon + brochetasGourmet
                + empanaditas + pizzetas + miniQuiche + brochetasPollo
                + shotsPostre + brownies + alfajores + brochetasFruta;

        Map<String, Object> productosSeleccionados = new LinkedHashMap<>();
        productosSeleccionados.put("Canapés gourmet", canapes + " unidades");
        productosSeleccionados.put("Crostinis gourmet", crostinis + " unidades");
        productosSeleccionados.put("Tapaditos mini", tapaditos + " unidades");
        productosSeleccionados.put("Rollitos de salmón", rollitosSalmon + " unidades");
        productosSeleccionados.put("Rollitos de jamón serrano", rollitosJamon + " unidades");
        productosSeleccionados.put("Brochetas gourmet", brochetasGourmet + " unidades");
        productosSeleccionados.put("Empanaditas de cóctel", empanaditas + " unidades");
        productosSeleccionados.put("Pizzetas napolitanas", pizzetas + " unidades");
        productosSeleccionados.put("Mini quiche", miniQuiche + " unidades");
        productosSeleccionados.put("Brochetas de pollo", brochetasPollo + " unidades");
        productosSeleccionados.put("Shots de postres", shotsPostre + " unidades");
        productosSeleccionados.put("Mini brownies", brownies + " unidades");
        productosSeleccionados.put("Mini alfajores", alfajores + " unidades");
        productosSeleccionados.put("Brochetas de frutas", brochetasFruta + " unidades");

        Map<String, Object> insumos = new LinkedHashMap<>();
        insumos.put("Carne de vacuno", redondear(menuCarne * 0.20) + " kg");
        insumos.put("Pollo", redondear((menuPollo * 0.18) + (brochetasPollo * 0.06)) + " kg");
        insumos.put("Verduras menú vegetariano", redondear(menuVegetariano * 0.25) + " kg");
        insumos.put("Proteína vegetal o legumbres", redondear(menuVegano * 0.16) + " kg");
        insumos.put("Acompañamientos", redondear((menuCarne + menuPollo + menuVegetariano + menuVegano) * 0.15) + " kg");
        insumos.put("Ensaladas", redondear(adultos * 0.12) + " kg");
        insumos.put("Pan o rolls", (adultos * 2 + tapaditos) + " unidades");
        insumos.put("Queso / rellenos fríos", redondear((canapes + crostinis + tapaditos) * 0.025) + " kg");
        insumos.put("Salmón ahumado", redondear(rollitosSalmon * 0.03) + " kg");
        insumos.put("Jamón serrano", redondear(rollitosJamon * 0.025) + " kg");
        insumos.put("Masa bocados calientes", redondear((empanaditas + pizzetas + miniQuiche) * 0.04) + " kg");
        insumos.put("Fruta fresca", redondear(brochetasFruta * 0.10) + " kg");

        Map<String, Object> menuInfantil = new LinkedHashMap<>();
        menuInfantil.put("Menús niños", menuNinos + " unidades");
        menuInfantil.put("Jugos en caja", (menuNinos * 3) + " unidades");
        menuInfantil.put("Mini plato o mini sándwich", menuNinos + " unidades");
        menuInfantil.put("Snack dulce niños", menuNinos + " unidades");

        Map<String, Object> bebidas = new LinkedHashMap<>();
        bebidas.put("Jugo natural", jugoLitros + " litros");
        bebidas.put("Agua mineral individual", aguas + " unidades");
        bebidas.put("Jugo en caja niños", (menuNinos * 3) + " unidades");
        bebidas.put("Café estimado", redondear(adultos * 0.05) + " kg");
        bebidas.put("Pisco sour artesanal", piscoSour + " botella(s)");
        bebidas.put("Espumante", espumante + " botella(s)");

        Map<String, Object> personal = new LinkedHashMap<>();
        personal.put("Chefs requeridos", calcularChefs(asistentes, totalBocados));
        personal.put("Ayudantes de cocina", Math.max(1, dividirHaciaArriba(asistentes, 60)));
        personal.put("Mozos requeridos", Math.max(1, dividirHaciaArriba(asistentes, 25)));
        personal.put("Personal de montaje", Math.max(1, dividirHaciaArriba(asistentes, 50)));

        List<String> tareas = new ArrayList<>();
        tareas.add("Confirmar asistentes y menús especiales 48 horas antes.");
        tareas.add("Comprar insumos según kilos calculados.");
        tareas.add("Separar producción de fríos, calientes, dulces y bebidas.");
        tareas.add("Etiquetar menús vegetarianos, veganos y niños.");
        tareas.add("Asignar mozos por sector del evento.");
        tareas.add("Revisar montaje, vajilla, reposición y retiro final.");

        int costoMenus = menuCarne * 18500 + menuPollo * 16500 + menuVegetariano * 14500 + menuVegano * 15500 + menuNinos * 7500;
        int costoProductos = canapes * 760 + crostinis * 1260 + tapaditos * 1900 + rollitosSalmon * 1575 + rollitosJamon * 1490 + brochetasGourmet * 2325
                + empanaditas * 990 + pizzetas * 1160 + miniQuiche * 1120 + brochetasPollo * 2325
                + shotsPostre * 1900 + brownies * 995 + alfajores * 995 + brochetasFruta * 1625
                + jugoLitros * 6900 + aguas * 1200 + piscoSour * 19900 + espumante * 14900;

        Map<String, Object> respuesta = new LinkedHashMap<>();
        respuesta.put("mensaje", "Minuta operativa generada correctamente");
        respuesta.put("asistentes", asistentes);
        respuesta.put("adultos", adultos);
        respuesta.put("productosSeleccionados", productosSeleccionados);
        respuesta.put("insumos", insumos);
        respuesta.put("menuInfantil", menuInfantil);
        respuesta.put("bebidas", bebidas);
        respuesta.put("personal", personal);
        respuesta.put("tareas", tareas);
        respuesta.put("costoMenus", costoMenus);
        respuesta.put("costoProductos", costoProductos);
        respuesta.put("totalEstimado", costoMenus + costoProductos);
        respuesta.put("nota", "Cálculo aproximado para planificación interna de cocina, compras y servicio.");

        return respuesta;
    }

    private int calcularChefs(int asistentes, int totalBocados) {
        int chefs = Math.max(1, dividirHaciaArriba(asistentes, 50));
        if (totalBocados > 150) chefs++;
        return chefs;
    }

    private int numero(Map<String, Object> datos, String clave) {
        Object valor = datos.get(clave);
        if (valor == null) return 0;
        if (valor instanceof Number) return ((Number) valor).intValue();
        try { return Integer.parseInt(valor.toString()); }
        catch (Exception e) { return 0; }
    }

    private int dividirHaciaArriba(int numero, int divisor) {
        return (int) Math.ceil(numero / (double) divisor);
    }

    private double redondear(double valor) {
        return Math.round(valor * 10.0) / 10.0;
    }
}
