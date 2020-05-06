package com.example.sw2.entity;


import javax.persistence.*;

@Entity
@Table(name = "Tienda")
public class Tienda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="idTienda")
    private int idtienda;
    @Column(nullable = false)
    private String nombre;

    public int getIdtienda() {
        return idtienda;
    }

    public void setIdtienda(int idtienda) {
        this.idtienda = idtienda;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
