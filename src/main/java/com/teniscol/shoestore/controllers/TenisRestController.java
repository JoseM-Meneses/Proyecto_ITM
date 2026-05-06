package com.teniscol.shoestore.controllers;

import com.teniscol.shoestore.identidadesSQL.Tenis;
import com.teniscol.shoestore.services.TenisServicesInterface;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenis")
@Tag(name = "Tenis", description = "Operaciones del inventario de tenis")
public class TenisRestController implements TenisControllerAPI {

    private final TenisServicesInterface service;

    public TenisRestController(TenisServicesInterface service) {
        this.service = service;
    }

    @Override
    @GetMapping("/inventario")
    public List<Tenis> obtenerTenis() {
        return service.obtenerTenis();
    }

    @Override
    @GetMapping("/total")
    public int totalTenis() {
        return service.totalStock();
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarTenis(
            @RequestParam int id_tenis,
            @RequestParam double precio,
            @RequestParam int stock) {

        boolean ok = service.actualizarStock(id_tenis, precio, stock);

        if (!ok) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }

        return ResponseEntity.ok("Actualizado correctamente");
    }

    @Override
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarTenis(@RequestParam int idTenis) {

        boolean eliminado = service.eliminarTenis(idTenis);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }

        return ResponseEntity.ok("Eliminado correctamente");
    }

    @PostMapping("/agregar")
    public ResponseEntity<String> agregarTenis(
            @RequestParam String marca,
            @RequestParam String modelo,
            @RequestParam double precio,
            @RequestParam int stock) {

        Tenis tenis = Tenis.builder()
                .marca(marca)
                .modelo(modelo)
                .precio(precio)
                .stock(stock)
                .build();

        Tenis creado = service.insertarTenis(tenis);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Tenis creado con ID: " + creado.getIdTenis());
    }
}
    // Link del Swagger
    //http://localhost:8010/proyecto/swagger-ui/index.html#/


