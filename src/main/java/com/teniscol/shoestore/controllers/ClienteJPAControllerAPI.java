package com.teniscol.shoestore.controllers;

import com.teniscol.shoestore.identidadesJPA.Cliente;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClienteJPAControllerAPI {
    List<Cliente> obtenerClientes();
    ResponseEntity<Cliente> guardarCliente(String nombre, String correo, String telefono);
    ResponseEntity<String> eliminarCliente(int id);
    ResponseEntity<String> actualizarCliente(int id, String correo, String telefono);

}
