package com.teniscol.shoestore.services;

import com.teniscol.shoestore.DTO.CompraDetalleDTO;

import java.util.List;

public interface CompraServicesInterface {
    CompraDetalleDTO realizarCompra(int idTenis, int idCliente, int idSucursal, int talla, int cantidad);
    List<CompraDetalleDTO> obtenerCompras();
    boolean eliminarCompra(int idCompra);
}
