package com.example.sw2.entity;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="Asignacion_tienda")
public class AsignacionTiendas implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idtiendas;
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
    @JoinColumns({
            @JoinColumn(name = "gestor", referencedColumnName = "gestor"),
            @JoinColumn(name = "sede", referencedColumnName = "sede"),
            @JoinColumn(name = "producto_inventario", referencedColumnName = "producto_inventario"),
            @JoinColumn(name = "fecha_envio",referencedColumnName = "fecha_envio")
    })
    private AsignadosSedes asignadosSedes;



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
