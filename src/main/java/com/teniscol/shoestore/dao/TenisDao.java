package com.teniscol.shoestore.dao;

import com.teniscol.shoestore.services.Tenis;
import com.teniscol.shoestore.utilities.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TenisDao {

    public List<Tenis> obtenerTodos() {
        List<Tenis> lista = new ArrayList<>();

        String sql = "SELECT * FROM tenis";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Tenis t = new Tenis(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                );
                lista.add(t);
            }

        } catch (Exception e) {
            System.out.println("Error obtener al obtener todos: " + e.getMessage());
        }

        return lista;
    }

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
            System.out.println("Error al obtener el total del stock: " + e.getMessage());
        }

        return total;
    }

    public void actualizarStock(String marca, String modelo, Float precio, int stock) {
        String sql = "UPDATE tenis SET precio = ?, stock = ? WHERE marca = ? AND modelo = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setFloat(1, precio);
            ps.setInt(2, stock);
            ps.setString(3, marca);
            ps.setString(4, modelo);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error al actualizar el stock: " + e.getMessage());
        }
    }

    public void eliminar(String marca) {
        String sql = "DELETE FROM tenis WHERE marca = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, marca);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error al eliminar la marca: " + e.getMessage());
        }
    }

    public boolean realizarCompra(String marca, String modelo, int talla, int cantidad) {

        String verificar = "SELECT stock FROM tenis WHERE marca = ? AND modelo = ?";
        String actualizar = "UPDATE tenis SET stock = stock - ? WHERE marca = ? AND modelo = ?";
        String insertar = "INSERT INTO compra (marca, modelo, talla, cantidad) VALUES (?, ?, ?, ?)";

        try (Connection con = Conexion.obtenerConexion()) {

            PreparedStatement ps1 = con.prepareStatement(verificar);
            ps1.setString(1, marca);
            ps1.setString(2, modelo);

            ResultSet rs = ps1.executeQuery();

            if (rs.next()) {
                int stockActual = rs.getInt("stock");

                if (stockActual < cantidad) {
                    return false; // no hay stock
                }
            } else {
                return false; // no existe
            }

            PreparedStatement ps2 = con.prepareStatement(actualizar);
            ps2.setInt(1, cantidad);
            ps2.setString(2, marca);
            ps2.setString(3, modelo);
            ps2.executeUpdate();

            PreparedStatement ps3 = con.prepareStatement(insertar);
            ps3.setString(1, marca);
            ps3.setString(2, modelo);
            ps3.setInt(3, talla);
            ps3.setInt(4, cantidad);
            ps3.executeUpdate();

            return true;

        } catch (Exception e) {
            System.out.println("Error en compra: " + e.getMessage());
            return false;
        }
    }
}
