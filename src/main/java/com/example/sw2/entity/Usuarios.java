package com.example.sw2.entity;


import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "Usuarios")
public class Usuarios implements Serializable {

    @Id
    //@NotBlank
    @Digits(integer = 11, fraction = 0)
    @Column(name = "dni")
    private int idusuarios;
    @Column(nullable = false)
    @NotBlank
    @Size(max = 45, message = "Debe contener 45 caracteres como maximo")
    private String nombre;
    @Size(max = 45, message = "Debe contener 45 caracteres como maximo")
    @Column(nullable = false)
    @NotBlank
    private String apellido;
    //@Size(max = 100, message = "Debe contener 100 caracteres como maximo")
    private String foto;
    @Column(nullable = false)
    @Size(max = 45, message = "Debe contener 45 caracteres como maximo")
    @NotBlank
    private String correo;
    @Column(nullable = false)
    //@Size(max = 256, message = "Debe contener 256 caracteres como maximo")
    //@NotBlank
    private String password;
    @Size(max = 45, message = "Debe contener 45 caracteres como maximo")
    private String telefono;
    @ManyToOne
    @JoinColumn(name = "rol",nullable = false)
    private Roles roles;
    //@Digits(integer = 4, fraction = 0)
    //@NotBlank
    @Column(nullable = false)
    private Boolean cuentaactivada=false;
    @LastModifiedDate
    @Column(name="fecha_modificacion")
    private LocalDateTime fechamodificacion;
    @CreatedDate
    @Column(name="fecha_creacion",nullable =false)
    private LocalDateTime fechacreacion=LocalDateTime.now();


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }


    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;//new BCryptPasswordEncoder().encode(password);
    }

    public void setRawPassword(String ps){this.password = ps ;}

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public Boolean getCuentaactivada() {
        return cuentaactivada;
    }

    public void setCuentaactivada(Boolean cuentaactivada) {
        this.cuentaactivada = cuentaactivada;
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


    public int getIdusuarios() {
        return idusuarios;
    }

    public void setIdusuarios(int idusuarios) {
        this.idusuarios = idusuarios;
    }

    public String getFullname(){
        return this.nombre + " " + this.apellido;
    }

}
