package com.example.sw2.entity;

import com.example.sw2.constantes.CustomConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "Productos")
public class Productos {

    @Id
    private String codigonom;
    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
    @Column(nullable = false)
    private String codigodesc;
    @Column(nullable = false)
    private int linea;
    @Column(name="fecha_modificacion")
    private LocalDateTime fechamodificacion;
    @Column(name="fecha_creacion",nullable =false)
    private LocalDateTime fechacreacion;

    public String getNombreLinea(){
        return CustomConstants.getLineas().get(this.linea);
    }


    public String getCodigonom() {
        return codigonom;
    }

    public void setCodigonom(String codigonom) {
        this.codigonom = codigonom;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCodigodesc() {
        return codigodesc;
    }

    public void setCodigodesc(String codigodesc) {
        this.codigodesc = codigodesc;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
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
