package com.example.sw2.entity;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="Asignacion_tiendas")
public class AsignacionTiendas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idtiendas;
    @ManyToOne
    @JoinColumn(name="productoasignado",nullable = false)
    private AsignadosSedes asignadosSedes;
    @Column(nullable = false)
    private int stock;
    @Column(name="fecha_asignacion", nullable = false)
    private LocalDate fechaasignacion;
    @ManyToOne
    @JoinColumn(name="tienda",nullable = false)
    private Tienda tienda;
    @LastModifiedDate
    @Column(name="fecha_modificacion")
    private LocalDateTime fechamodificacion;
    @CreatedDate
    @Column(name="fecha_creacion",nullable =false)
    private LocalDateTime fechacreacion;
    @ManyToOne
    @JoinColumn(name = "asignado_sede_gestor")
    private AsignadosSedes gestor;
    @ManyToOne
    @JoinColumn(name = "asignado_sede_inventario")
    private AsignadosSedes inventario;
    @ManyToOne
    @JoinColumn(name = "asignado_sede_sede")
    private AsignadosSedes sede;
    @ManyToOne
    @JoinColumn(name = "asignado_sede_fecha")
    private AsignadosSedes fecha;

    public AsignadosSedes getGestor() {
        return gestor;
    }

    public void setGestor(AsignadosSedes gestor) {
        this.gestor = gestor;
    }

    public AsignadosSedes getInventario() {
        return inventario;
    }

    public void setInventario(AsignadosSedes inventario) {
        this.inventario = inventario;
    }

    public AsignadosSedes getSede() {
        return sede;
    }

    public void setSede(AsignadosSedes sede) {
        this.sede = sede;
    }

    public AsignadosSedes getFecha() {
        return fecha;
    }

    public void setFecha(AsignadosSedes fecha) {
        this.fecha = fecha;
    }

    public int getIdtiendas() {
        return idtiendas;
    }

    public void setIdtiendas(int idtiendas) {
        this.idtiendas = idtiendas;
    }

    public AsignadosSedes getAsignadosSedes() {
        return asignadosSedes;
    }

    public void setAsignadosSedes(AsignadosSedes asignadosSedes) {
        this.asignadosSedes = asignadosSedes;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public LocalDate getFechaasignacion() {
        return fechaasignacion;
    }

    public void setFechaasignacion(LocalDate fechaasignacion) {
        this.fechaasignacion = fechaasignacion;
    }

    public Tienda getTienda() {
        return tienda;
    }

    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
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
