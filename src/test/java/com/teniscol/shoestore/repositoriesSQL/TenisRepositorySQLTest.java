package com.teniscol.shoestore.repositoriesSQL;

import com.teniscol.shoestore.identidadesSQL.Tenis;
import com.teniscol.shoestore.utilities.Conexion;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TenisRepositorySQLTest {

    @Test
    void obtenerTodos_retornaLista() throws Exception {

        Connection con = mock(Connection.class);
        PreparedStatement ps = mock(PreparedStatement.class);
        ResultSet rs = mock(ResultSet.class);

        try (MockedStatic<Conexion> conexionMock = mockStatic(Conexion.class)) {

            conexionMock.when(Conexion::obtenerConexion).thenReturn(con);

            when(con.prepareStatement(anyString())).thenReturn(ps);
            when(ps.executeQuery()).thenReturn(rs);

            when(rs.next()).thenReturn(true, true, false);

            when(rs.getInt("id_tenis")).thenReturn(1, 2);
            when(rs.getString("marca")).thenReturn("Nike", "Adidas");
            when(rs.getString("modelo")).thenReturn("Air Max", "Forum");
            when(rs.getDouble("precio")).thenReturn(500000.0, 450000.0);
            when(rs.getInt("stock")).thenReturn(15, 20);

            TenisRepositorySQL repo = new TenisRepositorySQL();

            List<Tenis> result = repo.obtenerTodos();

            assertEquals(2, result.size());
            assertEquals("Nike", result.get(0).getMarca());
        }
    }
}
