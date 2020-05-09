package com.example.sw2.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name="Artesanos")
public class Artesanos {

    @Id
    @NotBlank(message = "Este campo no puede estar vacío")
    @Size(max = 4, min = 2, message = "El código debe contener 3 letras")
    private String codigo;
    @NotBlank(message = "Este campo no puede estar vacío")
    @Column(nullable =false)
    private String nombre;
    @NotBlank(message = "Este campo no puede estar vacío")
    @Column(nullable =false)
    private String apellidopaterno;
    private String apellidomaterno;
    @ManyToOne
    @JoinColumn(name="comunidad",nullable =false)
    private Comunidades comunidades;
    @Column(name="fecha_modificacion")
    private LocalDateTime fechamodificacion;
    @Column(name="fecha_creacion",nullable =false)
    private LocalDateTime fechacreacion;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidopaterno() {
        return apellidopaterno;
    }

    public void setApellidopaterno(String apellidopaterno) {
        this.apellidopaterno = apellidopaterno;
    }

    public String getApellidomaterno() {
        return apellidomaterno;
    }

    public void setApellidomaterno(String apellidomaterno) {
        this.apellidomaterno = apellidomaterno;
    }

    public Comunidades getComunidades() {
        return comunidades;
    }

    public void setComunidades(Comunidades comunidades) {
        this.comunidades = comunidades;
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
