package com.mariegourmet.backend.controller;

import com.mariegourmet.backend.model.Producto;
import com.mariegourmet.backend.repository.ProductoRepository;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    private final ProductoRepository productoRepository;

    public ProductoController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @GetMapping
    public List<Producto> listar() {
        return productoRepository.findByActivoTrueOrderByCategoriaAscNombreAsc();
    }

    @PostMapping
    public Producto crear(@RequestBody Producto producto) {
        producto.setActivo(true);
        return productoRepository.save(producto);
    }

    @PutMapping("/{id}")
    public Producto editar(@PathVariable Long id, @RequestBody Producto datos) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto == null) return null;

        producto.setNombre(datos.getNombre());
        producto.setCategoria(datos.getCategoria());
        producto.setDescripcion(datos.getDescripcion());
        producto.setPrecio(datos.getPrecio());

        return productoRepository.save(producto);
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        Producto producto = productoRepository.findById(id).orElse(null);
        if (producto == null) return "Producto no encontrado";

        producto.setActivo(false);
        productoRepository.save(producto);
        return "Producto eliminado correctamente";
    }
}
