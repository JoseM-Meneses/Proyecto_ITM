package com.teniscol.shoestore.repositoriesSQL;

import com.teniscol.shoestore.DTO.CompraDetalleDTO;

import java.util.List;

public interface CompraRepositoryInterface {
    CompraDetalleDTO realizarCompra(int idTenis, int idCliente, int idSucursal, int talla, int cantidad);
    List<CompraDetalleDTO> obtenerCompras();
    boolean eliminarCompra(int idCompra);
}
