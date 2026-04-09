package com.teniscol.shoestore.services;

public class Compra {

    private int id;
    private int idCliente;
    private int idSucursal;
    private String fecha;

    public Compra() {
    }

    public Compra(int id, int idCliente, int idSucursal, String fecha) {
        this.id = id;
        this.idCliente = idCliente;
        this.idSucursal = idSucursal;
        this.fecha = fecha;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public int getIdSucursal() { return idSucursal; }
    public void setIdSucursal(int idSucursal) { this.idSucursal = idSucursal; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
}
