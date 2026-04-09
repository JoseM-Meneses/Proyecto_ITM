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
                        rs.getInt("id_tenis"),
                        rs.getString("marca"),
                        rs.getString("modelo"),
                        rs.getDouble("precio"),
                        rs.getInt("stock")
                );
                lista.add(t);
            }

        } catch (Exception e) {
            System.out.println("Error obtener al obtener todos los tenis: " + e.getMessage());
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

    public void eliminar(int id) {
        String sql = "DELETE FROM tenis WHERE id_tenis = ?";

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("Producto con ID " + id + " eliminado con éxito.");
            } else {
                System.out.println("No se encontró ningún producto con el ID: " + id);
            }

        } catch (Exception e) {
            System.out.println("Error al eliminar el producto: " + e.getMessage());
        }
    }

    public boolean realizarCompra(int idTenis, int idCliente, int idSucursal, int talla, int cantidad) {

        String verificar = "SELECT stock FROM tenis WHERE id_tenis = ?";

        String actualizar = "UPDATE tenis SET stock = stock - ? WHERE id_tenis = ?";

        String insertarCompra = "INSERT INTO compra (id_cliente, id_sucursal) VALUES (?, ?)";

        String insertarDetalle = "INSERT INTO detalle_compra (id_compra, id_tenis, talla, cantidad, precio_unitario) " +
                "VALUES (?, ?, ?, ?, (SELECT precio FROM tenis WHERE id_tenis = ?))";

        Connection con = null;
        try {
            con = Conexion.obtenerConexion();
            con.setAutoCommit(false);

            PreparedStatement ps1 = con.prepareStatement(verificar);
            ps1.setInt(1, idTenis);
            ResultSet rs = ps1.executeQuery();
            if (!rs.next() || rs.getInt("stock") < cantidad) {
                return false;
            }

            PreparedStatement ps2 = con.prepareStatement(actualizar);
            ps2.setInt(1, cantidad);
            ps2.setInt(2, idTenis);
            ps2.executeUpdate();

            PreparedStatement ps3 = con.prepareStatement(insertarCompra, Statement.RETURN_GENERATED_KEYS);
            ps3.setInt(1, idCliente);
            ps3.setInt(2, idSucursal);
            ps3.executeUpdate();

            ResultSet generatedKeys = ps3.getGeneratedKeys();
            int idGenerado = 0;
            if (generatedKeys.next()) idGenerado = generatedKeys.getInt(1);

            PreparedStatement ps4 = con.prepareStatement(insertarDetalle);
            ps4.setInt(1, idGenerado);
            ps4.setInt(2, idTenis);
            ps4.setInt(3, talla);
            ps4.setInt(4, cantidad);
            ps4.setInt(5, idTenis);
            ps4.executeUpdate();

            con.commit();
            return true;

        } catch (Exception e) {
            if (con != null) try { con.rollback(); } catch (SQLException ex) {}
            System.out.println("Error en compra: " + e.getMessage());
            return false;
        }
    }
}
