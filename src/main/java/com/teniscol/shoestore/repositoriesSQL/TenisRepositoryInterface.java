package com.teniscol.shoestore.repositoriesSQL;

import com.teniscol.shoestore.identidadesSQL.Tenis;
import java.util.List;

public interface TenisRepositoryInterface {

    List<Tenis> obtenerTodos();
    Tenis insertarTenis(Tenis tenis);
    boolean actualizarStock(int idTenis, double precio, int stock);
    boolean eliminarTenis(int idTenis);
    int totalStock();
}