package com.teniscol.shoestore.repositoriesSQL;

import com.teniscol.shoestore.DTO.CompraDetalleDTO;
import com.teniscol.shoestore.utilities.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompraRepositorySQL {
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

    public List<CompraDetalleDTO> obtenerCompras() {
        List<CompraDetalleDTO> lista = new ArrayList<>();

        String sql = """
        SELECT c.id_compra, c.id_cliente, c.id_sucursal, c.fecha,
               d.id_tenis, d.talla, d.cantidad, d.precio_unitario
        FROM compra c
        INNER JOIN detalle_compra d ON c.id_compra = d.id_compra
    """;

        try (Connection con = Conexion.obtenerConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CompraDetalleDTO dto = new CompraDetalleDTO();

                dto.setIdCompra(rs.getInt("id_compra"));
                dto.setIdCliente(rs.getInt("id_cliente"));
                dto.setIdSucursal(rs.getInt("id_sucursal"));
                dto.setFecha(rs.getDate("fecha"));

                dto.setIdTenis(rs.getInt("id_tenis"));
                dto.setTalla(rs.getInt("talla"));
                dto.setCantidad(rs.getInt("cantidad"));
                dto.setPrecioUnitario(rs.getFloat("precio_unitario"));

                lista.add(dto);
            }

        } catch (Exception e) {
            System.out.println("Error al obtener compras: " + e.getMessage());
        }

        return lista;
    }

    public boolean eliminarCompra(int idCompra) {

        String sqlDetalle = "DELETE FROM detalle_compra WHERE id_compra = ?";
        String sqlCompra = "DELETE FROM compra WHERE id_compra = ?";

        try (Connection con = Conexion.obtenerConexion()) {

            con.setAutoCommit(false);

            try (PreparedStatement psDetalle = con.prepareStatement(sqlDetalle);
                 PreparedStatement psCompra = con.prepareStatement(sqlCompra)) {

                psDetalle.setInt(1, idCompra);
                psDetalle.executeUpdate();

                psCompra.setInt(1, idCompra);
                int filas = psCompra.executeUpdate();

                con.commit();

                return filas > 0;

            } catch (Exception e) {
                con.rollback();
                System.out.println("Error al eliminar compra: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }

        return false;
    }
}
