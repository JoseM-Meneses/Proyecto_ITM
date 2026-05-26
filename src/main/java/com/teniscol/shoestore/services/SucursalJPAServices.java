package com.teniscol.shoestore.services;

import com.teniscol.shoestore.identidadesJPA.Sucursal;
import com.teniscol.shoestore.identidadesJPA.Tenis;
import com.teniscol.shoestore.repositoryJPA.SucursalJPARepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SucursalJPAServices implements SucursalJPAServicesInterface {
    private final SucursalJPARepository repository;

    public SucursalJPAServices(SucursalJPARepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Sucursal> obtenerSucursales() {
        return repository.findAll();
    }

    @Override
    public Sucursal guardarSucursal(Sucursal sucursal) {
        return repository.save(sucursal);
    }

    @Override
    public boolean eliminarSucursal(int idSucursal) {
        if (!repository.existsById(idSucursal)) return false;
        repository.deleteById(idSucursal);
        return true;
    }

    @Override
    public boolean actualizarSucursal(int idSucursal, String nombre, String ciudad) {

        Optional<Sucursal> optional = repository.findById(idSucursal);

        if (optional.isEmpty()) {
            return false;
        }

        Sucursal sucursal = optional.get();

        sucursal.setNombre(nombre);
        sucursal.setCiudad(ciudad);

        repository.save(sucursal);

        return true;
    }
}
