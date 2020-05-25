package com.example.sw2.entity;

import com.example.sw2.constantes.CustomConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@EnableJpaAuditing
@Table(name = "Productos")
public class Productos implements Serializable {

    @Id
    @NotBlank
    @Size(max = 3, message = "El codigo debe contener máximo 3 caracteres")
    private String codigonom;
    @Column(nullable = false)
    @NotBlank
    private String nombre;
    @Column(nullable = false)
    private String descripcion;
    @NotBlank
    @Column(nullable = false)
    @Size(max = 3, message = "El codigo debe contener máximo 3 caracteres")
    private String codigodesc;
    @NotBlank
    @Column(name="linea",nullable = false)
    private String codigolinea;
    @LastModifiedDate
    @Column(name="fecha_modificacion")
    private LocalDateTime fechamodificacion;
    @CreatedDate
    @Column(name="fecha_creacion",nullable =false)
    private LocalDateTime fechacreacion;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "productos")
    private List<Inventario> inventario;


    public List<Inventario> getInventario() {
        return inventario;
    }
    public void setInventario(List<Inventario> inventario) {
        this.inventario = inventario;
    }



    public Productos(){
        this.fechacreacion=LocalDateTime.now();
    }

    public String getNombreLinea(){
        return CustomConstants.getLineas().get(this.codigolinea);
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

    public void setCodigolinea(String codigolinea) {
        this.codigolinea = codigolinea;
    }

    public String getCodigolinea() {
        return codigolinea;
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
