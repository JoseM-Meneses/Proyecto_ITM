package com.teniscol.shoestore.controllers;

import com.teniscol.shoestore.identidadesJPA.Cliente;
import com.teniscol.shoestore.services.ClienteJPAServicesInterface;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@Tag(name = "Cliente", description = "Operaciones relacionadas con los clientes de la tienda de tenis")
public class ClienteJPARestController implements ClienteJPAControllerAPI {

    private final ClienteJPAServicesInterface service;

    public ClienteJPARestController(ClienteJPAServicesInterface service) {
        this.service = service;
    }

    @Override
    @GetMapping("/listar")
    public List<Cliente> obtenerClientes() {
        return service.obtenerClientes();
    }

    @Override
    @PostMapping("/agregar")
    public ResponseEntity<Cliente> guardarCliente(
            @RequestParam String nombre,
            @RequestParam String correo,
            @RequestParam String telefono) {

        Cliente cliente = Cliente.builder()
                .nombre(nombre)
                .correo(correo)
                .telefono(telefono)
                .build();

        Cliente creado = service.guardarCliente(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Override
    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarCliente(@RequestParam int id_cliente) {

        boolean eliminado = service.eliminarCliente(id_cliente);

        if (!eliminado) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Cliente no encontrado");
        }

        return ResponseEntity.ok("Cliente eliminado");
    }
}