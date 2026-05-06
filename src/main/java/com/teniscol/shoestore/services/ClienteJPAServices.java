package com.teniscol.shoestore.services;

import com.teniscol.shoestore.identidadesJPA.Cliente;
import com.teniscol.shoestore.repositoryJPA.ClienteJPARepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteJPAServices implements ClienteJPAServicesInterface {

    private final ClienteJPARepository repository;

    public ClienteJPAServices(ClienteJPARepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cliente> obtenerClientes() {
        return repository.findAll();
    }

    @Override
    public Cliente guardarCliente(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    public boolean eliminarCliente(int id) {
        if (!repository.existsById(id)) return false;
        repository.deleteById(id);
        return true;
    }
}
