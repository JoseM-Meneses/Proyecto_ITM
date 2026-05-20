package com.teniscol.shoestore.controllers;

import com.teniscol.shoestore.identidadesJPA.Tenis;
import com.teniscol.shoestore.services.TenisJPAServicesInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenis")
@Tag(name = "Tenis", description = "Operaciones del inventario de tenis")
public class TenisJPARestController implements TenisJPAControllerAPI {

    private final TenisJPAServicesInterface service;

    public TenisJPARestController(TenisJPAServicesInterface service) {
        this.service = service;
    }

    @Operation(
            summary = "Ver inventario",
            description = "Permite ver inventario de la tienda")

    @Override
    @GetMapping("/inventario")
    public List<Tenis> obtenerTenis() {
        return service.obtenerTenis();
    }

    @Operation(
            summary = "Ver cantidad",
            description = "Permite ver la cantidad total de tenis en la tienda")

    @Override
    @GetMapping("/total")
    public int totalTenis() {
        return service.totalStock();
    }

    @Operation(
            summary = "Actualizar inventario",
            description = "Permite actualizar inventario de cada marca de tenis en la tienda")

    @Override
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

    @Operation(
            summary = "Eliminar tenis",
            description = "Permite eliminar tenis del inventario de la tienda")

    @Override
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarTenis(@RequestParam int idTenis) {

        boolean eliminado = service.eliminarTenis(idTenis);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }

        return ResponseEntity.ok("Eliminado correctamente");
    }

    @Operation(
            summary = "Agregar tenis",
            description = "Permite agregar un nuevo tenis al inventario de la tienda")

    @Override
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


