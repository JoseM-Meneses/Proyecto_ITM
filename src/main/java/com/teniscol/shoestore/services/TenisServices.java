package com.teniscol.shoestore.services;

import com.teniscol.shoestore.identidadesSQL.Tenis;
import com.teniscol.shoestore.repositoriesSQL.TenisRepositoryInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenisServices implements TenisServicesInterface {

    private final TenisRepositoryInterface repository;

    public TenisServices(TenisRepositoryInterface repository) {
        this.repository = repository;
    }

    @Override
    public List<Tenis> obtenerTenis() {
        return repository.obtenerTodos();
    }

    @Override
    public Tenis insertarTenis(Tenis tenis) {
        if (tenis.getPrecio() <= 0) {
            throw new IllegalArgumentException("Precio inválido");
        }

        if (tenis.getStock() < 0) {
            throw new IllegalArgumentException("Stock inválido");
        }

        return repository.insertarTenis(tenis);
    }

    @Override
    public boolean actualizarStock(int idTenis, double precio, int stock) {
        if (precio <= 0) {
            throw new IllegalArgumentException("Precio inválido");
        }

        if (stock < 0) {
            throw new IllegalArgumentException("Stock inválido");
        }

        return repository.actualizarStock(idTenis, precio, stock);
    }

    @Override
    public boolean eliminarTenis(int idTenis) {
        return repository.eliminarTenis(idTenis);
    }

    @Override
    public int totalStock() {
        return repository.totalStock();
    }
}
