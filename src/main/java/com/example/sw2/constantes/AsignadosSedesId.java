package com.example.sw2.constantes;

import com.example.sw2.entity.Inventario;
import com.example.sw2.entity.Usuarios;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Embeddable
public class AsignadosSedesId implements Serializable {

    @Column(name= "gestor")
    private int gestor;
    @Column(name= "sede")
    private int sede;
    @Column(name= "producto_inventario")
    private String producto_inventario;
    @Column(name = "fecha_inventario")
    private LocalDate fecha_inventario;

    public AsignadosSedesId(){}

    public AsignadosSedesId(int a, int b, String c, LocalDate d){
        this.setGestor(a);
        this.setSede(b);
        this.setProducto_inventario(c);
        this.setFecha_inventario(d);
    }

    public int getGestor() {
        return gestor;
    }

    public void setGestor(int gestor) {
        this.gestor = gestor;
    }

    public int getSede() {
        return sede;
    }

    public void setSede(int sede) {
        this.sede = sede;
    }

    public String getProducto_inventario() {
        return producto_inventario;
    }

    public void setProducto_inventario(String producto_inventario) {
        this.producto_inventario = producto_inventario;
    }

    public LocalDate getFecha_inventario() {
        return fecha_inventario;
    }

    public void setFecha_inventario(LocalDate fecha_inventario) {
        this.fecha_inventario = fecha_inventario;
    }
}
