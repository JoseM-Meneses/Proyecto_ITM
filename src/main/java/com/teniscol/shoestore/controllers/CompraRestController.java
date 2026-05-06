package com.teniscol.shoestore.controllers;

import com.teniscol.shoestore.DTO.CompraDetalleDTO;
import com.teniscol.shoestore.repositoriesSQL.CompraRepositorySQL;
import com.teniscol.shoestore.services.CompraServices;
import com.teniscol.shoestore.services.CompraServicesInterface;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compras")
@Tag(name = "Compra", description = "Operaciones de compra de tenis")
public class CompraRestController implements CompraControllerAPI{

    private final CompraServicesInterface service;

    public CompraRestController(CompraServicesInterface service) {
        this.service = service;
    }

    @Operation(
            summary = "Realizar compra",
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
    @Override
    @PostMapping("/comprar")
    public ResponseEntity<String> comprarTenis(
            @RequestParam int idTenis,
            @RequestParam int idCliente,
            @RequestParam int idSucursal,
            @RequestParam int talla,
            @RequestParam int cantidad) {

        boolean ok = service.realizarCompra(idTenis, idCliente, idSucursal, talla, cantidad);

        if (!ok) {
            return ResponseEntity.badRequest()
                    .body("Error: Stock insuficiente o datos inexistentes.");
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Compra registrada con éxito");
    }

    @Operation(
            summary = "Consultar compras",
            description = "Permite ver todas las compras realizadas",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Consulta exitosa",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = CompraServices.class))
                            }),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No hay compras registradas"
                    )
            })

    @Override
    @GetMapping("/listar")
    public ResponseEntity<List<CompraDetalleDTO>> obtenerCompras() {

        List<CompraDetalleDTO> compras = service.obtenerCompras();

        if (compras.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(compras);
    }

    @Operation(
            summary = "Eliminar compra",
            description = "Permite eliminar una compra junto con su detalle",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Compra eliminada correctamente",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = String.class))
                            }),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Compra no encontrada"
                    )
            })

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarCompra(@RequestParam int idCompra) {

        boolean eliminado = service.eliminarCompra(idCompra);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Compra no encontrada");
        }

        return ResponseEntity.ok("Compra eliminada correctamente");
    }
}
