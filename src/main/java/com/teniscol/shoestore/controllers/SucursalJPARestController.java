package com.teniscol.shoestore.controllers;

import com.teniscol.shoestore.identidadesJPA.Sucursal;
import com.teniscol.shoestore.services.SucursalJPAServicesInterface;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sucursales")
@Tag(name = "Sucursal", description = "Operaciones relacionadas con las sucursales dde la tienda")
public class SucursalJPARestController implements SucursalJPAControllerAPI {

    private final SucursalJPAServicesInterface service;

    public SucursalJPARestController(SucursalJPAServicesInterface service) {
        this.service = service;
    }

    @Override
    @GetMapping("/listar")
    public List<Sucursal> obtenerSucursales() {
        return service.obtenerSucursales();
    }

    @Override
    @PostMapping("/agregar")
    public ResponseEntity<Sucursal> guardarSucursal(
            @RequestParam String nombre,
            @RequestParam String ciudad) {

        Sucursal sucursal = Sucursal.builder()
                .nombre(nombre)
                .ciudad(ciudad)
                .build();

        Sucursal creado = service.guardarSucursal(sucursal);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Override
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarSucursal(@RequestParam int id_sucursal) {

        boolean eliminado = service.eliminarSucursal(id_sucursal);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Sucursal no encontrada");
        }

        return ResponseEntity.ok("Sucursal eliminada");
    }

    @Override
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarSucursal(
            @RequestParam int id_sucursal,
            @RequestParam String nombre,
            @RequestParam String ciudad) {

        boolean ok = service.actualizarSucursal(id_sucursal, nombre, ciudad);

        if (!ok) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Sucursal no encontrada");
        }

        return ResponseEntity.ok("Sucursal actualizada correctamente");
    }
}
