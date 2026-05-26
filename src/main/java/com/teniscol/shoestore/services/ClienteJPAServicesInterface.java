package com.teniscol.shoestore.services;

import com.teniscol.shoestore.identidadesJPA.Cliente;

import java.util.List;

public interface ClienteJPAServicesInterface {
    List<Cliente> obtenerClientes();
    Cliente guardarCliente(Cliente cliente);
    boolean eliminarCliente(int id);
    boolean actualizarCliente(int id, String correo, String telefono);

}
