package com.teniscol.shoestore.controllers;

import com.teniscol.shoestore.identidadesJPA.Sucursal;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SucursalJPAControllerAPI {
    List<Sucursal> obtenerSucursales();
    ResponseEntity<Sucursal> guardarSucursal(String nombre, String ciudad);
    ResponseEntity<String> eliminarSucursal(int idSucursal);
    ResponseEntity<String> actualizarSucursal(int idSucursal, String nombre, String ciudad);
}
