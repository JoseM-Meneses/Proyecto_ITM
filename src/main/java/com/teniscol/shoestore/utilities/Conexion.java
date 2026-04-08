package com.teniscol.shoestore.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=tenis_col;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "89985122Jm.";

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try {
            Connection con = Conexion.obtenerConexion();
            if (con != null) {
                System.out.println("Conectado a SQL");
            }
        } catch (Exception e) {
            System.out.println("Error de conexión: " + e.getMessage());
        }
    }
}
