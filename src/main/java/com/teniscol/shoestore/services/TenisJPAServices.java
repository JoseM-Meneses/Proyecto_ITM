package com.teniscol.shoestore.services;

import com.teniscol.shoestore.identidadesJPA.Tenis;
import com.teniscol.shoestore.repositoryJPA.TenisJPARepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TenisJPAServices implements TenisJPAServicesInterface {

    private final TenisJPARepository repository;

    public TenisJPAServices(TenisJPARepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Tenis> obtenerTenis() {
        return repository.findAll();
    }

    @Override
    public Tenis insertarTenis(Tenis tenis) {

        if (tenis.getPrecio() <= 0) {
            throw new IllegalArgumentException("Precio inválido");
        }

        if (tenis.getStock() < 0) {
            throw new IllegalArgumentException("Stock inválido");
        }

        return repository.save(tenis);
    }

    @Override
    public boolean actualizarStock(int idTenis, double precio, int stock) {

        Optional<Tenis> optional = repository.findById(idTenis);

        if (optional.isEmpty()) {
            return false;
        }

        Tenis tenis = optional.get();

        tenis.setPrecio(precio);
        tenis.setStock(stock);

        repository.save(tenis);

        return true;
    }

    @Override
    public boolean eliminarTenis(int idTenis) {

        if (!repository.existsById(idTenis)) {
            return false;
        }

        repository.deleteById(idTenis);

        return true;
    }

    @Override
    public int totalStock() {

        List<Tenis> lista = repository.findAll();

        return lista.stream()
                .mapToInt(Tenis::getStock)
                .sum();
    }
}
