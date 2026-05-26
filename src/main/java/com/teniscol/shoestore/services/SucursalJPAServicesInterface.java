package com.teniscol.shoestore.services;

import com.teniscol.shoestore.identidadesJPA.Sucursal;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SucursalJPAServicesInterface {
    List<Sucursal> obtenerSucursales();
    Sucursal guardarSucursal(Sucursal sucursal);
    boolean eliminarSucursal(int idSucursal);
    boolean actualizarSucursal(int idSucursal, String nombre, String ciudad);

}
