package com.example.sw2.entity;
import org.apache.el.lang.ELSupport;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="Asignados_sedes")
public class AsignadosSedes {

    @Id
    @Column(name="idAsignados")
    private int idasignados;
    @ManyToOne
    @JoinColumn(name="gestor",nullable = false)
    private Usuarios gestor;
    @ManyToOne
    @JoinColumn(name="sede",nullable = false)
    private Usuarios sede;
    @ManyToOne
    @JoinColumn(name="producto_inventario",nullable = false)
    private Inventario inventario;
    @Column(nullable = false)
    private int stock;
    @Column(nullable = false)
    private int cantidadactual;
    @Column(nullable = false)
    private BigDecimal precioventa;
    @Column(name="fecha_envio",nullable = false)
    private LocalDate fechaenvio;
    @ManyToOne
    @JoinColumn(name="estadoasignacion",nullable = false)
    private EstadoAsignacion estadoAsignacion;
    @LastModifiedDate
    @Column(name="fecha_modificacion")
    private LocalDateTime fechamodificacion;
    @CreatedDate
    @Column(name="fecha_creacion",nullable =false)
    private LocalDateTime fechacreacion;
    private String mensaje;

    public int getIdasignados() {
        return idasignados;
    }

    public void setIdasignados(int idasignados) {
        this.idasignados = idasignados;
    }

    public Usuarios getGestor() {
        return gestor;
    }

    public void setGestor(Usuarios gestor) {
        this.gestor = gestor;
    }

    public Usuarios getSede() {
        return sede;
    }

    public void setSede(Usuarios sede) {
        this.sede = sede;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCantidadactual() {
        return cantidadactual;
    }

    public void setCantidadactual(int cantidadactual) {
        this.cantidadactual = cantidadactual;
    }

    public BigDecimal getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(BigDecimal precioventa) {
        this.precioventa = precioventa;
    }

    public LocalDate getFechaenvio() {
        return fechaenvio;
    }

    public void setFechaenvio(LocalDate fechaenvio) {
        this.fechaenvio = fechaenvio;
    }

    public EstadoAsignacion getEstadoAsignacion() {
        return estadoAsignacion;
    }

    public void setEstadoAsignacion(EstadoAsignacion estadoAsignacion) {
        this.estadoAsignacion = estadoAsignacion;
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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
