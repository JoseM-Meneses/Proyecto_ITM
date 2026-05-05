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

    public List<Tenis> obtenerTenis() {
        return repository.obtenerTodos();
    }

    public Tenis insertarTenis(Tenis tenis) {
        return repository.insertarTenis(tenis);
    }

    public boolean actualizarStock(int idTenis, double precio, int stock) {
        return repository.actualizarStock(idTenis, precio, stock);
    }

    public boolean eliminarTenis(int idTenis) {
        return repository.eliminarTenis(idTenis);
    }

    public int totalStock() {
        return repository.totalStock();
    }
}
