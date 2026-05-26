package com.teniscol.shoestore.services;

import com.teniscol.shoestore.identidadesJPA.Tenis;
import java.util.List;

public interface TenisJPAServicesInterface {

    List<Tenis> obtenerTenis();
    Tenis insertarTenis(Tenis tenis);
    boolean actualizarStock(int idTenis, double precio, int stock);
    boolean eliminarTenis(int idTenis);
    int totalStock();
}
