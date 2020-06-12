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
    private LocalDateTime datetime;

    private int state;

    private int ntype;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getNtype() {
        return ntype;
    }

    public void setNtype(int ntype) {
        this.ntype = ntype;
    }

    public LocalDateTime getDatetime() {
        return datetime;
    }

    public void setDatetime(LocalDateTime datetime) {
        this.datetime = datetime;
    }

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



}
