package com.teniscol.shoestore.controllers;

import com.teniscol.shoestore.DTO.CompraDetalleDTO;
import com.teniscol.shoestore.repositoriesSQL.CompraRepositorySQL;
import com.teniscol.shoestore.services.CompraServices;
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
@RequestMapping("/restienda/compra")
@Tag(name = "Compra", description = "Operaciones de compra de tenis")
public class CompraController {

    private CompraRepositorySQL dao = new CompraRepositorySQL();

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

    @PostMapping("/comprar")
    public ResponseEntity<String> comprarTenis(
            @RequestParam int idTenis,
            @RequestParam int idCliente,
            @RequestParam int idSucursal,
            @RequestParam int talla,
            @RequestParam int cantidad) {

        if (talla < 34 || talla > 45) return ResponseEntity.badRequest().body("Talla inválida");
        if (cantidad <= 0) return ResponseEntity.badRequest().body("Cantidad inválida");

        boolean ok = dao.realizarCompra(idTenis, idCliente, idSucursal, talla, cantidad);

        if (!ok) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Stock insuficiente o datos inexistentes.");
        }

        return new ResponseEntity<>("Compra registrada con éxito", HttpStatus.CREATED);
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

    @GetMapping("/listar")
    public ResponseEntity<List<CompraDetalleDTO>> obtenerCompras() {

        List<CompraDetalleDTO> compras = dao.obtenerCompras();

        if (compras.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(compras, HttpStatus.OK);
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

        boolean eliminado = dao.eliminarCompra(idCompra);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Compra no encontrada");
        }

        return ResponseEntity.ok("Compra eliminada correctamente");
    }
}
