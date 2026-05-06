package com.teniscol.shoestore.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;


public class CompraDetalleDTO {

    private int idCompra;
    private int idCliente;
    private int idSucursal;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date fecha;

    private int idTenis;
    private int talla;
    private int cantidad;
    private float precioUnitario;

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getTalla() {
        return talla;
    }

    public void setTalla(int talla) {
        this.talla = talla;
    }

    public int getIdTenis() {
        return idTenis;
    }

    public void setIdTenis(int idTenis) {
        this.idTenis = idTenis;
    }

    public float getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(float precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
