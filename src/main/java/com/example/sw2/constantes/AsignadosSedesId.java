package com.example.sw2.constantes;

import com.example.sw2.entity.Inventario;
import com.example.sw2.entity.Usuarios;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

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
    @Column(name = "estadoasignacion")
    private Integer estadoasignacion;
    @Column(name = "precioventa")
    @Digits(integer = 10 /*precision*/, fraction = 2 /*scale*/, message = "Ingrese un precio válido")
    private Float precioventa;


    public AsignadosSedesId(){}

    public AsignadosSedesId(Usuarios gestor, Usuarios sede, Inventario inventario, Integer estadoasignacion, Float precioventa){
        this.gestor=gestor;
        this.sede=sede;
        this.productoinventario=inventario;
        this.estadoasignacion=estadoasignacion;
        this.precioventa=precioventa;
    }

    public AsignadosSedesId(int gestor, int sede, String productoinventario, Integer estadoasignacion, Float precioventa){
        this.gestor=new Usuarios(gestor);
        this.sede=new Usuarios(sede);
        this.productoinventario=new Inventario();
        this.productoinventario.setCodigoinventario(productoinventario);
        this.estadoasignacion=estadoasignacion;
        this.precioventa=precioventa;
    }


    /*

     */

    public String getNombreEstado(){
        return CustomConstants.getEstadoAsignacion().get(estadoasignacion);
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

    public Integer getEstadoasignacion() {
        return estadoasignacion;
    }

    public void setEstadoasignacion(Integer estadoasignacion) {
        this.estadoasignacion = estadoasignacion;
    }

    public Float getPrecioventa() {
        return precioventa;
    }

    public void setPrecioventa(Float precioventa) {
        this.precioventa = precioventa;
    }
}
