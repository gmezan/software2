package com.example.sw2.constantes;

import com.example.sw2.entity.Inventario;
import com.example.sw2.entity.Usuarios;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class AsignadosSedesId implements Serializable {
    @ManyToOne
    @JoinColumn(name = "gestor")
    private Usuarios gestor;
    @ManyToOne
    @JoinColumn(name= "sede")
    private Usuarios sede;
    @ManyToOne
    @JoinColumn(name= "producto_inventario")
    private Inventario productoinventario;
    @Column(name = "fecha_envio")
    private LocalDate fechaenvio;

    public AsignadosSedesId(){}

    public AsignadosSedesId(Usuarios gestor, Usuarios sede, Inventario inventario, LocalDate d){
        this.setGestor(gestor);
        this.setSede(sede);
        this.setProductoinventario(inventario);
        this.setFechaenvio(d);
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

    public Inventario getProductoinventario() {
        return productoinventario;
    }

    public void setProductoinventario(Inventario productoinventario) {
        this.productoinventario = productoinventario;
    }

    public LocalDate getFechaenvio() {
        return fechaenvio;
    }

    public void setFechaenvio(LocalDate fechaenvio) {
        this.fechaenvio = fechaenvio;
    }
}
