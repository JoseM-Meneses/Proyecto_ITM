package com.teniscol.shoestore.controllers;

import com.teniscol.shoestore.DTO.CompraDetalleDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CompraControllerAPI {
    ResponseEntity<String> comprarTenis(int idTenis, int idCliente, int idSucursal, int talla, int cantidad);

    ResponseEntity<List<CompraDetalleDTO>> obtenerCompras();

    ResponseEntity<String> eliminarCompra(int idCompra);
}
