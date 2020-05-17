package com.example.sw2.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Roles")
public class Roles implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idroles;
    @Column(nullable = false)
    private String nombrerol;

    public int getIdroles() {
        return idroles;
    }

    public void setIdroles(int idroles) {
        this.idroles = idroles;
    }

    public String getNombrerol() {
        return nombrerol;
    }

    public void setNombrerol(String nombrerol) {
        this.nombrerol = nombrerol;
    }
}
