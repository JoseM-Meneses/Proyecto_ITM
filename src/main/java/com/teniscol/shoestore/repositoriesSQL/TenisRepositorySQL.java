package com.teniscol.shoestore.repositoriesSQL;

import com.teniscol.shoestore.identidadesSQL.Tenis;
import com.teniscol.shoestore.utilities.Conexion;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TenisRepositorySQL implements TenisRepositoryInterface {

    @Override
    public List<Tenis> obtenerTodos() {
        List<Tenis> lista = new ArrayList<>();
        String sql = "SELECT * FROM tenis";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tenis t = Tenis.builder()
                        .idTenis(rs.getInt("id_tenis"))
                        .marca(rs.getString("marca"))
                        .modelo(rs.getString("modelo"))
                        .precio(rs.getDouble("precio"))
                        .stock(rs.getInt("stock"))
                        .build();

                lista.add(t);
            }

        } catch (Exception e) {
            System.out.println("Error al obtener tenis: " + e.getMessage());
        }

        return lista;
    }

    @Override
    public int totalStock() {
        int total = 0;
        String sql = "SELECT SUM(stock) AS total FROM tenis";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                total = rs.getInt("total");
            }

        } catch (Exception e) {
            System.out.println("Error total stock: " + e.getMessage());
        }

        return total;
    }

    @Override
    public boolean actualizarStock(int idTenis, double precio, int stock) {
        String sql = "UPDATE tenis SET precio = ?, stock = ? WHERE id_tenis = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, precio);
            ps.setInt(2, stock);
            ps.setInt(3, idTenis);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error actualizar: " + e.getMessage());
        }

        return false;
    }

    @Override
    public boolean eliminarTenis(int idTenis) {
        String sql = "DELETE FROM tenis WHERE id_tenis = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idTenis);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error eliminar: " + e.getMessage());
        }

        return false;
    }

    @Override
    public Tenis insertarTenis(Tenis tenis) {
        String sql = "INSERT INTO tenis (marca, modelo, stock, precio) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, tenis.getMarca());
            ps.setString(2, tenis.getModelo());
            ps.setInt(3, tenis.getStock());
            ps.setDouble(4, tenis.getPrecio());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                tenis.setIdTenis(rs.getInt(1));
            }

        } catch (Exception e) {
            System.out.println("Error insertar: " + e.getMessage());
        }

        return tenis;
    }
}
