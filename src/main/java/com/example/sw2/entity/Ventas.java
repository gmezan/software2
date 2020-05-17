package com.example.sw2.entity;

import com.example.sw2.constantes.VentasId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Ventas")
public class Ventas {

    @EmbeddedId
    private VentasId id;

    @Size(max = 11, message = "El Ruc/Dni debe contener 11 caracteres")
    @Column(name="ruc_dni")
    private String rucdni;
    @NotBlank
    @Size(max = 45, message = "El Ruc/Dni debe contener 45 caracteres")
    @Column(nullable = false)
    private String nombrecliente;
    @Column(nullable = false)
    private String lugarventa;
    @ManyToOne
    @JoinColumn(name="productoinventario",nullable = false)
    private Inventario inventario;
    @Column(nullable = false)
    private LocalDate fecha;
    @ManyToOne
    @JoinColumn(name="vendedor",nullable = false)
    private Usuarios vendedor;
    @Column(nullable = false)
    private int cantidad;
    @Column(name="precio_venta",nullable = false)
    private BigDecimal precioventa;
    @Column(name="fecha_modificacion")
    private LocalDateTime fechamodificacion;
    @Column(name="fecha_creacion",nullable =false)
    private LocalDateTime fechacreacion;

    public VentasId getId() {
        return id;
    }

    public void setId(VentasId id) {
        this.id = id;
    }

    public String getRucdni() {
        return rucdni;
    }

    public void setRucdni(String rucdni) {
        this.rucdni = rucdni;
    }

    public String getNombrecliente() {
        return nombrecliente;
    }

    public void setNombrecliente(String nombrecliente) {
        this.nombrecliente = nombrecliente;
    }

    public String getLugarventa() {
        return lugarventa;
    }

    public void setLugarventa(String lugarventa) {
        this.lugarventa = lugarventa;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Usuarios getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuarios vendedor) {
        this.vendedor = vendedor;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(BigDecimal precioventa) {
        this.precioventa = precioventa;
    }

    public LocalDateTime getFechamodificacion() {
        return fechamodificacion;
    }

    public void setFechamodificacion(LocalDateTime fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    public LocalDateTime getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(LocalDateTime fechacreacion) {
        this.fechacreacion = fechacreacion;
    }
}
