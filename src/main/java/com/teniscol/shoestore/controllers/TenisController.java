package com.teniscol.shoestore.controllers;

import com.teniscol.shoestore.identidadesSQL.Tenis;
import com.teniscol.shoestore.services.TenisServicesInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restienda/tenis")
public class TenisController implements TenisControllerAPI {

    private final TenisServicesInterface service;

    public TenisController(TenisServicesInterface service) {
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

    @Override
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarTenis(
            @RequestParam int idTenis,
            @RequestParam double precio,
            @RequestParam int cantidad) {

        if (precio <= 0) return ResponseEntity.badRequest().body("Precio inválido");
        if (cantidad < 0) return ResponseEntity.badRequest().body("Cantidad inválida");

        boolean ok = service.actualizarStock(idTenis, precio, cantidad);

        if (!ok) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }

        return ResponseEntity.ok("Actualizado correctamente");
    }

    @Override
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarTenis(@RequestParam int id) {

        boolean eliminado = service.eliminarTenis(id);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No encontrado");
        }

        return ResponseEntity.ok("Eliminado correctamente");
    }

    @Override
    @PostMapping("/agregar")
    public ResponseEntity<String> agregarTenis(
            @RequestParam String marca,
            @RequestParam String modelo,
            @RequestParam double precio,
            @RequestParam int cantidad) {

        Tenis tenis = Tenis.builder()
                .marca(marca)
                .modelo(modelo)
                .precio(precio)
                .stock(cantidad)
                .build();

        Tenis creado = service.insertarTenis(tenis);

        return new ResponseEntity<>(
                "ID generado: " + creado.getIdTenis(),
                HttpStatus.CREATED
        );
    }
}
    // Link del Swagger
    //http://localhost:8010/proyecto/swagger-ui/index.html#/


