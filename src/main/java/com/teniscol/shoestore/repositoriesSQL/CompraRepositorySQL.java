package com.teniscol.shoestore.repositoriesSQL;

import com.teniscol.shoestore.DTO.CompraDetalleDTO;
import com.teniscol.shoestore.utilities.Conexion;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CompraRepositorySQL implements CompraRepositoryInterface {

    @Override
    public CompraDetalleDTO realizarCompra(
            int idTenis,
            int idCliente,
            int idSucursal,
            int talla,
            int cantidad) {

        String verificar = " SELECT stock, precio FROM tenis WHERE id_tenis = ? ";

        String actualizar = " UPDATE tenis SET stock = stock - ? WHERE id_tenis = ? ";

        String insertarCompra = "INSERT INTO compra(id_cliente, id_sucursal) VALUES (?, ?)";

        String insertarDetalle = " INSERT INTO detalle_compra (id_compra, id_tenis, talla, cantidad, precio_unitario) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexion.obtenerConexion()) {

            con.setAutoCommit(false);

            try (
                    PreparedStatement ps1 = con.prepareStatement(verificar);
                    PreparedStatement ps2 = con.prepareStatement(actualizar);
                    PreparedStatement ps3 = con.prepareStatement(insertarCompra, Statement.RETURN_GENERATED_KEYS);
                    PreparedStatement ps4 = con.prepareStatement(insertarDetalle)
            ) {

                ps1.setInt(1, idTenis);

                float precio = 0;

                try (ResultSet rs = ps1.executeQuery()) {
                    if (!rs.next()) {
                        con.rollback();
                        return null;
                    }

                    int stock = rs.getInt("stock");

                    if (stock < cantidad) {
                        con.rollback();
                        return null;
                    }

                    precio = rs.getFloat("precio");
                }

                ps2.setInt(1, cantidad);
                ps2.setInt(2, idTenis);

                ps2.executeUpdate();

                ps3.setInt(1, idCliente);
                ps3.setInt(2, idSucursal);

                ps3.executeUpdate();

                int idCompra = 0;

                try (ResultSet generatedKeys =
                             ps3.getGeneratedKeys()) {

                    if (generatedKeys.next()) {
                        idCompra = generatedKeys.getInt(1);}
                }

                ps4.setInt(1, idCompra);
                ps4.setInt(2, idTenis);
                ps4.setInt(3, talla);
                ps4.setInt(4, cantidad);
                ps4.setFloat(5, precio);

                ps4.executeUpdate();

                con.commit();
                CompraDetalleDTO dto =
                        new CompraDetalleDTO();

                dto.setIdCompra(idCompra);
                dto.setIdCliente(idCliente);
                dto.setIdSucursal(idSucursal);

                dto.setIdTenis(idTenis);
                dto.setTalla(talla);
                dto.setCantidad(cantidad);

                dto.setPrecioUnitario(precio);

                return dto;

            } catch (Exception e) {

                con.rollback();
                System.out.println(
                        "Error en compra: "
                                + e.getMessage()
                );
            }

        } catch (Exception e) {
            System.out.println(
                    "Error de conexión: "
                            + e.getMessage()
            );
        }

        return null;
    }

    @Override
    public List<CompraDetalleDTO> obtenerCompras() {

        List<CompraDetalleDTO> lista =
                new ArrayList<>();

        String sql = " SELECT c.id_compra, c.id_cliente, c.id_sucursal, c.fecha, d.id_tenis, d.talla, d.cantidad, d.precio_unitario FROM compra c INNER JOIN detalle_compra d ON c.id_compra = d.id_compra ";

        try (
                Connection con = Conexion.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

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

        } catch (Exception e) {System.out.println(
                "Error al obtener compras: " + e.getMessage());}

        return lista;
    }

    @Override
    public boolean eliminarCompra(int idCompra) {

        String sqlDetalle = " DELETE FROM detalle_compraWHERE id_compra = ? ";

        String sqlCompra = " DELETE FROM compra WHERE id_compra = ?";

        try (Connection con = Conexion.obtenerConexion()) {
            con.setAutoCommit(false);

            try (
                    PreparedStatement psDetalle = con.prepareStatement(sqlDetalle);
                    PreparedStatement psCompra = con.prepareStatement(sqlCompra)
            ) {

                psDetalle.setInt(1, idCompra);
                psDetalle.executeUpdate();
                psCompra.setInt(1, idCompra);

                int filas = psCompra.executeUpdate();

                con.commit();
                return filas > 0;

            } catch (Exception e) {
                con.rollback();
                System.out.println(
                        "Error al eliminar compra: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }

        return false;
    }
}