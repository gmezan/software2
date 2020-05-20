package com.example.sw2.constantes;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
public class AsignadosSedesId implements Serializable {

    @Column(name= "gestor")
    private int gestor;
    @Column(name= "sede")
    private int sede;
    @Column(name= "producto_inventario")
    private String productoinventario;
    @Column(name = "fecha_envio")
    private LocalDate fechaenvio;

    public AsignadosSedesId(){}

    public AsignadosSedesId(int a, int b, String c, LocalDate d){
        this.setGestor(a);
        this.setSede(b);
        this.setProductoinventario(c);
        this.setFechaenvio(d);
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

    public LocalDate getFechaenvio() {
        return fechaenvio;
    }

    public void setFechaenvio(LocalDate fechaenvio) {
        this.fechaenvio = fechaenvio;
    }
}
