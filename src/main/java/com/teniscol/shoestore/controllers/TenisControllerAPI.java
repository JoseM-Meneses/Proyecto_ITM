package com.teniscol.shoestore.controllers;

import com.teniscol.shoestore.identidadesSQL.Tenis;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface TenisControllerAPI {

    List<Tenis> obtenerTenis();

    int totalTenis();

    ResponseEntity<String> actualizarTenis(int idTenis, double precio, int cantidad);

    ResponseEntity<String> eliminarTenis(int id);

    ResponseEntity<String> agregarTenis(String marca, String modelo, double precio, int cantidad);
}
