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
    private String productoinventario;
    @Column(name = "fecha_inventario")
    private LocalDate fechainventario;

    public AsignadosSedesId(){}

    public AsignadosSedesId(int a, int b, String c, LocalDate d){
        this.setGestor(a);
        this.setSede(b);
        this.setProductoinventario(c);
        this.setFechainventario(d);
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

    public String getProductoinventario() {
        return productoinventario;
    }

    public void setProductoinventario(String productoinventario) {
        this.productoinventario = productoinventario;
    }

    public LocalDate getFechainventario() {
        return fechainventario;
    }

    public void setFechainventario(LocalDate fechainventario) {
        this.fechainventario = fechainventario;
    }
}
