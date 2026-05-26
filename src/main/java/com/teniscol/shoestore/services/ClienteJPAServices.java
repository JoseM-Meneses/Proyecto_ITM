package com.teniscol.shoestore.services;

import com.teniscol.shoestore.identidadesJPA.Cliente;
import com.teniscol.shoestore.identidadesJPA.Sucursal;
import com.teniscol.shoestore.repositoryJPA.ClienteJPARepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public boolean actualizarCliente(int id, String correo, String telefono) {

        Optional<Cliente> optional = repository.findById(id);

        if (optional.isEmpty()) {
            return false;
        }

        Cliente cliente = optional.get();

        cliente.setCorreo(correo);
        cliente.setTelefono(telefono);

        repository.save(cliente);

        return true;
    }
}
