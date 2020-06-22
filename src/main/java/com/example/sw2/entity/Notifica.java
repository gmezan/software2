package com.example.sw2.entity;

import com.example.sw2.config.Auditable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Entity
@Table(name = "Notificaciones")
public class Notifica extends Auditable implements Serializable {

    @Id
    private int idnotificaciones;
    @ManyToOne
    @JoinColumn(name = "usuario",nullable = false)
    private Usuarios usuario;
    @Column(nullable = false)
    private String mensaje;
    @Column(nullable = false)
    private int estado;
    @Column(nullable = false)
    private int ntype;

    @Transient
    private String beautifiedDatetime;

    public void createBeautifiedDatetime(){
        /*
            menos de 1 hora -> hace x minutos
            mas o 1 horas -> hoy a las hh:mm
            otro día en la misma semana -> nombre del día
            otro día de otra semana -> dd de Mes
        */
        String s = super.getFechacreacion().toLocalTime().toString()+" - ";
        s+=super.getFechacreacion().toLocalDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
        beautifiedDatetime = s;
    }

    public String getBeautifiedDatetime() {
        return beautifiedDatetime;
    }

    public void setBeautifiedDatetime(String beautifiedDatetime) {
        this.beautifiedDatetime = beautifiedDatetime;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getNtype() {
        return ntype;
    }

    public void setNtype(int ntype) {
        this.ntype = ntype;
    }

    public int getIdnotificaciones() {
        return idnotificaciones;
    }

    public void setIdnotificaciones(int idnotificaciones) {
        this.idnotificaciones = idnotificaciones;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }



}
