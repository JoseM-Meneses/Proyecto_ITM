package com.teniscol.shoestore.services;

import com.teniscol.shoestore.DTO.CompraDetalleDTO;
import com.teniscol.shoestore.repositoriesSQL.CompraRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompraServices implements CompraServicesInterface {

    private final CompraRepositoryInterface repository;

    public CompraServices(CompraRepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public boolean realizarCompra(int idTenis, int idCliente, int idSucursal, int talla, int cantidad) {

        if (talla < 34 || talla > 45) {
            throw new IllegalArgumentException("Talla inválida");
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException("Cantidad inválida");
        }

        return repository.realizarCompra(idTenis, idCliente, idSucursal, talla, cantidad);
    }

    @Override
    public List<CompraDetalleDTO> obtenerCompras() {
        return repository.obtenerCompras();
    }

    @Override
    public boolean eliminarCompra(int idCompra) {
        return repository.eliminarCompra(idCompra);
    }
}
