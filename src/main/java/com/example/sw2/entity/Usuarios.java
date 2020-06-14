package com.example.sw2.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import net.bytebuddy.utility.RandomString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Entity
@Table(name = "Usuarios")
public class Usuarios implements Serializable {

    @Id
    //@NotBlank
    @Digits(integer = 8, fraction = 0)
    @Column(name = "dni")
    private int idusuarios;
    @Column(nullable = false)
    @NotBlank(message = "Este campo no puede estar vacío")
    @Size(max = 20, message = "Debe contener 20 caracteres como maximo")
    private String nombre;
    @Size(max = 20, message = "Debe contener 20 caracteres como maximo")
    @Column(nullable = false)
    @NotBlank(message = "Este campo no puede estar vacío")
    private String apellido;

    private String foto;
    @Column(nullable = false)
    @Size(max = 45, message = "Debe contener 45 caracteres como maximo")
    @NotBlank(message = "Este campo no puede estar vacío")
    private String correo;
    @Column(nullable = false)
    @JsonIgnore
    private String password;
    @Positive
    @Digits(integer = 9, fraction=0, message = "Ingrese un número de celular válido")
    private int telefono;
    @ManyToOne
    @JoinColumn(name = "rol",nullable = false)
    private Roles roles;
    //@Digits(integer = 4, fraction = 0)
    //@NotBlank
    @Column(nullable = false)
    private Boolean cuentaactivada=true;
    @LastModifiedDate
    @Column(name="fecha_modificacion")
    private LocalDateTime fechamodificacion;
    @CreatedDate
    @Column(name="fecha_creacion",nullable =false)
    private LocalDateTime fechacreacion=LocalDateTime.now();

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vendedor")
    private List<Ventas> ventas;


    public Usuarios(){

    }

    public Usuarios(int idusuarios){
        this.idusuarios=idusuarios;
    }

    //@JsonIgnore
    //@OneToMany(fetch = FetchType.LAZY, mappedBy = "sede")
    //private List<AsignadosSedes> asignadosSedes;


    public String generateNewPassword(){
        RandomString rs = new RandomString(8);
        int[] randomNum = {ThreadLocalRandom.current().nextInt(0, 9),ThreadLocalRandom.current().nextInt(0, 9)};
        String newpassword = rs.nextString().toLowerCase()+ String.valueOf(randomNum[0])+ String.valueOf(randomNum[1]) ;
        password = new BCryptPasswordEncoder().
                encode(newpassword);
        return newpassword;
    }

    public List<Ventas> getVentas() {
        return ventas;
    }

    public void setVentas(List<Ventas> ventas) {
        this.ventas = ventas;
    }

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
        this.correo = correo.replace(" ","");
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = new BCryptPasswordEncoder().encode(password);
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
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


    public Usuarios updateFields(Usuarios u2){
        nombre = u2.getNombre();
        apellido = u2.getApellido();
        correo = u2.getCorreo();
        telefono = u2.getTelefono();
        return this;
    }


}
