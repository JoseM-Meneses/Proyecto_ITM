package com.teniscol.shoestore.controllers;

import com.teniscol.shoestore.dao.TenisDao;
import com.teniscol.shoestore.services.Tenis;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/restienda")
public class ServiceRest {

    private TenisDao dao = new TenisDao();

    @Operation(
            tags = {"EndPoints"},
            summary = "Inventario",
            description = "Permite ver el Inventario de la tienda",
            responses = {
                @ApiResponse(
                    responseCode = "200",
                    description = "Se ejecuta correctamente el servicio",
                    content = {
                        @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Tenis.class))
                    })
    })

    @GetMapping("/inventario")
    public List<Tenis> obtenerInventario() {
        return dao.obtenerTodos();
    }

    @Operation(
            tags = {"EndPoints"},
            summary = "Total",
            description = "Permite ver el total de tenis disponibles en la tienda",
            responses = {
                @ApiResponse(
                    responseCode = "200",
                    description = "Se ejecuta correctamente el servicio",
                        content = {
                                @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        schema = @Schema(implementation = Integer.class))
                        })
    })

    @GetMapping("/total")
    public int totalTenis() {
        return dao.totalStock();
    }

    @Operation(
            tags = {"EndPoints"},
            summary = "Comprar",
            description = "Permite comprar tenis del inventario de la tienda",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Se ha creado correctamente el servicio",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = String.class))
                            })
            })
    @PostMapping("/comprar")
    public ResponseEntity<String> comprar(
            @RequestParam String marca,
            @RequestParam String modelo,
            @RequestParam int talla,
            @RequestParam int cantidad) {

        if (talla < 35 || talla > 43) {
            return ResponseEntity.badRequest().body("Talla inválida");
        }

        boolean ok = dao.realizarCompra(marca, modelo, talla, cantidad);

        if (!ok) {
            return ResponseEntity.badRequest()
                    .body("No hay stock o no existe");
        }

        return new ResponseEntity<>(
                "Compra realizada correctamente ",
                HttpStatus.CREATED
        );
    }

    @Operation(
            tags = {"EndPoints"},
            summary = "Actualizar",
            description = "Permite actualizar el stock de tenis de la tienda",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se ejecuta correctamente el servicio",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = String.class))
                            })
            })
    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarStock(
            @RequestParam String marca,
            @RequestParam String modelo,
            @RequestParam Float precio,
            @RequestParam int cantidad) {

        dao.actualizarStock(marca, modelo, precio, cantidad);

        return new ResponseEntity<>(
                "Stock actualizado de " + marca,
                HttpStatus.OK
        );
    }

    @Operation(
            tags = {"EndPoints"},
            summary = "Eliminar",
            description = "Permite eliminar alguna marca de la tienda",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Se ejecuta correctamente el servicio",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = String.class))
                            })
            })
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarMarca(@RequestParam String marca) {

        dao.eliminar(marca);

        return new ResponseEntity<>(
                "Marca eliminada: " + marca,
                HttpStatus.OK
        );
    }

    // Link del Swagger
    //http://localhost:8010/proyecto/swagger-ui/index.html#/
}

