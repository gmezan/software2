package com.example.sw2.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Notificaciones")
public class Notifica implements Serializable {

    @Id
    private int idnotificaciones;
    @ManyToOne
    @JoinColumn(name = "usuario",nullable = false)
    private Usuarios usuarios;
    @Column(nullable = false)
    private String mensaje;
    @Column(nullable = false)
    private LocalDateTime fecha;

    public int getIdnotificaciones() {
        return idnotificaciones;
    }

    public void setIdnotificaciones(int idnotificaciones) {
        this.idnotificaciones = idnotificaciones;
    }

    public Usuarios getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
