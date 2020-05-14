package com.example.sw2.entity;

import com.example.sw2.constantes.CustomConstants;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Table(name = "Productos")
public class Productos {

    @Id
    @NotBlank
    private String codigonom;
    @Column(nullable = false)
    @NotBlank
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
    @NotBlank
    @Column(nullable = false)
    private String codigodesc;
    @Range(max = 3, min = 1)
    @Column(nullable = false)
    private int linea;
    @LastModifiedDate
    @Column(name="fecha_modificacion")
    private LocalDateTime fechamodificacion;
    @CreatedDate
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
