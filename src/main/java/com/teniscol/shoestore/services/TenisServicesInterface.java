package com.teniscol.shoestore.services;

import com.teniscol.shoestore.identidadesSQL.Tenis;
import java.util.List;

public interface TenisServicesInterface {

    List<Tenis> obtenerTenis();

    Tenis insertarTenis(Tenis tenis);

    boolean actualizarStock(int idTenis, double precio, int stock);

    boolean eliminarTenis(int idTenis);

    int totalStock();
}
